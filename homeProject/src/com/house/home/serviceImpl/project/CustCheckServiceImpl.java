package com.house.home.serviceImpl.project;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.CustCheckDao;
import com.house.home.entity.project.CustCheck;
import com.house.home.service.project.CustCheckService;
@SuppressWarnings("serial")
@Service
public class CustCheckServiceImpl extends BaseServiceImpl implements CustCheckService {

	@Autowired
	private CustCheckDao custCheckDao;
	
	/**
	 * 工地结算分页查找
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,CustCheck custCheck){
		return custCheckDao.findPageBySql(page, custCheck);
	}
}
