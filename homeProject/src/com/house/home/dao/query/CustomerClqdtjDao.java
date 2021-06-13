package com.house.home.dao.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
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

@SuppressWarnings("serial")
@Repository
public class CustomerClqdtjDao extends BaseDao {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {   
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pClqdtjMx(?,?,?,?,?,?,?,?,?)}");
			call.setInt(1, page.getPageNo());
			call.setInt(2,page.getPageSize());
			call.registerOutParameter(3, Types.INTEGER);
			call.setTimestamp(4, customer.getSignDateFrom() == null ? null : new Timestamp(
					customer.getSignDateFrom().getTime()));
			call.setTimestamp(5, customer.getSignDateTo() == null ? null : new Timestamp(
					customer.getSignDateTo().getTime()));
			call.setString(6, customer.getCustType());
			call.setTimestamp(7, customer.getCheckOutDateFrom() == null ? null : new Timestamp(
					customer.getCheckOutDateFrom().getTime()));
			call.setTimestamp(8, customer.getCheckOutDateTo() == null ? null : new Timestamp(
					customer.getCheckOutDateTo().getTime()));
			call.setString(9, customer.getIsOutSet()); //是否套餐外材料 add by zb
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 	
			page.setTotalCount(call.getInt(3));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 
}

