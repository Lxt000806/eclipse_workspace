package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CourseType;
import com.house.home.entity.basic.Department1;

public interface Department1Service extends BaseService {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Department1 department1);
	
	public Department1 getByDesc2(String desc2);

	public List<Department1> findByNoExpired();
	
	public List<Department1> getByDepType(String depType);
	
	public Department1 getByCode(String code);
	//部门三级联动
	public List<Map<String,Object>> findDepType(int type,String pCode);

	public List<Map<String,Object>> findDepAll(int type,String pCode);

	public void expiredChild(Department1 department1);
}
