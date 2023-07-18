package me.erickren.reggie.common;

import lombok.extern.slf4j.Slf4j;
import me.erickren.reggie.exception.CategoryNotEmptyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice(annotations = {RestControllerAdvice.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理SQL重复异常
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException e) {
            log.error(e.getMessage());
            if (e.getMessage().contains("Duplicate entry")) {
                String[] split = e.getMessage().split(" ");
                String msg = split[2] + "已存在。";
                return R.error(msg);
            } else {
                return R.error("未知错误！");
            }
    }

    /**
     * 处理非空分类删除问题
     * @param e
     * @return
     */
    @ExceptionHandler(CategoryNotEmptyException.class)
    public R<String> categoryNotEmptyHandler(CategoryNotEmptyException e) {
        log.error(e.getMessage());
        return R.error(e.getMessage());
    }
}
