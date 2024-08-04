package com.project.management.pojo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @description Project查询条件
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@Data
public class ProjectQuery {

	/**
	 * 页码
	 */
    private Integer page;

	/**
	 * 页宽
	 */
    private Integer size;
	
    /**
     * Project Name
     */
    private String name;
	
    /**
     * Category
     */
    private String category;
	
    /**
     * Creator
     */
    private Integer creatorId;
}