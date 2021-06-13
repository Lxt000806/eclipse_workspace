package com.house.home.serviceImpl.design;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.design.PrePlanDao;
import com.house.home.entity.design.PrePlan;
import com.house.home.service.design.PrePlanService;

@SuppressWarnings("serial")
@Service
public class PrePlanServiceImpl extends BaseServiceImpl implements PrePlanService {

	@Autowired
	private PrePlanDao prePlanDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrePlan prePlan){
		return prePlanDao.findPageBySql(page, prePlan);
	}

}
