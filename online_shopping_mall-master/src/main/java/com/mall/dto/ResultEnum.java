package com.mall.dto;

import lombok.Getter;

/**
 * 错误码枚举
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/24 15:22
 */
@Getter
public enum ResultEnum {

    /**
     * 公用的错误码：200 - 299，400， 500
     */

    /** 加入队列成功 */
    QUEUE(0, "排队中！"),
    /** 操作成功 */
    SUCCESS(200, "成功！"),
    /** 用户未登录或登录过期 */
    NO_LOGIN(203, "用户未登录或登录过期！"),
    /** token验证异常,登录过期或者未登录 */
    TOKEN_VERIFY_ERROR(204, "token验证异常！"),
    /** 操作失败 */
    FAIL(400, "失败！"),
    /** 操作失败 */
    CAPTCHA_FAIL(403, "验证码错误！"),
    /** 服务器异常 */
    INTERNAL_SERVER_ERROR(500,  "服务器异常！"),


    /** 用户名或密码错误 */
    USERNAME_OR_PASSWORD_ERROR(501, "用户名或密码错误！"),
    /** 用户名已经存在 */
    USERNAME_IS_EXIST(502, "用户名已经存在！"),
    /** 分类不存在 */
    CATEGORY_NOT_EXIST(503, "分类不存在！"),
    /** 分类已经存在 */
    CATEGORY_IS_EXIST(503, "分类已经存在！"),
    /** spu不存在 */
    SPU_NOT_EXIST(504, "spu不存在！"),
    /** sku不存在 */
    SKU_NOT_EXIST(504, "sku不存在！"),
    ;

    private Integer resultCode;

    private String resultMsg;

    ResultEnum(Integer resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

}
