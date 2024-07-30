package com.project.management.pojo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @description User查询条件
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@Data
public class UserQuery {

	/**
	 * 页码
	 */
    private Integer page;

	/**
	 * 页宽
	 */
    private Integer size;
	
    /**
     * Username
     */
    private String username;
	
    /**
     * Email
     */
    private String email;
	
    /**
     * Role
     */
    private String role;
	
    /**
     * Affiliation
     */
    private String affiliation;
	
    /**
     * Preference
     */
    private String preference;
}