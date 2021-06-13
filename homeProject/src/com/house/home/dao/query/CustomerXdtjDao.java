package com.house.home.dao.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Repository
public class CustomerXdtjDao extends BaseDao {		
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {
	                                                                                                        
		Assert.notNull(customer);

		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pXdtj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setInt(1, page.getPageNo());
			call.setInt(2,page.getPageSize());
			call.setString(3, customer.getStatistcsMethod());
			call.setString(4, customer.getRole());
			call.setString(5, customer.getDepartment1());
			call.setString(6, customer.getDepartment2());
			call.setString(7, customer.getDepartment3());
			call.setString(8, customer.getTeam());
			call.setString(9, customer.getCustType());
			if ("1".equalsIgnoreCase(customer.getStatistcsDateType())) {
				call.setTimestamp(10, customer.getDateFrom() == null ? null : new Timestamp(
						customer.getDateFrom().getTime()));
				call.setTimestamp(11, customer.getDateTo() == null ? null : new Timestamp(
						customer.getDateTo().getTime()));
				call.setTimestamp(12, null);
				call.setTimestamp(13, null);
			} else {
				call.setTimestamp(10, null);
				call.setTimestamp(11, null);
				call.setTimestamp(12, customer.getDateFrom() == null ? null : new Timestamp(
						customer.getDateFrom().getTime()));
				call.setTimestamp(13, customer.getDateTo() == null ? null : new Timestamp(
						customer.getDateTo().getTime()));
			}
			call.setString(14, customer.getBuilderCode());
			call.setString(15, customer.getRegion());
			call.setString(16, customer.getSource());
			call.setString(17, customer.getCzybh());
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

