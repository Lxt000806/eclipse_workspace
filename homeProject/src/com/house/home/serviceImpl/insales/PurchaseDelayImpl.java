package com.house.home.serviceImpl.insales;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.PurchaseDelayDao;
import com.house.home.entity.insales.PurchaseDelay;
import com.house.home.service.insales.PurchaseDelayService;

@SuppressWarnings("serial")
@Service
public class PurchaseDelayImpl extends BaseServiceImpl implements PurchaseDelayService{
	@Autowired
	private PurchaseDelayDao purchaseDelayDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PurchaseDelay purchaseDelay){
		return purchaseDelayDao.findPageBySql(page, purchaseDelay);
	}
}
