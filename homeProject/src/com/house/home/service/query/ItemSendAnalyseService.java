package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.ItemApp;

public interface ItemSendAnalyseService extends BaseService {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, ItemApp itemApp,
			UserContext uc);

	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String, Object>> page,
			ItemApp itemApp);

	/**
	 * TODO 按发货类型查材料
	 * @author	created by zb
	 * @date	2018-7-25--下午3:07:26
	 * @param page
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> findItemPageBySql(Page<Map<String, Object>> page,
			ItemApp itemApp);

}
