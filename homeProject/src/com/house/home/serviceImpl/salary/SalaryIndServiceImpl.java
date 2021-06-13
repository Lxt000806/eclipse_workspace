package com.house.home.serviceImpl.salary;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.salary.SalaryIndDao;
import com.house.home.entity.salary.SalaryInd;
import com.house.home.service.salary.SalaryIndService;

@SuppressWarnings("serial")
@Service 
public class SalaryIndServiceImpl extends BaseServiceImpl implements SalaryIndService {
	@Autowired
	private  SalaryIndDao salaryIndDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SalaryInd salaryInd) {
		return salaryIndDao.findPageBySql(page,salaryInd);
	}

	@Override
	public boolean checkSalaryIndDescr(String descr, String Code, String m_umState) {
	
		return salaryIndDao.checkSalaryIndDescr(descr, Code, m_umState);
	}

	
	
}
