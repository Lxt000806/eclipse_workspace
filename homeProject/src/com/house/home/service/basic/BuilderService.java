package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Builder;
import com.house.home.web.controller.builderRep.builderAnalysisController;

public interface BuilderService extends BaseService {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Builder builder);
	
	public Page<Map<String, Object>> findSpcBuilderPageBySql(Page<Map<String , Object>> page,Builder builder);
	
	public Page<Map<String, Object>> findSpcBuilderAddPageBySql(Page<Map<String , Object>> page,Builder builder,String arr);

	public List<Builder> findByNoExpired(String condition);

	public Builder getByDescr(String descr);
	
	public Map<String, Object> findByCode( String code);
	
	public List<Map<String, Object>> findRegionCodeByAuthority(int type,
			String pCode,UserContext uc);
	public void infoUpdate(Builder builder);
	
	public List<Map<String, Object>> findPrjRegion(String regionCode2);

	public void regionUpdate(Builder builder);
	
	public Page<Map<String, Object>> getBuilderList(Page<Map<String, Object>> page,Builder builder);
}
