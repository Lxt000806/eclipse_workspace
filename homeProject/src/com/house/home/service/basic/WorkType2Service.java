package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.WorkType2;

public interface WorkType2Service extends BaseService {

	/**
	 * @Description: TODO workType2_list分页查询
	 * @author	created by zb
	 * @date	2018-8-17--下午2:09:59
	 * @param page
	 * @param workType2
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page,
			WorkType2 workType2);

	public Page<Map<String,Object>> getPreWorkCostWorkType2(Page<Map<String, Object>> page, WorkType2 workType2);
	
}
