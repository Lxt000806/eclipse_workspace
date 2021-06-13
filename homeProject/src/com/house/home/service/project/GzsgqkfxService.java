package com.house.home.service.project;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;

public interface GzsgqkfxService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,Customer customer,String isSign, String orderBy,String direction);

	public Page<Map<String,Object>> findHasArrPageBySql(Page<Map<String,Object>> page,String workType12,Customer customer,String isSign);

	public Page<Map<String,Object>> findBuilderRepPageBySql(Page<Map<String,Object>> page,String workType12,Customer customer,String isSign);

	public Page<Map<String,Object>> findCompletePageBySql(Page<Map<String,Object>> page,String workType12,
			Customer customer,String isSign,String level,String onTimeCmp);

	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page,Customer customer);

	public Page<Map<String,Object>> findOndoPageBySql(Page<Map<String,Object>> page,String workType12,Customer customer,String isSign);
	
	public Page<Map<String,Object>> findHasConfPageBySql(Page<Map<String,Object>> page,String workType12,Customer customer,String isSign);

	public Map<String , Object>  findPrjRegionDescr(String Code);
	
	public Page<Map<String,Object>> getWorkSignPicBySql(Page<Map<String,Object>> page, String no);

	
}
