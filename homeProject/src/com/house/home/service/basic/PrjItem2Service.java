package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.PrjItem2;

public interface PrjItem2Service extends BaseService {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, PrjItem2 prjItem2);
	
	public List<Map<String, Object>> getPrjItem2ListByPrjItem1(String prjItem1);
}
