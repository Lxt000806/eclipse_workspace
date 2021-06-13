package com.house.home.serviceImpl.finance;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.finance.SupplierPayDao;
import com.house.home.entity.finance.SupplierPay;
import com.house.home.service.finance.SupplierPayService;

@SuppressWarnings("serial")
@Service 
public class SupplierPayServiceImpl extends BaseServiceImpl implements SupplierPayService {
	@Autowired
	private  SupplierPayDao supplierPayDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SupplierPay supplierPay) {
		return supplierPayDao.findPageBySql(page, supplierPay);
	}

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, SupplierPay supplierPay) {
		return supplierPayDao.findDetailPageBySql(page, supplierPay);
	}

	@Override
	public List<Map<String, Object>> doSetPaidAmount(SupplierPay supplierPay) {
		return supplierPayDao.doSetPaidAmount(supplierPay);
	}

	@Override
	public Map<String, Object> getSplInfo(SupplierPay supplierPay) {
		return supplierPayDao.getSplInfo(supplierPay);
	}

	@Override
	public Map<String, Object> getSumPaidAmount(SupplierPay supplierPay) {
		return supplierPayDao.getSumPaidAmount(supplierPay);
	}

	@Override
	public Map<String, Object> getSumRemainAmount(SupplierPay supplierPay) {
		return supplierPayDao.getSumRemainAmount(supplierPay);
	}

	@Override
	public Map<String, Object> getSumPaidAmount2(SupplierPay supplierPay) {
		return supplierPayDao.getSumPaidAmount2(supplierPay);
	}

	@Override
	public Result doSave(SupplierPay supplierPay) {
		return supplierPayDao.doSave(supplierPay);
	}

}
