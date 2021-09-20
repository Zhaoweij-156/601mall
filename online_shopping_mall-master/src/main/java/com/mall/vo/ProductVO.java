package com.mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/1 8:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO {

    private Long spuId;
    private Long skuId;
    private String spuName;
    private String skuName;
    private Integer publishStatus;
    private String defaultImg;

}
