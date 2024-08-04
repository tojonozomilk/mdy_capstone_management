package com.project.management.pojo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @description Group查询条件
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@Data
public class GroupQuery {

	/**
	 * 页码
	 */
    private Integer page;

	/**
	 * 页宽
	 */
    private Integer size;
	
    /**
     * Group Name
     */
    private String name;
	
    /**
     * Admin
     */
    private Integer adminId;
	
    /**
     * Project
     */
    private Integer projectId;
}