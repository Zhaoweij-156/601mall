package com.mall.service;

import com.mall.pojo.Cart;

import java.util.List;

/**
 * @author yhc
 */
public interface CartService {
    /**
     * 添加购物车
     *
     * @param memberId
     * @param skuId
     * @param skuCount
     * @return boolean
     */
    boolean add(long memberId, long skuId, int skuCount);

    /**
     * 删除购物车
     *
     * @param memberId
     * @param cartIds
     * @return boolean
     */
    boolean del(long memberId, List<Integer> cartIds);

    /**
     * 获取购物车列表
     *
     * @param memberId
     * @return List<Cart>
     */
    List<Cart> getList(long memberId);

    /**
     * 修改购物车单数量
     *
     * @param memberId
     * @param id
     * @param skuCount
     * @return boolean
     */
    boolean edit(long memberId, long id, int skuCount);
}
