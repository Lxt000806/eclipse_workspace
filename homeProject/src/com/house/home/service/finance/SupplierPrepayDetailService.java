package com.house.home.service.finance;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.SupplierPrepayDetail;

public interface SupplierPrepayDetailService extends BaseService{
	
	/**SupplierPrepayDetail分页信息
	 * @param page
	 * @param baseItemPlanBak
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SupplierPrepayDetail supplierPrepayDetail);

}
