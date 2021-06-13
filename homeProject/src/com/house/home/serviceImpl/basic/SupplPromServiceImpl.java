package com.house.home.serviceImpl.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.basic.SupplPromDao;
import com.house.home.entity.basic.SupplProm;
import com.house.home.service.basic.SupplPromService;

@SuppressWarnings("serial")
@Service 
public class SupplPromServiceImpl extends BaseServiceImpl implements SupplPromService {
	@Autowired
	private  SupplPromDao supplPromDao;

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, SupplProm supplProm) {
		return supplPromDao.findDetailPageBySql(page,supplProm);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SupplProm supplProm) {
		return supplPromDao.findPageBySql(page, supplProm);
	}

	@Override
	public Page<Map<String, Object>> findItemPageBySql(
			Page<Map<String, Object>> page, SupplProm supplProm) {
		return supplPromDao.findItemPageBySql(page, supplProm);
	}

	@Override
	public Result doSave(SupplProm supplProm) {
		return supplPromDao.doSave(supplProm);
	}

}
