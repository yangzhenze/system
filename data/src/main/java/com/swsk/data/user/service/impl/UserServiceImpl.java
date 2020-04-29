package com.swsk.data.user.service.impl;

import com.swsk.data.util.generate.db.Page;
import com.swsk.data.user.entity.User;
import com.swsk.data.user.dao.IUserDao;
import com.swsk.data.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author yzz
 * @Date 2020-04-26 16:28:43
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserDao userDao;
    /**
     * 保存
     */
    @Override
    public boolean save(User user) {
        return userDao.save(user);
    }

    /**
     * 根据id查询
     */
    @Override
    public User getById(java.lang.Integer id){
        return userDao.findById(id);
    }

    /**
     * 根据id删除
     */
    @Override
    public boolean deleteByIds(java.lang.Integer ... ids){
        return userDao.delById(ids);
    }

    /**
     * 更新
     */
    @Override
    public boolean update(User user) {
        return userDao.update(user);
    }

    /**
     * 删除
     */
    @Override
    public boolean delete(User user) {
        return userDao.delete(user);
    }

    /**
     * 分页
     */
    @Override
    public Page<User> getPage(int page, int pageSize) {
        return userDao.findPage(page, pageSize);
    }

}