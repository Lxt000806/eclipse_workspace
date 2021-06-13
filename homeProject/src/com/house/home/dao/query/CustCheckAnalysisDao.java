package com.house.home.dao.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.Customer;
@Repository
@SuppressWarnings("serial")
public class CustCheckAnalysisDao extends BaseDao {
	public Page<Map<String,Object>> findPageBySql_pkhjsfx(Page<Map<String,Object>> page, Customer customer) {
		Assert.notNull(customer);

		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pkhjsfx(?,?,?,?,?,?,?,?,?)}");
			call.setTimestamp(1, customer.getDateFrom() == null ? null : new Timestamp(
					customer.getDateFrom().getTime()));
			call.setTimestamp(2, customer.getDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(customer.getDateTo()).getTime()));
			call.setString(3, customer.getCustType());
			call.setString(4, customer.getStatistcsMethod());
			call.setString(5, customer.getRole());
			call.setString(6, customer.getDepartment1());
			call.setString(7, customer.getDepartment2());
			call.setInt(8, page.getPageNo());
			call.setInt(9,page.getPageSize());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 			
			if (!list.isEmpty()) {
				page.setTotalCount((Integer)list.get(0).get("totalcount"));
			} else {
				page.setTotalCount(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 

	public Page<Map<String,Object>> goJqGridCheckDetail(Page<Map<String, Object>> page, Date dateFrom, Date dateTo, String role,
														String statistcsMethod, String department1, String department2, String custtype, String constructType, String isContainSoft){
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pkhjsfxMxCheck(?,?,?,?,?,?,?,?,?)}");
			call.setTimestamp(1, dateFrom == null ? null : new Timestamp(dateFrom.getTime()));
			call.setTimestamp(2, dateTo == null ? null : new Timestamp(DateUtil.endOfTheDay(dateTo).getTime()));
			call.setString(3, role);
			call.setString(4, statistcsMethod);
			call.setString(5, custtype);
			call.setString(6, department1);
			call.setString(7, department2);
			call.setString(8, constructType);
			call.setString(9, isContainSoft);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 			
			if (!list.isEmpty()) {
				page.setTotalCount((Integer)list.get(0).get("totalcount"));
			} else {
				page.setTotalCount(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	}

	public Page<Map<String,Object>> goJqGridReturnDetail(Page<Map<String, Object>> page, Date dateFrom, Date dateTo, String role, String statistcsMethod, String department1, 
														String department2, String custtype, String constructType, String isContainSoft, int returnFlag){
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pkhjsfxMxReturn(?,?,?,?,?,?,?,?,?,?)}");
			call.setTimestamp(1, dateFrom == null ? null : new Timestamp(dateFrom.getTime()));
			call.setTimestamp(2, dateTo == null ? null : new Timestamp(DateUtil.endOfTheDay(dateTo).getTime()));
			call.setString(3, role);
			call.setString(4, statistcsMethod);
			call.setString(5, custtype);
			call.setString(6, department1);
			call.setString(7, department2);
			call.setString(8, constructType);
			call.setString(9, isContainSoft);
			call.setInt(10, returnFlag);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 			
			if (!list.isEmpty()) {
				page.setTotalCount((Integer)list.get(0).get("totalcount"));
			} else {
				page.setTotalCount(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	}
}
