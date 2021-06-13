package com.house.home.service.design;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.GetShowBuildsEvt;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.DesignDemo;
import com.house.home.entity.design.FixArea;

public interface DesignDemoService extends BaseService{
	
	public Page<Map<String, Object>> getShowBuilds(Page<Map<String, Object>> page, GetShowBuildsEvt evt);

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, DesignDemo designDemo,UserContext uc);
	
	public Page<Map<String,Object>> findDesignPic(Page<Map<String,Object>> page, DesignDemo designDemo,UserContext uc);

	public void doDeleteDemo(String no,String custCode,String photoName);

	public void doDeleteAllDemo(String no);
	
	public Map<String, Object> getQty(String builderCode);
	
	public Page<Map<String, Object>> getById(Page<Map<String, Object>> page,String id);

}
