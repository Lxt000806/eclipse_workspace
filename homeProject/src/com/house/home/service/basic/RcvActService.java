package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.RcvAct;

public interface RcvActService extends BaseService {
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, RcvAct rcvAct);
	
	public RcvAct getByCode(String code);

	public RcvAct getByDescr(String descr, String code);

}
