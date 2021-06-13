package com.house.home.serviceImpl.salary;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.salary.SalaryPosiClassDao;
import com.house.home.entity.salary.SalaryPosiClass;
import com.house.home.service.salary.SalaryPosiClassService;

@SuppressWarnings("serial")
@Service
public class SalaryPosiClassServiceImpl extends BaseServiceImpl implements SalaryPosiClassService {
    
    @Autowired
    private SalaryPosiClassDao salaryPosiClassDao;

    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            SalaryPosiClass salaryPosiClass) {
        
        return salaryPosiClassDao.findPageBySql(page, salaryPosiClass);
    }


}
