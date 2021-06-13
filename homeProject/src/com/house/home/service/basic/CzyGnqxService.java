package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CzyGnqx;

public interface CzyGnqxService extends BaseService {

	/**CzyGnqx分页信息
	 * @param page
	 * @param czyGnqx
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CzyGnqx czyGnqx);
	
	public CzyGnqx getCzyGnqx(String mkdm, String gnmc, String czybh);
	
}
