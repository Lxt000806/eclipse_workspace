package com.house.home.dao.commi;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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
import com.house.home.entity.basic.Employee;

@SuppressWarnings("serial")
@Repository
public class PersonalCommiQueryDao extends BaseDao{

	@SuppressWarnings("deprecation")
	public List<Map<String, Object>> getMainPageData(Employee employee) {
		
		List<Map<String, Object>> res = new ArrayList<Map<String,Object>>();
		
		Assert.notNull(employee);
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pMyCommi(?,?)}");
			call.setInt(1, employee.getMon());
			call.setString(2, employee.getNumber());
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
	
}
