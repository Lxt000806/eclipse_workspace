package com.house.home.service.project;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.JobType;

public interface JobTypeService extends BaseService {

	/**JobType分页信息
	 * @param page
	 * @param jobType
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, JobType jobType);
	public List<Map<String, Object>> getPrjJobTypeList(String itemRight);
	/**
	 * ERP主页查询
	 * @author	created by zb
	 * @date	2019-9-4--上午10:12:28
	 * @param page
	 * @param jobType
	 * @return
	 */
	public Page<Map<String,Object>> findERPPageBySql(Page<Map<String, Object>> page, JobType jobType);
}
