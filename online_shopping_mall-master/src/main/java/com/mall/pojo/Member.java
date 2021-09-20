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
 * 会员类
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/18 0:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 用户名
    private String username;

    // 密码
    private String password;

    // 盐
    private String salt;

    // 昵称
    private String nickname;

    // 手机号码
    private String mobile;

    // 邮箱
    private String email;

    // 头像
    private String avatar;

    // 性别
    private String gender;

    // 生日
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date birth;

    // 所在城市
    private String city;

    // 职业
    private String job;

    // 个性签名
    private String sign;

    // 用户状态：1—正常；0—冻结
    private Integer status;

    // 注册时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    // 更改时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date modifiedTime;

}
