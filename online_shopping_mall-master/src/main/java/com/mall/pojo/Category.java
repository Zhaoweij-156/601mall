package com.mall.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 商品分类
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/18 0:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 分类名称
    private String name;

    // 是否显示[0-不显示;1-显示]
    private Integer showStatus;

    // 图标地址
    private String icon;

}
