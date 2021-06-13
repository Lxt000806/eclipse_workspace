package com.house.home.service.basic;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.GetCustomerInfoEvt;
import com.house.home.client.service.evt.GetDesignDemoListEvt;
import com.house.home.client.service.evt.GetShowBuildsEvt;
import com.house.home.entity.basic.APIKey;

public interface APIService extends BaseService {
	
	public Page<Map<String, Object>> getCustomerInfo(Page<Map<String, Object>> page, GetCustomerInfoEvt evt);
	
	public List<Map<String, Object>> getCustomerDetailInfo(String custCode, Date dateFrom, Date dateTo, String prjItems);
	
	public List<Map<String, Object>> getPrjProgPhoto(String custCode);
	
}
