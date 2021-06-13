package com.house.home.serviceImpl.salary;

import com.house.framework.commons.orm.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.salary.SalaryPaymentQueryDao;
import com.house.home.service.salary.SalaryPaymentQueryService;

import com.house.framework.commons.orm.Page;

import java.util.List;
import java.util.Map;
import com.house.home.entity.salary.SalaryPayment;

@SuppressWarnings("serial")
@Service 
public class SalaryPaymentQueryServiceImpl extends BaseServiceImpl implements SalaryPaymentQueryService {
	@Autowired
	private  SalaryPaymentQueryDao salaryPaymentQueryDao;
	@Override	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, SalaryPayment salaryPayment){
	
		return salaryPaymentQueryDao.findPageBySql(page, salaryPayment);
	}
	
	@Override
	public List<Map<String, Object>> getSchemeList(SalaryPayment salaryPayment) {
		
		return salaryPaymentQueryDao.getSchemeList(salaryPayment);
	}

	@Override
	public List<Map<String, Object>> getPaymentDefList(SalaryPayment salaryPayment) {
		
		return salaryPaymentQueryDao.getPaymentDefList(salaryPayment);
	}
}
