package com.mall.service;

import com.mall.pojo.Category;

import java.util.List;

/**
 * 商品分类 service 接口
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/31 10:26
 */
public interface CategoryService {

    /**
     * 获取所有分类，传参来剔除下架的
     *
     * @param flag boolean
     * @return List<Category>
     */
    List<Category> getAllCategory(boolean flag);

    /**
     * 根据id获取分类
     *
     * @param id Long
     * @return Category
     */
    Category getById(Long id);

    /**
     * 更新分类
     *
     * @param category Category
     * @return Boolean
     */
    Boolean updateCategory(Category category);

    /**
     * 新增分类
     *
     * @param category Category
     * @return Boolean
     */
    Boolean addCategory(Category category);

    /**
     * 根据id删除分类
     *
     * @param id Long
     * @return Boolean
     */
    Boolean deleteCategory(Long id);

}
