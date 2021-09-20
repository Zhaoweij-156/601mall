package com.mall.vo;

import com.mall.pojo.Order;
import com.mall.pojo.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 订单VO
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/8 12:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO extends Order {

    private List<OrderItem> orderItems;

}
