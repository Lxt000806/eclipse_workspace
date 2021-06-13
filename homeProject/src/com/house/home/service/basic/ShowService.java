package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.GetDesignDemoListEvt;
import com.house.home.client.service.evt.GetShowBuildsEvt;
import com.house.home.entity.basic.APIKey;

public interface ShowService extends BaseService {

	public Page<Map<String, Object>> getShowBuilds(Page<Map<String, Object>> page, GetShowBuildsEvt evt);
 
	public List<Map<String, Object>> getPrjProgConfirm(String custCode);
	
	public List<Map<String, Object>> getPrjProgConfirmPhoto(String prjProgConfirmNo, Integer number);
	 
	public List<Map<String, Object>> getCityAppUrlList(Double longitude, Double latitude);
	 
	public List<Map<String, Object>> getPrjItem1List();
	
	public Page<Map<String, Object>> getDesignDemoList(Page<Map<String, Object>> page, GetDesignDemoListEvt evt);
	
	public Map<String, Object> getDesignDemoDetail(String no);
	
	public List<Map<String, Object>> getDesignDemoDetailPhotos(String no);
	
	public Map<String, Object> getAPIKey(String apiKey);
}
