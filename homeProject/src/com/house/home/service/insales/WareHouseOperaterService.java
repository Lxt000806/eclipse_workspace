package com.house.home.service.insales;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.WareHouseOperater;

public interface WareHouseOperaterService extends BaseService {

	/**WareHouseOperater分页信息
	 * @param page
	 * @param wareHouseOperater
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WareHouseOperater wareHouseOperater);
	public Page<Map<String,Object>> findPageByCzy(Page<Map<String,Object>> page, String id);
	public List<Map<String, Object>> findByCzybh(String czybh);
}
