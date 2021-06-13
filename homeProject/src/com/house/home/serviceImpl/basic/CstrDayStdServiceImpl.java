package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CstrDayStdDao;
import com.house.home.entity.basic.CstrDayStd;
import com.house.home.service.basic.CstrDayStdService;

@SuppressWarnings("serial")
@Service
public class CstrDayStdServiceImpl extends BaseServiceImpl implements CstrDayStdService {

	@Autowired
	private CstrDayStdDao cstrDayStdDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CstrDayStd cstrDayStd){
		return cstrDayStdDao.findPageBySql(page, cstrDayStd);
	}

}
