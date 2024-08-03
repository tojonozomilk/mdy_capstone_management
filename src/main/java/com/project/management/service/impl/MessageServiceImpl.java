package com.project.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.management.entity.Message;
import com.project.management.entity.User;
import com.project.management.mapper.MessageMapper;
import com.project.management.pojo.MessageQuery;
import com.project.management.pojo.Notification;
import com.project.management.pojo.PageData;
import com.project.management.service.MessageService;
import com.project.management.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ethan
 * @description message 业务实现
 * @date 2024-07-08 20:46:21
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private UserService userService;

    @Override
    public PageData<Message> getAll(MessageQuery query) {
        // 构造查询条件
        Page<Message> pageRequest = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        // 设置查询字段
        wrapper.select(Message::getId,
                Message::getType,
                Message::getContent,
                Message::getSenderId,
                Message::getReceiverId,
                Message::getIsRead,
                Message::getCreateDate,
                Message::getAmendDate);

        // Type的查询条件
        String type = query.getType();
        if (type != null && !"".equals(type)) {
            wrapper.eq(Message::getType, type);
        }

        // Content的查询条件
        String content = query.getContent();
        if (content != null && !"".equals(content)) {
            wrapper.like(Message::getContent, content);
        }

        // Sender的查询条件
        Integer senderId = query.getSenderId();
        if (senderId != null) {
            wrapper.eq(Message::getSenderId, senderId);
        }

        // Receiver的查询条件
        Integer receiverId = query.getReceiverId();
        if (receiverId != null) {
            wrapper.eq(Message::getReceiverId, receiverId);
        }

        Page<Message> page = baseMapper.selectPage(pageRequest, wrapper);

        // 构造返回分页数据
        PageData<Message> pageData = new PageData<>();
        pageData.setPage((int) page.getCurrent());
        pageData.setSize((int) page.getSize());
        pageData.setTotal((int) page.getTotal());
        pageData.setContent(page.getRecords());

        return pageData;
    }

    @Override
    public Message get(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void saveMessage(Message message) {
        saveOrUpdate(message);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        // 根据ID获取数据，数据存在则删除
        Message message = this.get(id);
        if (message == null) {
            return;
        }

        baseMapper.deleteById(id);
    }

    @Override
    public void deleteBySenderId(Integer senderId) {
        // 根据删除条件查询全部数据
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Message::getId);
        wrapper.eq(Message::getSenderId, senderId);

        List<Message> messageList = baseMapper.selectList(wrapper);
        // 根据主键进行删除
        for (Message message : messageList) {
            this.delete(message.getId());
        }
    }

    @Override
    public void deleteByReceiverId(Integer receiverId) {
        // 根据删除条件查询全部数据
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Message::getId);
        wrapper.eq(Message::getReceiverId, receiverId);

        List<Message> messageList = baseMapper.selectList(wrapper);
        // 根据主键进行删除
        for (Message message : messageList) {
            this.delete(message.getId());
        }
    }

    @Override
    public List<Notification> getNotifications(Integer userId) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        //select * from message where receiver_id = 1 and (type = 'Notice' or (is_read = 0 and type = 'Chat')) order by create_date desc limit 20
        wrapper.eq(Message::getReceiverId, userId)
                .and(w -> w.eq(Message::getType, "Notice")
                        .or(subWrapper -> subWrapper.eq(Message::getIsRead, 0)
                                .eq(Message::getType, "Chat")))
                .orderByDesc(Message::getCreateDate)
                .last("limit 20");

        List<Message> messages = baseMapper.selectList(wrapper);

        return messages.stream()
                .map(message -> {
                    Notification notification = new Notification();
                    BeanUtils.copyProperties(message, notification);
                    Integer senderId = message.getSenderId();
                    if (senderId != null) {
                        User user = userService.get(senderId);
                        String username = user.getUsername();
                        notification.setSender(username);
                    }

                    return notification;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> getMessages(Integer senderId, Integer receiverId, Integer minId) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Message::getReceiverId, receiverId, senderId);
        wrapper.in(Message::getSenderId, receiverId, senderId);
        wrapper.eq(Message::getType, "Chat");
        if (minId != null) {
            wrapper.lt(Message::getId, minId);
        }
        wrapper.orderByDesc(Message::getId);
        wrapper.last("limit 10");
        List<Message> messages = baseMapper.selectList(wrapper);
        Collections.reverse(messages);

        List<Integer> unReadList = messages.stream()
                .filter(message -> !message.getIsRead() && !message.getSenderId().equals(receiverId))
                .map(Message::getId)
                .collect(Collectors.toList());
        if (!unReadList.isEmpty()) {
            LambdaUpdateWrapper<Message> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.in(Message::getId, unReadList);
            updateWrapper.set(Message::getIsRead, true);
            this.update(updateWrapper);
        }

        return messages;
    }

    @Override
    public void readNotification(Integer messageId) {
        LambdaUpdateWrapper<Message> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Message::getId, messageId);
        updateWrapper.set(Message::getIsRead, true);
        this.update(updateWrapper);
    }
}