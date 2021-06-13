package com.house.home.serviceImpl.finance;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.GiftCheckOutDao;
import com.house.home.entity.finance.GiftCheckOut;
import com.house.home.entity.insales.GiftApp;
import com.house.home.service.finance.GiftCheckOutService;


@SuppressWarnings("serial")
@Service
public class GiftCheckOutServiceImpl extends BaseServiceImpl implements GiftCheckOutService {

	@Autowired
	public GiftCheckOutDao giftCheckOutDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, GiftCheckOut giftCheckOut) {
		return giftCheckOutDao.findPageBySql(page, giftCheckOut);
	}
	
	@Override
	public Page<Map<String, Object>> findAppPageBySql(
			Page<Map<String, Object>> page, GiftApp giftApp,GiftCheckOut giftCheckOut) {
		return giftCheckOutDao.findAppPageBySql(page, giftApp,giftCheckOut);
	}
	
	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, GiftApp giftApp) {
		return giftCheckOutDao.findDetailPageBySql(page, giftApp);
	}
	
	@Override
	public Page<Map<String, Object>> findIssueDetailPageBySql(
			Page<Map<String, Object>> page, GiftApp giftApp) {
		return giftCheckOutDao.findIssueDetailPageBySql(page, giftApp);
	}
	
	@Override
	public Page<Map<String, Object>> findDetail_depaPageBySql(
			Page<Map<String, Object>> page, String no) {
		return giftCheckOutDao.findDetail_depaPageBySql(page, no);
	}

	@Override
	public Result saveGiftCheckOut(GiftCheckOut giftCheckOut) {
		return giftCheckOutDao.saveGiftCheckOut(giftCheckOut);
	}
	
	@Override
	public Result doUpdate(GiftCheckOut giftCheckOut) {
		return giftCheckOutDao.saveGiftCheckOut(giftCheckOut);
	}
	
	@Override
	public Result doCheckCancel(GiftCheckOut giftCheckOut) {
		return giftCheckOutDao.saveGiftCheckOut(giftCheckOut);
	}
	
	@Override
	public Result doReturnCheck(GiftCheckOut giftCheckOut) {
		return giftCheckOutDao.saveGiftCheckOut(giftCheckOut);
	}

	@Override
	public Page<Map<String, Object>> findDetail_custDepaPageBySql(Page<Map<String, Object>> page, String no) {
		return giftCheckOutDao.findDetail_custDepaPageBySql(page, no);
	}

	
}
