package com.house.home.service.project;


import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.WorkerEvalListEvt;
import com.house.home.entity.project.WorkerEval;




public interface WorkerEvalService extends BaseService {

	public Page<Map<String,Object>>  getWorkerEvalList(Page page,WorkerEvalListEvt evt);
	public Result doSave(WorkerEval workerEval);
	
	/**
	 * @Description:工人评价管理分页查询
	 * @author	created by zb
	 * @date	2018-11-14--上午10:02:05
	 * @param page
	 * @param workerEval
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page,
			WorkerEval workerEval);
}
