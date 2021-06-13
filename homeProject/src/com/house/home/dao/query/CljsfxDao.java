package com.house.home.dao.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
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
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.Customer;

@Repository
@SuppressWarnings("serial")
public class CljsfxDao extends BaseDao{
	
	/**
	 * 材料结算明细—查看
	 * */
	public Page<Map<String, Object>> findPageBySqlDetail(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql =  " select c.Code CustCode,c.Descr CustDescr,c.Address,d1.Code Dept1Code,d1.Desc2 Dept1Descr, " +
				" round((1.0/num),2) CustCount,round((Area*1.0/num),2) Area, round((isnull(d.LineAmount,0)/num),2) LineAmount," +
				" round((isnull(d.AllCost,0)/num),2) AllCost, round((isnull(d.LineAmount,0)/num)-(isnull(d.AllCost,0)/num),2) Profit  " +
				" from tCustomer c " +
				" inner join tCustStakeholder cs on c.Code=cs.CustCode inner join ( 	select CustCode, Role, count(1) num from tCustStakeholder 	" +
				" where Expired='F' and Role='00' group by CustCode, Role ) cs2 on cs2.CustCode = cs.CustCode and cs2.Role = cs.Role " +
				" inner join tEmployee e on e.Number=cs.EmpCode inner join tDepartment1 d1 on d1.Code=e.Department1 " +
				" inner join tItemType1 it1 on it1.Code='"+customer.getItemType1() +"'"+
				" inner join (   select ir.CustCode,sum(case when ir.IsOutSet='1' then ir.LineAmount else round(round(ir.Qty*isnull(iad.ProjectCost,i.ProjectCost)*ir.Markup/100,0)+ir.ProcessCost,0) end) LineAmount,sum(ir.Qty*ir.Cost+ir.ProcessCost+ir.DiscCost) AllCost  	" +
				" from tItemReq ir left join tItemAppDetail iad on ir.PK=iad.ReqPk inner join tItem i on ir.ItemCode=i.Code where ir.IsService=0 and ir.ItemType1='"+customer.getItemType1()+"' and ir.Qty<>0  	group by ir.CustCode ) d on d.CustCode=c.Code " +
				" where 1=1 ";
		if (StringUtils.isNotBlank(customer.getDepartment1())) {
			sql += " and d1.Code=? ";	
			list.add(customer.getDepartment1());
		}		
		if (!"".equals(customer.getCustCheckDateFrom())){
			sql += " and c.CustCheckDate>= ? ";	
			list.add(customer.getCustCheckDateFrom());			
		}
		if (!"".equals(customer.getCustCheckDateTo())){
			sql += " and c.CustCheckDate< dateadd(day,1,?) ";	
			list.add(customer.getCustCheckDateTo());			
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and c.CustType in " + "('"+customer.getCustType().replace(",", "','" )+ "')";
		}
		if (StringUtils.isNotBlank(customer.getDepartment2())) {
			sql += " and e.Department2= ? ";	
			list.add(customer.getDepartment2());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by c.CustCheckDate";
		}								  
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 材料结算明细
	 * */	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCljsfx(?,?,?,?,?)}");
			call.setString(1, customer.getStatistcsMethod());
			call.setString(2, customer.getItemType1());
			call.setTimestamp(3, new java.sql.Timestamp(customer.getDateFrom().getTime()));
			call.setTimestamp(4, new java.sql.Timestamp(customer.getDateTo().getTime()));
			call.setString(5, customer.getCustType());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 			
			if (!list.isEmpty()) {
				/*if ("1".equals(prjJob.getStatistcsMethod())) {
					 page.setTotalCount((Integer)list.get(0).get("totalcount"));
					}*/
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
