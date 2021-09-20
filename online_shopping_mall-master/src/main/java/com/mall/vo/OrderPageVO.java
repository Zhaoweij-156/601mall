package com.mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单VO 分页展示
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/8 11:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPageVO {

    private Long id; // 订单id
    private Long memberId; // 会员id
    private String orderSn; // 订单号
    private String memberUsername; // 用户名
    private Integer status; // 订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】
    private Integer deleteStatus; // 删除状态(软删除)【T->未删除；F->已删除】

}
