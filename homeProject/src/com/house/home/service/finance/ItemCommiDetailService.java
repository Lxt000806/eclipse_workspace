package com.house.home.service.finance;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.ItemCommiDetail;

public interface ItemCommiDetailService extends BaseService {

	/**ItemCommiDetail分页信息
	 * @param page
	 * @param itemCommiDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemCommiDetail itemCommiDetail);
	/**ItemCommiDetail分页信息
	 * @param page
	 * @param itemCommiDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_khxx(Page<Map<String,Object>> page, ItemCommiDetail itemCommiDetail);
	
	
}
