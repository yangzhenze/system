package com.swsk.web;

import com.swsk.data.user.entity.User;
import com.zzy.db.helper.JdbcTemplateHelper;
import org.springframework.stereotype.Component;

/**
 * @author zzy
 * @Date 2020-03-25 19:22
 */
public class ThreadTest implements Runnable{


    int s = 0;

    public ThreadTest(int s){
        this.s = s;
    }

    @Override
    public void run() {
        User user = new User();
        user.setAccount(java.lang.Thread.currentThread().getName());
        user.setName(java.lang.Thread.currentThread().getName());
        System.out.println(java.lang.Thread.currentThread().getName());
        try{
            System.out.println(java.lang.Thread.currentThread().getName()+":"+ JdbcTemplateHelper.insertAndGetEntity(user).toString());
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
