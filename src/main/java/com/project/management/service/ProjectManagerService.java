package com.project.management.service;

import com.project.management.entity.ProjectManager;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.management.pojo.PageData;
import com.project.management.pojo.ProjectManagerQuery;
import com.project.management.pojo.SelectorOption;

import java.util.List;

/**
 * @author Ethan
 * @description project manager 业务层
 * @date 2024-07-08 20:46:21
 */
public interface ProjectManagerService extends IService<ProjectManager> {

    /**
     * 分页查询全部project manager数据
     *
     * @param query 查询条件
     * @return 分页查询project manager结果
     */
    PageData<ProjectManager> getAll(ProjectManagerQuery query);

    /**
     * 根据主键查询project manager
     *
     * @param id 主键
     * @return project manager查询结果
     */
    ProjectManager get(Integer id);

    /**
     * 插入或更新project manager
     *
     * @param projectManager 保存对象
     */
    void saveProjectManager(ProjectManager projectManager);

    /**
     * 根据主键删除project manager
     *
     * @param id 主键
     */
    void delete(Integer id);

    /**
     * 根据Project删除全部关联数据
     *
     * @param projectId Project
     */
    void deleteByProjectId(Integer projectId);

    /**
     * 根据Manager删除全部关联数据
     *
     * @param managerId Manager
     */
    void deleteByManagerId(Integer managerId);

    /**
     * 添加管理員
     *
     * @param projectId 項目id
     * @param managerId 管理員id
     * @return 添加結果
     */
    boolean addProjectManager(Integer projectId, Integer managerId);

    /**
     * 刪除管理員
     *
     * @param projectId 項目id
     * @param managerId 管理員id
     */
    void removeProjectManager(Integer projectId, Integer managerId);
}