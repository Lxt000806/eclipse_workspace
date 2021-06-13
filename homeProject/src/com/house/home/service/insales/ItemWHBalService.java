package com.house.home.service.insales;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.ItemWHBal;

public interface ItemWHBalService extends BaseService {
	/**itemWHBal分页信息
	 * @param page
	 * @param wareHouse
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemWHBal itemWHBal);

	public Page<Map<String,Object>> findPurchPageBySql(Page<Map<String,Object>> page, ItemWHBal itemWHBal);
/**
 * itemWHBal分页信息按材料汇总
 * @param page
 * @param itemWHBal
 * @return
 */
	public Page<Map<String,Object>> findPageGroupByItem(Page<Map<String, Object>> page,
			ItemWHBal itemWHBal);
	
	/**
	 * 仓库滞销品分析
	 * @param page
	 * @param itemWHBal
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_ckzxp(Page<Map<String, Object>> page,
			ItemWHBal itemWHBal);
	
	/**
	 * 仓库滞销品分析
	 * @param page
	 * @param itemWHBal
	 * @return
	 */
	public List<Map<String,Object>> findListBySql_ckzxp(ItemWHBal itemWHBal);

	public Page<Map<String,Object>> findItemSampleDetailPageBySql(Page<Map<String, Object>> page,
			ItemWHBal itemWHBal);
	/**
	 * 库存余额分页查询
	 * @param page
	 * @param itemWHBal
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_kcyecx(Page<Map<String, Object>> page,
			ItemWHBal itemWHBal);

	/**
	 * 库位余额分页查询（库存余额查询模块）
	 * @author	created by zb
	 * @date	2018-12-8--上午11:59:43
	 * @param page
	 * @param itemWHBal
	 */
	public Page<Map<String,Object>> findWHPosiBalPageBySql(Page<Map<String, Object>> page,
			ItemWHBal itemWHBal);

	/**
	 * 按材料类型2查询（库存余额查询模块）
	 * @author	created by zb
	 * @date	2018-12-8--下午5:50:20
	 * @param page
	 * @param itemWHBal 
	 * @return
	 */
	public Page<Map<String,Object>> findItemType2PageBySql(Page<Map<String, Object>> page,
			ItemWHBal itemWHBal);
	
	public Page<Map<String,Object>> findOrderAnalysis(Page<Map<String, Object>> page,
			ItemWHBal itemWHBal);
}
