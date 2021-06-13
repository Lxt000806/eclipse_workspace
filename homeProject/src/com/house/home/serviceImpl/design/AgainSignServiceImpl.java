package com.house.home.serviceImpl.design;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.design.AgainSignDao;
import com.house.home.entity.design.AgainSign;
import com.house.home.service.design.AgainSignService;

@SuppressWarnings("serial")
@Service
public class AgainSignServiceImpl extends BaseServiceImpl implements AgainSignService {

	@Autowired
	private AgainSignDao againSignDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, AgainSign againSign){
		return againSignDao.findPageBySql(page, againSign);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_khxx(
			Page<Map<String, Object>> page, AgainSign againSign) {
		return againSignDao.findPageBySql_khxx(page,againSign);
	}

}
