package com.house.home.service.commi;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.BusinessCommiFloatRuleDetail;

public interface BusinessCommiFloatRuleDetailService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BusinessCommiFloatRuleDetail businessCommiFloatRuleDetail);

}
