package com.project.management.controller;

import com.project.management.entity.Project;
import com.project.management.service.ProjectService;
import com.project.management.pojo.Result;
import com.project.management.pojo.PageData;
import com.project.management.pojo.ProjectQuery;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.management.pojo.SelectorOption;

import java.util.List;

/**
 * @description Project API Controller
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService service;

	/**
	 * Get all Projects
	 *
	 * @param query condition
	 * @return PageData<Project>
	 */
    @GetMapping
    public Result<PageData<Project>> getAll(ProjectQuery query) {
        PageData<Project> result = service.getAll(query);
        return Result.success(result);
    }

	/**
	 * Get Project by id
	 *
	 * @param id ID
	 * @return project
	 */
    @GetMapping("{id}")
    public Result<Project> get(@PathVariable Integer id) {
        Project project = service.get(id);
        return Result.success(project);
    }

	/**
	 * Upsert project
	 *
	 * @param project Project in req body
	 */
    @PostMapping
    public Result<Project> save(@RequestBody Project project) {
        service.saveProject(project);
        return Result.success(project);
    }

	/**
	 * Delete Project by id
	 *
	 * @param id ID
	 */
    @DeleteMapping("{id}")
    public Result<Project> delete(@PathVariable Integer id) {
        service.delete(id);
        return Result.success();
    }

	/**
	 * Get all Project ID for drop-down
	 *
	 * @return list of ID
	 */
    @GetMapping("getAllId")
	public Result<List<SelectorOption>> getAllId() {
        List<SelectorOption> options = service.getAllId();
        return Result.success(options);
	}

}