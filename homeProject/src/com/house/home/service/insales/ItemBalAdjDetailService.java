package com.house.home.service.insales;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemBalAdjDetail;
import com.house.home.entity.insales.ItemWHBal;

public interface ItemBalAdjDetailService extends BaseService {
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemBalAdjDetail itemBalAdjDetail);

	public Page<Map<String,Object>> findSupplierPageBySql(Page<Map<String,Object>> page, ItemBalAdjDetail itemBalAdjDetail);

	/**
	 * 当前移动成本 / 该仓库该材料的库存量
	 */
	public Map<String, Object> getAvgCost(String whCode, String itCode);

	public Map<String,Object> getPosiQty(String whCode,String itCode);
	public Map<String,Object> getAllQty(String whCode,String itCode);
	public Page<Map<String, Object>> detailQuery(Page<Map<String, Object>> page, ItemBalAdjDetail itemBalAdjDetail);
	
}
