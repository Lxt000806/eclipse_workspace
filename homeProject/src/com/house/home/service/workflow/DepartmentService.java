package com.house.home.service.workflow;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.workflow.Department;

public interface DepartmentService extends BaseService {

	/**Department分页信息
	 * @param page
	 * @param department
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Department department,UserContext uc);
	/**
	 * DepEmp分页信息
	 * 
	 * @param page
	 * @param department
	 * @return
	 */
	public Page<Map<String, Object>> findEmpBySql(Page<Map<String, Object>> page, Department department);
	/**
	 * 保存
	 * 
	 * @param department
	 * @return
	 */
	public Result doSaveProc(Department department);
	/**
	 * 所有子部门
	 * 
	 * @param page
	 * @param department
	 * @return
	 */
	public List<Map<String, Object>> findLowerDeptBySql(String code);
	/**
	 * 是否有部门人员
	 * 
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> hasEmp(String code);
	
	public List<Map<String, Object>> findDepartmentNoExpired();
	
	/**
	 * 本部门+权限部门
	 * @author cjg
	 * @date 2020-3-2
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findDeptList(Page<Map<String, Object>> page, String czybh,String desc2,String containsChild);
	
	/**
	 * 查询所有一级部门
	 * @author cjg
	 * @date 2020-3-2
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findDept1List(Page<Map<String, Object>> page,String desc2);
	
	/**
	 * 查询子部门
	 * @author cjg
	 * @date 2020-3-2
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findChildDeptList(Page<Map<String, Object>> page, String department,String desc2);
	/**
	 * 查询员工
	 * @author cjg
	 * @date 2020-3-2
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findEmpList(Page<Map<String, Object>> page, String department,String nameChi);
	/**
	 * 获取子部门，以逗号拼接
	 * @param number
	 * @return
	 */
	public String getChildDeptStr(String number);
}
