package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.SendToCmpWhDao;
import com.house.home.entity.basic.SendToCmpWh;
import com.house.home.service.basic.SendToCmpWhService;

@SuppressWarnings("serial")
@Service
public class SendToCmpWhServiceImpl extends BaseServiceImpl implements SendToCmpWhService {

	@Autowired
	private SendToCmpWhDao sendToCmpWhDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SendToCmpWh sendToCmpWh){
		return sendToCmpWhDao.findPageBySql(page, sendToCmpWh);
	}

}
