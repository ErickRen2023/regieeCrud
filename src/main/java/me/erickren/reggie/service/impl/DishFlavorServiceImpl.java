package me.erickren.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.erickren.reggie.dto.DishDto;
import me.erickren.reggie.mapper.DishFlavorMapper;
import me.erickren.reggie.pojo.DishFlavor;
import me.erickren.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * DateTime: 2023/07/18 - 17:17
 * Author: ErickRen
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
