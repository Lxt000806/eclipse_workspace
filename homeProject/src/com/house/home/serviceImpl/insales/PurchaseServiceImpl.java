package com.house.home.serviceImpl.insales;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.PurchaseDao;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.insales.PurchaseFee;
import com.house.home.service.insales.PurchaseService;

@SuppressWarnings("serial")
@Service
public class PurchaseServiceImpl extends BaseServiceImpl implements PurchaseService {

	@Autowired
	private PurchaseDao purchaseDao;
	
	
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Purchase purchase){
		return purchaseDao.findPageBySql(page, purchase);
	}
	
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, Purchase purchase){
		return purchaseDao.findDetailPageBySql(page, purchase);
	}
	
	public Page<Map<String,Object>> findPurchGZPageBySql(Page<Map<String,Object>> page, Purchase purchase){
		return purchaseDao.findPurchGZPageBySql(page, purchase);
	}
	
	public Page<Map<String,Object>> findAppItemPageBy(Page<Map<String,Object>> page, Purchase purchase){
		return purchaseDao.findAppItemPageBy(page, purchase);
	}
	
	public Page<Map<String,Object>> getPurchFeeDetail(Page<Map<String,Object>> page, PurchaseFee purchaseFee){
		return purchaseDao.getPurchFeeDetail(page, purchaseFee);
	}
	
	public Page<Map<String,Object>> getPurchFeeList(Page<Map<String,Object>> page, Purchase purchase){
		return purchaseDao.getPurchFeeList(page, purchase);
	}
	
	public Page<Map<String,Object>> findPageBySql1(Page<Map<String,Object>> page, Purchase purchase){
		return purchaseDao.findPageBySql1(page, purchase);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_gysjs(
			Page<Map<String, Object>> page, Purchase purchase) {
		return purchaseDao.findPageBySql_gysjs(page, purchase);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_gysjsAdd(
			Page<Map<String, Object>> page, Purchase purchase) {
		return purchaseDao.findPageBySql_gysjsAdd(page, purchase);
	}

	@Override
	public Map<String, Object> getByNo(String id) {
		return purchaseDao.getByNo(id);
	}

	@Override
	public Page<Map<String,Object>> getDetailByNo(Page<Map<String,Object>> page,String no) {
		return purchaseDao.getDetailByNo(page,no);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_gysjsAll(
			Page<Map<String, Object>> page, Purchase purchase) {
		return purchaseDao.findPageBySql_gysjsAll(page, purchase);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_khxx(
			Page<Map<String, Object>> page, Purchase purchase) {
		return purchaseDao.findPageBySql_khxx(page,purchase);
	}
	/**
	 *部分到货明细表 
	 */
	@Override
	public Result doPurchaseDetail(Purchase purchase){
		return purchaseDao.doPurchaseDetail(purchase);
	}
	@Override
	public Result doPurchReturnCheckOut(Purchase purchase){
		return purchaseDao.doPurchReturnCheckOut(purchase);
	}
	
	@Override
	public Result doPurchaseSave(Purchase purchase){
		return purchaseDao.doPurchReturnCheckOut(purchase);
	}

	@Override
	public Map<String, Object> ajaxDoReturn(String no) {
		return purchaseDao.ajaxDoReturn(no);
	}

	@Override
	public Map<String, Object> getAjaxArriveDay(String code) {
		return purchaseDao.getAjaxArriveDay(code);
	}

	@Override
	public boolean whRight(String whCode, String czybh) {
		return purchaseDao.whRight(whCode,czybh);
	}

	@Override
	public double getProjectCost(String itCode) {
		// TODO Auto-generated method stub
		return purchaseDao.getProjectCost(itCode);
	}

	@Override
	public String getItemRight(String czybh) {
		// TODO Auto-generated method stub
		return purchaseDao.getItemRight(czybh);
	}

	@Override
	public List<Map<String, Object>> getChengeParameter(String custCode,String itemType2,String itemType1) {
		return purchaseDao.getChengeParameter(custCode,itemType1,itemType2);
	}
	
	@Override
	public List<Map<String, Object>> getChengeParameter2(String custCode,String itemType2,String itemType1) {
		return purchaseDao.getChengeParameter2(custCode,itemType1,itemType2);
	}
	
	@Override
	public List<Map<String, Object>> getChengeParameter3(String custCode,String itemType2,String itemType1,String supplierCode) {
		return purchaseDao.getChengeParameter3(custCode,itemType1,itemType2,supplierCode);
	}

	@Override
	public Result doAppItem(Purchase purchase) {
		// TODO Auto-generated method stub
		return purchaseDao.doAppItem(purchase);
	}

	@Override
	public void cancelAppItem(String no,String lastUpdatedBy) {
		// TODO Auto-generated method stub
		purchaseDao.cancelAppItem(no,lastUpdatedBy);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_purchaseFeeDetail(
			Page<Map<String, Object>> page, Purchase purchase) {
		
		return purchaseDao.findPageBySql_purchaseFeeDetail(page, purchase);
	}

	@Override
	public Result doPurchFeeSave(PurchaseFee purchaseFee) {
		// TODO Auto-generated method stub
		return purchaseDao.doPurchFeeSave(purchaseFee);
	}

	@Override
	public String getPurchFeeNo(String no) {
		// TODO Auto-generated method stub
		return purchaseDao.getPurchFeeNo(no);
	}

	@Override
	public boolean existsPurchFee(String no) {
		// TODO Auto-generated method stub
		return purchaseDao.existsPurchFee(no);
	}

	@Override
	public String getLogoName(String custCode) {
		return purchaseDao.getLogoName(custCode);
	}
	
	
	
}
