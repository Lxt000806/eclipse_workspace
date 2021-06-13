package com.house.home.serviceImpl.finance;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.PayManageDao;
import com.house.home.entity.finance.SupplierPrepay;
import com.house.home.entity.finance.SupplierPrepayDetail;
import com.house.home.entity.insales.Supplier;
import com.house.home.service.finance.PayManageService;

@SuppressWarnings("serial")
@Service
public class PayManageServiceImpl extends BaseServiceImpl implements PayManageService {

	@Autowired
	private PayManageDao paymanageDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SupplierPrepay supplierPrepay) {
		return paymanageDao.findPageBySql(page, supplierPrepay);
	}

	@Override
	public Result dopayManageSave(SupplierPrepay supplierPrepay) {
		// TODO Auto-generated method stub
		return paymanageDao.doPayManageReturnCheckOut(supplierPrepay);
	}
	

	@Override
	public Page<Map<String, Object>> findPageBySqlDetail(
			Page<Map<String, Object>> page, SupplierPrepayDetail supplierPrepayDetail) {
		// TODO Auto-generated method stub
		return paymanageDao.findPageBySqlDetail(page, supplierPrepayDetail);
	}
	
	@Override
	public Page<Map<String, Object>> findPageBySqlMxSelect(
			Page<Map<String, Object>> page, SupplierPrepay supplierPrepay) {
		// TODO Auto-generated method stub
		return paymanageDao.findPageBySqlMxSelect(page, supplierPrepay);
	}

	@Override
	public Page<Map<String, Object>> findPageBySqlYeSelect(
			Page<Map<String, Object>> page, SupplierPrepay supplierPrepay) {
		// TODO Auto-generated method stub
		return paymanageDao.findPageBySqlYeSelect(page, supplierPrepay);
	}
	
	@Override
	public Page<Map<String, Object>> goJqGridYeChangeSelect(
			Page<Map<String, Object>> page,Supplier supplier) {
		// TODO Auto-generated method stub
		return paymanageDao.findPageBySqlYeChangeSelect(page, supplier);
	}

	
	
	@Override
	public Page<Map<String, Object>> getSupplAccountJqGrid(
			Page<Map<String, Object>> page, SupplierPrepay supplierPrepay) {
		
		return paymanageDao.getSupplAccountJqGrid(page, supplierPrepay);
	}

	@Override
	public List<Map<String, Object>> getDetailOrderBySupplCode(
			SupplierPrepay supplierPrepay) {
		return paymanageDao.getDetailOrderBySupplCode(supplierPrepay);
	}

	@Override
	public Page<Map<String, Object>> findProcListJqGrid(
			Page<Map<String, Object>> page, SupplierPrepay supplierPrepay) {
		return paymanageDao.findProcListJqGrid(page, supplierPrepay);
	}

	@Override
	public Page<Map<String, Object>> getPuJqGrid(Page<Map<String, Object>> page, SupplierPrepay supplierPrepay) {
		return paymanageDao.getPuJqGrid(page, supplierPrepay);
	}

	@Override
	public String getPunos(String no, String supplCode) {
		return paymanageDao.getPunos(no, supplCode);
	}
	
}
 
