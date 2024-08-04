package com.project.management.utils;

import com.project.management.entity.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Ethan
 * @description 登录用户 Session 工具
 * @date 2024-07-08 20:46:21
 */
public class SessionUtils {

    private SessionUtils() {
        throw new AssertionError();
    }

    /**
     * 获取用户id
     */
    public static Integer getUserId() {
        return getUser().getId();
    }

    /**
     * 获取登录用户
     */
    public static User getUser() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            HttpSession session = request.getSession(false);
            if (session != null) {
                return (User) session.getAttribute("user");
            }
        }

        throw new RuntimeException("redirect");
    }
}
