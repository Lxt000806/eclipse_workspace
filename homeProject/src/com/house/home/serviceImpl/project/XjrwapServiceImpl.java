package com.house.home.serviceImpl.project;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.XjrwapDao;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.ProgCheckPlan;
import com.house.home.entity.project.ProgCheckPlanDetail;
import com.house.home.service.project.XjrwapService;

@SuppressWarnings("serial")
@Service
public class XjrwapServiceImpl extends BaseServiceImpl implements XjrwapService{

	@Autowired
	private XjrwapDao xjrwapDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ProgCheckPlan progCheckPlan) {
		return xjrwapDao.findPageBySql(page, progCheckPlan);
	}
	
	
	@Override
	public Page<Map<String, Object>> findPrjPageBySql(
			Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		return xjrwapDao.findPrjPageBySql(page);
	}


	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, ProgCheckPlanDetail progCheckPlanDetail) {
		return xjrwapDao.findDetailPageBySql(page, progCheckPlanDetail);
	}
	
	@Override
	public Page<Map<String, Object>> findAddPageBySql(
			Page<Map<String, Object>> page,Customer customer,String arr,String checkType,
			String auto,String longitude,String latitude,String isCheckDept,String importancePrj) {
		return xjrwapDao.findAddPageBySql(page,  customer,arr,checkType,auto,longitude,latitude,isCheckDept,importancePrj);
	}
	
	@Override
	public Page<Map<String, Object>> findFroAddPageBySql(
			Page<Map<String, Object>> page, ProgCheckPlan progCheckPlan,String arr) {
		return xjrwapDao.findFroAddPageBySql(page, progCheckPlan,arr);
	}

	@Override
	public Result doSave(ProgCheckPlan progCheckPlan) {
		return xjrwapDao.doSave(progCheckPlan);
	}
	
	@Override
	public Result doUpdate(ProgCheckPlan progCheckPlan) {
		return xjrwapDao.doSave(progCheckPlan);
	}

	@Override
	public Integer getFroNum() {
		return xjrwapDao.countFro();
	}

	@Override
	public String getIsCheckDept(String czybh) {
		// TODO Auto-generated method stub
		return xjrwapDao.getIsCheckDept(czybh);
	}


	@Override
	public void doSavePrjMan(String projectMan,String lastUpdatedBy) {
		// TODO Auto-generated method stub
		xjrwapDao.doSavePrjMan(projectMan,lastUpdatedBy);
	}


	@Override
	public boolean getPrjManByCode(String prjMan) {
		// TODO Auto-generated method stub
		return 	xjrwapDao.getPrjManByCode(prjMan);
	}


	@Override
	public void doDelPrjMan(Integer pk) {
		// TODO Auto-generated method stub
		xjrwapDao.doDelPrjMan(pk);
	}
	
	
	
	
	
}
