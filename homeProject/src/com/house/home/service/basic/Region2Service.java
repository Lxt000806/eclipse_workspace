package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Region2;

public interface Region2Service extends BaseService {
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Region2 region2);
	public boolean validDescr(String descr); 
}
