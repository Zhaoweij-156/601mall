package com.mall.service;

/**
 * @author yhc
 */
public interface SecKillService {
    /**
     * 获取真正的秒杀接口
     *
     * @param memberId
     * @return String
     */
    String getUri(long memberId);

    /**
     * 秒杀
     *
     * @param memberId
     * @param addressId
     * @param productId
     * @return String
     */
    String secKill(long memberId, int productId,int addressId);

    /**
     * 检测uri与用户id是否对应
     *
     * @param memberId
     * @param uri
     * @return boolean
     */
    boolean check(long memberId, String uri);
}
