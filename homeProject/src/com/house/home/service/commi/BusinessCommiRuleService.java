package com.house.home.service.commi;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.BusinessCommiRule;

public interface BusinessCommiRuleService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BusinessCommiRule businessCommiRule);

}
