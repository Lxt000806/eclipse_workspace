package com.house.home.dao.salary;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.salary.SalaryData;

@SuppressWarnings("serial")
@Repository
public class PersonalSalaryQueryDao extends BaseDao{

	@SuppressWarnings("deprecation")
	public List<Map<String, Object>> getMainPageData(SalaryData salaryData) {
		
		List<Map<String, Object>> res = new ArrayList<Map<String,Object>>();
		
		Assert.notNull(salaryData);
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pRpt_Salary_Main(?,?,?,?,?,?,?,?,?,?)}");
			call.setInt(1, salaryData.getSalaryMon() == null ? 0 :salaryData.getSalaryMon());
			call.setInt(2, salaryData.getSalaryScheme() == null ? 0 :salaryData.getSalaryScheme());
			call.setString(3, salaryData.getDept1Code());
			call.setString(4, salaryData.getEmpStatus());
			call.setTimestamp(5, salaryData.getDateFrom() == null ? null
					: new Timestamp(salaryData.getDateFrom().getTime()));
			call.setTimestamp(6, salaryData.getDateTo() == null ? null
					: new Timestamp(salaryData.getDateTo().getTime()));
			call.setString(7,salaryData.getPositionClass());
			call.setString(8,salaryData.getEmpName());
			call.setString(9,"1");
			call.setString(10,"1");
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
	
	public List<Map<String, Object>> getSalaryScheme(SalaryData salaryData){
		if(salaryData.getSalaryMon() == null){
			return new ArrayList<Map<String,Object>>();
		}
		
		String sql = "select a.SalaryScheme, b.Descr from tSalaryStatusCtrl a " +
				" left join tSalaryScheme b on b.PK = a.SalaryScheme" +
				" where a.Status = '3' and a.SalaryMon = ? ";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{salaryData.getSalaryMon()});
		if(list != null && list.size()>0){
			return list;
		}
		
		return null;
	}
}
