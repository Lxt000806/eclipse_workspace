package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.PrjItem2Dao;
import com.house.home.entity.basic.PrjItem2;
import com.house.home.service.basic.PrjItem2Service;

@SuppressWarnings("serial")
@Service
public class PrjItem2ServiceImpl extends BaseServiceImpl implements
		PrjItem2Service {
	@Autowired
	private PrjItem2Dao prjItem2Dao;
	
	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, PrjItem2 prjItem2) {
		return prjItem2Dao.findPageBySql(page, prjItem2);
	}
	
	@Override
	public List<Map<String, Object>> getPrjItem2ListByPrjItem1(String prjItem1){
		return prjItem2Dao.getPrjItem2ListByPrjItem1(prjItem1);
	}
}
