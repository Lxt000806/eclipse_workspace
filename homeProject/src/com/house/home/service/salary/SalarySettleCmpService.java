package com.house.home.service.salary;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalarySettleCmp;

public interface SalarySettleCmpService extends BaseService {

    Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            SalarySettleCmp salarySettleCmp);

    List<SalarySettleCmp> findSalarySettleCmpsByDescr(String salarySettleCmpDescr);
}
