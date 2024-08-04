package com.project.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description group member 实体类
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@Data
@TableName("group_member")
public class GroupMember implements Serializable {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * user id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * group id
     */
    @TableField("group_id")
    private Integer groupId;

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