package com.house.home.serviceImpl.basic;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.WorkingDateDao;
import com.house.home.entity.basic.WorkingDate;
import com.house.home.service.basic.WorkingDateService;

@SuppressWarnings("serial")
@Service
public class WorkingDateServiceImpl extends BaseServiceImpl implements
	WorkingDateService {
	@Autowired
	private WorkingDateDao workingDateDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, WorkingDate workingDate) {
		return workingDateDao.findPageBySql(page, workingDate);
	}

	@Override
	public void doUpdate(WorkingDate workingDate) {
		workingDate.setLastUpdate(new Date());
		workingDate.setActionLog("Edit");
		workingDate.setExpired("F");
		workingDateDao.doUpdate(workingDate);
	}

	@Override
	public void doDateInit(WorkingDate workingDate) {
		workingDate.setLastUpdate(new Date());
		workingDate.setActionLog("Edit");
		workingDate.setExpired("F");
		workingDateDao.doDateInit(workingDate);
	}

	@Override
	public void doUpdateHoliType(WorkingDate workingDate) {
		workingDate.setLastUpdate(new Date());
		workingDate.setActionLog("Edit");
		workingDate.setExpired("F");
		workingDateDao.doUpdateHoliType(workingDate);
	}
}
