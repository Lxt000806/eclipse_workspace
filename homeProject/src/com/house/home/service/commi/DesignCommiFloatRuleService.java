package com.house.home.service.commi;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.DesignCommiFloatRule;

public interface DesignCommiFloatRuleService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, DesignCommiFloatRule designCommiFloatRule);

	public Result doSave(DesignCommiFloatRule designCommiFloatRule);
	
	public List<Map<String, Object>> getFloatRuleSelection();
}
