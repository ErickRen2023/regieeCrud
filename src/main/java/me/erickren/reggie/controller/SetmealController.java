package me.erickren.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.erickren.reggie.common.R;
import me.erickren.reggie.dto.SetmealDto;
import me.erickren.reggie.pojo.Category;
import me.erickren.reggie.pojo.Setmeal;
import me.erickren.reggie.pojo.SetmealDish;
import me.erickren.reggie.service.CategoryService;
import me.erickren.reggie.service.SetmealDishService;
import me.erickren.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DateTime: 2023/07/19 - 17:58
 * Author: ErickRen
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增套餐
     * @param setmealDto Dto传输模型
     * @return R
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody SetmealDto setmealDto) {
        setmealDto.setCreateUser((Long) request.getSession().getAttribute("employee"));
        setmealDto.setUpdateUser(((Long) request.getSession().getAttribute("employee")));
        for (SetmealDish setmealDish : setmealDto.getSetmealDishes()) {
            setmealDish.setCreateUser((Long) request.getSession().getAttribute("employee"));
            setmealDish.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        }
        setmealService.saveWithDish(setmealDto);

        return R.success("保存成功");
    }

    /**
     * 分页查询
     * @param page 当前页数
     * @param pageSize 每页个数
     * @return R
     */
    @GetMapping("/page")
    public R<Page> list(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> targetPage = new Page<>();
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Setmeal::getName, name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo, queryWrapper);

        // copy Object
        BeanUtils.copyProperties(pageInfo, targetPage, "records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> lists = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                setmealDto.setCategoryName(category.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());
        targetPage.setRecords(lists);
        return R.success(targetPage);
    }

    /**
     * 更改套餐销售状态
     * @param statusNumber 0：停售 1：销售
     * @param ids ids
     * @return R
     */
    @PostMapping("/status/{statusNumber}")
    public R<String> update(@PathVariable int statusNumber, @RequestParam List<Long> ids) {
        for (Long id : ids) {
            Setmeal setmeal = setmealService.getById(id);
            setmeal.setStatus(statusNumber);
            setmealService.updateById(setmeal);
        }
        return R.success("更改状态成功。");
    }

    /**
     * 删除套餐
     * @param ids ids
     * @return R
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        setmealService.deleteWithDish(ids);
        return R.success("删除成功");
    }

    /**
     * 根据id获取套餐
     * @param id id
     * @return R
     */
    @GetMapping("/{id}")
    public R<Setmeal> getById(@PathVariable Long id) {
        Setmeal setmeal = setmealService.getById(id);
        return R.success(setmeal);
    }
}
