package com.project.management.service;

import com.project.management.entity.Approval;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.management.pojo.MyApprovalQuery;
import com.project.management.pojo.PageData;
import com.project.management.pojo.ApprovalQuery;
import java.util.List;

/**
 * @description approval 业务层
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
public interface ApprovalService extends IService<Approval> {

	/**
	 * 分页查询全部approval数据
	 *
	 * @param query 查询条件
	 * @return 分页查询approval结果
	 */
    PageData<Approval> getAll(ApprovalQuery query);

	/**
	 * 根据主键查询approval
	 *
	 * @param id 主键
	 * @return approval查询结果
	 */
    Approval get(Integer id);

	/**
	 * 插入或更新approval
	 *
	 * @param approval 保存对象
	 */
    void saveApproval(Approval approval);

	/**
	 * 根据主键删除approval
	 *
	 * @param id 主键
	 */
    void delete(Integer id);

	PageData<Approval> getApprovals(MyApprovalQuery query);

	void approve(Integer approvalId);

	void reject(Integer approvalId);
}