package com.swsk.data.util.generate.create;

/**
 * @author zzy
 * @Date 2018/12/26 2:12 PM
 */
public class CreateTest {


    public static void main(String[] args) {
        String FILE_PATH = "data/src/main/java/com/swsk/data/user/entity";
        String DATA_BASE_NAME = "fxjc";
        String URL = "192.168.1.226";
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String USER_NAME = "root";
        String PASSWORD = "swsk123456";

        //多个表用'',分隔
        String IN_TABLES = "'user'";
        //是否创建dao
        boolean IS_DAO = true;
        boolean IS_SERVICE = true;

        Config config = new Config();
        config.setFilePath(FILE_PATH);
        config.setDataBaseName(DATA_BASE_NAME);
        config.setUrl(URL);
        config.setPort("3306");
        config.setDriver(JDBC_DRIVER);
        config.setUser(USER_NAME);
        config.setPassword(PASSWORD);
        config.setInTables(IN_TABLES);
        config.setCreateDao(IS_DAO);
        config.setCreateService(IS_SERVICE);


        Generate generate = new Generate();
        generate.CreateBean(config);

        //generate.CreateDao("system_admin/src/main/java/com/system/bean","BusinessDutyLog");
        //generate.CreateService("system_admin/src/main/java/com/system/bean","BusinessDutyLog");

    }
}
