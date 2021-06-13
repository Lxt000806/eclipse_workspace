package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.EmpTranLog;
import com.house.home.entity.basic.Employee;

public interface EmployeeService extends BaseService {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Employee employee,UserContext uc);
	
	public Employee getByPhoneAndMm(String phone, String mm);

	public Employee getByPhoneWithoutMM(String phone);

	public List<Employee> getByphone(String phone);

	public Page<Map<String,Object>> findPageBySql_forClient(Page<Map<String,Object>> page, Employee employee);
	
	public Page<Map<String,Object>> findPageByName(Page<Map<String,Object>> page, String name);
	
	/**
	 * 根据操作员编号获取直接领导
	 * @param userId
	 * @return
	 */
	public List<String> getDeptLeaders(String userId);
	
	public List<Map<String,Object>> getDeptLeadersList(String userId);

	public List<Map<String,Object>> findByNoExpired();
	
	public List<Map<String, Object>> getErpCzy();
	
	public List<String> getErpCzyList();

	public String getDepLeader(String code);

	// 20200415 mark by xzp 获取权限的代码统一到CzybmService
//	public Map<String, Object> getCZYGNQX(String czybh, String mkdm, String gnmc);
	
	/**
	 * 获取该员工的部门领导
	 * @param empNum
	 * @return
	 */
	public Employee getDepLeaderByEmpNum(String empNum);
	
	public List<Map<String,Object>> getProTypeOpt(String postype);	
	
	public Map<String, Object> validNameChi(String nameChi);
	
	/**
	 * 判断除某个员工编号外的记录中是否存在某个中文名
	 * 
	 * @param nameChi 中文名
	 * @param number 员工编号
	 * @return
	 * @author 张海洋
	 */
	public boolean existsNameChiExceptNumber(String nameChi, String number);
	
	/**
	 * 判断除某个员工编号外的记录中是否存在某个身份证号
	 * 
	 * @param idNum 身份证号
	 * @param number 员工编号
	 * @return
	 * @author 张海洋
	 */
	public boolean existsIdNumExceptNumber(String idNum, String number, String type);
	
	public Map<String, Object> validNum(String number,String idnum);
	public Result doEmpforInfo(Employee employee);
	//导出图片
	public List<Map<String, Object>> export();
	//获取最新的图片
	public Map<String,Object> getPhotoName(String number);
	/**
	 * 根据所属部门编号获取一二三级部门编号
	 * @param code
	 * @param tableName
	 * @return
	 */
	public String getCodeByDept(String tableName,String code);
	
	public List<Map<String, Object>> findEmployeeExpired();
	
	public boolean hasEmpAuthority(String czybh,String empCode );
	/**
	 * 获取员工领导的部门
	 * @param number
	 * @return
	 */
	public Map<String, Object> getDeptByLeader(String number);
	
	/**
	 * 获取员工修改历史记录
	 * @param empTranLog
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_empTranLog(Page<Map<String,Object>> page, EmpTranLog empTranLog);
		
	public Result doUpdateEmpStatus(Employee employee);
	
}
