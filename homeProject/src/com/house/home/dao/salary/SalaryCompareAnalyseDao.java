package com.house.home.dao.salary;
import com.house.framework.commons.orm.BaseDao;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.salary.SalaryData;

@SuppressWarnings("serial")
@Repository
public class SalaryCompareAnalyseDao extends BaseDao{

	public List<Map<String,Object>> findPageBySql(SalaryData salaryData) {
		List<Object> list = new ArrayList<Object>();
		
		if(salaryData.getSalaryMon() == null || salaryData.getCompareMon() == null || salaryData.getSalaryScheme() == null){
			return null;
		}
		
		String sql = "select *,a.ABaseSalary - b.BbaseSalary ChgBaseSalary, a.ARealPaySalary -b.BRealPaySalary chgRealPaySalary," +
				"		case when b.BbaseSalary <> '0' and b.BBaseSalary is not null then (a.AbaseSalary - b.BBaseSalary) / b.BBaseSalary else 0 end baseSalaryPer," +
				"		case	when b.BRealPaySalary <> '0' and b.BRealPaySalary is not null" +
				"						then (a.ARealPaySalary - b.BRealPaySalary) / b.BRealPaySalary else 0 end RealPaySalaryPer,a.AempNum - b.BempNum chgempnum " +
				" from (" +
				" select count(1) AEmpNum," +
				"	sum(case when b.JoinDate>=c.BeginDate and b.JoinDate<= c.EndDate then 1 else 0 end) AJoinNum," +
				"	sum(case when b.LeaveDate>=c.BeginDate and b.LeaveDate<= c.EndDate then 1 else 0 end) ALeaveNum," +
				"	sum(isnull(RealPaySalary,0)) ARealPaySalary,sum(isnull(a.BaseSalary,0)) AbaseSalary ," +
				"	sum(case when b.JoinDate>=c.BeginDate and b.JoinDate<= c.EndDate then isnull(RealPaySalary,0) else 0 end) AJoinRealPaySalary," +
				"	sum(case when b.LeaveDate>=c.BeginDate and b.LeaveDate<= c.EndDate then isnull(RealPaySalary,0) else 0 end) ALeaveRealPaySalary," +
				"	sum(case when b.JoinDate>=c.BeginDate and b.JoinDate<= c.EndDate then isnull(BaseSalary,0) else 0 end) AJoinBaseSalary," +
				"	sum(case when b.LeaveDate>=c.BeginDate and b.LeaveDate<= c.EndDate then isnull(BaseSalary,0) else 0 end) ALeaveBaseSalary," +
				"	sum(isnull(a.UnpaidSalary,0)) AUnPaidSalary" +
				" from  tSalaryEmpHis a " +
				" left join tSalaryEmp b on b.EmpCode = a.EmpCode " +
				" left join tSalaryMon c on c.SalaryMon = a.SalaryMon" +
				" where 1=1 " ;
		if(salaryData.getSalaryScheme() != null){
			sql+=" and a.salaryScheme = ?";
			list.add(salaryData.getSalaryScheme());
		}
		if(salaryData.getSalaryMon() != null){
			sql+=" and a.salaryMon = ?";
			list.add(salaryData.getSalaryMon());
		}
		sql+= " group by a.SalaryScheme , a.SalaryMon ) a" +
				" left join (" +
				"	select count(1) BEmpNum," +
				"	sum(case when b.JoinDate>=c.BeginDate and b.JoinDate<= c.EndDate then 1 else 0 end) BJoinNum," +
				"	sum(case when b.LeaveDate>=c.BeginDate and b.LeaveDate<= c.EndDate then 1 else 0 end) BLeaveNum," +
				"	sum(isnull(RealPaySalary,0)) BRealPaySalary,sum(isnull(a.BaseSalary,0)) BbaseSalary ," +
				"	sum(case when b.JoinDate>=c.BeginDate and b.JoinDate<= c.EndDate then isnull(RealPaySalary,0) else 0 end) BJoinRealPaySalary," +
				"	sum(case when b.LeaveDate>=c.BeginDate and b.LeaveDate<= c.EndDate then isnull(RealPaySalary,0) else 0 end) BLeaveRealPaySalary," +
				"	sum(case when b.JoinDate>=c.BeginDate and b.JoinDate<= c.EndDate then isnull(BaseSalary,0) else 0 end) BJoinBaseSalary," +
				"	sum(case when b.LeaveDate>=c.BeginDate and b.LeaveDate<= c.EndDate then isnull(BaseSalary,0) else 0 end) BLeaveBaseSalary," +
				"	sum(isnull(a.UnpaidSalary,0)) BUnPaidSalary" +
				"	from  tSalaryEmpHis a " +
				"	left join tSalaryEmp b on b.EmpCode = a.EmpCode " +
				"	left join tSalaryMon c on c.SalaryMon = a.SalaryMon" +
				"	where 1=1 " ;
		if(salaryData.getSalaryScheme() != null){
			sql+=" and a.salaryScheme = ?";
			list.add(salaryData.getSalaryScheme());
		}
		if(salaryData.getCompareMon() != null){
			sql+=" and a.salaryMon = ?";
			list.add(salaryData.getCompareMon());
		}
		sql+="	group by a.SalaryScheme , a.SalaryMon )b on 1=1";

		return this.findBySql(sql, list.toArray());
	}
	
	/**
	 * 本月入职人员
	 * @param page
	 * @param salaryData
	 * @return
	 */
	public Page<Map<String,Object>> findJoinEmpPageBySql(Page<Map<String,Object>> page, SalaryData salaryData) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" +
				" select a.EmpCode, a.EmpName, b.IDNum, c.Desc2 dep1Descr, d.Desc2 dep2Descr,b.JoinDate,a.baseSalary, a.realPaySalary,e.Desc2 positiondescr from (" +
				" 	select a.EmpCode, a.EmpName,sum(a.baseSalary)baseSalary,sum(a.RealPaySalary) realPaySalary,a.Position  from tSalaryEmpHis a " +
				" 	left join tSalaryMon b on b.SalaryMon = a.SalaryMon" +
				" 	where a.JoinDate >= b.BeginDate and a.JoinDate <= b.EndDate";
		if(salaryData.getSalaryScheme() != null){
			sql+=" and a.SalaryScheme = ? ";
			list.add(salaryData.getSalaryScheme());
		}
		if(salaryData.getSalaryMon() != null){
			sql+=" and a.SalaryMon = ? ";
			list.add(salaryData.getSalaryMon());
		}
		sql+= " 	group by a.EmpCode,a.EmpName,a.SalaryMon,a.position" +
				" )a " +
				" left join tSalaryEmp b on b.EmpCode = a.EmpCode" +
				" left join tDepartment1 c on c.Code = b.Department1" +
				" left join tDepartment2 d on d.Code = b.Department2 " +
				" left join tPosition e on e.Code = a.Position" ;

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a ";
		}

		return this.findPageBySql(page, sql, list.toArray());

	}
	
	/**
	 * 本月离职人员
	 * @param page
	 * @param salaryData
	 * @return
	 */
	public Page<Map<String,Object>> findLeaveEmpPageBySql(Page<Map<String,Object>> page, SalaryData salaryData) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" +
				" select a.EmpCode, a.EmpName, b.IDNum, c.Desc2 dep1Descr, d.Desc2 dep2Descr,b.JoinDate,a.baseSalary, " +
				" a.realPaySalary,a.leaveDate,e.Desc2 positionDescr, unPaidSalary " +
				" from (" +
				" 	select a.EmpCode, a.EmpName,sum(isnull(a.baseSalary,0))baseSalary,sum(isnull(a.RealPaySalary,0)) realPaySalary," +
				" 	sum(isnull(a.UnPaidSalary,0))unPaidSalary,a.leavedate,a.position  " +
				" 	from tSalaryEmpHis a " +
				" 	left join tSalaryMon b on b.SalaryMon = a.SalaryMon" +
				" 	where a.leaveDate >= b.BeginDate and a.leaveDate<= b.EndDate" ;
		
			if(salaryData.getSalaryScheme() != null){
				sql+=" and a.SalaryScheme = ? ";
				list.add(salaryData.getSalaryScheme());
			}
			if(salaryData.getSalaryMon() != null){
				sql+=" and a.SalaryMon = ? ";
				list.add(salaryData.getSalaryMon());
			}
			
			sql+=" 	group by a.EmpCode,a.EmpName,a.SalaryMon,a.leavedate,a.position" +
				" )a " +
				" left join tSalaryEmp b on b.EmpCode = a.EmpCode" +
				" left join tDepartment1 c on c.Code = b.Department1" +
				" left join tDepartment2 d on d.Code = b.Department2 " +
				" left join tPosition e on e.Code = a.Position" ;

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a ";
		}

		return this.findPageBySql(page, sql, list.toArray());

	}
	
	/**
	 * 基本工资变动名单
	 * @param page
	 * @param salaryData
	 * @return
	 */
	public Page<Map<String,Object>> findBaseSalaryCHgEmpPageBySql(Page<Map<String,Object>> page, SalaryData salaryData) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" +
				"	select a.EmpCode,c.EmpName,c.IDNum,d.Desc2 dep1Descr, e.desc2 dep2descr,f.Desc2 positiondescr,a.BaseSalary,b.baseSalary basesalarycompare," +
				"	a.BaseSalary - b.baseSalary chgBaseSalary," +
				"	case when b.baseSalary <>0 and b.baseSalary is not null then (a.BaseSalary - b.baseSalary)/b.baseSalary else 0 end chgPer" +
				" from (" +
				"	select a.EmpCode,sum(isnull(a.BaseSalary,0)) baseSalary  from tSalaryEmpHis a " +
				"	where 1=1  ";
			if(salaryData.getSalaryScheme() != null){
				sql+=" and a.SalaryScheme = ? ";
				list.add(salaryData.getSalaryScheme());
			}
			if(salaryData.getSalaryMon() != null){
				sql+=" and a.SalaryMon = ? ";
				list.add(salaryData.getSalaryMon());
			}
			sql+=	"	group by a.SalaryScheme, a.SalaryScheme ,a.EmpCode" +
				" )a " +
				" left join  (" +
				"	select a.EmpCode,sum(isnull(a.BaseSalary,0)) baseSalary  from tSalaryEmpHis a " +
				"	where 1 = 1  " ;
			if(salaryData.getSalaryScheme() != null){
				sql+=" and a.SalaryScheme = ? ";
				list.add(salaryData.getSalaryScheme());
			}
			if(salaryData.getCompareMon() != null){
				sql+=" and a.SalaryMon = ? ";
				list.add(salaryData.getCompareMon());
			}
			sql+= "	group by a.SalaryScheme, a.SalaryScheme ,a.EmpCode" +
				" )b on a.empCode = b.EmpCode " +
				" left join tSalaryEmp c on c.EmpCode = a.EmpCode" +
				" left join tDepartment1 d on d.Code = c.Department1" +
				" left join tDepartment2 e on e.Code = c.Department2" +
				" left join tPosition f on f.Code = c.Position" +
				" where 1=1 and a.BaseSalary - b.baseSalary <> 0 " ;

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by chgBaseSalary desc";
		}

		return this.findPageBySql(page, sql, list.toArray());

	}
	
	/**
	 * 实发工资变动名单
	 * @param page
	 * @param salaryData
	 * @return
	 */
	public Page<Map<String,Object>> findRealPayPageBySql(Page<Map<String,Object>> page, SalaryData salaryData) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" +
				"	select a.EmpCode,c.EmpName,c.IDNum,d.Desc2 dep1Descr, e.desc2 dep2descr,f.Desc2 positiondescr,a.RealPaySalary,b.RealPaySalary compareRealPaySalary," +
				"	a.RealPaySalary - b.RealPaySalary chgRealPaySalary," +
				"	case when b.RealPaySalary <>0 and b.RealPaySalary is not null then (a.RealPaySalary - b.RealPaySalary)/b.RealPaySalary else 0 end chgPer" +
				" from (" +
				"	select a.EmpCode,sum(isnull(a.RealPaySalary,0)) RealPaySalary  from tSalaryEmpHis a " +
				"	where 1=1  ";
			if(salaryData.getSalaryScheme() != null){
				sql+=" and a.SalaryScheme = ? ";
				list.add(salaryData.getSalaryScheme());
			}
			if(salaryData.getSalaryMon() != null){
				sql+=" and a.SalaryMon = ? ";
				list.add(salaryData.getSalaryMon());
			}
			sql+=	"	group by a.SalaryScheme, a.SalaryScheme ,a.EmpCode" +
				" )a " +
				" left join  (" +
				"	select a.EmpCode,sum(isnull(a.RealPaySalary,0)) RealPaySalary  from tSalaryEmpHis a " +
				"	where 1 = 1  " ;
			if(salaryData.getSalaryScheme() != null){
				sql+=" and a.SalaryScheme = ? ";
				list.add(salaryData.getSalaryScheme());
			}
			if(salaryData.getCompareMon() != null){
				sql+=" and a.SalaryMon = ? ";
				list.add(salaryData.getCompareMon());
			}
			sql+= "	group by a.SalaryScheme, a.SalaryScheme ,a.EmpCode" +
				" )b on a.empCode = b.EmpCode " +
				" left join tSalaryEmp c on c.EmpCode = a.EmpCode" +
				" left join tDepartment1 d on d.Code = c.Department1" +
				" left join tDepartment2 e on e.Code = c.Department2" +
				" left join tPosition f on f.Code = c.Position" +
				" where 1=1 and a.RealPaySalary - b.RealPaySalary <> 0 " ;

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.chgRealPaySalary desc";
		}

		return this.findPageBySql(page, sql, list.toArray());

	}
	
	/**
	 * 本月未付明细
	 * @param page
	 * @param salaryData
	 * @return
	 */
	public Page<Map<String,Object>> findUnPaidEmpPageBySql(Page<Map<String,Object>> page, SalaryData salaryData) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" +
				" select e.Desc2 positionDescr,a.EmpCode, a.EmpName, b.IDNum, c.Desc2 dep1Descr, d.Desc2 dep2Descr,b.JoinDate,a.unPaidSalary " +
				" ,x.note statusDescr,a.LeaveDate" +
				" from (" +
				" 	select a.EmpCode, a.EmpName,sum(isnull(a.UnPaidSalary,0))unPaidSalary,a.SalaryMon,a.salaryScheme,a.LeaveDate  from tSalaryEmpHis a " +
				" 	left join tSalaryMon b on b.SalaryMon = a.SalaryMon" +
				" 	where 1=1 " ;
			if(salaryData.getSalaryScheme() != null){
				sql+=" and a.SalaryScheme = ? ";
				list.add(salaryData.getSalaryScheme());
			}
			if(salaryData.getSalaryMon() != null){
				sql+=" and a.SalaryMon = ? ";
				list.add(salaryData.getSalaryMon());
			}
			sql+=" 	group by a.EmpCode,a.EmpName,a.SalaryMon,a.salaryScheme,a.LeaveDate" +
				" ) a " +
				" left join tSalaryEmp b on b.EmpCode = a.EmpCode" +
				" left join tDepartment1 c on c.Code = b.Department1" +
				" left join tDepartment2 d on d.Code = b.Department2 " +
				" left join tPosition e on e.Code = b.Position  " +
				" left join tXTDM x on x.ID = 'SALARYSTATUS' and x.cbm = b.SalaryStatus " +
				" where a.UnPaidSalary <> 0" ;

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}
