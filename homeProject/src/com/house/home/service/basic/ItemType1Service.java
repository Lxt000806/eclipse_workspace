package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemType1;

public interface ItemType1Service extends BaseService {

	/**ItemType1分页信息
	 * @param page
	 * @param itemType1
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemType1 itemType1);
	
	public List<ItemType1> findByNoExpired();
	
	/**
	 * 查询材料类型1列表
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> findItemType1(Map<String,Object> param);
	
	public List<Map<String,Object>> findItemType1(int type,String pCode);
	
}
