package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ItemType12Dao;
import com.house.home.entity.basic.ItemType12;
import com.house.home.service.basic.ItemType12Service;

@SuppressWarnings("serial")
@Service
public class ItemType12ServiceImpl extends BaseServiceImpl implements ItemType12Service {

	@Autowired
	private ItemType12Dao itemtype12Dao;

	
	@Override
	public ItemType12 getByDescr(String descr) {
		return itemtype12Dao.getByDescr(descr);
	}


	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemType12 itemtype12) {
		return itemtype12Dao.findPageBySql(page, itemtype12);
	}


	@Override
	public Result doItemType12Save(ItemType12 itemtype12) {
		return itemtype12Dao.doitemtype12ReturnCheckOut(itemtype12);
	}


	@Override
	public Result deleteForProc(ItemType12 itemtype12) {
		return itemtype12Dao.doitemtype12ReturnCheckOut(itemtype12);
	}


	@Override
	public ItemType12 getByDescr1(String descr, String descr1) {
		return itemtype12Dao.getByDescr1(descr,descr1);
	}


	
}
 
