package com.mall.vo;

import com.mall.pojo.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/31 15:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO extends Member {

    // 累计消费金额
    private BigDecimal consumeAmount;

    // 累计优惠金额
    private BigDecimal couponAmount;

    // 订单数量
    private Integer orderCount;

    // 退货数量
    private Integer returnOrderCount;

    // 登录次数
    private Integer loginCount;

}
