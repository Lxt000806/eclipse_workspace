package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CzyMtRegion;
import com.house.home.entity.basic.Czybm;

public interface CzyMtRegionService extends BaseService {

	/**CzyMtRegion分页信息
	 * @param page
	 * @param czyMtRegion
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CzyMtRegion czyMtRegion);
	
	public boolean isHasRegion(CzyMtRegion czyMtRegion);
	
}
