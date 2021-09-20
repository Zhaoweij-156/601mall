package com.mall.service;

import com.mall.pojo.Address;

import java.util.List;

/**
 * @author yhc
 */
public interface AddressService {

    /**
     * 获取用户的所有地址
     *
     * @param id
     * @return List<Address>
     */
    List<Address> getList(long id);

    /**
     * 删除地址
     *
     * @param memberId
     * @param id
     * @return boolean
     */
    boolean delAddress(long memberId, int id);

    /**
     * 修改地址
     *
     * @param memberId
     * @param address
     * @return boolean
     */
    boolean modAddress(long memberId, Address address);

    /**
     * 新增地址
     *
     * @param memberId
     * @param address
     * @return boolean
     */
    boolean addAddress(long memberId, Address address);

    /**
     * 设置默认地址
     *
     * @param memberId
     * @param id
     * @return boolean
     */
    boolean setDefault(long memberId, int id);
}
