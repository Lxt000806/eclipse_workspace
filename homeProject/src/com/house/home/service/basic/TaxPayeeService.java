package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.TaxPayee;

public interface TaxPayeeService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, TaxPayee taxPayee);

	public List<Map<String, Object>> getTaxPayeeList();
}
