package com.house.home.service.commi;

import java.util.Date;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiCycle;
import com.house.home.entity.commi.ItemCommiCycle;

public interface ItemCommiCycleService extends BaseService {

	/**ItemCommiCycle分页信息
	 * @param page
	 * @param itemCommiCycle
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemCommiCycle itemCommiCycle);
	/**
	 * 查询计算状态
	 * @param no
	 * @return
	 */
	public String checkStatus(String no);
	/**
	 * 检查周期
	 * @param no
	 * @param beginDate
	 * @return
	 */
	public String isExistsPeriod(String no,String beginDate);
	/**
	 * 计算完成
	 * @param no
	 */
	public void doComplete(String no);
	/**
	 * 退回
	 * @param no
	 */
	public void doReturn(String no);
	/**
	 * 生成提成数据
	 * 
	 * @param itemCommiCycle
	 */
	public Map<String, Object> doCount(ItemCommiCycle itemCommiCycle);
	/**
	 * 获取独立销售周期
	 * 
	 * @param mon
	 * @return
	 */
	public String getItemCommiNoByMon(Integer mon);
}
