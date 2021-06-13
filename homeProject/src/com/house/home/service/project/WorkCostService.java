package com.house.home.service.project;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.FixDuty;
import com.house.home.entity.project.WorkCost;

public interface WorkCostService extends BaseService {

	/**WorkCost分页信息
	 * @param page
	 * @param workCost
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkCost workCost);
	/**
	 * 通过员工号查员工姓名
	 * 
	 * @param page
	 * @param workCost
	 * @return
	 */
	public String findNameByEmnum(String emnum);
	/**
	 * 人工成本汇总
	 * 
	 * @param page
	 * @param workCost
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql2(Page<Map<String,Object>> page, WorkCost workCost);
	/**
	 * 按账号汇总
	 * 
	 * @param page
	 * @param workCost
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql3(Page<Map<String,Object>> page, WorkCost workCost);
	/**
	 * 保存，审核，审核取消，反审核，出纳签字
	 * 
	 * @param workCostDetail
	 * @return
	 */
	public Result doSaveProc(WorkCost workCost);
	/**
	 * 导入定责工资查询
	 * @author	created by zb
	 * @date	2019-7-19--下午3:18:44
	 * @param page
	 * @param fixDuty
	 */
	public Page<Map<String, Object>> goFixDutyJqGrid(Page<Map<String, Object>> page, FixDuty fixDuty);
	/**
	 * 是否水电发放
	 * @author	created by zb
	 * @date	2020-3-27--上午10:08:00
	 * @param custCode
	 * @return
	 */
	public String isWaterCostPay(String custCode);
}
