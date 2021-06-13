package com.house.home.service.project;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemSet;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.ItemCheck;

public interface CljsglService extends BaseService{
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,  ItemCheck itemCheck,String itemRight);
	
	public Page<Map<String,Object>> findPageBySqlDetail(Page<Map<String,Object>> page,  Customer customer);
	/**
	 * 材料结算确认
	 * @return qty<>sendQty的列
	 * */
	public Page<Map<String,Object>> findPageBySqlCljsqr(Page<Map<String,Object>> page,  ItemCheck itemCheck);
	
	/**
	 * 材料结算优惠分摊
	 * @return 优惠分摊的列
	 * */
	public Page<Map<String,Object>> findPageBySqlCljsDiscCost(Page<Map<String,Object>> page,  ItemCheck itemCheck);

	/**
	 * 
	 * @return 保存
	 * */
	public Result docljsglSave(ItemCheck itemCheck);
	
	public Result doCljsglDiscCost(ItemCheck itemCheck);
	/**当需求数量不等于发货数量时，是否继续进行材料结算操作
	 * @return 	
	 * 1:有需求数量不等于发货数量 
	 * 0:没有
	 */
	public Map<String,Object> IsContinueWhenReqQtyNotEqualSendQty(String custCode,String itemType1);
	
	public boolean getHasNotConfirmedItemChg(String custCode,String itemType1);
}
