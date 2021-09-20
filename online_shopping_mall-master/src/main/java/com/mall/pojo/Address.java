package com.mall.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 地址表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address {

    @TableId(type = IdType.AUTO) // 自增ID
    // 地址 ID
    private Long id;

    // 会员ID
    private Long memberId;

    // 收货人姓名
    private String name;

    // 电话
    private String phone;

    // 邮政编码
    private String postCode;

    // 省份
    private String province;

    // 城市
    private String city;

    // 区
    private String region;

    // 详细地址
    private String detailAddress;

    // 是否是默认地址
    private Integer defaultStatus;
}
