package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.MainCommiPerc;

public interface MainCommiPercService extends BaseService {

	/**MainCommiPerc分页信息
	 * @param page
	 * @param mainCommiPerc
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MainCommiPerc mainCommiPerc);
	
	/**
	 * 检查MainCommiPerc是否已存在
	 * @param page
	 * @param mainCommiPerc
	 * @return
	 */
	public boolean checkExistMainCommiPerc(MainCommiPerc mainCommiPerc);
	
}
