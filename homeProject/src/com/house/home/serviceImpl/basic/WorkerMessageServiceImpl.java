package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.basic.WorkerMessageDao;
import com.house.home.entity.basic.WorkerMessage;
import com.house.home.service.basic.WorkerMessageService;

@SuppressWarnings("serial")
@Service 
public class WorkerMessageServiceImpl extends BaseServiceImpl implements WorkerMessageService {
	@Autowired
	private  WorkerMessageDao workerMessageDao;

	@Override
	public Page<Map<String, Object>> getWorkerMessageNum(
			Page<Map<String, Object>> page, WorkerMessage workerMessage) {
		// TODO Auto-generated method stub
		return workerMessageDao.getWorkerMessageNum(page,workerMessage);
	}

	@Override
	public long getMessageCount(WorkerMessage workerMessage) {
		// TODO Auto-generated method stub
		return workerMessageDao.getMessageCount(workerMessage);
	}

	@Override
	public Page<Map<String, Object>> getMessageDetail(
			Page<Map<String, Object>> page, Integer pk) {
		// TODO Auto-generated method stub
		return workerMessageDao.getMessageDetail(page,pk);
	}

	@Override
	public Page<Map<String, Object>> getMessageNum(
			Page<Map<String, Object>> page, String rcvCzy) {
		// TODO Auto-generated method stub
		return workerMessageDao.getMessageNum(page,rcvCzy);
	}

	@Override
	public void doUpdateMessageStatus(String rcvCzy) {
		// TODO Auto-generated method stub
		workerMessageDao.doUpdateMessageStatus(rcvCzy);
	}

	@Override
	public List<Map<String, Object>> getWorkerMessagePushList() {
		// TODO Auto-generated method stub
		return workerMessageDao.getWorkerMessagePushList();
	}

	@Override
	public void updateWorkerMessageStatus(String id) {
		// TODO Auto-generated method stub
		workerMessageDao.updateWorkerMessageStatus(id);
	}
	
	
	

	
}
