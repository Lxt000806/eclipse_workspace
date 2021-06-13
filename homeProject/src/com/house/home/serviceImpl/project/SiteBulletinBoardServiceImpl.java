package com.house.home.serviceImpl.project;

import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.SiteBulletinBoardDao;
import com.house.home.entity.design.Customer;

import com.house.home.service.project.SiteBulletinBoardService;

@SuppressWarnings("serial")
@Service
public class SiteBulletinBoardServiceImpl extends BaseServiceImpl implements SiteBulletinBoardService{
	
	@Autowired
	private SiteBulletinBoardDao siteBulletinBoardDao;

	@Override
	public Map<String, Object> getSiteBulletinBoardCountInfo() {
		return siteBulletinBoardDao.getSiteBulletinBoardCountInfo();
	}

	@Override
	public Page<Map<String, Object>> getBuildingList(
			Page<Map<String, Object>> page) {
		return siteBulletinBoardDao.getBuildingList(page);
	}

	@Override
	public Page<Map<String, Object>> getConstructionList(
			Page<Map<String, Object>> page, String workerClassify) {
		return siteBulletinBoardDao.getConstructionList(page, workerClassify);
	}

	@Override
	public Page<Map<String, Object>> getWaitConfirmPrjProblemList(
			Page<Map<String, Object>> page) {
		return siteBulletinBoardDao.getWaitConfirmPrjProblemList(page);
	}

	@Override
	public Page<Map<String, Object>> getWaitDealPrjProblemList(
			Page<Map<String, Object>> page) {
		return siteBulletinBoardDao.getWaitDealPrjProblemList(page);
	}

	@Override
	public Page<Map<String, Object>> getCustProblemList(
			Page<Map<String, Object>> page) {
		return siteBulletinBoardDao.getCustProblemList(page);
	}

	@Override
	public Page<Map<String, Object>> getPrjCheckOrConfirmList(
			Page<Map<String, Object>> page, String countType) {
		return siteBulletinBoardDao.getPrjCheckOrConfirmList(page, countType);
	}

	@Override
	public Page<Map<String,Object>> getPrjProblemDetailList(
			Page<Map<String,Object>> page,String prjProblemType, String department2) {
		return siteBulletinBoardDao.getPrjProblemDetailList(page, prjProblemType, department2);
	}

	@Override
	public Page<Map<String, Object>> getCustProblemDetailList(
			Page<Map<String, Object>> page, String department2) {
		return siteBulletinBoardDao.getCustProblemDetailList(page, department2);
	}

	@Override
	public Page<Map<String, Object>> getConfirmDetailList(
			Page<Map<String, Object>> page, String countType, String number) {
		return siteBulletinBoardDao.getConfirmDetailList(page, countType, number);
	}

	@Override
	public Page<Map<String, Object>> getCheckDetailList(
			Page<Map<String, Object>> page, String countType, String number) {
		return siteBulletinBoardDao.getCheckDetailList(page, countType, number);
	}

	@Override
	public Page<Map<String, Object>> getPrjStageProgList(
			Page page, Date beginDate, Date endDate,String stage,Integer pageSize) {

		return siteBulletinBoardDao.getPrjStageProgList(page, beginDate,endDate, stage, pageSize);
	}

	@Override
	public Page<Map<String, Object>> getWorkerArrangeList(
			Page<Map<String, Object>> page, Customer customer) {

		return siteBulletinBoardDao.getWorkerArrangeList(page, customer);
	}

	@Override
	public Page<Map<String, Object>> getWorkerCompletedList(
			Page<Map<String, Object>> page, Customer customer) {

		return siteBulletinBoardDao.getWorkerCompletedList(page, customer);
	}

	@Override
	public Page<Map<String, Object>> getPrjAgainList(
			Page<Map<String, Object>> page, Customer customer) {

		return siteBulletinBoardDao.getPrjAgainList(page, customer);
	}
	
	
}
