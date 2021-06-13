package com.house.home.serviceImpl.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.basic.AppItemTypeBatchDao;
import com.house.home.entity.basic.AppItemTypeBatch;
import com.house.home.service.basic.AppItemTypeBatchService;

@SuppressWarnings("serial")
@Service 
public class AppItemTypeBatchServiceImpl extends BaseServiceImpl implements AppItemTypeBatchService {
	@Autowired
	private  AppItemTypeBatchDao appItemTypeBatchDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, AppItemTypeBatch appItemTypeBatch) {
		return appItemTypeBatchDao.findPageBySql(page, appItemTypeBatch);
	}

	@Override
	public Result doSave(AppItemTypeBatch appItemTypeBatch) {
		return appItemTypeBatchDao.doSave(appItemTypeBatch);
	}

	@Override
	public Page<Map<String, Object>> findDetailByCode(
			Page<Map<String, Object>> page, AppItemTypeBatch appItemTypeBatch) {
		return appItemTypeBatchDao.findDetailByCode(page, appItemTypeBatch);
	}

}
