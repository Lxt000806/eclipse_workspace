package com.house.home.serviceImpl.salary;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.salary.SalaryAdvanceDao;
import com.house.home.entity.salary.EmpAdvanceWage;
import com.house.home.service.salary.SalaryAdvanceService;

@SuppressWarnings("serial")
@Service 
public class SalaryAdvanceServiceImpl extends BaseServiceImpl implements SalaryAdvanceService {
	@Autowired
	private  SalaryAdvanceDao salaryAdvanceDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, EmpAdvanceWage empAdvanceWage) {
		return salaryAdvanceDao.findPageBySql(page, empAdvanceWage);
	}

}
