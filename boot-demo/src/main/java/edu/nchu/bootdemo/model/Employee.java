package edu.nchu.bootdemo.model;

import lombok.Data;

import java.util.Date;

@Data // 生成了构造函数 getset方法 以及toString hashcode equals
public class Employee {
    private String id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private Date updateTime;
    private String delFlag;

    private Department department;
}
