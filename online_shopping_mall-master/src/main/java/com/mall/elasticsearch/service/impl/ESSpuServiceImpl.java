package com.mall.elasticsearch.service.impl;

import com.mall.dao.SpuDao;
import com.mall.elasticsearch.repository.SpuRepository;
import com.mall.elasticsearch.service.ESSpuService;
import com.mall.pojo.Spu;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * spu service层实现
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/24 14:54
 */
@Service
@Slf4j
public class ESSpuServiceImpl implements ESSpuService {

    @Resource
    private SpuRepository spuRepository;

    @Resource
    private SpuDao spuDao;

    @Qualifier("elasticsearchClient")
    @Autowired
    private RestHighLevelClient client;

    private static final String NAME = "name";
    private static final String SPU = "spu";

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean refreshSpu() {
        // 先清空原有的数据
        spuRepository.deleteAll();
        // 从数据库中查询
        List<Spu> spuList = spuDao.selectList(null);
        // 再批量导入
        spuRepository.saveAll(spuList);
        return true;
    }

    /**
     * @see ESSpuService#saveSpu(com.mall.pojo.Spu)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSpu(Spu spu) {
        spuRepository.save(spu);
    }

    /**
     * @see ESSpuService#update(com.mall.pojo.Spu)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean update(Spu spu) {
        // 先根据id进行查询
        Optional<Spu> optional = spuRepository.findById(spu.getId());
        // 如果不存在，直接返回FALSE
        if (!optional.isPresent()) {
            log.error("该Id对应的Spu不存在：{}", spu.getId());
            return Boolean.FALSE;
        }
        Spu spu1 = optional.get();
        BeanUtils.copyProperties(spu, spu1);
        spuRepository.save(spu1);
        return Boolean.TRUE;
    }

    /**
     * @see ESSpuService#findById(java.lang.Long)
     */
    @Override
    public Spu findById(Long id) {
        Optional<Spu> spu = spuRepository.findById(id);
        // 返回spu或者null
        return spu.orElse(null);
    }

    /**
     * @see ESSpuService#deleteById(java.lang.Long)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Long id) {
        spuRepository.deleteById(id);
    }

    /**
     * @see ESSpuService#findByPage(org.springframework.data.domain.Pageable)
     */
    @Override
    public Page<Spu> findByPage(Pageable pageable) {
        Page<Spu> spuPage = spuRepository.findAll(pageable);
        return spuPage;
    }

    /**
     * @see ESSpuService#findAll()
     */
    @Override
    public List<Spu> findAll() {
        Iterable<Spu> spus = spuRepository.findAll();
        List<Spu> spuList = new ArrayList<>();
        spus.forEach(spuList::add);
        return spuList;
    }

    /**
     * @see ESSpuService#findHighlighter(java.lang.String, int, int)
     */
    @Override
    public List<Map<String,Object>> findHighlighter(String keyword, int pageNo, int pageSize) throws Exception {

        // 构建高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field(NAME)  // 设置name属性高亮
                .preTags("<span style='color:red'>")    // 设置前置tag
                .postTags("</span>")    // 设置后置tag
                .requireFieldMatch(false);  // 是否多个地方都高亮

        // 模糊查询
//        FuzzyQueryBuilder builder = QueryBuilders.fuzzyQuery(NAME, keyword);
        MatchQueryBuilder builder = QueryBuilders.matchQuery(NAME, keyword);

        int start = (pageNo - 1) * pageSize;
        // 条件搜索
        SearchRequest searchRequest = new SearchRequest(SPU);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                                            .from(start)
                                            .size(pageSize);

        // 设置模糊查询
        searchSourceBuilder.query(builder);
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
