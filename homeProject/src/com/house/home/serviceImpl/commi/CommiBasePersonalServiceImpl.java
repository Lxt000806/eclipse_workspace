package com.house.home.serviceImpl.commi;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.commi.CommiBasePersonalDao;
import com.house.home.entity.commi.CommiBasePersonal;
import com.house.home.service.commi.CommiBasePersonalService;

@SuppressWarnings("serial")
@Service
public class CommiBasePersonalServiceImpl extends BaseServiceImpl implements CommiBasePersonalService {

	@Autowired
	private CommiBasePersonalDao commiBasePersonalDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiBasePersonal commiBasePersonal){
		return commiBasePersonalDao.findPageBySql(page, commiBasePersonal);
	}

	@Override
	public boolean checkCommiBasePersonalExist(
			CommiBasePersonal commiBasePersonal) {
		return commiBasePersonalDao.checkCommiBasePersonalExist(commiBasePersonal);
	}

}
