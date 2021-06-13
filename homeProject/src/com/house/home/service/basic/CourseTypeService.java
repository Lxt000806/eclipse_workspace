package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CourseType;

public interface CourseTypeService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CourseType courseType);
	
	public CourseType getByCode(String code);

	public CourseType getByDescr(String descr, String code);

}
