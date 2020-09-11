package edu.nchu.bootdemo.dao;

import edu.nchu.bootdemo.model.Department;
import edu.nchu.bootdemo.model.Employee;

import java.util.List;

public interface DepartmentDao {

    Integer insert(Department department);

    Integer update(Department department);

    Integer delete(String id);

    Department findById(String id);

    //条件查询
    List<Department> findList(Department condition);


}
