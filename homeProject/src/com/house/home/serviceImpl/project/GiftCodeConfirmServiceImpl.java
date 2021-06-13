package com.house.home.serviceImpl.project;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.GiftCodeConfirmDao;
import com.house.home.entity.basic.CustAccount;
import com.house.home.service.project.GiftCodeConfirmService;


@SuppressWarnings("serial")
@Service
public class GiftCodeConfirmServiceImpl  extends BaseServiceImpl implements GiftCodeConfirmService {

	@Autowired
	private GiftCodeConfirmDao giftCodeConfirmDao;

	@Override
	public Page<Map<String, Object>> getGiftCodeConfirmList(
			Page<Map<String, Object>> page, String phone) {
		return giftCodeConfirmDao.getGiftCodeConfirmList(page,phone);
	}

	@Override
	public Page<Map<String, Object>> getGiftAppList(
			Page<Map<String, Object>> page, String custCode) {
		return giftCodeConfirmDao.getGiftAppList(page, custCode);
	}

	@Override
	public Page<Map<String, Object>> getCustomerList(
			Page<Map<String, Object>> page, String address) {
		return giftCodeConfirmDao.getCustomerList(page,address);
	}

	@Override
	public CustAccount getCustAccount(String phone) {
		CustAccount custAccount = giftCodeConfirmDao.getCustAccount(phone);
		return custAccount;
	}
	
}
