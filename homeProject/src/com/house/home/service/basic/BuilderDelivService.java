package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.BuilderDeliv;

public interface BuilderDelivService extends BaseService {

	/**BuilderDeliv分页信息
	 * @param page
	 * @param builderDeliv
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BuilderDeliv builderDeliv);
	/**
	 * 找第一个builderDelivCode
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> findFirstDelivCode(String builderCode);
}
