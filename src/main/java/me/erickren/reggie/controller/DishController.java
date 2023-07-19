package me.erickren.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.erickren.reggie.common.R;
import me.erickren.reggie.dto.DishDto;
import me.erickren.reggie.pojo.Dish;
import me.erickren.reggie.pojo.DishFlavor;
import me.erickren.reggie.service.CategoryService;
import me.erickren.reggie.service.DishFlavorService;
import me.erickren.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DateTime: 2023/07/18 - 11:09
 * Author: ErickRen
 */
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加菜品
     * @param request r
     * @param dish 菜品模型
     * @return R
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody DishDto dish) {
        for (DishFlavor flavor : dish.getFlavors()) {
            flavor.setCreateUser((Long) request.getSession().getAttribute("employee"));
            flavor.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        }
        dish.setCreateUser((Long) request.getSession().getAttribute("employee"));
        dish.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        dishService.saveWithFlavors(dish);
        return R.success("上传成功！");
    }

    /**
     * 分页查询
     * @param page 页码
     * @param pageSize 单页
     * @param name 关键字
     * @return R
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            String categoryName = categoryService.getById(item.getCategoryId()).getName();
            dishDto.setCategoryName(categoryName);
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }
}
