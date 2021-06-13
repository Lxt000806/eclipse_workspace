package com.house.home.service.finance;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.IntPerf;

public interface IntPerfService extends BaseService {

	/**IntPerf分页信息
	 * @param page
	 * @param intPerf
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntPerf intPerf);
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
}
