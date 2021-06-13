package com.house.home.serviceImpl.workflow;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.workflow.DepartmentDao;
import com.house.home.entity.workflow.Department;
import com.house.home.service.workflow.DepartmentService;

@SuppressWarnings("serial")
@Service
public class DepartmentServiceImpl extends BaseServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentDao departmentDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Department department, UserContext uc){
		return departmentDao.findPageBySql(page, department, uc);
	}

	@Override
	public Page<Map<String, Object>> findEmpBySql(Page<Map<String, Object>> page, Department department) {
		return departmentDao.findEmpBySql(page, department);
	}

	@Override
	public Result doSaveProc(Department department) {
		return departmentDao.doSaveProc(department);
	}

	@Override
	public List<Map<String, Object>> findLowerDeptBySql(String code) {
		return departmentDao.findLowerDeptBySql(code);
	}

	@Override
	public List<Map<String, Object>> hasEmp(String code) {
		return departmentDao.hasEmp(code);
	}

	@Override
	public List<Map<String, Object>> findDepartmentNoExpired(){
		return departmentDao.findDepartmentNoExpired();
	}
	@Override
	public Page<Map<String, Object>> findDeptList(Page<Map<String, Object>> page,String czybh,String desc2,String containsChild) {
		return departmentDao.findDeptList(page,czybh, desc2,containsChild);
	}

	@Override
	public Page<Map<String, Object>> findDept1List(Page<Map<String, Object>> page,String desc2) {
		return departmentDao.findDept1List(page, desc2);
	}

	@Override
	public Page<Map<String, Object>> findChildDeptList(Page<Map<String, Object>> page,String department,String desc2) {
		return departmentDao.findChildDeptList(page,department, desc2);
	}

	@Override
	public Page<Map<String, Object>> findEmpList(
			Page<Map<String, Object>> page, String department,String nameChi) {
		return departmentDao.findEmpList(page, department,nameChi);
	}

	@Override
	public String getChildDeptStr(String number) {
		return departmentDao.getChildDeptStr(number);
	}
}
