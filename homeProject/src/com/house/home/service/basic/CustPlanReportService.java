package com.house.home.service.basic;

import java.util.List;

import com.house.framework.commons.orm.BaseService;
import com.house.home.entity.basic.CustPlanReport;
import com.house.home.entity.basic.CustTypePlanReport;

public interface CustPlanReportService extends BaseService {

    /**
     * 查找套餐客户的默认预算报表，只有一个（CustPlanReport）
     * 
     * @param custType
     * @return
     */
    CustPlanReport findPackageCustPlanReport(String custType);
    
    /**
     * 查找清单客户的默认预算报表，可有多个（CustPlanReport）
     * 
     * @param custType
     * @return
     */
    List<CustPlanReport> findListCustPlanReports(String custType);
    
    /**
     * 查找套餐客户的默认预算报表，只有一个（CustTypePlanReport）
     * 
     * @param custType
     * @return
     */
    CustTypePlanReport findPackageCustTypePlanReport(String custType);
    
    /**
     * 查找清单客户的默认预算报表，可有多个（CustTypePlanReport）
     * 
     * @param custType
     * @return
     */
    List<CustTypePlanReport> findListCustTypePlanReports(String custType);

    /**
     * 设置客户类型的默认预算报表，暂时只用作设置套餐客户，
     * 如果以后要同时设置清单客户再扩展此方法
     * 
     * @param custTypeCode
     * @param custPlanReportCode
     * @param czybh
     */
    void setPlanReportsForCustType(String custTypeCode, String custPlanReportCode, String czybh);
    
    /**
     * 查找套餐客户的不可用预算报表
     * 
     * @return
     */
    List<CustPlanReport> findPackageCustDisabledPlanReports();
    
    /**
     * 查找清单客户的不可用预算报表
     * 
     * @return
     */
    List<CustPlanReport> findListCustDisabledPlanReports();
	
}
