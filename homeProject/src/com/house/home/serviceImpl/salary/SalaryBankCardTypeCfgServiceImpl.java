package com.house.home.serviceImpl.salary;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.salary.SalaryBankCardTypeCfgDao;
import com.house.home.entity.salary.SalaryBankCardTypeCfg;
import com.house.home.service.salary.SalaryBankCardTypeCfgService;

@SuppressWarnings("serial")
@Service 
public class SalaryBankCardTypeCfgServiceImpl extends BaseServiceImpl implements SalaryBankCardTypeCfgService {
	@Autowired
	private  SalaryBankCardTypeCfgDao salaryBankCardTypeCfgDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,
			SalaryBankCardTypeCfg salaryBankCardTypeCfg) {
		return salaryBankCardTypeCfgDao.findPageBySql(page,salaryBankCardTypeCfg);
	}

	@Override
	public boolean checkSalaryBankCardTypeCfg(
			SalaryBankCardTypeCfg salaryBankCardTypeCfg, String m_umState) {
		
		return salaryBankCardTypeCfgDao.checkSalaryBankCardTypeCfg(salaryBankCardTypeCfg,m_umState);
	}

	
}
