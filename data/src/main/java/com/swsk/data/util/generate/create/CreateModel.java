/**
 *
 */
package com.swsk.data.util.generate.create;

import com.swsk.data.util.generate.util.Common;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yzz
 * @Date 2018/12/26 3:35 PM
 * 映射数据库，自动生成Entity
 */
public class CreateModel {
    protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(CreateModel.class);

    private Connection conn = null;
    private final String URL;
    private final String DB_URL;
    private final String JDBC_DRIVER;
    private final String USER_NAME;
    private final String PASSWORD;
    private final String DATABASENAME;
    private final String PACKAGENAME;
    private final String FILE_PATH;
    /**
     * 要生成的table
     */
    private final String IN_TABLES;
    private final boolean IS_DAO;
    private final boolean IS_SERVICE;

    public CreateModel(Config config) {
        this.URL = config.getUrl();
        this.DATABASENAME = config.getDataBaseName();
        this.DB_URL = "jdbc:mysql://" + this.URL + ":" + config.getPort() + "/" + this.DATABASENAME;
        this.JDBC_DRIVER = config.getDriver();
        this.USER_NAME = config.getUser();
        this.PASSWORD = config.getPassword();
        this.FILE_PATH = config.getFilePath();
        this.IS_DAO = config.isCreateDao();
        this.IS_SERVICE = config.isCreateService();
        this.PACKAGENAME = FILE_PATH.substring(FILE_PATH.indexOf("java") + 5, FILE_PATH.length()).replace("/", ".");
        this.IN_TABLES = config.getInTables();
    }


    /**
     * 获得连接
     *
     * @return
     */
    public boolean getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            logger.error("数据库连接失败！", e.getCause());
            return false;
        } catch (SQLException e) {
            logger.error("数据库连接失败！", e.getCause());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 关闭数据库
     */
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            logger.error("关闭数据库连接失败！");
            e.printStackTrace();
        }
    }

    /**
     * 生成字段静态声明
     *
     * @param tableName
     * @return
     */
    private String CreateEntityString(String tableName) {
        String result = "package " + PACKAGENAME + ";\n\n";
        result += "import java.io.Serializable;\n" +
                "import " + Common.importPath + "anotation.Table;\n" +
                "import " + Common.importPath + "anotation.Column;\n" +
                "import lombok.Data;\n";

        String sql = "SELECT COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT,COLUMN_KEY FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '" + DATABASENAME + "' AND table_name='"
                + tableName + "';";
        String fieldStr = "";

        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                FieldMeta fieldMeta = new FieldMeta();
                fieldMeta.setFieldName(toFieldName(resultSet.getString(1)));
                fieldMeta.setFieldDataType(resultSet.getString(2));
                fieldMeta.setFieldComment(resultSet.getString(3));

                if (fieldMeta.getFieldComment() != "" || null != fieldMeta.getFieldComment()) {
                    fieldStr += "    /**\n";
                    fieldStr += "     *" + fieldMeta.getFieldComment() + "\n";
                    fieldStr += "     */\n";
                }

                if (resultSet.getString(4).equals("PRI")) {
                    fieldStr += "    @Column(name=\"" + resultSet.getString(1) + "\",isPK = true)\n";
                } else {
                    fieldStr += "    @Column(name=\"" + resultSet.getString(1) + "\")\n";
                }
                fieldStr += "    private " + fieldMeta.getFieldDataType() + " " + fieldMeta.getFieldName() + ";\n\n";
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        result += "/**\n";
        result += " * @author zzy\n";
        result += " * @Date " + DateFormatUtils.format(new Date(), "yyyy/mm/dd HH:mm:ss") + "\n";
        result += " */\n";
        result += "@Data\n";
        result += "@Table(name=\"" + tableName + "\")\n";
        result += "public class "
                + toClassName(tableName)
                + " implements Serializable{\n\n";
        result += "    private static final long serialVersionUID = 1L;\n";
        result += fieldStr;
        result += "}";
        return result;
    }


    /**
     * 获得数据库的所有表名
     *
     * @return
     */
    public List<String> getAllTables() {
        String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '" + DATABASENAME + "'";

        if (null != IN_TABLES && "" != IN_TABLES) {
            sql += " AND TABLE_NAME in (" + IN_TABLES + ")";
        }

        try {
            List<String> result = new ArrayList<String>();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                if (resultSet.getString(1) != null
                        && !resultSet.getString(1).isEmpty()) {
                    result.add(resultSet.getString(1));
                }
            }

            return result;
        } catch (Exception e) {
            logger.error("查询数据库表失败！");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成Entity
     */
    public void CreateEntity() {
        if (getConnection()) {
            try {
                List<String> tables = getAllTables();
                List<String> beanNames = new ArrayList<>();

                for (int i = 0; i < tables.size(); i++) {
                    File createFolder = new File(System.getProperty("user.dir")
                            + "/" + FILE_PATH.replace(".", "/"));
                    // 路径不存在，生成文件夹
                    if (!createFolder.exists()) {
                        createFolder.mkdirs();
                    }
                    String entityString = CreateEntityString(tables.get(i).trim());
                    String beanName = toClassName(tables.get(i));
                    File entity = new File(createFolder.getAbsolutePath() + "/"
                            + beanName
                            + ".java");

                    if (entity.exists()) {
                        entity.delete();
                    }
                    beanNames.add(beanName);
                    // 写入文件
                    BufferedWriter out = new BufferedWriter(new FileWriter(
                            entity, false));
                    out.write(entityString);
                    out.close();
                }

                closeConnection();
                logger.info("dao生成成功！");
                logger.debug("dao生成成功！");

                if (IS_DAO) {
                    CreateDao createDao = new CreateDao(FILE_PATH, beanNames.toString());
                    createDao.generation();
                }

                if (IS_SERVICE) {
                    CreateService createService = new CreateService(FILE_PATH, beanNames.toString());
                    createService.generation();
                }
            } catch (Exception e) {
                logger.error("dao生成失败！");
                e.printStackTrace();
            }
        }
    }

    private String toClassName(String str) {
        StringBuffer sb = new StringBuffer();
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);

                if (i == 0) {
                    sb.append(Character.toUpperCase(c));
                } else if (c == '_') {
                    ++i;
                    c = str.charAt(i);
                    sb.append(Character.toUpperCase(c));
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
        }

        return sb.toString();
    }

    private String toFieldName(String str) {
        StringBuffer sb = new StringBuffer();
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);

                if (i == 0) {
                    sb.append(Character.toLowerCase(c));
                } else if (c == '_') {
                    ++i;
                    c = str.charAt(i);
                    sb.append(Character.toUpperCase(c));
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
        }

        return sb.toString();
    }

}
