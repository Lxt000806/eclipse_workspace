package com.house.home.service.project;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.AutoArr;

public interface AutoArrService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, AutoArr autoArr);

	
}
