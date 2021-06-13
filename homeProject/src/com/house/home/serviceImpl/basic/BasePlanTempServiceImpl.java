package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.BasePlanTempDao;
import com.house.home.entity.basic.BasePlanTemp;
import com.house.home.service.basic.BasePlanTempService;

@SuppressWarnings("serial")
@Service
public class BasePlanTempServiceImpl extends BaseServiceImpl implements BasePlanTempService {

	@Autowired
	private BasePlanTempDao basePlanTempDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BasePlanTemp basePlanTemp){
		return basePlanTempDao.findPageBySql(page, basePlanTemp);
	}

	@Override
	public Result doSaveProc(BasePlanTemp basePlanTemp) {
		return basePlanTempDao.doSaveProc(basePlanTemp);
	}

	@Override
	public List<Map<String, Object>> checkExist(
			BasePlanTemp basePlanTemp) {
		return basePlanTempDao.checkExist(basePlanTemp);
	}

}
