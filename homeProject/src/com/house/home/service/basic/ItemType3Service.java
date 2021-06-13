package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemType3;

public interface ItemType3Service extends BaseService {

	/**ItemType3分页信息
	 * @param page
	 * @param itemType3
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemType3 itemType3);
	
	/**
	 * 查询材料类型3列表
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> findItemType3(Map<String,Object> param);
	/**
	 * 查找itemType3全部数据
	 */
	public Map<String,Object> findAllItemType3(String descr);
	
	public Map<String,Object> findThItemType3(ItemType3 itemType3);
	
}
