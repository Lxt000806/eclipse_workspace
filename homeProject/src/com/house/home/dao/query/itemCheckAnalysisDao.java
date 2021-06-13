package com.house.home.dao.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
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
public class itemCheckAnalysisDao extends BaseDao {
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pClwgfx(?,?,?,?,?,?,?,?)}");
			call.setString(1, customer.getItemType1());
			call.setString(2, customer.getStatistcsMethod());
			call.setTimestamp(3, customer.getDateFrom() == null ? null : new Timestamp(
					customer.getDateFrom().getTime()));
			call.setTimestamp(4, customer.getDateTo() == null ? null : new Timestamp(
					customer.getDateTo().getTime()));
			call.setString(5, customer.getIsServiceItem());
			call.setString(6, customer.getItemType12());
			call.setString(7, customer.getCustType());
			call.setString(8, customer.getAddress());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 	
			page.setTotalCount(page.getResult().size());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 
	public Page<Map<String,Object>> findPageBySql_detail(Page<Map<String,Object>> page, Customer customer) {
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pItemPlanMx(?,?,?,?,?,?,?,?)}");
			call.setString(1, customer.getItemType1());
			call.setString(2, customer.getCustType());
			call.setTimestamp(3, customer.getDateFrom() == null ? null : new Timestamp(
					customer.getDateFrom().getTime()));
			call.setTimestamp(4, customer.getDateTo() == null ? null : new Timestamp(
					customer.getDateTo().getTime()));
			call.setInt(5, "1".equals(customer.getStatistcsMethod())? 1 :0);
			call.setInt(6, "2".equals(customer.getStatistcsMethod())? 1 :0);
			call.setInt(7,customer.getSignMonth());
			call.setString(8, customer.getDepartment1());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 	
			page.setTotalCount(page.getResult().size());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 
}
