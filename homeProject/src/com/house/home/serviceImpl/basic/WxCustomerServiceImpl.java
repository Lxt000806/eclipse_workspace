package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CustAccountDao;
import com.house.home.dao.basic.WxCustomerDao;
import com.house.home.entity.basic.CustAccount;
import com.house.home.entity.basic.CustMapped;
import com.house.home.service.basic.WxCustomerService;

@SuppressWarnings("serial")
@Service
public class WxCustomerServiceImpl  extends BaseServiceImpl implements WxCustomerService {
	@Autowired
	private WxCustomerDao wxCustomerDao;
	@Autowired
	private CustAccountDao custAccountDao;
	
	public Page<Map<String, Object>> getCustCodeListByOpenId(Page<Map<String, Object>> page,String openid) {
		// TODO Auto-generated method stub
		return wxCustomerDao.getCustCodeListByOpenId(page,openid);
	}
	
	public CustAccount getCustAccountByOpenId(String openId){
		return wxCustomerDao.getCustAccountByOpenId(openId);
	}
	
	@Override
	public CustAccount getCustAccountByPhone(String phone, String mm) {
		// TODO Auto-generated method stub
		return custAccountDao.getCustAccountByPhone(phone,mm);
	}
	@Override
	public List<Map<String, Object>> getCustCodeListByPhone(String phone) {
		// TODO Auto-generated method stub
		return custAccountDao.getCustCodeListByPhone(phone);
	}
	
	public  List<Map<String, Object>> getCustCodeListByPhoneFromCustomer(String portalAccount,String phone){
		return custAccountDao.getCustCodeListByPhoneFromCustomer(portalAccount,phone);
	}
	
	@Override
	public void saveCustMapped(CustMapped custMapped) {
		custAccountDao.saveCustMapped(custMapped);
	}
	
}
