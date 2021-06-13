package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.BaseCheckItemPlan;

public interface BaseCheckItemPlanService extends BaseService {

	/**BaseCheckItemPlan分页信息
	 * @param page
	 * @param baseCheckItemPlan
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseCheckItemPlan baseCheckItemPlan);
	/**
	 * 项目结算界面表头信息
	 * 
	 * @param code
	 * @return
	 */
	public List<Map<String, Object>> findHeadInfoBySql(String code);
	/**
	 * 表身数据
	 * 
	 * @param page
	 * @param baseCheckItemPlan
	 * @return
	 */
	public Page<Map<String, Object>> findBodyInfoBySql(Page<Map<String, Object>> page, BaseCheckItemPlan baseCheckItemPlan);
	/**
	 * 从客户报价导入
	 * 
	 * @param baseCheckItemPlan
	 * @return
	 */
	public List<Map<String, Object>> importFromCust(BaseCheckItemPlan baseCheckItemPlan);
	/**
	 * 保存
	 * 
	 * @param baseCheckItemPlan
	 * @return
	 */
	public Result doSaveProc(BaseCheckItemPlan baseCheckItemPlan);
	/**
	 * 独立销售客户类型
	 * 
	 * @return
	 */
	public List<Map<String, Object>> addAllInfoCustType();
}
