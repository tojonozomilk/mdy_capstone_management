package com.project.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.management.entity.User;
import com.project.management.mapper.UserMapper;
import com.project.management.pojo.*;
import com.project.management.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ethan
 * @description user 业务实现
 * @date 2024-07-08 20:46:21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectManagerService projectManagerService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private MessageService messageService;

    @Override
    public PageData<User> getAll(UserQuery query) {
        // 构造查询条件
        Page<User> pageRequest = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        // 设置查询字段
        wrapper.select(User::getId,
                User::getUsername,
                User::getAvatar,
                User::getEmail,
                User::getRole,
                User::getAffiliation,
                User::getPreference,
                User::getCreateDate,
                User::getAmendDate);
        wrapper.orderByDesc(User::getAmendDate);

        // Username的查询条件
        String username = query.getUsername();
        if (username != null && !"".equals(username)) {
            wrapper.like(User::getUsername, username);
        }

        // Email的查询条件
        String email = query.getEmail();
        if (email != null && !"".equals(email)) {
            wrapper.like(User::getEmail, email);
        }

        // Role的查询条件
        String role = query.getRole();
        if (role != null && !"".equals(role)) {
            wrapper.eq(User::getRole, role);
        }

        // Affiliation的查询条件
        String affiliation = query.getAffiliation();
        if (affiliation != null && !"".equals(affiliation)) {
            wrapper.eq(User::getAffiliation, affiliation);
        }

        // Preference的查询条件
        String preference = query.getPreference();
        if (preference != null && !"".equals(preference)) {
            wrapper.eq(User::getPreference, preference);
        }

        Page<User> page = baseMapper.selectPage(pageRequest, wrapper);

        // 构造返回分页数据
        PageData<User> pageData = new PageData<>();
        pageData.setPage((int) page.getCurrent());
        pageData.setSize((int) page.getSize());
        pageData.setTotal((int) page.getTotal());
        pageData.setContent(page.getRecords());

        return pageData;
    }

    @Override
    public User get(Integer id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void saveUser(User user) {
        if (user.getRole() == null) {
            user.setRole("Student");
        }
        saveOrUpdate(user);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        // 根据ID获取数据，数据存在则删除
        User user = this.get(id);
        if (user == null) {
            return;
        }

        baseMapper.deleteById(id);
        // 删除关联的project数据
        projectService.deleteByCreatorId(id);
        // 删除关联的project manager数据
        projectManagerService.deleteByManagerId(id);
        // 删除关联的group数据
        groupService.deleteByAdminId(id);
        // 删除关联的group member数据
        groupMemberService.deleteByUserId(id);
        // 删除关联的message数据
        messageService.deleteBySenderId(id);
        // 删除关联的message数据
        messageService.deleteByReceiverId(id);
    }

    @Override
    public List<SelectorOption> getAllId() {
        // 查询全部ID
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getId, User::getUsername);
        List<User> userList = baseMapper.selectList(wrapper);

        return userList.stream()
                .map(item -> {
                    // 转换成前端下拉选项
                    SelectorOption option = new SelectorOption();
                    option.setValue(item.getId());
                    option.setLabel(item.getUsername());
                    return option;
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserInfo getUserInfo(Integer userId) {
        User user = this.get(userId);
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        userInfo.setUserId(user.getId());
        return userInfo;
    }

    @Override
    public User login(LoginDto dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        User user = this.getOne(wrapper);
        if (user == null) {
            throw new RuntimeException("Account not exists!");
        }

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Password incorrect!");
        }

        return user;
    }

    @Override
    public void register(User user) {
        String email = user.getEmail();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);

        if (this.count(wrapper) > 0) {
            throw new RuntimeException("Email exists!");
        }
        this.saveUser(user);
    }
}