package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.ActivitySet;

public interface ActivitySetService extends BaseService {

	public Page<Map<String,Object>> getActivitySetList(Page<Map<String,Object>> page, String  actNo,String ticketNo,String tokenCzyDescr);

	public Page<Map<String,Object>> getActSupplList(Page<Map<String,Object>> page,String actNo , String  supplType,String supplCodeDescr);
	
	public Map<String,Object> doSaveActivitySet(ActivitySet activitySet);
	
	public Map<String,Object> getActSupplDetail(String supplCode);
	
	public Map<String,Object> getActSetDetail(Integer pk);
	
	public Map<String,Object> doActSetCancel(Integer pk,String czybh);
}
