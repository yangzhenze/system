package com.swsk.data;
import com.swsk.data.config.SpringUtils;
import com.swsk.data.user.dao.IUserDao;
import com.swsk.data.user.dao.UserRepository;
import com.swsk.data.user.entity.User;
import com.swsk.data.user.entity.User2;
import com.swsk.data.user.service.IUserService;
import com.zzy.db.helper.JdbcTemplateHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataApplicationTests {

    @Autowired
    IUserService userService;
    @Autowired
    IUserDao userDao;

    @Autowired
    UserRepository userRepository;


    @Test
    public void contextLoads() {
        List<User2> users = userRepository.findAllBy();
        /*User user = new User();
        user.setAccount("admin");
        user.setProvinceCode("001");
        List<User> users = userDao.findList(user);

        users.forEach( x->{
            System.out.println(x.toString());
        });*/

        String [] dataName = SpringUtils.applicationContext.getBeanNamesForType(DataSource.class);
        for (String s : dataName) {
            System.out.println(s);
        }


    }

    @Test
    public void add() throws InterruptedException {
        /*User user = new User();
        user.setAccount("123");
        user.setName("test1");
        user.setCreateDate(new Date());
        System.out.println(JdbcTemplateHelper.insertAndGetEntity(user));*/

        for (int i = 0; i < 10; i++) {
            CurDataSourceThread curDataSourceThread = new CurDataSourceThread();
            Thread thread = new Thread(curDataSourceThread);
            thread.start();

            /*if(i == 5){
                Thread.sleep(3000);
                System.out.println("week up");
            }*/
        }

    }

    @Test
    public void findById(){
        System.out.println(userService.getById(2).toString());
    }

    @Test
    public void del(){
        userService.deleteByIds(1);
    }

    @Test
    public void select(){
        User user = new User();
        System.out.println(JdbcTemplateHelper.findEntity(user));
    }

    @Test
    public void getBean(){
        String [] beans = SpringUtils.applicationContext.getBeanNamesForType(DataSource.class);
        for (String bean : beans) {
            System.out.println(bean);
        }
    }

    @Test
    public void getUseDataSource(){
        List<Map<String,Object>> mapOne =  JdbcTemplateHelper.select("select * from user");
        System.out.println("mapOne================================================================");
        mapOne.forEach(x->{
            System.out.println(x);
        });

        Object second = SpringUtils.applicationContext.getBean("dataSourceSecond");
        JdbcTemplateHelper.setDataSource((DataSource)second);
        List<Map<String,Object>> mapTow =  JdbcTemplateHelper.select("select * from user");
        System.out.println("mapTow================================================================");
        mapTow.forEach(x->{
            System.out.println(x);
        });


        Object master = SpringUtils.applicationContext.getBean("dataSourceMaster");
        JdbcTemplateHelper.setDataSource((DataSource)master);
        List<Map<String,Object>> mapThree =  JdbcTemplateHelper.select("select * from user");
        System.out.println("mapTow================================================================");
        mapThree.forEach(x->{
            System.out.println(x);
        });

    }

    @Test
    public void batch() throws IllegalAccessException {
        List<User> users = new ArrayList();

        List<Object[]> args = new ArrayList();

        for (int i = 0; i < 100; i++) {
            Object [] b = new Object[1];
            b[0] = i;
            args.add(b);
            User user = new User();
            user.setAccount(i+"");
            users.add(user);
        }

        Long startDate = System.currentTimeMillis();
        JdbcTemplateHelper.getJdbcTemplate().batchUpdate("insert into user (account) values(?)",args);
        System.out.println("jdbc用时："+(System.currentTimeMillis()-startDate)/1000);

        Long startDate2 = System.currentTimeMillis();
        JdbcTemplateHelper.batchSave(users);
        System.out.println("jdbc2用时："+(System.currentTimeMillis()-startDate2)/1000);

        User user = new User();
        user.setAccount("321");
        users.add(user);
        System.out.println(JdbcTemplateHelper.insertAndGetEntity(user).toString());


        /*GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        JdbcTemplateHelper.jdbcTemplate.update("INSERT INTO `fxjc`.`user`(`account`, `password`) VALUES ('admin', 'e10adc3949ba59abbe56e057f20f883e');",keyHolder);
        System.out.println(keyHolder.getKey().longValue());*/

    }

    public static void main(String[] args) {
    }





}
