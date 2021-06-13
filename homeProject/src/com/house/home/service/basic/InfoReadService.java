package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.InfoRead;

public interface InfoReadService extends BaseService {

	/**InfoRead分页信息
	 * @param page
	 * @param infoRead
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, InfoRead infoRead);
	
	public Result updateForProc(InfoRead infoRead);
	
}
