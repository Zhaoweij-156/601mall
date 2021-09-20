package com.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.dao.MemberDao;
import com.mall.pojo.Member;
import com.mall.service.MemberService;
import com.mall.utils.JWTUtil;
import com.mall.utils.MD5Util;
import com.mall.vo.MemberVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/31 15:16
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberDao memberDao;

    /**
     * @see com.mall.service.MemberService#getAllMemberForPage(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public IPage<Member> getAllMemberForPage(Integer pageNo, Integer pageSize) {
        IPage<Member> memberPage = new Page<>(pageNo, pageSize);
        memberPage = memberDao.selectPage(memberPage, null);

        return memberPage;
    }

    @Override
    public MemberVO getMember(Long id) {
        return memberDao.getMemberById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean changeStatus(Long id, Integer status) {
        int i = memberDao.changeStatus(id, status);
        return i > 0;
    }

    @Override
    public String login(String username, String password) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.select("id", "password", "salt", "email", "status").eq("username", username).or().eq("email", username);
        Member member = memberDao.selectOne(wrapper);

        if (member == null || !member.getPassword().equals(MD5Util.getSaltMD5(password, member.getSalt())) || member.getStatus() == 0) {
            return null;
        }
        // return token
        return JWTUtil.generateToken(String.valueOf(member.getId()), "admin", member.getEmail());
    }

    @Override
    public boolean registered(String username, String password, String email) {
        // 查当前是否已存在用户
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.select("id").eq("username", username)
                .or().eq("email", username)
                .or().eq("username", email)
                .or().eq("email", email);
        Member member = memberDao.selectOne(wrapper);
        if (member != null) {
            return false;
        }

        String salt = UUID.randomUUID().toString().replace("-", "");
        Date date = new Date();
        member = new Member(0L, username, MD5Util.getSaltMD5(password, salt), salt, null, null,
                email, null, null, null, null, null, null, 1, date, date);
        member.setAvatar("http://jysx.xyz/img/imge8b49373-c983-421d-b686-1845ddf8a6ad.jpg");
        int insert = memberDao.insert(member);
        return insert > 0;
    }

    @Override
    public boolean rePassword(String oldPassword, String newPassword, HttpServletRequest request) {
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("id", memberId);
        Member member = memberDao.selectOne(wrapper);
        if (member == null || !Objects.equals(member.getPassword(), MD5Util.getSaltMD5(oldPassword, member.getSalt()))) {
            return false;
        }
        member.setModifiedTime(new Date());
        member.setPassword(MD5Util.getSaltMD5(newPassword, member.getSalt()));
        return memberDao.updateById(member) > 0;
    }

    @Override
    public boolean resetpwd(String email, String password) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        Member member = memberDao.selectOne(wrapper);
        member.setPassword(MD5Util.getSaltMD5(password,member.getSalt()));
        member.setModifiedTime(new Date());
        return memberDao.updateById(member) > 0;
    }

}
