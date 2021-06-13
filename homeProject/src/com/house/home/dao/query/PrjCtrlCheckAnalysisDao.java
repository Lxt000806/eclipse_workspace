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
public class PrjCtrlCheckAnalysisDao extends BaseDao {
	
	/**
	 * 工地发包结算分析列表信息
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
			call = conn.prepareCall("{Call pPrjCtrlCheckAnalysis(?,?,?,?,?)}");
			call.setTimestamp(1, customer.getConfirmDateFrom() == null ? null : new Timestamp(
					customer.getConfirmDateFrom().getTime()));
			call.setTimestamp(2, customer.getConfirmDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(customer.getConfirmDateTo()).getTime()));
			call.setString(3, customer.getCustType());
			call.setString(4, customer.getProjectMan());
			call.setString(5, customer.getContainOilPaint());
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

