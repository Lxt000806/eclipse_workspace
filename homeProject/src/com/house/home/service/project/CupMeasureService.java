package com.house.home.service.project;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.CupMeasure;

public interface CupMeasureService extends BaseService {

	/**CupMeasure分页信息
	 * @param page
	 * @param cupMeasure
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CupMeasure cupMeasure);
	/**
	 * 确认
	 * 
	 * @param page
	 * @param fixDuty
	 * @return
	 */
	public String confirm(CupMeasure cupMeasure);
	
	public Double getCupDownHigh(String custCode);
	
	public Double getCupUpHigh(String custCode);
	
	public Double getBathDownHigh(String custCode);
	
	public Double getBathUpHigh(String custCode);
}
