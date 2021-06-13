package com.house.home.service.oa;

import com.house.framework.commons.orm.BaseService;
import com.house.home.entity.oa.AppErp;
import com.house.home.entity.oa.Leave;

public interface ErpAppService extends BaseService{
	
	
	public AppErp getByProcessInstanceId(String id);

}
