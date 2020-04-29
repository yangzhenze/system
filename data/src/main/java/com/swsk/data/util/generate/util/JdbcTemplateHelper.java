package com.swsk.data.util.generate.util;

import com.swsk.data.util.generate.db.Page;
import com.swsk.data.util.generate.db.ReflexUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author zzy
 * @Date 2019-10-23 14:22
 */
@Component
public class JdbcTemplateHelper {
    private static Logger log = LoggerFactory.getLogger(JdbcTemplateHelper.class);

    @Autowired
    private JdbcTemplate jt;

    public static JdbcTemplate jdbcTemplate;

    public JdbcTemplateHelper(){
        log.info("实例化JdbcTemplateHelper！");
    }

    @PostConstruct
    public void constructAfter() {
        jdbcTemplate = this.jt;
        log.info("JdbcTemplateHelper初始化完成！");
    }


    /**
     * 添加
     *
     * @param entity
     * @return
     */
    public static <T> boolean insertEntity(T entity) {
        return operation(entity, ReflexUtil.SQL_INSERT);
    }

    /**
     * 添加或更新
     *
     * @param entity
     * @return
     */
    public static <T> boolean insertOrUpdate(T entity) {
        return operation(entity, ReflexUtil.SQL_INSERT_OR_UPDATE);
    }


    /**
     * 获取最后插入的主键id ===只适用于自增
     * @return
     */
    private static Integer getLastInsertId(){
        Map<String,Object> ret = selectFirst("SELECT LAST_INSERT_ID() as id");
        return Integer.valueOf(ret.get("ID").toString());
    }


    /**
     * 添加或更新--批量
     *
     * @param entityList
     */
    public static <T> void insertOrUpdate(List<T> entityList) {
        entityList.forEach(t -> {
            insertOrUpdate(t);
        });
    }

    /**
     * 插入并返回实体===必须设定主键
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> T insertAndGetEntity(T entity){
        return insertOrUpdateAndGetEntity(entity,ReflexUtil.SQL_INSERT);
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    public static <T> boolean updateEntity(T entity) {
        return operation(entity, ReflexUtil.SQL_UPDATE);
    }


    /**
     * 更新并返回实体===必须设定主键
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> T updateAndGetEntity(T entity){
        return insertOrUpdateAndGetEntity(entity,ReflexUtil.SQL_UPDATE);
    }

    /**
     * 添加或更新--批量 ==异常回滚(当前对象id值不为空时更新，否则添加)
     *
     * @param entityList
     * @return
     */
    public static <T> boolean batchInsertOrUpdate(List<T> entityList) {
        try{
            return batchForEntity(entityList, ReflexUtil.SQL_INSERT_OR_UPDATE);
        }catch (Exception e){
            log.error("添加或更新失败",e);
            return false;
        }

    }

    /**
     * 添加--批量 ==异常回滚
     *
     * @param entityList
     * @return
     */
    public static <T> boolean batchSave(List<T> entityList) {
        try{
            return batchForEntity(entityList, ReflexUtil.SQL_INSERT);
        }catch (Exception e){
            log.error("批量添加失败",e);
            return false;
        }

    }


    private static <T> boolean batchForEntity(List<T> entityList,String sqlFlag) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean result = false;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            //设置手动提交
            connection.setAutoCommit(false);

            for (T t : entityList) {
                String sql = ReflexUtil.makeSql(t, sqlFlag);
                Object[] args = ReflexUtil.setArgs(t, sqlFlag);
                ReflexUtil.forDebuggingAndPrintingSQLLogs(sql, args);
                preparedStatement = connection.prepareStatement(sql);
                // 设置参数
                ArgumentPreparedStatementSetter statementSetter = new ArgumentPreparedStatementSetter(args);
                statementSetter.setValues(preparedStatement);
                result = preparedStatement.executeUpdate() > 0 ? true : false;

                if (!result) {
                    connection.rollback();
                    break;
                }
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw  e;
        } finally {
            try {
                if(null != preparedStatement){
                    preparedStatement.close();
                }

                if(null != connection){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private static <T> T insertOrUpdateAndGetEntity(T entity,String sqlFlag){
        String pk = ReflexUtil.getTablePk(entity.getClass());

        if(null == pk){
            Assert.isNull(false, "该对象所属类未设置主键，无法进行下一步操作");
        }
        Object pkV;

        try {
            pkV =  ReflexUtil.getColumnValue(entity,pk);
        } catch (IllegalAccessException e) {
            log.error("获取主键值失败,插入失败!",e);
            return null;
        }
        boolean flag = false;

        if(sqlFlag.equals(ReflexUtil.SQL_INSERT)){
            flag = insertEntity(entity);
        }else if(sqlFlag.equals(ReflexUtil.SQL_UPDATE)){
            flag = updateEntity(entity);
        }

        if(flag){
            if(null != pkV){
                return (T) findEntityById(entity.getClass(),pkV.toString());
            }

            Integer id = getLastInsertId();
            return (T) findEntityById(entity.getClass(),id);
        }

        return null;
    }


    //#####################################################删除START#####################################################

    /**
     * 删除
     *
     * @param entity
     * @return
     */
    public static <T> boolean deleteEntity(T entity) {
        return operation(entity, ReflexUtil.SQL_DELETE);
    }


    /**
     * 根据id删除
     *
     * @param ids
     * @return
     */
    public static <T> boolean deleteById(Class<T> clazz, Serializable... ids) {
        boolean result = false;
        StringBuffer args = new StringBuffer();

        for (int i = 0; i < ids.length; i++) {
            if (0 != i) {
                args.append(",");
            }
            args.append("?");
        }
        StringBuffer sql = new StringBuffer();
        sql.append(ReflexUtil.SQL_DELETE);
        sql.append(ReflexUtil.SQL_FROM);
        sql.append(ReflexUtil.getTableName(clazz));
        sql.append(ReflexUtil.SQL_WHERE);
        sql.append(ReflexUtil.getTablePk(clazz));
        sql.append(ReflexUtil.SQL_IN);
        sql.append("(").append(args).append(")");
        ReflexUtil.forDebuggingAndPrintingSQLLogs(sql.toString(), ids);

        try {
            if (jdbcTemplate.update(sql.toString(), ids) > 0) {
                result = true;
            }
        } catch (Exception e) {
            log.error("删除失败! 异常信息: {}",e.getMessage(),e);
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    //#####################################################删除END#####################################################


    /**
     * 根据实体查询
     *
     * @param entity
     * @return
     */
    public static <T> List<T> findEntity(T entity) {
        String sql = ReflexUtil.makeSql(entity, ReflexUtil.SQL_SELECT);
        Object[] args = ReflexUtil.setArgs(entity, ReflexUtil.SQL_SELECT);
        ReflexUtil.forDebuggingAndPrintingSQLLogs(sql, args);
        return (List<T>) jdbcTemplate.query(sql,args,ReflexUtil.rowMapper(entity.getClass()));
    }

    /**
     * 增删改操作方法
     *
     * @param entity
     * @param operationType
     * @param <T>
     * @return
     */
    private static <T> boolean operation(T entity, String operationType) {
        String sql = ReflexUtil.makeSql(entity, operationType);
        Object[] args = ReflexUtil.setArgs(entity, operationType);
        ReflexUtil.forDebuggingAndPrintingSQLLogs(sql, args);
        boolean result = false;

        if (jdbcTemplate.update(sql, args) > 0) {
            result = true;
        }
        return result;
    }

    /**
     * 使用SQL增、删、改操作
     * @param sql
     * @param args
     * @return
     */
    public static int update(String sql,Object... args){
        ReflexUtil.forDebuggingAndPrintingSQLLogs(sql, args);
        return jdbcTemplate.update(sql,args);
    }

    public static int update(String sql){
        ReflexUtil.forDebuggingAndPrintingSQLLogs(sql,null);
        return jdbcTemplate.update(sql);
    }


    //#####################################################查询START#####################################################

    /**
     * 查询返回实体(多条)
     *
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public static <T> List<T> findEntity(Class<T> clazz, String sql, Object... args) {
        ReflexUtil.forDebuggingAndPrintingSQLLogs(sql, args);
        return jdbcTemplate.query(sql, args, ReflexUtil.rowMapper(clazz));
    }


    /**
     * 查询返回实体(单条)
     *
     * @param sql
     * @param args
     * @return
     */
    public static <T> T findFirstEntity(Class<T> clazz, String sql, Object... args) {
        ReflexUtil.forDebuggingAndPrintingSQLLogs(sql, args);
        List<T> list = jdbcTemplate.query(sql, args, ReflexUtil.rowMapper(clazz));
        return list.size() > 0 ? list.get(0) : null;
    }

    /**
     * 查询全部实体
     *
     * @return
     */
    public static <T> List<T> findAllEntity(Class<T> clazz) {
        ReflexUtil.checkTableAnnotation(clazz);
        String sql = ReflexUtil.SQL_SELECT_FROM + ReflexUtil.getTableName(clazz);
        return jdbcTemplate.query(sql, ReflexUtil.rowMapper(clazz));
    }

    /**
     * 根据sql查询返回为Map类型(多条)
     *
     * @param sql
     * @param args
     * @return
     */
    public static List<Map<String, Object>> select(String sql, Object... args) {
        ReflexUtil.forDebuggingAndPrintingSQLLogs(sql, args);
        return jdbcTemplate.queryForList(sql, args);
    }

    /**
     * 根据sql查询返回为Map类型(单条)
     *
     * @param sql
     * @param args
     * @return
     */
    public static Map<String, Object> selectFirst(String sql, Object... args) {
        ReflexUtil.forDebuggingAndPrintingSQLLogs(sql, args);
        List<Map<String, Object>> list = select(sql, args);
        return list.size() > 0 ? list.get(0) : null;
    }


    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public static <T> T findEntityById(Class<T> clazz, Serializable id) {
        StringBuffer sql = new StringBuffer();
        sql.append(ReflexUtil.SQL_SELECT_FROM).
                append(ReflexUtil.getTableName(clazz)).
                append(ReflexUtil.SQL_WHERE).
                append(ReflexUtil.getTablePk(clazz)).
                append("=?");
        List<T> list = jdbcTemplate.query(sql.toString(), ReflexUtil.rowMapper(clazz), id);
        ReflexUtil.forDebuggingAndPrintingSQLLogs(sql.toString(), new Object[]{id});
        return list.size() > 0 ? list.get(0) : null;
    }

    //#####################################################查询END#####################################################


    //#####################################################分页START#####################################################

    /**
     * 分页查询
     *
     * @param whichPage
     * @param pageSize
     * @param select
     * @param sql
     * @param args
     * @return
     */
    public static Page<Map<String, Object>> paginate(int whichPage, int pageSize, String select, String sql, Object... args) {
        if(log.isDebugEnabled()){
            log.debug("============================================================PAGINATE-START============================================================");
        }
        String totalRowSql = ReflexUtil.SQL_SELECT + " count(*) " + ReflexUtil.replaceOrderBy(sql);
        int totalRow = selectForTotalRow(totalRowSql, args);
        int totalPage = getTotalPage(totalRow, pageSize);
        sql += new StringBuffer(ReflexUtil.SQL_LIMIT).append((whichPage - 1) * pageSize).append(",").append(pageSize).toString();
        List<Map<String, Object>> pageList = select(select + " " + sql, args);
        Page<Map<String, Object>> page = new Page<>(pageList, whichPage, pageSize, totalPage, totalRow);
        if(log.isDebugEnabled()){
            log.debug("============================================================PAGINATE-END============================================================");
        }
        return page;

    }


    /**
     * 分页查询
     *
     * @param clazz
     * @param whichPage
     * @param pageSize
     * @param whereSql
     * @param args
     * @param <T>
     * @return
     */
    public static <T> Page<T> paginate(Class<T> clazz, int whichPage, int pageSize, String whereSql, Object... args) {
        ReflexUtil.checkTableAnnotation(clazz);

        if(log.isDebugEnabled()){
            log.debug("============================================================PAGINATE-START============================================================");
        }

        if (StringUtils.isEmpty(whereSql)) {
            whereSql = ReflexUtil.SQL_EMPTY;
        } else {
            if (!whereSql.startsWith(ReflexUtil.SQL_EMPTY)) {
                whereSql = ReflexUtil.SQL_EMPTY + whereSql;
            }
        }

        if (!StringUtils.isBlank(whereSql) && !whereSql.toUpperCase().contains(ReflexUtil.SQL_WHERE.trim())) {
            whereSql = ReflexUtil.SQL_WHERE + whereSql;
        }
        String totalRowSql = ReflexUtil.SQL_SELECT + " count(*)" + ReflexUtil.SQL_FROM + ReflexUtil.getTableName(clazz) + ReflexUtil.replaceOrderBy(whereSql);
        int totalRow = selectForTotalRow(totalRowSql, args);
        int totalPage = getTotalPage(totalRow, pageSize);
        whereSql += new StringBuffer(ReflexUtil.SQL_LIMIT).append((whichPage - 1) * pageSize).append(",").append(pageSize).toString();
        String listData = ReflexUtil.SQL_SELECT_FROM + ReflexUtil.getTableName(clazz) + whereSql;

        for (Object arg : args) {
            log.debug(arg.toString());
        }
        List<T> pageList = findEntity(clazz, listData, args);
        Page<T> page = new Page<>(pageList, whichPage, pageSize, totalPage, totalRow);
        if(log.isDebugEnabled()){
            log.debug("============================================================PAGINATE-END============================================================");
        }
        return page;

    }

    private static int selectForTotalRow(String totalRowSql,Object... args){
        ReflexUtil.forDebuggingAndPrintingSQLLogs(totalRowSql, args);
        return jdbcTemplate.queryForObject(totalRowSql, args, Integer.class);
    }

    private static int getTotalPage(int totalRow, int pageSize) {
        return totalRow % pageSize == 0 ? totalRow / pageSize : totalRow / pageSize + 1;
    }

    //#####################################################分页END#####################################################
}
