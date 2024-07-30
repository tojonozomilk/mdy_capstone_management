package com.project.management.service.impl;

import java.util.Date;

import com.project.management.entity.Approval;
import com.project.management.entity.Message;
import com.project.management.entity.Project;
import com.project.management.entity.ProjectManager;
import com.project.management.pojo.PageData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.management.pojo.ProjectManagerQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.management.pojo.SelectorOption;
import com.project.management.service.MessageService;
import com.project.management.service.ProjectManagerService;
import com.project.management.mapper.ProjectManagerMapper;
import com.project.management.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ethan
 * @description project manager 业务实现
 * @date 2024-07-08 20:46:21
 */
@Service
public class ProjectManagerServiceImpl extends ServiceImpl<ProjectManagerMapper, ProjectManager> implements ProjectManagerService {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MessageService messageService;

    @Override
    public PageData<ProjectManager> getAll(ProjectManagerQuery query) {
        // 构造查询条件
        Page<ProjectManager> pageRequest = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<ProjectManager> wrapper = new LambdaQueryWrapper<>();
        // 设置查询字段
        wrapper.select(ProjectManager::getId,
                ProjectManager::getProjectId,
                ProjectManager::getManagerId,
                ProjectManager::getCreateDate,
                ProjectManager::getAmendDate);
        wrapper.orderByDesc(ProjectManager::getAmendDate);

        // Project的查询条件
        Integer projectId = query.getProjectId();
        if (projectId != null) {
            wrapper.eq(ProjectManager::getProjectId, projectId);
        }

        // Manager的查询条件
        Integer managerId = query.getManagerId();
        if (managerId != null) {
            wrapper.eq(ProjectManager::getManagerId, managerId);
        }

        Page<ProjectManager> page = baseMapper.selectPage(pageRequest, wrapper);

        // 构造返回分页数据
        PageData<ProjectManager> pageData = new PageData<>();
        pageData.setPage((int) page.getCurrent());
        pageData.setSize((int) page.getSize());
        pageData.setTotal((int) page.getTotal());
        pageData.setContent(page.getRecords());

        return pageData;
    }

    @Override
    public ProjectManager get(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void saveProjectManager(ProjectManager projectManager) {
        saveOrUpdate(projectManager);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        // 根据ID获取数据，数据存在则删除
        ProjectManager projectManager = this.get(id);
        if (projectManager == null) {
            return;
        }

        baseMapper.deleteById(id);
    }

    @Override
    public void deleteByProjectId(Integer projectId) {
        // 根据删除条件查询全部数据
        LambdaQueryWrapper<ProjectManager> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(ProjectManager::getId);
        wrapper.eq(ProjectManager::getProjectId, projectId);

        List<ProjectManager> projectManagerList = baseMapper.selectList(wrapper);
        // 根据主键进行删除
        for (ProjectManager projectManager : projectManagerList) {
            this.delete(projectManager.getId());
        }
    }

    @Override
    public void deleteByManagerId(Integer managerId) {
        // 根据删除条件查询全部数据
        LambdaQueryWrapper<ProjectManager> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(ProjectManager::getId);
        wrapper.eq(ProjectManager::getManagerId, managerId);

        List<ProjectManager> projectManagerList = baseMapper.selectList(wrapper);
        // 根据主键进行删除
        for (ProjectManager projectManager : projectManagerList) {
            this.delete(projectManager.getId());
        }
    }

    @Override
    public boolean addProjectManager(Integer projectId, Integer managerId) {
        ProjectManager projectManager = new ProjectManager();
        projectManager.setProjectId(projectId);
        projectManager.setManagerId(managerId);
        this.saveProjectManager(projectManager);

        Project project = projectService.get(projectId);
        Message message = new Message();
        message.setType("Notice");
        message.setContent("You have become the manager of Project " + project.getName());
        message.setReceiverId(managerId);
        message.setIsRead(false);
        messageService.saveMessage(message);

        return true;
    }

    @Override
    public void removeProjectManager(Integer projectId, Integer managerId) {
        LambdaQueryWrapper<ProjectManager> managerWrapper = new LambdaQueryWrapper<>();
        managerWrapper.eq(ProjectManager::getProjectId, projectId);
        managerWrapper.eq(ProjectManager::getManagerId, managerId);
        this.remove(managerWrapper);
    }
}