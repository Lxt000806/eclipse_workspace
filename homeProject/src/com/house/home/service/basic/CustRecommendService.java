package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.CustRecommend;

public interface CustRecommendService extends BaseService {
	
	public Page<Map<String, Object>> getCustRecommendList(Page<Map<String, Object>> page, CustRecommend custRecommend);
	
	public Page<Map<String, Object>> goJqGrid(Page<Map<String, Object>> page, CustRecommend custRecommend, UserContext uc);
	
	public Page<Map<String, Object>> goCountJqGrid(Page<Map<String, Object>> page, CustRecommend custRecommend, UserContext uc);
	
	public CustRecommend getCustRecommendByCustPhone(String custPhone);
	
	public  Map<String, Object> getCustRecommendDetail(int pk);
	
}
