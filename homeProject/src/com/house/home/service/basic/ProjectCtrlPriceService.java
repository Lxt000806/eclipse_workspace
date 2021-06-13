package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;

import com.house.home.entity.basic.ProjectCtrlPrice;

public interface ProjectCtrlPriceService extends BaseService{

	/**
	 * @Description: TODO 发包单价分页查询
	 * @author	created by zb
	 * @date	2018-10-25--上午9:49:24
	 * @param page
	 * @param projectCtrlPrice
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
			ProjectCtrlPrice projectCtrlPrice);

}
