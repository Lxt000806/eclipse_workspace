package com.house.home.serviceImpl.design;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.design.EmpWorkPlanDao;
import com.house.home.entity.design.EmpWorkPlan;
import com.house.home.service.design.EmpWorkPlanService;

@SuppressWarnings("serial")
@Service
public class EmpWorkPlanServiceImpl extends BaseServiceImpl implements EmpWorkPlanService {

	@Autowired
	private EmpWorkPlanDao empWorkPlanDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, EmpWorkPlan empWorkPlan){
		return empWorkPlanDao.findPageBySql(page, empWorkPlan);
	}

	@Override
	public String isExistsThisPlan(EmpWorkPlan empWorkPlan) {
		return empWorkPlanDao.isExistsThisPlan(empWorkPlan);
	}

	@Override
	public String isExistsNextPlan(EmpWorkPlan empWorkPlan) {
		return empWorkPlanDao.isExistsNextPlan(empWorkPlan);
	}
	
	@Override
	public Result doSaveProc(EmpWorkPlan empWorkPlan) {
		return empWorkPlanDao.doSaveProc(empWorkPlan);
	}

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page, EmpWorkPlan empWorkPlan) {
		return empWorkPlanDao.findDetailPageBySql(page, empWorkPlan);
	}

	@Override
	public Page<Map<String, Object>> findViewPageBySql(Page<Map<String, Object>> page, EmpWorkPlan empWorkPlan) {
		return empWorkPlanDao.findViewPageBySql(page, empWorkPlan);
	}


}
