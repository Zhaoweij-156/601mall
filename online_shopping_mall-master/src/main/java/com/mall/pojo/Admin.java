package com.mall.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 后台管理员类
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/18 0:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Admin {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 用户名
    private String username;

    // 密码
    private String password;

    // 盐
    private String salt;

    // 邮箱
    private String email;

    //手机
    private String mobile;

    // 状态 0：禁用 1：正常
    private Integer status;

    // 创建者ID
    private Long createUserId;

    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 更改时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;

}
