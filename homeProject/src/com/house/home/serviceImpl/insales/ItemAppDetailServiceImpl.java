package com.house.home.serviceImpl.insales;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.ItemAppDetailDao;
import com.house.home.entity.insales.ItemAppDetail;
import com.house.home.service.insales.ItemAppDetailService;

@SuppressWarnings("serial")
@Service
public class ItemAppDetailServiceImpl extends BaseServiceImpl implements ItemAppDetailService {

	@Autowired
	private ItemAppDetailDao itemAppDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemAppDetail itemAppDetail){
		return itemAppDetailDao.findPageBySql(page, itemAppDetail);
	}
	
	public Page<Map<String,Object>> findPurchPageBySql(Page<Map<String,Object>> page, ItemAppDetail itemAppDetail){
		return itemAppDetailDao.findPurchPageBySql(page, itemAppDetail);
	}
	
	public Page<Map<String,Object>> findImportPageBySql(Page<Map<String,Object>> page, ItemAppDetail itemAppDetail){
		return itemAppDetailDao.findImportPageBySql(page, itemAppDetail);
	}

	public Page<Map<String, Object>> findPageByNo(
			Page<Map<String, Object>> page, String id) {
		return itemAppDetailDao.findPageByNo(page,id);
	}

	@Override
	public Page<Map<String, Object>> findItemAppDetailExists(Page<Map<String, Object>> page,
			Map<String, Object> param) {
		return itemAppDetailDao.findItemAppDetailExists(page, param);
	}

	@Override
	public Page<Map<String, Object>> findItemAppDetailFast(Page<Map<String, Object>> page, Map<String, Object> param) {
		return itemAppDetailDao.findItemAppDetailFast(page, param);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_return(
			Page<Map<String, Object>> page, ItemAppDetail itemAppDetail) {
		return itemAppDetailDao.findPageBySql_return(page,itemAppDetail);
	}

	@Override
	public Page<Map<String, Object>> findItemAppDetailExistsReturn(
			Page<Map<String, Object>> page, Map<String, Object> param) {
		return itemAppDetailDao.findItemAppDetailExistsReturn(page,param);
	}


	@Override
	public Page<Map<String, Object>> findCheckedPageBySql(
			Page<Map<String, Object>> page, ItemAppDetail itemAppDetail) {
		// TODO Auto-generated method stub
		return itemAppDetailDao.findCheckedPageBySql(page,itemAppDetail);
	}

	@Override
	public Page<Map<String, Object>> findAppliedPageBySql(
			Page<Map<String, Object>> page, ItemAppDetail itemAppDetail) {
		// TODO Auto-generated method stub
		return itemAppDetailDao.findAppliedPageBySql(page,itemAppDetail);
	}


	@Override
	public Page<Map<String, Object>> findPageByNo_khxxcx(
			Page<Map<String, Object>> page, String no, String custCode) {
		return itemAppDetailDao.findPageByNo_khxxcx(page,no,custCode);
	}

	@Override
	public List<Map<String, Object>> findNotSendItemList(String custCode) {
		// TODO Auto-generated method stub
		return itemAppDetailDao.findNotSendItemList(custCode);
	}

	@Override
	public Page<Map<String, Object>> findSendDetailBySql(
			Page<Map<String, Object>> page, ItemAppDetail itemAppDetail) {
		return itemAppDetailDao.findSendDetailBySql(page, itemAppDetail);
	}


}
