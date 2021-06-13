package com.house.home.serviceImpl.finance;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.finance.WHCheckOutDao;
import com.house.home.entity.driver.ItemAppSend;
import com.house.home.entity.finance.WHCheckOut;
import com.house.home.entity.insales.SalesInvoice;
import com.house.home.service.finance.WHCheckOutService;

@SuppressWarnings("serial")
@Service
public class WHCheckOutServiceImpl extends BaseServiceImpl implements WHCheckOutService {

	@Autowired
	private WHCheckOutDao whCheckOutDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WHCheckOut whCheckOut,UserContext uc){
		return whCheckOutDao.findPageBySql(page, whCheckOut,uc);
	}

	@Override
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, WHCheckOut whCheckOut,UserContext uc){
		return whCheckOutDao.findDetailPageBySql(page, whCheckOut,uc);
	}
	@Override
	public Result doWHCheckOutForProc( WHCheckOut whCheckOut) {
		return  whCheckOutDao.doWHCheckOutForProc(whCheckOut);
	}
	@Override
	public Result doWHCheckOutCheckForProc( WHCheckOut whCheckOut) {
		return  whCheckOutDao.doWHCheckOutCheckForProc(whCheckOut);
	}
	
	@Override
	public Page<Map<String, Object>> findItemAppSendDetailBySql(
			Page<Map<String, Object>> page, WHCheckOut whCheckOut) {
		return whCheckOutDao.findItemAppSendDetailBySql(page, whCheckOut);
	}
	@Override
	public Page<Map<String, Object>> findSalesInvoiceDetailBySql(
			Page<Map<String, Object>> page, WHCheckOut whCheckOut) {
		return whCheckOutDao.findSalesInvoiceDetailBySql(page, whCheckOut);
	}
	
	@Override
	public Page<Map<String,Object>> findTotalByItemType2BySql(Page<Map<String,Object>> page, WHCheckOut whCheckOut){
		return whCheckOutDao.findTotalByItemType2BySql(page, whCheckOut);
	}
	@Override
	public Page<Map<String,Object>> findTotalByBrandBySql(Page<Map<String,Object>> page, WHCheckOut whCheckOut){
		return whCheckOutDao.findTotalByBrandBySql(page, whCheckOut);
	}
	@Override
	public Page<Map<String, Object>> findWHCheckOutItemAppSendAdd(Page<Map<String, Object>> page,ItemAppSend itemAppSend) {
		return whCheckOutDao.findWHCheckOutItemAppSendAdd(page, itemAppSend);
	}	
	@Override
	public Page<Map<String, Object>> findWHCheckOutSalesInvoiceAdd(Page<Map<String, Object>> page,SalesInvoice salesInvoice) {
		return whCheckOutDao.findWHCheckOutSalesInvoiceAdd(page, salesInvoice);
	}	
	@Override
	public Page<Map<String,Object>> findTotalByCompanyBySql(Page<Map<String,Object>> page, WHCheckOut whCheckOut){
		return whCheckOutDao.findTotalByCompanyBySql(page, whCheckOut);
	}
	
	@Override
	public Map<String, Object> doGenWHCheckOutSendFee(WHCheckOut whCheckOut) {
		return whCheckOutDao.doGenWHCheckOutSendFee(whCheckOut);
	}
}
