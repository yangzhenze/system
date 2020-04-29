package com.swsk.data.user.dao.impl;

import com.swsk.data.util.generate.db.Page;
import com.swsk.data.util.generate.db.BaseDao;
import com.swsk.data.user.entity.User;
import com.swsk.data.user.dao.IUserDao;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.lang.Integer;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;
/**
 * @author yzz
 * @Date 2020-04-26 16:28:43
 */
@Repository
public class UserDaoImpl extends BaseDao<User,Integer> implements IUserDao {
    @Override
    public boolean save(User user) {
        return super.insertEntity(user);
    }

    @Override
    public User saveAndGet(User user) {
        return super.insertAndGetEntity(user);
    }

    @Override
    public boolean update(User user) {
        return super.updateEntity(user);
    }

    @Override
    public User updateAndGet(User user) {
        return super.updateAndGetEntity(user);
    }

    @Override
    public User findById(Integer id) {
        return super.findEntityById(id);
    }

    @Override
    public boolean delete(User user) {
        return super.deleteEntity(user);
    }

    @Override
    public boolean delById(Integer ... ids) {
        return super.deleteById(ids);
    }

    @Override
    public Page<User> findPage(int page, int pageSize) {
        return super.paginate(page,pageSize,null);
    }

    @Override
    public List<User> findList(User user){
        String wd = " where";
        List<Object> params = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select * from user");
        if(null != user.getId()){
            sql.append(wd+" id = ? ");
            wd = " and";
            params.add(user.getId());
        }
        
        if(!StringUtils.isEmpty(user.getAccount())){
            sql.append(wd+" account = ? ");
            wd = " and";
            params.add(user.getAccount());
        }
        
        if(!StringUtils.isEmpty(user.getPassword())){
            sql.append(wd+" password = ? ");
            wd = " and";
            params.add(user.getPassword());
        }
        
        if(!StringUtils.isEmpty(user.getProvince())){
            sql.append(wd+" province = ? ");
            wd = " and";
            params.add(user.getProvince());
        }
        
        if(!StringUtils.isEmpty(user.getProvinceCode())){
            sql.append(wd+" province_code = ? ");
            wd = " and";
            params.add(user.getProvinceCode());
        }
        
        if(!StringUtils.isEmpty(user.getCity())){
            sql.append(wd+" city = ? ");
            wd = " and";
            params.add(user.getCity());
        }
        
        if(!StringUtils.isEmpty(user.getCityCode())){
            sql.append(wd+" city_code = ? ");
            wd = " and";
            params.add(user.getCityCode());
        }
        
        if(!StringUtils.isEmpty(user.getCounty())){
            sql.append(wd+" county = ? ");
            wd = " and";
            params.add(user.getCounty());
        }
        
        if(!StringUtils.isEmpty(user.getCountyCode())){
            sql.append(wd+" county_code = ? ");
            wd = " and";
            params.add(user.getCountyCode());
        }
        
        if(!StringUtils.isEmpty(user.getName())){
            sql.append(wd+" name = ? ");
            wd = " and";
            params.add(user.getName());
        }
        
        if(!StringUtils.isEmpty(user.getContact())){
            sql.append(wd+" contact = ? ");
            wd = " and";
            params.add(user.getContact());
        }
        
        if(null != user.getEnable()){
            sql.append(wd+" enable = ? ");
            wd = " and";
            params.add(user.getEnable());
        }
        
        if(null != user.getRoleId()){
            sql.append(wd+" role_id = ? ");
            wd = " and";
            params.add(user.getRoleId());
        }
        
        if(!StringUtils.isEmpty(user.getRoleDesc())){
            sql.append(wd+" role_desc = ? ");
            wd = " and";
            params.add(user.getRoleDesc());
        }
        
        if(!StringUtils.isEmpty(user.getDepartment())){
            sql.append(wd+" department = ? ");
            wd = " and";
            params.add(user.getDepartment());
        }
        
        if(!StringUtils.isEmpty(user.getPosition())){
            sql.append(wd+" position = ? ");
            wd = " and";
            params.add(user.getPosition());
        }
        
        if(!StringUtils.isEmpty(user.getEmail())){
            sql.append(wd+" email = ? ");
            wd = " and";
            params.add(user.getEmail());
        }
        
        if(null != user.getCreateDate()){
            sql.append(wd+" create_date = ? ");
            wd = " and";
            params.add(user.getCreateDate());
        }
        
        if(!StringUtils.isEmpty(user.getCreateName())){
            sql.append(wd+" create_name = ? ");
            wd = " and";
            params.add(user.getCreateName());
        }
        
        if(null != user.getUpdateDate()){
            sql.append(wd+" update_date = ? ");
            wd = " and";
            params.add(user.getUpdateDate());
        }
        
        if(!StringUtils.isEmpty(user.getUpdateName())){
            sql.append(wd+" update_name = ? ");
            wd = " and";
            params.add(user.getUpdateName());
        }
        
        if(null != user.getIsDel()){
            sql.append(wd+" is_del = ? ");
            params.add(user.getIsDel());
        }
        
        return super.findEntity(sql.toString(), params.toArray());
    }

}