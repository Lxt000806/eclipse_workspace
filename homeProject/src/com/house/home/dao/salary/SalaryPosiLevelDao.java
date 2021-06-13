package com.house.home.dao.salary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryPosiLevel;

@SuppressWarnings("serial")
@Repository
public class SalaryPosiLevelDao extends BaseDao {

    public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,
            SalaryPosiLevel salaryPosiLevel) {
        
        List<Object> parameters = new ArrayList<Object>();

        String sql = "select * from (" +
        		"select a.PK, a.Descr, b.Descr SalaryPosiClassDescr, " +
        		"a.Salary, a.BasicSalary, a.MinPerfAmount, b.IsFront, c.NOTE IsFrontDescr, " +
        		"a.DispSeq, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog " +
        		"from tSalaryPosiLevel a " +
        		"left join tSalaryPosiClass b on a.PosiClass = b.PK " +
        		"left join tXTDM c on b.IsFront = c.CBM and c.ID = 'SALISFRONT' " +
        		"where 1 = 1 ";

        if (StringUtils.isNotBlank(salaryPosiLevel.getIsFront())) {
            sql += " and b.IsFront = ? ";
            parameters.add(salaryPosiLevel.getIsFront());
        }
        
        if (salaryPosiLevel.getSalaryPosiClass() != null) {
            sql += " and b.PK = ? ";
            parameters.add(salaryPosiLevel.getSalaryPosiClass());
        }

        if (StringUtils.isBlank(salaryPosiLevel.getExpired()) || "F".equals(salaryPosiLevel.getExpired())) {
            sql += " and a.Expired = 'F' ";
        }

        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.PK";
        }

        return this.findPageBySql(page, sql, parameters.toArray());
    }

    public List<Map<String, Object>> findLevelsBySalaryPosiClass(Integer salaryPosiClassPk) {
        String sql = "select * from tSalaryPosiLevel where PosiClass = ?";
        
        return findBySql(sql, salaryPosiClassPk);
    }
    
    public void updateSalaryPosiLevel(SalaryPosiLevel level) {
        String sql = "update tSalaryPosiLevel "
                       + "set Descr = ?, Salary = ?, BasicSalary = ?, "
                   + "MinPerfAmount = ?, DispSeq = ?, "
                   + "LastUpdatedBy = ?, LastUpdate = getdate(), "
                   + "Expired = ?, ActionLog = 'EDIT' "
                   + "where PK = ? ";
        
        executeUpdateBySql(sql, level.getDescr(), level.getSalary(),
                level.getBasicSalary(), level.getMinPerfAmount(),
                level.getDispSeq(), level.getLastUpdatedBy(),
                level.getExpired(), level.getPk());
    }
    
    public void updateSalaryEmpsSalary(SalaryPosiLevel level) {
        String sql = "update tSalaryEmp "
                + "set Salary = b.Salary, BasicSalary = b.BasicSalary, "
                + "    LastUpdate = getdate(), LastUpdatedBy = ?, ActionLog = 'EDIT' "
                + "from tSalaryEmp a "
                + "inner join tSalaryPosiLevel b on a.PosiLevel = b.PK "
                + "where a.BasicSalarySetMode = '1' and a.Status = 'ACT' "
                + "    and (a.Salary <> b.Salary or a.BasicSalary <> b.BasicSalary) "
                + "    and b.PK = ? ";
        
        executeUpdateBySql(sql, level.getLastUpdatedBy(), level.getPk());
    }

}

