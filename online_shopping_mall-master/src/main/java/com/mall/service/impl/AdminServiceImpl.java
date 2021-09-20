package com.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.dao.AdminDao;
import com.mall.pojo.Admin;
import com.mall.service.AdminService;
import com.mall.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * admin service 实现层
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/25 16:21
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {

    private static final String DEFAULT_RETURN_PWD = "******";

    @Resource
    private AdminDao adminDao;

    @Override
    public Admin login(String username, String password) {
        // 构造查询对象
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        Admin admin = adminDao.selectOne(wrapper);
        if (null == admin) {
            return null;
        }
        // DB中的密码
        String dbPwd = admin.getPassword();
        // 盐
        String salt = admin.getSalt();
        String saltMD5 = MD5Util.getSaltMD5(password, salt);
        if (!saltMD5.equals(dbPwd)) {
            return null;
        }
        // 重新设密码
        admin.setPassword(DEFAULT_RETURN_PWD);
        return admin;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean register(Admin admin) {
        // 查询用户是否存在
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", admin.getUsername());
        Admin admin1 = adminDao.selectOne(wrapper);
        if (null != admin1) {
            return Boolean.FALSE;
        }

        // 盐,用uuid,32位
        String salt = UUID.randomUUID().toString().replace("-", "");
        // 构造进入数据库的密码
        String dbPwd = MD5Util.getSaltMD5(admin.getPassword(), salt);

        // 重新设置值
        admin.setPassword(dbPwd);
        admin.setSalt(salt);
        admin.setCreateTime(new Date());
        admin.setModifiedTime(new Date());

        adminDao.insert(admin);

        return Boolean.TRUE;
    }

    @Override
    public Admin getByUsername(String username) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        Admin admin = adminDao.selectOne(wrapper);
        return admin;
    }
}
