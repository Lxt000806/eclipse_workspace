package com.house.home.service.finance;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.driver.ItemAppSend;
import com.house.home.entity.finance.WHCheckOut;
import com.house.home.entity.insales.SalesInvoice;


public interface WHCheckOutService extends BaseService {

	/**whcheckout分页信息
	 * @param page
	 * @param whcheckout
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WHCheckOut whCheckOut,UserContext uc);
	
	/**whcheckout明细分页信息
	 * @param page
	 * @param whcheckout
	 * @return
	 */
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, WHCheckOut whCheckOut,UserContext uc);
	
	/**发货单明细分页信息
	 * @param page
	 * @param itemApp
	 * @return
	 */
	
	public Page<Map<String,Object>> findItemAppSendDetailBySql(Page<Map<String,Object>> page, WHCheckOut whCheckOut);
	
	/**销售单明细
	 * @param page
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> findSalesInvoiceDetailBySql(Page<Map<String,Object>> page, WHCheckOut whCheckOut);
	/**按材料类型2汇总
	 * @param page
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> findTotalByItemType2BySql(Page<Map<String,Object>> page, WHCheckOut whCheckOut);
	/**按品牌汇总
	 * @param page
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> findTotalByBrandBySql(Page<Map<String,Object>> page, WHCheckOut whCheckOut);
	/**
	 * 调用存储过程操作仓库出库信息（新增、修改）
	 * @param whCheckOut
	 * @return
	 */
	public Result doWHCheckOutForProc(WHCheckOut whCheckOut);
	/**
	 * 调用存储过程操作仓库出库信息（审核、反审核）
	 * @param whCheckOut
	 * @return
	 */
	public Result doWHCheckOutCheckForProc(WHCheckOut whCheckOut);
	
	
	/**
	 * 新增送货单
	 * @param page
	 * @param whCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> findWHCheckOutItemAppSendAdd(Page<Map<String,Object>> page,ItemAppSend itemAppSend);
	/**
	 * 新增送货单
	 * @param page
	 * @param whCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> findWHCheckOutSalesInvoiceAdd(Page<Map<String,Object>> page,SalesInvoice salesInvoice);
	
	public Page<Map<String,Object>> findTotalByCompanyBySql(Page<Map<String,Object>> page, WHCheckOut whCheckOut);
	
	public Map<String, Object> doGenWHCheckOutSendFee(WHCheckOut whCheckOut);
	
	
}
