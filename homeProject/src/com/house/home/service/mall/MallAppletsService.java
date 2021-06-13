package com.house.home.service.mall;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.MallAppletsEvt;

public interface MallAppletsService extends BaseService  {
	
	public Page<Map<String, Object>> getWorkerList(Page<Map<String, Object>> page, MallAppletsEvt mallAppletsEvt);
	
}
