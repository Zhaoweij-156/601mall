package com.mall.vo;

import com.mall.pojo.Sku;
import com.mall.pojo.Spu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/8 15:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductReceptionPageVO extends Spu {

    private Sku sku;

}
