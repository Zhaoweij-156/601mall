package com.mall.controller.user;

import com.mall.annotation.UserLoginToken;
import com.mall.dto.Result;
import com.mall.dto.ResultEnum;
import com.mall.service.MemberService;
import com.mall.utils.Utils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 功能描述：用户登录控制器
 *
 * @author yhc
 * @date 2021年08月12日 14:13
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class MemberController {
    @Resource
    private Utils utils;

    @Resource
    private MemberService memberService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("captchaCode") String captchaCode,
                        @RequestParam("captchaId") String captchaId) {

        if (!utils.checkCatch(captchaCode, captchaId)) {
            return Result.genResult(ResultEnum.CAPTCHA_FAIL);
        }

        String token = memberService.login(username, password);

        if (token == null || token.isEmpty()) {
            return Result.genResult(ResultEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        return Result.succ(token);
    }

    @RequestMapping(value = "/registered", method = RequestMethod.POST)
    public Result registered(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("email") String email,
                             @RequestParam("emailCode") String emailCode) {
        if (!utils.checkEmail(email, emailCode)) {
            return Result.genResult(ResultEnum.CAPTCHA_FAIL);
        }

        if (!memberService.registered(username, password, email)) {
            return Result.genResult(ResultEnum.USERNAME_IS_EXIST);
        }

        return Result.genResult(ResultEnum.SUCCESS);
    }

    @UserLoginToken
    @RequestMapping(value = "/repassword", method = RequestMethod.POST)
    public Result rePassword(@RequestParam("oldPassword") String oldPassword,
                             @RequestParam("newPassword") String newPassword,
                             HttpServletRequest request) {
        boolean rePassword = memberService.rePassword(oldPassword, newPassword, request);
        return rePassword ? Result.genResult(ResultEnum.SUCCESS) : Result.fail("修改失败，请检查旧密码是否正确正确！");
    }

    @RequestMapping(value = "/resetpwd", method = RequestMethod.POST)
    public Result resetpwd(@RequestParam("email") String email,
                           @RequestParam("emailCode") String emailCode,
                           @RequestParam("password") String password) {
        if (!utils.checkEmail(email, emailCode)) {
            return Result.genResult(ResultEnum.CAPTCHA_FAIL);
        }
        boolean resetpwd = memberService.resetpwd(email, password);

        return resetpwd ? Result.genResult(ResultEnum.SUCCESS) : Result.genResult(ResultEnum.FAIL);
    }
}
