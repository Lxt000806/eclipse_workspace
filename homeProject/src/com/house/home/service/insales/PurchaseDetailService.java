package com.house.home.service.insales;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.PurchaseDetail;

public interface PurchaseDetailService extends BaseService {

	/**PurchaseDetail分页信息
	 * @param page
	 * @param purchaseDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPurchPageBySql(Page<Map<String,Object>> page, PurchaseDetail purchaseDetail);


	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PurchaseDetail purchaseDetail);
	public Page<Map<String,Object>> findViewOPageBySql(Page<Map<String,Object>> page, PurchaseDetail purchaseDetail);
	public Page<Map<String,Object>> findViewCPageBySql(Page<Map<String,Object>> page, PurchaseDetail purchaseDetail);
	/**未到货PurchaseDetail分页信息
	 * @param page
	 * @param purchaseDetail
	 * @return
	 */
	public Page<Map<String,Object>> findNotArriPageBySql(Page<Map<String, Object>> page,
			PurchaseDetail purchaseDetail);
	/**PurchaseDetail分页信息
	 * @param page
	 * @param purchaseDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageByNo(Page<Map<String,Object>> page, String no);
	/**PurchaseDetail分页信息-到货分析
	 * @param page
	 * @param purchaseDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_dhfx(Page<Map<String,Object>> page, PurchaseDetail purchaseDetail);
	
	/**
	 *采购明细查看 
	 */
	public PurchaseDetail getPurchaseDetail(PurchaseDetail purchaseDetail);


	public PurchaseDetail getQtyCal(PurchaseDetail purchaseDetail);
}
