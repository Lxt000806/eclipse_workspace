package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.ItemReqDetailQueryDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.ItemReqDetailQueryService;

@Service
@SuppressWarnings("serial")
public class ItemReqDetailQueryServiceImpl extends BaseServiceImpl implements ItemReqDetailQueryService{
	
	@Autowired
	private ItemReqDetailQueryDao itemReqDetailQueryDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return itemReqDetailQueryDao.findPageBySql(page,customer);
	}
	
		
}
