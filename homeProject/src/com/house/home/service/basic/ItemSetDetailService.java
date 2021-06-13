package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemSetDetail;

public interface ItemSetDetailService extends BaseService {

	/**ItemSetDetail分页信息
	 * @param page
	 * @param itemSetDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemSetDetail itemSetDetail);
	
}
