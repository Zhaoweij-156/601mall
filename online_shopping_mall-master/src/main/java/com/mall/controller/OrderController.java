package com.mall.controller;

import com.mall.annotation.UserLoginToken;
import com.mall.dto.Result;
import com.mall.dto.ResultEnum;
import com.mall.service.OrderService;
import com.mall.utils.JWTUtil;
import com.mall.vo.OrderInfoVO;
import com.mall.vo.OrderListVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * 功能描述：订单控制器，用mq消息队列！！！
 *
 * @author yhc
 * @date 2021年08月19日 17:04
 */

@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @UserLoginToken
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result addOrder(@RequestParam("cartIds") List<Integer> cartIds,
                           @RequestParam("addressId") int addressId,
                           @RequestParam("note") String note,
                           HttpServletRequest request) {

        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        String addOrder = orderService.addOrder(memberId, cartIds, addressId, note);

        return addOrder != null ? Result.genResult(ResultEnum.SUCCESS, addOrder) : Result.genResult(ResultEnum.FAIL);
    }

    @UserLoginToken
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public Result getStatus(@RequestParam("id") String id) {
        return orderService.getStatus(id);
    }

    @UserLoginToken
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public Result getOrderList(@RequestParam("status") int status,
                               @RequestParam("pageSize") int pageSize,
                               @RequestParam("page") int page,
                               HttpServletRequest request) {
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        List<OrderListVO> orderListVO = orderService.getList(memberId, status,pageSize,page);

        return orderListVO != null ? Result.succ(orderListVO) : Result.fail();
    }

    @UserLoginToken
    @RequestMapping(value = "/getInfo", method = RequestMethod.GET)
    public Result getInfo(@RequestParam("id") int id, HttpServletRequest request) {
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        OrderInfoVO orderInfoVO = orderService.getInfo(memberId, id);

        return orderInfoVO != null ? Result.succ(orderInfoVO) : Result.fail();
    }

    @UserLoginToken
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Result delOrder(@RequestParam("id") int id, HttpServletRequest request) {
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        boolean del = orderService.delOrder(memberId, id);

        return del ? Result.genResult(ResultEnum.SUCCESS) : Result.genResult(ResultEnum.FAIL);
    }

    @UserLoginToken
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public Result pay(@RequestParam("orderId") int orderId,
                      @RequestParam("payType") int payType,
                      HttpServletRequest request) {
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        boolean pay = orderService.pay(memberId, orderId, payType);

        return pay ? Result.genResult(ResultEnum.SUCCESS) : Result.genResult(ResultEnum.FAIL);
    }

    @UserLoginToken
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public Result cancel(@RequestParam("orderId") int orderId, HttpServletRequest request) {
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        boolean cancel = orderService.cancel(memberId, orderId);

        return cancel ? Result.genResult(ResultEnum.SUCCESS) : Result.genResult(ResultEnum.FAIL);
    }

    @UserLoginToken
    @RequestMapping(value = "/refund",method = RequestMethod.POST)
    public Result refund(@RequestParam("orderId") int orderId, HttpServletRequest request) {
        long memberId = Integer.parseInt(Objects.requireNonNull(JWTUtil.getId(request.getHeader("token"))));

        boolean refund = orderService.refund(memberId, orderId);

        return refund ? Result.genResult(ResultEnum.SUCCESS) : Result.genResult(ResultEnum.FAIL);
    }
}
