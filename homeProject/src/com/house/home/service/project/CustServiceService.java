package com.house.home.service.project;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.DoSaveCustServiceEvt;
import com.house.home.client.service.evt.DoUpdateCustServiceEvt;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.project.CustService;

public interface CustServiceService extends BaseService{

	/**
	 * @Description:  主页分页查询
	 * @author	created by zb
	 * @date	2018-8-6--下午3:00:23
	 * @param page
	 * @param custService
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, 
			CustService custService);

	/**
	 * @Description:  根据code获取相关客户信息（暂且客户售后（custService）、税务信息登记（custTax）在用。）
	 * @author	created by zb
	 * @date	2018-9-5--下午3:24:53
	 * @param code
	 * @return
	 */
	public Map<String, Object> getCustDetailByCode(String code);
	
	public Map<String, Object> doSaveCustService(DoSaveCustServiceEvt evt);
	
	public Map<String, Object> getCustServiceDetail(String no);
	
	public Map<String, Object> doUpdateCustService(DoUpdateCustServiceEvt evt);
	
	public Map<String, Object> goComplete(String no);
	
	public Page<Map<String, Object>> AppfindBySql(Page<Map<String, Object>> page, 
			CustService custService);

	
}
