package edu.nchu.bootdemo.model;


import lombok.Data;

/**
 * 处方详情
 */
@Data
public class PresDetail {

    private String drugInfoId;
    private String presId;
    private Integer drugCount;
    private Drug drug; // 每一条处方详情对应一个药品信息

}
