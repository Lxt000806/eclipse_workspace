package com.house.home.service.salary;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryData;
import com.house.home.entity.salary.SalaryMon;

public interface SalaryMonService extends BaseService {

    Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,SalaryMon salaryMon);
    
    /**
     * 获取结算中月份
     * @param page
     * @param salaryMon
     * @return
     */
    public List<Map<String, Object>> getCheckingMon();

    public List<Map<String, Object>> getCheckedMon();
    
    public List<Map<String, Object>> getLastMon(SalaryData salaryData);

}
