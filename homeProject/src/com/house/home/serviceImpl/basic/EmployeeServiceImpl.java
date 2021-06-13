package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.basic.EmployeeDao;
import com.house.home.dao.workflow.DepartmentDao;
import com.house.home.entity.basic.EmpTranLog;
import com.house.home.entity.basic.Employee;
import com.house.home.service.basic.EmployeeService;

@SuppressWarnings("serial")
@Service
public class EmployeeServiceImpl extends BaseServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Employee employee,UserContext uc){
		return employeeDao.findPageBySql(page, employee,uc);
	}
	public Employee getByPhoneAndMm(String phone, String mm) {
		return employeeDao.getByPhoneAndMm(phone,mm);
	}
	
	public Employee getByPhoneWithoutMM(String phone) {
		return employeeDao.getByPhoneWithoutMM(phone);
	}

	@Override
	public List<Employee> getByphone(String phone) {
		return employeeDao.getByPhone(phone);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_forClient(
			Page<Map<String, Object>> page, Employee employee) {
		return employeeDao.findPageBySql_forClient(page,employee);
	}

	@Override
	public Page<Map<String, Object>> findPageByName(
			Page<Map<String, Object>> page, String name) {
		// TODO Auto-generated method stub
		return employeeDao.findPageByName(page,name);
	}

	@Override
	public List<String> getDeptLeaders(String userId) {
		return employeeDao.getDeptLeaders(userId);
	}

	@Override
	public List<Map<String,Object>> findByNoExpired() {
		return employeeDao.findByNoExpired();
	}

	@Override
	public List<Map<String, Object>> getDeptLeadersList(String userId) {
		return employeeDao.getDeptLeadersList(userId);
	}

	@Override
	public List<Map<String, Object>> getErpCzy() {
		// TODO Auto-generated method stub
		return employeeDao.getErpCzy();
	}

	@Override
	public List<String> getErpCzyList() {
		// TODO Auto-generated method stub
		return employeeDao.getErpCzyList();
	}

	@Override
	public String getDepLeader(String code) {
		// TODO Auto-generated method stub
		return employeeDao.getDepLeader(code);
	}

	// 20200415 mark by xzp 获取权限的代码统一到CzybmService
//	@Override
//	public Map<String, Object> getCZYGNQX(String czybh, String mkdm, String gnmc){
//		return employeeDao.getCZYGNQX(czybh, mkdm, gnmc);
//	}
	
	@Autowired
	private DepartmentDao departmentDao;
	

	@Override
	public Employee getDepLeaderByEmpNum(String empNum) {
		String leaderCode = departmentDao.getLeaderCodeByEmpCode(empNum);
		
		// Bug修复 张海洋 20200604
		if (StringUtils.isBlank(leaderCode)) {
            return null;
        }
		
		return employeeDao.get(Employee.class, leaderCode);
	}

	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	@Override
	public List<Map<String, Object>> getProTypeOpt(String postype) {
		// TODO Auto-generated method stub
		return employeeDao.getProTypeOpt(postype);
	}

	@Override
	public Map<String, Object> validNameChi(String nameChi) {
		// TODO Auto-generated method stub
		return employeeDao.validNameChi(nameChi);
	}

	@Override
	public Map<String, Object> validNum(String number,String idnum) {
		// TODO Auto-generated method stub
		return employeeDao.validNum(number,idnum);
	}

	@Override
	public Result doEmpforInfo(Employee employee) {
		// TODO Auto-generated method stub
		return employeeDao.doEmpforInfo(employee);
	}
	@Override
	public List<Map<String, Object>> export() {
		// TODO Auto-generated method stub
		return employeeDao.export();
	}
	@Override
	public Map<String, Object> getPhotoName(String number) {
		// TODO Auto-generated method stub
		return employeeDao.getPhotoName(number);
	}
	@Override
	public String getCodeByDept(String tableName, String code) {
		return employeeDao.getCodeByDept(tableName, code);
	}
	
	@Override
	public List<Map<String, Object>> findEmployeeExpired(){
		return employeeDao.findEmployeeExpired();
	}
	public boolean hasEmpAuthority(String czybh,String empCode ){
		return employeeDao.hasEmpAuthority(czybh, empCode);
	}
	@Override
	public Map<String, Object> getDeptByLeader(String number) {
		return employeeDao.getDeptByLeader(number);
	}
	@Override
	public Page<Map<String, Object>> findPageBySql_empTranLog(
			Page<Map<String, Object>> page, EmpTranLog empTranLog) {
		return employeeDao.findPageBySql_empTranLog(page, empTranLog);
	}
	
    @Override
    public boolean existsNameChiExceptNumber(String nameChi, String number) {      
        return employeeDao.existsNameChiExceptNumber(nameChi, number);
    }
    
    @Override
    public boolean existsIdNumExceptNumber(String idNum, String number, String type) {
        return employeeDao.existsIdNumExceptNumber(idNum, number, type);
    }
    
    @Override
    public Result doUpdateEmpStatus(Employee employee){
    	return employeeDao.doUpdateEmpStatus(employee);
    }

}
