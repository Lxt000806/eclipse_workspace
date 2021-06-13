package com.house.home.service.finance;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.Purchase;

public interface SupplCheckConfirmService extends BaseService{

	/**
	 * 主列表 
	 * @author	created by zb
	 * @date	2019-4-4--上午9:48:33
	 * @param page
	 * @param purchase
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, Purchase purchase);
	/**
	 * 获取采购费用明细
	 * @author	created by zb
	 * @date	2019-4-4--下午3:35:51
	 * @param page
	 * @param purchase
	 * @return
	 */
	public Page<Map<String, Object>> findPurFeeDetailPageBySql(Page<Map<String, Object>> page,
			Purchase purchase);
	/**
	 * 系统计算其它费用
	 * @author	created by zb
	 * @date	2019-5-29--下午5:13:25
	 * @param purchase
	 * @return
	 */
	public Result genSupplOtherFee(Purchase purchase);

	public List<Map<String, Object>> isSupplierChecked(String purchaseNo);
}
