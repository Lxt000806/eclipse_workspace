package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.PrjJob;

public interface SendNoticeService extends BaseService {
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, Customer customer);
	
	public Page<Map<String,Object>> findPageBySql_detail(Page<Map<String, Object>> page, Customer customer);
	
	public Map<String, Object> getItemAppInfo(String iaNo);
	
	public Page<Map<String, Object>> goItemAppJqGrid(Page<Map<String, Object>> page, Customer customer);
	
	public Result doSendNotice(PrjJob prjJob);
	
	public List<Map<String, Object>> findPrjTypeByItemType1Auth(int type,String pCode,UserContext uc);
	
}
