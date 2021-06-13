package com.house.home.serviceImpl.insales;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.PurchasePayDao;
import com.house.home.entity.insales.PurchasePay;
import com.house.home.service.insales.PurchasePayService;


@SuppressWarnings("serial")
@Service
public class PurchasePayServiceImpl extends BaseServiceImpl implements PurchasePayService {

	@Autowired
	private PurchasePayDao purchasePayDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PurchasePay purchasePay){
		return purchasePayDao.findPageBySql(page, purchasePay);
	}
	
}
