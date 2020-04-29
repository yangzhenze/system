package com.swsk.data.user.dao;

import com.swsk.data.user.entity.User;
import com.swsk.data.util.generate.db.IBaseDao;
import java.lang.Integer;
import java.util.List;
/**
 * @author yzz
 * @Date 2020-04-26 16:28:43
 */
public interface IUserDao extends IBaseDao<User,Integer> {

    /**
     * 根据属性值查询
     * @param user
     * @return List<User>
     */
    List<User> findList(User user);
}