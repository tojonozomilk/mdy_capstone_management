package com.project.management.controller;

import com.project.management.entity.Group;
import com.project.management.service.GroupService;
import com.project.management.pojo.Result;
import com.project.management.pojo.PageData;
import com.project.management.pojo.GroupQuery;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.management.pojo.SelectorOption;
import java.util.List;

/**
 * @description Group API Controller
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService service;

	/**
	 * Get All Groups Paginated
	 *
	 * @param query condition
	 * @return PageData
	 */
    @GetMapping
    public Result<PageData<Group>> getAll(GroupQuery query) {
        PageData<Group> result = service.getAll(query);
        return Result.success(result);
    }

	/**
	 * Get Group by ID
	 *
	 * @param id ID
	 * @return Group
	 */
    @GetMapping("{id}")
    public Result<Group> get(@PathVariable Integer id) {
        Group group = service.get(id);
        return Result.success(group);
    }

	/**
	 * Upsert Group
	 *
	 * @param group Group in req body
	 */
    @PostMapping
    public Result<Group> save(@RequestBody Group group) {
        service.saveGroup(group);
        return Result.success(group);
    }

	/**
	 * Delete Group by id
	 *
	 * @param id ID
	 */
    @DeleteMapping("{id}")
    public Result<Group> delete(@PathVariable Integer id) {
        service.delete(id);
        return Result.success();
    }

	/**
	 * Get All Group IDs for drop-down
	 *
	 * @return List of group ids
	 */
    @GetMapping("getAllId")
	public Result<List<SelectorOption>> getAllId() {
        List<SelectorOption> options = service.getAllId();
        return Result.success(options);
	}
}