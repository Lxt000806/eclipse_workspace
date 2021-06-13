package com.house.home.service.design;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.CustPayTran;

public interface CustPayTranService extends BaseService {
	
	/**
	 * 客户付款变动明细分页信息
	 * @param page
	 * @param custPayTran
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustPayTran custPayTran);
	/**
	 * 保存客户付款变动明细
	 * @param custPayTran
	 * @return
	 */
	public Result doCustPayTran(CustPayTran custPayTran);	
	/**
	 * 重打小票
	 * @param page
	 * @param custPayTran
	 * @return
	 */
	public void doRePrint(CustPayTran custPayTran);
	
	/**
	 * 客户付款变动明细修改手续费
	 * @param custPayTran
	 * @return
	 */
	public Result doUpdateProcedureFee(CustPayTran custPayTran);
}
