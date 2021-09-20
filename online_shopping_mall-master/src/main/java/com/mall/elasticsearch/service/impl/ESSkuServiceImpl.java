package com.mall.elasticsearch.service.impl;

import com.mall.dao.SkuDao;
import com.mall.elasticsearch.repository.SkuRepository;
import com.mall.elasticsearch.service.ESSkuService;
import com.mall.pojo.Sku;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/8/8 15:44
 */
@Service
@Slf4j
public class ESSkuServiceImpl implements ESSkuService {

    @Resource
    private SkuRepository skuRepository;

    @Resource
    private SkuDao skuDao;

    @Qualifier("elasticsearchClient")
    @Autowired
    private RestHighLevelClient client;

    private static final String NAME = "sku_name";
    private static final String SKU = "sku";

    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized Boolean refreshSku() {
        // 先清空
        skuRepository.deleteAll();
        // 从数据库中查询出来
        List<Sku> skuList = skuDao.selectList(null);
        skuRepository.saveAll(skuList);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSku(Sku sku) {
        skuRepository.save(sku);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean update(Sku sku) {
        // 先根据id进行查询
        Optional<Sku> optional = skuRepository.findById(sku.getId());
        // 如果不存在，直接返回FALSE
        if (!optional.isPresent()) {
            log.error("该Id对应的Spu不存在：{}", sku.getId());
            return Boolean.FALSE;
        }
        Sku sku1 = optional.get();
        BeanUtils.copyProperties(sku, sku1);
        skuRepository.save(sku1);
        return Boolean.TRUE;
    }

    @Override
    public Sku findById(Long id) {
        Optional<Sku> sku = skuRepository.findById(id);
        return sku.orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        skuRepository.deleteById(id);
    }

    @Override
    public Page<Sku> findByPage(Pageable pageable) {
        Page<Sku> skuPage = skuRepository.findAll(pageable);
        return skuPage;
    }

    @Override
    public Map<Object, Map<String, Object>> findByPage(int pageNo, int pageSize, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, String sort) throws Exception {
        int start = (pageNo - 1) * pageSize;

        SearchRequest searchRequest = new SearchRequest(SKU);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .from(start)
                .size(pageSize);

        if (null != categoryId) {
            // 根据分类查询
            TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("category_id", categoryId);
            searchSourceBuilder.query(termQueryBuilder);
        }

        // 区间查询
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("present_price").from(minPrice).to(maxPrice);
        searchSourceBuilder.query(rangeQueryBuilder);

        // 排序
        if ("desc".equals(sort)) {
            searchSourceBuilder.sort("present_price", SortOrder.DESC);

        } else if ("asc".equals(sort)) {
            searchSourceBuilder.sort("present_price", SortOrder.ASC);
        }

        // 执行搜索
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("使用elasticsearchClient.search发生异常", e);
            throw new Exception(e);
        }
        SearchHits hits = searchResponse.getHits();
        long total = hits.getTotalHits().value;
        Map<Object, Map<String, Object>> resultMap = new HashMap<>();
        for (SearchHit hit : hits) {
            Map<String, Object> map = hit.getSourceAsMap();
            resultMap.putIfAbsent(map.get("spu_id"), map);
        }
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> findHighlighter(String keyword, int pageNo, int pageSize, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, String sort) throws Exception {
        // 构建高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field(NAME)  // 设置name属性高亮
                .preTags("<span style='color:red'>")    // 设置前置tag
                .postTags("</span>")    // 设置后置tag
                .requireFieldMatch(false);  // 是否多个地方都高亮

        // 计算起点
        int start = (pageNo - 1) * pageSize;

        // 条件搜索
        SearchRequest searchRequest = new SearchRequest(SKU);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .from(start)
                .size(pageSize);

        // 匹配查询
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(NAME, keyword);

        // 区间查询
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("present_price").from(minPrice).to(maxPrice);

        // 条件查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(rangeQueryBuilder);

        if (null != categoryId) {
            // 根据分类查询
            TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("category_id", categoryId);
//            searchSourceBuilder.query(termQueryBuilder);
            boolQueryBuilder.must(termQueryBuilder);
        }

        // 排序
        if ("desc".equals(sort)) {
            searchSourceBuilder.sort("present_price", SortOrder.DESC);

        } else if ("asc".equals(sort)) {
            searchSourceBuilder.sort("present_price", SortOrder.ASC);
        }
        boolQueryBuilder.must(matchQueryBuilder);

        // 设置模糊查询
        searchSourceBuilder.query(boolQueryBuilder);

        // 设置超时时间，60s
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        // 设置高亮
        searchSourceBuilder.highlighter(highlightBuilder);

        // 执行搜索
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("使用elasticsearchClient.search发生异常", e);
            throw new Exception(e);
        }

        // 解析结果
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {

            // 原来的结果
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();

            // 高亮部分
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField name = highlightFields.get(NAME);
            // 解析高亮字段，将原来的字段换成高亮字段
            if (null != name) {
                Text[] fragments = name.getFragments();
                StringBuilder newName = new StringBuilder();
                for (Text fragment : fragments) {
                    // 将高亮显示的内容拆分后组装到newName
                    newName.append(fragment);
                }
                sourceAsMap.put(NAME, newName.toString());
            }
            list.add(sourceAsMap);
        }

        return list;
    }
}
