package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.WorkSignAlarmDao;
import com.house.home.entity.basic.WorkSignAlarm;
import com.house.home.service.basic.WorkSignAlarmService;

@SuppressWarnings("serial")
@Service
public class WorkSignAlarmServiceImpl extends BaseServiceImpl implements WorkSignAlarmService {

	@Autowired
	private WorkSignAlarmDao workSignAlarmDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkSignAlarm workSignAlarm){
		return workSignAlarmDao.findPageBySql(page, workSignAlarm);
	}

}
