package com.project.management.controller;

import com.project.management.entity.ProjectManager;
import com.project.management.service.ProjectManagerService;
import com.project.management.pojo.Result;
import com.project.management.pojo.PageData;
import com.project.management.pojo.ProjectManagerQuery;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description project manager API接口
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@RestController
@RequestMapping("/projectManager")
public class ProjectManagerController {

    @Autowired
    private ProjectManagerService service;

	/**
	 * 分页查询全部project manager数据
	 *
	 * @param query 查询条件
	 * @return 分页查询project manager结果
	 */
    @GetMapping
    public Result<PageData<ProjectManager>> getAll(ProjectManagerQuery query) {
        PageData<ProjectManager> result = service.getAll(query);
        return Result.success(result);
    }

	/**
	 * 根据主键查询project manager
	 *
	 * @param id 路径参数-主键
	 * @return project manager查询结果
	 */
    @GetMapping("{id}")
    public Result<ProjectManager> get(@PathVariable Integer id) {
        ProjectManager projectManager = service.get(id);
        return Result.success(projectManager);
    }

	/**
	 * 插入或更新project manager
	 *
	 * @param projectManager 保存Project Manager对象
	 */
    @PostMapping
    public Result<ProjectManager> save(@RequestBody ProjectManager projectManager) {
        service.saveProjectManager(projectManager);
        return Result.success();
    }

	/**
	 * 根据主键删除Project Manager
	 *
	 * @param id 主键
	 */
    @DeleteMapping("{id}")
    public Result<ProjectManager> delete(@PathVariable Integer id) {
        service.delete(id);
        return Result.success();
    }
}