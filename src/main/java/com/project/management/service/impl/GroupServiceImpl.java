package com.project.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.management.entity.*;
import com.project.management.mapper.GroupMapper;
import com.project.management.pojo.GroupQuery;
import com.project.management.pojo.PageData;
import com.project.management.pojo.SelectorOption;
import com.project.management.service.*;
import com.project.management.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ethan
 * @description group 业务实现
 * @date 2024-07-08 20:46:21
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {

    @Autowired
    private ProjectManagerService projectManagerService;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private MessageService messageService;

    @Override
    public PageData<Group> getAll(GroupQuery query) {
        // 构造查询条件
        Page<Group> pageRequest = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<>();
        // 设置查询字段
        wrapper.select(Group::getId,
                Group::getName,
                Group::getAdminId,
                Group::getProjectId,
                Group::getCreateDate,
                Group::getAmendDate);
        wrapper.orderByDesc(Group::getAmendDate);

        // Group Name的查询条件
        String name = query.getName();
        if (name != null && !"".equals(name)) {
            wrapper.like(Group::getName, name);
        }

        // Admin的查询条件
        Integer adminId = query.getAdminId();
        if (adminId != null) {
            wrapper.eq(Group::getAdminId, adminId);
        }

        // Project的查询条件
        Integer projectId = query.getProjectId();
        if (projectId != null) {
            wrapper.eq(Group::getProjectId, projectId);
        }

        Page<Group> page = baseMapper.selectPage(pageRequest, wrapper);

        // 构造返回分页数据
        PageData<Group> pageData = new PageData<>();
        pageData.setPage((int) page.getCurrent());
        pageData.setSize((int) page.getSize());
        pageData.setTotal((int) page.getTotal());
        pageData.setContent(page.getRecords());

        return pageData;
    }

    @Override
    public Group get(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void saveGroup(Group group) {
        boolean insert = group.getAdminId() == null;
        if (insert) {
            group.setAdminId(SessionUtils.getUserId());
        }
        saveOrUpdate(group);
        if (insert) {
            GroupMember groupMember = new GroupMember();
            groupMember.setUserId(group.getAdminId());
            groupMember.setGroupId(group.getId());
            groupMemberService.saveGroupMember(groupMember);
        } else {
            LambdaQueryWrapper<GroupMember> groupMemberQueryWrapper = new LambdaQueryWrapper<>();
            groupMemberQueryWrapper.in(GroupMember::getGroupId, group.getId());
            groupMemberQueryWrapper.select(GroupMember::getUserId);
            List<Integer> userIdList = groupMemberService.list(groupMemberQueryWrapper)
                    .stream()
                    .map(GroupMember::getUserId)
                    .collect(Collectors.toList());

            userIdList.remove(SessionUtils.getUserId());
            for (Integer userId : userIdList) {
                Message message = new Message();
                message.setType("Notice");
                message.setContent("Your Group \"" + group.getName() + "\" has been updated");
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
        Group group = this.get(id);
        if (group == null) {
            return;
        }

        baseMapper.deleteById(id);
        // 删除关联的group member数据
        groupMemberService.deleteByGroupId(id);
    }

    @Override
    public List<SelectorOption> getAllId() {
        // 查询全部ID
        LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Group::getId, Group::getName);
        List<Group> groupList = baseMapper.selectList(wrapper);

        return groupList.stream()
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
    public void deleteByAdminId(Integer adminId) {
        // 根据删除条件查询全部数据
        LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Group::getId);
        wrapper.eq(Group::getAdminId, adminId);

        List<Group> groupList = baseMapper.selectList(wrapper);
        // 根据主键进行删除
        for (Group group : groupList) {
            this.delete(group.getId());
        }
    }

    @Override
    public void deleteByProjectId(Integer projectId) {
        // 根据删除条件查询全部数据
        LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Group::getId);
        wrapper.eq(Group::getProjectId, projectId);

        List<Group> groupList = baseMapper.selectList(wrapper);
        // 根据主键进行删除
        for (Group group : groupList) {
            this.delete(group.getId());
        }
    }

    @Override
    public boolean addProjectGroup(Integer projectId, Integer groupId) {
        Integer userId = SessionUtils.getUserId();
        LambdaQueryWrapper<ProjectManager> projectManagerWrapper = new LambdaQueryWrapper<>();
        projectManagerWrapper.eq(ProjectManager::getProjectId, projectId);
        projectManagerWrapper.eq(ProjectManager::getManagerId, userId);
        boolean isManager = projectManagerService.count(projectManagerWrapper) > 0;

        Group group = this.get(groupId);
        Project project = projectService.get(projectId);
        List<SelectorOption> groupMember = groupMemberService.getGroupMember(groupId);
        if (isManager) {
            LambdaUpdateWrapper<Group> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Group::getId, groupId);
            updateWrapper.isNull(Group::getProjectId);
            updateWrapper.set(Group::getProjectId, projectId);
            this.update(updateWrapper);
            for (SelectorOption option : groupMember) {
                Message message = new Message();
                message.setType("Notice");
                message.setContent("Your Group " + group.getName() + " have successfully joined Project " + project.getName());
                message.setReceiverId((Integer) option.getValue());
                message.setIsRead(false);
                messageService.saveMessage(message);
            }
        } else {
            Approval approval = new Approval();
            approval.setType("Project");
            approval.setStatus("Init");
            approval.setProjectId(projectId);
            approval.setGroupId(groupId);
            approval.setCreatorId(userId);
            approval.setRemark("Group " + group.getName() + " wants to join Project " + project.getName());
            approvalService.saveApproval(approval);
        }

        return isManager;
    }

    @Override
    public void removeProjectGroup(Integer projectId, Integer groupId) {
        LambdaUpdateWrapper<Group> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Group::getId, groupId);
        updateWrapper.eq(Group::getProjectId, projectId);
        updateWrapper.set(Group::getProjectId, null);
        this.update(updateWrapper);
    }

    @Override
    public List<SelectorOption> getAvailableGroup() {
        LambdaQueryWrapper<Group> groupWrapper = new LambdaQueryWrapper<>();
        groupWrapper.isNull(Group::getProjectId);
        groupWrapper.select(Group::getId, Group::getName);
        List<Group> groupList = this.list(groupWrapper);

        return groupList.stream()
                .map(item -> {
                    SelectorOption option = new SelectorOption();
                    option.setValue(item.getId());
                    option.setLabel(item.getName());
                    return option;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Group> getSummaryGroup(Integer userId) {
        LambdaQueryWrapper<GroupMember> groupMemberWrapper = new LambdaQueryWrapper<>();
        groupMemberWrapper.eq(GroupMember::getUserId, userId);
        groupMemberWrapper.select(GroupMember::getGroupId);

        List<Integer> groupIdList = groupMemberService.list(groupMemberWrapper)
                .stream()
                .map(GroupMember::getGroupId)
                .distinct()
                .collect(Collectors.toList());

        if (groupIdList.isEmpty()) {
            return Collections.emptyList();
        }

        return baseMapper.selectBatchIds(groupIdList);
    }
}