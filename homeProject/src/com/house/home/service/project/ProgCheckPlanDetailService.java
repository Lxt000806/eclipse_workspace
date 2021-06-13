package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.ProgCheckPlanDetail;

public interface ProgCheckPlanDetailService extends BaseService {

	/**ProgCheckPlanDetail分页信息
	 * @param page
	 * @param progCheckPlanDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ProgCheckPlanDetail progCheckPlanDetail);
	
	public ProgCheckPlanDetail getByAppPk(Integer appPk);
}
