package com.house.home.service.salary;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalarySchemeType;

public interface SalarySchemeTypeService extends BaseService {

    Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            SalarySchemeType salarySchemeType);

}
