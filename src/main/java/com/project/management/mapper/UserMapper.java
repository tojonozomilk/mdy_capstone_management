package com.project.management.mapper;

import com.project.management.entity.User;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @description user Mapper
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}