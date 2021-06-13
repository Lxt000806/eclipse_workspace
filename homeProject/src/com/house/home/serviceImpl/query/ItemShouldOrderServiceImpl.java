package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.ItemShouldOrderDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.ItemShouldOrderService;
@Service
@SuppressWarnings("serial")
public class ItemShouldOrderServiceImpl extends BaseServiceImpl implements ItemShouldOrderService {
	@Autowired
	private ItemShouldOrderDao itemShouldOrderDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return itemShouldOrderDao.findPageBySql(page,customer);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_detail(
			Page<Map<String, Object>> page, Customer customer) {
		return itemShouldOrderDao.findPageBySql_detail(page,customer);
	}
	
}
