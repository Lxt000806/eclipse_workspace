package com.house.home.service.project;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.CustProblemEvt;
import com.house.home.entity.project.CustProblem;

public interface CustProblemService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustProblem custProblem, UserContext uc);

	public void dealCustCompaint(String no,int pk,String dealDate,String dealRemarks);
	
	public void doRcv(String planDealDate,Integer pk,Date rcvDate,String dealRemarks);
	
	public void doUpdate(CustProblem custProblem);
	
	public Page<Map<String, Object>> findAllBySql(
			Page<Map<String, Object>> page, CustProblem custProblem) ;
	
	public List<Map<String,Object>> findPromType1(int type,String pCode);

	public List<Map<String,Object>> findPromRsn(int type,String pCode);

	public void doToCustService(CustProblemEvt evt, UserContext uc);
}
