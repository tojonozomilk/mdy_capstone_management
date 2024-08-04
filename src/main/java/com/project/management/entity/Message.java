package com.project.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ethan
 * @description message 实体类
 * @date 2024-07-08 20:46:21
 */
@Data
@TableName("message")
public class Message implements Serializable {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * message type
     * Notice/Chat
     */
    @TableField("type")
    private String type;

    /**
     * content
     */
    @TableField("content")
    private String content;

    /**
     * sender id
     */
    @TableField("sender_id")
    private Integer senderId;

    /**
     * receiver id
     */
    @TableField("receiver_id")
    private Integer receiverId;

    /**
     * is read
     */
    @TableField("is_read")
    private Boolean isRead;

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