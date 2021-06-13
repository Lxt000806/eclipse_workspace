package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.MainCommiPercIndeSale;

public interface MainCommiPercIndeSaleService extends BaseService {

	/**MainCommiPercIndeSale分页信息
	 * @param page
	 * @param mainCommiPercIndeSale
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MainCommiPercIndeSale mainCommiPercIndeSale);
	
	/**
	 * 检查MainCommiPercIndeSale是否重复
	 * @param page
	 * @param mainCommiPercIndeSale
	 * @return
	 */
	public boolean checkExistMainCommiPercIndeSale(MainCommiPercIndeSale mainCommiPercIndeSale);
}
