package com.house.home.serviceImpl.query;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.PurchasePartArriveDao;
import com.house.home.entity.insales.Purchase;
import com.house.home.service.query.PurchasePartArriveService;

@SuppressWarnings("serial")
@Service
public class PurchasePartArriveServiceImpl extends BaseServiceImpl implements PurchasePartArriveService {

	@Autowired
	private PurchasePartArriveDao purchasePartArriveDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, Purchase purchase) {
		return purchasePartArriveDao.findPageBySql(page, purchase);
	}

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, Purchase purchase) {
		return purchasePartArriveDao.findDetailPageBySql(page, purchase);
	}
}
