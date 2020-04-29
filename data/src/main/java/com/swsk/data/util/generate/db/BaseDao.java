package com.swsk.data.util.generate.db;
import com.swsk.data.util.generate.anotation.Table;
import com.swsk.data.util.generate.util.JdbcTemplateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * @author zzy
 */
@Slf4j
public class BaseDao<T,ID extends Serializable> {

    public String tableName;
    private String pkName;
    public RowMapper<T> rowMapper;


    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public BaseDao() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        entityClass = (Class<T>) type.getActualTypeArguments()[0];
        rowMapper = BeanPropertyRowMapper.newInstance(entityClass);
        //表名
        this.tableName = entityClass.getAnnotation(Table.class).name();
        this.pkName = ReflexUtil.getTablePk(entityClass);

        if(log.isDebugEnabled()){
            log.debug("================================================================");
            log.debug("表名为：" + this.tableName);
            log.debug("Dao实现类为：" + entityClass.getName());
            log.debug("主键字段为：" + this.pkName);
            log.debug("================================================================");
        }
    }

    /**
     * 插入
     * @param entity
     * @return
     */
    public boolean insertEntity(T entity) {
        return com.swsk.data.util.generate.db.JdbcTemplateHelper.insertEntity(entity);
    }


    /**
     * 插入返回实体
     * @param entity
     * @return
     */
    public T insertAndGetEntity(T entity){
        return com.swsk.data.util.generate.db.JdbcTemplateHelper.insertAndGetEntity(entity);
    }

    /**
     * 更新
     * @param entity
     * @return
     */
    public boolean updateEntity(T entity) {
        return com.swsk.data.util.generate.util.JdbcTemplateHelper.updateEntity(entity);
    }


    /**
     * 插入返回实体
     * @param entity
     * @return
     */
    public T updateAndGetEntity(T entity){
        return com.swsk.data.util.generate.db.JdbcTemplateHelper.updateAndGetEntity(entity);
    }

    /**
     * 删除
     * @param entity
     * @return
     */
    public boolean deleteEntity(T entity) {
        return com.swsk.data.util.generate.db.JdbcTemplateHelper.deleteEntity(entity);
    }

    /**
     * 添加或更新--批量
     * @param entityList
     */
    public void insertOrUpdate(List<T> entityList){
        com.swsk.data.util.generate.db.JdbcTemplateHelper.insertOrUpdate(entityList);
    }

    /**
     * 添加或更新--批量 ==异常回滚
     * @param entityList
     * @return
     */
    public boolean batchInsertOrUpdate(List<T> entityList){
        return com.swsk.data.util.generate.db.JdbcTemplateHelper.batchInsertOrUpdate(entityList);

    }


    /**
     * 添加或更新
     * @param entity
     * @return
     */
    public boolean insertOrUpdate(T entity){
        return com.swsk.data.util.generate.db.JdbcTemplateHelper.insertOrUpdate(entity);
    }

    /**
     * 根据id删除
     * @param ids
     * @return
     */
    public boolean deleteById(ID ... ids){
        return com.swsk.data.util.generate.db.JdbcTemplateHelper.deleteById(entityClass,ids);
    }

    /**
     * 删除该表数据
     * @return
     */
    public boolean deleteTable() {
        String sql = " TRUNCATE TABLE " + this.tableName;
        return com.swsk.data.util.generate.db.JdbcTemplateHelper.update(sql) > 0?true:false;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public T findEntityById(ID id) {
        return com.swsk.data.util.generate.util.JdbcTemplateHelper.findEntityById(entityClass,id);
    }


    /**
     * 查询返回实体(单条)
     * @param sql
     * @param args
     * @return
     */
    public T findFirstEntity(String sql,Object ... args){
        return com.swsk.data.util.generate.util.JdbcTemplateHelper.findFirstEntity(entityClass,sql, args);
    }

    /**
     * 查询全部实体
     * @return
     */
    public List<T> findAllEntity() {
        return com.swsk.data.util.generate.util.JdbcTemplateHelper.findAllEntity(entityClass);
    }

    /**
     * 查询返回实体(多条)
     * @param sql
     * @param args
     * @return
     */
    public List<T> findEntity(String sql,Object ... args){
        return com.swsk.data.util.generate.util.JdbcTemplateHelper.findEntity(entityClass,sql,args);
    }

    /**
     * 根据sql查询返回为Map类型(多条)
     * @param sql
     * @param args
     * @return
     */
    public List<Map<String,Object>> select(String sql,Object ... args){
        return com.swsk.data.util.generate.util.JdbcTemplateHelper.select(sql,args);
    }

    /**
     * 根据sql查询返回为Map类型(单条)
     * @param sql
     * @param args
     * @return
     */
    public Map<String,Object> selectFirst(String sql,Object ... args){
        return com.swsk.data.util.generate.util.JdbcTemplateHelper.selectFirst(sql,args);
    }

    /**
     * 获取实体分页 sql为where
     * @param whichPage
     * @param pageSize
     * @param whereSql
     * @param args
     * @return
     */
    public Page<T> paginate(int whichPage, int pageSize, String whereSql, Object ... args){
        return com.swsk.data.util.generate.util.JdbcTemplateHelper.paginate(entityClass,whichPage,pageSize,whereSql,args);

    }

    public Page<Map<String,Object>> paginate(int whichPage,int pageSize,String select,String sql,Object ... args){
        return JdbcTemplateHelper.paginate(whichPage,pageSize,select,sql,args);
    }





}



