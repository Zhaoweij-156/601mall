package com.mall.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mall.pojo.Sku;
import com.mall.pojo.SkuImages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/1 10:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SkuVO extends Sku {

    private Long id; // 自增id
    private Long spuId; // 商品id
    private String 	skuName; // sku名称（土豪金128G苹果6）
    private String skuDesc; // sku介绍描述（土豪金128G苹果6有多牛逼巴拉巴拉）
    private Long categoryId; // 所属分类id
    private String skuDefaultImg; // 默认图片（冗余字段）
    private BigDecimal originalPrice; // 原价
    private BigDecimal presentPrice; // 现价
    private Integer saleCount; // 销量
    private Integer publishStatus	; // 上架状态[0-下架，1-上架]
    private Integer stock; // 库存

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime; // 创建时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime; // 修改时间

    private List<SkuImages> skuImages;



}
