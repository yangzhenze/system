package com.swsk.web.config;

import com.swsk.web.util.HttpUtil;
import com.swsk.web.util.Ret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常统一处理
 * @author zzy
 * @Date 2020-02-27 16:01
 */
@RestControllerAdvice
@Slf4j
public class ControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public Object handleException(Exception e, HttpServletRequest request){
        log.error("异常:{}",e.getMessage(), e);
        if(HttpUtil.isRequestAjax(request)){
            return Ret.msgSetVal("异常:"+e.getMessage());
        }
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("msg",e.getMessage());
        return modelAndView;
    }
}
