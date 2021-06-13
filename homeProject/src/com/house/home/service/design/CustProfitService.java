package com.house.home.service.design;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.CustProfit;

public interface CustProfitService extends BaseService {

	/**CustProfit分页信息
	 * @param page
	 * @param custProfit
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustProfit custProfit);
	/**
	 * 合同信息预录保存
	 * @param custProfit
	 * @return
	 */
	public Result doCustProfitForProc(CustProfit custProfit);
	
}
