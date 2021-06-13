package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.PrjConfirmAppEvt;
import com.house.home.entity.project.WorkerEval;


public interface PrjConfirmAppService extends BaseService {
	
	public Page<Map<String,Object>> getPrjConfirmAppList (Page<Map<String,Object>> page ,PrjConfirmAppEvt evt);
	
	public String addPrjConfirmApp(PrjConfirmAppEvt evt);
	
	public Map<String,Object> getPrjConfirmAppByPk(Integer pk);
	
	public String updatePrjConfirmApp(PrjConfirmAppEvt evt);
	
	public Map<String,Object> getMaxPkCustWorker(String custCode,String prjItem);
	
	public List<Map<String, Object>> getConfirmStatus(String custCode,String workType12);
	
	public List<Map<String, Object>> getBefWorkerList(String custCode,String workerCode,String workType12);

	public boolean existPrjConfirmApp(String custCode,String prjItem,String status);
}
