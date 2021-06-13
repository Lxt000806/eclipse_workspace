package com.house.home.service.salary;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryPosiClass;

public interface SalaryPosiClassService extends BaseService {

    Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            SalaryPosiClass salaryPosiClass);

	
}
