package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.AppServerUrlDao;
import com.house.home.entity.basic.AppServerUrl;
import com.house.home.service.basic.AppServerUrlService;

@SuppressWarnings("serial")
@Service
public class AppServerUrlServiceImpl extends BaseServiceImpl implements AppServerUrlService {

	@Autowired
	private AppServerUrlDao appServerUrlDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, AppServerUrl appServerUrl){
		return appServerUrlDao.findPageBySql(page, appServerUrl);
	}

}
