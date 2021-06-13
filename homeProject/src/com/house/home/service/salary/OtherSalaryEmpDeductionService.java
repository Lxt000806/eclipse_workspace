package com.house.home.service.salary;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryEmpDeduction;

public interface OtherSalaryEmpDeductionService extends BaseService {

	/**OtherSalaryEmpDeduction分页信息
	 * @param page
	 * @param otherSalaryEmpDeduction
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmpDeduction salaryEmpDeduction);
	
}
