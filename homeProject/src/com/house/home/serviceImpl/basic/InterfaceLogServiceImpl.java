package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.InterfaceLogDao;
import com.house.home.entity.basic.InterfaceLog;
import com.house.home.service.basic.InterfaceLogService;

@SuppressWarnings("serial")
@Service
public class InterfaceLogServiceImpl extends BaseServiceImpl implements InterfaceLogService {

	@Autowired
	private InterfaceLogDao interfaceLogDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, InterfaceLog interfaceLog){
		return interfaceLogDao.findPageBySql(page, interfaceLog);
	}

}
