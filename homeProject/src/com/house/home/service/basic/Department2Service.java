package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Department2;

public interface Department2Service extends BaseService {

	/**二级部门列表
	 * @param page
	 * @param department2
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Department2 department2);
	
	/**根据名称获取二级部门
	 * @param desc2
	 * @return
	 */
	public Department2 getByDesc2(String desc2);

	public Department2 getByCode(String code);
	/**根据一级部门获取二级部门列表
	 * @param code
	 * @return
	 */
	public List<Department2> getByDepartment1(String code);

	public List<Department2> findByNoExpired();
	
	public List<Department2> getByDepType(String depType);
	
	public void expiredChild(Department2 department2);
	
	/**
	 * 获取二级部门,带领导
	 * @return
	 */
	public List<Department2> getDepartment2WithLeader();
}
