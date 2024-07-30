package com.project.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description project 实体类
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@Data
@TableName("project")
public class Project implements Serializable {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * name
     */
    @TableField("name")
    private String name;

    /**
     * description
     */
    @TableField("description")
    private String description;

    /**
     * category
     * Research/Application
     */
    @TableField("category")
    private String category;

    /**
     * creator
     */
    @TableField("creator_id")
    private Integer creatorId;

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