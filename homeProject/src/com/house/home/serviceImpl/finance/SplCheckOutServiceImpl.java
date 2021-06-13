package com.house.home.serviceImpl.finance;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.SplCheckOutDao;
import com.house.home.entity.finance.SplCheckOut;
import com.house.home.entity.insales.Purchase;
import com.house.home.service.finance.SplCheckOutService;

@SuppressWarnings("serial")
@Service
public class SplCheckOutServiceImpl extends BaseServiceImpl implements SplCheckOutService {

	@Autowired
	private SplCheckOutDao splCheckOutDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SplCheckOut splCheckOut){
		return splCheckOutDao.findPageBySql(page, splCheckOut);
	}

	@Override
	public Result doSaveSplCheckOutForProc(SplCheckOut splCheckOut) {
		return splCheckOutDao.doSaveSplCheckOutForProc(splCheckOut);
	}

	@Override
	public Page<Map<String, Object>> findCodePageBySql(
			Page<Map<String, Object>> page, SplCheckOut splCheckOut) {
		return splCheckOutDao.findCodePageBySql(page, splCheckOut);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_purchaseFeeDetail(
			Page<Map<String, Object>> page, Purchase purchase) {
		
		return splCheckOutDao.findPageBySql_purchaseFeeDetail(page, purchase);
	}

	@Override
	public Result doSaveSplOtherCostForProc(Purchase purchase) {
		return splCheckOutDao.doSaveSplOtherCostForProc(purchase);
	}
	
	

}
