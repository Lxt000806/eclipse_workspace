package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Hdmpgl;

public interface HdmpglService extends BaseService {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Hdmpgl hdmpgl,UserContext uc);

	public void doCallBack(Integer pk,String lastUpdatedBy);

	public void doSign(Integer pk,String lastUpdatedBy,String status);

	public void doCreate(String actNo,Integer length,String prefix , Integer ticketNum,String lastUpdatedBy);
	
	public boolean isRepetition(String prefix,Integer numFrom,Integer numTo,Integer length,String actNo);

	public boolean isSend(String prefix,Integer numFrom,Integer numTo,Integer length,String actNo);
	
	public boolean hasTicket(String prefix,Integer numFrom,Integer numTo,Integer length,String actNo);

	public void doDeleteTickets(String prefix,Integer numFrom,Integer numTo,Integer length,String actNo);

	public String getPk(String actNo,String ticketNo);
	
	public Map<String, Object> getValidActNum();
	
	public boolean isExistPhone(String actNo, String phone);
	
	public Page<Map<String,Object>> goDetail_OrderJqGrid(Page<Map<String,Object>> page, Hdmpgl hdmpgl);

	public Page<Map<String,Object>> goDetail_giftJqGrid(Page<Map<String,Object>> page, Hdmpgl hdmpgl);

	public Page<Map<String,Object>> findOrderDetailJqGrid(Page<Map<String,Object>> page, Hdmpgl hdmpgl);

	public Page<Map<String,Object>> findGiftDetailJqGrid(Page<Map<String,Object>> page, Hdmpgl hdmpgl);

	
}
