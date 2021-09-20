package com.mall.elasticsearch;

import com.mall.annotation.UserLoginToken;
import com.mall.dto.Result;
import com.mall.elasticsearch.service.ESSkuService;
import com.mall.elasticsearch.service.ESSpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/14 10:24
 */
@RestController
@RequestMapping("/es")
public class ESController {

    @Autowired
    private ESSpuService esSpuService;

    @Autowired
    private ESSkuService esSkuService;

    @UserLoginToken
    @PostMapping("/spu/refresh")
    public Result refreshSpu() {

        Boolean flag = esSpuService.refreshSpu();
        return flag ? Result.succ(null) : Result.fail();
    }

    @UserLoginToken
    @PostMapping("/sku/refresh")
    public Result refreshSku() {

        Boolean flag = esSkuService.refreshSku();
        return flag ? Result.succ(null) : Result.fail();
    }

}
