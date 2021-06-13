package com.house.home.serviceImpl.insales;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.ItemWHBalDao;
import com.house.home.entity.insales.ItemWHBal;
import com.house.home.service.insales.ItemWHBalService;

@SuppressWarnings("serial")
@Service
public class ItemWHBalServiceImpl extends BaseServiceImpl implements
		ItemWHBalService {
	@Autowired
	private ItemWHBalDao itemWHBalDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
		return itemWHBalDao.findPageBySql(page,itemWHBal);
	}
	
	@Override
	public Page<Map<String, Object>> findPurchPageBySql(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
		return itemWHBalDao.findPurchPageBySql(page,itemWHBal);
	}

	@Override
	public Page<Map<String, Object>> findPageGroupByItem(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
		return itemWHBalDao.findPageGroupByItem(page,itemWHBal);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_ckzxp(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
		return itemWHBalDao.findPageBySql_ckzxp(page,itemWHBal);
	}

	@Override
	public List<Map<String, Object>> findListBySql_ckzxp(ItemWHBal itemWHBal) {
		return itemWHBalDao.findListBySql_ckzxp(itemWHBal);
	}

	@Override
	public Page<Map<String, Object>> findItemSampleDetailPageBySql(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
		return itemWHBalDao.findItemSampleDetailPageBySql(page, itemWHBal);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_kcyecx(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
		return itemWHBalDao.findPageBySql_kcyecx(page, itemWHBal);
	}

	@Override
	public Page<Map<String, Object>> findWHPosiBalPageBySql(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
		return itemWHBalDao.findWHPosiBalPageBySql(page, itemWHBal);
	} 

	@Override
	public Page<Map<String, Object>> findItemType2PageBySql(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
		return itemWHBalDao.findItemType2PageBySql(page, itemWHBal);
	}
	
	@Override
	public Page<Map<String, Object>> findOrderAnalysis(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
		return itemWHBalDao.findOrderAnalysis(page, itemWHBal);
	}

}
