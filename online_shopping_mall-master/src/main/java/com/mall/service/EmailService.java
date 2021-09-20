package com.mall.service;

import org.apache.commons.mail.EmailException;

/**
 * @author Administrator
 */
public interface EmailService {
    /**
     * 发送邮箱验证码
     * @param email
     * @return boolean
     */
    boolean send(String email) throws EmailException;

    /**
     * 获取该邮箱当天发送邮件是否达到上限
     * @param email
     * @return true 已达到5次
     */
    boolean todayLimit(String email);

    /**
     * 邮箱发送次数 + 1
     * @param email
     * @return boolean
     */
    void emailIncr(String email);

    /**
     * 检测邮箱验证码是否正确
     *
     * @param email
     * @param code
     * @return boolean
     */
    boolean check(String email,String code);
}
