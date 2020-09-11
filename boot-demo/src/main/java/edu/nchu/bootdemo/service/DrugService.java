package edu.nchu.bootdemo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.nchu.bootdemo.dao.DrugDao;
import edu.nchu.bootdemo.model.Drug;
import edu.nchu.bootdemo.model.Employee;
import edu.nchu.bootdemo.model.PageCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service

public class DrugService {
    @Autowired
    private DrugDao drugDao;

    public boolean insert(Drug drug) {
        drug.setUpdateTime(new Date());
        return drugDao.insert(drug) > 0;
    }

    public boolean update(Drug drug) {
        drug.setUpdateTime(new Date());
        return drugDao.update(drug) > 0;
    }

    public boolean delete(String id) {
        return drugDao.delete(id) > 0;
    }

    public Drug findById(String id) {
        return drugDao.findById(id);
    }

    public List<Drug> findList(Drug condition) {
        return drugDao.findList(condition);
    }

    /**
     * 分页方法
     *
     * @param pageCondition 分页相关信息和查询条件
     * @return
     */
    public PageInfo<Drug> page(PageCondition<Drug> pageCondition) {
        return PageHelper.startPage(pageCondition.getPageNum(), pageCondition.getPageSize())
                .doSelectPageInfo(() -> drugDao.findList(pageCondition.getCondition()));
    }

    public Drug findLOne(Drug condition) {
        return drugDao.findOne(condition);
    }
}
