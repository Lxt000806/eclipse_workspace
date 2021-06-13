package com.house.home.serviceImpl.project;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.project.AgainAwardDao;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.AgainAward;
import com.house.home.entity.project.AgainAwardDetail;
import com.house.home.service.project.AgainAwardService;

@SuppressWarnings("serial")
@Service
public class AgainAwardServiceImpl extends BaseServiceImpl implements AgainAwardService {
	
	@Autowired
	private AgainAwardDao againAwardDao;
	
	@Override
	public Page<Map<String, Object>> goJqGrid(Page<Map<String, Object>> page, AgainAward againAward, UserContext userContext){
		return againAwardDao.goJqGrid(page, againAward, userContext);
	}

	@Override
	public Page<Map<String, Object>> goJqGridAddDetail(Page<Map<String, Object>> page, Customer customer){
		return againAwardDao.goJqGridAddDetail(page, customer);
	}
	
	@Override
	public Result doSave(AgainAward againAward, String xml){
		return againAwardDao.doSave(againAward, xml);
	}
	
	@Override
	public Map<String, Object> getAgainAwardByNo(String no){
		return againAwardDao.getAgainAwardByNo(no);
	}
	
	@Override
	public List<Map<String, Object>> goJqGridAgainAwardDetail(Page<Map<String, Object>> page, String no){
		return againAwardDao.goJqGridAgainAwardDetail(page, no);
	}
	
    @Override
    public Page<Map<String, Object>> goJqGridDetailList(Page<Map<String, Object>> page,
            AgainAwardDetail againAwardDetail, UserContext userContext) {
        return againAwardDao.goJqGridDetailList(page, againAwardDetail, userContext);
    }

    @Override
    public List<Map<String, Object>> getStakeholders(String custCode, String bonusScheme) {
        return againAwardDao.getStakeholders(custCode, bonusScheme);
    }
}
