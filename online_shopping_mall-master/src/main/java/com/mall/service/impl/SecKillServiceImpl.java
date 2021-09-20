package com.mall.service.impl;

import com.mall.service.SecKillService;
import com.mall.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 功能描述：
 *
 * @author yhc
 * @date 2021年08月23日 9:58
 */

@Service("secKillService")
public class SecKillServiceImpl implements SecKillService {

    private final String SECKILL_KEY = "seckill_h_key:";

    private final String PRODUCT_KEY = "seckill_product_h_key:";

    @Resource
    private RedisUtils redisUtils;

    @Override
    public String getUri(long memberId) {
        String uri = UUID.randomUUID().toString().replace("-","");

        redisUtils.set(SECKILL_KEY+uri,memberId,60*5);

        return uri;
    }

    @Override
    public String secKill(long memberId, int productId,int addressId) {
        Object o = redisUtils.get(PRODUCT_KEY + productId);
        if (o == null) {
            return null;
        }
        long decr = redisUtils.decr(PRODUCT_KEY + productId, 1);

        if (decr < 0) {
            redisUtils.incr(PRODUCT_KEY + productId, 1);
            return null;
        }

        String orderSn = UUID.randomUUID().toString();



        return orderSn;
    }

    @Override
    public boolean check(long memberId, String uri) {
        Object o = redisUtils.get(SECKILL_KEY + uri);
        if (o == null || memberId != (long)o) {
            return false;
        }
        return true;
    }
}
