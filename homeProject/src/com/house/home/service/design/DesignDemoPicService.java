package com.house.home.service.design;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;

public interface DesignDemoPicService extends BaseService{

	public List<Map<String, Object>> findNoPushYunPics() ;

	public void updateDesignDemoPicStatus();
}
