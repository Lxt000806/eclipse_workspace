package com.house.home.serviceImpl.salary;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.salary.SalaryCalcReportDao;
import com.house.home.entity.salary.SalaryData;
import com.house.home.service.salary.SalaryCalcReportService;

@SuppressWarnings("serial")
@Service 
public class SalaryCalcReportServiceImpl extends BaseServiceImpl implements SalaryCalcReportService{

	@Autowired
	private SalaryCalcReportDao salaryCalcReportDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SalaryData salaryData) {

		return salaryCalcReportDao.findPageBySql(page, salaryData);
	}

	
}
