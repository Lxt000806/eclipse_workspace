package com.house.home.service.insales;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.BaseItemReq;

public interface BaseItemReqService extends BaseService {

	/**BaseItemReq分页信息
	 * @param page
	 * @param baseItemReq
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemReq baseItemReq);
	
	/**
	 * 已有项新增
	 * @param page
	 * @param itemReq
	 * @return
	 */
	public Page<Map<String,Object>> findBaseItemReqList(Page<Map<String,Object>> page, BaseItemReq baseItemReq);
	
}
