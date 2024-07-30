package com.project.management.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @description API统一返回值
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@Getter
@Setter
public class Result<T> {

	/**
	 * 是否请求成功
	 */
    private boolean success;
	
	/**
	 * 错误信息
	 */
    private String message;
	
	/**
	 * 主体数据
	 */
    private T data;

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        return result;
    }

    public static <T> Result<T> success(String message) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> failed(String message) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }

}