package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.WorkType12;

public interface WkTpAnly_CTService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkType12 workType12);

	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page,
			WorkType12 workType12,String layOut,String area);

}
