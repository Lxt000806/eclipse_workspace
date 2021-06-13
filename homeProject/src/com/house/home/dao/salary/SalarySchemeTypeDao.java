package com.house.home.dao.salary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalarySchemeType;

@SuppressWarnings("serial")
@Repository
public class SalarySchemeTypeDao extends BaseDao {

    public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,
            SalarySchemeType salarySchemeType) {
        
        List<Object> parameters = new ArrayList<Object>();

        String sql = "select * from (" +
        		"select a.Code, a.Descr, a.DispSeq, a.Remarks, " +
        		"a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog " +
        		"from tSalarySchemeType a " +
        		"where 1 = 1 ";

        if (StringUtils.isBlank(salarySchemeType.getExpired()) || "F".equals(salarySchemeType.getExpired())) {
            sql += " and a.Expired = 'F' ";
        }

        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.Code";
        }

        return this.findPageBySql(page, sql, parameters.toArray());
    }

}

