package com.swsk.web;

import com.swsk.data.user.entity.User;
import com.swsk.data.util.generate.util.JdbcTemplateHelper;
import org.springframework.stereotype.Component;

/**
 * @author zzy
 * @Date 2020-03-25 19:22
 */
@Component
public class ThreadTest implements Runnable{

    @Override
    public void run() {
        User user = new User();
        user.setAccount(1+"");
        user.setName("test"+1);
        System.out.println(java.lang.Thread.currentThread().getName()+":"+1);
        try{
            System.out.println(java.lang.Thread.currentThread().getName()+":"+ JdbcTemplateHelper.insertAndGetEntity(user).toString());
        }catch (Exception e){
            System.out.println(e);
            e.getStackTrace();
        }
    }
}
