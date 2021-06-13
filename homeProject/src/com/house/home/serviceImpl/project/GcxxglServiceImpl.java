package com.house.home.serviceImpl.project;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.project.GcxxglDao;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.Purchase;
import com.house.home.entity.project.Gcxxgl;
import com.house.home.service.project.GcxxglService;

@SuppressWarnings("serial")
@Service
public class GcxxglServiceImpl extends BaseServiceImpl implements GcxxglService{
	@Autowired
	private GcxxglDao gcxxglDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer,UserContext uc){
		return gcxxglDao.findPageBySql(page, customer,uc);
	}
	
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, Customer customer ,UserContext uc){
		return gcxxglDao.findDetailPageBySql(page, customer,uc);
	}
	
	public Page<Map<String,Object>> findDelayDetailPageBySql(Page<Map<String,Object>> page, Customer customer){
		return gcxxglDao.findDelayDetailPageBySql(page, customer);
	}
	
	public Page<Map<String,Object>> findTotalDelayPageBySql(Page<Map<String,Object>> page, Customer customer){
		return gcxxglDao.findTotalDelayPageBySql(page, customer);
	}
	
	@Override
	public void doChengeCheckMan(String custCode,String chengeCheckMan,Date lastUpdate,String lastUpdatedBy){
		 gcxxglDao.doChengeCheckMan(custCode,chengeCheckMan,lastUpdate,lastUpdatedBy);
	}
	
	@Override
	public void updatePreloftsMan(String code,String preloftsMan,String preloftsManDescr,String lastUpdatedBy,String oldPreloftsMan ){
		gcxxglDao.updatePreloftsMan(code,preloftsMan,preloftsManDescr,lastUpdatedBy,oldPreloftsMan);
	}

	@Override
	public void updateCustomer(String custCode,String projectMan,String oldProjectMan,String lastUpdatedBy) {
		gcxxglDao.updateCustomer(custCode,projectMan,oldProjectMan,lastUpdatedBy);		
	}
	
	@Override
	public String getConstDay(){
		
		return gcxxglDao.getConstDay();
	}

	@Override
	public Integer getCustStakeholderPk(String custCode,String role) {
		// TODO Auto-generated method stub
		return gcxxglDao.getCustStakeholderPk(custCode,role);
	}
	
	
	
	
}
