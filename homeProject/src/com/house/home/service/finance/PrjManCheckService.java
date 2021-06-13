package com.house.home.service.finance;

import java.util.List;
import java.util.Map;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.PrjCheck;


public interface PrjManCheckService extends BaseService {
	
	public Map<String, Object> getSendQty(String itemCode,String custCode);
	
	public Page<Map<String,Object>> findPageBySql_totalQualityFee(Page<Map<String,Object>> page,PrjCheck prjCheck);
	
	public Page<Map<String,Object>> findPageBySql_qualityDetail(Page<Map<String,Object>> page,PrjCheck prjCheck);
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,Customer customer,UserContext uc);	
	
	public boolean isCheckWorkCost(String custCode);

	public boolean isAbnormalItemApp(String custCode,String custTypeType);
	
	public List<Map<String, Object>> getTypeDescr();
	
	public List<Map<String, Object>> getWorkType1Descr();
	
	public Page<Map<String,Object>> findPageBySqlPrjCheckDetail(Page<Map<String,Object>> page, PrjCheck prjCheck);
	
	public Result doPrjCheckForProc(PrjCheck prjCheck);
	
	public Page<Map<String,Object>> findPageBySql_prjWithHold(Page<Map<String,Object>> page,PrjCheck prjCheck);
	
	public Map<String,Object> getQualityFee(String custCode);
	
	public Map<String,Object> getPrjCheck(String custCode ,String prjCtrlType);
	
	public boolean isOneWorker(String custCode);
	
	public boolean isQualityFeeZero(String custCode);
	
	public Map<String,Object> findBySql(String custCode);
	
	public Map<String, Object> getRemainQualityFee(String custCode);
	
	public Page<Map<String,Object>> findFixDutyPageBySql(Page<Map<String,Object>> page, PrjCheck prjCheck);
	
	public Page<Map<String,Object>> findFixDutyDetailPageBySql(Page<Map<String,Object>> page, PrjCheck prjCheck);
	
	public Page<Map<String,Object>> findBaseItemChgDetailPageBySql(Page<Map<String,Object>> page, PrjCheck prjCheck);
}

