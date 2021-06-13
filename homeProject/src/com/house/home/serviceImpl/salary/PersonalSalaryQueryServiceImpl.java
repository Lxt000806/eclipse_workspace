package com.house.home.serviceImpl.salary;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.salary.PersonalSalaryQueryDao;
import com.house.home.entity.salary.SalaryData;
import com.house.home.entity.salary.SalaryEmp;
import com.house.home.service.salary.PersonalSalaryQueryService;

@SuppressWarnings("serial")
@Service
public class PersonalSalaryQueryServiceImpl extends BaseServiceImpl implements PersonalSalaryQueryService{

	@Autowired
	private PersonalSalaryQueryDao personalSalaryQueryDao;
	
	@Override
	public List<Map<String, Object>> getMainPageData(SalaryData salaryData) {

		return personalSalaryQueryDao.getMainPageData(salaryData);
	}

	@Override
	public List<Map<String, Object>> getSalaryScheme(SalaryData salaryData) {

		return personalSalaryQueryDao.getSalaryScheme(salaryData);	
	}

	
}
