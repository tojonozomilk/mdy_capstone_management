package com.project.management.mapper;

import com.project.management.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @description message Mapper
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}