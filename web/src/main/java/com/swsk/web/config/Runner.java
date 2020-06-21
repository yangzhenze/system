package com.swsk.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 项目启动完成执行
 * @author zzy
 * @Date 2019-07-18 15:10
 */
@Slf4j
// @Component
public class Runner implements ApplicationRunner {

    @Value("${server.port}")
    public String port;

    @Override
    public void run(ApplicationArguments args) {
        log.info("防汛决策智慧气象服务系统！http://localhost:"+port);
        openLocal();
    }

    /**
     * 项目启动后打开1个页面
     */
    public void openLocal() {
        String os = System.getProperty("os.name").toUpperCase();
        String command = null;

        if(os.contains("MAC")){
            command = Command.MAC.getCommand();
        }else if(os.contains("LINUX")){
            command = Command.LINUX.getCommand();
        }else if(os.contains("WINDOWS")){
            command = Command.WINDOWS.getCommand();
        }

        try {
            Runtime.getRuntime().exec(command+port);
        } catch (Exception ex) {
            log.info("命令打开浏览器失败！请手动打开！");
        }
    }

    enum Command{
        MAC("open  http://localhost:"),
        LINUX("x-www-browser  http://localhost:"),
        WINDOWS("cmd /c start  http://localhost:");

        private String command;

        Command(String command){
            this.command = command;
        }

        public String getCommand() {
            return command;
        }
    }
}
