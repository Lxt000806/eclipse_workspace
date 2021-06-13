package com.house.home.serviceImpl.finance;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.MainCommiDao;
import com.house.home.entity.finance.MainCommi;
import com.house.home.service.finance.MainCommiService;

@SuppressWarnings("serial")
@Service
public class MainCommiServiceImpl extends BaseServiceImpl implements MainCommiService {

	@Autowired
	private MainCommiDao mainCommiDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MainCommi mainCommi){
		return mainCommiDao.findPageBySql(page, mainCommi);
	}
	@Override
	public void doSaveCountBack(String no) {
		mainCommiDao.doSaveCountBack(no);
	}

	@Override
	public String checkStatus(String no) {
		return mainCommiDao.checkStatus(no);
	}

	@Override
	public void doSaveCount(String no) {
		mainCommiDao.doSaveCount(no);
	}

	@Override
	public String isExistsPeriod(String no, String beginDate) {
		return mainCommiDao.isExistsPeriod(no, beginDate);
	}

	@Override
	public Map<String, Object> doCount(String no, String lastUpdatedBy) {
		return mainCommiDao.doCount(no, lastUpdatedBy);
	}
	@Override
	public Page<Map<String, Object>> goFdlJqGrid(Page<Map<String, Object>> page, MainCommi mainCommi) {
		return mainCommiDao.goFdlJqGrid(page, mainCommi);
	}
	@Override
	public Page<Map<String, Object>> goDlJqGrid(Page<Map<String, Object>> page,MainCommi mainCommi) {
		return mainCommiDao.goDlJqGrid(page, mainCommi);
	}
	@Override
	public void doUpdateFdl(Integer pk, Double managercommi,
			Double mainbusimancommi, Double declaremancommi,
			Double checkmancommi, Double deptfundcommi, Double totalcommi,String lastupdatedby) {
		mainCommiDao.doUpdateFdl(pk, managercommi, mainbusimancommi, declaremancommi, checkmancommi, deptfundcommi, totalcommi,lastupdatedby);
	}
	@Override
	public void doUpdateDl(Integer pk, Double businessmancommi,String lastupdatedby) {
		mainCommiDao.doUpdateDl(pk, businessmancommi,lastupdatedby);
	}
	@Override
	public Page<Map<String, Object>> goFdlReportJqGrid(Page<Map<String, Object>> page, MainCommi mainCommi) {
		return mainCommiDao.goFdlReportJqGrid(page, mainCommi);
	}
	@Override
	public Page<Map<String, Object>> goDlReportJqGrid(Page<Map<String, Object>> page, MainCommi mainCommi) {
		return mainCommiDao.goDlReportJqGrid(page, mainCommi);
	}
}
