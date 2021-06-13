package com.house.home.service.finance;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.LaborFeeType;

public interface LaborFeeTypeService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, LaborFeeType laborFeeType);
	
	public LaborFeeType getByCode(String code);

}
