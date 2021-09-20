package com.mall.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(indexName = "sku", shards = 3, replicas = 1)
public class Sku {

    @TableId(type = IdType.AUTO)
    @Id
    private Long id; // 自增id

    @Field(type = FieldType.Auto, name = "spu_id")
    private Long spuId; // 商品id

    @Field(type = FieldType.Text, name = "sku_name")
    private String 	skuName; // sku名称（土豪金128G苹果6）

    @Field(type = FieldType.Text, name = "sku_desc")
    private String skuDesc; // sku介绍描述（土豪金128G苹果6有多牛逼巴拉巴拉）

    @Field(type = FieldType.Auto, name = "category_id")
    private Long categoryId; // 所属分类id

    @Field(type = FieldType.Keyword, name = "sku_default_img")
    private String skuDefaultImg; // 默认图片（冗余字段）

    @Field(type = FieldType.Scaled_Float, name = "original_price", scalingFactor = 100)
    private BigDecimal originalPrice; // 原价

    @Field(type = FieldType.Scaled_Float, name = "present_price", scalingFactor = 100)
    private BigDecimal presentPrice; // 现价

    @Field(type = FieldType.Auto, name = "sale_count")
    private Integer saleCount; // 销量

    @Field(type = FieldType.Auto, name = "publish_status")
    private Integer publishStatus	; // 上架状态[0-下架，1-上架]

    @Field(type = FieldType.Auto, name = "stock")
    private Integer stock; // 库存
}
