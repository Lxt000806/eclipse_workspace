package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.WorkType1;

public interface WorkType1Service extends BaseService {

	/**WorkType1分页信息
	 * @param page
	 * @param workType1
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkType1 workType1);

	public List<WorkType1> findByNoExpired();
	
	public List<Map<String,Object>> findWorkType(int type,String pCode);
	public List<Map<String,Object>> findWorkTypeAll(int type,String pCode);
	public List<Map<String,Object>> findWorkTypeForWorker(int type,String pCode);
	/**
	 * 人工工种分类
	 */ 
	public List<Map<String,Object>> findOfferWorkType(int type,String pCode);
	
}
