package com.house.home.service.insales;

import java.security.PublicKey;
import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.insales.PurchaseFee;

public interface PurchaseService extends BaseService {

	/**明细查询分页信息
	 * @param page
	 * @param purchase
	 * @return
	 */
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, Purchase purchase);
	
	/**采购跟踪分页信息
	 * @param page
	 * @param purchase
	 * @return
	 */
		public Page<Map<String,Object>> findPurchGZPageBySql(Page<Map<String,Object>> page, Purchase purchase);

		public Page<Map<String,Object>> findAppItemPageBy(Page<Map<String,Object>> page, Purchase purchase);

	/**Purchase分页信息
	 * @param page
	 * @param purchase
	 * @return
	 */
		public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Purchase purchase);

		public Page<Map<String,Object>> getPurchFeeDetail(Page<Map<String,Object>> page, PurchaseFee purchaseFee);

		public Page<Map<String,Object>> getPurchFeeList(Page<Map<String,Object>> page, Purchase purchase);


	/**Purchase1分页信息
	 * @param page
	 * @param purchase
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql1(Page<Map<String,Object>> page, Purchase purchase);
	
	/**
	 * 采购单结算查看
	 * 
	 * @param page
	 * @param purchase
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_gysjs(Page<Map<String,Object>> page, Purchase purchase);
	
	/**
	 * 采购单结算查看-该供应商的所有结算单
	 * 
	 * @param page
	 * @param purchase
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_gysjsAll(Page<Map<String,Object>> page, Purchase purchase);
	
	/**
	 * 采购单结算新增
	 * 
	 * @param page
	 * @param purchase
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_gysjsAdd(Page<Map<String,Object>> page, Purchase purchase);

	public Map<String,Object> getByNo(String id);
	
	public Page<Map<String, Object>> getDetailByNo(Page<Map<String,Object>> page,String no);
	
	/**采购单分页信息-客户信息查询
	 * @param page
	 * @param purchase
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_khxx(Page<Map<String,Object>> page, Purchase purchase);
	
	/**
	 *部分到货采购明细单 
	 *@param purchase
	 *@return
	 */
	public Result doPurchaseDetail(Purchase purchase );
	
	/**
	 *采购退回确认
	 *@param purchase
	 *@return
	 */
	public Result doPurchReturnCheckOut(Purchase purchase);
	
	public Result doPurchaseSave(Purchase purchase);
	
	public Map<String, Object> ajaxDoReturn(String no);
	
	public Map<String , Object>getAjaxArriveDay(String code);

	public boolean whRight(String whCode,String czybh);
	
	public double getProjectCost(String itCode);
	
	public String getItemRight(String czybh);
	
	public List<Map<String, Object>> getChengeParameter(String custCode,String itemType2,String itemType1) ;
	public List<Map<String, Object>> getChengeParameter2(String custCode,String itemType2,String itemType1) ;
	public List<Map<String, Object>> getChengeParameter3(String custCode,String itemType2,String itemType1,String supplierCode) ;
	
	public Result doAppItem(Purchase purchase);
	
	public void cancelAppItem(String no,String lastUpdatedBy);
	
	public Page<Map<String,Object>> findPageBySql_purchaseFeeDetail(Page<Map<String,Object>> page,
			Purchase purchase);
	
	public Result doPurchFeeSave(PurchaseFee purchaseFee);
	
	public String getPurchFeeNo(String no);
	
	public boolean existsPurchFee(String no);
	
	public String getLogoName(String custCode);


}



