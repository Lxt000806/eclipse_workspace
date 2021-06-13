package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.CljsglDao;
import com.house.home.dao.query.CljsfxDao;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.ItemCheck;
import com.house.home.service.project.CljsglService;
import com.house.home.service.query.CljsfxService;

@Service
@SuppressWarnings("serial")
public class CljsglServiceImpl extends BaseServiceImpl implements CljsglService{

	@Autowired
	private CljsglDao cljsglDao;

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemCheck itemCheck,String itemRight) {
		return cljsglDao.findPageBySql(page, itemCheck,itemRight);
	}
	
	public Page<Map<String, Object>> findPageBySqlDetail(
			Page<Map<String, Object>> page, Customer customer) {
		return cljsglDao.findPageBySqlDetail(page, customer);
	}

	@Override
	public Result docljsglSave(ItemCheck itemCheck) {
		// TODO Auto-generated method stub
		return cljsglDao.docljsglCheckOut(itemCheck);
	}
	
	@Override
	public Result doCljsglDiscCost(ItemCheck itemCheck) {
		// TODO Auto-generated method stub
		return cljsglDao.doCljsglDiscCost(itemCheck);
	}

	@Override
	public Map<String, Object> IsContinueWhenReqQtyNotEqualSendQty(
			String custCode, String itemType1) {
		// TODO Auto-generated method stub
		return cljsglDao.IsContinueWhenReqQtyNotEqualSendQty(custCode,itemType1);
	}

	@Override
	public Page<Map<String, Object>> findPageBySqlCljsqr(
			Page<Map<String, Object>> page, ItemCheck itemCheck) {
		// TODO Auto-generated method stub
		return cljsglDao.findPageBySqlCljsqr(page,itemCheck);
	}
	
	@Override
	public Page<Map<String, Object>> findPageBySqlCljsDiscCost(
			Page<Map<String, Object>> page, ItemCheck itemCheck) {
		// TODO Auto-generated method stub
		return cljsglDao.findPageBySqlCljsDiscCost(page,itemCheck);
	}

	@Override
	public boolean getHasNotConfirmedItemChg(String custCode, String itemType1) {
		// TODO Auto-generated method stub
		return cljsglDao.getHasNotConfirmedItemChg(custCode,itemType1);
	}
	
	
	
}
