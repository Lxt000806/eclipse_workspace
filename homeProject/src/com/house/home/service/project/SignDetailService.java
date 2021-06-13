package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.SignDetailEvt;
import com.house.home.entity.design.Customer;

public interface SignDetailService extends BaseService{
	
	public Page<Map<String, Object>> getCheckConfirmList(Page<Map<String, Object>> page, Customer customer, UserContext uc);

	public Page<Map<String, Object>> getSignDetail(Page<Map<String, Object>> page, Customer customer, UserContext uc);

	public List<Map<String, Object>> getPrjProgConfirmPhoto(String no, Integer num);

	public List<Map<String, Object>> getSignInPic(String no, Integer num);

	public List<Map<String, Object>> getWorkSignInPic(String no, Integer num);

	public List<Map<String, Object>> getPicMore(Customer customer);

	public List<Map<String, Object>> getNoSignList(Page<Map<String, Object>> page, SignDetailEvt evt, Boolean iSAdminAssign);
	
	public Page<Map<String, Object>> getPrjNoSignList(Page<Map<String, Object>> page, SignDetailEvt evt);
}
