package com.house.home.serviceImpl.project;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.IntProduceDao;
import com.house.home.entity.project.IntProduce;
import com.house.home.service.project.IntProduceService;

@SuppressWarnings("serial")
@Service
public class IntProduceServiceImpl extends BaseServiceImpl implements IntProduceService{

	@Autowired
	private IntProduceDao intProduceDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, IntProduce intProduce) {
		return intProduceDao.findPageBySql(page, intProduce);
	}
	
	@Override
	public void doIntProdSave(IntProduce intProduce) {
		intProduce.setActionLog("ADD");
		intProduce.setLastUpdate(new Date());
		intProduce.setExpired("F");
		this.save(intProduce);
	}

	@Override
	public List<Map<String, Object>> getCustIntProg(IntProduce intProduce) {
		return intProduceDao.getCustIntProg(intProduce);
	}

	@Override
	public List<Map<String, Object>> getIntProduce(IntProduce intProduce) {
		return intProduceDao.getIntProduce(intProduce);
	}

	@Override
	public void doIntProdUpdate(IntProduce intProduce) {
		intProduce.setActionLog("Edit");
		intProduce.setLastUpdate(new Date());
		intProduce.setExpired("F");
		this.update(intProduce);
	}
}
