package com.house.home.serviceImpl.project;

import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.PrjBulletinBoardEvt;
import com.house.home.dao.project.PrjBulletinBoardDao;
import com.house.home.entity.design.Customer;

import com.house.home.service.project.PrjBulletinBoardService;

@SuppressWarnings("serial")
@Service
public class PrjBulletinBoardServiceImpl extends BaseServiceImpl implements PrjBulletinBoardService{
	
	@Autowired
	private PrjBulletinBoardDao prjBulletinBoardDao;
	
	@Override
	public Page<Map<String,Object>> getDepartmentList(Page<Map<String,Object>> page, UserContext uc) {
		return prjBulletinBoardDao.getDepartmentList(page, uc);
	}

	@Override
	public Map<String, Object> getPrjBulletinBoardCountInfo(PrjBulletinBoardEvt evt) {
		return prjBulletinBoardDao.getPrjBulletinBoardCountInfo(evt);
	}

	@Override
	public Page<Map<String, Object>> getBuildingList(
			Page<Map<String, Object>> page, PrjBulletinBoardEvt evt) {
		return prjBulletinBoardDao.getBuildingList(page, evt);
	}

	@Override
	public Page<Map<String, Object>> getPrjStageProgList(
			Page page, Date beginDate, Date endDate,String stage,Integer pageSize,String department) {
		return prjBulletinBoardDao.getPrjStageProgList(page, beginDate,endDate, stage, pageSize,department);
	}
	
	@Override
	public Page<Map<String, Object>> getConstructionList(
			Page<Map<String, Object>> page, PrjBulletinBoardEvt evt) {
		return prjBulletinBoardDao.getConstructionList(page, evt);
	}
	
	@Override
	public Page<Map<String,Object>> getPrjTaskExecAnalyList(Page<Map<String,Object>> page, PrjBulletinBoardEvt evt) {
		return prjBulletinBoardDao.getPrjTaskExecAnalyList(page, evt);
	}
	
	@Override
	public Page<Map<String, Object>> getWaitConfirmPrjProblemList(
			Page<Map<String, Object>> page, PrjBulletinBoardEvt evt) {
		return prjBulletinBoardDao.getWaitConfirmPrjProblemList(page, evt);
	}

	@Override
	public Page<Map<String, Object>> getWaitDealPrjProblemList(
			Page<Map<String, Object>> page, PrjBulletinBoardEvt evt) {
		return prjBulletinBoardDao.getWaitDealPrjProblemList(page, evt);
	}

	@Override
	public Page<Map<String, Object>> getCustProblemList(
			Page<Map<String, Object>> page, PrjBulletinBoardEvt evt) {
		return prjBulletinBoardDao.getCustProblemList(page, evt);
	}
	
	@Override
	public Page<Map<String,Object>> getProjectManSignList(Page<Map<String,Object>> page, PrjBulletinBoardEvt evt) {
		return prjBulletinBoardDao.getProjectManSignList(page, evt);
	}
	
	@Override
	public Page<Map<String,Object>> getPrjAgainList(Page page, Customer customer, String department2) {
		return prjBulletinBoardDao.getPrjAgainList(page, customer, department2);
	}

	@Override
	public Page<Map<String,Object>> getPrjTaskExecAnalyDetailList(Page<Map<String,Object>> page, String rcvCzy) {
		return prjBulletinBoardDao.getPrjTaskExecAnalyDetailList(page, rcvCzy);
	}
	
	@Override
	public Page<Map<String,Object>> getPrjProblemDetailList(
			Page<Map<String,Object>> page, PrjBulletinBoardEvt evt) {
		return prjBulletinBoardDao.getPrjProblemDetailList(page, evt);
	}

	@Override
	public Page<Map<String, Object>> getCustProblemDetailList(
			Page<Map<String, Object>> page, PrjBulletinBoardEvt evt) {
		return prjBulletinBoardDao.getCustProblemDetailList(page, evt);
	}

	@Override
	public Page<Map<String, Object>> getConfirmDetailList(
			Page<Map<String, Object>> page, String countType, String number) {
		return prjBulletinBoardDao.getConfirmDetailList(page, countType, number);
	}

	@Override
	public Page<Map<String, Object>> getCheckDetailList(
			Page<Map<String, Object>> page, String countType, String number) {
		return prjBulletinBoardDao.getCheckDetailList(page, countType, number);
	}


	@Override
	public Page<Map<String, Object>> getWorkerArrangeList(
			Page<Map<String, Object>> page, Customer customer) {

		return prjBulletinBoardDao.getWorkerArrangeList(page, customer);
	}

	@Override
	public Page<Map<String, Object>> getWorkerCompletedList(
			Page<Map<String, Object>> page, Customer customer) {

		return prjBulletinBoardDao.getWorkerCompletedList(page, customer);
	}
	
}
