package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.QdhbfxDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.QdhbfxService;

@Service
@SuppressWarnings("serial")
public class QdhbfxServiceImpl extends BaseServiceImpl implements QdhbfxService{

	@Autowired
	private QdhbfxDao qdhbfxDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,Customer customer,String role) {
		// TODO Auto-generated method stub
		return qdhbfxDao.findPageBySql(page,customer,role);
	
	}
	
}
