package com.house.home.service.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Hdmpgl;
import com.house.home.entity.design.Customer;

public interface QdhbfxService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,Customer customer,String role);

	
}
