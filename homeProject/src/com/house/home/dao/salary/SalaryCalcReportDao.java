package com.house.home.dao.salary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.salary.SalaryData;

@SuppressWarnings("serial")
@Repository
public class SalaryCalcReportDao extends BaseDao{
	
	@SuppressWarnings("unused")
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryData salaryData) {
		
		List<Object> list = new ArrayList<Object>();
		
		String groupCol = "";
		String groupBySql = "";
		String groupByEnd = "";
		String colSql = "";
		String joinSql = "";
		
		if("1".equals(salaryData.getCalcRptType()) || "3".equals(salaryData.getCalcRptType())){
			groupCol =",a.ConSignCmp,a.Department2,a.EmpCode,isnull(a.Position,'')Position,a.empName ";
			groupBySql =",a.ConSignCmp,a.Department2,a.EmpCode,isnull(a.Position,''),a.EmpName  ";
			groupByEnd =",a.ConSignCmp,a.Department2,a.EmpCode,isnull(a.Position,''),a.empName";
			colSql = ",c.Descr cmpDescr, d.Desc2 dept2Descr, e.Desc2 PositionDescr  ";
			joinSql = " left join tConSignCmp c on c.Code = a.ConSignCmp" +
					" left join tDepartment2 d on d.Code = a.Department2" +
					" left join tPosition e on e.Code = a.Position";
		} 

		if("3".equals(salaryData.getCalcRptType()) || "4".equals(salaryData.getCalcRptType()) ){
			groupCol +=",a.SalaryMon"; 
			groupBySql +=",a.SalaryMon"; 
			groupByEnd +=",a.SalaryMon"; 
			
		}
		
		String sql = "select * from	( select a.*, b.Desc2 dept1Descr" + colSql +
				" from ( select count(1) empNum,a.Department1 " +groupCol+
				"	,sum(isnull(BaseSalary, 0)) BaseSalary," +
				"			sum(isnull(Withhold, 0)) Withhold, sum(isnull(TaxSalary, 0)) TaxSalary," +
				"			sum(isnull(IncomeTax, 0)) IncomeTax, sum(isnull(RealPaySalary, 0)) RealPaySalary," +
				"			sum(isnull(UnpaidSalary, 0)) UnpaidSalary  " +
				"		from ( select	count(1) EmpNum, a.Department1"+ groupCol +
				" ,sum(isnull(BaseSalary, 0)) BaseSalary," +
				"				sum(isnull(Withhold, 0)) Withhold, sum(isnull(TaxSalary, 0)) TaxSalary," +
				"				sum(isnull(IncomeTax, 0)) IncomeTax, sum(isnull(RealPaySalary, 0)) RealPaySalary," +
				"				sum(isnull(UnpaidSalary, 0)) UnpaidSalary " +
				"			from tSalaryEmpHis a" +
				"			where 1 = 1 " ;
		if(salaryData.getSalaryMon() != null){
			sql+=" and a.SalaryMon >= ?";
			list.add(salaryData.getSalaryMon());
		}	
		if(salaryData.getSalaryMonTo() != null){
			sql+=" and a.SalaryMon < = ?";
			list.add(salaryData.getSalaryMonTo());
		}
		if(salaryData.getSalaryScheme() != null){
			sql+=" and a.SalaryScheme = ? ";
			list.add(salaryData.getSalaryScheme());
		}
		if(StringUtils.isNotBlank(salaryData.getDepartment1())){
			sql+=" and a.Department1 = ? ";
			list.add(salaryData.getDepartment1());
		}
		if(StringUtils.isNotBlank(salaryData.getDepartment2())){
			sql+=" and a.Department2 = ? ";
			list.add(salaryData.getDepartment2());
		}
		if(StringUtils.isNotBlank(salaryData.getEmpName()) && ("1".equals(salaryData.getCalcRptType()) || "3".equals(salaryData.getCalcRptType()))){
			sql+=" and (a.Empname = ? or a.empCode = ? or a.IdNum = ? )";
			list.add(salaryData.getEmpName());
			list.add(salaryData.getEmpName());
			list.add(salaryData.getEmpName());
		}
		sql+="		group by a.Department1, a.EmpName" + groupBySql +
				" 	) a group by a.Department1 "+ groupByEnd +
				" ) a" +
				" left join tDepartment1 b on b.Code = a.Department1"
				+ joinSql ;
				

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
}
