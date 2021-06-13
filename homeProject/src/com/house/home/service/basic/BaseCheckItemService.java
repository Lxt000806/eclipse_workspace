package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.BaseCheckItem;

public interface BaseCheckItemService extends BaseService {
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseCheckItem baseCheckItem);
	public Page<Map<String, Object>> getItemBaseType(Page<Map<String, Object>> page, BaseCheckItem baseCheckItem);
	/**
	 * @Description: TODO 基础结算项目code查询
	 * @author	created by zb
	 * @date	2018-9-21--下午2:08:25
	 * @param page
	 * @param baseCheckItem
	 * @return
	 */
	public Page<Map<String,Object>> findCodePageBySql(Page<Map<String, Object>> page,
			BaseCheckItem baseCheckItem);
				
	public void doUpdateWorkerClassify(BaseCheckItem baseCheckItem);
	
	public String getWorkerClassify(String code);

}
