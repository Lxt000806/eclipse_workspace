package com.house.home.serviceImpl.basic;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.home.dao.basic.CustPlanReportDao;
import com.house.home.entity.basic.CustPlanReport;
import com.house.home.entity.basic.CustTypePlanReport;
import com.house.home.service.basic.CustPlanReportService;

@SuppressWarnings("serial")
@Service
public class CustPlanReportServiceImpl extends BaseServiceImpl implements CustPlanReportService {

	@Autowired
	private CustPlanReportDao custPlanReportDao;

    @Override
    public CustPlanReport findPackageCustPlanReport(String custType) {
        List<CustPlanReport> reports = custPlanReportDao.findCustPlanReportsByCustType(custType);
        
        if (reports != null && reports.size() > 0) {
            return reports.get(0);
        }
        
        return null;
    }

    @Override
    public List<CustPlanReport> findListCustPlanReports(String custType) {
        List<CustPlanReport> reports = custPlanReportDao.findCustPlanReportsByCustType(custType);
        
        return reports != null ? reports : Collections.<CustPlanReport>emptyList();
    }
    
    @Override
    public CustTypePlanReport findPackageCustTypePlanReport(String custType) {
        List<CustTypePlanReport> reports = custPlanReportDao.findCustTypePlanReportsByCustType(custType);
        
        if (reports != null && reports.size() > 0) {
            return reports.get(0);
        }
        
        return null;
    }

    @Override
    public List<CustTypePlanReport> findListCustTypePlanReports(String custType) {
        List<CustTypePlanReport> reports = custPlanReportDao.findCustTypePlanReportsByCustType(custType);
        
        return reports != null ? reports : Collections.<CustTypePlanReport>emptyList();
    }

    @Override
    public void setPlanReportsForCustType(String custTypeCode, String custPlanReportCode, String czybh) {
        
        if (StringUtils.isBlank(custTypeCode)) {
            throw new IllegalStateException("设置默认预算报表失败：缺少客户类型编号！");
        }
        
        if (StringUtils.isBlank(custPlanReportCode)) {
            throw new IllegalStateException("设置默认预算报表失败：缺少预算报表编号！");
        }
        
        CustTypePlanReport report = findPackageCustTypePlanReport(custTypeCode);
        if (report == null) {
            CustTypePlanReport planReport = new CustTypePlanReport();
            planReport.setCustType(custTypeCode);
            planReport.setCustPlanReport(custPlanReportCode);
            planReport.setLastUpdate(new Date());
            planReport.setLastUpdatedBy(czybh);
            planReport.setExpired("F");
            planReport.setActionLog("ADD");
            
            save(planReport);
        } else {
            report.setCustPlanReport(custPlanReportCode);
            report.setLastUpdate(new Date());
            report.setLastUpdatedBy(czybh);
            report.setActionLog("EDIT");
            
            update(report);
        }
        
    }

    @Override
    public List<CustPlanReport> findPackageCustDisabledPlanReports() {
        return custPlanReportDao.findDisabledCustPlanReportsByCustType("2");
    }

    @Override
    public List<CustPlanReport> findListCustDisabledPlanReports() {
        return custPlanReportDao.findDisabledCustPlanReportsByCustType("1");
    }

}
