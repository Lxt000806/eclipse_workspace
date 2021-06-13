package com.house.home.serviceImpl.salary;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.salary.SalaryDataDao;
import com.house.home.entity.salary.SalaryData;
import com.house.home.service.salary.SalaryDataService;

@SuppressWarnings("serial")
@Service 
public class SalaryDataServiceImpl extends BaseServiceImpl implements SalaryDataService {
	@Autowired
	private  SalaryDataDao salaryDataDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SalaryData salaryData) {
		// TODO Auto-generated method stub
		return salaryDataDao.findPageBySql(page, salaryData);
	}

	
	
}
