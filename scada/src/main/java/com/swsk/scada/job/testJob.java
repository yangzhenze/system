package com.swsk.scada.job;

import com.swsk.data.user.entity.User;
import com.zzy.db.helper.JdbcTemplateHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzy
 * @Date 2020-03-12 18:26
 */
@Component
public class testJob {

    @Scheduled(cron = "0/1 * * * * ?")
    public void test(){
        List<User> users = new ArrayList();
        User user = new User();
        user.setId(1);
        users.add(user);
        JdbcTemplateHelper.batchSave(users);
    }
}
