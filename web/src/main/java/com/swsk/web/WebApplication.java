package com.swsk.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.swsk")
public class WebApplication {

    public static void main(String[] args) {
        //SpringApplication.run(WebApplication.class, args);
        SpringApplication springApplication = new SpringApplication(WebApplication.class);
        springApplication.run(args);
    }

}
