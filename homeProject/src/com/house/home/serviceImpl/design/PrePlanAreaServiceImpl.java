package com.house.home.serviceImpl.design;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.design.PrePlanAreaDao;
import com.house.home.entity.design.PrePlan;
import com.house.home.entity.design.PrePlanArea;
import com.house.home.service.design.PrePlanAreaService;

@SuppressWarnings("serial")
@Service 
public class PrePlanAreaServiceImpl extends BaseServiceImpl implements PrePlanAreaService {
	@Autowired
	private  PrePlanAreaDao prePlanAreaDao;

	@Override
	public String getNoByCustCode(String custCode) {

		return prePlanAreaDao.getNoByCustCode(custCode);
	}

	@Override
	public Page<Map<String, Object>> findPlanAreaJqgridBySql(
			Page<Map<String, Object>> page, PrePlan prePlan) {

		return prePlanAreaDao.findPlanAreaJqgridBySql(page,prePlan);
	}

	@Override
	public Result doSave(PrePlanArea prePlanArea) {
		return prePlanAreaDao.doSave(prePlanArea);
	}
	
	@Override
	public Result doUpdate(PrePlanArea prePlanArea) {
		// TODO Auto-generated method stub
		return prePlanAreaDao.doSave(prePlanArea);
	}

	@Override
	public Page<Map<String, Object>> findDoorWindJqgridBySql(
			Page<Map<String, Object>> page, Integer pk) {
		return prePlanAreaDao.findDoorWindJqgridBySql(page,pk);
	}

	@Override
	public Result doDelPrePlanArea(PrePlanArea prePlanArea) {
		return prePlanAreaDao.doSave(prePlanArea);
	}

	@Override
	public Result doUpward(PrePlanArea prePlanArea) {
		// TODO Auto-generated method stub
		return prePlanAreaDao.doSave(prePlanArea);
	}
	
	@Override
	public Result doDownward(PrePlanArea prePlanArea) {
		// TODO Auto-generated method stub
		return prePlanAreaDao.doSave(prePlanArea);
	}

	@Override
	public Result doAutoAddDefaultArea(PrePlanArea prePlanArea) {
		// TODO Auto-generated method stub
		return prePlanAreaDao.doSave(prePlanArea);
	}

	@Override
	public String getDWMaxPk(String type,Integer areaPk) {
		// TODO Auto-generated method stub
		return prePlanAreaDao.getDWMaxPk(type ,areaPk);
	}

	@Override
	public boolean getCanUpdateArea(String custCode) {
		return prePlanAreaDao.getCanUpdateArea(custCode);
	}
	
	

	
	
}
