package com.house.home.serviceImpl.salary;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.salary.SalaryEmpRegDao;
import com.house.home.entity.salary.SalaryEmpReg;
import com.house.home.service.salary.SalaryEmpRegService;

@SuppressWarnings("serial")
@Service 
public class SalaryEmpRegServiceImpl extends BaseServiceImpl implements SalaryEmpRegService {
	@Autowired
	private  SalaryEmpRegDao salaryEmpRegDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SalaryEmpReg salaryEmpReg) {

		return salaryEmpRegDao.findPageBySql(page, salaryEmpReg);
	}

	@Override
	public List<Map<String, Object>> checkInfo(SalaryEmpReg salaryEmpReg) {

		return salaryEmpRegDao.checkInfo(salaryEmpReg);
	}
}
