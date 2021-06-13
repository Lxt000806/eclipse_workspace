package com.house.home.dao.salary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.salary.SalaryEmpDeduction;

@SuppressWarnings("serial")
@Repository
public class OtherSalaryEmpDeductionDao extends BaseDao {

	/**
	 * OtherSalaryEmpDeduction分页信息
	 * 
	 * @param page
	 * @param otherSalaryEmpDeduction
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmpDeduction otherSalaryEmpDeduction) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select e.Descr ConSignCmpDescr,a.SalaryEmp,c.EmpName,f.Desc2 Department1Descr, "
					+"b.Descr DeductType2Descr,a.Amount,a.SalaryMon,a.DeductDate,a.LastUpdate,a.LastUpdatedBy, "
					+"a.Expired,a.ActionLog,a.Remarks,a.PK "
					+"from tSalaryEmpDeduction a  "
					+"left join tSalaryDeductType2 b on a.DeductType2=b.Code "
					+"left join tSalaryEmp c on a.SalaryEmp=c.EmpCode "
					+"left join tConSignCmp e on c.ConSignCmp=e.Code "
					+"left join tDepartment1 f on c.Department1=f.Code " 
					+"where a.DeductType1='2' ";

    	if (otherSalaryEmpDeduction.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(otherSalaryEmpDeduction.getPk());
		}
    	if(StringUtils.isNotBlank(otherSalaryEmpDeduction.getQueryCondition())){
			sql+=" and ( c.EmpName like ? or c.EmpCode like ? or c.IDNum like ? )";
			list.add("%"+otherSalaryEmpDeduction.getQueryCondition()+"%");
			list.add("%"+otherSalaryEmpDeduction.getQueryCondition()+"%");
			list.add("%"+otherSalaryEmpDeduction.getQueryCondition()+"%");
		}
    	if (otherSalaryEmpDeduction.getSalaryMon() != null) {
			sql += " and a.SalaryMon=?";
			list.add(otherSalaryEmpDeduction.getSalaryMon());
		}
    	if (StringUtils.isNotBlank(otherSalaryEmpDeduction.getDeductType2())) {
			sql += " and a.DeductType2=? ";
			list.add(otherSalaryEmpDeduction.getDeductType2());
		}
    	if (StringUtils.isNotBlank(otherSalaryEmpDeduction.getConSignCmp())) {
			sql += " and c.ConSignCmp=? ";
			list.add(otherSalaryEmpDeduction.getConSignCmp());
		}
    	if (StringUtils.isNotBlank(otherSalaryEmpDeduction.getDepartment1())) {
			sql += " and c.Department1 in ("+SqlUtil.resetStatus(otherSalaryEmpDeduction.getDepartment1())+")";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.SalaryEmp asc,a.DeductDate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

