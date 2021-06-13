package com.house.home.dao.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
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
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
@Repository
@SuppressWarnings("serial")
public class MainBusiManWorkloadDao extends BaseDao {
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		Assert.notNull(customer);

		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pMainBusiManWorkload(?,?,?,?)}");
			call.setTimestamp(1, customer.getDateFrom() == null ? null : new Timestamp(
					customer.getDateFrom().getTime()));
			call.setTimestamp(2, customer.getDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(customer.getDateTo()).getTime()));
			call.setString(3, customer.getCustType());
			call.setString(4,"F".equals(customer.getExpired())? "F":"F,T");
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 	
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 
	public Page<Map<String, Object>> findPageBySql_beginDetail(
		Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select * from( " 
				+" select  a.Address, a.Area, c.Desc1 custtypeDescr,cs.LastUpdate ConfirmBegin,d.ConfirmDate,"
				+" x1.note prjitemDescr "
				+" from tcuststakeholder cs "
				+" left join tCustomer a on cs.CustCode=a.Code "
				+" left join tCusttype c on c.Code = a.CustType "
				+" left join ( select  max(Pk) pk, Custcode from   tPrjProg a "
				+"                    where   a.Begindate = ( select max(Begindate) from tPrjProg where custcode = a.CustCode) "
				+"                    group by a.CustCode "
				+"                  ) pp on pp.Custcode = a.Code "
				+" left  join tPrjProg ppp on ppp.pk = pp.pk "
				+" left join tXTDM x1 on x1.CBM = ppp.prjitem and x1.ID = 'PRJITEM' "
				+" left join tCustItemConfDate d on d.CustCode=a.Code and ItemTimeCode='01'"
				+" where cs.role='34' and cs.Expired='F' and cs.EmpCode=? ";
				list.add(customer.getEmpCode());
				//+" where exists(select 1 from tCustItemConfProg w_c where w_c.CustCode=a.Code) ";       
		if(customer.getDateFrom()!=null){
			sql+="and cs.LastUpdate>=? ";
			list.add(customer.getDateFrom());
		}
		if(customer.getDateTo()!=null){
			sql+="and cs.LastUpdate<=? ";
			list.add(DateUtil.endOfTheDay(customer.getDateTo()));
		}
		/*
		if (StringUtils.isNotBlank(customer.getEmpCode())){
			sql += " and exists(select 1 from tcuststakeholder cs" 
				 + " where cs.CustCode=a.Code and cs.role='34' and cs.Expired='F' and cs.EmpCode=? ) ";
			list.add(customer.getEmpCode());
		}*/
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and a.CustType in" + "('"+(customer.getCustType().replaceAll(",", "','")+"')");
		}	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.ConfirmBegin";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPageBySql_buildingDetail(
		Page<Map<String, Object>> page, Customer customer) {
			List<Object> list = new ArrayList<Object>();
			String sql ="select * from( " 
						+"select  a.Address, a.Area, c.Desc1 custtypeDescr, ConfirmBegin, "
						+" x1.note prjitemDescr,o.Descr confItemdescr,isnull(ch.ChgNum,0)ChgNum "
						+" from  tCustomer a "
			            +" left join tCusttype c on c.Code = a.CustType "
			            +" left join ( select  max(Pk) pk, Custcode from tPrjProg a "
			            +"                where   a.Begindate = ( select max(Begindate) from tPrjProg where custcode = a.CustCode) "
			            +"               group by a.CustCode "
			            +"              ) pp on pp.Custcode = a.Code "
			            +" left  join tPrjProg ppp on ppp.pk = pp.pk "
			            +" left join tXTDM x1 on x1.CBM = ppp.prjitem and x1.ID ='PRJITEM' "
			            +" left  join ( select min(b.Code) code, a.Code CustCode "
			            +"                 from   dbo.tCustomer a "
			            +"                        left  join dbo.tConfItemTime b on 1 =1 "
			            +"                        left join tCustItemConfDate c on c.ItemTimeCode = b.Code and c.CustCode = a.Code "
			            +"                 where  a.status = '4' and c.CustCode is  null "
			            +"                 group by a.Code " 
			            +"               ) oo on oo.CustCode = a.code "
			            +" left join tConfItemTime o on o.code = oo.code " 
			            +" left join(select CustCode, count(1)ChgNum from tItemChg "
			        	+"		group by CustCode "
			            +" )ch on ch.CustCode=a.code "
			            +" where a.status='4' and  exists(select 1 from tCustItemConfProg w_c where w_c.CustCode=a.Code) "
			            +" and a.EndCode not in('3','4','6') " ;       

			if (StringUtils.isNotBlank(customer.getCustType())) {
				sql += " and a.CustType in" + "('"+(customer.getCustType().replaceAll(",", "','")+"')");
			}
			if (StringUtils.isNotBlank(customer.getEmpCode())){
				sql += " and exists(select 1 from tcuststakeholder cs" 
					+ " where cs.CustCode=a.Code and cs.role='34' and cs.Expired='F' and cs.EmpCode=? ) ";
				list.add(customer.getEmpCode());
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " )a order by a.ConfirmBegin";
			}
			return this.findPageBySql(page, sql, list.toArray());
	}
	public Page<Map<String, Object>> findPageBySql_completedDetail(
		Page<Map<String, Object>> page, Customer customer) {
			List<Object> list = new ArrayList<Object>();
			String sql ="select * from( " 
					+ " select a.Address,a.Area,c.Desc1 custtypeDescr,ConfirmBegin ,EndDate "
					+ " from tCustomer a  "
					+ " left join tCusttype c on c.Code=a.CustType "
					+ " where a.EndCode='3'  and exists(select 1 from tCustItemConfProg w_c where w_c.CustCode=a.Code) ";
			if(customer.getDateFrom()!=null){
				 sql+="and a.EndDate>=? ";
				 list.add(customer.getDateFrom());
			}
			if(customer.getDateTo()!=null){
				 sql+="and a.EndDate<=? ";
				 list.add(DateUtil.endOfTheDay(customer.getDateTo()));
			}
			if (StringUtils.isNotBlank(customer.getCustType())) {
					sql += " and a.CustType in" + "('"+(customer.getCustType().replaceAll(",", "','")+"')");
			}
			if (StringUtils.isNotBlank(customer.getEmpCode())){
				sql += " and exists(select 1 from tcuststakeholder cs" 
					+ " where cs.CustCode=a.Code and cs.role='34' and cs.Expired='F' and cs.EmpCode=? ) ";
				 list.add(customer.getEmpCode());
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " )a order by a.ConfirmBegin";
			}
			return this.findPageBySql(page, sql, list.toArray());
	}
	public Page<Map<String, Object>> findPageBySql_firstConfirmDetail(
			Page<Map<String, Object>> page, Customer customer) {
			List<Object> list = new ArrayList<Object>();
			String sql ="select * from( " 
					+ " select b.Address,b.Area,c.Desc1 custtypeDescr, a.ConfirmDate,tpp.ConfirmDate enddate , "
					+ " case when a.ConfirmDate is not null and (tpp.ConfirmDate is null or a.ConfirmDate <= tpp.ConfirmDate) then '是' else '否' end isOnTime "
					+ " from tCustItemConfDate a "
					+ " inner join tCustomer b on a.CustCode=b.Code "
					+ " left join tCusttype c on c.Code=b.CustType "
					+ " left join tPrjProg tpp on a.CustCode = tpp.CustCode and exists(select 1 from tConfItemTime where endPrjItem=tpp.PrjItem and Code='01') "
					+ " where a.ItemTimeCode='01' ";
			if(customer.getDateFrom()!=null){
				 sql+="and a.ConfirmDate>=? ";
				 list.add(customer.getDateFrom());
			}
			if(customer.getDateTo()!=null){
				 sql+="and a.ConfirmDate<=? ";
				 list.add(DateUtil.endOfTheDay(customer.getDateTo()));
			}
			if (StringUtils.isNotBlank(customer.getCustType())) {
				sql += " and b.CustType in" + "('"+(customer.getCustType().replaceAll(",", "','")+"')");
			}
			if (StringUtils.isNotBlank(customer.getEmpCode())){
				sql += " and exists(select 1 from tcuststakeholder cs" 
					+ " where cs.CustCode=a.CustCode and cs.role='34' and cs.Expired='F' and cs.EmpCode=? ) ";
				 list.add(customer.getEmpCode());
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " )a order by a.ConfirmDate";
			}
			return this.findPageBySql(page, sql, list.toArray());
		}
	public Page<Map<String, Object>> findPageBySql_secondConfirmDetail(
		Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select * from( " 
				  + " select b.Address,b.Area,c.Desc1 custtypeDescr , a.ConfirmDate,tpp.ConfirmDate EndDate,"
				  + " case when a.ConfirmDate is not null and (tpp.ConfirmDate is null or a.ConfirmDate <= tpp.ConfirmDate) then '是' else '否' end isOnTime "
				  + " from tCustItemConfDate a "
				  + " inner join tCustomer b on a.CustCode=b.Code "
				  + " left join tCusttype c on c.Code=b.CustType "
				  + " left join tPrjProg tpp on a.CustCode = tpp.CustCode and exists(select 1 from tConfItemTime where endPrjItem=tpp.PrjItem and Code='02') "
				  + " where a.ItemTimeCode='02' ";
		if(customer.getDateFrom()!=null){
			 sql+="and a.ConfirmDate>=? ";
			list.add(customer.getDateFrom());
		}
		if(customer.getDateTo()!=null){
			sql+="and a.ConfirmDate<=? ";
			list.add(DateUtil.endOfTheDay(customer.getDateTo()));
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and b.CustType in" + "('"+(customer.getCustType().replaceAll(",", "','")+"')");
		}	
		if (StringUtils.isNotBlank(customer.getEmpCode())){
			sql += " and exists(select 1 from tcuststakeholder cs" 
				+ " where cs.CustCode=a.CustCode and cs.role='34' and cs.Expired='F' and cs.EmpCode=? ) ";
			 list.add(customer.getEmpCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.ConfirmDate";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	public Page<Map<String, Object>> findPageBySql_chgDetail(
		Page<Map<String, Object>> page, Customer customer) {
			List<Object> list = new ArrayList<Object>();
			String sql ="select * from( " 
					  + " select b.Address,b.Area,c.Desc1 custtypeDescr ,a.no,a.confirmdate,a.Amount "
					  + " from tItemChg a " 
					  + " inner join tCustomer b on a.CustCode=b.Code "
					  + " left join tCusttype c on c.Code=b.CustType " 
					  + " where a.ItemType1='ZC' ";
		if(customer.getDateFrom()!=null){
			  sql+="and a.confirmdate>=? ";
			  list.add(customer.getDateFrom());
		}
		if(customer.getDateTo()!=null){
			  sql+="and a.confirmdate<=? ";
			  list.add(DateUtil.endOfTheDay(customer.getDateTo()));
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and b.CustType in" + "('"+(customer.getCustType().replaceAll(",", "','")+"')");
		}
		if (StringUtils.isNotBlank(customer.getEmpCode())){
			sql += " and exists(select 1 from tcuststakeholder cs" 
				+ " where cs.CustCode=b.Code and cs.role='34' and cs.Expired='F' and cs.EmpCode=? ) ";
			 list.add(customer.getEmpCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.Address";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	
}
