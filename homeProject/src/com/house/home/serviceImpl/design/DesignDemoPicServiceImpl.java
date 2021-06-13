package com.house.home.serviceImpl.design;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.design.DesignDemoPicDao;
import com.house.home.service.design.DesignDemoPicService;

@SuppressWarnings("serial")
@Service 
public class DesignDemoPicServiceImpl extends BaseServiceImpl implements DesignDemoPicService {
	@Autowired
	private  DesignDemoPicDao designDemoPicDao;

	@Override
	public List<Map<String, Object>> findNoPushYunPics() {
		// TODO Auto-generated method stub
		return designDemoPicDao.findNoPushYunPics();
	}

	@Override
	public void updateDesignDemoPicStatus() {
		// TODO Auto-generated method stub
		designDemoPicDao.updateDesignDemoPicStatus();
	}

	
	
	
}
