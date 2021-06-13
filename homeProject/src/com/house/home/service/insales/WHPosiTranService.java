package com.house.home.service.insales;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.WHPosiTran;

public interface WHPosiTranService extends BaseService {

	/**WHPosiTran分页信息
	 * @param page
	 * @param wHPosiTran
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WHPosiTran wHPosiTran);
	
}
