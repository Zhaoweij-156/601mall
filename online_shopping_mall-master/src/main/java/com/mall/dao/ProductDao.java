package com.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.vo.ProductIndexTop8VO;
import com.mall.vo.ProductReceptionPageVO;
import com.mall.vo.ProductVO;
import com.mall.vo.SkuVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/1 8:45
 */
public interface ProductDao extends BaseMapper<ProductVO> {
    /**
     * 查询所有商品 分页
     *
     * @param page IPage<ProductVO>
     * @param categoryId Long
     * @return IPage<ProductVO>
     */
    IPage<ProductVO> getAllProductForPage(IPage<ProductVO> page, @Param("categoryId") Long categoryId);

    SkuVO getSkuBySkuId(@Param("skuId") Long skuId);

    IPage<ProductReceptionPageVO> getAllProductReceptionForPage(IPage<ProductReceptionPageVO> page, @Param("categoryId") Long categoryId,
                                                                @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice,
                                                                @Param("sortord") String sortord);

    List<ProductIndexTop8VO> getIndexTop8();

    List<SkuVO> getSkuBySpuId(@Param("spuId") Long spuId);

}
