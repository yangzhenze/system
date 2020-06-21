package com.swsk.data.user.entity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * @author yzz
 * @Date 2020/41/07 14:41:10
 */
@Data
@Table("user")
public class User2 implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     *主键
     */
    @Id
    private Integer id;

    /**
     *帐号
     */
    private String account;

    /**
     *密码
     */
    private String password;

    /**
     *省
     */
    private String province;

    /**
     *省编号
     */
    private String provinceCode;

    /**
     *市
     */
    private String city;

    /**
     *市编号
     */
    private String cityCode;

    /**
     *县
     */
    private String county;

    /**
     *县编号
     */
    private String countyCode;

    /**
     *姓名
     */
    private String name;

    /**
     *联系方式
     */
    private String contact;

    /**
     *是否启用1.启用 0.不启用
     */
    private Integer enable;

    /**
     *
     */
    private Integer roleId;

    /**
     *角色类型
     */
    private String roleDesc;

    /**
     *所属部门
     */
    private String department;

    /**
     *职务
     */
    private String position;

    /**
     *邮箱
     */
    private String email;

    /**
     *创建时间
     */
    private java.util.Date createDate;

    /**
     *创建人
     */
    private String createName;

    /**
     *更新时间
     */
    private java.util.Date updateDate;

    /**
     *更新人
     */
    private String updateName;

    /**
     *删除状态0.未删除1.删除
     */
    private Integer isDel;

}