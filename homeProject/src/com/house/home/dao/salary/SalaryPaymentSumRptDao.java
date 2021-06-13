package com.house.home.dao.salary;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.salary.SalaryPayment;

@SuppressWarnings("serial")
@Repository
public class SalaryPaymentSumRptDao extends BaseDao {
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SalaryPayment salaryPayment) {	
		List<Object> list = new ArrayList<Object>();
 
		String sql = " select * from (" +
				" select a.pk,b.EmpCode,b.EmpName,c.Desc2 dept1Descr,d.Desc2 dept2Descr,e.Descr positionClassDescr,b.JoinDate,b.LeaveDate," +
				" f.Descr,b.BasicSalary ,a.lastUpdate " +
				" from tSalaryData a " +
				" left join tSalaryEmp b on b.EmpCode = a.SalaryEmp" +
				" left join tDepartment1 c on c.Code = b.Department1" +
				" left join tDepartment2 d on d.Code = b.Department2" +
				" left join tSalaryPosiClass e on e.PK = b.PosiClass" +
				" left join tSalaryMon f on f.SalaryMon = a.SalaryMon " +
				" where 1=1" ;

		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public List<Map<String, Object>> getMainPageData(SalaryPayment salaryPayment) {
		
		List<Map<String, Object>> res = new ArrayList<Map<String,Object>>();
		
		Assert.notNull(salaryPayment);
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pRpt_Salary_Payment_Sum(?,?,?,?,?,?,?)}");
			call.setInt(1, salaryPayment.getSalaryMon() == null ? 0:salaryPayment.getSalaryMon());
			call.setString(2, salaryPayment.getSignCmpCode());
			call.setInt(3, salaryPayment.getPaymentDef() ==null ? 0:salaryPayment.getPaymentDef());
			call.setString(4, salaryPayment.getDepartment1());
			call.setInt(5, salaryPayment.getSalaryScheme());
			call.setString(6, salaryPayment.getDeptType());
			call.setString(7, salaryPayment.getBelongType());
			call.execute();
			ResultSet rs = call.getResultSet();
			res=BeanConvertUtil.resultSetToList(rs);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		
		return res;
	}
	
	public List<Map<String, Object>> getPaymentDefList(SalaryPayment salaryPayment){
		
		String sql = "Select * from tSalaryPaymentDefHis a where a.SalaryMon = ? ";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{salaryPayment.getSalaryMon()});
		if(list != null && list.size()>0){
	
			return list;
		}
		
		return null;
	}
}
