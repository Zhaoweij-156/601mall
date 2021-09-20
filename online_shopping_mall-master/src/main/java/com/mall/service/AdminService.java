package com.mall.service;

import com.mall.pojo.Admin;

/**
 * admin service层
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/25 16:17
 */
public interface AdminService {

    Admin login(String username, String password);

    Boolean register(Admin admin);

    Admin getByUsername(String username);

}
