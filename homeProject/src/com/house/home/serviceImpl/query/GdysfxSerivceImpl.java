package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.GdysfxDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.GdysfxSerivce;

@Service
@SuppressWarnings("serial")
public class GdysfxSerivceImpl extends BaseServiceImpl implements GdysfxSerivce {

	@Autowired
	private GdysfxDao gdysfxDao ;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer,String orderBy,String direction) {
		return gdysfxDao.findPageBySql(page,customer,orderBy,direction);
	}
	
	
}
