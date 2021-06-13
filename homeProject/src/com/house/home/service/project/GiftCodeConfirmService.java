package com.house.home.service.project;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CustAccount;

public interface GiftCodeConfirmService extends BaseService{

	public Page<Map<String,Object>> getGiftCodeConfirmList(Page<Map<String,Object>> page,String phone);
	
	public Page<Map<String,Object>> getGiftAppList(Page<Map<String,Object>> page,String custCode);
	
	public Page<Map<String,Object>> getCustomerList(Page<Map<String,Object>> page,String address);
	
	public CustAccount getCustAccount(String phone);
}
