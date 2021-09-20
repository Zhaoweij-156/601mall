package com.mall.controller.user;

import com.baomidou.mybatisplus.extension.api.R;
import com.mall.annotation.UserLoginToken;
import com.mall.dto.Result;
import com.mall.dto.ResultEnum;
import com.mall.pojo.Address;
import com.mall.service.AddressService;
import com.mall.utils.JWTUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 功能描述：
 *
 * @author yhc
 * @date 2021年08月17日 10:50
 */

@RestController
@CrossOrigin
@RequestMapping("/user/address")
public class AddressController {
    @Resource
    private AddressService addressService;

    @UserLoginToken
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public Result getList(HttpServletRequest request) {
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        return Result.genResult(ResultEnum.SUCCESS, addressService.getList(memberId));
    }

    @UserLoginToken
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result delAddress(@RequestParam("id") int id, HttpServletRequest request) {
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        boolean delAddress = addressService.delAddress(memberId, id);
        return delAddress ? Result.genResult(ResultEnum.SUCCESS) : Result.genResult(ResultEnum.FAIL);
    }

    @UserLoginToken
    @RequestMapping(value = "/mod", method = RequestMethod.POST)
    public Result modAddress(@RequestBody Address address, HttpServletRequest request) {
        if (address == null) {
            return Result.genResult(ResultEnum.FAIL);
        }
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        boolean modAddress = addressService.modAddress(memberId, address);

        return modAddress ? Result.genResult(ResultEnum.SUCCESS) : Result.genResult(ResultEnum.FAIL);
    }

    @UserLoginToken
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result addAddress(@RequestBody Address address, HttpServletRequest request) {
        if (address == null) {
            return Result.genResult(ResultEnum.FAIL);
        }
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        boolean add = addressService.addAddress(memberId, address);

        return add ? Result.genResult(ResultEnum.SUCCESS) : Result.genResult(ResultEnum.FAIL);
    }

    @UserLoginToken
    @RequestMapping(value = "/setDef", method = RequestMethod.POST)
    public Result setDef(@RequestParam("id") int id, HttpServletRequest request) {
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));
        boolean setDefault = addressService.setDefault(memberId, id);

        return setDefault ? Result.genResult(ResultEnum.SUCCESS) : Result.genResult(ResultEnum.FAIL);
    }
}
