package me.erickren.reggie.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.erickren.reggie.pojo.Setmeal;
import me.erickren.reggie.pojo.SetmealDish;
import java.util.List;


/**
 * DTO: 套餐数据传输模型
 * {@link Setmeal}
 * {@link SetmealDish}
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
