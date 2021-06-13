package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.WaterAreaCfm;

public interface WaterAreaCfmService extends BaseService {

	/**WaterAreaCfm分页信息
	 * @param page
	 * @param waterAreaCfm
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WaterAreaCfm waterAreaCfm);
	
}
