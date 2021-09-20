package com.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.dao.*;
import com.mall.dto.Result;
import com.mall.dto.ResultEnum;
import com.mall.pojo.*;
import com.mall.rabbitmq.MQSender;
import com.mall.service.OrderService;
import com.mall.utils.RedisUtils;
import com.mall.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 订单service 实现
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021-8-8 12:07:22
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private OrderOperateHistoryDao orderOperateHistoryDao;

    @Resource
    private MQSender mqSender;

    @Resource
    private OrderItemDao orderItemDao;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private SkuDao skuDao;

    private final String ORDER_KEY = "order_key_uniq:";

    @Override
    public IPage<OrderPageVO> getAllForPage(Integer pageNo, Integer pageSize, Integer deleteStatus) {
        IPage<OrderPageVO> page = new Page<>(pageNo, pageSize);
        return orderDao.getAllForPage(page, deleteStatus);
    }

    @Override
    public OrderVO getDetailById(Long id) {
        return orderDao.getDetailById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deliver(Long id, String operator) {
        Order order = orderDao.selectById(id);

        // 发货，更改订单状态
        order.setStatus(OrderStatusEnum.SHIPPED.getCode());
        // 物流不做了
        order.setAutoConfirmDay(10);
        order.setDeliveryTime(new Date());
        order.setModifiedTime(new Date());

        // 更改订单操作历史
        OrderOperateHistory operateHistory = orderOperateHistoryDao.selectOne(new QueryWrapper<OrderOperateHistory>().eq("order_id", id));
        operateHistory.setOperateTime(new Date());
        operateHistory.setOrderSatus(OrderStatusEnum.SHIPPED.getCode());
        operateHistory.setOperator(operator);

        int i = orderDao.updateById(order);
        int j = orderOperateHistoryDao.updateById(operateHistory);

        return i > 0 && j > 0;
    }

    @Override
    public String addOrder(long memberId, List<Integer> cartIds, int addressId, String note) {
        String orderSn = UUID.randomUUID().toString();
        CartToOrderVO cartToOrderVO = new CartToOrderVO(orderSn, cartIds, memberId, addressId, note);
        boolean sender = mqSender.senderNormal(JSON.toJSONString(cartToOrderVO));
        if (sender) {
            redisUtils.set(ORDER_KEY + orderSn, 0, 3600 * 2);
        }
        return sender ? orderSn : null;
    }

    @Override
    public Result getStatus(String id) {
        Object o = redisUtils.get(ORDER_KEY + id);
        if (o == null) {
            return Result.genResult(ResultEnum.FAIL);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", (int) o);
        if ((int) o == 200) {
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.select("id").eq("order_sn", id);
            Order order = orderDao.selectOne(wrapper);

            map.put("id", order.getId());
            return Result.succ(map);
        }
        return Result.succ(map);
    }

    @Override
    public List<OrderListVO> getList(long memberId, int status,int pageSize,int page) {
        List<OrderListVO> list = new ArrayList<>();
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("member_id", memberId);
        if (status != -1) {
            orderQueryWrapper.eq("status", status);
        }
        orderQueryWrapper.orderByDesc("id");

        Page<Order> orderPage = new Page<>(page,pageSize);
        Page<Order> orderPage1 = orderDao.selectPage(orderPage, orderQueryWrapper);

        List<Order> orders = orderPage1.getRecords();

        for (Order order : orders) {
            OrderListVO orderListVO = new OrderListVO(order.getId(), order.getOrderSn(), order.getTotalAmount(), order.getStatus(), null);

            QueryWrapper<OrderItem> wrapper = new QueryWrapper<>();
            wrapper.eq("order_sn", order.getOrderSn());
            List<OrderItem> orderItems = orderItemDao.selectList(wrapper);
            orderListVO.setOrderItems(orderItems);

            list.add(orderListVO);
        }

        return list;
    }

    @Override
    public OrderInfoVO getInfo(long memberId, int id) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("member_id", memberId).eq("id", id);
        Order order = orderDao.selectOne(orderQueryWrapper);

        QueryWrapper<OrderItem> wrapper = new QueryWrapper<>();
        wrapper.eq("order_sn", order.getOrderSn());
        List<OrderItem> orderItems = orderItemDao.selectList(wrapper);

        return new OrderInfoVO(order, orderItems);
    }

    @Override
    public boolean delOrder(long memberId, int id) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId).eq("id", id);
        Order order = orderDao.selectOne(wrapper);
        if (order == null) {
            return false;
        }
        return orderDao.deleteById(id) > 0;
    }

    @Override
    public boolean pay(long memberId, int orderId, int payType) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",memberId).eq("id",orderId);
        Order order = orderDao.selectOne(wrapper);
        if (order == null) {
            return false;
        }
        order.setPayType(payType);
        order.setStatus(1);
        order.setConfirmStatus(0);
        order.setPayTime(new Date());
        order.setModifiedTime(new Date());

        return orderDao.updateById(order) > 0;
    }

    @Override
    public boolean cancel(long memberId, int orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",memberId).eq("id",orderId);
        Order order = orderDao.selectOne(wrapper);
        if (order == null) {
            return false;
        }
        order.setModifiedTime(new Date());
        order.setStatus(5);
        QueryWrapper<OrderItem> orderItemQueryWrapper = new QueryWrapper<>();
        orderItemQueryWrapper.eq("order_sn",order.getOrderSn());
        List<OrderItem> orderItems = orderItemDao.selectList(orderItemQueryWrapper);
        for (int i=0;i<orderItems.size();i++) {
            OrderItem orderItem = orderItems.get(i);
            QueryWrapper<Sku> skuQueryWrapper = new QueryWrapper<Sku>();
            skuQueryWrapper.eq("id",orderItem.getSkuId());
            Sku sku = skuDao.selectOne(skuQueryWrapper);
            sku.setStock(sku.getStock()+orderItem.getSkuCount());
            skuDao.updateById(sku);
        }

        return orderDao.updateById(order) > 0;
    }

    @Override
    public boolean refund(long memberId, int orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",memberId).eq("id",orderId);
        Order order = orderDao.selectOne(wrapper);
        if (order == null) {
            return false;
        }
        order.setModifiedTime(new Date());
        order.setStatus(4);
        QueryWrapper<OrderItem> orderItemQueryWrapper = new QueryWrapper<>();
        orderItemQueryWrapper.eq("order_sn",order.getOrderSn());
        List<OrderItem> orderItems = orderItemDao.selectList(orderItemQueryWrapper);
        for (int i=0;i<orderItems.size();i++) {
            OrderItem orderItem = orderItems.get(i);
            QueryWrapper<Sku> skuQueryWrapper = new QueryWrapper<Sku>();
            skuQueryWrapper.eq("id",orderItem.getSkuId());
            Sku sku = skuDao.selectOne(skuQueryWrapper);
            sku.setStock(sku.getStock()+orderItem.getSkuCount());
            skuDao.updateById(sku);
        }

        return orderDao.updateById(order) > 0;
    }
}
