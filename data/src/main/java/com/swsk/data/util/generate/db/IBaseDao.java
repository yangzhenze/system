package com.swsk.data.util.generate.db;


import java.io.Serializable;

/**
 * @author zzy
 */
public interface IBaseDao<T,ID extends Serializable>{
    /**
     * 保存
     * @param t
     * @return
     */
    boolean save(T t);

    /**
     * 保存并返回
     * @param t
     * @return
     */
    T saveAndGet(T t);

    /**
     * 更新
     * @param t
     * @return
     */
    boolean update(T t);

    /**
     * 更新并返回
     * @param t
     * @return
     */
    T updateAndGet(T t);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    T findById(ID id);

    /**
     * 删除
     * @param t
     * @return
     */
    boolean delete(T t);

    /**
     * 根据id批量删除
     * @param ids
     * @return
     */
    boolean delById(ID... ids);



    /**
     * 获取分页
     * @param page
     * @param pageSize
     * @return
     */
    Page<T> findPage(int page, int pageSize);
}
