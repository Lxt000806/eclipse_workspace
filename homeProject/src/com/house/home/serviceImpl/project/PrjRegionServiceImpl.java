package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.PrjRegionDao;
import com.house.home.entity.project.PrjRegion;
import com.house.home.service.project.PrjRegionService;

@SuppressWarnings("serial")
@Service
public class PrjRegionServiceImpl extends BaseServiceImpl implements PrjRegionService {

	@Autowired
	private PrjRegionDao prjRegionDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjRegion prjRegion){
		return prjRegionDao.findPageBySql(page, prjRegion);
	}

	@Override
	public void doUpdate(PrjRegion prjRegion) {
		prjRegionDao.doUpdate(prjRegion);
	}

	@Override
	public boolean checkExsist(PrjRegion prjRegion) {
		return prjRegionDao.checkExsist(prjRegion);
	}

}
