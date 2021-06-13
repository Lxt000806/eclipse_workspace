package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.BaseItemTempDao;
import com.house.home.entity.insales.BaseItemTemp;
import com.house.home.service.insales.BaseItemTempService;

@SuppressWarnings("serial")
@Service
public class BaseItemTempServiceImpl extends BaseServiceImpl implements BaseItemTempService {

	@Autowired
	private BaseItemTempDao baseItemTempDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemTemp baseItemTemp){
		return baseItemTempDao.findPageBySql(page, baseItemTemp);
	}

	@Override
	public Page<Map<String, Object>> findListPageBySql(
			Page<Map<String, Object>> page, BaseItemTemp baseItemTemp) {
		return baseItemTempDao.findListPageBySql(page, baseItemTemp);
	}

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, BaseItemTemp baseItemTemp) {
		return baseItemTempDao.findDetailPageBySql(page,baseItemTemp);
	}

	@Override
	public Result doSave(BaseItemTemp baseItemTemp) {
		return baseItemTempDao.doSave(baseItemTemp);
	}

}
