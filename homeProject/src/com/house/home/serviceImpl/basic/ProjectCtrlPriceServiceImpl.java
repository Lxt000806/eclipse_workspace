package com.house.home.serviceImpl.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.basic.ProjectCtrlPriceDao;
import com.house.home.entity.basic.ProjectCtrlPrice;
import com.house.home.service.basic.ProjectCtrlPriceService;

@SuppressWarnings("serial")
@Service 
public class ProjectCtrlPriceServiceImpl extends BaseServiceImpl implements ProjectCtrlPriceService {
	@Autowired
	private  ProjectCtrlPriceDao projectCtrlPriceDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ProjectCtrlPrice projectCtrlPrice) {
		return this.projectCtrlPriceDao.findPageBySql(page, projectCtrlPrice);
	}

}
