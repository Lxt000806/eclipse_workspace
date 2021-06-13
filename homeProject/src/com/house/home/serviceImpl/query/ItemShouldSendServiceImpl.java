package com.house.home.serviceImpl.query;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.ItemShouldSendDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.ItemShouldSendService;
@Service
@SuppressWarnings("serial")
public class ItemShouldSendServiceImpl extends BaseServiceImpl implements ItemShouldSendService {
	@Autowired
	private ItemShouldSendDao itemShouldSendDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return itemShouldSendDao.findPageBySql(page,customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_detail(
			Page<Map<String, Object>> page, Customer customer) {
		return itemShouldSendDao.findPageBySql_detail(page,customer);
	}

	@Override
	public Map<String, Object> getItemAppInfo(String iaNo) {
		return itemShouldSendDao.getItemAppInfo(iaNo);
	}
	
}
