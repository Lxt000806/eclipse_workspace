package com.house.home.service.design;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.BaseItemPlan;
import com.house.home.entity.design.Customer;

public interface BaseItemPlanService extends BaseService {

	/**BaseItemPlan分页信息
	 * @param page
	 * @param baseItemPlan
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemPlan baseItemPlan);
	
	/**基础预算
	 * @param page
	 * @param itemReq
	 */
	public Page<Map<String,Object>> findPageBySql_jzys(Page<Map<String, Object>> page, BaseItemPlan baseItemPlan);

	public boolean hasBaseItemPlan(String custCode);

	/**
	 * 计算基础预算对应区域名称的总费用
	 * @param custCode 客户编号
	 * @param descr	区域名称
	 * @return
	 */
	public Map<String,Object> getBaseFeeComp(String custCode,String descr);
	/**
	 * 基础预算保存
	 * @param customer
	 * @return
	 */
	public Result doBaseItemForProc(Customer customer);
	/**
	 * 基础预算套餐保存
	 * @param customer
	 * @return
	 */
	public Result doBaseItemTCForProc(Customer customer);
	/**
	 * 基础预算数
	 * @param customer
	 * @return
	 */
	public long getBaseItemPlanCount(BaseItemPlan baseItemPlan);
	
	/**
	 * 计算客户砌墙面积（tBaseItem.PrjType = '1'）
	 * 
	 * @param custCode
	 * @return
	 */
	public double calculateWallArea(String custCode);
	
	public String getBaseItemPlanAutoQty(BaseItemPlan baseItemPlan);
}
