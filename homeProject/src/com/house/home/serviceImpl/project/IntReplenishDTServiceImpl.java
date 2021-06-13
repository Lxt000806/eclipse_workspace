package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.IntReplenishDTDao;
import com.house.home.entity.project.IntReplenishDT;
import com.house.home.service.project.IntReplenishDTService;

@SuppressWarnings("serial")
@Service
public class IntReplenishDTServiceImpl extends BaseServiceImpl implements IntReplenishDTService {

	@Autowired
	private IntReplenishDTDao intReplenishDTDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntReplenishDT intReplenishDT){
		return intReplenishDTDao.findPageBySql(page, intReplenishDT);
	}

	@Override
	public Page<Map<String, Object>> goCodeJqGrid(Page<Map<String, Object>> page, IntReplenishDT intReplenishDT) {
		return intReplenishDTDao.goCodeJqGrid(page, intReplenishDT);
	}

	@Override
	public Page<Map<String, Object>> findNoPageBySql(
			Page<Map<String, Object>> page, IntReplenishDT intReplenishDT) {
		return intReplenishDTDao.findNoPageBySql(page, intReplenishDT);
	}

}
