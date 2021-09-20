package com.mall.vo;

import com.mall.pojo.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 功能描述：
 *
 * @author yhc
 * @date 2021年08月20日 16:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListVO {
    long id;
    String orderSn;
    BigDecimal payAmount;
    Integer status;
    List<OrderItem> orderItems;
}
