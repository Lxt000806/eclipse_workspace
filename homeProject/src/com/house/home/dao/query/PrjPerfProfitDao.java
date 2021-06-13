package com.house.home.dao.query;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.Customer;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
@SuppressWarnings("serial")
@Repository
public class PrjPerfProfitDao extends BaseDao{

	/**
	 * 工地结算利润分析
	 * @author	created by zb
	 * @date	2019-1-10--下午4:35:26
	 * @param page
	 * @param customer
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPrjPerfProfit(?,?,?,?,?,?,?,?,?)}");
			call.setString(1, customer.getAddress().trim());
			call.setTimestamp(2, customer.getCustCheckDateFrom() == null ? null : new Timestamp(
					customer.getCustCheckDateFrom().getTime()));
			call.setTimestamp(3, customer.getCustCheckDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(customer.getCustCheckDateTo()).getTime()));
			call.setString(4, customer.getCustType());
			call.setString(5, customer.getConstructType());
			call.setString(6, customer.getCheckStatus());
			call.setString(7, customer.getIsGetDetail());
			call.setString(8, customer.getConstructStatus());
			call.setString(9, customer.getIsNotPrint());
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
