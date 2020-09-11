package edu.nchu.bootdemo.dao;

import edu.nchu.bootdemo.model.Employee;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

/**
 *
 */
public interface EmployeeDao {

    Integer insert(Employee employee);

    Integer update(Employee employee);

    Integer delete(String id);

    Employee findById(String id);

    //条件查询
    List<Employee> findList(Employee condition);

    // 登录
    Employee find(Employee condition);
}
