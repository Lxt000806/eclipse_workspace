package com.house.home.service.query;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Doc;

public interface DocQuerySerivce extends BaseService{

	public Page<Map<String, Object>>  findPageBySql(Page<Map<String,Object>> page, Doc doc);
	
	public List<Map<String, Object>> getAuthNode(String czybm, boolean hasAuth);
}
