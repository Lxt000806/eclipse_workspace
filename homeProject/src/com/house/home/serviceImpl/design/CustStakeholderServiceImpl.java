package com.house.home.serviceImpl.design;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.design.CustStakeholderDao;
import com.house.home.entity.design.CustStakeholder;
import com.house.home.service.design.CustStakeholderService;

@SuppressWarnings("serial")
@Service
public class CustStakeholderServiceImpl extends BaseServiceImpl implements CustStakeholderService {

	@Autowired
	private CustStakeholderDao custStakeholderDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustStakeholder custStakeholder,UserContext uc){
		return custStakeholderDao.findPageBySql(page, custStakeholder,uc);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_khxx(
			Page<Map<String, Object>> page, CustStakeholder custStakeholder) {
		return custStakeholderDao.findPageBySql_khxx(page,custStakeholder);
	}

	@Override
	public Result updateForProc(CustStakeholder custStakeholder) {
		return custStakeholderDao.updateForProc(custStakeholder);
	}

	@Override
	public int getCount(String custCode, String role) {
		return custStakeholderDao.getCount(custCode,role);
	}
	
	@Override
	public List<Map<String,Object>> getListByCustCodeAndRole(String custCode, String role){
		return custStakeholderDao.getListByCustCodeAndRole(custCode,role);
	}

	@Override
	public int getPkByCustCodeAndRole(String custCode, String role) {
		return custStakeholderDao.getPkByCustCodeAndRole(custCode,role);
	}

	@Override
	public boolean phoneShow(String custCode, String empCode, String status) {
		return custStakeholderDao.phoneShow(custCode,empCode,status);
	}
	
	@Override
	public void doDelInteEmp(String  code){
		custStakeholderDao.doDelInteEmp(code);
	}
	
	@Override
	public void doDelCGEmp(Integer pk){
		custStakeholderDao.doDelCGEmp(pk);
	}

	@Override
	public Map<String, Object> getStakeholderInfo(String custCode) {
		return custStakeholderDao.getStakeholderInfo(custCode);
	}

	@Override
	public Result updateGcxxglDesigner(CustStakeholder custStakeholder) {
		return custStakeholderDao.updateGcxxglDesigner(custStakeholder);
	}
	
	@Override
	public Result doUpdateMainManager(CustStakeholder custStakeholder) {
		return custStakeholderDao.doUpdateMainManager(custStakeholder);
	}

	@Override
	public Result doSave(CustStakeholder custStakeholder) {
		return custStakeholderDao.updateForProc(custStakeholder);
	}

	@Override
	public int onlyBusinessMan(String custCode) {
		return custStakeholderDao.onlyBusinessMan(custCode);
	}

	@Override
	public int onlyDesigner(String custCode) {
		return custStakeholderDao.onlyDesigner(custCode);
	}

	@Override
	public String getRoleByCustCodeAndEmpCode(String custCode, String empCode) {
		return custStakeholderDao.getRoleByCustCodeAndEmpCode(custCode,empCode);
	}

	@Override
	public String getStakeholderLinkedByRole(String custCode, String role) {
		return custStakeholderDao.getStakeholderLinkedByRole(custCode, role);
	}

	@Override
	public boolean hasCustAuthorityByRole(String custCode, String role,
			UserContext uc) {
		return custStakeholderDao.hasEmployeeByRole(custCode, role, uc);
	}

}
