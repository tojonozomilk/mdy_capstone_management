package com.project.management.controller;

import com.project.management.entity.User;
import com.project.management.pojo.LoginDto;
import com.project.management.pojo.Result;
import com.project.management.service.UserService;
import com.project.management.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ethan
 * @description system API接口
 * @date 2024-07-08 20:46:21
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private UserService userService;

    @GetMapping("login")
    public Result<Object> login(LoginDto dto, HttpServletRequest request) {
        User user = userService.login(dto);
        user.setPassword(null);
        request.getSession().setAttribute("user", user);
        return Result.success();
    }

    @GetMapping("getCurrentUser")
    public Result<User> getCurrentUser() {
        User user = SessionUtils.getUser();
        return Result.success(user);
    }

    @PostMapping("register")
    public Result<Object> register(@RequestBody User user) {
        userService.register(user);
        return Result.success();
    }

    @GetMapping("logout")
    public Result<Object> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return Result.success();
    }
}
