package com.project.management.pojo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @description Project Manager查询条件
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@Data
public class ProjectManagerQuery {

	/**
	 * 页码
	 */
    private Integer page;

	/**
	 * 页宽
	 */
    private Integer size;
	
    /**
     * Project
     */
    private Integer projectId;
	
    /**
     * Manager
     */
    private Integer managerId;
}