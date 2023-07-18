package me.erickren.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.erickren.reggie.exception.CategoryNotEmptyException;
import me.erickren.reggie.mapper.CategoryMapper;
import me.erickren.reggie.pojo.Category;
import me.erickren.reggie.pojo.Dish;
import me.erickren.reggie.pojo.Setmeal;
import me.erickren.reggie.service.CategoryService;
import me.erickren.reggie.service.DishService;
import me.erickren.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * DateTime: 2023/07/17 - 17:01
 * Author: ErickRen
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);
        if (count > 0) {
            throw new CategoryNotEmptyException("当前分类下关联了菜品，不能删除！");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        count = setmealService.count(setmealLambdaQueryWrapper);
        if (count > 0) {
            throw new CategoryNotEmptyException("当前分类下关联了套餐，不能删除！");
        }
        super.removeById(id);
    }
}
