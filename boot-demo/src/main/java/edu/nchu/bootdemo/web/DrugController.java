package edu.nchu.bootdemo.web;

import com.github.pagehelper.PageInfo;
import edu.nchu.bootdemo.model.Drug;
import edu.nchu.bootdemo.model.Employee;
import edu.nchu.bootdemo.model.MedicalRecord;
import edu.nchu.bootdemo.model.PageCondition;
import edu.nchu.bootdemo.service.DrugService;
import edu.nchu.bootdemo.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class DrugController {
    @Autowired
    private DrugService drugService;

    @Autowired
    private IdWorker idWorker;

    /**
     * 插入数据
     * @param drug
     */
    @RequestMapping(value = "/drug",method = RequestMethod.POST)
    public boolean insert(@RequestBody Drug drug){
        drug.setId(idWorker.nextId()+"");
        drug.setUpdateTime(new Date());
        drug.setDelFlag("0");
        return drugService.insert(drug);
    }

    /**
     * 修改数据
     * @param drug
     */
    @PutMapping(value = "/drug")
    public boolean update(@RequestBody Drug drug){
        return drugService.update(drug);
    }

    /**
     * 删除数据
     * @param id
     */
    @DeleteMapping(value = "/drug/{id}")
    public boolean delete(@PathVariable String id){
        return drugService.delete(id);
    }

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    @GetMapping(value = "/drug/{id}")
    public Drug findById(@PathVariable String id){
        return drugService.findById(id);
    }

    /**
     * 实现分页的条件查询
     * @param pageCondition 包含分页对象和employee
     * @return 返回分页对象
     */
    @PostMapping("/drug/find")
    public PageInfo<Drug> page(@RequestBody PageCondition<Drug> pageCondition){
        return drugService.page(pageCondition);
    }

    /**
     * 更具条件模糊查询
     * @param condition
     * @return
     */
    @PostMapping("/drug/findCondition")
    public List<Drug> findList(@RequestBody Drug condition){
        return drugService.findList(condition);

    }

    /**
     * 根据条件精确查询
     * @param condition
     * @return
     */
    @PostMapping("/drug/findByOne")
    public Drug findByOne(@RequestBody Drug condition){
        return drugService.findLOne(condition);
    }

    /**
     * 开药
     * @param medicalRecord
     * @return
     */
    @PutMapping("/drugOut")
    public boolean updateOutDrug(@RequestBody MedicalRecord medicalRecord){
        for(int i = 0;i<medicalRecord.getPrescription().getPresDetails().size();i++){
            drugService.update(medicalRecord.getPrescription().getPresDetails().get(i).getDrug());
            return false;
        }
        return true;
    }
}
