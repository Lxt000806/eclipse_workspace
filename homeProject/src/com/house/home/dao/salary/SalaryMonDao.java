package com.house.home.dao.salary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryData;
import com.house.home.entity.salary.SalaryMon;

@SuppressWarnings("serial")
@Repository
public class SalaryMonDao extends BaseDao {

    public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,
            SalaryMon salaryMon) {
        
        List<Object> parameters = new ArrayList<Object>();

        String sql = "select * from (" +
        		"select a.SalaryMon, a.Descr, a.BeginDate, a.EndDate, " +
        		"a.Status, b.NOTE StatusDescr, a.FirstCalcTime, a.LastCalcTime, " +
        		"a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog " +
        		"from tSalaryMon a " +
        		"left join tXTDM b on a.Status = b.CBM and b.ID = 'SALMONSTAT' " +
        		"where 1 = 1 ";

        // 202000 < ? < (202100 = 202000 + 100)
        if (salaryMon.getYear() != null) {
            sql += " and a.SalaryMon > ? and a.SalaryMon < ? ";
            parameters.add(salaryMon.getYear());
            parameters.add(salaryMon.getYear() + 100);
        }

        if (StringUtils.isBlank(salaryMon.getExpired()) || "F".equals(salaryMon.getExpired())) {
            sql += " and a.Expired = 'F' ";
        }

        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.SalaryMon";
        }

        return this.findPageBySql(page, sql, parameters.toArray());
    }
    
    /**
     * 获取结算中月份
     * @param page
     * @param salaryMon
     * @return
     */
    public List<Map<String, Object>> getCheckingMon() {
        
        List<Object> parameters = new ArrayList<Object>();

        String sql = " select max(SalaryMon)SalaryMon from tSalaryMon where Status='2' ";

        return this.findBySql(sql, parameters.toArray());
    }

    public List<Map<String, Object>> getCheckedMon() {
        
        List<Object> parameters = new ArrayList<Object>();

        String sql = " select max(SalaryMon)SalaryMon from tSalaryMon where Status='3' ";

        return this.findBySql(sql, parameters.toArray());
    }
    
	 public List<Map<String, Object>> getLastMon(SalaryData salaryData) {
        
        List<Object> parameters = new ArrayList<Object>();

        String sql = " select max(SalaryMon)SalaryMon from tSalaryMon where SalaryMon < ? ";
        
        parameters.add(salaryData.getSalaryMon());
        return this.findBySql(sql, parameters.toArray());
    }
}

