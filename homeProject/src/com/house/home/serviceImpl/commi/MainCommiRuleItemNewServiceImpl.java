package com.house.home.serviceImpl.commi;

import com.house.framework.commons.orm.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.commi.MainCommiRuleItemNewDao;
import com.house.home.service.commi.MainCommiRuleItemNewService;

@SuppressWarnings("serial")
@Service 
public class MainCommiRuleItemNewServiceImpl extends BaseServiceImpl implements MainCommiRuleItemNewService {
	@Autowired
	private  MainCommiRuleItemNewDao mainCommiRuleItemNewDao;

}
