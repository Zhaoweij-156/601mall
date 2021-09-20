package com.mall.service.impl;

import com.mall.service.EmailService;
import com.mall.utils.RedisUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;

/**
 * 功能描述：邮箱service
 *
 * @author yhc
 * @date 2021年08月12日 11:24
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService {
    /**
     * email 的 key 值前缀
     */
    private final String H_KEY_PRE_FIX = "email_601mall_h_key:";

    /**
     * email 的 limit key 值前缀
     */
    private final String LIMIT_KEY_PRE_FIX = "email_601mall_limit_key:";

    @Resource
    private RedisUtils redisUtils;

    @Override
    public boolean send(String userEmail) throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName("smtp.163.com");
        email.setAuthenticator(new DefaultAuthenticator("yhcnet_e@163.com", "SNAHEDWLIFTRHLCC"));
        email.setSSLOnConnect(true);
        email.setSmtpPort(465);
        email.setFrom("yhcnet_e@163.com");
        email.setSubject("601mall验证码");
        String code = "";
        for (int i = 0; i < 4; i++) {
            code += new Random().nextInt(10);
        }
        email.setMsg("欢迎使用601mall，您本次的验证码是：" + code + " ,验证码有效期 5分钟 ，请尽快输入，谢谢！（若您未发送此邮件，请忽略！）");

        email.addTo(userEmail);
        String send = email.send();
        if (send != null) {
            redisUtils.set(H_KEY_PRE_FIX + userEmail, code, 350);
            emailIncr(userEmail);
            return true;
        }
        return false;
    }

    @Override
    public boolean todayLimit(String email) {
        Object count = redisUtils.get(LIMIT_KEY_PRE_FIX + email);
        if (count == null) {
            return false;
        }
        return (int) count >= 5;
    }

    /**
     * 如果为空，则新建一个为1的值，如果不为空，自增1
     *
     * @param email
     */
    @Override
    public void emailIncr(String email) {
        // 获取过期时间
        long nowTime = System.currentTimeMillis() / 1000;
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        if (redisUtils.get(LIMIT_KEY_PRE_FIX + email) == null) {
            redisUtils.set(LIMIT_KEY_PRE_FIX + email, 1, calendar.getTimeInMillis() / 1000 - nowTime);
            return;
        }
        redisUtils.incr(LIMIT_KEY_PRE_FIX + email, 1);
        redisUtils.expire(LIMIT_KEY_PRE_FIX + email, calendar.getTimeInMillis() / 1000 - nowTime);
    }

    /**
     * 检测邮箱验证码是否正确
     *
     * @param email
     * @param code
     * @return boolean
     */
    @Override
    public boolean check(String email, String code) {
        String realCode = String.valueOf(redisUtils.get(H_KEY_PRE_FIX + email));
        if (realCode == null || !Objects.equals(realCode, code)) {
            return false;
        }
        return true;
    }

}
