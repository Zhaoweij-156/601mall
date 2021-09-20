package com.mall.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.annotation.UserLoginToken;
import com.mall.dto.Result;
import com.mall.pojo.Member;
import com.mall.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/31 15:08
 */
@RestController
@RequestMapping("/admin")
public class AdminMemberController {

    @Autowired
    private MemberService memberService;

    @UserLoginToken
    @GetMapping("/allmember")
    public Result allMember(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        IPage<Member> memberForPage = memberService.getAllMemberForPage(pageNo, pageSize);
        return Result.succ(memberForPage);
    }

    @UserLoginToken
    @GetMapping("/member")
    public Result getMember(@RequestParam("id") Long id) {
        return Result.succ(memberService.getMember(id));
    }

    @UserLoginToken
    @PostMapping("/changestatus")
    public Result changeStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
        Boolean flag = memberService.changeStatus(id, status);
        return flag ? Result.succ(null) : Result.fail();
    }

}
