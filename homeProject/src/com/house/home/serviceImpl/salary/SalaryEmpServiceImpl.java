package com.house.home.serviceImpl.salary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.salary.SalaryEmpDao;
import com.house.home.entity.salary.SalaryEmp;
import com.house.home.service.salary.SalaryEmpService;

@SuppressWarnings("serial")
@Service
public class SalaryEmpServiceImpl extends BaseServiceImpl implements SalaryEmpService {

	@Autowired
	private SalaryEmpDao salaryEmpDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmp salaryEmp){
		return salaryEmpDao.findPageBySql(page, salaryEmp);
	}

	@Override
	public Result doSaveProc(SalaryEmp salaryEmp) {
		return salaryEmpDao.doSaveProc(salaryEmp);
	}

	@Override
	public List<Map<String, Object>> getIsFront(Integer pk) {
		return salaryEmpDao.getIsFront(pk);
	}

	@Override
	public List<Map<String, Object>> getSalaryByLevel(Integer pk) {
		return salaryEmpDao.getSalaryByLevel(pk);
	}

	@Override
	public Page<Map<String, Object>> goBankJqGrid(Page<Map<String, Object>> page, SalaryEmp salaryEmp) {
		return salaryEmpDao.goBankJqGrid(page, salaryEmp);
	}

	@Override
	public Page<Map<String, Object>> goEmpSyncJqGrid(Page<Map<String, Object>> page, SalaryEmp salaryEmp) {
		return salaryEmpDao.goEmpSyncJqGrid(page, salaryEmp);
	}

	@Override
	public void doEmpSync(String empCodes) {
		salaryEmpDao.doEmpSync(empCodes);
	}

	@Override
	public List<Map<String, Object>> getPayCmp(String empCode) {
		return salaryEmpDao.getPayCmp(empCode);
	}

	@Override
	public List<Map<String, Object>> getSalaryScheme(String empCode) {
		return salaryEmpDao.getSalaryScheme(empCode);
	}

	@Override
	public List<Map<String, Object>> getEmpByParam(String category,String empName,String financialCode) {
		return salaryEmpDao.getEmpByParam(category, empName,financialCode);
	}

	@Override
	public List<Map<String, Object>> findPosiByAuthority(int type,String pCode, UserContext uc) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			String itemRight="";
			for(String str:uc.getItemRight().trim().split(",")){
				itemRight+="'"+str+"',";			
			}
			param.put("pCode", itemRight.substring(0, itemRight.length()-1));
			resultList = this.salaryEmpDao.findPosiClass(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.salaryEmpDao.findPosiLevel(param);
		}
		return resultList;
	}

}
