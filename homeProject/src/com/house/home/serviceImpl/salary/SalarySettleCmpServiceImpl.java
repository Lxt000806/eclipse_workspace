package com.house.home.serviceImpl.salary;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.salary.SalarySettleCmpDao;
import com.house.home.entity.salary.SalarySettleCmp;
import com.house.home.service.salary.SalarySettleCmpService;

@SuppressWarnings("serial")
@Service
public class SalarySettleCmpServiceImpl extends BaseServiceImpl implements SalarySettleCmpService {
    
    @Autowired
    private SalarySettleCmpDao salarySettleCmpDao;

    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            SalarySettleCmp salarySettleCmp) {
        
        return salarySettleCmpDao.findPageBySql(page, salarySettleCmp);
    }

    @Override
    public List<SalarySettleCmp> findSalarySettleCmpsByDescr(String salarySettleCmpDescr) {
        return salarySettleCmpDao.findSalarySettleCmpsByDescr(salarySettleCmpDescr);
    }

}
