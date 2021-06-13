package com.house.home.service.insales;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.PurchArr;

public interface PurchArrService extends BaseService {

	/**PurchArr分页信息
	 * @param page
	 * @param purchArr
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PurchArr purchArr);
	
	/**到货单分页信息
	 * @param page
	 * @param purchArr
	 * @return
	 */
	public Page<Map<String,Object>> findPurchArrPageBySql(Page<Map<String,Object>> page, PurchArr purchArr);
	
	/**PurchArr分页信息
	 * @param page
	 * @param purchArr
	 * @return
	 */
	public Page<Map<String,Object>> findPageByPuno(Page<Map<String,Object>> page, String puno);
	
}
