package com.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.pojo.Member;
import com.mall.vo.MemberVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/31 15:13
 */
public interface MemberService {

    /**
     * 查询所有用户，分页
     *
     * @param pageNo   当前第几页
     * @param pageSize 当前页大小
     * @return IPage<Member>
     */
    IPage<Member> getAllMemberForPage(Integer pageNo, Integer pageSize);

    /**
     * 根据id查询用户的信息
     *
     * @param id Long
     * @return MemberVO
     */
    MemberVO getMember(Long id);

    /**
     * 根据id冻结用户
     *
     * @param id     Long
     * @param status Integer
     * @return Boolean
     */
    Boolean changeStatus(Long id, Integer status);

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return String：：token
     */
    String login(String username, String password);

    /**
     * 用户注册
     *
     * @param username
     * @param password
     * @param email
     * @return boolean
     */
    boolean registered(String username, String password, String email);

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @param request
     * @return boolean
     */
    boolean rePassword(String oldPassword, String newPassword, HttpServletRequest request);

    /**
     * 重置密码
     *
     * @param email
     * @param password
     * @return boolean
     */
    boolean resetpwd(String email, String password);
}
