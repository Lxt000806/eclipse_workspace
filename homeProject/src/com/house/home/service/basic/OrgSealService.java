package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.OrgSeal;

public interface OrgSealService extends BaseService {

	/**OrgSeal分页信息
	 * @param page
	 * @param orgSeal
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, OrgSeal orgSeal);
	
	public void doSave(OrgSeal orgSeal);
	
	public void doUpdate(OrgSeal orgSeal);
	
	public void doDelete(OrgSeal orgSeal);
}
