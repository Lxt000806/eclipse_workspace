package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CzyMtRegionDao;
import com.house.home.entity.basic.CzyMtRegion;
import com.house.home.service.basic.CzyMtRegionService;

@SuppressWarnings("serial")
@Service
public class CzyMtRegionServiceImpl extends BaseServiceImpl implements CzyMtRegionService {

	@Autowired
	private CzyMtRegionDao czyMtRegionDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CzyMtRegion czyMtRegion){
		return czyMtRegionDao.findPageBySql(page, czyMtRegion);
	}

	@Override
	public boolean isHasRegion(CzyMtRegion czyMtRegion) {
		// TODO Auto-generated method stub
		return czyMtRegionDao.isHasRegion(czyMtRegion);
	}

}
