package com.mall.pojo;

import lombok.Getter;

/**
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/8 14:28
 */
@Getter
public enum OrderStatusEnum {

    PENDING(0, "待付款"),
    TO_BE_SHIPPED(1, "待发货"),
    SHIPPED(2, "已发货"),
    COMPLETE(3, "已完成"),
    CLOSED(4, "已关闭"),
    INVALID(5, "无效订单")
    ;

    private Integer code;
    private String desc;

    private OrderStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
