package com.house.home.service.project;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.JobTypeConfItemType;

public interface JobTypeConfItemTypeService extends BaseService {

	/**JobTypeConfItemType分页信息
	 * @param page
	 * @param jobTypeConfItemType
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, JobTypeConfItemType jobTypeConfItemType);
	
	public List<Map<String, Object>> getByJobTypeAndConfItemType(JobTypeConfItemType jobTypeConfItemType);
}
