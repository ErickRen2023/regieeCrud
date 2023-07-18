package me.erickren.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import me.erickren.reggie.common.R;
import me.erickren.reggie.pojo.Category;
import me.erickren.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

/**
 * DateTime: 2023/07/17 - 17:00
 * Author: ErickRen
 */
@Slf4j
@RequestMapping("/category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加分类
     * @param request
     * @param category
     * @return
     */
    @PostMapping
    public R<String> addCategory(HttpServletRequest request, @RequestBody Category category) {
        category.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        category.setCreateUser((Long) request.getSession().getAttribute("employee"));
        categoryService.save(category);
        return R.success("新建分类成功！");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        Page<Category> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Category::getSort);
        categoryService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 删除分类
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids) {
        categoryService.remove(ids);
        return R.success("删除成功");
    }

    /**
     * 由ID修改分类信息
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Category category) {
        category.setUpdateUser(((Long) request.getSession().getAttribute("employee")));
        categoryService.updateById(category);
        return R.success("修改分类信息成功！");
    }
}
