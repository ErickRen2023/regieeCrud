package me.erickren.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.erickren.reggie.mapper.DishMapper;
import me.erickren.reggie.pojo.Dish;
import me.erickren.reggie.service.DishService;
import org.springframework.stereotype.Service;

/**
 * DateTime: 2023/07/18 - 11:07
 * Author: ErickRen
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
