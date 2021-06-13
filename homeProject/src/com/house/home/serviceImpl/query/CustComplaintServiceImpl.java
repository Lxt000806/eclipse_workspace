package com.house.home.serviceImpl.query;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.GetCustComplaintListEvt;
import com.house.home.dao.query.CustComplaintDao;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.CustComplaint;
import com.house.home.service.query.CustComplaintService;

@Service
@SuppressWarnings("serial")
public class CustComplaintServiceImpl extends BaseServiceImpl implements CustComplaintService{

	@Autowired
	private CustComplaintDao custComplaintDao;
	
	
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CustComplaint custComplaint) {
		// TODO Auto-generated method stub
		return custComplaintDao.findPageBySql(page, custComplaint);
	}

	@Override
	public Page<Map<String, Object>> getCustComplaintList(Page<Map<String, Object>> page, GetCustComplaintListEvt evt, UserContext uc){
		return custComplaintDao.getCustComplaintList(page, evt, uc);
	}
	
	@Override
	public Map<String, Object> getCustComplaintDetail(String no){
		return custComplaintDao.getCustComplaintDetail(no);
	}
	
	@Override
	public List<Map<String, Object>>  getCustComplaint(String no){
		return custComplaintDao.getCustComplaint(no);
	}
	
	@Override
	public List<Map<String, Object>>  getCustEval(String custCode){
		return custComplaintDao.getCustEval(custCode);
	}
	
	@Override
	public List<Map<String, Object>>  getCustAddress(String identity,String phone){
		return custComplaintDao.getCustAddress(identity,phone);
	}
	
	@Override
	public List<Map<String, Object>> getCustComplaintDetailList(String no){
		return custComplaintDao.getCustComplaintDetailList(no);
	}

	@Override
	public Map<String, Object> getCustInfo(String custCode) {
		// TODO Auto-generated method stub
		return custComplaintDao.getCustInfo(custCode);
	}

	@Override
	public Result doSave(CustComplaint custComplaint) {
		// TODO Auto-generated method stub
		return custComplaintDao.doSave(custComplaint);
	}
	
	@Override
	public Result doUpdate(CustComplaint custComplaint) {
		//更新客户表电话
		Customer customer = custComplaintDao.get(Customer.class, custComplaint.getCustCode());
		customer.setMobile1(custComplaint.getMobile1());
		customer.setMobile2(custComplaint.getMobile2());
		custComplaintDao.update(customer);
		
		return custComplaintDao.doSave(custComplaint);
	}

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, CustComplaint custComplaint) {
		// TODO Auto-generated method stub
		return custComplaintDao.findDetailPageBySql(page,custComplaint);
	}
	
	@Override
	public Page<Map<String, Object>> findComplaintDetailPageBySql(
			Page<Map<String, Object>> page, CustComplaint custComplaint) {
		// TODO Auto-generated method stub
		return custComplaintDao.findComplaintDetailPageBySql(page,custComplaint);
	}
	
	@Override
	public Page<Map<String, Object>> getCustComplaintList_forCust(Page<Map<String, Object>> page, GetCustComplaintListEvt evt){
		return custComplaintDao.getCustComplaintList_forCust(page, evt);
	}
	
}
