package com.project.management.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;

/**
 * @description MyBatis-Plus Configuration
 * @author Ethan
 * @date 2024-07-08 20:46:21
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.project.management.mapper")
public class MybatisPlusConfig {

    /**
     * MyBatis-Plus Pagination
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * Fields fii-na
     */
    @Bean
    public DateHandler dateHandler() {
        return new DateHandler();
    }

    private static class DateHandler implements MetaObjectHandler {

        //  Fill-na strategy when insert
        @Override
        public void insertFill(MetaObject metaObject) {
            this.setFieldValByName("createDate", new Date(), metaObject);
            this.setFieldValByName("amendDate", new Date(), metaObject);
        }

        //  Fill-na strategy when update
        @Override
        public void updateFill(MetaObject metaObject) {
            this.setFieldValByName("amendDate", new Date(), metaObject);
        }
    }
}