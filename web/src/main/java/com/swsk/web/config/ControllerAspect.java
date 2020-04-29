package com.swsk.web.config;

import com.swsk.web.util.AspectUtil;
import com.swsk.web.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 路由访问查看
 * @author zzy
 * @Date 2019-07-18 15:25
 */
@Slf4j
@Aspect
@Component
public class ControllerAspect {

    @Pointcut(value = "execution(* com.swsk..*.*Controller.*(..))")
    public void Pointcut(){
    }

    @Around(value = "Pointcut()")
    public Object printInfo(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if(log.isDebugEnabled()){
            this.handle(proceedingJoinPoint);
        }
        return proceedingJoinPoint.proceed();
    }

    private void handle(ProceedingJoinPoint point) {
        try{
            Method currentMethod = AspectUtil.INSTANCE.getMethod(point);
            String className = AspectUtil.INSTANCE.getClassName(point);
            StringBuffer sb = new StringBuffer();
            sb.append("\n============================================================================");
            sb.append("====================Controller Begin=================================================");
            sb.append("===============================================\n");
            sb.append("==  Method: {} \n");
            sb.append("==  IP: {} \n");
            sb.append("==  URL: {} \n");
            sb.append("==  RequestMethod: {} \n");
            sb.append("==  Params: {} \n");
            sb.append("============================================================================");
            sb.append("====================Controller End=================================================");
            sb.append("===============================================\n");
            log.debug(sb.toString(),className+"."+currentMethod.getName()+"()",HttpUtil.getIp(),HttpUtil.getRequestUrl(), HttpUtil.getMethod(),HttpUtil.getRequestParams());
        }catch (NoSuchMethodException nsme){
            nsme.printStackTrace();
            log.error("控制层跟踪出错！ 异常信息: {}",nsme.getMessage(),nsme);
        }
    }
}
