package com.house.home.service.project;

import java.util.Date;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.PrjBulletinBoardEvt;
import com.house.home.entity.design.Customer;

public interface PrjBulletinBoardService extends BaseService{
	
	public Page<Map<String,Object>> getDepartmentList(Page<Map<String,Object>> page, UserContext uc);
	
	public Map<String, Object> getPrjBulletinBoardCountInfo(PrjBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getBuildingList(Page<Map<String,Object>> page, PrjBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getPrjStageProgList(Page page, Date beginDate,Date endDate,String stage,Integer pageSize,String department);

	public Page<Map<String,Object>> getConstructionList(Page<Map<String,Object>> page, PrjBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getPrjTaskExecAnalyList(Page<Map<String,Object>> page, PrjBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getWaitConfirmPrjProblemList(Page<Map<String,Object>> page, PrjBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getWaitDealPrjProblemList(Page<Map<String,Object>> page, PrjBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getCustProblemList(Page<Map<String,Object>> page, PrjBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getProjectManSignList(Page<Map<String,Object>> page, PrjBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getPrjAgainList(Page page, Customer customer, String department2);
	
	public Page<Map<String,Object>> getPrjTaskExecAnalyDetailList(Page<Map<String,Object>> page, String rcvCzy);
	
	public Page<Map<String,Object>> getPrjProblemDetailList(Page<Map<String,Object>> page, PrjBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getCustProblemDetailList(Page<Map<String,Object>> page, PrjBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getConfirmDetailList(Page<Map<String,Object>> page, String countType, String number);
	
	public Page<Map<String,Object>> getCheckDetailList(Page<Map<String,Object>> page, String countType, String number);
	
	
	
	public Page<Map<String,Object>> getWorkerArrangeList(Page<Map<String,Object>> page, Customer customer);
	
	public Page<Map<String,Object>> getWorkerCompletedList(Page<Map<String,Object>> page, Customer customer);


}
