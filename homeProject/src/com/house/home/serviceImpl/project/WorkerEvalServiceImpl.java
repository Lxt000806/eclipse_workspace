package com.house.home.serviceImpl.project;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.WorkerEvalEvt;
import com.house.home.client.service.evt.WorkerEvalListEvt;
import com.house.home.dao.project.WorkerEvalDao;
import com.house.home.entity.project.WorkerEval;
import com.house.home.service.project.WorkerEvalService;

@SuppressWarnings("serial")
@Service
public class WorkerEvalServiceImpl extends BaseServiceImpl implements WorkerEvalService{

	@Autowired
	private WorkerEvalDao workerEvalDao;

	@Override
	public Page<Map<String,Object>>  getWorkerEvalList(Page page,WorkerEvalListEvt evt) {
		// TODO Auto-generated method stub
		return workerEvalDao.getWorkerEvalList(page,evt);
	}

	@Override
	public Result doSave(WorkerEval workerEval) {
		// TODO Auto-generated method stub
		return workerEvalDao.doSave(workerEval);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, WorkerEval workerEval) {
		return workerEvalDao.findPageBySql(page, workerEval);
	}

}
