package me.erickren.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.erickren.reggie.dto.DishDto;
import me.erickren.reggie.pojo.Dish;

/**
 * DateTime: 2023/07/18 - 11:06
 * Author: ErickRen
 */
public interface DishService extends IService<Dish> {

    /**
     * 新增菜品 伴随口味
     * @param dishDto 传输模型
     */
    void saveWithFlavors(DishDto dishDto);

    /**
     * 根据ID查询菜品 附带口味
     * @param id id
     * @return 传输模型
     */
    DishDto getByIdWithFlavor(Long id);

    /**
     * 更新菜品 伴随口味
     * @param dishDto DTO传输模型{@link DishDto}
     */
    void updateWithFlavors(DishDto dishDto);
}
