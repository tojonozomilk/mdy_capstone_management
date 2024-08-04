package com.project.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.management.entity.Message;
import com.project.management.pojo.MessageQuery;
import com.project.management.pojo.Notification;
import com.project.management.pojo.PageData;

import java.util.List;

/**
 * @author Ethan
 * @description message 业务层
 * @date 2024-07-08 20:46:21
 */
public interface MessageService extends IService<Message> {

    /**
     * 分页查询全部message数据
     *
     * @param query 查询条件
     * @return 分页查询message结果
     */
    PageData<Message> getAll(MessageQuery query);

    /**
     * 根据主键查询message
     *
     * @param id 主键
     * @return message查询结果
     */
    Message get(Integer id);

    /**
     * 插入或更新message
     *
     * @param message 保存对象
     */
    void saveMessage(Message message);

    /**
     * 根据主键删除message
     *
     * @param id 主键
     */
    void delete(Integer id);

    /**
     * 根据Sender删除全部关联数据
     *
     * @param senderId Sender
     */
    void deleteBySenderId(Integer senderId);

    /**
     * 根据Receiver删除全部关联数据
     *
     * @param receiverId Receiver
     */
    void deleteByReceiverId(Integer receiverId);

    List<Notification> getNotifications(Integer userId);

    List<Message> getMessages(Integer senderId, Integer receiverId, Integer minId);

    void readNotification(Integer messageId);
}