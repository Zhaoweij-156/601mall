package com.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.dao.MemberDao;
import com.mall.interceptor.WebConfig;
import com.mall.pojo.Member;
import com.mall.service.PersonalService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * 功能描述：
 *
 * @author yhc
 * @date 2021年08月18日 16:02
 */
@Service("personalService")
public class PersonalServiceImpl implements PersonalService {
    @Resource
    private MemberDao memberDao;

    @Override
    public Member getInfo(long id) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.select("username", "nickname", "mobile", "email", "avatar", "gender", "birth", "city", "job", "sign")
                .eq("id", id);

        Member member = memberDao.selectOne(wrapper);

        // 隐私处理
        if (member != null && member.getMobile() != null) {
            char[] chs = member.getMobile().toCharArray();
            for (int i = 4; i < chs.length; i++) {
                chs[i] = '*';
            }
            member.setMobile(new String(chs));
        }
        if (member != null && member.getEmail() != null) {
            char[] chs = member.getEmail().toCharArray();
            int i = 1;
            while (chs[i] != '@') {
                chs[i++] = '*';
            }
            member.setEmail(new String(chs));
        }

        return member;
    }

    @Override
    public boolean saveInfo(long memberId, Member member) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("id",memberId);
        Member realMember = memberDao.selectOne(wrapper);
        // 修改信息
        realMember.setNickname(member.getNickname());
        realMember.setMobile(member.getMobile());
        realMember.setGender(member.getGender());
        realMember.setBirth(member.getBirth());
        realMember.setCity(member.getCity());
        realMember.setSign(member.getSign());
        realMember.setJob(member.getJob());
        realMember.setModifiedTime(new Date());

        return memberDao.updateById(realMember) > 0;
    }

    @Override
    public boolean avatar(long memberId, MultipartFile file) throws IOException {

        File ava = new File("/www/wwwroot/http/img");
        String realPath = ava.getCanonicalPath();
        if(!ava.exists()){
            ava.mkdirs();
        }

        String name = file.getOriginalFilename();
        String suffix = name.substring(name.lastIndexOf("."));

        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("id",memberId);
        Member member = memberDao.selectOne(wrapper);

        String newName  = UUID.randomUUID().toString()+suffix;
        member.setAvatar("http://www.jysx.xyz/img/"+newName);

        file.transferTo(new File(realPath+"/"+newName));
        return memberDao.updateById(member) > 0;
    }
}
