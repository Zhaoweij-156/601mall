package com.mall.service.impl;

import com.mall.service.CaptchaService;
import com.mall.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 功能描述：
 *
 * @author yhc
 * @date 2021年08月11日 11:40
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
    /**
     * redis 的 key 值前缀
     */
    private final String H_KEY_PRE_FIX = "captcha_601mall_h_key:";

    @Resource
    private RedisUtils redisUtils;

    /**
     * 检测验证码是否正确，不区分大小写
     *
     * @param captchaCode
     * @param captchaId
     * @return
     */
    @Override
    public boolean captchaCheck(String captchaCode, String captchaId) {
        if (captchaCode == null || captchaId == null) {
            return false;
        }

        String realCode = (String) redisUtils.get(H_KEY_PRE_FIX + captchaId);
        if (realCode == null || realCode.length() == 0) {
            return false;
        }
        // 判断是否相同
        return Objects.equals(realCode.toLowerCase(), captchaCode.toLowerCase());
    }
}
