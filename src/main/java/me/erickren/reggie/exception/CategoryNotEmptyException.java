package me.erickren.reggie.exception;

/**
 * DateTime: 2023/07/18 - 11:17
 * Author: ErickRen
 */
public class CategoryNotEmptyException extends RuntimeException {
    public CategoryNotEmptyException(String msg) {
        super(msg);
    }
}
