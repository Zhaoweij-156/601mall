package com.mall.vo;

import com.mall.pojo.Order;
import com.mall.pojo.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 功能描述：
 *
 * @author yhc
 * @date 2021年08月20日 16:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoVO {
    Order order;
    List<OrderItem> orderItemList;
}
