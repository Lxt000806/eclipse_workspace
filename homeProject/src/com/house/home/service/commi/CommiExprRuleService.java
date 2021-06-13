package com.house.home.service.commi;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiExprRule;

public interface CommiExprRuleService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiExprRule commiExprRule);

}
