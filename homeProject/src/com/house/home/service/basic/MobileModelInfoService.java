package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;

public interface MobileModelInfoService extends BaseService {
	
	public Map<String,Object> getMobileModelInfo(String manufacturer, String model, String version);
	
	public void saveMobileModelInfo(String manufacturer, String model, String czybh);
}
