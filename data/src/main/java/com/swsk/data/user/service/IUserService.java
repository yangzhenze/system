package com.swsk.data.user.service;

import com.swsk.data.user.entity.User;
import com.swsk.data.util.generate.db.Page;
/**
 * @author yzz
 * @Date 2020-04-26 16:28:43
 */
public interface IUserService {
    /**
     * 保存
     * @param user
     * @return
     */
    boolean save(User user);

    /**
     * 根据id查询
     * @param id
     * @returnUser
     */
    User getById(java.lang.Integer id);

    /**
     * 根据id删除
     * @param ids
     * @return boolean
     */
    boolean deleteByIds(java.lang.Integer ... ids);

    /**
     * 更新
     * @param user
     * @return boolean
     */
    boolean update(User user);

    /**
     * 删除
     * @param user
     * @return boolean
     */
    boolean delete(User user);

    /**
     * 分页
     * @param page
     * @param pageSize
     * @return Page<User>
     */
    Page<User> getPage(int page, int pageSize);

}