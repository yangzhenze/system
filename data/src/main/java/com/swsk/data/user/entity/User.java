package com.swsk.data.user.entity;

import java.io.Serializable;
import com.zzy.db.helper.anotation.Table;
import com.zzy.db.helper.anotation.Column;
import lombok.Data;
/**
 * @author yzz
 * @Date 2020/41/07 14:41:10
 */
@Data
@Table(name="user")
public class User implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     *主键
     */
    @Column(name="id",isPK = true)
    private java.lang.Integer id;

    /**
     *帐号
     */
    @Column(name="account")
    private java.lang.String account;

    /**
     *密码
     */
    @Column(name="password")
    private java.lang.String password;

    /**
     *省
     */
    @Column(name="province")
    private java.lang.String province;

    /**
     *省编号
     */
    @Column(name="province_code")
    private java.lang.String provinceCode;

    /**
     *市
     */
    @Column(name="city")
    private java.lang.String city;

    /**
     *市编号
     */
    @Column(name="city_code")
    private java.lang.String cityCode;

    /**
     *县
     */
    @Column(name="county")
    private java.lang.String county;

    /**
     *县编号
     */
    @Column(name="county_code")
    private java.lang.String countyCode;

    /**
     *姓名
     */
    @Column(name="name")
    private java.lang.String name;

    /**
     *联系方式
     */
    @Column(name="contact")
    private java.lang.String contact;

    /**
     *是否启用1.启用 0.不启用
     */
    @Column(name="enable")
    private java.lang.Integer enable;

    /**
     *
     */
    @Column(name="role_id")
    private java.lang.Integer roleId;

    /**
     *角色类型
     */
    @Column(name="role_desc")
    private java.lang.String roleDesc;

    /**
     *所属部门
     */
    @Column(name="department")
    private java.lang.String department;

    /**
     *职务
     */
    @Column(name="position")
    private java.lang.String position;

    /**
     *邮箱
     */
    @Column(name="email")
    private java.lang.String email;

    /**
     *创建时间
     */
    @Column(name="create_date")
    private java.util.Date createDate;

    /**
     *创建人
     */
    @Column(name="create_name")
    private java.lang.String createName;

    /**
     *更新时间
     */
    @Column(name="update_date")
    private java.util.Date updateDate;

    /**
     *更新人
     */
    @Column(name="update_name")
    private java.lang.String updateName;

    /**
     *删除状态0.未删除1.删除
     */
    @Column(name="is_del")
    private java.lang.Integer isDel;

}