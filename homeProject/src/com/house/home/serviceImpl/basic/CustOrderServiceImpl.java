package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CustOrderDao;
import com.house.home.entity.basic.CustOrder;
import com.house.home.service.basic.CustOrderService;

@SuppressWarnings("serial")
@Service
public class CustOrderServiceImpl extends BaseServiceImpl implements CustOrderService {

	@Autowired
	private CustOrderDao custOrderDao;
	
	@Override
	public boolean existsCustOrder(String phone){
		return custOrderDao.existsCustOrder(phone);
	}
	
	@Override
	public Page<Map<String, Object>> goJqGrid(Page<Map<String, Object>> page, CustOrder custOrder){
		return custOrderDao.goJqGrid(page, custOrder);
	}
}
