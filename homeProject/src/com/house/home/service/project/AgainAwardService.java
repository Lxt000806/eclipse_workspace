package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.AgainAward;
import com.house.home.entity.project.AgainAwardDetail;

public interface AgainAwardService extends BaseService {
	
	public Page<Map<String, Object>> goJqGrid(Page<Map<String, Object>> page, AgainAward againAward, UserContext userContext);
	
	public Page<Map<String, Object>> goJqGridAddDetail(Page<Map<String, Object>> page, Customer customer);

	public Result doSave(AgainAward againAward, String xml);
	
	public Map<String, Object> getAgainAwardByNo(String no);
	
	public List<Map<String, Object>> goJqGridAgainAwardDetail(Page<Map<String, Object>> page, String no);
	
	public Page<Map<String, Object>> goJqGridDetailList(Page<Map<String, Object>> page, AgainAwardDetail againAwardDetail, UserContext userContext);

	/**
	 * 根据客户编号和奖励方案获取干系人列表
	 * 
	 * @param custCode
	 * @param bonusScheme 奖励方案 -> 1: 业务员 2： 设计师 3： 业务员和设计师
	 * @return
	 */
    public List<Map<String, Object>> getStakeholders(String custCode, String bonusScheme);
}
