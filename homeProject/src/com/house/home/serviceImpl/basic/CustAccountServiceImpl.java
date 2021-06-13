package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.home.dao.basic.CustAccountDao;
import com.house.home.entity.basic.CustAccount;
import com.house.home.entity.basic.CustMapped;
import com.house.home.service.basic.CustAccountService;

@SuppressWarnings("serial")
@Service
public class CustAccountServiceImpl extends BaseServiceImpl implements
		CustAccountService {
	@Autowired
	private CustAccountDao custAccountDao;
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
	@Override
	public List<Map<String, Object>> getGiftCustCodeListByPhone(String phone) {
		return custAccountDao.getGiftCustCodeListByPhone(phone);
	}
	

}
