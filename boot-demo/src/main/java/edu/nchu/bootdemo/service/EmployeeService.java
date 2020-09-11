package edu.nchu.bootdemo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nchu.bootdemo.dao.EmployeeDao;
import edu.nchu.bootdemo.model.Employee;
import edu.nchu.bootdemo.model.PageCondition;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmployeeService {

    /**
     * 注入加密工具
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeDao employeeDao;

    public boolean insert(Employee employee) {
        employee.setUpdateTime(new Date());
        return employeeDao.insert(employee) > 0;
    }

    public boolean update(Employee employee) {
        employee.setUpdateTime(new Date());
        return employeeDao.update(employee) > 0;
    }

    public boolean delete(String id) {
        return employeeDao.delete(id) > 0;
    }

    public Employee findById(String id) {
        return employeeDao.findById(id);
    }

    public List<Employee> findList(Employee condition) {
        return employeeDao.findList(condition);
    }

    /**
     * 验证登录
     *
     * @param condition 条件 employee对象
     * @return 成功返回employee对象 失败返回null
     */
    public Employee authLogin(Employee condition) {
        // 根据用户名查询表
        Employee employee = employeeDao.find(condition);
        if (employee == null) {
            return null;
        }
        // 判断密码是否正确
        boolean result = passwordEncoder.matches(condition.getPassword(), employee.getPassword());
        return result ? employee : null;

    }

    /**
     * 分页方法
     *
     * @param pageCondition 分页相关信息和查询条件
     * @return
     */
    public PageInfo<Employee> page(PageCondition<Employee> pageCondition) {
        return PageHelper.startPage(pageCondition.getPageNum(), pageCondition.getPageSize())
                .doSelectPageInfo(() -> employeeDao.findList(pageCondition.getCondition()));
    }
}
