package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ActivityDao;
import com.house.home.entity.basic.Activity;
import com.house.home.service.basic.ActivityService;

@SuppressWarnings("serial")
@Service
public class ActivityServiceImpl extends BaseServiceImpl implements ActivityService {

	@Autowired
	private ActivityDao activityDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Activity activity){
		return activityDao.findPageBySql(page, activity);
	}

	@Override
	public Page<Map<String, Object>> findActByName(
			Page<Map<String, Object>> page, String actName) {
		// TODO Auto-generated method stub
		return activityDao.findActByName(page,actName);
	}

	@Override
	public String getCurrActivity(){
		return activityDao.getCurrActivity();
	}

	@Override
	public Boolean checkActivity(Activity activity) {
		return activityDao.checkActivity(activity);
	}
}
