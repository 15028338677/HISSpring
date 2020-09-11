package edu.nchu.bootdemo.dao;

import edu.nchu.bootdemo.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordDao {

    MedicalRecord findById(String id);

    List<MedicalRecord> findAll();

    List<MedicalRecord> findList(MedicalRecord condition);

    boolean insert_to_prescription(MedicalRecord medicalRecord);

    boolean insert_to_mr_drug(MedicalRecord medicalRecord);

    List<MedicalRecord> findByName(String name);

    Integer insert(MedicalRecord medicalRecord);

    List<MedicalRecord> findCondition(MedicalRecord condition);

    List<MedicalRecord> findIsDrugCondition(MedicalRecord condition);

    List<MedicalRecord> findByPresId(String id);
}
