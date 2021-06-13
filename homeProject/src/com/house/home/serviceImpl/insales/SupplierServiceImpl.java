package com.house.home.serviceImpl.insales;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.SupplierDao;
import com.house.home.entity.insales.Supplier;
import com.house.home.service.insales.SupplierService;

@SuppressWarnings("serial")
@Service
public class SupplierServiceImpl extends BaseServiceImpl implements SupplierService {

	@Autowired
	private SupplierDao supplierDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Supplier supplier,String itemRight){
		return supplierDao.findPageBySql(page, supplier,itemRight);
	}

	@Override
	public String getItemType1(String czybh) {
		// TODO Auto-generated method stub
		return supplierDao.getItemType1(czybh);
	}

	@Override
	public Page<Map<String, Object>> findSupplierPageBySql(
			Page<Map<String, Object>> page, Supplier supplier) {
		return supplierDao.findSupplierPageBySql(page, supplier);
	}

	@Override
	public Result doSave(Supplier supplier) {
		return supplierDao.doSave(supplier);
	}

	@Override
	public Page<Map<String, Object>> findPrepayTranJqGridBySql(
			Page<Map<String, Object>> page, Supplier supplier) {
		return supplierDao.findPrepayTranJqGridBySql(page, supplier);
	}

	@Override
	public Page<Map<String, Object>> findSupTimeJqGridBySql(
			Page<Map<String, Object>> page, Supplier supplier) {
		return supplierDao.findSupTimeJqGridBySql(page, supplier);
	}

	@Override
	public Result doDDDSave(Supplier supplier) {
		return supplierDao.doDDDSave(supplier);
	}

	@Override
	public Page<Map<String, Object>> getCmpCode(Page<Map<String, Object>> page,
			Supplier supplier) {
		return supplierDao.getCmpCode(page, supplier);
	}
	
	
}
