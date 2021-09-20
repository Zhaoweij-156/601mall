package com.mall.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * MD5 加密工具类
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/25 15:59
 */
@Slf4j
public class MD5Util {

    private static final String HASH_ALGORITHM_NAME = "MD5";

    /**
     * 盐值 MD5 加密
     *
     * @param pwd  原始密码
     * @param salt 盐
     * @return String 加密后的密码， 32 位
     */
    public static String getSaltMD5(String pwd, String salt) {
        try {
            byte[] bytes = salt.getBytes(StandardCharsets.UTF_8);
            MessageDigest m = MessageDigest.getInstance(HASH_ALGORITHM_NAME);
            m.update(bytes);
            m.update(pwd.getBytes(StandardCharsets.UTF_8));
            byte[] s = m.digest();
            StringBuilder result = new StringBuilder();
            for (byte b : s) {
                result.append(Integer.toHexString((0x000000ff & b) | 0xffffff00)
                        .substring(6));
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5盐值加密出现异常", e);
            e.printStackTrace();
        }
        return pwd;
    }

    public static void main(String[] args) {
//        System.out.println(getSaltMD5("123456", "123456"));
        String s = UUID.randomUUID().toString().replace("-", "");
        System.out.println(s + " | " + s.length());
    }

}
