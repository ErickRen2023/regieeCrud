package me.erickren.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.erickren.reggie.dto.SetmealDto;
import me.erickren.reggie.pojo.Setmeal;

import java.util.List;

/**
 * DateTime: 2023/07/18 - 11:06
 * Author: ErickRen
 */
public interface SetmealService extends IService<Setmeal> {

    /**
     * 保存套餐 伴随菜品
     * @param setmealDto 套餐传输模型
     */
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐
     * @param ids id
     */
    void deleteWithDish(List<Long> ids);
}
