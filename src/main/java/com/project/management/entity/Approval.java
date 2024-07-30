package com.project.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ethan
 * @description approval 实体类
 * @date 2024-07-08 20:46:21
 */
@Data
@TableName("approval")
public class Approval implements Serializable {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * type
     * Group/Project
     */
    @TableField("type")
    private String type;

    /**
     * status
     * Init/Approve/Reject
     */
    @TableField("status")
    private String status;

    /**
     * related id
     */
    @TableField("project_id")
    private Integer projectId;

    /**
     * related id
     */
    @TableField("group_id")
    private Integer groupId;

    /**
     * related id
     */
    @TableField("creator_id")
    private Integer creatorId;

    /**
     * remark
     */
    @TableField("remark")
    private String remark;

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