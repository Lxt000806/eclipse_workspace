package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ItemSetDao;
import com.house.home.entity.basic.ItemSet;
import com.house.home.entity.basic.ItemSetDetail;
import com.house.home.service.basic.ItemSetService;

@SuppressWarnings("serial")
@Service
public class ItemSetServiceImpl extends BaseServiceImpl implements ItemSetService {

	@Autowired
	private ItemSetDao itemSetDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemSet itemSet) {
		return itemSetDao.findPageBySql(page, itemSet);
	}

	@Override
	public Page<Map<String, Object>> findPageBySqlDetail(
			Page<Map<String, Object>> page, ItemSetDetail itemSetDetail) {
		return itemSetDao.findPageBySqlDetail(page, itemSetDetail);
	}

	@Override
	public Result doItemSetSave(ItemSet itemSet) {
		return itemSetDao.doitemsetReturnCheckOut(itemSet);
	}

	@Override
	public Page<Map<String, Object>> findPageBySqlDetailadd(
			Page<Map<String, Object>> page, ItemSetDetail itemSetDetail) {
		return itemSetDao.findPageBySqlDetailadd(page, itemSetDetail);	
	}

	@Override
	public Result deleteForProc(ItemSet itemSet) {
		return itemSetDao.doitemsetReturnCheckOut(itemSet);
	}

	@Override
	public ItemSet getByDescr(String descr) {
		return itemSetDao.getByDescr(descr);
	}

	@Override
	public ItemSet getByDescr1(String descr, String descr1) {
		return itemSetDao.getByDescr1(descr, descr1);
	}
	
	@Override
	public List<Map<String, Object>> findItemSetNo(ItemSet itemSet) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		resultList = this.itemSetDao.findItemSetNo(itemSet);
		return resultList;
	}
	
}
 
