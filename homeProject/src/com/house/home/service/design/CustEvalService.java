package com.house.home.service.design;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.CustEval;
import com.house.home.entity.design.ItemPlanTemp;

public interface CustEvalService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustEval custEval);
	
}
