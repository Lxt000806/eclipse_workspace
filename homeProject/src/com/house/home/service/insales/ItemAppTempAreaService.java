package com.house.home.service.insales;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemAppTempArea;

public interface ItemAppTempAreaService extends BaseService {

	/**ItemAppTempArea分页信息
	 * @param page
	 * @param itemAppTempArea
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemAppTempArea itemAppTempArea);
	
}
