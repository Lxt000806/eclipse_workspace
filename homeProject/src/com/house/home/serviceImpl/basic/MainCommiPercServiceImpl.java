package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.MainCommiPercDao;
import com.house.home.entity.basic.MainCommiPerc;
import com.house.home.service.basic.MainCommiPercService;

@SuppressWarnings("serial")
@Service
public class MainCommiPercServiceImpl extends BaseServiceImpl implements MainCommiPercService {

	@Autowired
	private MainCommiPercDao mainCommiPercDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MainCommiPerc mainCommiPerc){
		return mainCommiPercDao.findPageBySql(page, mainCommiPerc);
	}

	@Override
	public boolean checkExistMainCommiPerc(MainCommiPerc mainCommiPerc) {
		return mainCommiPercDao.checkExistMainCommiPerc(mainCommiPerc);
	}


}
