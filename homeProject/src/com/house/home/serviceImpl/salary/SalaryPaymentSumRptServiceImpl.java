package com.house.home.serviceImpl.salary;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.salary.SalaryPaymentSumRptDao;
import com.house.home.entity.salary.SalaryPayment;
import com.house.home.service.salary.SalaryPaymentSumRptService;

@SuppressWarnings("serial")
@Service
public class SalaryPaymentSumRptServiceImpl extends BaseServiceImpl implements SalaryPaymentSumRptService{

	@Autowired
	private SalaryPaymentSumRptDao salaryPaymentSumRptDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SalaryPayment salaryPayment) {
		
		return salaryPaymentSumRptDao.findPageBySql(page, salaryPayment);
	}

	@Override
	public List<Map<String, Object>> getMainPageData(SalaryPayment salaryPayment) {

		return salaryPaymentSumRptDao.getMainPageData(salaryPayment);
	}

	@Override
	public List<Map<String, Object>> getPaymentDefList(
			SalaryPayment salaryPayment) {

		return salaryPaymentSumRptDao.getPaymentDefList(salaryPayment);
	}
	
}
