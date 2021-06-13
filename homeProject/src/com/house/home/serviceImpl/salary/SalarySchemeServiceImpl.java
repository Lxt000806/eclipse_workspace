package com.house.home.serviceImpl.salary;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.salary.SalarySchemeDao;
import com.house.home.entity.salary.SalaryScheme;
import com.house.home.service.salary.SalarySchemeService;

@SuppressWarnings("serial")
@Service 
public class SalarySchemeServiceImpl extends BaseServiceImpl implements SalarySchemeService {
	@Autowired
	private  SalarySchemeDao salarySchemeDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SalaryScheme salaryScheme) {
		
		return salarySchemeDao.findPageBySql(page, salaryScheme);
	}
	
	@Override
	public Page<Map<String, Object>> findSalaryItemBySql(
			Page<Map<String, Object>> page, SalaryScheme salaryScheme) {
		
		return salarySchemeDao.findSalaryItemBySql(page, salaryScheme);
	}
	
	@Override
	public Page<Map<String, Object>> findEmpListBySql(
			Page<Map<String, Object>> page, SalaryScheme salaryScheme) {
		
		return salarySchemeDao.findEmpListBySql(page, salaryScheme);
	}
	
	@Override
	public Page<Map<String, Object>> findPaymentListBySql(
			Page<Map<String, Object>> page, SalaryScheme salaryScheme) {
		
		return salarySchemeDao.findPaymentListBySql(page, salaryScheme);
	}
	
	@Override
	public Page<Map<String, Object>> findSchemeItemBySql(
			Page<Map<String, Object>> page, SalaryScheme salaryScheme) {
		
		return salarySchemeDao.findSchemeItemBySql(page, salaryScheme);
	}
	
	@Override
	public Result doSave(SalaryScheme salaryScheme) {
		
		return salarySchemeDao.doSave(salaryScheme);
	}

	@Override
	public Result doSavePayment(SalaryScheme salaryScheme) {

		return salarySchemeDao.doSavePayment(salaryScheme);
	}

}
