package com.house.home.serviceImpl.salary;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.salary.SalaryEmpOverTimeDao;
import com.house.home.entity.salary.SalaryEmpOverTime;
import com.house.home.service.salary.SalaryEmpOverTimeService;

@SuppressWarnings("serial")
@Service
public class SalaryEmpOverTimeServiceImpl extends BaseServiceImpl implements SalaryEmpOverTimeService {

	@Autowired
	private SalaryEmpOverTimeDao salaryEmpOverTimeDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmpOverTime salaryEmpOverTime){
		return salaryEmpOverTimeDao.findPageBySql(page, salaryEmpOverTime);
	}

	@Override
	public List<Map<String, Object>> isExistsMon(SalaryEmpOverTime salaryEmpOverTime) {
		return salaryEmpOverTimeDao.isExistsMon(salaryEmpOverTime);
	}

	@Override
	public Result doSaveBatch(SalaryEmpOverTime salaryEmpOverTime) {
		return salaryEmpOverTimeDao.doSaveBatch(salaryEmpOverTime);
	}

}
