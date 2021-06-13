package com.house.home.serviceImpl.insales;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.SalesInvoiceDao;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.SaleCust;
import com.house.home.entity.insales.ItemWHBal;
import com.house.home.entity.insales.SalesInvoice;
import com.house.home.service.insales.SalesInvoiceService;
@SuppressWarnings("serial")
@Service
public class SalesInvoiceServiceImpl extends BaseServiceImpl implements
		SalesInvoiceService {
	@Autowired
	private SalesInvoiceDao salesInvoiceDao;

	public Page<Map<String, Object>> findPageBySql(Page<Map<String , Object>> page,SalesInvoice salesInvoice, Czybm czybm){
		return salesInvoiceDao.findPageBySql(page, salesInvoice, czybm);
	}

	@Override
	public Page<Map<String, Object>> findSalesInvoicePageBySql(
			Page<Map<String, Object>> page, SalesInvoice salesInvoice) {
		return salesInvoiceDao.findSalesInvoicePageBySql(page, salesInvoice);
	}

	@Override
	public Result doCustSave(SaleCust saleCust) {
		return salesInvoiceDao.doCustSave(saleCust);
	}

	@Override
	public Employee getEmpDescrByCZYBH(String czybh) {
		return salesInvoiceDao.getEmpDescrByCZYBH(czybh);
	}

	@Override
	public boolean isAuthorized(String no) {
		return salesInvoiceDao.isAuthorized(no);
	}

	@Override
	public Page<Map<String, Object>> findItemWHBalPageBySql(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
		return salesInvoiceDao.findItemWHBalPageBySql(page, itemWHBal);
	}

	@Override
	public Map<String, Object> getQtyNow(String itCode, String whCode) {
		return salesInvoiceDao.getQtyNow(itCode, whCode);
	}

	@Override
	public Result doSalesOrder(SalesInvoice salesInvoice) {
		return salesInvoiceDao.doSalesOrder(salesInvoice);
	}

	@Override
	public Page<Map<String, Object>> findPurDetailPageBySql(
			Page<Map<String, Object>> page, String sino) {
		return salesInvoiceDao.findPurDetailPageBySql(page, sino);
	}

	@Override
	public Map<String, Object> getItemBatchHeader(String itemType1,
			String czybhCode) {
		return salesInvoiceDao.getItemBatchHeader(itemType1, czybhCode);
	}

	@Override
	public Page<Map<String, Object>> findItemBatchPageBySql(
			Page<Map<String, Object>> page, String ibdno) {
		return salesInvoiceDao.findItemBatchPageBySql(page, ibdno);
	}

	@Override
	public Map<String, Object> getItemInfo(String itCode) {
		return salesInvoiceDao.getItemInfo(itCode);
	}

	@Override
	public Page<Map<String, Object>> findSalesInvoiceDetailPageBySql(
			Page<Map<String, Object>> page, String sino) {
		return salesInvoiceDao.findSalesInvoiceDetailPageBySql(page, sino);
	}

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, String no) {
		return salesInvoiceDao.findDetailPageBySql(page, no);
	}

	@Override
	public Boolean getPurchaseCount(String no) {
		return salesInvoiceDao.getPurchaseCount(no);
	}

	@Override
	public Page<Map<String, Object>> findDetailViewPageBySql(
			Page<Map<String, Object>> page, SalesInvoice salesInvoice) {
		return salesInvoiceDao.findDetailViewPageBySql(page,salesInvoice);
	}
}
