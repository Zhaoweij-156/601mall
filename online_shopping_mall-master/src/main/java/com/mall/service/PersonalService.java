package com.mall.service;

import com.mall.pojo.Member;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author yhc
 */
public interface PersonalService {

    /**
     * 获取用户信息
     *
     * @param id
     * @return Member
     */
    Member getInfo(long id);

    /**
     * 保存个人信息
     *
     * @param member
     * @param memberId
     * @return boolean
     */
    boolean saveInfo(long memberId,Member member);

    /**
     * 上传头像
     *
     * @param memberId
     * @param file
     * @return boolean
     */
    boolean avatar(long memberId, MultipartFile file) throws IOException;
}
