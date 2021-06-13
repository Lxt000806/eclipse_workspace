package com.house.home.service.project;

import java.util.Date;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

public interface SiteBulletinBoardService extends BaseService{
	
	public Map<String, Object> getSiteBulletinBoardCountInfo();
	
	public Page<Map<String,Object>> getBuildingList(Page<Map<String,Object>> page);

	public Page<Map<String,Object>> getConstructionList(Page<Map<String,Object>> page, String workerClassify);
	
	public Page<Map<String,Object>> getWaitConfirmPrjProblemList(Page<Map<String,Object>> page);
	
	public Page<Map<String,Object>> getWaitDealPrjProblemList(Page<Map<String,Object>> page);
	
	public Page<Map<String,Object>> getCustProblemList(Page<Map<String,Object>> page);
	
	public Page<Map<String,Object>> getPrjCheckOrConfirmList(Page<Map<String,Object>> page, String countType);
	
	public Page<Map<String,Object>> getPrjProblemDetailList(Page<Map<String,Object>> page,String prjProblemType, String department2);
	
	public Page<Map<String,Object>> getCustProblemDetailList(Page<Map<String,Object>> page, String department2);
	
	public Page<Map<String,Object>> getConfirmDetailList(Page<Map<String,Object>> page, String countType, String number);
	
	public Page<Map<String,Object>> getCheckDetailList(Page<Map<String,Object>> page, String countType, String number);
	
	public Page<Map<String,Object>> getPrjStageProgList(Page page, Date beginDate,Date endDate,String stage,Integer pageSize);
	
	public Page<Map<String,Object>> getWorkerArrangeList(Page<Map<String,Object>> page, Customer customer);
	
	public Page<Map<String,Object>> getWorkerCompletedList(Page<Map<String,Object>> page, Customer customer);

	public Page<Map<String,Object>> getPrjAgainList(Page<Map<String,Object>> page, Customer customer);

}
