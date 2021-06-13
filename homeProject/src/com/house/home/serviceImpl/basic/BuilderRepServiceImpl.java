package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.BuilderRepDao;
import com.house.home.entity.project.BuilderRep;
import com.house.home.service.basic.BuilderRepService;

@SuppressWarnings("serial")
@Service
public class BuilderRepServiceImpl extends BaseServiceImpl implements BuilderRepService {

	@Autowired
	private BuilderRepDao builderepDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, BuilderRep builderRep) {
		return builderepDao.findPageBySql(page, builderRep);
	}

	@Override
	public Result deleteForProc(BuilderRep builderRep) {
		return builderepDao.doitemsetReturnCheckOut(builderRep);
	}

	@Override
	public Page<Map<String, Object>> findPageBySqlBrbb(
			Page<Map<String, Object>> page, BuilderRep builderRep) {
		return builderepDao.findPageBySql_gdbb_brbb(page, builderRep);
	}	
	
	@Override
	public Page<Map<String, Object>> findPageBySqltgphView(
			Page<Map<String, Object>> page, BuilderRep builderRep) {
		return builderepDao.findPageBySqltgphView(page, builderRep);
	}


	
}
 
