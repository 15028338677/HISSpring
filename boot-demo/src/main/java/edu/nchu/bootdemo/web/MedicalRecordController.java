package edu.nchu.bootdemo.web;

import com.github.pagehelper.PageInfo;
import com.nimbusds.jose.JOSEException;
import edu.nchu.bootdemo.model.*;
import edu.nchu.bootdemo.service.DrugService;
import edu.nchu.bootdemo.service.EmployeeService;
import edu.nchu.bootdemo.service.MedicalRecordService;
import edu.nchu.bootdemo.service.PrescriptionService;
import edu.nchu.bootdemo.util.IdWorker;
import edu.nchu.bootdemo.util.JwtException;
import edu.nchu.bootdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private EmployeeService employeeService;


    @Autowired
    private IdWorker idWorker;

    @Autowired
    private DrugService drugService;

    /**
     * 插入数据
     */
    @RequestMapping(value = "/mr/appoint", method = RequestMethod.POST)
    public boolean insert(@RequestBody MedicalRecord medicalRecord) {
        medicalRecord.setId(idWorker.nextId() + "");
        medicalRecord.setUpdateTime(new Date());
        medicalRecord.setDelFlag("0");
        return medicalRecordService.insert(medicalRecord);
    }

    /**
     * 增加处方信息
     *
     * @param medicalRecord
     * @return
     */
    @PostMapping("/mr")
    public boolean insert2(@RequestBody MedicalRecord medicalRecord, HttpServletRequest request) {
        //1. 先获取token，从请求头中获取token
        String token = request.getHeader("access-token");
        //如果验证成功，返回一个代表当前登录用户的employee对象
        Employee employee = null;
        try {
            employee = JwtUtil.validToken(token);
        } catch (ParseException | JOSEException | JwtException e) {
            e.printStackTrace();
        }
        if (employee != null) {
            // 获取当前医生id
            String doctorId = employee.getId();
            // 根据id查询医生信息
            employee = employeeService.findById(doctorId);
            // 获取当前医生所属部门
            Department depart = employee.getDepartment();
            // 给medicalRecord赋值
            Prescription prescription = medicalRecord.getPrescription();
            prescription.setDoctor(employee);
            prescription.setDepartment(depart);
            prescription.setPresId(idWorker.nextId() + "");
            medicalRecord.setUpdateTime(new Date());
            medicalRecord.setPrescription(prescription);
        }
        return medicalRecordService.insert2(medicalRecord);
    }

    @GetMapping("/mr/{id}")
    public MedicalRecord findById(@PathVariable String id) {
        return medicalRecordService.findById(id);
    }

    @GetMapping("/mr/all")
    public List<MedicalRecord> findAll() {
        return medicalRecordService.findAll();
    }

    @PostMapping("/mr/list")
    public List<MedicalRecord> findList(@RequestBody MedicalRecord condition) {
        return medicalRecordService.findList(condition);
    }

    /**
     * 待条件的分页查询
     *
     * @param pageCondition 分页
     * @return
     */
    @PostMapping("/mr/page")
    public PageInfo<MedicalRecord> page(@RequestBody PageCondition<MedicalRecord> pageCondition) {
        return medicalRecordService.page(pageCondition);
    }

    /**
     * 根据患者name模糊查询
     *
     * @param name 患者姓名
     * @return 模糊查询的结果list
     */
    @GetMapping("/mr/name/{name}")
    public List<MedicalRecord> findByName(@PathVariable String name) {
        return medicalRecordService.findByName(name);
    }

    /**
     * 根据条件查询病历信息
     *
     * @param condition
     * @return
     */
    @PostMapping("/mr/condition")
    public List<MedicalRecord> findCondition(@RequestBody MedicalRecord condition) {
        return medicalRecordService.findCondition(condition);
    }

    /**
     * 开具处方查询患者信息
     * 只查询没有开具处方的患者
     *
     * @param id
     * @return
     */
    @GetMapping("/mr/id/{id}")
    public List<MedicalRecord> findByPresId(@PathVariable String id) {
        return medicalRecordService.findByPresId(id);
    }

    /**
     * 根据条件查询病历信息 查询没有发药的
     *
     * @param condition
     * @return
     */
    @PostMapping("/mr/isDrug/condition")
    public List<MedicalRecord> findConditionDrug(@RequestBody MedicalRecord condition) {
        return medicalRecordService.findIsDrugCondition(condition);
    }

    /**
     * 缴费
     *
     * @param medicalRecord
     * @return
     */
    @PutMapping("/mr/pay")
    public boolean updatePay(@RequestBody MedicalRecord medicalRecord) {
        Prescription prescription = medicalRecord.getPrescription();
        prescription.setPayTime(new Date());
        prescription.setStatus("1");
        return prescriptionService.update(prescription);
    }

    /**
     * 开药
     *
     * @param medicalRecord
     * @return
     */
    @PutMapping("/mr/out")
    public boolean updateOutDrug(@RequestBody MedicalRecord medicalRecord) {
        Prescription prescription = medicalRecord.getPrescription();
        prescription.setStatus("2");
        if (!prescriptionService.update(prescription)) {
            return false;
        }
        for (int i = 0; i < medicalRecord.getPrescription().getPresDetails().size(); i++) {
            if (!drugService.update(medicalRecord.getPrescription().getPresDetails().get(i).getDrug()))
                return false;
        }
        return true;
    }
}
