package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Region;

public interface RegionService extends BaseService {
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Region region);//主表	
	public boolean validCode(String code);
	public boolean validDescr(String descr);
}
