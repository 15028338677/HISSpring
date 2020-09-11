package edu.nchu.bootdemo.model;

import lombok.Data;

import java.util.Date;

/**
 * 药瓶信息
 */
@Data
public class Drug {

    private String id;
    private String drugCode;
    private String drugName;
    private String unit;
    private Double price;
    private Date updateTime;
    private String delFlag;
    private int stock; // 库存
}
