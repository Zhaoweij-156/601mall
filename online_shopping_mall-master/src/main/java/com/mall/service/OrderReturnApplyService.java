package com.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.pojo.OrderReturnApply;
import com.mall.vo.OrderReturnApplyPageVO;

/**
 * 用户退货订单service 接口
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/7 17:06
 */
public interface OrderReturnApplyService {

    /**
     * 根据用户名分页查询用户退货订单
     *
     * @param pageNo 第几页
     * @param pageSize 一页几条
     * @param username 用户名
     * @return 分页数据
     */
    IPage<OrderReturnApplyPageVO> getAllByUsernameForPage(Integer pageNo, Integer pageSize, String username);

    /**
     * 根据id查询详情
     *
     * @param id id
     * @return OrderReturnApply
     */
    OrderReturnApply getDetail(Long id);

    /**
     * 退货操作
     *
     * @param id id
     * @param handleMan 处理人
     * @param handleNote 处理备注
     * @param status 状态
     * @return Boolean
     */
    Boolean operateReturnOrder(Long id, String handleMan, String handleNote, Integer status);

}
