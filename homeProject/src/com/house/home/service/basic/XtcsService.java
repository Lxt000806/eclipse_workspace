package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Xtcs;

public interface XtcsService extends BaseService {

	/**Xtcs分页信息
	 * @param page
	 * @param xtcs
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Xtcs xtcs);
	
	/**
	 * 获取相关ID的取值
	 * @param id
	 * @return
	 */
	public String getQzById(String id);
}
