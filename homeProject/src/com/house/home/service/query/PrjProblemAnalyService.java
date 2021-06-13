package com.house.home.service.query;

import java.util.Date;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.PrjProblem;

public interface PrjProblemAnalyService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjProblem prjProblem);

	public Page<Map<String,Object>> findPageBySqlWaitDeal(Page<Map<String,Object>> page, PrjProblem prjProblem);
	
	public Page<Map<String,Object>> findPageBySqlView(Page<Map<String,Object>> page, PrjProblem prjProblem);

}
