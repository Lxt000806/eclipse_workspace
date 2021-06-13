package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

public interface GdysfxSerivce extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page,
			Customer customer,String orderBy,String direction);

}
