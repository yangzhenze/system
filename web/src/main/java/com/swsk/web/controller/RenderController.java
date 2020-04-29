package com.swsk.web.controller;
import com.swsk.data.user.entity.User;
import com.swsk.data.util.generate.util.JdbcTemplateHelper;
import com.swsk.web.ThreadTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zzy
 * @Date 2020-03-10 17:27
 */
@Slf4j
@Controller
public class RenderController {


    @Autowired
    ThreadTest threadTest;

    @RequestMapping("/")
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView("index");

        for (int i = 1; i <2 ; i++) {
            java.lang.Thread thread1 = new java.lang.Thread(threadTest);
            thread1.start();
        }

        return modelAndView;
    }
}
