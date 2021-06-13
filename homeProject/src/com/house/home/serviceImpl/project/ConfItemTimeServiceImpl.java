package com.house.home.serviceImpl.project;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.ConfItemTimeDao;
import com.house.home.entity.project.ConfItemTime;
import com.house.home.service.project.ConfItemTimeService;

@SuppressWarnings("serial")
@Service
public class ConfItemTimeServiceImpl extends BaseServiceImpl implements ConfItemTimeService {

	@Autowired
	private ConfItemTimeDao confItemTimeDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ConfItemTime confItemTime){
		return confItemTimeDao.findPageBySql(page, confItemTime);
	}

	@Override
	public List<Map<String,Object>> findConfItemCodeListBySql(String custCode) {
		// TODO Auto-generated method stub
		return confItemTimeDao.findConfItemCodeListBySql(custCode);
	}

}
