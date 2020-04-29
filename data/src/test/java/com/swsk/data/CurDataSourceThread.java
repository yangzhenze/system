package com.swsk.data;

import com.alibaba.druid.pool.DruidDataSource;
import com.swsk.data.config.SpringUtils;
import com.swsk.data.util.generate.db.JdbcHolder;
import com.swsk.data.util.generate.db.JdbcTemplateHelper;

import javax.sql.DataSource;

/**
 * @author zzy
 * @Date 2020-04-03 18:02
 */
public class CurDataSourceThread implements Runnable {

    @Override
    public void run(){
        System.out.println(Thread.currentThread()+"初始:"+((DruidDataSource) JdbcTemplateHelper.getJdbcTemplate().getDataSource()).getUrl());
        Object second = SpringUtils.applicationContext.getBean("dataSourceSecond");
        JdbcTemplateHelper.setDataSource((DataSource) second);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("week up");
        System.out.println(Thread.currentThread()+"最后:"+((DruidDataSource)JdbcTemplateHelper.getJdbcTemplate().getDataSource()).getUrl());
        JdbcTemplateHelper.removeDataSource();
    }

}
