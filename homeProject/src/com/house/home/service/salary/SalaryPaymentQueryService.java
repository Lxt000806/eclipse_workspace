package com.house.home.service.salary;

import com.house.framework.commons.orm.BaseService;

import com.house.framework.commons.orm.Page;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryPayment;

public interface SalaryPaymentQueryService extends BaseService{
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryPayment salaryPayment);
	
	public List<Map<String, Object>> getSchemeList(SalaryPayment salaryPayment);

	public List<Map<String, Object>> getPaymentDefList(SalaryPayment salaryPayment);

}
