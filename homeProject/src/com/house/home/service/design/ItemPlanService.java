package com.house.home.service.design;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.CustGift;
import com.house.home.entity.basic.CustGiftItem;
import com.house.home.entity.design.BaseItemPlan;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ItemPlan;
import com.house.home.entity.project.ItemPreMeasure;

public interface ItemPlanService extends BaseService {

	/**ItemPlan分页信息
	 * @param page
	 * @param itemPlan
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPlan itemPlan,UserContext uc);

	
	/**主材预算
	 * @param page
	 * @param itemReq
	 */
	public Page<Map<String,Object>> findPageBySql_zcys(Page<Map<String, Object>> page, ItemPlan itemPlan);

	public boolean hasItemPlan(String custCode);

	/**软装预算
	 * @param page
	 * @param itemReq
	 */
	public Page<Map<String,Object>> findPageBySql_rzys(Page<Map<String, Object>> page, ItemPlan itemPlan);
	/**
	 * 软装（含套餐）、主材、集成预算保存
	 * @param request
	 * @param response
	 * @param role
	 */
	public Result doItemForProc(ItemPlan itemPlan);
	/**
	 * 主材、集成预算套餐保存
	 * @param request
	 * @param response
	 * @param role
	 */
	public Result doItemTCForProc(ItemPlan itemPlan);
	/**
	 * 调用导入存储过程补齐数据
	 * @param data
	 */
	public void importDetail(Map<String, Object> data);
	/**
	 * 从预算添加
	 * @param page
	 * @param itemReq
	 * @return
	 */
	public Page<Map<String,Object>> findItemPlanList(Page<Map<String,Object>> page,ItemPlan itemPlan);
	/**
	 * 从基础模板添加
	 * @param page
	 * @param itemReq
	 * @return
	 */
	public Result doBaseBatchTempProc(ItemPlan itemPlan);
	
	/**
	 * 从基础模板添加
	 * @param page
	 * @param itemReq
	 * @return
	 */
	public Result doBaseItemTempProc(ItemPlan itemPlan);
	/**
	 * 复制预算
	 * @param request
	 * @param response
	 * @param role
	 */
	public Result doCopyPlan(ItemPlan itemPlan);
	/**
	 * 快速预算导入
	 * @param request
	 * @param response
	 * @param role
	 */
	public Result doCopyPlanPre(ItemPlan itemPlan);
	/**
	 * 预算从模板导入
	 * @param request
	 * @param response
	 * @param role
	 */
	public Result doPrePlanTemp(ItemPlan itemPlan);
	
	public Page<Map<String,Object>> findPageBySql_ckyj(Page<Map<String, Object>> page,
			ItemPlan itemPlan);

	//获取预算明细数
	public long getItemPlanCount(ItemPlan itemPlan);

	public Page<Map<String,Object>> findPageBySql_zcmlfx(Page<Map<String, Object>> page,
			ItemPlan itemPlan);
			
	public Map<String,Object> findBySql_mlfx(ItemPlan itemPlan);
	/**
	 * 清空预算
	 * @param itemPlan
	 * @return
	 */
	public Result doClearPlanForProc(ItemPlan itemPlan);

	public  Map<String, Object> findBySql_GetFourCustPay(ItemPlan itemPlan);
	
	public Map<String,Object>  getPayRemark(ItemPlan itemPlan);
	

	public  Map<String, Object> findBySql_GetItemPlanQty(ItemPlan itemPlan);

	public List<Map<String, Object>> getFixAreaTypes();

	
	/**
	 * 主材预算重新生成
	 * @param request
	 * @param response
	 * @param itemPlan
	 */
	public Result doRegenFromPrePlanTemp(ItemPlan itemPlan);
	/**
	 * 基础预算重新生成
	 * @param request
	 * @param response
	 * @param itemPlan
	 */
	public Result doRegenBasePlanFromPrePlanTemp(ItemPlan itemPlan);
	
	public  Map<String, Object> findBySql_GetBaseItemPlanQty(BaseItemPlan baseItemPlan);
	
	/**
	 * 预算备份
	 * @param request
	 * @param response
	 * @param itemPlan
	 */
	public Result doBaseAndItemPlanBak(ItemPlan itemPlan);
	/**
	 * 预算获取明细数据
	 * @param request
	 * @param response
	 * @param itemPlan
	 */
	public Page<Map<String,Object>> findPlanBakPageBySql(Page<Map<String,Object>> page, ItemPlan itemPlan);
	/**
	 * 预算获取空间数据
	 * @param request
	 * @param response
	 */
	public List<Map<String, Object>> getPrePlanAreaDescr(String custCode);
	
	public boolean getDelNotify(Integer pk,String custCode);
	
	/**
	 * 获取税金
	 * @param no
	 * @return
	 */
	public String getTax(Customer customer);
	
	/**
	 * 获取税金
	 * @param no
	 * @return
	 */
	public List<Map<String, Object>> getContractFeeRepType(Customer customer);
	
	/**
	 * 获取赠送项目
	 * @param itemPlan
	 */
	public Page<Map<String,Object>> findPageBySql_giftByDescr(Page<Map<String,Object>> page, ItemPlan itemPlan);
	
	/**
	 * 获取赠送材料
	 * @param itemPlan
	 */
	public Page<Map<String,Object>> findPageBySql_giftItem(Page<Map<String,Object>> page, ItemPlan itemPlan);
	
	/**
	 * 是否含赠送项目
	 * @param custCode
	 */
	public boolean hasGiftItem(String custCode);
	
	public Page<Map<String,Object>> getCustGiftJqGrid(Page<Map<String, Object>> page,
			CustGift custGift);	
	
	public Page<Map<String,Object>> getCustGiftAllJqGrid(Page<Map<String, Object>> page,
			CustGift custGift);	
	
	public Page<Map<String,Object>> getItemGiftJqgrid(Page<Map<String, Object>> page,
			ItemPlan itemPlan);	
	
	public Page<Map<String,Object>> getCustGiftItemJqgrid(Page<Map<String, Object>> page,
			CustGiftItem custGiftItem);	
	
	public double getMaxDiscExpr(Integer giftPk,double area,String custType);
	
	/**
	 * 基础预算和模板差异
	 * @param page
	 * @param itemPlan
	 * @return
	 */
	public Result getBaseItemTempDiff(ItemPlan itemPlan);
	
	/**
	 * 主材预算和模板差异
	 * @param page
	 * @param itemPlan
	 * @return
	 */
	public Result getItemTempDiff_ZC(ItemPlan itemPlan);
	
	/**
	 * 基础预算添加套餐包
	 * @param page
	 * @param ItemPlan
	 * @return
	 */
	public Result doBaseItemSetAddProc(ItemPlan itemPlan);
	
	public Page<Map<String,Object>> goSoftSendJqGrid(Page<Map<String,Object>> page,String custCode);
	
	/**
	 * 软装发货
	 * @param request
	 * @param response
	 * @param itemPlan
	 */
	public Result doSoftSend(ItemPreMeasure itemPreMeasure, String xml);
	
	public Page<Map<String,Object>> findProcListJqGrid(Page<Map<String,Object>> page,Customer customer);
	
	public boolean checkProcStatus(ItemPlan itemPlan);

	public List<Map<String, Object>> getCustAgreement(Customer customer);
	
	public List<Map<String, Object>> getClearInvList(ItemPlan itemPlan, String isClearInv);	

	public List<Map<String, Object>> getSaleDiscApproveDetail(ItemPlan itemPlan);

	public List<Map<String, Object>> getWfProcDetail(ItemPlan itemPlan);
	
	public String getSaleDiscApproveStatus(Customer customer);

	public List<Map<String, Object>> checkCommiConstruct(ItemPlan itemPlan);	
	
	public Map<String, Object> getBefAmountByCustCodeItemType(ItemPlan itemPlan);
	
	public boolean hasItemPlanQtyUnequal(String  custCode);
	
	public boolean hasBasePlanQtyUnequal(String  custCode);
	
}
