package com.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.dao.OrderReturnApplyDao;
import com.mall.pojo.OrderReturnApply;
import com.mall.service.OrderReturnApplyService;
import com.mall.vo.OrderReturnApplyPageVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户退货service 实现
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/7 17:08
 */
@Service
public class OrderReturnApplyServiceImpl implements OrderReturnApplyService {

    @Resource
    private OrderReturnApplyDao orderReturnApplyDao;

    @Override
    public IPage<OrderReturnApplyPageVO> getAllByUsernameForPage(Integer pageNo, Integer pageSize, String username) {
        IPage<OrderReturnApplyPageVO> page = new Page<>(pageNo, pageSize);
        return orderReturnApplyDao.getAllByUsernameForPage(page, username);
    }

    @Override
    public OrderReturnApply getDetail(Long id) {
        return orderReturnApplyDao.selectById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean operateReturnOrder(Long id, String handleMan, String handleNote, Integer status) {
        // 先根据id查询
        OrderReturnApply orderReturnApply = orderReturnApplyDao.selectById(id);

        // 设置更新值
        orderReturnApply.setHandleMan(handleMan);
        orderReturnApply.setHandleNote(handleNote);
        orderReturnApply.setStatus(status);
        orderReturnApply.setHandleTime(new Date());
        // 更新
        int i = orderReturnApplyDao.updateById(orderReturnApply);
        return i > 0;
    }
}
