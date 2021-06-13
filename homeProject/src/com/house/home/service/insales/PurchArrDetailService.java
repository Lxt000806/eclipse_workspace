package com.house.home.service.insales;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.PurchArrDetail;

public interface PurchArrDetailService extends BaseService {

	/**PurchArrDetail分页信息
	 * @param page
	 * @param purchArrDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PurchArrDetail purchArrDetail);
	/**PurchArrDetail分页信息
	 * @param page
	 * @param purchArrDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageByPano(Page<Map<String,Object>> page, String pano);
	
}
