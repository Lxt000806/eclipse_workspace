package com.house.home.serviceImpl.salary;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.home.dao.salary.SalaryPosiLevelDao;
import com.house.home.entity.salary.SalaryPosiLevel;
import com.house.home.service.salary.SalaryPosiLevelService;

@SuppressWarnings("serial")
@Service
public class SalaryPosiLevelServiceImpl extends BaseServiceImpl implements SalaryPosiLevelService {
    
    @Autowired
    private SalaryPosiLevelDao salaryPosiLevelDao;

    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            SalaryPosiLevel salaryPosiLevel) {
        
        return salaryPosiLevelDao.findPageBySql(page, salaryPosiLevel);
    }

    @Override
    public List<SalaryPosiLevel> findLevelsBySalaryPosiClass(Integer salaryPosiClassPk) {
        List<Map<String, Object>> levels =
                salaryPosiLevelDao.findLevelsBySalaryPosiClass(salaryPosiClassPk);

        return BeanConvertUtil.mapToBeanList(levels, SalaryPosiLevel.class);
    }

    @Override
    public void doUpdate(SalaryPosiLevel level) {
        salaryPosiLevelDao.updateSalaryPosiLevel(level);
        salaryPosiLevelDao.updateSalaryEmpsSalary(level);
    }

}
