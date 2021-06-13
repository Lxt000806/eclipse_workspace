package com.house.home.serviceImpl.finance;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.SupplierCheckDao;
import com.house.home.entity.finance.SplCheckOut;
import com.house.home.service.finance.SupplierCheckService;

@SuppressWarnings("serial")
@Service
public class SupplierCheckServiceImpl extends BaseServiceImpl implements SupplierCheckService {

	@Autowired
	private SupplierCheckDao supplierCheckDao;
	
	@Override
	public Page<Map<String,Object>> goJqGrid(Page<Map<String,Object>> page, SplCheckOut splCheckOut){
		return supplierCheckDao.goJqGrid(page, splCheckOut);
	}
	
	@Override
	public Page<Map<String,Object>> goJqGridAddPurchase(Page<Map<String,Object>> page, SplCheckOut splCheckOut){
		return supplierCheckDao.goJqGridAddPurchase(page, splCheckOut);
	}
	
	@Override
	public List<Map<String,Object>> goJqGridIntInstall(Page<Map<String,Object>> page, String custCode){
		return supplierCheckDao.goJqGridIntInstall(page, custCode);
	}
	
	@Override
	public List<Map<String,Object>> goJqGridMainItem(Page<Map<String,Object>> page, String checkOutNo){
		return supplierCheckDao.goJqGridMainItem(page, checkOutNo);
	}
	
	@Override
	public List<Map<String,Object>> goJqGridExcess(Page<Map<String,Object>> page, String nos, String splCode, String checkOutNo){
		return supplierCheckDao.goJqGridExcess(page, nos, splCode, checkOutNo);
	}
	
	@Override
	public List<Map<String,Object>> goJqGridWithHold(Page<Map<String,Object>> page, String nos, String splCode, String checkOutNo){
		return supplierCheckDao.goJqGridWithHold(page, nos, splCode, checkOutNo);
	}
	
	@Override
	public Result doSaveForProc(SplCheckOut splCheckOut){
		return supplierCheckDao.doSaveForProc(splCheckOut);
	}
	
	@Override
	public Result doShForProc(SplCheckOut splCheckOut){
		return supplierCheckDao.doShForProc(splCheckOut);
	}
	
	@Override
	public Map<String, Object> getSplCheckOutByNo(String no){
		return supplierCheckDao.getSplCheckOutByNo(no);
	}
	
	@Override
	public Map<String, Object> checkSupplierPay(String no){
		return supplierCheckDao.checkSupplierPay(no);
	}
	
	@Override
	public List<Map<String, Object>> judgePrintPage(String no){
		return supplierCheckDao.judgePrintPage(no);
	}

	@Override
	public Map<String, Object> doGenOtherCostForProc(SplCheckOut splCheckOut) {
		// TODO Auto-generated method stub
		return supplierCheckDao.doGenOtherCostForProc(splCheckOut);
	}

	@Override
	public Page<Map<String, Object>> goJqGridMainItemByCompany(
			Page<Map<String, Object>> page, SplCheckOut splCheckOut) {
		
		return supplierCheckDao.goJqGridMainItemByCompany(page,splCheckOut);
	}

	@Override
	public Page<Map<String, Object>> goJqGridMainItemByItemType3(
			Page<Map<String, Object>> page, SplCheckOut splCheckOut) {
		return supplierCheckDao.goJqGridMainItemByItemType3(page, splCheckOut);
	}

	@Override
	public Page<Map<String, Object>> goJqGridMainItemByCustDept(
			Page<Map<String, Object>> page, SplCheckOut splCheckOut) {
		return supplierCheckDao.goJqGridMainItemByCustDept(page, splCheckOut);
	}

	@Override
	public String getNoChecAppReturnNum(String supplCode) {
		
		return supplierCheckDao.getNoChecAppReturnNum(supplCode);
	}

	@Override
	public List<Map<String, Object>> getDetailByCheckOutNo(
			SplCheckOut splCheckOut) {

		return supplierCheckDao.getDetailByCheckOutNo(splCheckOut);
	}

	@Override
	public Page<Map<String, Object>> findProcListJqGrid(
			Page<Map<String, Object>> page, SplCheckOut splCheckOut) {

		return supplierCheckDao.findProcListJqGrid(page, splCheckOut);
	}

	@Override
	public Map<String, Object> getAmountByCheckOutNo(SplCheckOut splCheckOut) {
		return supplierCheckDao.getAmountByCheckOutNo(splCheckOut);
	} 
	
}
