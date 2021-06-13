package com.house.home.service.commi;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.BusinessCommiFloatRule;

public interface BusinessCommiFloatRuleService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BusinessCommiFloatRule businessCommiFloatRule);

	public Result doSave(BusinessCommiFloatRule businessCommiFloatRule);
	
	public List<Map<String, Object>> getFloatRuleSelection();
}
