package com.house.home.service.salary;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryEmpPension;

public interface SalaryEmpPensionService extends BaseService {

	/**SalaryEmpPension分页信息
	 * @param page
	 * @param salaryEmpPension
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmpPension salaryEmpPension);
	
	/**
	 * 月份区间是否重叠
	 * 
	 * @return
	 */
	public List<Map<String, Object>> isExistsMon(SalaryEmpPension salaryEmpPension);
	
	public Result doImportSalaryEmpPension(SalaryEmpPension salaryEmpPension);
	
	public List<Map<String, Object>> isCheckMon(SalaryEmpPension salaryEmpPension);
}
