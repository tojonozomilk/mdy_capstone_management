package com.project.management.pojo;

import lombok.Data;

/**
 * @description 前端统一选项实体
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@Data
public class SelectorOption {
	/**
	 * 选项值
	 */
    private Object value;
	/**
	 * 展示标签
	 */
    private Object label;
}