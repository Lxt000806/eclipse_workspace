package com.house.home.serviceImpl.basic;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.entity.Menu;
import com.house.home.dao.basic.ItemType2Dao;
import com.house.home.dao.basic.ItemType3Dao;
import com.house.home.entity.basic.ItemType2;
import com.house.home.service.basic.ItemType2Service;

@SuppressWarnings("serial")
@Service
public class ItemType2ServiceImpl extends BaseServiceImpl implements ItemType2Service {

	@Autowired
	private ItemType2Dao itemType2Dao;
	@Autowired
	private ItemType3Dao itemType3Dao;
	
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemType2 itemType2){
		return itemType2Dao.findPageBySql(page, itemType2);
	}

	@Override
	public List<Map<String, Object>> findItemType2(Map<String, Object> param) {
		return itemType2Dao.findItemType2(param);
	}

	@Override
	public List<Menu> findAllItemType2ByitemType1(
			String itemType1) {
		return itemType2Dao.findAllItemType2ByitemType1(itemType1);
	}

	@Override
	public List<ItemType2> findByItemType1(String itemType1) {
		return itemType2Dao.findByItemType1(itemType1);
	}
	public Map<String, Object> getItemType2Detail(String code) {
		return itemType2Dao.getItemType2sDetail(code);
	}
	
	@Override
	public List<Map<String, Object>> findPrjType(int type,
			String pCode) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			resultList = this.itemType2Dao.findPrjType1(param);
		}
		return resultList;
	}

	@Override
	public List<Map<String, Object>> findItemType2(int type, String pCode) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			resultList = this.itemType2Dao.findByItemType2Fir(param);
		}else if(type == 2){
			param.put("pCode", pCode);
			resultList = this.itemType3Dao.findByItemByType3(param);
			}
		return resultList;
	}
	
	@Override
	public Map<String, Object> getItemType1ByItemType2(String itemType2) {
		return itemType2Dao.getItemType1ByItemType2(itemType2);
	}
}
