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
import com.house.home.entity.salary.SalaryEmpDeduction;
import com.house.home.entity.salary.SalaryEmpOverTime;
import com.house.home.entity.salary.SalaryEmpPension;

@SuppressWarnings("serial")
@Repository
public class SalaryEmpOverTimeDao extends BaseDao {

	/**
	 * SalaryEmpOverTime分页信息
	 * 
	 * @param page
	 * @param salaryEmpOverTime
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmpOverTime salaryEmpOverTime) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( select a.SalaryEmp,c.EmpName,a.RegisterCZY,a.RegisterDate,a.Times,a.SalaryMon, "
				+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.Remarks,d.Status SalaryMonStatus,a.PK "
				+"from tSalaryEmpOverTime a  "
				+"left join tSalaryEmp c on a.SalaryEmp=c.EmpCode "
				+"left join tSalaryMon d on a.SalaryMon=d.SalaryMon " 
				+"where 1=1 ";

    	if (salaryEmpOverTime.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(salaryEmpOverTime.getPk());
		}
    	if(StringUtils.isNotBlank(salaryEmpOverTime.getQueryCondition())){
			sql+=" and ( c.EmpName like ? or c.EmpCode like ? or c.IDNum like ?)";
			list.add("%"+salaryEmpOverTime.getQueryCondition()+"%");
			list.add("%"+salaryEmpOverTime.getQueryCondition()+"%");
			list.add("%"+salaryEmpOverTime.getQueryCondition()+"%");
		}
    	if (salaryEmpOverTime.getSalaryMon() != null) {
			sql += " and a.SalaryMon=?";
			list.add(salaryEmpOverTime.getSalaryMon());
		}
    	if (StringUtils.isNotBlank(salaryEmpOverTime.getConSignCmp())) {
			sql += " and c.ConSignCmp=? ";
			list.add(salaryEmpOverTime.getConSignCmp());
		}
    	if (StringUtils.isNotBlank(salaryEmpOverTime.getPosiClass())) {
			sql += " and c.PosiClass=? ";
			list.add(salaryEmpOverTime.getPosiClass());
		}
    	if (StringUtils.isNotBlank(salaryEmpOverTime.getDepartment1())) {
			sql += " and c.Department1 in ("+SqlUtil.resetStatus(salaryEmpOverTime.getDepartment1())+")";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.RegisterDate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 是否存在重复月份
	 * 
	 * @return
	 */
	public List<Map<String, Object>> isExistsMon(SalaryEmpOverTime salaryEmpOverTime) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select 1 from tSalaryEmpOverTime where SalaryEmp=? and SalaryMon=?  ";
		list.add(salaryEmpOverTime.getSalaryEmp());
		list.add(salaryEmpOverTime.getSalaryMon());
		if(salaryEmpOverTime.getPk()!=null){
			sql+=" and PK<>? ";
			list.add(salaryEmpOverTime.getPk());
		}
		return this.findBySql(sql,list.toArray());
	}
	
	/**
	 * Excel导入调过程
	 * @param salaryEmpOverTime
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveBatch (SalaryEmpOverTime salaryEmpOverTime) {
		Assert.notNull(salaryEmpOverTime);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSalaryEmpOverTime_import(?,?,?,?,?)}");
			call.setString(1, salaryEmpOverTime.getLastUpdatedBy());
			call.setInt(2, salaryEmpOverTime.getSalaryMon());
			call.setString(3, salaryEmpOverTime.getDetailXml());
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
}

