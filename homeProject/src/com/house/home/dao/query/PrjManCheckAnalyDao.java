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


@SuppressWarnings("serial")
@Repository
public class PrjManCheckAnalyDao extends BaseDao {
	
	/**
	 * 项目经理结算分析列表信息
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPrjManCheckAnaly(?,?,?,?,?,?)}");
			call.setTimestamp(1, customer.getCustCheckDateFrom() == null ? null : new Timestamp(
					customer.getCustCheckDateFrom().getTime()));
			call.setTimestamp(2, customer.getCustCheckDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(customer.getCustCheckDateTo()).getTime()));
			call.setString(3, customer.getCustType());
			call.setString(4, customer.getDepartment2());
			call.setString(5, customer.getContainOilPaint());
			call.setString(6, customer.getInstallElev());
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
	/**
	 * 项目经理结算分析明细列表信息
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String,Object>> findDetailBySql(Page<Map<String,Object>> page, Customer customer) {
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPrjManCheckAnalyDetail(?,?,?,?,?,?,?)}");
			call.setTimestamp(1, customer.getCustCheckDateFrom() == null ? null : new Timestamp(
					customer.getCustCheckDateFrom().getTime()));
			call.setTimestamp(2, customer.getCustCheckDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(customer.getCustCheckDateTo()).getTime()));
			call.setString(3, customer.getCustType());
			call.setString(4, customer.getDepartment2());
			call.setString(5, customer.getContainOilPaint());
			call.setString(6, customer.getProjectMan());
			call.setString(7, customer.getInstallElev());
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

