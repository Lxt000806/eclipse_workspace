package com.house.home.service.salary;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryItem;
import com.house.home.entity.salary.SalaryScheme;

public interface SalarySchemeService extends BaseService{
	
	public Page<Map<String, Object>>  findPageBySql(Page<Map<String,Object>> page, SalaryScheme salaryScheme);
	
	public Page<Map<String, Object>>  findSalaryItemBySql(Page<Map<String,Object>> page, SalaryScheme salaryScheme);
	
	public Page<Map<String, Object>>  findEmpListBySql(Page<Map<String,Object>> page, SalaryScheme salaryScheme);

	public Page<Map<String, Object>>  findPaymentListBySql(Page<Map<String,Object>> page, SalaryScheme salaryScheme);

	public Page<Map<String, Object>>  findSchemeItemBySql(Page<Map<String,Object>> page, SalaryScheme salaryScheme);
	
	public Result doSave(SalaryScheme salaryScheme);

	public Result doSavePayment(SalaryScheme salaryScheme);

}
