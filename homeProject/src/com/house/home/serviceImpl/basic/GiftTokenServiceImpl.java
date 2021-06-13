package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.GiftTokenDao;
import com.house.home.entity.basic.GiftToken;
import com.house.home.service.basic.GiftTokenService;

@SuppressWarnings("serial")
@Service
public class GiftTokenServiceImpl extends BaseServiceImpl implements GiftTokenService {
	@Autowired
	GiftTokenDao giftTokenDao = new GiftTokenDao();
	
	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, GiftToken giftToken){
		return giftTokenDao.findPageBySql(page,giftToken);
	}
	
	@Override
	public Map<String,Object> existTokenNo(String tokenNo){
		return giftTokenDao.existTokenNo(tokenNo);
	}
}
