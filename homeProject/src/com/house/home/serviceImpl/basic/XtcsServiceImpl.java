package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.XtcsDao;
import com.house.home.entity.basic.Xtcs;
import com.house.home.service.basic.XtcsService;

@SuppressWarnings("serial")
@Service
public class XtcsServiceImpl extends BaseServiceImpl implements XtcsService {

	@Autowired
	private XtcsDao xtcsDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Xtcs xtcs){
		return xtcsDao.findPageBySql(page, xtcs);
	}

	@Override
	public String getQzById(String id) {
		return xtcsDao.getQzById(id);
	}

}
