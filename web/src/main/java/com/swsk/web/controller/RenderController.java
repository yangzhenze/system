package com.swsk.web.controller;
import com.swsk.web.ThreadTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zzy
 * @Date 2020-03-10 17:27
 */
@Slf4j
@Controller
public class RenderController {

    @RequestMapping("/")
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView("index");

        /*for (int i = 1; i <100 ; i++) {
            java.lang.Thread thread1 = new java.lang.Thread(new ThreadTest(i));
            thread1.start();
        }*/

        return modelAndView;
    }
}
