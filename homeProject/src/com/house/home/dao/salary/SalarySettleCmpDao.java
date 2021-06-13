package com.house.home.dao.salary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.home.entity.salary.SalarySettleCmp;

@SuppressWarnings("serial")
@Repository
public class SalarySettleCmpDao extends BaseDao {

    public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,
            SalarySettleCmp salarySettleCmp) {
        
        List<Object> parameters = new ArrayList<Object>();

        String sql = "select * from (" +
        		"select a.PK, a.Descr, a.Remarks, " +
        		"a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog,a.BankType, a.CardId,a.Actname,x.note bankDescr " +
        		"from tSalarySettleCmp a " +
        		" left join tXtdm x on x.cbm = a.BankType and x.id = 'SALBANKTYPE'" +
        		"where 1 = 1 ";

        if (StringUtils.isNotBlank(salarySettleCmp.getDescr())) {
            sql += " and a.Descr = ? ";
            parameters.add(salarySettleCmp.getDescr());
        }

        if (StringUtils.isBlank(salarySettleCmp.getExpired()) || "F".equals(salarySettleCmp.getExpired())) {
            sql += " and a.Expired = 'F' ";
        }

        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.PK";
        }

        return this.findPageBySql(page, sql, parameters.toArray());
    }

    public List<SalarySettleCmp> findSalarySettleCmpsByDescr(String salarySettleCmpDescr) {
        String sql = "select * from tSalarySettleCmp where Descr = ?";
        
        List<Map<String, Object>> cmps = findBySql(sql, salarySettleCmpDescr);
        
        return BeanConvertUtil.mapToBeanList(cmps, SalarySettleCmp.class);
    }
}

