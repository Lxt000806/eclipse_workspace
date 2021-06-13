package com.house.home.service.project;

import java.util.Date;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.IntegrateBulletinBoardEvt;
import com.house.home.entity.design.Customer;

public interface IntegrateBulletinBoardService extends BaseService{
	
	public Page<Map<String,Object>> getDepartmentList(Page<Map<String,Object>> page, UserContext uc);
	
	public Map<String, Object> getIntegrateBulletinBoardCountInfo(IntegrateBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getDesigningList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getDesignedList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt);

	public Page<Map<String, Object>> getProductionList(Page<Map<String, Object>> page, IntegrateBulletinBoardEvt evt);
	
	public Page<Map<String, Object>> getShippedList(Page<Map<String, Object>> page, IntegrateBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getInstallingList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getInstalledList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getSalingList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getSignOrConfirmList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getProductionDetailList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getInstallingDetailList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getSignOrConfirmDetailList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getWorkSignList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt);
	
	public Page<Map<String,Object>> getWorkSignDetailList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt);


}
