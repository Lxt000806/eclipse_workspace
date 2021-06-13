package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ItemTypeConfirmDao;
import com.house.home.entity.basic.ItemTypeConfirm;
import com.house.home.service.basic.ItemTypeConfirmService;

@SuppressWarnings("serial")
@Service
public class ItemTypeConfirmServiceImpl extends BaseServiceImpl implements ItemTypeConfirmService{

	@Autowired
	private ItemTypeConfirmDao itemTypeConfirmDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,ItemTypeConfirm itemTypeConfirm) {
		// TODO Auto-generated method stub
		return itemTypeConfirmDao.findPageBySql(page,itemTypeConfirm);
	}

	
	
	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page,ItemTypeConfirm itemTypeConfirm) {
		// TODO Auto-generated method stub
		return itemTypeConfirmDao.findDetailPageBySql(page,itemTypeConfirm);
	}



	@Override
	public Result doSave(ItemTypeConfirm itemTypeConfirm) {
		// TODO Auto-generated method stub
		return itemTypeConfirmDao.doSave(itemTypeConfirm);
	}
	
	
	
}
