package me.erickren.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.erickren.reggie.pojo.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * DateTime: 2023/07/18 - 11:05
 * Author: ErickRen
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
