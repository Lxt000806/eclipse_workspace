package com.house.home.serviceImpl.finance;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.SupplCheckConfirmDao;
import com.house.home.entity.insales.Purchase;
import com.house.home.service.finance.SupplCheckConfirmService;

@SuppressWarnings("serial")
@Service 
public class SupplCheckConfirmImpl extends BaseServiceImpl implements SupplCheckConfirmService {
	@Autowired
	private  SupplCheckConfirmDao supplCheckConfirmDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Purchase purchase) {
		return supplCheckConfirmDao.findPageBySql(page, purchase);
	}

	@Override
	public Page<Map<String, Object>> findPurFeeDetailPageBySql(
			Page<Map<String, Object>> page, Purchase purchase) {
		return supplCheckConfirmDao.findPurFeeDetailPageBySql(page, purchase);
	}

	@Override
	public Result genSupplOtherFee(Purchase purchase) {
		return supplCheckConfirmDao.genSupplOtherFee(purchase);
	}

	@Override
	public List<Map<String, Object>> isSupplierChecked(String purchaseNo) {
		return supplCheckConfirmDao.isSupplierChecked(purchaseNo);
	}


}
