package com.house.home.dao.salary;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.salary.SalaryEmpReg;
import com.house.home.entity.salary.SalaryInd;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class SalaryEmpRegDao extends BaseDao{

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SalaryEmpReg salaryEmpReg) {	
		List<Object> list = new ArrayList<Object>();
 
		String sql = " select * from (" +
				" select b.idNum,a.pk,a.SalaryEmp, b.EmpName, c.Descr, a.lastUpdate,a.LastUpdatedby, a.Expired, a.actionLog,a.remarks   " +
				" ,a.SocialInsurParam" +
				" from  tSalaryEmpReg a " +
				" left join tSalaryEmp b on b.EmpCode = a.salaryEmp" +
				" left join tSocialInsurParam c on c.pk = a.SocialInsurParam " +
				" where 1=1" ;
		if(StringUtils.isNotBlank(salaryEmpReg.getQueryCondition())){
			sql += " and (b.EmpCode like ? or b.EmpName like ? or b.IdNum like ?)";
			list.add("%"+salaryEmpReg.getQueryCondition()+"%");
			list.add("%"+salaryEmpReg.getQueryCondition()+"%");
			list.add("%"+salaryEmpReg.getQueryCondition()+"%");
		}
		if (StringUtils.isBlank(salaryEmpReg.getExpired())
				|| "F".equals(salaryEmpReg.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<Map<String, Object>> checkInfo(SalaryEmpReg salaryEmpReg){
		String sql = " select 1 from tSalaryEmpReg where SalaryEmp = ? and expired = 'F' and pk <> ?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{salaryEmpReg.getSalaryEmp(),salaryEmpReg.getPk()});
		if(list != null && list.size() > 0){
			return list;
		}
		
		return null;
	}

}
