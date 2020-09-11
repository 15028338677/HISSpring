package edu.nchu.bootdemo.model;

import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * 处方信息表
 */
@Data
public class Prescription {

    private String presId;
    private String mrId; //病历表id
    private String description; // 病情描述
    private String status; // 处方状态：0：未付款 1 已付款  2 已发药
    private String amount; // 总金额
    private String contract; // 合同类型
    private String selfPay; // 自费金额
    private Date payTime; // 支付时间
    private Date updateTime;

    private Employee doctor; // 开具处方的专家
    private Department department; // 挂号的科室
    private List<PresDetail> presDetails;// 对应的处方详情 根据 Prescription.mrId对应
}
