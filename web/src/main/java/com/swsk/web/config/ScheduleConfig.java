package com.swsk.web.config;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author zzy
 * @Date 2019-08-16 11:09
 * 所有的定时任务都放在一个线程池中，定时任务启动时使用不同都线程。
 */
@Component
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //设定一个长度10的定时任务线程池
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(10);
        //taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
        taskRegistrar.setScheduler(scheduledThreadPoolExecutor);
    }
}
