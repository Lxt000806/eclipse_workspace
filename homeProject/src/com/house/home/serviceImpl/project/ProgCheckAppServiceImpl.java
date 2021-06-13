package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.project.ProgCheckAppDao;
import com.house.home.entity.project.ProgCheckApp;
import com.house.home.service.project.ProgCheckAppService;

@SuppressWarnings("serial")
@Service
public class ProgCheckAppServiceImpl extends BaseServiceImpl implements ProgCheckAppService {

	@Autowired
	private ProgCheckAppDao progCheckAppDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, 
			ProgCheckApp progCheckApp, UserContext uc){
		return progCheckAppDao.findPageBySql(page, progCheckApp, uc);
	}

	@Override
	public Map<String, Object> getByPk(Integer pk) {
		return progCheckAppDao.getByPk(pk);
	}

}
