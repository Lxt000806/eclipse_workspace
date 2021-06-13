package com.house.home.dao.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Repository
public class JcxdybbDao extends BaseDao {

	/**
	 * 集成下单月报表			
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
			call = conn.prepareCall("{Call pJcxdybb(?,?,?,?,?,?,?,?)}");
			call.setTimestamp(1, customer.getDateFrom() == null ? null : new Timestamp(
					customer.getDateFrom().getTime()));
			call.setTimestamp(2, customer.getDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(customer.getDateTo()).getTime()));
			call.setString(3, customer.getEmpCode());
			call.setString(4, customer.getDepartment1());
			call.setString(5, customer.getDepartment2());
			call.setString(6, customer.getDepartment3());
			call.setString(7, customer.getCustType());
			call.setString(8, customer.getAddress());
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
	
	public Page<Map<String,Object>> llmx_findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select b.No,b.ConfirmDate,d.Descr jccpName,c.ItemCode,g.Descr clName,c.Qty llQty, "
				+" round(e.UnitPrice*e.MarkUp*c.Qty/100*(case when b.Status='RETURN' then -1 else 1 end),0) jsje,e.Qty clxqQty,e.UnitPrice,e.MarkUp,e.LineAmount "
				+" from tItemapp b "
				+" inner join tItemAppDetail c on b.No=c.No "
				+" inner join tIntProd d on c.IntProdPk=d.PK "
				+" inner join tItemReq e on c.ReqPk= e.PK "
				+" left join tItem g on c.ItemCode=g.Code "
				+" where b.ConfirmDate>= ? and b.ConfirmDate<= ? "
				+" and b.ItemType1='JC' and b.Status in ('CONFIRMED','SEND','RETURN') "
				+" and b.CustCode=? and d.isCupBoard=? ";
		
		list.add(customer.getDateFrom());
		list.add(customer.getDateTo());
		list.add(customer.getCode());
		list.add(customer.getIsCupboard());
		return this.findPageBySql(page, sql, list.toArray());
	} 
	
	public Page<Map<String,Object>> zjmx_findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select f.No,f.Date,f.BefAmount,f.DiscAmount,f.Amount "
				+" from tItemchg f "
				+" where f.Date>= ? and f.Date<= ? "
				+" and f.ItemType1='JC' and f.Status='2' "
				+" and f.CustCode= ? and f.isCupBoard= ?";
		list.add(customer.getDateFrom());
		list.add(customer.getDateTo());
		list.add(customer.getCode());
		list.add(customer.getIsCupboard());
		return this.findPageBySql(page, sql, list.toArray());
	} 
}


	
