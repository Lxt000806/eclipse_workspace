package com.house.home.serviceImpl.project;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.project.CustVisitDao;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustProblem;
import com.house.home.entity.project.CustVisit;
import com.house.home.service.project.CustVisitService;

@SuppressWarnings("serial")
@Service 
public class CustVisitServiceImpl extends BaseServiceImpl implements CustVisitService {
	@Autowired
	private  CustVisitDao custVisitDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
			CustVisit custVisit) {
		return custVisitDao.findPageBySql(page, custVisit);
	}

	@Override
	public Page<Map<String, Object>> findSearchCust1BySql(
			Page<Map<String, Object>> page, Customer customer) {
		return custVisitDao.findSearchCust1BySql(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findSearchCust2BySql(
			Page<Map<String, Object>> page, Customer customer) {
		return custVisitDao.findSearchCust2BySql(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findSearchCust3BySql(
			Page<Map<String, Object>> page, Customer customer) {
		return custVisitDao.findSearchCust3BySql(page, customer);
	}

	@Override
	public Result doSave(CustVisit custVisit) {
		return custVisitDao.doSave(custVisit);
	}

	@Override
	public Page<Map<String, Object>> findPrjItemByCode(
			Page<Map<String, Object>> page, String code) {
		return custVisitDao.findPrjItemByCode(page, code);
	}

	@Override
	public Page<Map<String, Object>> findCustProblemByNo(
			Page<Map<String, Object>> page, String no) {
		return custVisitDao.findCustProblemByNo(page, no);
	}

	@Override
	public Result doUpdate(CustVisit custVisit) {
		return custVisitDao.doUpdate(custVisit);
	}

	@Override
	public Page<Map<String, Object>> findDetailListPageBySql(
			Page<Map<String, Object>> page, CustVisit custVisit, CustProblem custProblem) {
		return custVisitDao.findDetailListPageBySql(page, custVisit, custProblem);
	}

}
