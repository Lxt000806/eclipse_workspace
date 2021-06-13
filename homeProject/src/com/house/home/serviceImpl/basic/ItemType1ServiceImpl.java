package com.house.home.serviceImpl.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ItemType12Dao;
import com.house.home.dao.basic.ItemType1Dao;
import com.house.home.entity.basic.ItemType1;
import com.house.home.service.basic.ItemType1Service;

@SuppressWarnings("serial")
@Service
public class ItemType1ServiceImpl extends BaseServiceImpl implements ItemType1Service {

	@Autowired
	private ItemType1Dao itemType1Dao;
	@Autowired 
	private ItemType12Dao itemType12Dao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemType1 itemType1){
		return itemType1Dao.findPageBySql(page, itemType1);
	}

	public List<ItemType1> findByNoExpired() {
		return itemType1Dao.findByNoExpired();
	}

	@Override
	public List<Map<String, Object>> findItemType1(Map<String, Object> param) {
		return itemType1Dao.findItemType1(param);
	}
	
	
	@Override
	public List<Map<String, Object>> findItemType1(int type,
			String pCode) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			resultList = this.itemType1Dao.findItemByType1(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.itemType12Dao.findItemByType12(param);
			}
		return resultList;
	}
	

}
