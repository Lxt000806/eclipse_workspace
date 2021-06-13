package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.PrePlanTempDao;
import com.house.home.entity.basic.PrePlanTemp;
import com.house.home.service.basic.PrePlanTempService;

@SuppressWarnings("serial")
@Service
public class PrePlanTempServiceImpl extends BaseServiceImpl implements PrePlanTempService {

	@Autowired
	private PrePlanTempDao prePlanTempDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrePlanTemp prePlanTemp){
		return prePlanTempDao.findPageBySql(page, prePlanTemp);
	}

	@Override
	public Result doSaveProc(PrePlanTemp prePlanTemp) {
		return prePlanTempDao.doSaveProc(prePlanTemp);
	}

}
