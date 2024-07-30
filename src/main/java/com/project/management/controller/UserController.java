package com.project.management.controller;

import com.project.management.entity.User;
import com.project.management.service.UserService;
import com.project.management.pojo.Result;
import com.project.management.pojo.PageData;
import com.project.management.pojo.UserQuery;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.management.pojo.SelectorOption;
import java.util.List;

/**
 * @description user API接口
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

	/**
	 * 分页查询全部user数据
	 *
	 * @param query 查询条件
	 * @return 分页查询user结果
	 */
    @GetMapping
    public Result<PageData<User>> getAll(UserQuery query) {
        PageData<User> result = service.getAll(query);
        return Result.success(result);
    }

	/**
	 * 根据主键查询user
	 *
	 * @param id 路径参数-主键
	 * @return user查询结果
	 */
    @GetMapping("{id}")
    public Result<User> get(@PathVariable Integer id) {
        User user = service.get(id);
        return Result.success(user);
    }

	/**
	 * 插入或更新user
	 *
	 * @param user 保存User对象
	 */
    @PostMapping
    public Result<User> save(@RequestBody User user) {
        service.saveUser(user);
        return Result.success();
    }

	/**
	 * 根据主键删除User
	 *
	 * @param id 主键
	 */
    @DeleteMapping("{id}")
    public Result<User> delete(@PathVariable Integer id) {
        service.delete(id);
        return Result.success();
    }

	/**
	 * 获取全部ID，用于下拉框引用
	 *
	 * @return ID选项
	 */
    @GetMapping("getAllId")
	public Result<List<SelectorOption>> getAllId() {
        List<SelectorOption> options = service.getAllId();
        return Result.success(options);
	}
}