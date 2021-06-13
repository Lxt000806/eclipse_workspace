package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.WaterAreaCfmDao;
import com.house.home.entity.project.WaterAreaCfm;
import com.house.home.service.project.WaterAreaCfmService;

@SuppressWarnings("serial")
@Service
public class WaterAreaCfmServiceImpl extends BaseServiceImpl implements WaterAreaCfmService {

	@Autowired
	private WaterAreaCfmDao waterAreaCfmDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WaterAreaCfm waterAreaCfm){
		return waterAreaCfmDao.findPageBySql(page, waterAreaCfm);
	}

}
