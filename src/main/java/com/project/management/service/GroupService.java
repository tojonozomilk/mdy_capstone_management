package com.project.management.service;

import com.project.management.entity.Group;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.management.pojo.PageData;
import com.project.management.pojo.GroupQuery;

import java.util.List;

import com.project.management.pojo.SelectorOption;

/**
 * @author Ethan
 * @description group 业务层
 * @date 2024-07-08 20:46:21
 */
public interface GroupService extends IService<Group> {

    /**
     * 分页查询全部group数据
     *
     * @param query 查询条件
     * @return 分页查询group结果
     */
    PageData<Group> getAll(GroupQuery query);

    /**
     * 根据主键查询group
     *
     * @param id 主键
     * @return group查询结果
     */
    Group get(Integer id);

    /**
     * 插入或更新group
     *
     * @param group 保存对象
     */
    void saveGroup(Group group);

    /**
     * 根据主键删除group
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
     * 根据Admin删除全部关联数据
     *
     * @param adminId Admin
     */
    void deleteByAdminId(Integer adminId);

    /**
     * 根据Project删除全部关联数据
     *
     * @param projectId Project
     */
    void deleteByProjectId(Integer projectId);

    /**
     * 添加組
     *
     * @param projectId 項目id
     * @param groupId   組id
     * @return 添加結果
     */
    boolean addProjectGroup(Integer projectId, Integer groupId);


    /**
     * 刪除組
     *
     * @param projectId 項目id
     * @param groupId   組id
     */
    void removeProjectGroup(Integer projectId, Integer groupId);

    /**
     * 獲取可用組
     *
     * @return 可用組
     */
    List<SelectorOption> getAvailableGroup();

    List<Group> getSummaryGroup(Integer userId);
}