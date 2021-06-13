package com.house.home.dao.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.org.apache.bcel.internal.generic.Select;

@SuppressWarnings("serial")
@Repository
public class PrjMinusAnalyDao extends BaseDao{
	
	/*public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( ";
		if(StringUtils.isNotBlank(customer.getItemType1())){
			if("ZC".equals(customer.getItemType1())){
			sql+="select a.*,g.descr itemTypedescr,b.allCost,c.address,d.desc1 custTYpeDescr,e.nameChi projectMandescr,ed.desc1 prjDept2,f.nameCHI designManDescr," +
					" fd1.desc1 designDept1 ,fd.desc1 designDept2," +
					" case when a.PlanAmount =0 or a.PlanAmount is null then 1 else 1 - (b.AllCost/a.PlanAmount) end discPer from (" +
					" select a.Code custCode,e.Code itemType ,'' isCup,sum(b.LineAmount+b.ProcessCost) planAmount from tCustomer a" +
					" left join tItemPlan b on b.CustCode=a.Code" +
					" left join tItem c on c.Code=b.ItemCode" +
					" left join tItemType2 d on d.code=c.ItemType2" +
					" left join titemTYpe12 e on e.Code=d.ItemType12" +
					" where a.custCheckDate is not null and a.custCheckDate  between ? and ? " +
					" and b.ItemType1 ='ZC' " +
					" group by e.Code,a.Code" +
					" )a " +
					" left join (" +
					" select a.CustCode,e.Code itemType,sum(a.befLineAmount + a.ProcessCost + a.DiscCost) AllCost from tItemReq a" +
					" left join tCustomer b on b.Code=a.CustCode" +
					" left join tItem c on c.Code=a.ItemCode" +
					" left join tItemType2 d on d.Code=c.ItemType2" +
					" left join tItemType12 e on e.Code=d.ItemType12" +
					" where b.custCheckDate  is not null and b.custCheckDate  between ? and ?" +
					" and a.ItemType1 ='ZC'" +
					" group by a.CustCode,e.Code" +
					" ) b on b.CustCode = a.CustCode and b.itemType = a.itemType " +
					" left join tCustomer c on c.code = a.custCode " +
					" left join tCustType d on d.code = c.custType" +
					" left join tEmployee e on e.number = c.ProjectMan " +
					" left join tDepartment2 ed on ed.code = e.department2" +
					" left join tEmployee f on f.number = c.designMan " +
					" left join tDepartment1 fd1 on fd1.code = f.department1 " +
					" left join tDepartment2 fd on fd.code = f.department2 " +
					" left join tItemType12 g on g.code = a.ItemType " +
					" where case when a.PlanAmount =0 or a.PlanAmount is null then 1 else (b.AllCost/a.PlanAmount) end <0.3 ";
			}
			if("JC".equals(customer.getItemType1())){
				sql+="select a.*,b.allCost,c.address,d.desc1 custTYpeDescr,e.nameChi projectMandescr,ed.desc1 prjDept2,f.nameCHI designManDescr," +
						" fd1.desc1 designDept1 ,fd.desc1 designDept2 ,x1.note iscup, " +
						" case when a.PlanAmount =0 or a.PlanAmount is null then 1 else 1 - (b.AllCost/a.PlanAmount) end discPer" +
						" from (" +
						" select a.Code custCode,'' itemType,c.IsCupboard IsCupboard,sum(b.LineAmount+b.ProcessCost) planAmount from tCustomer a" +
						" left join tItemPlan b on b.CustCode=a.Code" +
						" left join tIntProd c on c.pk  = b.IntProdPK" +
						" where a.custCheckDate  is not null and a.custCheckDate  between ? and ? " +
						" and b.ItemType1 ='JC' " +
						" group by a.Code,c.IsCupboard" +
						" )a" +
						" left join (" +
						" select a.CustCode,'' itemtype,c.IsCupboard ,sum(a.befLineAmount + a.ProcessCost + a.DiscCost) AllCost from tItemReq a" +
						" left join tCustomer b on b.Code=a.CustCode" +
						" left join tIntProd c on c.pk = a.IntProdPK" +
						" where b.custCheckDate  is not null and b.custCheckDate  between ? and ? " +
						" and a.ItemType1 ='JC'" +
						" group by a.CustCode,c.IsCupboard" +
						" ) b on b.CustCode = a.CustCode and b.IsCupboard = a.IsCupBoard" +
						" left join tCustomer c on c.code = a.custCode " +
						" left join tCustType d on d.code = c.custType" +
						" left join tEmployee e on e.number = c.ProjectMan " +
						" left join tDepartment2 ed on ed.code = e.department2" +
						" left join tEmployee f on f.number = c.designMan " +
						" left join tDepartment1 fd1 on fd1.code = f.department1 " +
						" left join tDepartment2 fd on fd.code = f.department2 " +
						" left join tXtdm x1 on x1.cbm = a.IsCupboard and x1.id='YESNO'" +
						" where case when a.PlanAmount =0 or a.PlanAmount is null then 1 else (b.AllCost/a.PlanAmount) end <0.3";
			}
			list.add(customer.getDateFrom());
			list.add(customer.getDateTo());
			list.add(customer.getDateFrom());
			list.add(customer.getDateTo());
		} 
		if(StringUtils.isNotBlank(customer.getProjectMan())){
			sql+=" and c.ProjectMan = ? ";
			list.add(customer.getProjectMan());
		}
		if(StringUtils.isNotBlank(customer.getCustType())){
			sql+=" and c.custtype in ('"+customer.getCustType().replaceAll(",",	"','")+",')";
		}
		if(StringUtils.isNotBlank(customer.getDepartment1())){
			sql+=" and fd1.code = ? ";
			list.add(customer.getDepartment1());
		}
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql+=" and ed.Code = ? ";
			list.add(customer.getDepartment2());
		}
		if(StringUtils.isNotBlank(customer.getIsCupboard()) && "JC".equals(customer.getItemType1())){
			sql+=" and a.IsCupBoard  = ? ";
			list.add(customer.getIsCupboard());
		}
		if(StringUtils.isNotBlank(customer.getItemType12()) && "ZC".equals(customer.getItemType1())){
			sql+=" and a.itemType in ('"+customer.getItemType12().replaceAll(",","','")+",')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a order by custcode asc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}*/
	
	@SuppressWarnings("deprecation")
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pJsgdjxfx(?,?,?,?,?,?,?,?,?)}");//
			call.setTimestamp(1, new java.sql.Timestamp(customer.getDateFrom().getTime()));
			call.setTimestamp(2, new java.sql.Timestamp(customer.getDateTo().getTime()));
			call.setString(3, customer.getItemType1());
			call.setString(4, customer.getCustType());
			call.setString(5, customer.getShowType());
			call.setString(6, customer.getDepartment2());
			call.setString(7, customer.getDepartment1());
			call.setString(8, customer.getItemType12());
			call.setString(9, customer.getIsCupboard());
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
	/**
	 * 明细查看
	 * @author	created by zb
	 * @date	2019-9-11--上午11:15:38
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (";
		if ("ZC".equals(customer.getItemType1())) {
			sql += "select a.*,c.Area , g.descr itemTypedescr, b.allCost, c.address, d.desc1 custTYpeDescr, e.nameChi projectMandescr,  "
				  +"	ed.desc1 prjDept2, f.nameCHI designManDescr ,  fd1.desc1 designDept1 ,  fd.desc1 designDept2 , "
				  +"	case when a.PlanAmount = 0 or a.PlanAmount is null then 1 else 1 - ( b.AllCost / a.PlanAmount ) end discPer, "
				  +"	a.PlanAmount-b.AllCost discAmount "
				  +"from (  "
				  +"	select a.Code custCode, e.Code itemType, '' isCup,  sum(b.LineAmount+b.ProcessCost) planAmount  "
				  +"	from tCustomer a "
				  +"	left join tItemPlan b on b.CustCode = a.Code "
				  +"	left join tItem c on c.Code = b.ItemCode "
				  +"	left join tItemType2 d on d.code = c.ItemType2 "
				  +"	left join titemTYpe12 e on e.Code = d.ItemType12 "
				  +"	where a.custCheckDate is not null and a.custCheckDate between ? and ? and b.ItemType1 = 'ZC' "
				  +"	group by  e.Code, a.Code "
				  +") a "
				  +"left join (  "
				  +"	select a.CustCode, e.Code itemType, "
				  +"	sum(a.LineAmount + a.ProcessCost + a.DiscCost) AllCost "
				  +"	from tItemReq a "
				  +"	left join tCustomer b on b.Code = a.CustCode "
				  +"	left join tItem c on c.Code = a.ItemCode "
				  +"	left join tItemType2 d on d.Code = c.ItemType2 "
				  +"	left join tItemType12 e on e.Code = d.ItemType12 "
				  +"	where b.custCheckDate is not null and b.custCheckDate between ? and ? and a.ItemType1 = 'ZC' "
				  +"	group by a.CustCode, e.Code "
				  +") b on b.CustCode = a.CustCode and b.itemType = a.itemType "
				  +"left join tCustomer c on c.code = a.custCode "
				  +"left join tCustType d on d.code = c.custType "
				  +"left join tEmployee e on e.number = c.ProjectMan "
				  +"left join tDepartment2 ed on ed.code = e.department2 "
				  +"left join tEmployee f on f.number = c.designMan "
				  +"left join tDepartment1 fd1 on fd1.code = f.department1 "
				  +"left join tDepartment2 fd on fd.code = f.department2 "
				  +"left join tItemType12 g on g.code = a.ItemType "
				  +"where case when a.PlanAmount = 0 or a.PlanAmount is null then 1  else ( b.AllCost / a.PlanAmount ) end < 0.3  ";
		} else {
			sql += "select a.* ,c.Area, b.allCost , c.address , d.desc1 custTYpeDescr , e.nameChi projectMandescr , ed.desc1 prjDept2 , "
				  +"	f.nameCHI designManDescr , fd1.desc1 designDept1 ,  fd.desc1 designDept2 , x1.note iscup , "
				  +"	case when a.PlanAmount = 0 or a.PlanAmount is null then 1 else 1 - ( b.AllCost / a.PlanAmount )  end discPer "
				  +"	,a.PlanAmount-b.AllCost discAmount "
				  +"from (  "
				  +"	select a.Code custCode, '' itemType,  c.IsCupboard IsCupboard, sum(b.LineAmount+b.ProcessCost) planAmount "
				  +"	from tCustomer a "
				  +"	left join tItemPlan b on b.CustCode = a.Code "
				  +"	left join tIntProd c on c.pk = b.IntProdPK "
				  +"	where a.custCheckDate is not null and a.custCheckDate between ? and ?  "
				  +"	and b.ItemType1 = 'JC'  group by  a.Code, c.IsCupboard "
				  +") a "
				  +"left join (  "
				  +"	select a.CustCode, '' itemtype, c.IsCupboard, sum(a.LineAmount + a.ProcessCost + a.DiscCost) AllCost "
				  +"	from tItemReq a "
				  +"	left join tCustomer b on b.Code = a.CustCode "
				  +"	left join tIntProd c on c.pk = a.IntProdPK "
				  +"	where b.custCheckDate is not null and b.custCheckDate between ? and ?  "
				  +"	and a.ItemType1 = 'JC'  group by a.CustCode, c.IsCupboard "
				  +") b on b.CustCode = a.CustCode and b.IsCupboard = a.IsCupBoard "
				  +"left join tCustomer c on c.code = a.custCode "
				  +"left join tCustType d on d.code = c.custType "
				  +"left join tEmployee e on e.number = c.ProjectMan "
				  +"left join tDepartment2 ed on ed.code = e.department2 "
				  +"left join tEmployee f on f.number = c.designMan "
				  +"left join tDepartment1 fd1 on fd1.code = f.department1 "
				  +"left join tDepartment2 fd on fd.code = f.department2 "
				  +"left join tXtdm x1 on x1.cbm = a.IsCupboard and x1.id = 'YESNO' "
				  +"where case when a.PlanAmount = 0 or a.PlanAmount is null then 1 else ( b.AllCost / a.PlanAmount ) end < 0.3 ";
		}
		list.add(customer.getDateFrom());
		list.add(customer.getDateTo());
		list.add(customer.getDateFrom());
		list.add(customer.getDateTo());
		if (StringUtils.isNotBlank(customer.getDepartment2())) {
			sql += " and ed.code=? ";
			list.add(customer.getDepartment2());
		}
		if (StringUtils.isNotBlank(customer.getDepartment1())) {
			sql += " and fd1.code=? ";
			list.add(customer.getDepartment1());
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and c.CustType in ('"+customer.getCustType().replace(",", "','")+"') ";
		}
		if ("ZC".equals(customer.getItemType1())) {
			if (StringUtils.isNotBlank(customer.getItemType12())) {
				sql += " and g.Code in ('"+customer.getItemType12().replace(",", "','")+"') ";
			}
		} else {
			if (StringUtils.isNotBlank(customer.getIsCupboard())) {
				sql += " and b.IsCupboard=? ";
				list.add(customer.getIsCupboard());
			}
		}
		if ("1".equals(customer.getShowType())) {
			if (StringUtils.isNotBlank(customer.getDesignMan())) {
				sql += " and c.DesignMan=? ";
				list.add(customer.getDesignMan());
			} else {
				sql += " and (c.DesignMan is null or c.DesignMan='') ";
			}
		} else {
			if (StringUtils.isNotBlank(customer.getProjectMan())) {
				sql += " and c.ProjectMan=? ";
				list.add(customer.getProjectMan());
			} else {
				sql += " and (c.ProjectMan is null or c.ProjectMan='') ";
			}
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "  ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += "  ) a order by a.discAmount desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
}
