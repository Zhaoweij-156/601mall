package com.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.pojo.Order;
import com.mall.vo.OrderPageVO;
import com.mall.vo.OrderVO;
import org.apache.ibatis.annotations.Param;

/**
 * 订单持久层
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/7 17:00
 */
public interface OrderDao extends BaseMapper<Order> {

    IPage<OrderPageVO> getAllForPage(IPage<OrderPageVO> page, @Param("deleteStatus") Integer deleteStatus);

    OrderVO getDetailById(Long id);

}
