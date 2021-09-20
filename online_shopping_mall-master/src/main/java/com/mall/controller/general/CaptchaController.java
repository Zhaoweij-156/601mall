package com.mall.controller.general;

import com.mall.dto.Result;
import com.mall.utils.RedisUtils;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 功能描述：图片验证码控制器
 *
 * @author yhc
 * @date 2021年08月11日 10:19
 */
@RestController
@CrossOrigin
@RequestMapping("/captcha")
public class CaptchaController {

    @Resource
    private RedisUtils redisUtils;

    /**
     * redis 的 key 值前缀
     */
    private final String H_KEY_PRE_FIX = "captcha_601mall_h_key:";

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result getCaptcha() {
        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(120, 48, 5);
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_ONLY_LOWER);
        // 把验证码储存在redis中,有效期 5 分钟
        String captchaCode = specCaptcha.text();
        String captchaId = UUID.randomUUID().toString();
        String captchaBody = specCaptcha.toBase64().replace("+", "%2B");
        redisUtils.set(H_KEY_PRE_FIX + captchaId, captchaCode, 60 * 5);
        Map<String, String> map = new HashMap<>(2);
        map.put("captchaId", captchaId);
        map.put("captchaBody", captchaBody);

        return Result.succ(map);
    }
}
