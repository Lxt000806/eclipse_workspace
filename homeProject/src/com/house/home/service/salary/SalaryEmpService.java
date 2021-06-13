package com.house.home.service.salary;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.salary.SalaryEmp;

public interface SalaryEmpService extends BaseService {

	/**SalaryEmp分页信息
	 * @param page
	 * @param salaryEmp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmp salaryEmp);

	/**
	 * 保存
	 * 
	 * @param salaryEmpDetail
	 * @return
	 */
	public Result doSaveProc(SalaryEmp salaryEmp);
	
	/**
	 * 根据岗位类别查前后端
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getIsFront(Integer pk);
	
	/**
	 * 根据岗位级别查工资
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getSalaryByLevel(Integer pk);
	
	/**
	 * 银行卡分页信息
	 * 
	 * @param page
	 * @param salaryEmp
	 * @return
	 */
	public Page<Map<String,Object>> goBankJqGrid(Page<Map<String,Object>> page, SalaryEmp salaryEmp);
	
	/**
	 * 员工信息同步列表
	 * 
	 * @param page
	 * @param salaryEmp
	 * @return
	 */
	public Page<Map<String,Object>> goEmpSyncJqGrid(Page<Map<String,Object>> page, SalaryEmp salaryEmp);
	
	/**
	 * 员工信息同步
	 * 
	 * @param empCodes
	 */
	public void doEmpSync(String empCodes);
	
	/**
	 * 工号查出款公司明细
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getPayCmp(String empCode);
	
	/**
	 * 获取员工所适用的薪酬方案
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getSalaryScheme(String empCode);
	
	/**
	 * 根据财务编码/姓名查员工
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getEmpByParam(String category,String empName,String financialCode);
	
	/**
	 * 岗位类别，岗位级别联动
	 * @param type
	 * @param pCode
	 * @param uc
	 * @return
	 */
	public List<Map<String, Object>> findPosiByAuthority(int type,String pCode, UserContext uc);
	
}
