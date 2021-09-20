package com.mall.controller;

import com.mall.annotation.UserLoginToken;
import com.mall.dto.Result;
import com.mall.dto.ResultEnum;
import com.mall.service.CartService;
import com.mall.utils.JWTUtil;
import com.mall.utils.Utils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * 功能描述：
 *
 * @author yhc
 * @date 2021年08月19日 10:13
 */

@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @Resource
    private Utils utils;

    @UserLoginToken
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result addCart(@RequestParam("skuId") long skuId,
                          @RequestParam("skuCount") int skuCount,
                          HttpServletRequest request) {
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        boolean add = cartService.add(memberId, skuId, skuCount);

        return add ? Result.genResult(ResultEnum.SUCCESS) : Result.genResult(ResultEnum.FAIL);
    }

    @UserLoginToken
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public Result delCart(@RequestParam("cartIds") List<Integer> cartIds,HttpServletRequest request) {
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        boolean del = cartService.del(memberId, cartIds);

        return del ? Result.genResult(ResultEnum.SUCCESS) : Result.genResult(ResultEnum.FAIL);
    }

    @UserLoginToken
    @RequestMapping(value = "/getList",method = RequestMethod.GET)
    public Result getList(HttpServletRequest request) {
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        return Result.succ(cartService.getList(memberId));
    }

    @UserLoginToken
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public Result editNum(@RequestParam("id")long id,
                          @RequestParam("skuCount")int skuCount,
                          HttpServletRequest request) {
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        // 接口限流
         if (utils.limit(memberId,"/cart/edit",5,5)) {
             return Result.fail("修改过于频繁,请稍后重试！");
         }

        return cartService.edit(memberId, id, skuCount) ? Result.genResult(ResultEnum.SUCCESS) : Result.genResult(ResultEnum.FAIL);
    }
}