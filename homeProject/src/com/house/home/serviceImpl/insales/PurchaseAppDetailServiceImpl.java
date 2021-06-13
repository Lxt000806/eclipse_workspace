package com.house.home.serviceImpl.insales;

import com.house.framework.commons.orm.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.insales.PurchaseAppDetailDao;
import com.house.home.service.insales.PurchaseAppDetailService;

import com.house.framework.commons.orm.Page;
import java.util.Map;import com.house.home.entity.insales.PurchaseAppDetail;
@SuppressWarnings("serial")
@Service 
public class PurchaseAppDetailServiceImpl extends BaseServiceImpl implements PurchaseAppDetailService {
	@Autowired
	private  PurchaseAppDetailDao purchaseAppDetailDao;
	@Override	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, PurchaseAppDetail purchaseAppDetail){
	
		return purchaseAppDetailDao.findPageBySql(page, purchaseAppDetail);
	}
}
