package edu.nchu.bootdemo.service;

import edu.nchu.bootdemo.dao.PrescriptionDao;
import edu.nchu.bootdemo.model.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionDao prescriptionDao;

    public boolean update(Prescription prescription) {
        return prescriptionDao.update(prescription) == 1;
    }
}
