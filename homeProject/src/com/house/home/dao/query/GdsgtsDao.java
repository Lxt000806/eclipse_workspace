package com.house.home.dao.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import sun.net.www.content.audio.x_aiff;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.Customer;

@Repository
@SuppressWarnings("serial")
public class GdsgtsDao extends BaseDao{
	
	@SuppressWarnings("deprecation")
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGdsgtsfx(?,?,?,?,?,?,?)}");//pBzsgfx 班组施工分析
			call.setTimestamp(1, new java.sql.Timestamp(customer.getDateFrom().getTime()));
			call.setTimestamp(2, new java.sql.Timestamp(customer.getDateTo().getTime()));
			call.setString(3, customer.getDepartment2());
			call.setString(4, customer.getProjectMan());
			call.setString(5, customer.getCustType());
			call.setInt(6, customer.getDaysFrom() == null ? 0:customer.getDaysFrom());
			call.setInt(7, customer.getDaysTo() == null ? 99999:customer.getDaysTo());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 			
			page.setTotalCount(list.size());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	}
	
}
