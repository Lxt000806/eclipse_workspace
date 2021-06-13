package com.house.home.serviceImpl.basic;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.home.dao.basic.MobileModelInfoDao;
import com.house.home.entity.basic.MobileModelInfo;
import com.house.home.service.basic.MobileModelInfoService;

@SuppressWarnings("serial")
@Service
public class MobileModelInfoServiceImpl extends BaseServiceImpl implements MobileModelInfoService {
	
	@Autowired
	private MobileModelInfoDao mobileModelInfoDao;
	
	@Override
	public Map<String,Object> getMobileModelInfo(String manufacturer, String model, String version){
		return this.mobileModelInfoDao.getMobileModelInfo(manufacturer, model, version);
	}
	
	@Override
	public void saveMobileModelInfo(String manufacturer, String model, String czybh){
		MobileModelInfo mobileModelInfo = new MobileModelInfo();
		mobileModelInfo.setManufacturer(manufacturer);
		mobileModelInfo.setModel(model);
		mobileModelInfo.setLastUpdate(new Date());
		mobileModelInfo.setLastUpdatedBy(czybh);
		mobileModelInfo.setActionLog("ADD");
		mobileModelInfo.setExpired("F");
		this.mobileModelInfoDao.save(mobileModelInfo);
	}
	
}
