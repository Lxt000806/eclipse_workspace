package com.house.home.service.basic;

import java.util.List;
import java.util.Map;


import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.WorkerMessage;

public interface WorkerMessageService extends BaseService{
	
	public Page<Map<String,Object>> getWorkerMessageNum(Page<Map<String,Object>> page, WorkerMessage workerMessage);

	public long getMessageCount(WorkerMessage workerMessage);
	
	public void doUpdateMessageStatus(String rcvCzy);

	public Page<Map<String, Object>> getMessageDetail(Page<Map<String,Object>> page, Integer pk);

	public Page<Map<String, Object>> getMessageNum(Page<Map<String,Object>> page, String rcvCzy);
	
	public List<Map<String, Object>> getWorkerMessagePushList() ;

	public void updateWorkerMessageStatus(String id);
}	
