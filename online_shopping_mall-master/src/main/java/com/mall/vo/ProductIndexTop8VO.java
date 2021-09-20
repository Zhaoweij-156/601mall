package com.mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/8 18:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductIndexTop8VO {

    private Long spuId;
    private Long skuId;
    private String spuName;
    private String skuName;
    private String skuDesc;
    private Integer publishStatus;
    private String defaultImg;

}
