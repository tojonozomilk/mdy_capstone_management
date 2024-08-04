package com.project.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.management.entity.*;
import com.project.management.mapper.GroupMemberMapper;
import com.project.management.pojo.GroupMemberQuery;
import com.project.management.pojo.PageData;
import com.project.management.pojo.SelectorOption;
import com.project.management.service.*;
import com.project.management.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ethan
 * @description group member 业务实现
 * @date 2024-07-08 20:46:21
 */
@Service
public class GroupMemberServiceImpl extends ServiceImpl<GroupMemberMapper, GroupMember> implements GroupMemberService {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private MessageService messageService;

    @Override
    public PageData<GroupMember> getAll(GroupMemberQuery query) {
        // 构造查询条件
        Page<GroupMember> pageRequest = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<GroupMember> wrapper = new LambdaQueryWrapper<>();
        // 设置查询字段
        wrapper.select(GroupMember::getId,
                GroupMember::getUserId,
                GroupMember::getGroupId,
                GroupMember::getCreateDate,
                GroupMember::getAmendDate);
        wrapper.orderByDesc(GroupMember::getAmendDate);

        // User的查询条件
        Integer userId = query.getUserId();
        if (userId != null) {
            wrapper.eq(GroupMember::getUserId, userId);
        }

        // Group的查询条件
        Integer groupId = query.getGroupId();
        if (groupId != null) {
            wrapper.eq(GroupMember::getGroupId, groupId);
        }

        Page<GroupMember> page = baseMapper.selectPage(pageRequest, wrapper);

        // 构造返回分页数据
        PageData<GroupMember> pageData = new PageData<>();
        pageData.setPage((int) page.getCurrent());
        pageData.setSize((int) page.getSize());
        pageData.setTotal((int) page.getTotal());
        pageData.setContent(page.getRecords());

        return pageData;
    }

    @Override
    public GroupMember get(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void saveGroupMember(GroupMember groupMember) {
        saveOrUpdate(groupMember);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        // 根据ID获取数据，数据存在则删除
        GroupMember groupMember = this.get(id);
        if (groupMember == null) {
            return;
        }

        baseMapper.deleteById(id);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        // 根据删除条件查询全部数据
        LambdaQueryWrapper<GroupMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(GroupMember::getId);
        wrapper.eq(GroupMember::getUserId, userId);

        List<GroupMember> groupMemberList = baseMapper.selectList(wrapper);
        // 根据主键进行删除
        for (GroupMember groupMember : groupMemberList) {
            this.delete(groupMember.getId());
        }
    }

    @Override
    public void deleteByGroupId(Integer groupId) {
        // 根据删除条件查询全部数据
        LambdaQueryWrapper<GroupMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(GroupMember::getId);
        wrapper.eq(GroupMember::getGroupId, groupId);

        List<GroupMember> groupMemberList = baseMapper.selectList(wrapper);
        // 根据主键进行删除
        for (GroupMember groupMember : groupMemberList) {
            this.delete(groupMember.getId());
        }
    }

    @Override
    public List<SelectorOption> getGroupMember(Integer groupId) {
        LambdaQueryWrapper<GroupMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(GroupMember::getUserId);
        wrapper.eq(GroupMember::getGroupId, groupId);

        List<GroupMember> groupMemberList = baseMapper.selectList(wrapper);
        return groupMemberList.stream()
                .map(item -> {
                    User user = userService.get(item.getUserId());
                    SelectorOption option = new SelectorOption();
                    option.setValue(user.getId());
                    option.setLabel(user.getUsername());
                    return option;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean addGroupMember(Integer groupId, Integer userId) {
        Integer currentUser = SessionUtils.getUserId();
        Group group = groupService.get(groupId);

        if (group.getAdminId().equals(currentUser)) {
            GroupMember groupMember = new GroupMember();
            groupMember.setUserId(userId);
            groupMember.setGroupId(groupId);
            this.saveGroupMember(groupMember);

            Message message = new Message();
            message.setType("Notice");
            message.setContent("You have successfully joined Group " + group.getName());
            message.setReceiverId(userId);
            message.setIsRead(false);
            messageService.saveMessage(message);

            return true;
        }

        Approval approval = new Approval();
        approval.setType("Group");
        approval.setStatus("Init");
        approval.setGroupId(groupId);
        approval.setCreatorId(userId);
        approval.setRemark("I want to join the group " + group.getName());
        approvalService.saveApproval(approval);

        Message message = new Message();
        message.setType("Notice");
        message.setContent("You are applying to join Group" + group.getName());
        message.setReceiverId(userId);
        message.setIsRead(false);
        messageService.saveMessage(message);

        return false;
    }

    @Override
    public void removeGroupMember(Integer groupId, Integer userId) {
        LambdaQueryWrapper<GroupMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GroupMember::getGroupId, groupId);
        wrapper.eq(GroupMember::getUserId, userId);
        baseMapper.delete(wrapper);
    }
}