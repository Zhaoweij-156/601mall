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

/**
 * 秒杀订单
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/18 0:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SeckillOrder {

    @TableId(type = IdType.AUTO) // 自增ID
    private Long id; // 订单id
    private Long memberId; // 会员id
    private String memberUsername; // 用户名
    private String secOrderSn; // 订单号
    private Long skuId;
    private String skuName;
    private String skuImg;
    private BigDecimal skuOriginalPrice;
    private BigDecimal payMount;

    private BigDecimal freightAmount; // 运费总额
    private Integer payType; // 支付方式【0->支付宝；1->微信；2->银联】
    private Integer status; // 订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】
    private String deliveryCompany; // 配送公司
    private String deliverySn; // 物流单号（保证唯一）
    private Integer autoConfirmDay; // 自动确认收货时间（天）
    private String receiverName; // 收货人姓名
    private String receiverPhone; // 收货人电话
    private String receiverPostCode; // 收货人邮箱
    private String receiverProvince; // 省份/直辖市
    private String receiverCity; // 城市
    private String receiverRegion; // 区
    private String receiverDetailAddress; // 详细地址
    private String node; // 订单备注
    private Integer confirmStatus; // 确认收货状态[F->未确认；T->已确认]
    private Integer deleteStatus; // 删除状态(软删除)【T->未删除；F->已删除】

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date payTime; // 支付时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date deliveryTime; // 发货时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date receiveTime; // 确认收货时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime; // 订单创建时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date modifiedTime; // 修改时间

}
