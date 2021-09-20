package com.mall.controller;

import com.mall.annotation.UserLoginToken;
import com.mall.dto.Result;
import com.mall.dto.ResultEnum;
import com.mall.service.SecKillService;
import com.mall.utils.JWTUtil;
import com.mall.utils.Utils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 功能描述：秒杀控制器
 *
 * @author yhc
 * @date 2021年08月23日 8:56
 */

@RestController
@CrossOrigin
@RequestMapping("/secKill")
public class SecKillController {

    @Resource
    private Utils utils;

    @Resource
    private SecKillService secKillService;

    @UserLoginToken
    @RequestMapping(value = "/getUri")
    public Result getUri(HttpServletRequest request) {
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        if (utils.limit(memberId, request.getRequestURI(), 5, 5)) {
            return Result.fail("访问频繁，请稍后重试！");
        }

        String uri = secKillService.getUri(memberId);

        return Result.succ(uri);
    }

    @UserLoginToken
    @RequestMapping(value = "/real/{uri}")
    public Result secKill(@PathVariable String uri,
                          @RequestParam("produceId") int productId,
                          @RequestParam("addressId") int addressId,
                          HttpServletRequest request) {
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        if (utils.limit(memberId, request.getRequestURI(), 5, 5)) {
            return Result.fail("访问频繁，请稍后重试！");
        }

        if (!secKillService.check(memberId, uri)) {
            return Result.fail("请求地址非法，你重试！");
        }

        String secKill = secKillService.secKill(memberId, productId, addressId);

        return secKill != null ? Result.genResult(ResultEnum.SUCCESS,secKill) : Result.fail("库存不足！");
    }
}
