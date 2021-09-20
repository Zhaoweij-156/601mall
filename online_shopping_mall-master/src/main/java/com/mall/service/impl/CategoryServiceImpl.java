package com.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.dao.CategoryDao;
import com.mall.pojo.Category;
import com.mall.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品分类 service 实现
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/31 10:27
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryDao categoryDao;

    /**
     * @see com.mall.service.CategoryService#getAllCategory(boolean)
     */
    @Override
    public List<Category> getAllCategory(boolean flag) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        if (flag) {
            wrapper.eq("show_status", 1);
        }
        List<Category> categories = categoryDao.selectList(wrapper);
        return categories;
    }

    /**
     * @see com.mall.service.CategoryService#getById(java.lang.Long)
     */
    @Override
    public Category getById(Long id) {
        return categoryDao.selectById(id);
    }

    /**
     * @see com.mall.service.CategoryService#updateCategory(com.mall.pojo.Category)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateCategory(Category category) {
        int i = categoryDao.updateById(category);
        return i > 0;
    }

    /**
     * @see com.mall.service.CategoryService#addCategory(com.mall.pojo.Category)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addCategory(Category category) {
        int i = categoryDao.insert(category);
        return i > 0;
    }

    /**
     * @see com.mall.service.CategoryService#deleteCategory(java.lang.Long)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteCategory(Long id) {
        int i = categoryDao.deleteById(id);
        return i > 0;
    }
}
