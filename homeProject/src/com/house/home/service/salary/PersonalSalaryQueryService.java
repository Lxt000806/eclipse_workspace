package com.house.home.service.salary;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.home.entity.salary.SalaryData;

public interface PersonalSalaryQueryService extends BaseService{
	
	public List<Map<String, Object>> getMainPageData(SalaryData salaryData);

	public List<Map<String, Object>> getSalaryScheme(SalaryData salaryData);

}
