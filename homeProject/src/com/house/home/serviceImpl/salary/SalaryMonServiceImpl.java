package com.house.home.serviceImpl.salary;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.salary.SalaryMonDao;
import com.house.home.entity.salary.SalaryData;
import com.house.home.entity.salary.SalaryMon;
import com.house.home.service.salary.SalaryMonService;

@SuppressWarnings("serial")
@Service
public class SalaryMonServiceImpl extends BaseServiceImpl implements SalaryMonService {
    
    @Autowired
    private SalaryMonDao salaryMonDao;

    @Override
    public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            SalaryMon salaryMon) {
        
        return salaryMonDao.findPageBySql(page, salaryMon);
    }

    @Override
	public List<Map<String, Object>> getCheckingMon() {
		return salaryMonDao.getCheckingMon();
	}
    
    @Override
	public List<Map<String, Object>> getCheckedMon() {
		return salaryMonDao.getCheckedMon();
	}

	@Override
	public List<Map<String, Object>> getLastMon(SalaryData salaryData) {
		return salaryMonDao.getLastMon(salaryData);
	}

}
