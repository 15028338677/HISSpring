package edu.nchu.bootdemo.web;

import com.github.pagehelper.PageInfo;
import com.nimbusds.jose.JOSEException;
import edu.nchu.bootdemo.model.Employee;
import edu.nchu.bootdemo.model.PageCondition;
import edu.nchu.bootdemo.service.EmployeeService;
import edu.nchu.bootdemo.util.IdWorker;
import edu.nchu.bootdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private IdWorker idWorker;

    /**
     * 插入数据
     * @param employee
     */
    @RequestMapping(value = "/employee",method = RequestMethod.POST)
    public boolean insert(@RequestBody Employee employee){
        employee.setId(idWorker.nextId()+"");
        employee.setUpdateTime(new Date());
        employee.setDelFlag("0");
        return employeeService.insert(employee);
    }

    /**
     * 修改数据
     * @param employee
     */
    @PutMapping(value = "/employee")
    public boolean update(@RequestBody Employee employee){
        return employeeService.update(employee);
    }

    /**
     * 删除数据
     * @param id
     */
    @DeleteMapping(value = "/employee/{id}")
    public boolean delete(@PathVariable String id){
        return employeeService.delete(id);
    }

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    @GetMapping(value = "/employee/{id}")
    public Employee findById(@PathVariable String id){
        return employeeService.findById(id);
    }

    /**
     * 处理登录请求
     * @return 返回的数据
     */
    @PostMapping("/login")
    public String login(@RequestBody Employee employee, HttpServletResponse response) throws JOSEException {
         Employee principal = employeeService.authLogin(employee);
         if (principal != null){
             // 登录成功 生成jwtToken 返回前台
             String token = JwtUtil.genToken(principal.getId(),principal.getUsername());
             // 将token加入到相应的头部
             response.setHeader("access-token",token);
             return "success";
         }else {
             // 设置相应的状态码
             response.setStatus(401);
             return "fail";
         }
    }

    /**
     * 实现分页的条件查询
     * @param pageCondition 包含分页对象和employee
     * @return 返回分页对象
     */
    @PostMapping("/employee/find")
    public PageInfo<Employee> page(@RequestBody PageCondition<Employee> pageCondition){
        return employeeService.page(pageCondition);
    }
}
