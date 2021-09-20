package com.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.pojo.OrderReturnApply;
import com.mall.vo.OrderReturnApplyPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * 用户退货订单 持久层
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/7 17:05
 */
public interface OrderReturnApplyDao extends BaseMapper<OrderReturnApply> {

    IPage<OrderReturnApplyPageVO> getAllByUsernameForPage(IPage<OrderReturnApplyPageVO> page, @Param("username") String username);

}
