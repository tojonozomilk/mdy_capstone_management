package com.project.management.controller;

import com.project.management.entity.GroupMember;
import com.project.management.service.GroupMemberService;
import com.project.management.pojo.Result;
import com.project.management.pojo.PageData;
import com.project.management.pojo.GroupMemberQuery;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description Group Member API Controller
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@RestController
@RequestMapping("/groupMember")
public class GroupMemberController {

    @Autowired
    private GroupMemberService service;

	/**
	 * Get all group member paginated
	 *
	 * @param query condition
	 * @return PageData
	 */
    @GetMapping
    public Result<PageData<GroupMember>> getAll(GroupMemberQuery query) {
        PageData<GroupMember> result = service.getAll(query);
        return Result.success(result);
    }

	/**
	 * Get group member by id
	 *
	 * @param id ID
	 * @return group member object
	 */
    @GetMapping("{id}")
    public Result<GroupMember> get(@PathVariable Integer id) {
        GroupMember groupMember = service.get(id);
        return Result.success(groupMember);
    }

	/**
	 * Upsert group member
	 *
	 * @param groupMember Group Member in req body
	 */
    @PostMapping
    public Result<GroupMember> save(@RequestBody GroupMember groupMember) {
        service.saveGroupMember(groupMember);
        return Result.success();
    }

	/**
	 * Remove Group Member by id
	 *
	 * @param id ID
	 */
    @DeleteMapping("{id}")
    public Result<GroupMember> delete(@PathVariable Integer id) {
        service.delete(id);
        return Result.success();
    }
}