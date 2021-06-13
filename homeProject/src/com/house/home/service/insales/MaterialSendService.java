package com.house.home.service.insales;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemApp;

public interface MaterialSendService extends BaseService {

	/**材料发货分页信息
	 * @param page
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemApp itemApp);
	
}
