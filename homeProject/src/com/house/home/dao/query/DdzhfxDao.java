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
public class DdzhfxDao extends BaseDao {
	
	/**
	 * 
	 * @param bmxq  操作员部门权限
	 * @return
	 */
	public Map<String,Object> getbmqx(String czy){
		String sql="select CustRight from tCZYBM where czybh= ? ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{czy});
		if (list!=null && list.size()>0){
			 return list.get(0);
		}
		return null;
	}

	/**
	 * 订单转化分析
	 * @param page
	 * @param pDdzhfxBS
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Page<Map<String,Object>> findPageBySql_Ddtjfx_Tjfs(Page<Map<String,Object>> page, Customer customer,String orderBy,String direction) {
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pDdzhfxBS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");//pDdzhfxBS 订单转化分析
			call.setInt(1, page.getPageNo());
			call.setInt(2,page.getPageSize());
			call.setString(3, customer.getStatistcsMethod());
			call.setString(4, customer.getDepartment1());
			call.setString(5, customer.getDepartment2());
			call.setString(6, customer.getDepartment3());
			call.setString(7, customer.getCustType());
			call.setString(8, customer.getTeam());
			call.setTimestamp(9, new java.sql.Timestamp(customer.getBeginDate().getTime()));
			call.setTimestamp(10, new java.sql.Timestamp(customer.getEndDate().getTime()));	
			call.setString(11, customer.getMonthNum());
			call.setString(12, customer.getRole());
			call.setString(13, customer.getLastUpdatedBy());
			call.setString(14, orderBy);
			call.setString(15, direction);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 			
			if (!list.isEmpty()) {
				/*if ("1".equals(customer.getStatistcsMethod())){
					page.setTotalCount((Integer)list.get(0).get("totalcount"));
				}*/
			} else {
				page.setTotalCount(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 
}

