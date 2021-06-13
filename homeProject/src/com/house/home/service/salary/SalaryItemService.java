package com.house.home.service.salary;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryItem;

public interface SalaryItemService extends BaseService{
	
	public Page<Map<String, Object>>  findPageBySql(Page<Map<String,Object>> page, SalaryItem salaryItem);

	public Page<Map<String, Object>>  findCategoryDefindPageBySql(Page<Map<String,Object>> page, SalaryItem salaryItem);
	
	public List<Map<String, Object>>   findFormulaNodeBySql(Page<Map<String,Object>> page, SalaryItem salaryItem);

	public boolean checkSalaryItemDescr(SalaryItem salaryItem,String m_umStatus);
	
	public Map<String, Object> getOperatorCfg();
	
}
