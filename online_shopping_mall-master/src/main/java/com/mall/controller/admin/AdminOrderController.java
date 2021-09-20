package com.mall.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.annotation.UserLoginToken;
import com.mall.dto.Result;
import com.mall.pojo.OrderReturnApply;
import com.mall.service.OrderReturnApplyService;
import com.mall.service.OrderService;
import com.mall.utils.JWTUtil;
import com.mall.vo.OrderPageVO;
import com.mall.vo.OrderReturnApplyPageVO;
import com.mall.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单管理控制器
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/7 11:12
 */
@RestController
@RequestMapping("/admin")
public class AdminOrderController {

    @Autowired
    private OrderReturnApplyService orderReturnApplyService;
    @Autowired
    private OrderService orderService;

    @UserLoginToken
    @GetMapping("/order/return/allByUsername")
    public Result getAllReturnOrderByUsername(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                              @RequestParam(value = "username", required = false) String username) {

        IPage<OrderReturnApplyPageVO> page = orderReturnApplyService.getAllByUsernameForPage(pageNo, pageSize, username);
        return Result.succ(page);
    }

    @UserLoginToken
    @GetMapping("/order/return/detail")
    public Result getReturnOrderDetail(@RequestParam("id") Long id) {

        OrderReturnApply orderReturnApply = orderReturnApplyService.getDetail(id);
        return Result.succ(orderReturnApply);
    }

    @UserLoginToken
    @PostMapping("/order/return/operate")
    public Result operateReturnOrder(@RequestParam("id") Long id,
                                     @RequestParam("handleMan") String handleMan,
                                     @RequestParam("handleNote") String handleNote,
                                     @RequestParam("status") Integer status) {

        Boolean flag = orderReturnApplyService.operateReturnOrder(id, handleMan, handleNote, status);
        return flag ? Result.succ(null) : Result.fail();
    }

    @UserLoginToken
    @GetMapping("/order/all")
    public Result getAllForPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "deleteStatus", required = false) Integer deleteStatus) {

        IPage<OrderPageVO> page = orderService.getAllForPage(pageNo, pageSize, deleteStatus);
        return Result.succ(page);
    }

    @UserLoginToken
    @GetMapping("/order/detail")
    public Result getDetailById(@RequestParam("id") Long id) {

        OrderVO orderVO = orderService.getDetailById(id);
        return Result.succ(orderVO);
    }

    @UserLoginToken
    @PostMapping("/order/deliver")
    public Result deliver(@RequestParam("id") Long id, HttpServletRequest request) {

        // 获取操作人
        String operator = JWTUtil.getId(request.getHeader("token"));
        Boolean flag = orderService.deliver(id, operator);
        return flag ? Result.succ(null) : Result.fail();
    }

}
