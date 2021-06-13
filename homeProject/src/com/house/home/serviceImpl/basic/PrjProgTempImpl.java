package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.PrjProgTempDao;
import com.house.home.entity.project.PrjProg;
import com.house.home.entity.project.PrjProgTemp;
import com.house.home.service.project.PrjProgTempService;

@SuppressWarnings("serial")
@Service
public class PrjProgTempImpl extends BaseServiceImpl implements PrjProgTempService{
	@Autowired
	private PrjProgTempDao prjProgTempDao;

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjProgTemp prjProgTemp){
		return prjProgTempDao.findPageBySql(page, prjProgTemp);
	}
}
