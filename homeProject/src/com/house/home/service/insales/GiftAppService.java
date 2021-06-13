package com.house.home.service.insales;

import java.util.Map;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.GiftApp;


public interface GiftAppService extends BaseService {

	/**GiftApp分页信息
	 * @param page
	 * @param GiftApp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,GiftApp giftApp,UserContext uc);	
	public Page<Map<String,Object>> findPageBySqlGiftAppDetail(Page<Map<String,Object>> page, GiftApp giftApp);
	public Page<Map<String,Object>> findPageBySqlGiftStakeholder(Page<Map<String,Object>> page, GiftApp giftApp);
	public Page<Map<String,Object>> findPageBySqlGiftStakeholderByCustCode(Page<Map<String,Object>> page, GiftApp giftApp);
	public Result doGiftAppForProc(GiftApp giftApp);
	public Result doGiftAppSendBySuppForProc(GiftApp giftApp);
	public Result doGiftAppSendForProc(GiftApp giftApp);
	public Result doGiftAppReturnForProc(GiftApp giftApp);
	public Page<Map<String,Object>> findGiftAppDetailExistsReturn(Page<Map<String, Object>> page,
			Map<String, Object> param);
	public Page<Map<String,Object>> findPageByQPrintSql(Page<Map<String,Object>> page,GiftApp giftApp);	
	
	public Map<String, Object> getSendQty(String itemCode,String custCode);
	
	public Page<Map<String, Object>> findPageBySql_customerxx(
			Page<Map<String, Object>> page, GiftApp giftApp);
	
	public Page<Map<String,Object>> findPageByDetailSql(Page<Map<String,Object>> page,GiftApp giftApp);
	
	/**
	 * 调用存储过程操作供应商直送、仓库发货
	 * @param itemApp
	 * @return
	 */
	public Result doSendBatchForProc(GiftApp giftApp);
	public double getGiftUseDisc(String custCode, String no);
	
}

