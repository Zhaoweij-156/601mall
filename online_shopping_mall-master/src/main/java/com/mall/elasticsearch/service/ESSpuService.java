package com.mall.elasticsearch.service;

import com.mall.pojo.Spu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Spu service层接口
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/24 14:53
 */
public interface ESSpuService {

    /**
     * 刷新Spu
     * @return 是否成功
     */
    Boolean refreshSpu();

    /**
     * 新增一个Spu
     *
     * @param spu Spu对象
     */
    void saveSpu(Spu spu);

    /**
     * 更新一个Spu，先查询再更新
     *
     * @param spu Spu对象
     */
    Boolean update(Spu spu);

    /**
     * 根据主键查询
     *
     * @param id 主键id
     * @return Spu对象
     */
    Spu findById(Long id);

    /**
     * 根据id删除
     *
     * @param id 主键id
     */
    void deleteById(Long id);

    /**
     * 根据条件分页查询
     *
     * @param pageable 分页对象
     * @return Page<Spu>
     */
    Page<Spu> findByPage(Pageable pageable);

    /**
     * 查询所有Spu
     *
     * @return List<Spu>
     */
    List<Spu> findAll();

    /**
     * 高亮查询，分页
     *
     * @param keyword 查询的keyword
     * @param pageNo 第几页
     * @param pageSize 页面大小
     * @return List<Spu>
     * @throws Exception e
     */
    List<Map<String,Object>> findHighlighter(String keyword, int pageNo, int pageSize) throws Exception;

}
