package com.house.home.service.salary;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.EmpAdvanceWage;
import com.house.home.entity.salary.SalaryInd;

public interface SalaryAdvanceService extends BaseService{

	public Page<Map<String, Object>>  findPageBySql(Page<Map<String,Object>> page, EmpAdvanceWage empAdvanceWage);

}
