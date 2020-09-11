package edu.nchu.bootdemo.web;

import com.github.pagehelper.PageInfo;
import edu.nchu.bootdemo.model.Department;
import edu.nchu.bootdemo.model.PageCondition;
import edu.nchu.bootdemo.service.DepartmentService;
import edu.nchu.bootdemo.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departService;

    @Autowired
    private IdWorker idWorker;

    /**
     * 新增数据
     */
    @RequestMapping(value = "/department",method = RequestMethod.POST)
    public boolean insert(@RequestBody Department department){
        department.setId(idWorker.nextId()+"");
        department.setUpdateTime(new Date());
        department.setDelFlag("0");
        return departService.insert(department);
    }

    /**
     * 修改数据
     */
    @PutMapping(value = "/department")
    public boolean update(@RequestBody Department department){
        return departService.update(department);
    }

    /**
     * 删除数据
     */
    @DeleteMapping(value = "/department/{id}")
    public boolean delete(@PathVariable String id){
        return departService.delete(id);
    }

    /**
     * 根据id查询数据
     */
    @GetMapping(value = "/department/{id}")
    public Department findById(@PathVariable String id){
        return departService.findById(id);
    }

    /**
     * 实现分页的条件查询
     * @param pageCondition 包含分页对象和employee
     * @return 返回分页对象
     */
    @PostMapping("/department/find")
    public PageInfo<Department> page(@RequestBody PageCondition<Department> pageCondition){
        return departService.page(pageCondition);
    }

    /**
     * 更具条件查询 不分页
     * @param condition
     * @return
     */
    @PostMapping("/department/findList")
    public List<Department> findList(@RequestBody Department condition){
        return departService.findList(condition);
    }

}
