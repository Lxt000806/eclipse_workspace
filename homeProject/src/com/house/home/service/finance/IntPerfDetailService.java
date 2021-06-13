package com.house.home.service.finance;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.IntPerfDetail;

public interface IntPerfDetailService extends BaseService {

	/**IntPerfDetail分页信息
	 * @param page
	 * @param intPerfDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntPerfDetail intPerfDetail);
	/**
	 * IntPerfDetail  Map
	 * @param pk
	 * @return
	 */
	public Map<String, Object> findMapBySql(String pk);
	/**
	 * 修改
	 * @param intPerfDetail
	 * @return
	 */
	public void doUpdate(IntPerfDetail intPerfDetail);
	/**
	 * 生成业绩数据
	 * @param no
	 * @param lastUpdatedBy
	 */
	public void doGetIntPerDetail(String no,String lastUpdatedBy,String prjPerfNo);
	/**
	 * IntPerfDetail报表
	 * 
	 * @param page
	 * @param intPerfDetail
	 * @return
	 */
	public Page<Map<String, Object>> findReportPageBySql(Page<Map<String, Object>> page, IntPerfDetail intPerfDetail);
	/**
	 * 查询优惠金额
	 * 
	 * @param intPerfDetail
	 * @return
	 */
	public Map<String, Object> findDisc(IntPerfDetail intPerfDetail);
}
