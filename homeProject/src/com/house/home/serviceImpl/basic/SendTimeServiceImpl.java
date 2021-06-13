package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.SendTimeDao;
import com.house.home.entity.basic.SendTime;
import com.house.home.service.basic.SendTimeService;

@SuppressWarnings("serial")
@Service
public class SendTimeServiceImpl extends BaseServiceImpl implements SendTimeService {

	@Autowired
	private SendTimeDao sendTimeDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SendTime sendTime){
		return sendTimeDao.findPageBySql(page, sendTime);
	}

	@Override
	public Page<Map<String, Object>> goDetailGrid(Page<Map<String, Object>> page, SendTime sendTime) {
		return sendTimeDao.goDetailGrid(page, sendTime);
	}

	@Override
	public List<Map<String, Object>> isSupplierTime(SendTime sendTime) {
		return sendTimeDao.isSupplierTime(sendTime);
	}

	@Override
	public Result doSaveProc(SendTime sendTime) {
		return sendTimeDao.doSaveProc(sendTime);
	}

}
