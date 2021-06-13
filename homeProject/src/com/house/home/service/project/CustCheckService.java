package com.house.home.service.project;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.CustCheck;

public interface CustCheckService extends BaseService {
	/**
	 * 工地结算分页查找
	 * @param page
	 * @param custCheck
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,CustCheck custCheck);
}
