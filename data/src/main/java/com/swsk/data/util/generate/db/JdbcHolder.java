package com.swsk.data.util.generate.db;
import com.swsk.data.config.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author zzy
 * @Date 2020/4/24 2:28 下午
 */
public abstract class JdbcHolder {
    private static Logger log = LoggerFactory.getLogger(JdbcTemplateHelper.class);

    /**
     * 记录每条线程引用的jdbcTemplate
     */
    protected static final ThreadLocal<JdbcTemplate> jdbcHolder = new ThreadLocal<>();

    /**
     * 设置当前线程所引用的数据源
     * @param dataSource
     */
     public static void setDataSource(DataSource dataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        setJdbcHolder(jdbcTemplate);
        log.info("成功设置【{}】的数据源记录!",Thread.currentThread().getName());
        System.out.println("成功设置【"+Thread.currentThread().getName()+"】的数据源记录!");
    }

    /**
     * 删除当前线程记录的jdbcTemplate
     * 防止内存溢出
     */
    public static void destroy(){
        jdbcHolder.remove();
        log.info("成功销毁【{}】的数据源记录!",Thread.currentThread().getName());
        System.out.println("成功销毁【"+Thread.currentThread().getName()+"】的数据源记录!");
    }

    private static void setJdbcHolder(JdbcTemplate jdbcTemplate){
        jdbcHolder.set(jdbcTemplate);
    }

    /**
     * 获取JdbcTemplate对象
     * @return
     */
    public static JdbcTemplate getJdbcTemplate(){
        if(null == jdbcHolder.get()){
            return SpringUtils.applicationContext.getBean(JdbcTemplate.class);
        }
        return jdbcHolder.get();
    }


}
