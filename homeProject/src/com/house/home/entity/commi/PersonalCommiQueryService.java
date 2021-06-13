package com.house.home.entity.commi;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.salary.SalaryData;

public interface PersonalCommiQueryService extends BaseService{
	
	public List<Map<String, Object>> getMainPageData(Employee employee);

}
