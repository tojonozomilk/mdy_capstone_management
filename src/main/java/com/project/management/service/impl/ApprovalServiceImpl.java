package com.project.management.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import com.project.management.entity.*;
import com.project.management.pojo.MyApprovalQuery;
import com.project.management.pojo.PageData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.management.pojo.ApprovalQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.management.service.*;
import com.project.management.mapper.ApprovalMapper;
import com.project.management.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ethan
 * @description approval 业务实现
 * @date 2024-07-08 20:46:21
 */
@Service
public class ApprovalServiceImpl extends ServiceImpl<ApprovalMapper, Approval> implements ApprovalService {

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private ProjectManagerService projectManagerService;

    @Autowired
    private MessageService messageService;

    @Override
    public PageData<Approval> getAll(ApprovalQuery query) {
        // 构造查询条件
        Page<Approval> pageRequest = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<Approval> wrapper = new LambdaQueryWrapper<>();
        // 设置查询字段
        wrapper.select(Approval::getId,
                Approval::getType,
                Approval::getStatus,
                Approval::getRemark,
                Approval::getCreatorId,
                Approval::getCreateDate,
                Approval::getAmendDate);
        wrapper.orderByDesc(Approval::getAmendDate);

        // Type的查询条件
        String type = query.getType();
        if (type != null && !"".equals(type)) {
            wrapper.eq(Approval::getType, type);
        }

        // Status的查询条件
        String status = query.getStatus();
        if (status != null && !"".equals(status)) {
            wrapper.eq(Approval::getStatus, status);
        }

        // Related的查询条件
        Integer relateId = query.getRelateId();
        if (relateId != null) {
            wrapper.eq(Approval::getGroupId, relateId);
        }

        // Remark的查询条件
        String remark = query.getRemark();
        if (remark != null && !"".equals(remark)) {
            wrapper.like(Approval::getRemark, remark);
        }

        Page<Approval> page = baseMapper.selectPage(pageRequest, wrapper);

        // 构造返回分页数据
        PageData<Approval> pageData = new PageData<>();
        pageData.setPage((int) page.getCurrent());
        pageData.setSize((int) page.getSize());
        pageData.setTotal((int) page.getTotal());
        pageData.setContent(page.getRecords());

        return pageData;
    }

    @Override
    public Approval get(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void saveApproval(Approval approval) {
        if (approval.getCreatorId() == null) {
            approval.setCreatorId(SessionUtils.getUserId());
        }
        saveOrUpdate(approval);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        // 根据ID获取数据，数据存在则删除
        Approval approval = this.get(id);
        if (approval == null) {
            return;
        }

        baseMapper.deleteById(id);
    }

    @Override
    public PageData<Approval> getApprovals(MyApprovalQuery query) {
        Integer userId = query.getUserId();

        LambdaQueryWrapper<ProjectManager> projectManagerWrapper = new LambdaQueryWrapper<>();
        projectManagerWrapper.eq(ProjectManager::getManagerId, userId);
        projectManagerWrapper.select(ProjectManager::getProjectId);
        Set<Integer> projectIdSet = projectManagerService.list(projectManagerWrapper)
                .stream()
                .map(ProjectManager::getProjectId)
                .collect(Collectors.toSet());

        LambdaQueryWrapper<Group> groupWrapper = new LambdaQueryWrapper<>();
        groupWrapper.eq(Group::getAdminId, userId);
        groupWrapper.select(Group::getId);
        Set<Integer> groupIdSet = groupService.list(groupWrapper)
                .stream()
                .map(Group::getId)
                .collect(Collectors.toSet());

        Page<Approval> pageRequest = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<Approval> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Approval::getId,
                Approval::getType,
                Approval::getStatus,
                Approval::getRemark,
                Approval::getCreatorId,
                Approval::getCreateDate,
                Approval::getAmendDate);
        if (groupIdSet.isEmpty()) {
            wrapper.eq(Approval::getCreatorId, userId);
        } else {
            wrapper.in(Approval::getProjectId, projectIdSet)
                    .or()
                    .in(Approval::getGroupId, groupIdSet)
                    .or()
                    .eq(Approval::getCreatorId, userId);
        }
        wrapper.orderByDesc(Approval::getAmendDate);

        Page<Approval> page = baseMapper.selectPage(pageRequest, wrapper);
        PageData<Approval> pageData = new PageData<>();
        pageData.setPage((int) page.getCurrent());
        pageData.setSize((int) page.getSize());
        pageData.setTotal((int) page.getTotal());
        pageData.setContent(page.getRecords());

        return pageData;
    }

    @Override
    @Transactional
    public void approve(Integer approvalId) {
        Approval approval = this.get(approvalId);
        if (!approval.getStatus().equals("Init")) {
            return;
        }

        String type = approval.getType();
        if (type.equals("Project")) {
            Integer groupId = approval.getGroupId();
            Integer projectId = approval.getProjectId();
            Group group = groupService.get(groupId);
            if (!group.getProjectId().equals(projectId)) {
                groupService.addProjectGroup(projectId, groupId);
            }
        } else if (type.equals("Group")) {
            Integer groupId = approval.getGroupId();
            Integer creatorId = approval.getCreatorId();
            LambdaQueryWrapper<GroupMember> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(GroupMember::getGroupId, groupId);
            queryWrapper.eq(GroupMember::getUserId, creatorId);
            if (groupMemberService.count(queryWrapper) == 0) {
                groupMemberService.addGroupMember(groupId, creatorId);
            }
        }

        approval.setStatus("Approved");
        this.saveApproval(approval);
    }

    @Override
    @Transactional
    public void reject(Integer approvalId) {
        Approval approval = this.get(approvalId);
        approval.setStatus("Reject");
        this.saveApproval(approval);

        Message message = new Message();
        message.setType("Notice");
        message.setContent("Your approval \"" + approval.getRemark() + "\" rejected");
        message.setReceiverId(approval.getCreatorId());
        message.setIsRead(false);
        messageService.saveMessage(message);
    }
}