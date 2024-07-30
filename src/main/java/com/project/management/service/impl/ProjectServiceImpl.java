package com.project.management.service.impl;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.management.entity.*;
import com.project.management.mapper.ProjectMapper;
import com.project.management.pojo.PageData;
import com.project.management.pojo.ProjectDetail;
import com.project.management.pojo.ProjectQuery;
import com.project.management.pojo.SelectorOption;
import com.project.management.service.*;
import com.project.management.utils.SessionUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

/**
 * @author Ethan
 * @description project 业务实现
 * @date 2024-07-08 20:46:21
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Autowired
    private ProjectManagerService projectManagerService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private ProjectService projectService;

    @Override
    public PageData<Project> getAll(ProjectQuery query) {
        // Query condition
        Page<Project> pageRequest = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        // fields
        wrapper.select(Project::getId,
                Project::getName,
                Project::getCategory,
                Project::getCreatorId,
                Project::getCreateDate,
                Project::getAmendDate);
        wrapper.orderByDesc(Project::getAmendDate);

        // by Project Name
        String name = query.getName();
        if (name != null && !"".equals(name)) {
            wrapper.like(Project::getName, name);
        }

        // by Category
        String category = query.getCategory();
        if (category != null && !"".equals(category)) {
            wrapper.eq(Project::getCategory, category);
        }

        // by Creator
        Integer creatorId = query.getCreatorId();
        if (creatorId != null) {
            wrapper.eq(Project::getCreatorId, creatorId);
        }

        Page<Project> page = baseMapper.selectPage(pageRequest, wrapper);

        // Setting PageData
        PageData<Project> pageData = new PageData<>();
        pageData.setPage((int) page.getCurrent());
        pageData.setSize((int) page.getSize());
        pageData.setTotal((int) page.getTotal());
        pageData.setContent(page.getRecords());

        return pageData;
    }

    @Override
    public Project get(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    @Transactional
    public void saveProject(Project project) {
        boolean insert = project.getCreatorId() == null;
        if (insert) {
            project.setCreatorId(SessionUtils.getUserId());
        }
        saveOrUpdate(project);

        if (insert) {
            ProjectManager projectManager = new ProjectManager();
            projectManager.setProjectId(project.getId());
            projectManager.setManagerId(project.getCreatorId());
            projectManagerService.saveProjectManager(projectManager);
        } else {
            LambdaQueryWrapper<ProjectManager> projectManagerQueryWrapper = new LambdaQueryWrapper<>();
            projectManagerQueryWrapper.eq(ProjectManager::getProjectId, project.getId());
            projectManagerQueryWrapper.select(ProjectManager::getManagerId);
            Set<Integer> userIdSet = projectManagerService.list(projectManagerQueryWrapper)
                    .stream()
                    .map(ProjectManager::getManagerId)
                    .collect(Collectors.toSet());

            LambdaQueryWrapper<Group> groupQueryWrapper = new LambdaQueryWrapper<>();
            groupQueryWrapper.eq(Group::getProjectId, project.getId());
            groupQueryWrapper.select(Group::getId);
            List<Integer> groupIdList = groupService.list(groupQueryWrapper)
                    .stream()
                    .map(Group::getId)
                    .collect(Collectors.toList());
            if (!groupIdList.isEmpty()) {
                LambdaQueryWrapper<GroupMember> groupMemberQueryWrapper = new LambdaQueryWrapper<>();
                groupMemberQueryWrapper.in(GroupMember::getGroupId, groupIdList);
                groupMemberQueryWrapper.select(GroupMember::getUserId);
                List<Integer> userIdList = groupMemberService.list(groupMemberQueryWrapper)
                        .stream()
                        .map(GroupMember::getUserId)
                        .collect(Collectors.toList());
                userIdSet.addAll(userIdList);
            }

            userIdSet.remove(SessionUtils.getUserId());
            for (Integer userId : userIdSet) {
                Message message = new Message();
                message.setType("Notice");
                message.setContent("Your Project \"" + project.getName() + "\" has been updated");
                message.setReceiverId(userId);
                message.setIsRead(false);
                messageService.saveMessage(message);
            }
        }
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        // 根据ID获取数据，数据存在则删除
        Project project = this.get(id);
        if (project == null) {
            return;
        }

        baseMapper.deleteById(id);
        // 删除关联的project manager数据
        projectManagerService.deleteByProjectId(id);
        // 删除关联的group数据
        groupService.deleteByProjectId(id);
    }

    @Override
    public List<SelectorOption> getAllId() {
        // 查询全部ID
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Project::getId, Project::getName);
        List<Project> projectList = baseMapper.selectList(wrapper);

        return projectList.stream()
                .map(item -> {
                    // 转换成前端下拉选项
                    SelectorOption option = new SelectorOption();
                    option.setValue(item.getId());
                    option.setLabel(item.getName());
                    return option;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByCreatorId(Integer creatorId) {
        // 根据删除条件查询全部数据
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Project::getId);
        wrapper.eq(Project::getCreatorId, creatorId);

        List<Project> projectList = baseMapper.selectList(wrapper);
        // 根据主键进行删除
        for (Project project : projectList) {
            this.delete(project.getId());
        }
    }

    @Override
    public ProjectDetail getProjectDetail(Integer id) {
        Project project = this.get(id);
        if (project == null) {
            return null;
        }

        ProjectDetail projectDetail = new ProjectDetail();
        BeanUtils.copyProperties(project, projectDetail);

        // get group list
        LambdaQueryWrapper<Group> groupWrapper = new LambdaQueryWrapper<>();
        groupWrapper.eq(Group::getProjectId, id);
        groupWrapper.select(Group::getId, Group::getName);
        List<SelectorOption> groupList = groupService.list(groupWrapper)
                .stream()
                .map(item -> {
                    SelectorOption option = new SelectorOption();
                    option.setValue(item.getId());
                    option.setLabel(item.getName());
                    return option;
                })
                .collect(Collectors.toList());
        projectDetail.setGroupList(groupList);

        // get manager list
        LambdaQueryWrapper<ProjectManager> managerWrapper = new LambdaQueryWrapper<>();
        managerWrapper.eq(ProjectManager::getProjectId, id);
        managerWrapper.select(ProjectManager::getManagerId);
        List<SelectorOption> managerList = projectManagerService.list(managerWrapper)
                .stream()
                .map(item -> {
                    User user = userService.get(item.getManagerId());
                    SelectorOption option = new SelectorOption();
                    option.setValue(user.getId());
                    option.setLabel(user.getUsername());
                    return option;
                })
                .collect(Collectors.toList());
        projectDetail.setManagerList(managerList);

        return projectDetail;
    }

    @Override
    @Transactional
    public void saveProjectDetail(ProjectDetail detail) {
        // save base info
        Integer projectId = detail.getId();
        this.saveProject(detail);

        // update group detail
        LambdaQueryWrapper<Group> groupWrapper = new LambdaQueryWrapper<>();
        groupWrapper.eq(Group::getProjectId, projectId);
        List<Group> oldGroupList = groupService.list(groupWrapper);
        for (Group group : oldGroupList) {
            group.setProjectId(null);
        }
        groupService.saveBatch(oldGroupList);

        List<SelectorOption> groupList = detail.getGroupList();
        List<Object> groupIdList = groupList.stream()
                .map(SelectorOption::getValue)
                .collect(Collectors.toList());

        LambdaQueryWrapper<Group> groupUpdateWrapper = new LambdaQueryWrapper<>();
        groupUpdateWrapper.in(Group::getId, groupIdList);
        Group updateGroup = new Group();
        updateGroup.setProjectId(projectId);
        groupService.update(updateGroup, groupUpdateWrapper);

        // update manager detail
        LambdaQueryWrapper<ProjectManager> managerWrapper = new LambdaQueryWrapper<>();
        managerWrapper.eq(ProjectManager::getProjectId, projectId);
        projectManagerService.remove(managerWrapper);

        List<SelectorOption> managerList = detail.getManagerList();
        List<ProjectManager> managerUpdateList = managerList.stream()
                .map(item -> {
                    ProjectManager projectManager = new ProjectManager();
                    projectManager.setProjectId(projectId);
                    projectManager.setManagerId((Integer) item.getValue());
                    return projectManager;
                })
                .collect(Collectors.toList());
        projectManagerService.saveBatch(managerUpdateList);
    }

    @Override
    public List<Project> getSummaryProject(List<Integer> projectIdList) {
        if (projectIdList.isEmpty()) {
            return Collections.emptyList();
        }

        List<Project> projectList = baseMapper.selectBatchIds(projectIdList);
        for (Project project : projectList) {
            project.setDescription(null);
        }

        return projectList;
    }

    @Override
    public Map<String, Integer> getReportMap() {
        Map<String, Integer> table = new HashMap<>();

        LambdaQueryWrapper<Project> wrapperProject = new LambdaQueryWrapper<>();
        wrapperProject.select(Project::getId, Project::getCategory);
        LambdaQueryWrapper<Group> wrapperGroup = new LambdaQueryWrapper<>();
        wrapperGroup.select(Group::getId, Group::getProjectId);
        LambdaQueryWrapper<User> wrapperUser = new LambdaQueryWrapper<>();
        wrapperUser.select(User::getId, User::getPreference, User::getRole).eq(User::getRole, "Student");
        List<Project> projects = projectService.list(wrapperProject);
        List<Group> groups = groupService.list(wrapperGroup);
        List<User> students = userService.list(wrapperUser);
        table.put("projectCount", projects.size());
        table.put("groupCount", groups.size());
        table.put("studentCount", students.size());
        table.put("projectAppCount", (int) projects.stream().filter(p -> p.getCategory().equals("Application")).count());
        table.put("projectResCount", (int) projects.stream().filter(p -> p.getCategory().equals("Research")).count());
        table.put("groupAllocatedCount", (int) groups.stream().filter(p -> p.getProjectId() > 0).count());
        table.put("studentAppCount", (int) students.stream().filter(stu -> stu.getPreference().equals("Application")).count());
        table.put("studentResCount", (int) students.stream().filter(stu -> stu.getPreference().equals("Research")).count());
        return table;
    }
}