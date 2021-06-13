package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.EmpCertification;

public interface EmpCertificationService extends BaseService {
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, EmpCertification empCertification);
}
