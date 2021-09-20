package com.mall.controller.general;

import com.mall.dto.Result;
import com.mall.dto.ResultEnum;
import com.mall.service.CaptchaService;
import com.mall.service.EmailService;
import org.apache.commons.mail.EmailException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 功能描述：发送邮箱验证码
 *
 * @author yhc
 * @date 2021年08月11日 12:50
 */
@RestController
@CrossOrigin
@RequestMapping("/email")
public class EmailController {
    @Resource
    private CaptchaService captchaService;

    @Resource
    private EmailService emailService;

    /**
     * 功能描述：邮箱发送控制器
     *
     * @param captchaId
     * @param captchaCode
     * @param email
     * @return
     * @throws EmailException
     */

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public Result send(@RequestParam(value = "captchaId") String captchaId,
                       @RequestParam(value = "captchaCode") String captchaCode,
                       @RequestParam(value = "email") String email) throws EmailException {
        if (!captchaService.captchaCheck(captchaCode, captchaId)) {
            return Result.genResult(ResultEnum.CAPTCHA_FAIL);
        }
        if (emailService.todayLimit(email)) {
            return Result.fail("当天发送次数已达上限，请第二天重试！");
        }
        if (emailService.send(email)) {
            return Result.succ("邮箱验证码发送成功，请注意查收！");
        }
        return Result.fail("发送失败，请重试！");
    }
}