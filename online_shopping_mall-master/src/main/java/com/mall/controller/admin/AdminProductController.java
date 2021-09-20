package com.mall.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mall.annotation.UserLoginToken;
import com.mall.dto.Result;
import com.mall.dto.ResultEnum;
import com.mall.pojo.Sku;
import com.mall.pojo.Spu;
import com.mall.service.ProductService;
import com.mall.vo.ProductVO;
import com.mall.vo.SkuVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/1 8:44
 */
@RestController
@RequestMapping("/admin")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @UserLoginToken
    @GetMapping("/product/allforpage")
    public Result getAllProductForPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                       @RequestParam(value = "categoryId", required = false) Long categoryId) {

        IPage<ProductVO> productVOPage = productService.getAllProductForPage(pageNo, pageSize, categoryId);
        return Result.succ(productVOPage);
    }

    @UserLoginToken
    @GetMapping("/sku")
    public Result getSku(@Param("skuId") Long skuId) {
        SkuVO sku = productService.getSkuVOBySkuId(skuId);
        return Result.succ(sku);
    }

    @UserLoginToken
    @PutMapping("/spu")
    public Result addSpu(Spu spu) {
        Boolean flag = productService.addSpu(spu);
        return flag ? Result.succ(null) : Result.fail();
    }

    @UserLoginToken
    @PostMapping("/spu")
    public Result updateSpu(Spu spu) {
        Spu spuById = productService.getSpuById(spu.getId());
        if (null == spuById) {
            return Result.genResult(ResultEnum.SPU_NOT_EXIST);
        }
        Boolean flag = productService.updateSpu(spu);
        return flag ? Result.succ(null) : Result.fail();
    }

    @UserLoginToken
    @PutMapping("/sku")
    public Result addSku(Sku sku) {
        Boolean flag = productService.addSku(sku);
        return flag ? Result.succ(null) : Result.fail();
    }

    @UserLoginToken
    @PostMapping("/sku")
    public Result updateSku(Sku sku) {
        Sku skuById = productService.getSkuById(sku.getId());
        if (null == skuById) {
            return Result.genResult(ResultEnum.SKU_NOT_EXIST);
        }
        Boolean flag = productService.updateSku(sku);
        return flag ? Result.succ(null) : Result.fail();
    }

}
