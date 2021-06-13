package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Department3;

public interface Department3Service extends BaseService {

	/**三级部门列表
	 * @param page
	 * @param department3
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Department3 department3);
	
	/**根据名称获取三级部门
	 * @param desc2
	 * @return
	 */
	public Department3 getByDesc2(String desc2);

	/**根据二级部门获取三级部门
	 * @param code
	 * @return
	 */
	public List<Department3> getByDepartment2(String code);

	public List<Department3> findByNoExpired();

	public Department3 getByCode(String code);
}
