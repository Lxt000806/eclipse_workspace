package com.house.home.service.finance;

import java.util.Map;

import org.jconfig.server.BaseServer;


import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.GiftCheckOut;
import com.house.home.entity.insales.GiftApp;

public interface GiftCheckOutService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,GiftCheckOut giftCheckOut);
	
	public Page<Map<String,Object>> findAppPageBySql(Page<Map<String,Object>> page,GiftApp giftApp,GiftCheckOut giftCheckOut);

	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page,GiftApp giftApp);

	public Page<Map<String,Object>> findIssueDetailPageBySql(Page<Map<String,Object>> page,GiftApp giftApp);

	public Page<Map<String,Object>> findDetail_depaPageBySql(Page<Map<String,Object>> page,String no);
	
	public Result saveGiftCheckOut(GiftCheckOut giftCheckOut);
	
	public Result doUpdate(GiftCheckOut giftCheckOut);
	
	public Result doCheckCancel(GiftCheckOut giftCheckOut);

	public Result doReturnCheck(GiftCheckOut giftCheckOut);
	
	public Page<Map<String, Object>> findDetail_custDepaPageBySql(Page<Map<String, Object>> page, String no);
	
	
}
