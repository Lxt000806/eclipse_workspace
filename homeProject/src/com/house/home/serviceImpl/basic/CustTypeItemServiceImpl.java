package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CustTypeItemDao;
import com.house.home.entity.basic.CustTypeItem;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.basic.Item;
import com.house.home.entity.project.PrjProg;
import com.house.home.service.basic.CustTypeItemService;

@SuppressWarnings("serial")
@Service
public class CustTypeItemServiceImpl extends BaseServiceImpl implements CustTypeItemService{
	@Autowired 
	private CustTypeItemDao custTypeItemDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CustTypeItem custTypeItem) {
		return custTypeItemDao.findPageBySql(page, custTypeItem);
	}

	@Override
	public void doSaveCustTypeItem(CustTypeItem custTypeItem) {
		// TODO Auto-generated method stub
		custTypeItemDao.doSaveCustTypeItem(custTypeItem);
	}

	@Override
	public String getCustTypeDescr(String custType) {
		// TODO Auto-generated method stub
		return custTypeItemDao.getCustTypeDescr(custType);
	}

	@Override
	public Result doSaveBatch(CustTypeItem custTypeItem) {
		// TODO Auto-generated method stub
		return custTypeItemDao.doSaveBatch(custTypeItem);
	}

	@Override
	public boolean hasExist(String itemCode, String custType, String remark) {
		// TODO Auto-generated method stub
		return custTypeItemDao.hasExist(itemCode,custType,remark);
	}

	@Override
	public Page<Map<String, Object>> getItemBySqlCode(
			Page<Map<String, Object>> page, Item item) {
		// TODO Auto-generated method stub
		return custTypeItemDao.getItemBySqlCode(page, item);
	}

	@Override
	public Page<Map<String, Object>> findItemPageBySql(
			Page<Map<String, Object>> page, Item item) {
		// TODO Auto-generated method stub
		return custTypeItemDao.findItemPageBySql(page, item);
	}
	
	@Override
	public boolean hasExists(String itemCode, String custType, String remark,
			Integer pk) {
		// TODO Auto-generated method stub
		return custTypeItemDao.hasExists(itemCode,custType,remark,pk);
	}

	@Override
	public boolean hasItem(Integer pk,String custType, String itemType1) {
		// TODO Auto-generated method stub
		return custTypeItemDao.hasItem(pk, custType, itemType1);
	}

	@Override
	public Integer getUniquePk(String itemCode, String custType, String itemType1,String remarks) {
		// TODO Auto-generated method stub
		return custTypeItemDao.getUniquePk(itemCode,custType, itemType1,remarks);
	}

	@Override
	public String getDiscAmtCalcTypeDescr(String discAmtCalcType) {
		return custTypeItemDao.getDiscAmtCalcTypeDescr(discAmtCalcType);
	}

	@Override
	public Result doSaveBatchAddNoExcelForProc(CustTypeItem custTypeItem) {
		return custTypeItemDao.doSaveBatchAddNoExcelForProc(custTypeItem);
	}
	
	@Override
	public boolean hasExistsICP(String itemCode, String custType, Double price, Integer pk) {
		return custTypeItemDao.hasExistsICP(itemCode,custType,price,pk);
	}
	
	@Override
	public boolean hasExistsICP(String itemCode, String custType, Double price) {
		return custTypeItemDao.hasExistsICP(itemCode,custType,price);
	}

	@Override
	public boolean checkFixAreaType(CustTypeItem custTypeItem) {
		
		return custTypeItemDao.checkFixAreaType(custTypeItem);
	}

	@Override
	public void doBatchDeal(CustTypeItem custTypeItem) {

		custTypeItemDao.doBatchDeal(custTypeItem);
	}
	
}
