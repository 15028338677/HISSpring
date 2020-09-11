package edu.nchu.bootdemo.dao;

import edu.nchu.bootdemo.model.Drug;

import java.util.List;

public interface DrugDao {

    Integer insert(Drug drug);

    Integer update(Drug drug);

    Integer delete(String id);

    Drug findById(String id);

    List<Drug> findList(Drug condition);

    Drug findOne(Drug condition);
}
