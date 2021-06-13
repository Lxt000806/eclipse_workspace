package com.house.home.service.finance;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.SplCheckOut;
import com.house.home.entity.insales.Purchase;

public interface SplCheckOutService extends BaseService {

	/**SplCheckOut分页信息
	 * @param page
	 * @param splCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SplCheckOut splCheckOut);

	/**
	 * 采购单结算保存
	 * @param splCheckOut
	 * @return
	 */
	public Result doSaveSplCheckOutForProc(SplCheckOut splCheckOut);

	/**
	 * 供应商结算选择查询
	 * @author	created by zb
	 * @date	2018-11-22--下午4:05:17
	 * @param page
	 * @param splCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> findCodePageBySql(Page<Map<String, Object>> page,
			SplCheckOut splCheckOut);
	
	public Page<Map<String,Object>> findPageBySql_purchaseFeeDetail(Page<Map<String,Object>> page,
			Purchase purchase);
	/**
	 * 采购单结算保存
	 * @param splCheckOut
	 * @return
	 */
	public Result doSaveSplOtherCostForProc(Purchase purchase);
	
}
