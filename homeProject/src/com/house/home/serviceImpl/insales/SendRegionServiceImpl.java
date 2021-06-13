package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.SendRegionDao;
import com.house.home.entity.insales.SendRegion;
import com.house.home.service.insales.SendRegionService;

@SuppressWarnings("serial")
@Service
public class SendRegionServiceImpl extends BaseServiceImpl implements SendRegionService {

	@Autowired
	private SendRegionDao sendRegionDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SendRegion sendRegion){
		return sendRegionDao.findPageBySql(page, sendRegion);
	}

	@Override
	public Page<Map<String, Object>> findSendRegionPageBySql(
			Page<Map<String, Object>> page, SendRegion sendRegion) {
		return sendRegionDao.findSendRegionPageBySql(page, sendRegion);
	}

	@Override
	public boolean hasDescr(String tableName, SendRegion sendRegion) {
		return sendRegionDao.hasDescr(tableName, sendRegion);
	}

}
