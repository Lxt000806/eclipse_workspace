package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.project.ConFeeChg;

public interface ConFeeChgService extends BaseService {

	/**ConFeeChg分页信息
	 * @param page
	 * @param conFeeChg
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ConFeeChg conFeeChg,UserContext uc);
	
	/**根据客户编号查询ConFeeChg分页信息
	 * @param page
	 * @param conFeeChg
	 * @return
	 */
	public Page<Map<String,Object>> findPageByCustCode(Page<Map<String,Object>> page, String custCode);
	/**
	 * 检查是否能够计算业绩
	 * @param conFeeChg
	 * @return
	 */
	public List<Map<String,Object>> checkPerformance(ConFeeChg conFeeChg);
	/**
	 * 检查是否能保存计算业绩
	 * @param conFeeChg
	 * @return
	 */
	public List<Map<String,Object>> checkSavePerformance(ConFeeChg conFeeChg);
	/**
	 * 保存计算业绩
	 * @param conFeeChg
	 * @return
	 */
	public void doPerformance(ConFeeChg conFeeChg);
	/**
	 * 保存，审核，审核取消，反审核
	 * 
	 * @param conFeeChgDetail
	 * @return
	 */
	public Result doSaveProc(ConFeeChg conFeeChg);
}
