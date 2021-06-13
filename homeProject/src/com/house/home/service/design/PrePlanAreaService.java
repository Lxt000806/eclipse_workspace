package com.house.home.service.design;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ItemType3;
import com.house.home.entity.design.PrePlan;
import com.house.home.entity.design.PrePlanArea;

public interface PrePlanAreaService extends BaseService{
	
	public String getNoByCustCode(String custCode);

	public Page<Map<String,Object>> findPlanAreaJqgridBySql(Page<Map<String,Object>> page, PrePlan prePlan);

	public Page<Map<String,Object>> findDoorWindJqgridBySql(Page<Map<String,Object>> page, Integer pk);
	
	public Result doSave(PrePlanArea prePlanArea);
	
	public Result doUpdate(PrePlanArea prePlanArea);

	public Result doDelPrePlanArea(PrePlanArea prePlanArea);

	public Result doUpward(PrePlanArea prePlanArea);

	public Result doDownward(PrePlanArea prePlanArea);
	
	public Result doAutoAddDefaultArea(PrePlanArea prePlanArea);
	
	public String getDWMaxPk(String type,Integer areaPk);
	
	public boolean getCanUpdateArea(String custCode);

}
