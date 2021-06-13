package com.house.home.service.insales;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemAppCtrl;

public interface ItemAppCtrlService extends BaseService {

	/**
	 * ItemAppCtrl分页信息
	 * @param page
	 * @param itemAppCtrl
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemAppCtrl itemAppCtrl);

	/**
	 * 是否套餐材料
	 * @param custType
	 * @param itemType1
	 * @return
	 */
	public ItemAppCtrl getByCustTypeAndItemType1(String custType,
			String itemType1);
	
}
