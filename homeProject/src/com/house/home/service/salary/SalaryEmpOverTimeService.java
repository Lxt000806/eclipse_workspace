package com.house.home.service.salary;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryEmpOverTime;

public interface SalaryEmpOverTimeService extends BaseService {

	/**SalaryEmpOverTime分页信息
	 * @param page
	 * @param salaryEmpOverTime
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmpOverTime salaryEmpOverTime);
	
	/**
	 * 是否存在重复月份
	 * 
	 * @return
	 */
	public List<Map<String, Object>> isExistsMon(SalaryEmpOverTime salaryEmpOverTime);
	
	/**
	 * Excel导入调过程
	 * @param salaryEmpOverTime
	 * @return
	 */
	public Result doSaveBatch (SalaryEmpOverTime salaryEmpOverTime);
}
