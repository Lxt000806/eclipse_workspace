package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.entity.Menu;
import com.house.home.entity.basic.ItemType2;

public interface ItemType2Service extends BaseService {

	/**ItemType2分页信息
	 * @param page
	 * @param itemType2
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemType2 itemType2);
	
	/**
	 * 查询材料类型2列表
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> findItemType2(Map<String,Object> param);
	/**
	 * 根据材料类型1查询所有材料类型2列表
	 * @param param
	 * @return
	 */
	public List<Menu> findAllItemType2ByitemType1(String itemType1);
	
	public List<ItemType2> findByItemType1(String itemType1);
	
	public Map<String , Object>  getItemType2Detail(String code);
	
	public List<Map<String,Object>> findPrjType(int type,String pCode);
	//材料类型2,3联动
	public List<Map<String,Object>> findItemType2(int type,String pCode);
	
	public Map<String, Object> getItemType1ByItemType2(String itemType2);
}
