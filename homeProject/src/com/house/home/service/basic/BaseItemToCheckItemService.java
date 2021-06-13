package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.BaseItemToCheckItem;

public interface BaseItemToCheckItemService extends BaseService{

	/**
	 * @Description: TODO 基础项目与结算项目映射管理 分页查询
	 * @author	created by zb
	 * @date	2018-9-20--上午9:48:19
	 * @param page
	 * @param baseItemToCheckItem
	 * @return
	 */
	public Page<Map<String , Object>> findPageBySql(Page<Map<String, Object>> page,
			BaseItemToCheckItem baseItemToCheckItem);

	/**
	 * @Description: 【基础项目编号+基础结算项目编号】不能重复
	 * @author	created by zb
	 * @date	2018-9-20--下午2:49:25
	 * @param baseItemCode
	 * @param baseCheckItemCode
	 * @return
	 */
	public boolean checkCode(String baseItemCode, String baseCheckItemCode);

}
