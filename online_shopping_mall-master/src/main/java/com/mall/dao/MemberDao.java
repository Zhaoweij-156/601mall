package com.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.pojo.Member;
import com.mall.vo.MemberVO;
import org.apache.ibatis.annotations.Param;

/**
 * 用户 持久层
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/31 15:11
 */
public interface MemberDao extends BaseMapper<Member> {
    /**
     * 根据id查询用户
     *
     * @param id Long
     * @return MemberVO
     */
    MemberVO getMemberById(Long id);

    /**
     * 改变用户状态
     *
     * @param id Long
     * @param status Integer
     * @return int
     */
    int changeStatus(@Param("id") Long id, @Param("status") Integer status);
}
