package edu.nchu.bootdemo.model;

import lombok.Data;

/**
 * 分页所需参数的对象 T代表条件对象类的泛型
 */
@Data
public class PageCondition<T> {

    private Integer pageNum; //页码
    private Integer pageSize; //每页条目数
    private T condition; // 代表条件的对象

}
