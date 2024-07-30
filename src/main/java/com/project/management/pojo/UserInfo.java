package com.project.management.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private Integer userId;
    private String username;
    private String avatar;
    private String email;
    private String role;
    private String affiliation;
    private String preference;
}
