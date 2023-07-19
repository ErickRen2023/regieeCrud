package me.erickren.reggie.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.erickren.reggie.pojo.Dish;
import me.erickren.reggie.pojo.DishFlavor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
