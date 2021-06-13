package com.house.home.serviceImpl.project;

import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.IntegrateBulletinBoardEvt;
import com.house.home.dao.project.IntegrateBulletinBoardDao;
import com.house.home.entity.design.Customer;

import com.house.home.service.project.IntegrateBulletinBoardService;

@SuppressWarnings("serial")
@Service
public class IntegrateBulletinBoardServiceImpl extends BaseServiceImpl implements IntegrateBulletinBoardService{
	
	@Autowired
	private IntegrateBulletinBoardDao integrateBulletinBoardDao;
	
	@Override
	public Page<Map<String,Object>> getDepartmentList(Page<Map<String,Object>> page, UserContext uc) {
		return integrateBulletinBoardDao.getDepartmentList(page, uc);
	}

	@Override
	public Map<String, Object> getIntegrateBulletinBoardCountInfo(IntegrateBulletinBoardEvt evt) {
		return integrateBulletinBoardDao.getIntegrateBulletinBoardCountInfo(evt);
	}

	@Override
	public Page<Map<String, Object>> getDesigningList(Page<Map<String, Object>> page, IntegrateBulletinBoardEvt evt) {
		return integrateBulletinBoardDao.getDesigningList(page, evt);
	}

	@Override
	public Page<Map<String, Object>> getDesignedList(Page<Map<String, Object>>  page, IntegrateBulletinBoardEvt evt) {
		return integrateBulletinBoardDao.getDesignedList(page, evt);
	}
	
	@Override
	public Page<Map<String, Object>> getProductionList(Page<Map<String, Object>> page, IntegrateBulletinBoardEvt evt) {
		return integrateBulletinBoardDao.getProductionList(page, evt);
	}
	
	@Override
	public Page<Map<String, Object>> getShippedList(Page<Map<String, Object>> page, IntegrateBulletinBoardEvt evt) {
		return integrateBulletinBoardDao.getShippedList(page, evt);
	}
	
	@Override
	public Page<Map<String, Object>> getInstallingList(
			Page<Map<String, Object>> page, IntegrateBulletinBoardEvt evt) {
		return integrateBulletinBoardDao.getInstallingList(page, evt);
	}

	@Override
	public Page<Map<String, Object>> getInstalledList(
			Page<Map<String, Object>> page, IntegrateBulletinBoardEvt evt) {
		return integrateBulletinBoardDao.getInstalledList(page, evt);
	}

	@Override
	public Page<Map<String, Object>> getSalingList(
			Page<Map<String, Object>> page, IntegrateBulletinBoardEvt evt) {
		return integrateBulletinBoardDao.getSalingList(page, evt);
	}
	
	@Override
	public Page<Map<String,Object>> getSignOrConfirmList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		return integrateBulletinBoardDao.getSignOrConfirmList(page, evt);
	}
	
	@Override
	public Page<Map<String,Object>> getProductionDetailList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		return integrateBulletinBoardDao.getProductionDetailList(page, evt);
	}
	
	@Override
	public Page<Map<String,Object>> getInstallingDetailList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		return integrateBulletinBoardDao.getInstallingDetailList(page, evt);
	}
	
	@Override
	public Page<Map<String,Object>> getSignOrConfirmDetailList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		return integrateBulletinBoardDao.getSignOrConfirmDetailList(page, evt);
	}
	
	@Override
	public Page<Map<String,Object>> getWorkSignList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		return integrateBulletinBoardDao.getWorkSignList(page, evt);
	}
	
	@Override
	public Page<Map<String,Object>> getWorkSignDetailList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		return integrateBulletinBoardDao.getWorkSignDetailList(page, evt);
	}
	
}
