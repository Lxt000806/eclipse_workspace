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
public class SoftCostQueryDao extends BaseDao {
	
	/**
	 * 获取材料类型12名称列表
	 * @param  
	 * @return
	 */
	public List<Map<String,Object>> getItemType12Descr(){
		String sql="select Descr from tItemType12 where ItemType1='RZ' and Expired='F' order by DispSeq desc ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{});
		if (list!=null && list.size()>0){
			 return list;
		}
		return null;
	}
	
	/**
	 * 软装收支明细查询			
	 * @param page
	 * @param customer
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSoftCostQuery(?,?,?,?)}");
			call.setTimestamp(1, customer.getCustCheckDateFrom() == null ? null : new Timestamp(
					customer.getCustCheckDateFrom().getTime()));
			call.setTimestamp(2, customer.getCustCheckDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(customer.getCustCheckDateTo()).getTime()));
			call.setString(3, customer.getCustType());
			call.setString(4, customer.getAddress());
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


	
