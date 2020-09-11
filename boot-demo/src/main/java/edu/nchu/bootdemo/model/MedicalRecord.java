package edu.nchu.bootdemo.model;

import lombok.Data;

import java.util.Date;

/**
 * 病历表
 * @author dell
 */
@Data
public class MedicalRecord {

    private String id; // 病历id
    private String name; // 患者姓名
    private String gender; // 患者性别
    private Integer age; // 患者
    private String contract; // 0：医保、自费
    private String operatorId; // 医疗的id
    private Department department; // 挂号的科室
    private String delFlag;
    private Date updateTime; // 更新时间

    private Prescription prescription;


}
