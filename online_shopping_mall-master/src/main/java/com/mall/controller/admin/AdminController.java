package com.mall.controller.admin;

import com.mall.annotation.UserLoginToken;
import com.mall.dto.Result;
import com.mall.dto.ResultEnum;
import com.mall.exception.NoLoginException;
import com.mall.pojo.Admin;
import com.mall.service.AdminService;
import com.mall.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台admin controller
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/25 15:41
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Admin admin = adminService.login(username, password);
        if (null == admin) {
            return Result.genResult(ResultEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        // 生成token
        String token = JWTUtil.generateToken(admin.getUsername(), JWTUtil.ISSUER, admin.getUsername());
        // 构造返回值对象
        Map<String, Object> map = new HashMap<>();
        map.put("admin", admin);
        map.put("token", token);
        return Result.succ(map);
    }

    @PostMapping("/register")
    public Result register(Admin admin) {
        Admin byUsername = adminService.getByUsername(admin.getUsername());
        if (null != byUsername) {
            return Result.genResult(ResultEnum.USERNAME_IS_EXIST);
        }
        Boolean flag = adminService.register(admin);
        if (!flag) {
            return Result.fail();
        }
        return Result.succ("注册成功");
    }

    @UserLoginToken
    @GetMapping("/testtoken")
    public Result testToken(String username, HttpServletRequest request) {
        if (!JWTUtil.verifyId(username, request)) {
            throw new NoLoginException("权限不足！");
        }
        return Result.succ("testToken");
    }

}
