package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.ItemCheck;

public interface ItemCheckService extends BaseService {

	/**ItemCheck分页信息
	 * @param page
	 * @param itemCheck
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemCheck itemCheck);
	/**
	 * 检查是否客户的材料已结算
	 * @param custCode
	 * @param itemType1
	 * @return
	 */
	public  boolean isCheckItem(String custCode,String itemType1);
}
