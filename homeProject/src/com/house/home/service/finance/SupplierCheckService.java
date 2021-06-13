package com.house.home.service.finance;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.SplCheckOut;

public interface SupplierCheckService extends BaseService {
	
	public Page<Map<String,Object>> goJqGrid(Page<Map<String,Object>> page, SplCheckOut splCheckOut);
	
	public Page<Map<String,Object>> goJqGridAddPurchase(Page<Map<String,Object>> page, SplCheckOut splCheckOut);
	
	public List<Map<String,Object>> goJqGridIntInstall(Page<Map<String,Object>> page, String custCode);
	
	public List<Map<String,Object>> goJqGridMainItem(Page<Map<String,Object>> page, String checkOutNo);
	
	public List<Map<String,Object>> goJqGridExcess(Page<Map<String,Object>> page, String nos, String splCode, String checkOutNo);
	
	public List<Map<String,Object>> goJqGridWithHold(Page<Map<String,Object>> page, String nos, String splCode, String checkOutNo);
	
	public Result doSaveForProc(SplCheckOut splCheckOut);
	
	public Result doShForProc(SplCheckOut splCheckOut);
	
	public Map<String, Object> getSplCheckOutByNo(String no);
	
	public Map<String, Object> checkSupplierPay(String no);
	
	public List<Map<String, Object>> judgePrintPage(String no);
	
	public Map<String, Object> doGenOtherCostForProc(SplCheckOut splCheckOut);
	
	public Page<Map<String,Object>> goJqGridMainItemByCompany(Page<Map<String,Object>> page, SplCheckOut splCheckOut);
	/**
	 * 按材料类型3汇总
	 * @author	created by zb
	 * @date	2020-4-22--下午4:46:52
	 * @param page
	 * @param splCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> goJqGridMainItemByItemType3(Page<Map<String, Object>> page,
			SplCheckOut splCheckOut);

	/**
	 * 按楼盘部门汇总
	 * @param page
	 * @param splCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> goJqGridMainItemByCustDept(Page<Map<String,Object>> page, SplCheckOut splCheckOut);
	
	public String getNoChecAppReturnNum(String supplCode);
	
	public List<Map<String,Object>> getDetailByCheckOutNo(SplCheckOut splCheckOut);

	public Page<Map<String,Object>> findProcListJqGrid(Page<Map<String,Object>> page, SplCheckOut splCheckOut);
	
	public Map<String, Object> getAmountByCheckOutNo(SplCheckOut splCheckOut);

}
