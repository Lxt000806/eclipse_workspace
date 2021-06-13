package com.house.home.service.salary;

import com.house.framework.commons.orm.BaseService;

import com.house.framework.commons.orm.Page;

import java.util.List;
import java.util.Map;
import com.house.home.entity.salary.SalaryData;

public interface SalaryCompareAnalyseService extends BaseService{
	public List<Map<String,Object>> findPageBySql(SalaryData salaryData);
	
	public Page<Map<String,Object>> findJoinEmpPageBySql(Page<Map<String,Object>> page, SalaryData salaryData);
	
	public Page<Map<String,Object>> findLeaveEmpPageBySql(Page<Map<String,Object>> page, SalaryData salaryData);
	
	public Page<Map<String,Object>> findBaseSalaryCHgEmpPageBySql(Page<Map<String,Object>> page, SalaryData salaryData);
	
	public Page<Map<String,Object>> findRealPayPageBySql(Page<Map<String,Object>> page, SalaryData salaryData);
	
	public Page<Map<String,Object>> findUnPaidEmpPageBySql(Page<Map<String,Object>> page, SalaryData salaryData);

}
