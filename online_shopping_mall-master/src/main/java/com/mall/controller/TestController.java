package com.mall.controller;

import com.mall.dto.Result;
import com.mall.dao.TestDao;
import com.mall.elasticsearch.repository.SpuRepository;
import com.mall.elasticsearch.service.ESSpuService;
import com.mall.pojo.Spu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/17 0:16
 */
@RestController
public class TestController {

    @Resource
    private TestDao testDao;
    @Autowired
    private SpuRepository spuRepository;
    @Resource
    private ESSpuService spuService;

    @GetMapping("/test")
    public Result test() {
        Spu spu = testDao.selectById(1L);
        return Result.succ(spu);
    }

    @GetMapping("/testes")
    public Result testes() {
        Iterable<Spu> spus = spuRepository.findAll();
        List<Spu> spuList = new ArrayList<>();
        for (Spu spu : spus) {
            spuList.add(spu);
        }
        return Result.succ(spuList);
    }

    @GetMapping("/testpage")
    public Result testpage() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<Spu> spuPage = spuService.findByPage(pageable);
        return Result.succ(spuPage);
    }

    @GetMapping("/testhigligt/{keyword}")
    public Result testhigligt(@PathVariable("keyword") String keyword) throws Exception {
        List<Map<String, Object>> highlighter = spuService.findHighlighter(keyword, 0, 2);
        return Result.succ(highlighter);
    }


}
