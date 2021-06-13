package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.PurchaseDetailDao;
import com.house.home.entity.insales.PurchaseDetail;
import com.house.home.service.insales.PurchaseDetailService;

@SuppressWarnings("serial")
@Service
public class PurchaseDetailServiceImpl extends BaseServiceImpl implements PurchaseDetailService {

	@Autowired
	private PurchaseDetailDao purchaseDetailDao;
	
	@Override
	public Page<Map<String,Object>> findPurchPageBySql(Page<Map<String,Object>> page, PurchaseDetail purchaseDetail){
		return purchaseDetailDao.findPurchPageBySql(page, purchaseDetail);
	}
	
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PurchaseDetail purchaseDetail){
		return purchaseDetailDao.findPageBySql(page, purchaseDetail);
	}
	public Page<Map<String,Object>> findViewOPageBySql(Page<Map<String,Object>> page, PurchaseDetail purchaseDetail){
		return purchaseDetailDao.findViewOPageBySql(page, purchaseDetail);
	}
	public Page<Map<String,Object>> findViewCPageBySql(Page<Map<String,Object>> page, PurchaseDetail purchaseDetail){
		return purchaseDetailDao.findViewCPageBySql(page, purchaseDetail);
	}

	@Override
	public Page<Map<String,Object>> findNotArriPageBySql(Page<Map<String, Object>> page,
			PurchaseDetail purchaseDetail) {
		return purchaseDetailDao.findNotArriPageBySql(page, purchaseDetail);
	}

	@Override
	public Page<Map<String, Object>> findPageByNo(
			Page<Map<String, Object>> page, String no) {
		return purchaseDetailDao.findPageByNo(page,no);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_dhfx(
			Page<Map<String, Object>> page, PurchaseDetail purchaseDetail) {
		return purchaseDetailDao.findPageBySql_dhfx(page,purchaseDetail);
	}

	@Override
	public PurchaseDetail getPurchaseDetail(PurchaseDetail purchaseDetail){
		return purchaseDetailDao.getPurchaseDetail(purchaseDetail);
	}
	
	@Override
	public PurchaseDetail getQtyCal(PurchaseDetail purchaseDetail){
		return purchaseDetailDao.getQtyCal(purchaseDetail);
	}
	
}
