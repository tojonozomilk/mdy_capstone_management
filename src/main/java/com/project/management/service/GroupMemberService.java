package com.project.management.service;

import com.project.management.entity.GroupMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.management.pojo.PageData;
import com.project.management.pojo.GroupMemberQuery;
import com.project.management.pojo.SelectorOption;

import java.util.List;

/**
 * @author Ethan
 * @description group member 业务层
 * @date 2024-07-08 20:46:21
 */
public interface GroupMemberService extends IService<GroupMember> {

    /**
     * 分页查询全部group member数据
     *
     * @param query 查询条件
     * @return 分页查询group member结果
     */
    PageData<GroupMember> getAll(GroupMemberQuery query);

    /**
     * 根据主键查询group member
     *
     * @param id 主键
     * @return group member查询结果
     */
    GroupMember get(Integer id);

    /**
     * 插入或更新group member
     *
     * @param groupMember 保存对象
     */
    void saveGroupMember(GroupMember groupMember);

    /**
     * 根据主键删除group member
     *
     * @param id 主键
     */
    void delete(Integer id);

    /**
     * 根据User删除全部关联数据
     *
     * @param userId User
     */
    void deleteByUserId(Integer userId);

    /**
     * 根据Group删除全部关联数据
     *
     * @param groupId Group
     */
    void deleteByGroupId(Integer groupId);

    /**
     * 獲取組成員
     *
     * @return 組成員
     */
    List<SelectorOption> getGroupMember(Integer groupId);

    /**
     * 添加組成員
     *
     * @return 組成員
     */
    boolean addGroupMember(Integer groupId, Integer userId);

    /**
     * 刪除組成員
     */
    void removeGroupMember(Integer groupId, Integer userId);
}