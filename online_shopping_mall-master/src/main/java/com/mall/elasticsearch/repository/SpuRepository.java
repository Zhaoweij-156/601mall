package com.mall.elasticsearch.repository;

import com.mall.pojo.Spu;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * spu持久层
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/18 22:07
 */
@Repository
public interface SpuRepository extends ElasticsearchRepository<Spu, Long> {
}
