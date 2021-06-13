package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.Region2Dao;
import com.house.home.entity.basic.Region2;
import com.house.home.service.basic.Region2Service;

@SuppressWarnings("serial")
@Service
public class Region2ServiceImpl extends BaseServiceImpl implements Region2Service {

	@Autowired
	private Region2Dao region2Dao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Region2 region2) {
		// TODO Auto-generated method stub
		return region2Dao.findPageBySql(page, region2);
	}

	@Override
	public boolean validDescr(String descr) {
		// TODO Auto-generated method stub
		return region2Dao.validDescr(descr);
	}
}
