package com.project.management.service;

import com.project.management.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.management.pojo.*;

import java.util.List;

/**
 * @description user 业务层
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
public interface UserService extends IService<User> {

	/**
	 * 分页查询全部user数据
	 *
	 * @param query 查询条件
	 * @return 分页查询user结果
	 */
    PageData<User> getAll(UserQuery query);

	/**
	 * 根据主键查询user
	 *
	 * @param id 主键
	 * @return user查询结果
	 */
    User get(Integer id);

	/**
	 * 插入或更新user
	 *
	 * @param user 保存对象
	 */
    void saveUser(User user);

	/**
	 * 根据主键删除user
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

    UserInfo getUserInfo(Integer userId);

	User login(LoginDto dto);

	void register(User user);
}