package com.swsk.data.util.generate.db;

import com.swsk.data.util.generate.anotation.Column;
import com.swsk.data.util.generate.anotation.Table;
import com.swsk.data.util.generate.util.DateUtil;
import com.swsk.data.util.generate.util.JdbcTemplateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author zzy
 * @Date 2019-10-23 15:42
 */
public class ReflexUtil {

    private static Logger log = LoggerFactory.getLogger(JdbcTemplateHelper.class);
    /**
     * 设置一些操作的常量
     */
    public static final String SQL_SELECT = "SELECT";
    public static final String SQL_START = " *";
    public static final String SQL_INSERT = "INSERT";
    public static final String SQL_UPDATE = "UPDATE";
    public static final String SQL_DELETE = "DELETE";
    public static final String SQL_INSERT_OR_UPDATE = " ON DUPLICATE KEY UPDATE ";
    public static final String SQL_WHERE = " WHERE ";
    public static final String SQL_AND = " AND ";
    public static final String SQL_FROM = " FROM ";
    public static final String SQL_EMPTY = " ";
    public static final String SQL_LIMIT = " LIMIT ";
    public static final String SQL_IN = " IN";
    public static final String SQL_SELECT_FROM = SQL_SELECT + SQL_START + SQL_FROM;

    private static final Pattern ORDER_BY_PATTERN = Pattern.compile(
            "order\\s+by\\s+[^,\\s]+(\\s+asc|\\s+desc)?(\\s*,\\s*[^,\\s]+(\\s+asc|\\s+desc)?)*",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private static final Pattern QUESTION = Pattern.compile("\\?{1}");

    public static final Pattern PLACEHOLDER = Pattern.compile("[a-zA-Z0-9_-]+");

    /**
     * 获取主键
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> String getTablePk(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            try {
                // 暴力反射
                fields[i].setAccessible(true);

                if (fields[i].isAnnotationPresent(Column.class) && fields[i].getAnnotation(Column.class).isPK()) {
                    return fields[i].getAnnotation(Column.class).name();
                }
            } catch (Exception e) {
                log.error("获取主键时反射异常! 异常信息: {}",e);
            }
        }
        log.info("{}未定义主键", clazz.getName());
        return null;
    }

    /**
     * 根据Column.class注解中的name值获取该实体对应的属性值
     *
     * @param entity
     * @param colName
     * @return
     */
    public static Object getValueByColumnName(Object entity, String colName) {
        Field[] fields = entity.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            try {
                // 暴力反射
                fields[i].setAccessible(true);
                if (fields[i].isAnnotationPresent(Column.class) && fields[i].getAnnotation(Column.class).name().equals(colName)) {
                    return fields[i].get(entity);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("获取属性值异常! 异常信息: {}",e.getMessage(),e);
            }
        }
        return null;
    }


    public static void forDebuggingAndPrintingSQLLogs(String sql, Object[] args) {
        if(log.isDebugEnabled()){
            boolean flag = true;
            String allSql = sql;
            StringBuffer params = new StringBuffer();

            for (Object arg : args) {
                String paramStr = "'%s'";

                if (flag) {
                    flag = false;
                } else {
                    params.append(",");
                }

                if(arg instanceof Date){
                    paramStr = String.format(paramStr, DateUtil.formatDate((Date)arg,"yyyy-MM-dd HH:mm:ss"));
                }else if(arg instanceof Integer || arg instanceof Long || arg instanceof Float || arg instanceof Double || arg instanceof BigDecimal){
                    paramStr = arg.toString();
                }else{
                    paramStr = String.format(paramStr,arg.toString());
                }
                params.append(arg.toString());
                allSql = QUESTION.matcher(allSql).replaceFirst(paramStr);
            }
            StringBuffer sb = new StringBuffer();
            sb.append("\n============================================================================");
            sb.append("====================SQL BEGIN=================================================");
            sb.append("===============================================\n");
            sb.append("SQL:");
            sb.append(sql);
            sb.append("\n");
            sb.append("PARAMS:");
            sb.append(params.toString());
            sb.append("\n");
            sb.append("COMPLETE-SQL:");
            sb.append("\n");
            sb.append(allSql);
            sb.append("\n============================================================================");
            sb.append("====================SQL END=================================================");
            sb.append("===============================================\n");
            log.debug(sb.toString());
        }
    }

    /**
     * 组装SQL
     *
     * @param sqlFlag
     * @param entity
     * @return
     */
    public static <T> String makeSql(T entity, String sqlFlag) {
        // 验证是否为标准的bean,否则报错
        checkTableAnnotation(entity.getClass());
        checkColumnAnnotation(entity.getClass());
        StringBuffer sql = new StringBuffer();
        // 表名
        String tableName = getTableName(entity.getClass());
        // 主键
        String pkName = getTablePk(entity.getClass());
        boolean flag = false;
        Field[] fields = entity.getClass().getDeclaredFields();

        if (sqlFlag.equals(SQL_INSERT) || sqlFlag.equals(SQL_INSERT_OR_UPDATE)) {
            sql.append(SQL_INSERT + " INTO " + tableName);
            sql.append("(");

            try {
                String paramsStr = getColumnStr(entity);

                if (StringUtils.isEmpty(paramsStr)) {
                    Assert.isNull(false, "对象属性均为空，无法进行下一步操作");
                }
                // 占位字符
                String placeStr = ReflexUtil.PLACEHOLDER.matcher(paramsStr).replaceAll("?");
                sql.append(paramsStr);
                sql.append(") VALUES (");
                sql.append(placeStr);
                sql.append(")");

                if(sqlFlag.equals(SQL_INSERT_OR_UPDATE)){
                    sql.append(SQL_INSERT_OR_UPDATE);
                    sql.append(paramsStr.replaceAll(","," =?,")).append(" =?");
                }
            } catch (Exception e) {
                log.error("组装INSERT SQL时反射异常! 异常信息:",e);
            }


        } else if (sqlFlag.equals(SQL_UPDATE)) {
            sql.append(SQL_UPDATE + " " + tableName + " SET ");
            for (int i = 0; fields != null && i < fields.length; i++) {
                try {
                    // 暴力反射
                    fields[i].setAccessible(true);

                    // 判断属性空值并排除主键
                    if (null != fields[i].get(entity) && fields[i].isAnnotationPresent(Column.class) && !fields[i].getAnnotation(Column.class).isPK()) {
                        String column = fields[i].getAnnotation(Column.class).name();
                        if (flag) {
                            sql.append(",");
                        } else {
                            flag = true;
                        }
                        sql.append(column).append("=").append("?");
                    }
                } catch (Exception e) {
                    log.error("组装UPDATE SQL语句反射异常! 异常信息:",e);
                }
            }

            if (!flag) {
                Assert.isNull(false, "对象属性均为空，无法进行下一步操作");
            }
            sql.append(SQL_WHERE + pkName + "=?");
        } else if (sqlFlag.equals(SQL_DELETE)) {
            // 关联表无主键
            if (pkName==null) {
                sql.append(SQL_DELETE + SQL_FROM + tableName + SQL_WHERE);
                for (int i = 0; fields != null && i < fields.length; i++) {
                    try {
                        // 暴力反射
                        fields[i].setAccessible(true);

                        //属性是否为空
                        if (null != fields[i].get(entity) && fields[i].isAnnotationPresent(Column.class)) {
                            String column = fields[i].getAnnotation(Column.class).name();

                            if (flag) {
                                sql.append(SQL_AND);
                            } else {
                                flag = true;
                            }
                            sql.append(column).append("=?");
                        }
                    } catch (Exception e) {
                        log.error("组装INSERT SQL时反射异常! 异常信息: ",e);
                    }
                }
            } else {
                sql.append(SQL_DELETE + SQL_FROM + tableName + SQL_WHERE + pkName + "=?");
            }
        }else if(sqlFlag.equals(SQL_SELECT)){
            sql.append(SQL_SELECT_FROM + " " + tableName);
            for (int i = 0; fields != null && i < fields.length; i++) {
                try {
                    // 暴力反射
                    fields[i].setAccessible(true);

                    // 判断属性空值并排除主键
                    if (null != fields[i].get(entity) && fields[i].isAnnotationPresent(Column.class)) {
                        String column = fields[i].getAnnotation(Column.class).name();
                        if (flag) {
                            sql.append(SQL_AND);
                        } else {
                            sql.append(SQL_WHERE);
                            flag = true;
                        }
                        sql.append(column).append("=").append("?");
                    }
                } catch (Exception e) {
                    log.error("组装UPDATE SQL语句反射异常! 异常信息:",e);
                }
            }

            if (!flag) {
                Assert.isNull(false, "对象属性均为空，无法进行下一步操作");
            }
        }
        return sql.toString();

    }


    /**
     * 根据表字段获取实体的类型
     * @param entity
     * @param column
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public static <T> Type getColumnType(T entity, String column) throws IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();

        for (int i = 0; fields != null && i < fields.length; i++) {
            // 暴力反射
            fields[i].setAccessible(true);

            //属性是否为空
            if (null != fields[i].getAnnotation(Column.class) && fields[i].getAnnotation(Column.class).name().equals(column)) {
                return fields[i].getGenericType();
            }
        }
        return null;
    }


    /**
     * 根据表字段获取实体的值
     * @param entity
     * @param column
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public static <T> Object getColumnValue(T entity,String column) throws IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();

        for (int i = 0; fields != null && i < fields.length; i++) {
            // 暴力反射
            fields[i].setAccessible(true);

            //属性是否为空
            if (null != fields[i].getAnnotation(Column.class) && fields[i].getAnnotation(Column.class).name().equals(column)) {
                return fields[i].get(entity);
            }
        }
        return null;
    }


    /**
     * 获取Column.class注解的表字段
     * @param entity
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    private static <T> String getColumnStr(T entity) throws IllegalAccessException {
        Field[] fields = entity.getClass().getDeclaredFields();
        StringBuffer paramsStr = new StringBuffer();
        boolean flag = false;

        for (int i = 0; fields != null && i < fields.length; i++) {
            // 暴力反射
            fields[i].setAccessible(true);

            //属性是否为空
            if (null != fields[i].get(entity) && fields[i].isAnnotationPresent(Column.class)) {
                String column = fields[i].getAnnotation(Column.class).name();
                if (flag) {
                    paramsStr.append(",");
                } else {
                    flag = true;
                }
                paramsStr.append(column);
            }
        }
        return paramsStr.toString();
    }


    /**
     * 设置参数
     *
     * @param entity
     * @param sqlFlag
     * @return
     */
    public static <T> Object[] setArgs(T entity, String sqlFlag) {
        Field[] fields = entity.getClass().getDeclaredFields();
        List<Object> args = new ArrayList<>();

        if (sqlFlag.equals(ReflexUtil.SQL_INSERT) || sqlFlag.equals(SQL_INSERT_OR_UPDATE) || sqlFlag.equals(SQL_SELECT)) {
            for (int i = 0; args != null && i < fields.length; i++) {
                try {
                    // 暴力反射
                    fields[i].setAccessible(true);

                    if (null != fields[i].get(entity) && fields[i].isAnnotationPresent(Column.class)) {
                        args.add(fields[i].get(entity));
                    }
                } catch (Exception e) {
                    log.error("设置INSERT参数时反射异常! 异常信息:",e);
                    e.printStackTrace();
                }
            }

            if(sqlFlag.equals(SQL_INSERT_OR_UPDATE)){
                args.addAll(args);
            }

        } else if (sqlFlag.equals(ReflexUtil.SQL_UPDATE)) {
            Object id = null;

            for (int i = 0; args != null && i < fields.length; i++) {
                try {
                    // 暴力反射
                    fields[i].setAccessible(true);
                    if (null != fields[i].get(entity) && fields[i].isAnnotationPresent(Column.class)) {
                        if (fields[i].getAnnotation(Column.class).isPK()) {
                            id = fields[i].get(entity);
                            continue;
                        }
                        args.add(fields[i].get(entity));
                    }

                } catch (Exception e) {
                    log.error("设置UPDATE参数时反射异常! 异常信息:",e);
                }
            }
            args.add(id);

        } else if (sqlFlag.equals(ReflexUtil.SQL_DELETE)) {
            List<Object> argsAll = new ArrayList<>();
            for (int i = 0; args != null && i < fields.length; i++) {
                try {
                    // 暴力反射
                    fields[i].setAccessible(true);
                    if (null != fields[i].get(entity)) {
                        if (fields[i].isAnnotationPresent(Column.class)) {
                            // 关联表无主键
                            argsAll.add(fields[i].get(entity));
                            if (fields[i].getAnnotation(Column.class).isPK()) {
                                args.add(fields[i].get(entity));
                                break;
                            }
                        }
                    }

                } catch (Exception e) {
                    log.error("设置DELETE参数时反射异常! 异常信息:",e);
                }
            }
            if (args.size()==0) {
                args.addAll(argsAll);
            }
        }
        return args.toArray();

    }

    /**
     * 获取表名
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> String getTableName(Class<T> clazz) {
        String tableName = null;

        if (clazz.isAnnotationPresent(Table.class)) {
            tableName = clazz.getAnnotation(Table.class).name();
        }
        //表名
        return tableName;
    }


    /**
     * 获取对应的转换实体
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> RowMapper<T> rowMapper(Class<T> clazz) {
        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(clazz);
        return rowMapper;

    }

    /**
     * 过滤order
     *
     * @param sql
     * @return
     */
    public static String replaceOrderBy(String sql) {
        return ORDER_BY_PATTERN.matcher(sql).replaceAll("");
    }

    /**
     * table注解验证
     *
     * @param clazz
     * @param <T>
     */
    public static <T> void checkTableAnnotation(Class<T> clazz) {
        Assert.state(clazz.isAnnotationPresent(Table.class), String.format("非标准实体,缺少%s注解", Table.class.getName()));
    }

    /**
     * column注解验证
     *
     * @param clazz
     * @param <T>
     */
    public static <T> void checkColumnAnnotation(Class<T> clazz) {
        boolean flag = false;
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                flag = true;
                break;
            }
        }
        Assert.state(flag, String.format("非标准实体,缺少%s注解", Column.class.getName()));

    }
}
