package com.project.management.pojo;

import lombok.Data;

/**
 * @author Ethan
 * @description Approval查询条件
 * @date 2024-07-08 20:46:21
 */
@Data
public class ApprovalQuery {

    /**
     * 页码
     */
    private Integer page;

    /**
     * 页宽
     */
    private Integer size;

    /**
     * Type
     */
    private String type;

    /**
     * Status
     */
    private String status;

    /**
     * Related
     */
    private Integer relateId;

    /**
     * Remark
     */
    private String remark;
}