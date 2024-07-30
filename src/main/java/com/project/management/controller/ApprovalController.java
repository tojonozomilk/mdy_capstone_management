package com.project.management.controller;

import com.project.management.entity.Approval;
import com.project.management.service.ApprovalService;
import com.project.management.pojo.Result;
import com.project.management.pojo.PageData;
import com.project.management.pojo.ApprovalQuery;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description Approval Controller API
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@RestController
@RequestMapping("/approval")
public class ApprovalController {

    @Autowired
    private ApprovalService service;

	/**
	 * Get All Approvals Paginated
	 *
	 * @param query query condition
	 * @return PageData
	 */
    @GetMapping
    public Result<PageData<Approval>> getAll(ApprovalQuery query) {
        PageData<Approval> result = service.getAll(query);
        return Result.success(result);
    }

	/**
	 * Get Approval by ID
	 *
	 * @param id ID
	 * @return Approval
	 */
    @GetMapping("{id}")
    public Result<Approval> get(@PathVariable Integer id) {
        Approval approval = service.get(id);
        return Result.success(approval);
    }

	/**
	 * Upsert Approval
	 *
	 * @param approval Approval in req body
	 */
    @PostMapping
    public Result<Approval> save(@RequestBody Approval approval) {
        service.saveApproval(approval);
        return Result.success();
    }

	/**
	 * Delete Approval by ID
	 *
	 * @param id ID
	 */
    @DeleteMapping("{id}")
    public Result<Approval> delete(@PathVariable Integer id) {
        service.delete(id);
        return Result.success();
    }
}