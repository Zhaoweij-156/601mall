package com.mall.controller.admin;

import com.mall.annotation.UserLoginToken;
import com.mall.dto.Result;
import com.mall.dto.ResultEnum;
import com.mall.pojo.Category;
import com.mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/31 10:36
 */
@RestController
@RequestMapping("/admin")
@UserLoginToken
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @UserLoginToken
    @GetMapping("/allcategory")
    public Result allCategory(boolean flag) {
        List<Category> categoryList = categoryService.getAllCategory(flag);
        return Result.succ(categoryList);
    }

    @UserLoginToken
    @GetMapping("/category")
    public Result getCategory(Long id) {
        Category category = categoryService.getById(id);
        return Result.succ(category);
    }

    @UserLoginToken
    @PostMapping("/category")
    public Result updateCategory(Category category) {
        Category category1 = categoryService.getById(category.getId());
        if (null == category1) {
            return Result.genResult(ResultEnum.CATEGORY_NOT_EXIST);
        }

        Boolean flag = categoryService.updateCategory(category);
        if (!flag) {
            return Result.fail();
        }
        return Result.succ(null);
    }

    @UserLoginToken
    @PutMapping("/category")
    public Result addCategory(Category category) {
        Category category1 = categoryService.getById(category.getId());
        if (null != category1) {
            return Result.genResult(ResultEnum.CATEGORY_IS_EXIST);
        }
        Boolean flag = categoryService.addCategory(category);
        return flag ? Result.succ(null) : Result.fail();
    }

    @UserLoginToken
    @DeleteMapping("/category")
    public Result deleteCategory(Long id) {
        Category category1 = categoryService.getById(id);
        if (null == category1) {
            return Result.genResult(ResultEnum.CATEGORY_NOT_EXIST);
        }
        Boolean flag = categoryService.deleteCategory(id);
        return flag ? Result.succ(null) : Result.fail();
    }

}
