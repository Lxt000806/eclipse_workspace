package com.house.home.service.insales;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.BaseItem;

public interface BaseItemService extends BaseService {

	/**BaseItem分页信息
	 * @param page
	 * @param baseItem
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItem baseItem);

	public Page<Map<String,Object>> findListPageBySql(Page<Map<String,Object>> page, BaseItem baseItem);

	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, BaseItem baseItem);

	/**
	 * 根据基装类型查询基装项目
	 * @param page
	 * @param baseItem
	 */
	public Page<Map<String,Object>> getItemBaseType(Page<Map<String, Object>> page,
			BaseItem baseItem);
	/**
	 * 查询基础类型
	 * @param type
	 * @param pCode
	 * @return
	 */
	public List<Map<String, Object>> findBaseItemType(Integer type, String pCode);
	/**
	 * 调用导入存储过程补齐数据
	 * @param data
	 */
	public void importDetail(Map<String, Object> data);
	
	public String getUomByBaseItemCode(String baseItemCode);
	/**
	 * 根据code获取基础项目算法
	 * @param baseItem
	 * @return
	 */
	public List<Map<String, Object>> getBaseAlgorithmByCode(BaseItem baseItem);
	
	public Result doSave(BaseItem baseItem);

	public Result doUpdate(BaseItem baseItem);

	public Result doUpdatePrice(BaseItem baseItem);
}
