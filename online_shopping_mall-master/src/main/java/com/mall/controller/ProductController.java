package com.mall.controller;

import com.mall.dto.Result;
import com.mall.elasticsearch.service.ESSkuService;
import com.mall.service.ProductService;
import com.mall.vo.ProductIndexTop8VO;
import com.mall.vo.SkuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 前台商品controller
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/8 17:27
 */
@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

    @Autowired
    private ESSkuService esSkuService;
    @Autowired
    private ProductService productService;

    @GetMapping("/allforpage")
    public Result getAllProductForPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                       @RequestParam(value = "categoryId", required = false) Long categoryId,
                                       @RequestParam(value = "minPrice", defaultValue = "0") BigDecimal minPrice,
                                       @RequestParam(value = "maxPrice", defaultValue = "999999999") BigDecimal maxPrice,
                                       @RequestParam(value = "sort", defaultValue = "") String sort) throws Exception {

        Map<Object, Map<String, Object>> page = esSkuService.findByPage(pageNo, pageSize, categoryId, minPrice, maxPrice, sort);
        return Result.succ(page.values());
    }

    @GetMapping("/top8")
    public Result top8() {

        List<ProductIndexTop8VO> top8 = productService.getIndexTop8();
        return Result.succ(top8);
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam("spuId") Long spuId) {

        List<SkuVO> skuVOS = productService.getDetailBySpuId(spuId);
        return Result.succ(skuVOS);
    }

    @GetMapping("/search")
    public Result search(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                         @RequestParam(value = "categoryId", required = false) Long categoryId,
                         @RequestParam(value = "minPrice", defaultValue = "0") BigDecimal minPrice,
                         @RequestParam(value = "maxPrice", defaultValue = "999999999") BigDecimal maxPrice,
                         @RequestParam(value = "sort", defaultValue = "") String sort,
                         @RequestParam(value = "keyword") String keyword) throws Exception {
        List<Map<String, Object>> list = esSkuService.findHighlighter(keyword, pageNo, pageSize, categoryId, minPrice, maxPrice, sort);
        return Result.succ(list);
    }

}
