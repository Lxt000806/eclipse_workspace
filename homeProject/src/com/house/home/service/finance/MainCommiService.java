package com.house.home.service.finance;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.MainCommiPerc;
import com.house.home.entity.finance.MainCommi;

public interface MainCommiService extends BaseService {

	/**MainCommi分页信息
	 * @param page
	 * @param mainCommi
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MainCommi mainCommi);
	/**
	 * 退回
	 * @param no
	 */
	public void doSaveCountBack(String no);
	/**
	 * 查询状态
	 * @param no
	 * @return
	 */
	public String  checkStatus(String no);
	/**
	 * 计算完成
	 * @param no
	 */
	public void doSaveCount(String no);
	/**
	 * 检查是否能计算周期
	 * @param no
	 * @param beginDate
	 * @return
	 */
	public String  isExistsPeriod(String no,String beginDate);
	/**
	 * 生成提成数据
	 * 
	 * @param no
	 * @param lastUpdatedBy
	 */
	public Map<String, Object> doCount(String no, String lastUpdatedBy);
	/**
	 *非独立销售明细
	 * 
	 * @param page
	 * @param mainCommi
	 * @return
	 */
	public Page<Map<String,Object>> goFdlJqGrid(Page<Map<String,Object>> page, MainCommi mainCommi);
	/**
	 *独立销售明细
	 * 
	 * @param page
	 * @param mainCommi
	 * @return
	 */
	public Page<Map<String,Object>> goDlJqGrid(Page<Map<String,Object>> page, MainCommi mainCommi);
	/**
	 * 更新非独立销售明细
	 * 
	 * @param pk
	 * @return
	 */
	public void doUpdateFdl(Integer pk,Double managercommi,Double mainbusimancommi,
				Double declaremancommi,Double checkmancommi,Double deptfundcommi,Double totalcommi,String lastupdatedby);
	/**
	 * 更新独立销售明细
	 * 
	 * @param pk
	 * @return
	 */
	public void doUpdateDl(Integer pk,Double businessmancommi,String lastupdatedby);
	/**
	 *非独立销售报表
	 * 
	 * @param page
	 * @param mainCommi
	 * @return
	 */
	public Page<Map<String,Object>> goFdlReportJqGrid(Page<Map<String,Object>> page, MainCommi mainCommi);
	/**
	 *独立销售报表
	 * 
	 * @param page
	 * @param mainCommi
	 * @return
	 */
	public Page<Map<String,Object>> goDlReportJqGrid(Page<Map<String,Object>> page, MainCommi mainCommi);
	
	
}

