package com.mall.service;

/**
 * @author yhc
 */
public interface CaptchaService {
    boolean captchaCheck(String captchaCode, String captchaId);
}
