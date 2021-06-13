package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.WorkCostDao;
import com.house.home.entity.project.FixDuty;
import com.house.home.entity.project.WorkCost;
import com.house.home.service.project.WorkCostService;

@SuppressWarnings("serial")
@Service
public class WorkCostServiceImpl extends BaseServiceImpl implements WorkCostService {

	@Autowired
	private WorkCostDao workCostDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkCost workCost){
		return workCostDao.findPageBySql(page, workCost);
	}

	@Override
	public String findNameByEmnum(String emnum) {
		return workCostDao.findNameByEmnum(emnum);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql2(
			Page<Map<String, Object>> page, WorkCost workCost) {
		return workCostDao.findPageBySql2(page, workCost);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql3(
			Page<Map<String, Object>> page, WorkCost workCost) {
		return workCostDao.findPageBySql3(page, workCost);
	}

	@Override
	public Result doSaveProc(WorkCost workCost) {
		return workCostDao.doSaveProc(workCost);
	}

	@Override
	public Page<Map<String,Object>> goFixDutyJqGrid(
			Page<Map<String, Object>> page, FixDuty fixDuty) {
		return workCostDao.goFixDutyJqGrid(page, fixDuty);
	}

	@Override
	public String isWaterCostPay(String custCode) {
		return workCostDao.isWaterCostPay(custCode);
	}

}
