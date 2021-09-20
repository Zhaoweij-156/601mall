package com.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.dto.Result;
import com.mall.vo.OrderInfoVO;
import com.mall.vo.OrderListVO;
import com.mall.vo.OrderPageVO;
import com.mall.vo.OrderVO;

import java.util.List;

/**
 * 订单service 接口
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/7 17:01
 */
public interface OrderService {

    /**
     * 分页查询订单,可根据删除状态筛选
     *
     * @param pageNo
     * @param pageSize
     * @param deleteStatus
     * @return
     */
    IPage<OrderPageVO> getAllForPage(Integer pageNo, Integer pageSize, Integer deleteStatus);

    /**
     * 根据id查询订单详情
     *
     * @param id
     * @return
     */
    OrderVO getDetailById(Long id);

    /**
     * 发货
     *
     * @param id 订单id
     * @param operator 操作人
     * @return
     */
    Boolean deliver(Long id, String operator);

    /**
     * 提交订单
     *
     * @param memberId
     * @param cartIds
     * @param addressId
     * @param note
     * @return String
     */
    String addOrder(long memberId, List<Integer> cartIds,int addressId,String note);

    /**
     * 获取用户订单状态
     *
     * @param id
     * @return Result
     */
    Result getStatus(String id);

    /**
     * 获取订单列表
     * @param memberId
     * @param status
     * @param pageSize
     * @param page
     * @return List<OrderListVO>
     */
    List<OrderListVO> getList(long memberId,int status,int pageSize,int page);

    /**
     * 获取订单详情
     * @param memberId
     * @param id
     * @return OrderInfo
     */
    OrderInfoVO getInfo(long memberId, int id);

    /**
     * 删除订单
     *
     * @param memberId
     * @param id
     * @return boolean
     */
    boolean delOrder(long memberId, int id);

    /**
     * 支付
     *
     * @param memberId
     * @param orderId
     * @param payType
     * @return  boolean
     */
    boolean pay(long memberId, int orderId, int payType);

    /**
     * 取消未支付订单
     *
     * @param memberId
     * @param orderId
     * @return boolean
     */
    boolean cancel(long memberId, int orderId);

    /**
     * 退款
     *
     * @param memberId
     * @param orderId
     * @return boolean
     */
    boolean refund(long memberId, int orderId);
}
