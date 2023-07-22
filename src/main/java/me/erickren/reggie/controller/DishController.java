package me.erickren.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        return R.success("上传成功");
    }

    /**
     * 修改菜品
     * @param request r
     * @param dish 菜品模型
     * @return R
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody DishDto dish) {
        for (DishFlavor flavor : dish.getFlavors()) {
            flavor.setCreateUser((Long) request.getSession().getAttribute("employee"));
            flavor.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        }
        dish.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        dishService.updateWithFlavors(dish);
        return R.success("修改成功");
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

    /**
     * 根据id获取菜品
     * @param id id
     * @return R
     */
    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 设置停售起售状态
     * @param statusNumber 停起售状态
     * @param ids id
     * @return R
     */
    @PostMapping("/status/{statusNumber}")
    public R<String> stopSell(@PathVariable int statusNumber, String ids) {
        if (ids.contains(",")) {
            String[] split = ids.split(",");
            for (String s : split) {
                Dish dish = dishService.getById(Long.valueOf(s));
                dish.setStatus(statusNumber);
                dishService.updateById(dish);
            }
        } else {
            Dish dish = dishService.getById(Long.valueOf(ids));
            dish.setStatus(statusNumber);
            dishService.updateById(dish);
        }
        return R.success("修改成功");
    }

    /**
     * 删除
     * @param ids id
     * @return R
     */
    @DeleteMapping
    public R<String> delete(String ids) {
        if (ids.contains(",")) {
            String[] split = ids.split(",");
            for (String s : split) {
                dishService.removeById(Long.valueOf(s));
            }
        } else {
            dishService.removeById(Long.valueOf(ids));
        }
        return R.success("删除成功");
    }

    /**
     * 获取菜品列表
     * @param dish 菜品模型
     * @return R
     */
    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId,  dish.getCategoryId());
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);
        return R.success(list);
    }
}
