package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Company;

public interface CompanyService extends BaseService {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Company company);
	
	public Company getByDesc2(String desc2);
	/**
	 * 打卡地点
	 * @author	created by zb
	 * @date	2019-5-10--下午5:54:21
	 * @param page
	 * @param company
	 * @return
	 */
	public Page<Map<String,Object>> findSignPlacePageBySql(Page<Map<String, Object>> page,
			Company company);
	/**
	 * 根据所在地点按顺序获取公司列表
	 * @author	created by zb
	 * @date	2019-5-21--下午3:05:38
	 * @param page
	 * @param company
	 * @return
	 */
	public Page<Map<String,Object>> findCmpListOrderDistanceBySql(Page<Map<String, Object>> page, Company company);
	/**
	 * 根据所在地点按顺序获取打卡地点列表
	 * @author	created by zb
	 * @date	2019-5-21--下午3:14:17
	 * @param page
	 * @param company
	 * @return
	 */
	public Page<Map<String,Object>> findSignPlaceOrderDistancePageBySql(Page<Map<String,Object>> page, Company company);
	
}
