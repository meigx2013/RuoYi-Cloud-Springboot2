package com.ruoyi.report.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author meigx
 * @version 1.0
 * @description: 数据源配置类
 * @date 2026/1/31 20:06
 */
@Configuration
public class DataSourceConfig {

    /**
     * 1. bean的名称必须为 minidaoDataSource
     * 2. jeecg.minidao-datasource对应的是yml中jeecg下的minidao-datasource, 可自定义
     * @return
     */
    @Bean(name="minidaoDataSource")
    @ConfigurationProperties(prefix = "jeecg.minidao-datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

}
