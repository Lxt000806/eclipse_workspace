package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemReq;

public interface ItemReqQueryService extends BaseService {
	/**ItemReq分页信息
	 * @param page
	 * @param itemReqQuery
	 * @returnfind
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemReq itemReq);
}
