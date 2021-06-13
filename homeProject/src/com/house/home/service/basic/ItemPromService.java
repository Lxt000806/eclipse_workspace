package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemProm;

public interface ItemPromService extends BaseService {

	/**ItemProm分页信息
	 * @param page
	 * @param itemProm
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemProm itemProm);
	/**
	 * 材料是否已存在促销记录
	 * 
	 * @param itemProm
	 */
	public List<Map<String, Object>> checkIsExists(ItemProm itemProm);
	/**
	 * 选中行数据
	 * 
	 * @param itemProm
	 */
	public List<Map<String, Object>> findListByPk(Integer pk);
	/**
	 * 材料是否存在
	 * 
	 * @param itemProm
	 */
	public List<Map<String, Object>> checkIsItemCode(ItemProm itemProm);
	/**
	 * 更新促销价
	 * 
	 * @param itemProm
	 */
	public void updatePrice(ItemProm itemProm);
	/**
	 * 材料促销查询
	 * 
	 * @param page
	 * @param itemProm
	 * @return
	 */
	public Page<Map<String, Object>> itemQuery(Page<Map<String, Object>> page, ItemProm itemProm,String itemRight);
	/**
	 * 材料数据
	 * 
	 * @param itemProm
	 */
	public List<Map<String, Object>> findItemData(String itemCode);
	/**
	 * excel导入
	 * @param page
	 * @param itemProm
	 * @return
	 */
	public Map<String, Object> importExcel(ItemProm itemProm);
}
