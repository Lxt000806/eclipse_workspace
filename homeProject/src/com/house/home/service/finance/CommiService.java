package com.house.home.service.finance;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.Commi;

public interface CommiService extends BaseService {

	/**Commi分页信息
	 * @param page
	 * @param commi
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Commi commi);
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
	 * 主材提成客户列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findCustBySql(Page<Map<String, Object>> page, Commi commi);
	/**
	 * 主材提成材料列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findItemBySql(Page<Map<String, Object>> page, Commi commi);
	/**
	 * 生成提成数据
	 * 
	 * @param no
	 * @param lastUpdatedBy
	 */
	public Map<String, Object> doCount(String no, String lastUpdatedBy,String isRegenCommiPerc);
	/**
	 * 客户明细map
	 * 
	 * @param pk
	 */
	public Map<String, Object> findCustMap(Page<Map<String, Object>> page,String pk);
	/**
	 * 材料明细map
	 * 
	 * @param pk
	 */
	public Map<String, Object> findItemMap(Page<Map<String, Object>> page,String pk);
	/**
	 * 材料需求列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findItemReqBySql(Page<Map<String, Object>> page, Commi commi);
	/**
	 * 批量更提成类型，提成点数
	 * @param commi
	 */
	public void doUpdateBatch(Commi commi);
	
}
