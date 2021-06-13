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
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.Customer;

@Repository
@SuppressWarnings("serial")
public class BusinessPlanResultAnalyDao extends BaseDao {
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pBusinessPlanResultAnaly(?,?,?,?,?,?,?,?,?,?,?)}");
			call.setTimestamp(1, customer.getDateFrom()==null?null:new Timestamp(customer.getDateFrom().getTime()));
			call.setTimestamp(2, customer.getDateTo()==null?null:new Timestamp(customer.getDateTo().getTime()));
			call.setString(3, customer.getPeriod());
			call.setString(4, customer.getStatistcsMethod());
			call.setString(5, customer.getEmpCode());
			call.setString(6, customer.getDepartment1());
			call.setString(7, customer.getDepartment2());
			call.setString(8, customer.getDepartment());
			call.setString(9, customer.getDepartment_emp());
			call.setString(10, customer.getPositionType());
			call.setString(11, customer.getDepType());
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
	
	public Page<Map<String,Object>> findBusinessStatistics(Page<Map<String,Object>> page, Customer customer) {
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pBusinessStatistics(?,?,?,?)}");
			call.setTimestamp(1, customer.getDateFrom()==null?null:new Timestamp(customer.getDateFrom().getTime()));
			call.setTimestamp(2, customer.getDateTo()==null?null:new Timestamp(customer.getDateTo().getTime()));
			call.setString(3, customer.getStatistcsMethod());
			call.setString(4, customer.getDepartment());
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
	
	public Page<Map<String,Object>> findBusinessDetail(Page<Map<String,Object>> page, Customer customer) {
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pBusinessPlanResultAnalyDetail(?,?,?,?,?,?,?,?,?,?)}");
			call.setTimestamp(1, customer.getDateFrom()==null?null:new Timestamp(customer.getDateFrom().getTime()));
			call.setTimestamp(2, customer.getDateTo()==null?null:new Timestamp(customer.getDateTo().getTime()));
			call.setString(3, customer.getSearchType());
			call.setString(4, customer.getEmpCode());
			call.setString(5, customer.getDepartment1());
			call.setString(6, customer.getDepartment2());
			call.setString(7, page.getPageOrderBy());
			call.setString(8, page.getPageOrder());
			call.setInt(9, page.getPageNo());
			call.setInt(10, page.getPageSize());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 
			
			//“新增资源客户”、“派单资源客户”、“联系客户”三个进行分页，从第一行记录中取总行数
			if(page.getResult().size()>0 && ("1".equals(customer.getSearchType()) || "7".equals(customer.getSearchType()) || "8".equals(customer.getSearchType()))){
				page.setTotalCount(Integer.parseInt(list.get(0).get("totalnum").toString()));
			}else{
				page.setTotalCount(page.getResult().size());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	}
}
