package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.AutoArrWorkerAppDao;
import com.house.home.entity.project.AutoArrWorkerApp;
import com.house.home.service.project.AutoArrWorkerAppService;

@SuppressWarnings("serial")
@Service
public class AutoArrWorkerAppServiceImpl extends BaseServiceImpl implements AutoArrWorkerAppService {

	@Autowired
	private AutoArrWorkerAppDao autoArrWorkerAppDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, AutoArrWorkerApp autoArrWorkerApp){
		return autoArrWorkerAppDao.findPageBySql(page, autoArrWorkerApp);
	}

}
