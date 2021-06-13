package com.house.home.service.insales;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.BaseItemTempDetail;

public interface BaseItemTempDetailService extends BaseService {

	/**BaseItemTempDetail分页信息
	 * @param page
	 * @param baseItemTempDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemTempDetail baseItemTempDetail);
	
}
