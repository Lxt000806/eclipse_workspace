package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.BuilderGroup;

public interface BuilderGroupService extends BaseService {

	/**项目大类分页信息
	 * @param page
	 * @param builderGroup
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BuilderGroup builderGroup);
	
	/**获取项目大类
	 * @param descr
	 * @return
	 */
	public BuilderGroup getByDescr(String descr);

	public List<BuilderGroup> findByNoExpired();
}
