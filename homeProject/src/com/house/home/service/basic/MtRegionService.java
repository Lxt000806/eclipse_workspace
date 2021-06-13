package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.MtRegion;

public interface MtRegionService extends BaseService {

	/**MtRegion分页信息
	 * @param page
	 * @param mtRegion
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MtRegion mtRegion);
	
}
