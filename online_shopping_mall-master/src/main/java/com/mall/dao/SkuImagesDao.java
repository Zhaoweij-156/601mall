package com.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.pojo.SkuImages;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/1 10:16
 */
public interface SkuImagesDao extends BaseMapper<SkuImages> {

    List<SkuImages> getAllImgBySkuId(@Param("skuId") Long skuId);

}
