package com.house.home.serviceImpl.query;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.WlpshzDao;
import com.house.home.entity.basic.Item;
import com.house.home.service.query.WlpshzService;

@Service
@SuppressWarnings("serial")
public class WlpshzServiceImpl extends BaseServiceImpl implements WlpshzService{
	
	@Autowired
	private WlpshzDao wlpshzDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Item item) {
		// TODO Auto-generated method stub
		return wlpshzDao.findPageBySql(page, item);
	}

	@Override
	public Page<Map<String, Object>> findTilePageBySql(
			Page<Map<String, Object>> page, String date) {
		// TODO Auto-generated method stub
		return wlpshzDao.findTilePageBySql(page, date);
	}

	@Override
	public Page<Map<String, Object>> findToiletPageBySql(
			Page<Map<String, Object>> page, String date) {
		// TODO Auto-generated method stub
		return wlpshzDao.findToiletPageBySql(page, date);
	}

	@Override
	public Page<Map<String, Object>> findFloorPageBySql(
			Page<Map<String, Object>> page, String date) {
		return wlpshzDao.findFloorPageBySql(page, date);
	}
	
	
	
}
