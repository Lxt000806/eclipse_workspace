package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SegDiscRule;

public interface SegDiscRuleService extends BaseService {

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SegDiscRule segDiscRule);
}
