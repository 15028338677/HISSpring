package edu.nchu.bootdemo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nchu.bootdemo.dao.DepartmentDao;
import edu.nchu.bootdemo.model.Department;
import edu.nchu.bootdemo.model.PageCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    //新增科室
    public boolean insert(Department department) {
        department.setUpdateTime(new Date());
        return departmentDao.insert(department) > 0;
    }

    //修改科室
    public boolean update(Department department) {
        department.setUpdateTime(new Date());
        return departmentDao.update(department) > 0;
    }

    //删除
    public boolean delete(String id) {
        return departmentDao.delete(id) > 0;
    }

    //通过id查找
    public Department findById(String id) {
        return departmentDao.findById(id);
    }

    //条件查询
    public List<Department> findList(Department condition) {
        return departmentDao.findList(condition);
    }

    /**
     * 分页方法
     *
     * @param pageCondition 分页相关信息和查询条件
     * @return
     */
    public PageInfo<Department> page(PageCondition<Department> pageCondition) {
        return PageHelper.startPage(pageCondition.getPageNum(), pageCondition.getPageSize())
                .doSelectPageInfo(() -> departmentDao.findList(pageCondition.getCondition()));
    }

}
