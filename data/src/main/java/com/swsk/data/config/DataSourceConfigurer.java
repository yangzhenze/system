package com.swsk.data.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.zzy.db.helper.SetDefaultJdbcTemplate;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * @author zzy
 * @Date 2020-04-03 11:26
 * 数据源配置
 */
@Configuration
public class DataSourceConfigurer {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DataSourceConfigurer.class);

    /**
     * 判断是否有匹配的配置文件
     * @return
     */
    @ConditionalOnProperty(prefix = "spring.datasource.druid.master",name = "url")
    @Primary
    @Bean(value = "dataSourceMaster")
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource dataSourceMaster(){
        log.info("Init DataSourceOne");
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 判断是否有匹配的配置文件
     * @return
     */
    @ConditionalOnProperty(prefix = "spring.datasource.druid.second",name = "url")
    @Bean(value = "dataSourceSecond")
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource dataSourceSecond(){
        log.info("Init DataSourceSecond");
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public SetDefaultJdbcTemplate setDefaultJdbcTemplate(){
        log.info("set default JdbcTemplate on JdbcTemplateHelper");
        return new SetDefaultJdbcTemplate();
    }
}
