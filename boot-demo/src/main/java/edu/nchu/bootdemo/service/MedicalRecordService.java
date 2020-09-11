package edu.nchu.bootdemo.service;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nchu.bootdemo.dao.MedicalRecordDao;
import edu.nchu.bootdemo.model.MedicalRecord;
import edu.nchu.bootdemo.model.PageCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordDao medicalRecordDao;


    public MedicalRecord findById(String id){
        return medicalRecordDao.findById(id);
    }

    public List<MedicalRecord> findAll(){
        return medicalRecordDao.findAll();
    }

    public List<MedicalRecord> findList(MedicalRecord medicalRecord){
        return medicalRecordDao.findList(medicalRecord);
    }

    /**
     * @param pageCondition 分页对象
     * @return 返回分页信息
     */
    public PageInfo<MedicalRecord> page(PageCondition<MedicalRecord> pageCondition){
//        return PageHelper.startPage(pageCondition.getPageNum(),pageCondition.getPageSize())
//                .doSelectPageInfo(new ISelect() {
//                    @Override
//                    public void doSelect() {
//                        // 调用真正的查询语句， 又PageHelper完成分页工作
//                        medicalRecordDao.findList(pageCondition.getCondition());
//                    }
//                });
        return PageHelper.startPage(pageCondition.getPageNum(),pageCondition.getPageSize())
                .doSelectPageInfo(()->medicalRecordDao.findList(pageCondition.getCondition()));
    }

    public boolean insert2(MedicalRecord medicalRecord){
        return medicalRecordDao.insert_to_prescription(medicalRecord) && medicalRecordDao.insert_to_mr_drug(medicalRecord);
    }

    public List<MedicalRecord> findByName(String name) {
        return medicalRecordDao.findByName(name);
    }

    //挂号：插入一条数据
    public boolean insert(MedicalRecord medicalRecord) {
        medicalRecord.setUpdateTime(new Date());
        return medicalRecordDao.insert(medicalRecord) > 0;
    }

    public List<MedicalRecord> findCondition(MedicalRecord condition) {
        return medicalRecordDao.findCondition(condition);
    }

    public List<MedicalRecord> findIsDrugCondition(MedicalRecord condition) {
        return medicalRecordDao.findIsDrugCondition(condition);
    }

    public List<MedicalRecord> findByPresId(String id) {
        return medicalRecordDao.findByPresId(id);
    }
}
