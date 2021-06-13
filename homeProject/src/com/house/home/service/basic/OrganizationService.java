package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Organization;

public interface OrganizationService extends BaseService {

	/**Organization分页信息
	 * @param page
	 * @param organization
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Organization organization);
	
	public Result doSave(Organization organization);
	
	public void doUpdate(Organization organization);
	
	public void doDelete(Organization organization);
	
	public void doIdentity(Organization organization);
	
	public void doRefreshIdentity(Organization organization);
}
