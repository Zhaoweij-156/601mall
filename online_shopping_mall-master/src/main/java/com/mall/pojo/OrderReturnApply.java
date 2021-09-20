package com.mall.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderReturnApply {

    @TableId(type = IdType.AUTO)
    private Long id; // 自增id
    private Long orderId; // 订单id
    private String orderSn; // 订单编号
    private Long skuId; // 退货商品id

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime; // 申请时间
    private String memberUsername; // 会员用户名
    private BigDecimal returnAmount; // 退款金额
    private String returnName; // 退货人姓名
    private String 	returnPhone; // 退货人电话
    private Integer status; // 申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date handleTime; // 处理时间
    private String skuImg; // 商品图片
    private String skuName; // 商品名称
    private Integer skuCount; // 退货数量
    private BigDecimal skuOriginalPrice; // 原价
    private BigDecimal skuPresentPrice; // 现价
    private String reason; // 原因
    private String handleNote; // 处理备注
    private String 	handleMan; // 处理人员
    private String 	receiveMan; // 收货人

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date receiveTime; // 收货时间
    private String 	receiveNote; // 收货备注
    private String 	receivePhone; // 收货人电话
    private String 	companyAddress; // 公司收货地址

}
