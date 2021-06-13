package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.BaseItemChgDetail;

public interface BaseItemChgDetailService extends BaseService {

	/**BaseItemChgDetail分页信息
	 * @param page
	 * @param baseItemChgDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemChgDetail baseItemChgDetail);

	/**根据批次号获取基础变更详情
	 * @param page
	 * @param id
	 * @return
	 */
	public Page<Map<String,Object>> findPageByNo(Page<Map<String,Object>> page, String id);
	
}
