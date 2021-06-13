package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.IntProgDetail;

public interface IntProgDetailService extends BaseService {

	/**IntProgDetail分页信息
	 * @param page
	 * @param intProgDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntProgDetail intProgDetail);
	
	/**根据custcode查map
	 * @param intProgDetail
	 * @return
	 */
	public Map<String, Object> getIntProgDetail(String custCode); 
	
	/**根据cbm查note
	 * @param intProgDetail
	 * @return
	 */
	public Map<String , Object>  findDescr(String cbm,String id);
}
