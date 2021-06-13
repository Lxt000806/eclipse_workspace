package com.house.home.service.design;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.EmpWorkPlan;

public interface EmpWorkPlanService extends BaseService {

	/**EmpWorkPlan分页信息
	 * @param page
	 * @param empWorkPlan
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, EmpWorkPlan empWorkPlan);
	/**
	 * 是否存在本周计划
	 * @author cjg
	 * @date 2020-1-3
	 * @param planCzy
	 * @return
	 */
	public String isExistsThisPlan(EmpWorkPlan empWorkPlan);
	/**
	 * 是否存在下周计划
	 * @author cjg
	 * @date 2020-1-3
	 * @param planCzy
	 * @return
	 */
	public String isExistsNextPlan(EmpWorkPlan empWorkPlan);
	/**
	 * 保存
	 * @param empWorkPlan
	 * @return
	 */
	public Result doSaveProc(EmpWorkPlan empWorkPlan);
	/**
	 * EmpWorkPlanDetail分页信息
	 * 
	 * @param page
	 * @param empWorkPlan
	 * @return
	 */
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, EmpWorkPlan empWorkPlan);
	/**
	 * EmpWorkPlanDetail分页信息
	 * 
	 * @param page
	 * @param empWorkPlan
	 * @return
	 */
	public Page<Map<String,Object>> findViewPageBySql(Page<Map<String,Object>> page, EmpWorkPlan empWorkPlan);
}
