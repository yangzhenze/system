package com.swsk.scada.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动完成执行
 * @author zzy
 * @Date 2019-07-18 15:10
 */
@Slf4j
@Component
public class Runner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        log.info("项目启动OK！！！！！！");
    }
}
