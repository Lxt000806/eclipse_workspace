package com.house.home.service.project;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.WorkCon;

public interface WorkConService extends BaseService {

	/**WorkCon分页信息
	 * @param page
	 * @param workCon
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkCon workCon);
	/**
	 * 重复判断
	 * @param cmpCustType
	 * @return
	 */
	public List<Map<String, Object>> checkExist(WorkCon workCon);
}
