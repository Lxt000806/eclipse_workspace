package com.house.home.serviceImpl.design;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.design.CustSceneDesiDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.design.CustSceneDesiService;

@SuppressWarnings("serial")
@Service
public class CustSceneDesiServiceImpl extends BaseServiceImpl implements CustSceneDesiService {

	@Autowired
	private CustSceneDesiDao custSceneDesiDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return custSceneDesiDao.findPageBySql(page,customer);
	}

	@Override
	public String getDesignerCode(String code) {
		// TODO Auto-generated method stub
		return custSceneDesiDao.getDesignerCode(code);
	}

	@Override
	public String getSceneDesignerCode(String code) {
		// TODO Auto-generated method stub
		return custSceneDesiDao.getSceneDesignerCode(code);
	}

	@Override
	public void doUpdateSceneDesi(String code, String custSceneDesi,String lastUpdatedBy,String oldEmpCode) {
		// TODO Auto-generated method stub
		custSceneDesiDao.doUpdateSceneDesi(code, custSceneDesi, lastUpdatedBy, oldEmpCode);
	}

	@Override
	public void doSaveSceneDesi(String code, String custSceneDesi,String lastUpdatedBy) {
		// TODO Auto-generated method stub
		custSceneDesiDao.doSaveSceneDesi(code, custSceneDesi, lastUpdatedBy);
	}

	@Override
	public void doDeleteSceneDesi(String code, String oldEmpCode,String lastUpdatedBy) {
		// TODO Auto-generated method stub
		custSceneDesiDao.doDeleteSceneDesi(code, lastUpdatedBy, oldEmpCode);
	}

	@Override
	public String getIsAddCustType() {
		// TODO Auto-generated method stub
		return custSceneDesiDao.getIsAddCustType();
	}

	@Override
	public Map<String, Object> getSceneDesiDepartment() {
		return custSceneDesiDao.getSceneDesiDepartment();
	}
	
	
}
