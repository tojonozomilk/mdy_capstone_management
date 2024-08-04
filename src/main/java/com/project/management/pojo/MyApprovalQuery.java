package com.project.management.pojo;

import lombok.Data;

/**
 * @author Ethan
 * @description Approval查询条件
 * @date 2024-07-08 20:46:21
 */
@Data
public class MyApprovalQuery {

    /**
     * 页码
     */
    private Integer page;

    /**
     * 页宽
     */
    private Integer size;

    /**
     * user id
     */
    private Integer userId;
}