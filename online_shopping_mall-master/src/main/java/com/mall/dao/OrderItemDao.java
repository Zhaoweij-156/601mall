package com.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.pojo.OrderItem;

import java.util.List;

/**
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/8 14:10
 */
public interface OrderItemDao extends BaseMapper<OrderItem> {

    List<OrderItem> getOrderItemsByOrderId(Long orderId);

}
