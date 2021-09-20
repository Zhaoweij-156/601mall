package com.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.dao.CartDao;
import com.mall.dao.SkuDao;
import com.mall.dao.SpuDao;
import com.mall.pojo.Cart;
import com.mall.pojo.Sku;
import com.mall.pojo.Spu;
import com.mall.service.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能描述：
 *
 * @author yhc
 * @date 2021年08月19日 11:07
 */
@Service("cartService")
public class CartServiceImpl implements CartService {

    @Resource
    private CartDao cartDao;

    @Resource
    private SkuDao skuDao;

    @Resource
    private SpuDao spuDao;


    @Override
    public boolean add(long memberId, long skuId, int skuCount) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",memberId).eq("sku_id",skuId);
        Cart cart = cartDao.selectOne(wrapper);

        // 新增
        if (cart == null) {
            QueryWrapper<Sku> skuWrapper = new QueryWrapper<>();
            skuWrapper.eq("id",skuId);
            Sku sku = skuDao.selectOne(skuWrapper);
            QueryWrapper<Spu> spuWrapper = new QueryWrapper<>();
            spuWrapper.eq("id",sku.getSpuId());
            Spu spu = spuDao.selectOne(spuWrapper);

            cart = new Cart(0L,memberId,spu.getId(),spu.getName(),spu.getCategoryId(),skuId,sku.getSkuName(),sku.getSkuDefaultImg(),sku.getOriginalPrice(),sku.getPresentPrice(),skuCount);
            return cartDao.insert(cart) > 0;
        }

        // 已存在，则更新数量
        cart.setSkuCount(cart.getSkuCount() + skuCount);
        return cartDao.updateById(cart) > 0;
    }

    @Override
    public boolean del(long memberId, List<Integer> cartIds) {
        int del = 0;
        for (Integer cartId : cartIds) {
            QueryWrapper<Cart> wrapper = new QueryWrapper<>();
            wrapper.eq("member_id",memberId).eq("id",cartId);
            del += cartDao.delete(wrapper);
        }

        return del > 0;
    }

    @Override
    public List<Cart> getList(long memberId) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.select("id","spu_id","spu_name","category_id","sku_id","sku_name","sku_img","sku_original_price","sku_present_price","sku_count")
                .eq("member_id",memberId);
        return cartDao.selectList(wrapper);
    }

    @Override
    public boolean edit(long memberId, long id, int skuCount) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",memberId).eq("id",id);
        Cart cart = cartDao.selectOne(wrapper);
        if (cart == null) {
            return false;
        }
        cart.setSkuCount(skuCount);
        return cartDao.updateById(cart) > 0;
    }
}
