package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.ItemPreMeasure;

public interface ItemPreMeasureService extends BaseService {

	/**ItemPreMeasure分页信息
	 * @param page
	 * @param itemPreMeasure
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPreMeasure itemPreMeasure);
	
	public int getMessageCount(String supplCode);

	public Map<String, Object> getByPk(Integer pk);
	
	public int getPrjJobMessageCount(String supplCode);
}
