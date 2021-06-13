package com.house.home.service.salary;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryInd;

public interface SalaryIndService extends BaseService{

	public Page<Map<String, Object>>  findPageBySql(Page<Map<String,Object>> page, SalaryInd salaryInd);

	public boolean checkSalaryIndDescr(String descr, String Code, String m_umState);
	
}
