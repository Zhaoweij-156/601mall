package com.mall.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 会员统计信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberStatisticsInfo {

    @TableId(type = IdType.AUTO) // 自增ID
    // 会员信息ID
    private Long id;

    // 会员ID
    private Long memberId;

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
