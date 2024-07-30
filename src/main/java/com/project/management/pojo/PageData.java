package com.project.management.pojo;

import lombok.Data;
import java.util.List;

/**
 * @description 分页数据实体
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@Data
public class PageData<T> {
	/**
	 * 页码
	 */
    private Integer page;
	/**
	 * 页宽
	 */
    private Integer size;
	/**
	 * 总数
	 */
    private Integer total;
	/**
	 * 当前页数据
	 */
    private List<T> content;
}