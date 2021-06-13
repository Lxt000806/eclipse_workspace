package com.house.home.service.project;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.SalaryType;

public interface SalaryTypeService extends BaseService {

	/**SalaryType分页信息
	 * @param page
	 * @param salaryType
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryType salaryType);

	public List<SalaryType> findByNoExpired();

	public Map<String,Object> findByCode(String code);
	
}
