package com.project.management.service;

import com.project.management.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.management.pojo.PageData;
import com.project.management.pojo.ProjectDetail;
import com.project.management.pojo.ProjectQuery;

import java.util.List;
import java.util.Map;

import com.project.management.pojo.SelectorOption;

/**
 * @author Ethan
 * @description project 业务层
 * @date 2024-07-08 20:46:21
 */
public interface ProjectService extends IService<Project> {

    /**
     * 分页查询全部project数据
     *
     * @param query 查询条件
     * @return 分页查询project结果
     */
    PageData<Project> getAll(ProjectQuery query);

    /**
     * 根据主键查询project
     *
     * @param id 主键
     * @return project查询结果
     */
    Project get(Integer id);

    /**
     * 插入或更新project
     *
     * @param project 保存对象
     */
    void saveProject(Project project);

    /**
     * 根据主键删除project
     *
     * @param id 主键
     */
    void delete(Integer id);

    /**
     * 获取全部ID，用于下拉框引用
     *
     * @return ID选项
     */
    List<SelectorOption> getAllId();

    /**
     * 根据Creator删除全部关联数据
     *
     * @param creatorId Creator
     */
    void deleteByCreatorId(Integer creatorId);

    /**
     * 查询project明細数据
     *
     * @param id 查询条件
     * @return 查询project结果
     */
    ProjectDetail getProjectDetail(Integer id);

    /**
     * 保存project明細数据
     */
    void saveProjectDetail(ProjectDetail detail);

    List<Project> getSummaryProject(List<Integer> projectIdList);

    Map<String, Integer> getReportMap();
}