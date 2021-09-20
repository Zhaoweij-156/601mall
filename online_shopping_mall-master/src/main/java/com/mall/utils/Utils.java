package com.mall.utils;

import com.mall.service.CaptchaService;
import com.mall.service.EmailService;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

/**
 * 常用工具类
 *
 * @author yhc
 */
@Component
public class Utils {

    @Resource
    private CaptchaService captchaService;

    @Resource
    private EmailService emailService;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 传入一个bytes类型的数据返回MD5加密后的字符串，用于密码转换
     *
     * @param bytes
     * @return
     */
    public static String md5(byte[] bytes) {
        return DigestUtils.md5DigestAsHex(bytes);
    }

    public boolean checkCatch(String captchaCode, String captchaId) {
        return captchaService.captchaCheck(captchaCode, captchaId);
    }

    /**
     * 验证邮箱验证码
     *
     * @param email
     * @param emailCode
     * @return boolean
     */
    public boolean checkEmail(String email, String emailCode) {
        return emailService.check(email, emailCode);
    }

    /**
     * 接口限流
     *
     * @param memberId
     * @param uri
     * @return boolean 达到次数返回true
     */
    public boolean limit(long memberId,String uri,int second,int max) {
        String key = "uri_limit:" + uri + "_" + memberId;
        Object num = redisUtils.get(key);
        if (num == null) {
            redisUtils.set(key,1,second);
            return false;
        }
        if ((int)num >= max) {
            return true;
        }

        redisUtils.incr(key,1);
        return false;
    }
}
