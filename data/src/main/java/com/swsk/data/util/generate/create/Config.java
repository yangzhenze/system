package com.swsk.data.util.generate.create;

/**
 * @author zzy
 * @Date 2018/12/26 3:30 PM
 */
public class Config {
    private String filePath;
    private String dataBaseName;
    private String url;
    private String port = "3306";
    private String driver = "com.mysql.jdbc.Driver";
    private String user;
    private String password;

    /**
     * 多个表用'',分隔
     */
    private String inTables;
    /**
     * 是否创建dao
     */
    private boolean isCreateDao = true;
    private boolean isCreateService = true;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInTables() {
        return inTables;
    }

    public void setInTables(String inTables) {
        this.inTables = inTables;
    }

    public boolean isCreateDao() {
        return isCreateDao;
    }

    public void setCreateDao(boolean createDao) {
        isCreateDao = createDao;
    }

    public boolean isCreateService() {
        return isCreateService;
    }

    public void setCreateService(boolean createService) {
        isCreateService = createService;
    }
}
