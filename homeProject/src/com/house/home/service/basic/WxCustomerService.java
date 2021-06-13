package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CustAccount;
import com.house.home.entity.basic.CustMapped;

public interface WxCustomerService extends BaseService{

	public Page<Map<String, Object>> getCustCodeListByOpenId(Page<Map<String, Object>> page,String openid);
	
	public CustAccount getCustAccountByOpenId(String openId);
	
	/**
	 * 根据手机号及密码查询客户账号
	 */
	public CustAccount getCustAccountByPhone(String phone,String mm );
	
	/**
	 * 查看所有登记手机号为phone的客户号
	 */
	public  List<Map<String, Object>> getCustCodeListByPhoneFromCustomer(String portalAccount,String phone);
	
	/**
	 * 查询客户的所有工地编号
	 */
	public  List<Map<String, Object>> getCustCodeListByPhone(String phone);
	
	public void saveCustMapped(CustMapped custMapped);
}
