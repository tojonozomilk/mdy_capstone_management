package com.project.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description user 实体类
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@Data
@TableName("user")
public class User implements Serializable {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * username
     */
    @TableField("username")
    private String username;

    /**
     * password
     */
    @TableField("password")
    private String password;

    /**
     * avatar path
     */
    @TableField("avatar")
    private String avatar;

    /**
     * email
     */
    @TableField("email")
    private String email;

    /**
     * user role
     * Administrator/ProjectClient/Tutor/Coordinator/Student
     */
    @TableField("role")
    private String role;

    /**
     * affiliation
     */
    @TableField("affiliation")
    private String affiliation;

    /**
     * preference
     * Research/Application
     */
    @TableField("preference")
    private String preference;

    /**
     * create date
     */
    @TableField(value = "create_date", fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * amend date
     */
    @TableField(value = "amend_date", fill = FieldFill.INSERT_UPDATE)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date amendDate;
}