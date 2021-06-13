package com.house.home.serviceImpl.project;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.BaseCheckItemPlanDao;
import com.house.home.entity.project.BaseCheckItemPlan;
import com.house.home.service.project.BaseCheckItemPlanService;

@SuppressWarnings("serial")
@Service
public class BaseCheckItemPlanServiceImpl extends BaseServiceImpl implements BaseCheckItemPlanService {

	@Autowired
	private BaseCheckItemPlanDao baseCheckItemPlanDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseCheckItemPlan baseCheckItemPlan){
		return baseCheckItemPlanDao.findPageBySql(page, baseCheckItemPlan);
	}

	@Override
	public List<Map<String, Object>> findHeadInfoBySql(String code) {
		return baseCheckItemPlanDao.findHeadInfoBySql(code);
	}

	@Override
	public Page<Map<String, Object>> findBodyInfoBySql(Page<Map<String, Object>> page, BaseCheckItemPlan baseCheckItemPlan) {
		return baseCheckItemPlanDao.findBodyInfoBySql(page, baseCheckItemPlan);
	}

	@Override
	public List<Map<String, Object>> importFromCust(BaseCheckItemPlan baseCheckItemPlan) {
		return baseCheckItemPlanDao.importFromCust(baseCheckItemPlan);
	}

	@Override
	public Result doSaveProc(BaseCheckItemPlan baseCheckItemPlan) {
		return baseCheckItemPlanDao.doSaveProc(baseCheckItemPlan);
	}

	@Override
	public List<Map<String, Object>> addAllInfoCustType() {
		return baseCheckItemPlanDao.addAllInfoCustType();
	}
	
}
