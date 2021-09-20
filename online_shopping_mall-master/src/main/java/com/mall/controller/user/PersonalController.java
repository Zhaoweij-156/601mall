package com.mall.controller.user;

import com.mall.annotation.UserLoginToken;
import com.mall.dto.Result;
import com.mall.dto.ResultEnum;
import com.mall.pojo.Member;
import com.mall.service.PersonalService;
import com.mall.utils.JWTUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * 功能描述：个人信息
 *
 * @author yhc
 * @date 2021年08月18日 15:26
 */

@RestController
@CrossOrigin
@RequestMapping("/user/personal")
public class PersonalController {

    @Resource
    private PersonalService personalService;

    @UserLoginToken
    @RequestMapping(value = "/getInfo",method = RequestMethod.GET)
    public Result getInfo(HttpServletRequest request) {
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        return Result.succ(personalService.getInfo(memberId));
    }

    @UserLoginToken
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result saveInfo(@RequestBody Member member,HttpServletRequest request) {
        if (member == null) {
            return Result.genResult(ResultEnum.FAIL);
        }
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        boolean saveInfo = personalService.saveInfo(memberId,member);

        return saveInfo ? Result.genResult(ResultEnum.SUCCESS) : Result.genResult(ResultEnum.FAIL);
    }

    @UserLoginToken
    @RequestMapping(value = "/avatar",method = RequestMethod.POST)
    public Result avatar(@RequestParam("file")MultipartFile file,HttpServletRequest request) throws IOException {
        if (file == null) {
            return Result.genResult(ResultEnum.FAIL);
        }

        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        boolean avatar = personalService.avatar(memberId, file);

        return avatar ? Result.genResult(ResultEnum.SUCCESS) : Result.genResult(ResultEnum.FAIL);
    }
}
