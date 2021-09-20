package com.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.pojo.Sku;
import com.mall.pojo.Spu;
import com.mall.vo.ProductIndexTop8VO;
import com.mall.vo.ProductReceptionPageVO;
import com.mall.vo.ProductVO;
import com.mall.vo.SkuVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品service 实现
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/1 9:24
 */
public interface ProductService {

    IPage<ProductReceptionPageVO> getAllProductReceptionForPage(Integer pageNo, Integer pageSize, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, String sortord);

    List<ProductIndexTop8VO> getIndexTop8();

    List<SkuVO> getDetailBySpuId(Long spuId);

    /**
     * 获取所有商品 分页
     * 可传分类id来指定
     * 后台使用
     *
     * @param pageNo 第几页
     * @param pageSize 页面大小
     * @param categoryId Long
     * @return IPage<ProductVO>
     */
    IPage<ProductVO> getAllProductForPage(Integer pageNo, Integer pageSize, Long categoryId);

    /**
     * 根据skuId获取sku
     *
     * @param skuId Long
     * @return SkuVO
     */
    SkuVO getSkuVOBySkuId(Long skuId);

    /**
     * 根据id获取sku
     *
     * @param id Long
     * @return Sku
     */
    Sku getSkuById(Long id);

    /**
     * 新增sku
     *
     * @param sku Sku
     * @return Boolean
     */
    Boolean addSku(Sku sku);

    /**
     * 修改sku
     *
     * @param sku Sku
     * @return Boolean
     */
    Boolean updateSku(Sku sku);

    /**
     * 根据id获取spu
     *
     * @param id Long
     * @return Spu
     */
    Spu getSpuById(Long id);

    /**
     * 新增spu
     *
     * @param spu Spu
     * @return Boolean
     */
    Boolean addSpu(Spu spu);

    /**
     * 修改spu
     *
     * @param spu Spu
     * @return Boolean
     */
    Boolean updateSpu(Spu spu);

}
