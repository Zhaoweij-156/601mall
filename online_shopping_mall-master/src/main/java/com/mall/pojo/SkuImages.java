package com.mall.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SkuImages {

    @TableId(type = IdType.AUTO)
    private Long id; // 自增id
    private Long skuId; // 商品id
    private String imgUrl; // 图片地址
    private Integer defaultImg; // 是否是默认图片[F-不是,T-是]
}
