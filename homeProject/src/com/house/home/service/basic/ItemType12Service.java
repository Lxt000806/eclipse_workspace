package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemType12;


public interface ItemType12Service extends BaseService {

	/**ItemSet分页信息
	 * @param page
	 * @param ItemSet
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemType12 itemtype12);

	public ItemType12 getByDescr(String descr);

	public Result doItemType12Save(ItemType12 itemtype12);

	public Result deleteForProc(ItemType12 itemtype12);

	public ItemType12 getByDescr1(String descr, String descr1);	
}

