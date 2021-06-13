package com.house.home.service.salary;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryPosiLevel;

public interface SalaryPosiLevelService extends BaseService {

    Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,
            SalaryPosiLevel salaryPosiLevel);

    List<SalaryPosiLevel> findLevelsBySalaryPosiClass(Integer salaryPosiClassPk);
    
    /**
     * 更新薪酬岗位级别，
     * 如果此岗位级别工资与基本工资发生变动，
     * 同时更新引用此岗位级别的薪酬员工的工资与基本工资
     * 
     * @param level
     */
    void doUpdate(SalaryPosiLevel level);
    
}
