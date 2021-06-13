package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.SignDetailEvt;
import com.house.home.dao.project.SignDetailDao;
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Service
public class SignDetailServiceImpl extends BaseServiceImpl implements SignDetailService{

	@Autowired
	private SignDetailDao signDetailDao;

	@Override
	public Page<Map<String, Object>> getCheckConfirmList(Page<Map<String, Object>> page,
			 Customer customer, UserContext uc) {
		
		return signDetailDao.getCheckConfirmList(page, customer, uc);
	}

	@Override
	public Page<Map<String, Object>> getSignDetail(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {

		return signDetailDao.getSignDetail(page, customer, uc);
	
	}

	@Override
	public List<Map<String, Object>> getPrjProgConfirmPhoto(String no,
			Integer num) {

		return signDetailDao.getPrjProgConfirmPhoto(no, num);
	}

	@Override
	public List<Map<String, Object>> getSignInPic(String no, Integer num) {

		return signDetailDao.getSignInPic(no, num);
	}

	@Override
	public List<Map<String, Object>> getWorkSignInPic(String no, Integer num) {
	
		return signDetailDao.getWorkSignInPic(no, num);
	}

	@Override
	public List<Map<String, Object>> getPicMore(Customer customer) {

		return signDetailDao.getPicMore(customer);
	}

	@Override
	public List<Map<String, Object>> getNoSignList(
			Page<Map<String, Object>> page, SignDetailEvt evt,
			Boolean iSAdminAssign) {
		return signDetailDao.getNoSignList(page, evt, iSAdminAssign);
	}

	@Override
	public Page<Map<String, Object>> getPrjNoSignList(
			Page<Map<String, Object>> page, SignDetailEvt evt) {
		return signDetailDao.getPrjNoSignList(page, evt);
	}
	
	
	
	
}
