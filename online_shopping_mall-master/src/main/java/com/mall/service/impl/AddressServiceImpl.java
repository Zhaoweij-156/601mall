package com.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.dao.AddressDao;
import com.mall.pojo.Address;
import com.mall.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能描述：
 *
 * @author yhc
 * @date 2021年08月17日 11:00
 */
@Service("addressService")
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressDao addressDao;

    @Override
    public List<Address> getList(long id) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", id);
        return addressDao.selectList(wrapper);
    }

    @Override
    public boolean delAddress(long memberId, int id) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id).eq("member_id", memberId);
        Address address = addressDao.selectOne(wrapper);
        if (address == null) {
            return false;
        }
        int del = addressDao.deleteById(id);
        return del > 0;
    }

    @Override
    public boolean modAddress(long memberId, Address address) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("id", address.getId()).eq("member_id", memberId);
        Address address2 = addressDao.selectOne(wrapper);
        if (address2 == null) {
            return false;
        }
        address.setMemberId(memberId);
        // 判断是否设置为默认地址
        if (address.getDefaultStatus() == 1) {
            wrapper = new QueryWrapper<>();
            wrapper.eq("member_id",memberId).eq("default_status",1);
            Address defaultAddress = addressDao.selectOne(wrapper);
            if (defaultAddress != null && !defaultAddress.getId().equals(address.getId())) {
                defaultAddress.setDefaultStatus(0);
                addressDao.updateById(defaultAddress);
            }
        }
        int updateById = addressDao.updateById(address);
        return updateById > 0;
    }

    @Override
    public boolean addAddress(long memberId, Address address) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId);
        List<Address> addresses = addressDao.selectList(wrapper);
        if (addresses.size() >= 10) {
            return false;
        }
        address.setMemberId(memberId);
        address.setId(0L);
        address.setDefaultStatus(0);
        // 如果当前没有默认地址，设新增的为默认地址
        wrapper.eq("default_status",1);
        Address defaultAddress = addressDao.selectOne(wrapper);
        if (defaultAddress == null) {
            address.setDefaultStatus(1);
        }
        int insert = addressDao.insert(address);

        return insert > 0;
    }

    @Override
    public boolean setDefault(long memberId, int id) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId).eq("id",id);
        Address address = addressDao.selectOne(wrapper);
        // 判断当前的地址是否为用户的地址
        if (address == null) {
            return false;
        }
        // 找出用户的默认地址并置为非默认
        wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId).eq("default_status",1);
        Address defaultAddress = addressDao.selectOne(wrapper);
        if (defaultAddress != null) {
            defaultAddress.setDefaultStatus(0);
            addressDao.updateById(defaultAddress);
        }

        // 设置当前地址为默认地址
        wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        Address nowAddress = addressDao.selectOne(wrapper);
        nowAddress.setDefaultStatus(1);
        int i = addressDao.updateById(nowAddress);
        return i > 0;
    }
}
