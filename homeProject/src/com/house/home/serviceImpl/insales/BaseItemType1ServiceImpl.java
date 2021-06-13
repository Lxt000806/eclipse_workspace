package com.house.home.serviceImpl.insales;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.entity.Menu;
import com.house.home.dao.insales.BaseItemType1Dao;
import com.house.home.dao.insales.BaseItemType2Dao;
import com.house.home.entity.insales.BaseItemType1;
import com.house.home.service.insales.BaseItemType1Service;

@SuppressWarnings("serial")
@Service
public class BaseItemType1ServiceImpl extends BaseServiceImpl implements BaseItemType1Service {

	@Autowired
	private BaseItemType1Dao baseItemType1Dao;
	@Autowired
	private BaseItemType2Dao baseItemType2Dao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemType1 baseItemType1){
		return baseItemType1Dao.findPageBySql(page, baseItemType1);
	}

	@Override
	public List<Menu> getTreeMenu() {
		// TODO Auto-generated method stub
		return baseItemType1Dao.getTreeMenu();
	}
    /**
     * 三级联动
     */
	@Override
	public List<Map<String, Object>> findBaseItemType(int type, String pCode) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			resultList = this.baseItemType1Dao.findBaseItemType1(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.baseItemType2Dao.findBaseItemType2(param);
		}
		return resultList;
	}
	
	@Override
	public List<Menu> getCheckTreeMenu() {
		// TODO Auto-generated method stub
		return baseItemType1Dao.getCheckTreeMenu();
	}

	@Override
	public boolean valideCode(String id) {
		// TODO Auto-generated method stub
		return baseItemType1Dao.valideCode(id);
	}

	@Override
	public Page<Map<String, Object>> getBaseItemType1(
			Page<Map<String, Object>> page) {
		return baseItemType1Dao.getBaseItemType1(page);
	}

	

}
