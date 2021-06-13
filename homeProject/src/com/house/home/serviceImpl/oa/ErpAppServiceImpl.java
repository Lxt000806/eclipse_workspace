package com.house.home.serviceImpl.oa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.home.dao.oa.ErpAppDao;
import com.house.home.entity.oa.AppErp;
import com.house.home.entity.oa.Leave;
import com.house.home.service.oa.ErpAppService;

@SuppressWarnings("serial")
@Service
public class ErpAppServiceImpl extends BaseServiceImpl implements ErpAppService {
	
	@Autowired
	private ErpAppDao erpAppDao;
	
	@Override
	public AppErp getByProcessInstanceId(String id) {
		return erpAppDao.getByProcessInstanceId(id);
	}
	
}
