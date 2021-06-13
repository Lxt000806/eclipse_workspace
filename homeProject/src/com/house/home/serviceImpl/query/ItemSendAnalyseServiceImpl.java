package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.query.ItemSendAnalyseDao;
import com.house.home.entity.insales.ItemApp;
import com.house.home.service.query.ItemSendAnalyseService;
@SuppressWarnings("serial")
@Service
public class ItemSendAnalyseServiceImpl extends BaseServiceImpl implements
		ItemSendAnalyseService {
	@Autowired
	private ItemSendAnalyseDao itemSendAnalyseDao;
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemApp itemApp, UserContext uc) {
		// TODO Auto-generated method stub
		return itemSendAnalyseDao.findPageBySql(page, itemApp, uc);
	}
	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, ItemApp itemApp) {
		// TODO Auto-generated method stub
		return itemSendAnalyseDao.findDetailPageBySql(page, itemApp);
	}
	
	@Override
	public Page<Map<String, Object>> findItemPageBySql(
			Page<Map<String, Object>> page, ItemApp itemApp) {
		return itemSendAnalyseDao.findItemPageBySql(page, itemApp);
	}

}
