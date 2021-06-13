package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.IntMeasureBrandDao;
import com.house.home.entity.basic.IntMeasureBrand;
import com.house.home.service.basic.IntMeasureBrandService;

@SuppressWarnings("serial")
@Service
public class IntMeasureBrandServiceImpl extends BaseServiceImpl implements IntMeasureBrandService {

	@Autowired
	private IntMeasureBrandDao intMeasureBrandDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntMeasureBrand intMeasureBrand){
		return intMeasureBrandDao.findPageBySql(page, intMeasureBrand);
	}

	@Override
	public List<Map<String, Object>> getByType(String type) {
		return intMeasureBrandDao.getByType(type);
	}

}
