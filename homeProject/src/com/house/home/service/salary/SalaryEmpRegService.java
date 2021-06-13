package com.house.home.service.salary;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryEmp;
import com.house.home.entity.salary.SalaryEmpReg;

public interface SalaryEmpRegService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmpReg salaryEmpReg);

	public List<Map<String, Object>> checkInfo(SalaryEmpReg salaryEmpReg);
}
