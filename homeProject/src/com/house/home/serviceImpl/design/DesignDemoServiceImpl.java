package com.house.home.serviceImpl.design;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;

import org.aspectj.weaver.patterns.HasMemberTypePattern;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.house.home.client.service.evt.GetShowBuildsEvt;
import com.house.home.dao.design.DesignDemoDao;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.design.DesignDemo;
import com.house.home.service.design.DesignDemoService;

@SuppressWarnings("serial")
@Service 
public class DesignDemoServiceImpl extends BaseServiceImpl implements DesignDemoService {
	@Autowired
	private  DesignDemoDao designDemoDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, DesignDemo designDemo,UserContext uc) {

		return designDemoDao.findPageBySql(page, designDemo,uc);
	}

	@Override
	public Page<Map<String, Object>> findDesignPic(
			Page<Map<String, Object>> page, DesignDemo designDemo,
			UserContext uc) {
	
		return designDemoDao.findDesignPic(page, designDemo,uc);
	}

	@Override
	public void doDeleteDemo(String no, String custCode, String photoName) {

		designDemoDao.doDeleteDemo(no,custCode,photoName);
	}
	
	@Override
	public void doDeleteAllDemo(String no) {

		designDemoDao.doDeleteAllDemo(no);
	}

	@Override
	public Map<String, Object> getQty(String builderCode) {
		Map<String, Object> map =new HashMap();
		map = designDemoDao.getQty(builderCode);
		map.put("designDemoNum",designDemoDao.getDesignDemoQty(builderCode));
		
		return map;
	}
	
	public Page<Map<String, Object>> getById(Page<Map<String, Object>> page,String id) {
		return designDemoDao.getById(page,id);
	}

	@Override
	public Page<Map<String, Object>> getShowBuilds(Page<Map<String, Object>> page, GetShowBuildsEvt evt){
		return designDemoDao.getShowBuilds(page, evt);
	}
	
	

}
