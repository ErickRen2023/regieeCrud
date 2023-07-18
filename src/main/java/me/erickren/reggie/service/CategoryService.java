package me.erickren.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.erickren.reggie.pojo.Category;

/**
 * DateTime: 2023/07/17 - 17:01
 * Author: ErickRen
 */
public interface CategoryService extends IService<Category> {

    /**
     * 删除
     * @param id
     */
    void remove(Long id);
}
