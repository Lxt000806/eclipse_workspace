package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.MtRegionDao;
import com.house.home.entity.basic.MtRegion;
import com.house.home.service.basic.MtRegionService;

@SuppressWarnings("serial")
@Service
public class MtRegionServiceImpl extends BaseServiceImpl implements MtRegionService {

	@Autowired
	private MtRegionDao mtRegionDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MtRegion mtRegion){
		return mtRegionDao.findPageBySql(page, mtRegion);
	}

}
