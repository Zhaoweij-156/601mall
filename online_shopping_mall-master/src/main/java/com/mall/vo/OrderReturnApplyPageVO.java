package com.mall.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用户退货订单信息VO（分页）
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/7 17:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderReturnApplyPageVO {

    private Long id; // 自增id
    private String memberUsername; // 会员用户名
    private String orderSn; // 订单编号

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime; // 申请时间
    private Integer status; // 申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]
}
