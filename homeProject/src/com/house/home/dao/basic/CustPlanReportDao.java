package com.house.home.dao.basic;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.home.entity.basic.CustPlanReport;
import com.house.home.entity.basic.CustTypePlanReport;

@SuppressWarnings("serial")
@Repository
public class CustPlanReportDao extends BaseDao {

    /**
     * 查找一个客户类型的默认预算报表（CustPlanReport）
     * 
     * @param custType
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CustPlanReport> findCustPlanReportsByCustType(String custType) {
        
        String hql = "from CustPlanReport c where c.code in ("
                + "           select d.custPlanReport from CustTypePlanReport d where d.custType = ?)";
        
        return (List<CustPlanReport>) find(hql, custType);
    }
    
    /**
     * 查找一个客户类型的默认预算报表（CustTypePlanReport）
     * 
     * @param custType
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CustTypePlanReport> findCustTypePlanReportsByCustType(String custType) {
        
        String hql = "from CustTypePlanReport d where d.custType = ?";
        
        return (List<CustTypePlanReport>) find(hql, custType);
    }
    
    /**
     * 删除一个客户类型的默认预算报表
     * 
     * @param custType
     * @return
     */
    public long deleteCustTypePlanReportsByCustType(String custType) {
        
        String hql = "delete from CustTypePlanReport d where d.custType = ?";
        
        return executeUpdate(hql, custType);
    }
    
    /**
     * 根据客户类型的类型（清单或套餐）查找不可用的预算报表
     * 
     * @param type 清单或套餐
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<CustPlanReport> findDisabledCustPlanReportsByCustType(String type) {
        
        String hql = "from CustPlanReport c where c.isEnable = '0' and c.type = ?";
        
        return (List<CustPlanReport>) find(hql, type);
    }

}
