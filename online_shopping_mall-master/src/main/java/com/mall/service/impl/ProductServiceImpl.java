package com.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.dao.ProductDao;
import com.mall.dao.SkuDao;
import com.mall.dao.SpuDao;
import com.mall.elasticsearch.service.ESSkuService;
import com.mall.elasticsearch.service.ESSpuService;
import com.mall.pojo.Sku;
import com.mall.pojo.Spu;
import com.mall.service.ProductService;
import com.mall.vo.ProductIndexTop8VO;
import com.mall.vo.ProductReceptionPageVO;
import com.mall.vo.ProductVO;
import com.mall.vo.SkuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品service 实现
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/1 9:24
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductDao productDao;
    @Resource
    private SpuDao spuDao;
    @Resource
    private SkuDao skuDao;

    @Autowired
    private ESSpuService esSpuService;
    @Autowired
    private ESSkuService esSkuService;

    @Override
    public IPage<ProductReceptionPageVO> getAllProductReceptionForPage(Integer pageNo, Integer pageSize, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, String sortord) {

        IPage<ProductReceptionPageVO> page = new Page<>(pageNo, pageSize);
        return null;
    }

    @Override
    public List<ProductIndexTop8VO> getIndexTop8() {
        return productDao.getIndexTop8();
    }

    @Override
    public List<SkuVO> getDetailBySpuId(Long spuId) {

        List<SkuVO> skuVOS = productDao.getSkuBySpuId(spuId);
        return skuVOS;
    }

    @Override
    public IPage<ProductVO> getAllProductForPage(Integer pageNo, Integer pageSize, Long categoryId) {
        IPage<ProductVO> page = new Page<>(pageNo, pageSize);
        return productDao.getAllProductForPage(page, categoryId);
    }

    @Override
    public SkuVO getSkuVOBySkuId(Long skuId) {
        return productDao.getSkuBySkuId(skuId);
    }

    @Override
    public Sku getSkuById(Long id) {
        return skuDao.selectById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addSku(Sku sku) {
        int i = skuDao.insert(sku);
        // 添加进es
        esSkuService.saveSku(sku);
        return i > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateSku(Sku sku) {
        Sku selectById = skuDao.selectById(sku.getId());
        if (null == selectById) {
            return Boolean.FALSE;
        }
        int i = skuDao.updateById(sku);
        Boolean flag = esSkuService.update(sku);
        return flag && i > 0;
    }

    @Override
    public Spu getSpuById(Long id) {
        return spuDao.selectById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addSpu(Spu spu) {
        // 设置创建时间和修改时间
        spu.setCreateTime(new Date());
        spu.setModifiedTime(new Date());
        // 插入到mysql
        int i = spuDao.insert(spu);
        // 添加进es，方法比较笨，应该用增量工具来操作。。
        esSpuService.saveSpu(spu);
        return i > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateSpu(Spu spu) {
        Spu selectById = spuDao.selectById(spu.getId());
        if (null == selectById) {
            return Boolean.FALSE;
        }
        spu.setCreateTime(selectById.getCreateTime());
        spu.setModifiedTime(new Date());
        int i = spuDao.updateById(spu);
        Boolean flag = esSpuService.update(spu);
        return flag && i > 0;
    }

}
