package me.erickren.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.erickren.reggie.dto.DishDto;
import me.erickren.reggie.mapper.DishMapper;
import me.erickren.reggie.pojo.Dish;
import me.erickren.reggie.pojo.DishFlavor;
import me.erickren.reggie.service.DishFlavorService;
import me.erickren.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DateTime: 2023/07/18 - 11:07
 * Author: ErickRen
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    @Transactional
    public void saveWithFlavors(DishDto dishDto) {
        this.save(dishDto);

        Long id = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().peek((item) -> item.setDishId(id)).collect(Collectors.toList());
        // save to dishFlavor
        dishFlavorService.saveBatch(flavors);
    }
}
