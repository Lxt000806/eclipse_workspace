package com.house.home.serviceImpl.salary;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.salary.SalarySchemeTypeDao;
import com.house.home.entity.salary.SalarySchemeType;
import com.house.home.service.salary.SalarySchemeTypeService;

@SuppressWarnings("serial")
@Service
public class SalarySchemeTypeServiceImpl extends BaseServiceImpl implements SalarySchemeTypeService {
    
    @Autowired
    private SalarySchemeTypeDao salarySchemeTypeDao;

    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            SalarySchemeType salarySchemeType) {
        
        return salarySchemeTypeDao.findPageBySql(page, salarySchemeType);
    }

}
