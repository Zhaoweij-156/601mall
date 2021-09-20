package com.mall.elasticsearch.service;

import com.mall.pojo.Sku;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * es sku service 接口
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/8 15:41
 */
public interface ESSkuService {

    Boolean refreshSku();

    void saveSku(Sku sku);

    Boolean update(Sku sku);

    Sku findById(Long id);

    void deleteById(Long id);

    Page<Sku> findByPage(Pageable pageable);

    Map<Object, Map<String, Object>> findByPage(int pageNo, int pageSize, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, String sort) throws Exception;

    List<Map<String,Object>> findHighlighter(String keyword, int pageNo, int pageSize, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, String sort) throws Exception;

}
