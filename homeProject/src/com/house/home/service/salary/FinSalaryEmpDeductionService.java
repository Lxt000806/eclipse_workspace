package com.house.home.service.salary;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryEmpDeduction;

public interface FinSalaryEmpDeductionService extends BaseService {

	/**FinSalaryEmpDeduction分页信息
	 * @param page
	 * @param finSalaryEmpDeduction
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmpDeduction salaryEmpDeduction);
	
	/**
	 * 查扣款科目
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getDeductType2(String deductType2);
	
	/**
	 * Excel导入调过程
	 * @param finSalaryEmpDeduction
	 * @return
	 */
	public Result doSaveBatch (SalaryEmpDeduction finSalaryEmpDeduction);
}
