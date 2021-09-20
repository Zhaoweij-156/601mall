package com.mall.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.dao.*;
import com.mall.pojo.*;
import com.mall.utils.RedisUtils;
import com.mall.vo.CartToOrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 功能描述：秒杀队列接收者/消费者
 *
 * @author yhc
 */

@Service
@Slf4j
public class MQReceiver {

    private final String SECKILL_QUEUE = "SECKILL_QUEUE";
    private final String ORDER_KEY = "order_key_uniq:";

    @Resource
    private OrderDao orderDao;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private OrderItemDao orderItemDao;

    @Resource
    private SkuDao skuDao;

    @Resource
    private CartDao cartDao;

    @Resource
    private AddressDao addressDao;

    @Resource
    private MemberDao memberDao;

    @RabbitListener(queues = SECKILL_QUEUE)
    public void receiveNormal(String message) {
        log.info("接受消息：" + message);
        CartToOrderVO cartToOrderVO = JSON.parseObject(message, CartToOrderVO.class);
        String orderSn = cartToOrderVO.getOrderSn();
        // 总金额
        BigDecimal total = new BigDecimal("0");
        List<Integer> cartIds = cartToOrderVO.getCartIds();
        for (int i=0;i<cartIds.size();){
            Integer cartId = cartIds.get(i);
            QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
            cartQueryWrapper.eq("id", cartId);
            Cart cart = cartDao.selectOne(cartQueryWrapper);

            QueryWrapper<Sku> skuQueryWrapper = new QueryWrapper<>();
            skuQueryWrapper.eq("id", cart.getSkuId());
            Sku sku = skuDao.selectOne(skuQueryWrapper);
            // 库存不足
            if (sku.getStock() < cart.getSkuCount()) {
                cartIds.remove(cartId);
                continue;
            }
            OrderItem orderItem = new OrderItem(0L, -1L, orderSn, cart.getSpuId(), cart.getSpuName(), cart.getCategoryId(), cart.getSkuId(), cart.getSkuName(), cart.getSkuImg(), cart.getSkuOriginalPrice(), cart.getSkuPresentPrice(), cart.getSkuCount());
            orderItemDao.insert(orderItem);
            sku.setStock(sku.getStock() - cart.getSkuCount());
            skuDao.updateById(sku);
            cartDao.deleteById(cartId);
            total = total.add(cart.getSkuPresentPrice().multiply(new BigDecimal(cart.getSkuCount())));
            i++;
        }

        if (cartIds.size() > 0) {
            QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
            memberQueryWrapper.eq("id", cartToOrderVO.getMemberId());
            Member member = memberDao.selectOne(memberQueryWrapper);

            QueryWrapper<Address> addressQueryWrapper = new QueryWrapper<>();
            addressQueryWrapper.eq("id", cartToOrderVO.getAddressId());
            Address address = addressDao.selectOne(addressQueryWrapper);

            Date date = new Date();
            Order order = new Order(0L, cartToOrderVO.getMemberId(), orderSn, member.getUsername(), total, total, new BigDecimal("0"), 0, 0, null, null, -1, address.getName(), address.getPhone(),
                    address.getPostCode(), address.getProvince(), address.getCity(), address.getRegion(), address.getDetailAddress(), cartToOrderVO.getNote(), 0, 0, null, null, null, date, date);
            int insert = orderDao.insert(order);
            if (insert > 0) {
                redisUtils.set(ORDER_KEY+orderSn,200,3600*2);
            }
        } else {
            redisUtils.set(ORDER_KEY+orderSn,400,3600*2);
        }

    }
}
