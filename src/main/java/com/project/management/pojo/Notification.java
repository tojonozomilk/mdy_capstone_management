package com.project.management.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Notification {
    /**
     * id
     */
    private Integer id;

    /**
     * message type
     * Notice/Chat
     */
    private String type;

    /**
     * content
     */
    private String content;

    /**
     * isRead
     */
    private Boolean isRead;

    /**
     * sender id
     */
    private Integer senderId;

    /**
     * sender
     */
    private String sender;

    /**
     * create date
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
}
