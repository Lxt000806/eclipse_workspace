package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.InfoAttach;

public interface InfoAttachService extends BaseService {

	/**InfoAttach分页信息
	 * @param page
	 * @param infoAttach
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, InfoAttach infoAttach);
	
	public List<Map<String,Object>> getByInfoNum(String num);
	
}
