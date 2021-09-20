package com.mall;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.dao.ProductDao;
import com.mall.elasticsearch.service.ESSkuService;
import com.mall.elasticsearch.service.ESSpuService;
import com.mall.pojo.Sku;
import com.mall.vo.ProductVO;
import com.mall.vo.SkuVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@SpringBootTest
class OnlineShoppingMallApplicationTests {

    @Resource
    private ProductDao productDao;
    @Autowired
    private ESSpuService esSpuService;
    @Autowired
    private ESSkuService esSkuService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testSku() throws Exception {
//        Sku sku = esSkuService.findById(1L);
//        System.out.println(sku);

        Map<Object, Map<String, Object>> map = esSkuService.findByPage(1, 3, 1L, new BigDecimal("0"), new BigDecimal("999999999"), "desc");

        System.out.println(map);
    }

    @Test
    public void test() {
        Page<ProductVO> page = new Page<>(2, 1);
        IPage<ProductVO> productVOIPage = productDao.getAllProductForPage(page, 1L);
        System.out.println("总页数：" + productVOIPage.getPages());
        List<ProductVO> productVOS = productVOIPage.getRecords();
        System.out.println(productVOS);
        System.out.println("======================================");
        System.out.println(productVOIPage);
    }

    @Test
    public void test2() {
        SkuVO skuVO = productDao.getSkuBySkuId(1L);
        System.out.println(skuVO);
    }

    @Test
    public void testEs() {
//        Spu spu = esSpuService.findById(3L);
//        System.out.println(spu);
//        List<Spu> all = esSpuService.findAll();
//        System.out.println(all);
        esSkuService.deleteById(1L);
        Sku sku = new Sku();
        sku.setId(1L);
        sku.setSpuId(1L);
        sku.setSkuName(" IQUNIX L80-动力方程式机械键盘 三模机械无线键盘 蓝牙键盘 热插拔客制化键盘 cherry红轴RGB版");
        sku.setSkuDesc(" IQUNIX L80-动力方程式机械键盘 三模机械无线键盘 蓝牙键盘 热插拔客制化键盘 cherry红轴RGB版");
        sku.setCategoryId(1L);
        sku.setSkuDefaultImg("https://img11.360buyimg.com/n1/s450x450_jfs/t1/193085/7/7351/100636/60c174caEdab6e827/31384f1f07845a56.jpg");
        sku.setOriginalPrice(new BigDecimal("999.00"));
        sku.setPresentPrice(new BigDecimal("896.00"));
        sku.setSaleCount(123);
        sku.setPublishStatus(1);
        sku.setStock(999);
        esSkuService.saveSku(sku);
    }

    @Test
    public void testEsHighLight() {
        try {
            List<Map<String, Object>> highlighter = esSkuService.findHighlighter("IQUNIX", 1, 10, 1L, null, null, "asc");
            for (Map<String, Object> map : highlighter) {
                System.out.println(map);
            }
//            System.out.println(highlighter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
