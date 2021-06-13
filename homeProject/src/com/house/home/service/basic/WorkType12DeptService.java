package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Position;
import com.house.home.entity.basic.WorkType12Dept;

public interface WorkType12DeptService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkType12Dept workType12Dept);

	public boolean getIsExists(String code);

	public boolean getIsExistsDescr(String descr,String workType12);

	public boolean getIsExistsDescrByCode(String code,String descr,String workType12);
	
	public Page<Map<String,Object>> getWorkType12DeptList(Page<Map<String,Object>> page, WorkType12Dept workType12Dept);


}

