package com.house.home.dao.salary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryPosiClass;

@SuppressWarnings("serial")
@Repository
public class SalaryPosiClassDao extends BaseDao {

    public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,
            SalaryPosiClass salaryPosiClass) {
        
        List<Object> parameters = new ArrayList<Object>();

        String sql = "select * from (" +
        		"select PK, Descr, IsFront, b.NOTE IsFrontDescr, LastUpdate, LastUpdatedBy, " +
        		"Expired, ActionLog " +
        		"from tSalaryPosiClass a " +
        		"left join tXTDM b on a.IsFront = b.CBM and b.ID = 'SALISFRONT' " +
        		"where 1=1 ";

        if (StringUtils.isNotBlank(salaryPosiClass.getIsFront())) {
            sql += " and a.IsFront = ? ";
            parameters.add(salaryPosiClass.getIsFront());
        }

        if (StringUtils.isBlank(salaryPosiClass.getExpired()) || "F".equals(salaryPosiClass.getExpired())) {
            sql += " and a.Expired = 'F' ";
        }

        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.PK";
        }

        return this.findPageBySql(page, sql, parameters.toArray());
    }
}

