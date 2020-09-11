package edu.nchu.bootdemo.model;

import lombok.Data;

import java.util.Date;

@Data
public class Department {
    private String id;
    private String departName;
    private String departCode;
    private Date updateTime;
    private String delFlag;



}
