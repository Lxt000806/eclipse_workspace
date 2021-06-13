package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Department3;

public interface StaffingService extends BaseService{
	
	/**
	 * 人员编制统计查询
	 * @author	created by zb
	 * @date	2018-12-19--上午9:24:28
	 * @param page
	 * @param departType
	 * @param department3 
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, String departType, Department3 department3);

}
