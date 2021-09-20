package com.mall.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 购物车类
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/18 0:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cart {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 会员id
    private Long memberId;

    // spu_id
    private Long spuId;

    // spu_name
    private String spuName;

    // 商品分类id
    private Long categoryId;

    // 商品sku编号
    private Long skuId;

    // 商品sku名字
    private String skuName;

    // 商品sku图片
    private String skuImg;

    // 原价
    private BigDecimal skuOriginalPrice;

    // 现价
    private BigDecimal skuPresentPrice;

    // 商品购买的数量
    private Integer skuCount;


}
