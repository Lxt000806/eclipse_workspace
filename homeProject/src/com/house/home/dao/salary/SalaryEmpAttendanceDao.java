package com.house.home.dao.salary;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.salary.SalaryData;
import com.house.home.entity.salary.SalaryEmpAttendance;
import com.house.home.entity.salary.SalaryEmpDeduction;
import com.house.home.entity.salary.SalaryEmpOverTime;

@SuppressWarnings("serial")
@Repository
public class SalaryEmpAttendanceDao extends BaseDao {

	/**
	 * SalaryEmpAttendance分页信息
	 * 
	 * @param page
	 * @param salaryEmpAttendance
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmpAttendance salaryEmpAttendance) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.SalaryEmp,b.EmpName,b.IDNum,a.SalaryMon,  "
					+"a.AbsentDays,a.AbsentTimes,a.ImportCZY,a.ImportDate,  "
					+"a.LateTimes,a.LeaveDays,a.LeaveEarlyTimes,a.SeriousLateTimes,  "
					+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.PK,a.LateOverHourTimes  "
					+"from tSalaryEmpAttendance a   "
					+"left join tSalaryEmp b on a.SalaryEmp=b.EmpCode  "
					+"where 1=1 ";

    	if (salaryEmpAttendance.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(salaryEmpAttendance.getPk());
		}
    	if(StringUtils.isNotBlank(salaryEmpAttendance.getQueryCondition())){
			sql+=" and ( b.EmpName like ? or b.EmpCode like ? or b.IDNum like ?)";
			list.add("%"+salaryEmpAttendance.getQueryCondition()+"%");
			list.add("%"+salaryEmpAttendance.getQueryCondition()+"%");
			list.add("%"+salaryEmpAttendance.getQueryCondition()+"%");
		}
    	if (salaryEmpAttendance.getSalaryMon() != null) {
			sql += " and a.SalaryMon=?";
			list.add(salaryEmpAttendance.getSalaryMon());
		}
    	if (StringUtils.isNotBlank(salaryEmpAttendance.getConSignCmp())) {
			sql += " and b.ConSignCmp=? ";
			list.add(salaryEmpAttendance.getConSignCmp());
		}
    	if (StringUtils.isNotBlank(salaryEmpAttendance.getPosiClass())) {
			sql += " and b.PosiClass=? ";
			list.add(salaryEmpAttendance.getPosiClass());
		}
    	if (StringUtils.isNotBlank(salaryEmpAttendance.getDepartment1())) {
			sql += " and b.Department1 in ("+SqlUtil.resetStatus(salaryEmpAttendance.getDepartment1())+")";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.SalaryEmp asc,a.ImportDate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 批量删除
	 * 
	 * @param salaryMon
	 */
	public Long doDeleteBatch(Integer salaryMon) {
		String sql = " delete from tSalaryEmpAttendance where SalaryMon=? ";
		return this.executeUpdateBySql(sql, new Object[] {salaryMon});
	}
	
	/**
	 * 是否存在重复月份
	 * 
	 * @return
	 */
	public List<Map<String, Object>> isExistsMon(SalaryEmpAttendance salaryEmpAttendance) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select 1 from tSalaryEmpAttendance where SalaryEmp=? and SalaryMon=?  ";
		list.add(salaryEmpAttendance.getSalaryEmp());
		list.add(salaryEmpAttendance.getSalaryMon());
		if(salaryEmpAttendance.getPk()!=null){
			sql+=" and PK<>? ";
			list.add(salaryEmpAttendance.getPk());
		}
		return this.findBySql(sql,list.toArray());
	}
	
	/**
	 * Excel导入调过程
	 * @param salaryEmpAttendance
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveBatch (SalaryEmpAttendance salaryEmpAttendance) {
		Assert.notNull(salaryEmpAttendance);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSalaryEmpAttendance_import(?,?,?,?,?)}");
			call.setString(1, salaryEmpAttendance.getLastUpdatedBy());
			call.setInt(2, salaryEmpAttendance.getSalaryMon());
			call.setString(3, salaryEmpAttendance.getDetailXml());
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Integer getDefSalaryMon(){
		
		String sql = "select min(salaryMon)SalaryMon from tSalaryMon a " +
				"where a.Expired='F' and a.Status<>'3'" +
				"and not exists(select 1 from tSalaryStatusCtrl in_a where in_a.SalaryMon=a.SalaryMon and in_a.status = '3') ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{});
		if(list != null && list.size()>0){
			return Integer.parseInt(list.get(0).get("SalaryMon").toString());
		}
		
		return null;
	}
}

