package com.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private int code;
    private String msg;
    private Object data;

    /**
     * code 200-成功   /  400-异常
     * msg 消息说明
     * data 数据
     */


    /**
     * 构建一个成功的返回对象
     *
     * @param data 返回数据
     * @return Result
     */
    public static Result succ(Object data) {
        return genResult(ResultEnum.SUCCESS, data);
    }

    /**
     * 构建一个默认的错误返回值
     *
     * @return Result
     */
    public static Result fail() {
        return genResult(ResultEnum.FAIL, null);
    }

    /**
     * 构建一个错误的返回对象
     *
     * @param data 返回数据
     * @return Result
     */
    public static Result fail(Object data) {
        return genResult(ResultEnum.FAIL, data);
    }

    /**
     * 构建返回值
     *
     * @param resultEnum 错误码枚举
     * @return Result
     */
    public static Result genResult(ResultEnum resultEnum) {
        return new Result(resultEnum.getResultCode(), resultEnum.getResultMsg(), null);
    }

    /**
     * 构建返回值
     *
     * @param resultEnum 错误码枚举
     * @param data 返回数据
     * @return Result
     */
    public static Result genResult(ResultEnum resultEnum, Object data) {
        return new Result(resultEnum.getResultCode(), resultEnum.getResultMsg(), data);
    }

}
