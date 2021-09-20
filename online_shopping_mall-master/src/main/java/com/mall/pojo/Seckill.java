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
 * 秒杀商品
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/18 0:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Seckill {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 商品sku编号
    private Long skuId;

    // 商品sku名字
    private String skuName;

    // 商品sku图片
    private String skuImg;

    // 原价
    private BigDecimal skuOriginalPrice;

    // 秒杀价格
    private BigDecimal seckillPrice;

    // 秒杀库存
    private Integer stock;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    // 秒杀开始时间
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    // 秒杀结束时间
    private Date endTime;

}
