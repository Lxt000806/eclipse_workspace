package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.BuilderDelivDao;
import com.house.home.entity.basic.BuilderDeliv;
import com.house.home.service.basic.BuilderDelivService;

@SuppressWarnings("serial")
@Service
public class BuilderDelivServiceImpl extends BaseServiceImpl implements BuilderDelivService {

	@Autowired
	private BuilderDelivDao builderDelivDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BuilderDeliv builderDeliv){
		return builderDelivDao.findPageBySql(page, builderDeliv);
	}

	@Override
	public List<Map<String, Object>> findFirstDelivCode(String builderCode) {
		return builderDelivDao.findFirstDelivCode(builderCode);
	}
}
