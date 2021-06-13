package com.house.home.serviceImpl.project;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.WorkerArrAlermDao;
import com.house.home.entity.project.WorkerArrAlerm;
import com.house.home.service.project.WorkerArrAlermService;

@SuppressWarnings("serial")
@Service
public class WorkerArrAlermServiceImpl extends BaseServiceImpl implements WorkerArrAlermService{
	@Autowired
	private WorkerArrAlermDao workerArrAlermDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, WorkerArrAlerm workerArrAlerm) {
		// TODO Auto-generated method stub
		return workerArrAlermDao.findPageBySql(page,workerArrAlerm);
	}
	
	
}
