package com.house.home.service.finance;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.DiscToken;

public interface DiscTokenService extends BaseService {

	/**DiscToken分页信息
	 * @param page
	 * @param discToken
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, DiscToken discToken);
	
	/**
	 * 获取优惠金额合计
	 * 
	 * @param id
	 * @return
	 */
	public double getDiscTokenAmountTotal(DiscToken discToken);
	
	/**
	 * 获取明细
	 * @param discToken
	 * @return
	 */
	public Page<Map<String,Object>> findHasSelectPageBySql(Page<Map<String,Object>> page, DiscToken discToken);
	
	/**
	 * 获取优惠券号
	 * @param discToken
	 * @return
	 */
	public String getDiscTokenNo(DiscToken discToken);
	
}
