package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ItemType3Dao;
import com.house.home.entity.basic.ItemType3;
import com.house.home.service.basic.ItemType3Service;

@SuppressWarnings("serial")
@Service
public class ItemType3ServiceImpl extends BaseServiceImpl implements ItemType3Service {

	@Autowired
	private ItemType3Dao itemType3Dao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemType3 itemType3){
		return itemType3Dao.findPageBySql(page, itemType3);
	}

	@Override
	public List<Map<String, Object>> findItemType3(Map<String, Object> param) {
		return itemType3Dao.findItemType3(param);
	}

	@Override
	public Map<String, Object> findAllItemType3(String descr) {
		// TODO Auto-generated method stub
		return itemType3Dao.findAllItemType3(descr);
	}

	@Override
	public Map<String, Object> findThItemType3(ItemType3 itemType3) {
		// TODO Auto-generated method stub
		return itemType3Dao.findThItemType3(itemType3);
	}
}
