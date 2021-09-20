package com.mall.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItem {

    @TableId(type = IdType.AUTO) // 自增id
    private Long id; // 订单项目id
    private Long orderId; // 订单id
    private String orderSn; // 订单编号
    private Long spuId; // 商品id
    private String spuName; // 商品名字
    private Long categoryId; // 商品分类id
    private Long skuId; // 商品sku编号
    private String skuName; // 商品sku名字
    private String skuImg; // 商品sku图片
    private BigDecimal skuOriginalPrice; // 原价
    private BigDecimal skuPresentPrice; // 现价
    private Integer skuCount; // 商品购买的数量

}
