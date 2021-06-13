package com.house.home.service.salary;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryEmpAttendance;

public interface SalaryEmpAttendanceService extends BaseService {

	/**SalaryEmpAttendance分页信息
	 * @param page
	 * @param salaryEmpAttendance
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmpAttendance salaryEmpAttendance);
	
	/**
	 * 批量删除
	 * 
	 * @param salaryMon
	 */
	public Long doDeleteBatch(Integer salaryMon);
	
	/**
	 * 是否存在重复月份
	 * 
	 * @return
	 */
	public List<Map<String, Object>> isExistsMon(SalaryEmpAttendance salaryEmpAttendance);
	
	/**
	 * Excel导入调过程
	 * @param salaryEmpAttendance
	 * @return
	 */
	public Result doSaveBatch (SalaryEmpAttendance salaryEmpAttendance);
	
	public Integer getDefSalaryMon();
}
