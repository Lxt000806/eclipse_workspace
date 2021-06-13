package com.house.home.dao.salary;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.EmpAdvanceWage;
import com.house.home.entity.salary.SalaryInd;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class SalaryAdvanceDao extends BaseDao{

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, EmpAdvanceWage empAdvanceWage) {	
		List<Object> list = new ArrayList<Object>();
 
		String sql = " select * from ( select a.SalaryEmp,b.EmpName,a.AdvanceWage,a.Remarks,a.LastUpdate," +
				" a.LastUpdatedBy,a.Expired,a.ActionLog,c.Desc1 dept1Descr " +
				" from tEmpAdvanceWage a " +
				 " left join tSalaryEmp b on b.EmpCode = a.SalaryEmp " +
				 " left join tDepartment1 c on c.Code = b.Department1 " +
				 " where 1=1 "; 
		
		if(StringUtils.isNotBlank(empAdvanceWage.getQueryCondition())){
			sql+=" and (b.empName like ?";
			sql+=" or b.IDNum like ?";
			sql+=" or b.empCode like ? )";
			
			list.add("%"+empAdvanceWage.getQueryCondition()+"%");
			list.add("%"+empAdvanceWage.getQueryCondition()+"%");
			list.add("%"+empAdvanceWage.getQueryCondition()+"%");
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

}
