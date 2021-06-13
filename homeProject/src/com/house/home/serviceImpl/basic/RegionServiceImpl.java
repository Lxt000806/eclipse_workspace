package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.RegionDao;
import com.house.home.entity.basic.Region;
import com.house.home.service.basic.RegionService;

@SuppressWarnings("serial")
@Service
public class RegionServiceImpl extends BaseServiceImpl implements RegionService {

	@Autowired
	private RegionDao regionDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Region region) {
		// TODO Auto-generated method stub
		return regionDao.findPageBySql(page, region);
	}

	@Override
	public boolean validCode(String code) {
		// TODO Auto-generated method stub
		return regionDao.validCode(code);
	}

	@Override
	public boolean validDescr(String descr) {
		// TODO Auto-generated method stub
		return regionDao.validDescr(descr);
	}
}
