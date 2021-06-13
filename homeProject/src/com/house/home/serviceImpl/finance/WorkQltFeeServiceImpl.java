package com.house.home.serviceImpl.finance;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.WorkQltFeeDao;
import com.house.home.entity.finance.WorkQltFeeTran;
import com.house.home.entity.project.Worker;
import com.house.home.service.finance.WorkQltFeeService;

@SuppressWarnings("serial")
@Service
public class WorkQltFeeServiceImpl extends BaseServiceImpl implements
		WorkQltFeeService {

	@Autowired
	private WorkQltFeeDao workQltFeeDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Worker worker) {
		return workQltFeeDao.findPageBySql(page, worker);
	}

	@Override
	public Page<Map<String, Object>> findDetailBySql(
			Page<Map<String, Object>> page, WorkQltFeeTran workQltFeeTran) {
		return workQltFeeDao.findDetailBySql(page, workQltFeeTran);
	}

	@Override
	public void doUpdate(WorkQltFeeTran workQltFeeTran) {
		workQltFeeDao.doUpdate(workQltFeeTran);
	}

}
