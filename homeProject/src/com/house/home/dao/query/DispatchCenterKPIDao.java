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
import com.house.home.entity.design.Customer;
@Repository
@SuppressWarnings("serial")
public class DispatchCenterKPIDao extends BaseDao {
	/*
	 * 列表查询
	 * @pa
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		Assert.notNull(customer);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pDispatchCenterKPI(?,?,?,?)}");
			call.setTimestamp(1, customer.getDateFrom() == null ? null : new Timestamp(
					customer.getDateFrom().getTime()));
			call.setTimestamp(2, customer.getDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(customer.getDateTo()).getTime()));
			call.setString(3, customer.getDepartment2());
			call.setString(4, customer.getStatistcsMethod());
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
	
	public Page<Map<String, Object>> findPageBySql_picConfirmDetail(
		Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select * from(select c.Address, a.ConfirmDate,a.SubmitDate,c.ToConstructDate,c.EndDate, " 
				  + " case when c.status='5' then '' else " 
				  + " 	case when case when c.ToConstructDate is not null  then "
				  + "	 case when c.ToConstructDate<a.SubmitDate then  datediff(dd,a.SubmitDate,a.ConfirmDate)  else  datediff(dd,c.ToConstructDate,a.ConfirmDate)  end "
				  + "	 else datediff(dd,a.SubmitDate,a.ConfirmDate) end <=3 then '是' else '否' end " 
				  + " end isonTime "
				  + " from  tDesignPicPrg a " 
				  + " left outer join tCustomer c  on c.code=a.CustCode "
				  + " left outer join tEmployee e on  a.ConfirmCZY=e.Number "
				  + " left outer join tEmployee e1 on e1.Number=c.projectman  " 
				  + " where a.Expired='F' and a.Status='4' and (a.SubmitDate is not null or a.SubmitDate<>'' )  and c.EndCode not in('3','4') " ;
		if(customer.getDateFrom()!=null){
			  sql+="and a.ConfirmDate>=? ";
			  list.add(customer.getDateFrom());
		}
		if(customer.getDateTo()!=null){
			 sql+="and a.ConfirmDate<=? ";
			 list.add(DateUtil.endOfTheDay(customer.getDateTo()));
		}
		if (StringUtils.isNotBlank(customer.getDepartment2())) {
			sql += " and e1.Department2 in" + "('"+(customer.getDepartment2().replaceAll(",", "','")+"')");
		}
		if (StringUtils.isNotBlank(customer.getEmpCode())) {
			sql += " and a.ConfirmCZY=? ";
			 list.add(customer.getEmpCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.ConfirmDate ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPageBySql_beginDetail(
			Page<Map<String, Object>> page, Customer customer) {
			List<Object> list = new ArrayList<Object>();
			String sql ="select * from(select a.Address,a.confirmbegin "
					+ " from  tCustomer a  "
                    + "   inner join tEmployee e on e.Number=a.projectman  "
                    + " where a.Expired='F' and a.EndCode<>'4'  "  ;
			if(customer.getDateFrom()!=null){
				  sql+="and a.confirmbegin>=? ";
				  list.add(customer.getDateFrom());
			}
			if(customer.getDateTo()!=null){
				 sql+="and a.confirmbegin<=? ";
				 list.add(DateUtil.endOfTheDay(customer.getDateTo()));
			}
			if (StringUtils.isNotBlank(customer.getDepartment2())) {
				sql += " and e.Department2 in" + "('"+(customer.getDepartment2().replaceAll(",", "','")+"')");
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " )a order by a.confirmbegin ";
			}
			return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPageBySql_checkDetail(
			Page<Map<String, Object>> page, Customer customer) {
			List<Object> list = new ArrayList<Object>();
			String sql ="select * from(select a.Address,a.CustCheckDate,isnull(RZCheckAmount,0)  RZCheckAmount "
					+ " from  tCustomer a  "
                    + " inner join tEmployee e on e.Number=a.projectman  "
					+ " left outer join ( select ir.CustCode, sum(ir.LineAmount) RZCheckAmount "
					+ " 	from tItemReq ir " 
					+ "     where  ir.ItemType1 ='RZ' and ir.Qty<>0 "
					+ "     group by ir.CustCode "
					+ " ) d on d.CustCode = a.Code " 
					+ " where a.Expired='F' and a.EndCode<>'4'  " 
                    + " and  a.ContainSoft=1  "
                    + " and exists(select 1 from tItemReq where SendQty<>0 and LineAmount<>0 and ItemType1='RZ' and CustCode=a.Code ) ";
			if(customer.getDateFrom()!=null){
				  sql+="and a.CustCheckDate>=? ";
				  list.add(customer.getDateFrom());
			}
			if(customer.getDateTo()!=null){
				 sql+="and a.CustCheckDate<=? ";
				 list.add(DateUtil.endOfTheDay(customer.getDateTo()));
			}
			if (StringUtils.isNotBlank(customer.getDepartment2())) {
				sql += " and e.Department2 in" + "('"+(customer.getDepartment2().replaceAll(",", "','")+"')");
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " )a order by a.CustCheckDate ";
			}
			return this.findPageBySql(page, sql, list.toArray());
	}
	public Page<Map<String, Object>> findPageBySql_specItemReqDetail(
			Page<Map<String, Object>> page, Customer customer) {
			List<Object> list = new ArrayList<Object>();
			String sql ="select * from(select a.Address,a.SpecItemReqDate "
					+ " from  tCustomer a  "
                    + " left join tEmployee e on e.Number=a.projectman  "
					+ " where a.Expired='F'   " ;

			if(customer.getDateFrom()!=null){
				  sql+="and a.SpecItemReqDate>=? ";
				  list.add(customer.getDateFrom());
			}
			if(customer.getDateTo()!=null){
				 sql+="and a.SpecItemReqDate<=? ";
				 list.add(DateUtil.endOfTheDay(customer.getDateTo()));
			}
			if (StringUtils.isNotBlank(customer.getDepartment2())) {
				sql += " and e.Department2 in" + "('"+(customer.getDepartment2().replaceAll(",", "','")+"')");
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " )a order by a.SpecItemReqDate ";
			}
			return this.findPageBySql(page, sql, list.toArray());
	}
	public Page<Map<String, Object>> findPageBySql_fixDutyDetail(
			Page<Map<String, Object>> page, Customer customer) {
			List<Object> list = new ArrayList<Object>();
			String sql ="select * from(select b.address,a.CfmDate,a.DutyDate, case when datediff(dd,a.CfmDate,a.DutyDate)<= 3 then '是' else '否' end isOnTime "
					   + " from tFixDuty a "
					   + " left join tCustomer b on a.CustCode=b.code  "
					   + " left outer join tEmployee e on e.Number=b.projectman "
					   + " where a.Expired='F'   " ;

			if(customer.getDateFrom()!=null){
				  sql+="and a.DutyDate>=? ";
				  list.add(customer.getDateFrom());
			}
			if(customer.getDateTo()!=null){
				 sql+="and a.DutyDate<=? ";
				 list.add(DateUtil.endOfTheDay(customer.getDateTo()));
			}
			if (StringUtils.isNotBlank(customer.getDepartment2())) {
				sql += " and e.Department2 in" + "('"+(customer.getDepartment2().replaceAll(",", "','")+"')");
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " )a order by a.DutyDate ";
			}
			return this.findPageBySql(page, sql, list.toArray());
	} 
	public Page<Map<String, Object>> findPageBySql_dispatchDetail(
			Page<Map<String, Object>> page, Customer customer) {
			List<Object> list = new ArrayList<Object>();
			String sql ="select * from(select a.address, "
					  + " case when ct.IsPartDecorate='1' then case when ContractFee>=80000 then 1 else 0.5 end else 1 end custCount,a.ContractFee, "
			 		  + " cwa.appdate,case when xc.QZ in ('01','03') then cwa.comedate else cwa2.comedate end comedate,a.ConfirmBegin,x1.NOTE IsPartDecorateDescr "
			 		  + " from tCustomer  a "
			 		  + " inner join tBuilder b on a.BuilderCode=b.Code "
			 		  + " inner join tRegion2 r2 on r2.Code=b.RegionCode2 "
					  + " inner join tPrjRegion c on c.Code=r2.PrjRegionCode "
					  + " inner join  tCusttype ct on ct.code=a.CustType "
					  + " left  join tCustWorker d on d.CustCode=a.code and d.WorkType12='01'  "
					  + " left  join tcustworkerapp cwa  on cwa.custworkpk=d.pk "
					  + " left join tCustWorker f on f.CustCode=a.code and f.WorkType12='02' "
					  + " left join tcustworkerapp cwa2 on cwa2.custworkpk=f.pk "
					  + " left  join tEmployee e on e.Number=a.projectman "
					  + " left join tXTCS xc on xc.id='CmpnyCode' "
					  + " left join tXTDM x1 on x1.id='YESNO' and X1.CBM=ct.IsPartDecorate "
					  + " where a.Expired='F' and ((ct.IsPartDecorate='1' and  a.ConfirmBegin>=? and  a.ConfirmBegin<=?) "
					  + " or (ct.IsPartDecorate='0' and  ((cwa.comedate>=? and cwa.comedate<=? and xc.QZ in('01','03') ) or (cwa2.comedate>=? and cwa2.comedate<=? and xc.QZ in('02','04') ) )) )";
			list.add(customer.getDateFrom());
			list.add(DateUtil.endOfTheDay(customer.getDateTo()));
			list.add(customer.getDateFrom());
			list.add(DateUtil.endOfTheDay(customer.getDateTo()));
			list.add(customer.getDateFrom());
			list.add(DateUtil.endOfTheDay(customer.getDateTo()));
			if (StringUtils.isNotBlank(customer.getPrjRegionCode())) {
				sql += " and r2.PrjRegionCode=? ";
				list.add(customer.getPrjRegionCode());
			}
			if (StringUtils.isNotBlank(customer.getDepartment2())) {
				sql += " and e.Department2 in" + "('"+(customer.getDepartment2().replaceAll(",", "','")+"')");
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " )a order by a.address ";
			}
			
			return this.findPageBySql(page, sql, list.toArray());
	} 
	public Page<Map<String, Object>> findPageBySql_mainCheckDetail	(
			Page<Map<String, Object>> page, Customer customer) {
			List<Object> list = new ArrayList<Object>();
			String sql ="select * from(select a.address,CustCheckDate, "
					  + " round(isnull(d.ZCOutSetSaleAmount,0)+isnull(f.ProjectAmount,0)+isnull(d.ServOutSetSaleAmount,0)/3,2) checkAmount "
					  +" from tCustomer  a "
					  +" inner join tBuilder b on a.BuilderCode=b.Code "
					  +" inner join tRegion2 r2 on r2.Code=b.RegionCode2 " 
					  +" inner join tPrjRegion c on c.Code=r2.PrjRegionCode "
					  +" left outer join tEmployee e on e.Number=a.projectman "
					  +" left join  ( "
					  +" select ir.CustCode, "
					  +" sum(case when  ir.IsService=0  then ir.LineAmount else 0 end )ZCOutSetSaleAmount, "
					  +" sum(case when  ir.IsService=1  then ir.LineAmount else 0 end )ServOutSetSaleAmount "
					  +" from tItemReq ir  "   
					  +" left join tItem i on ir.ItemCode=i.Code "
					  +" where ir.ItemType1='ZC' and IsOutSet='1' "
					  +" group by ir.CustCode "
					  +" ) d on d.CustCode=a.Code "
					  +" left join ( "
					  +"	select a.CustCode,sum(round(a.OtherCost+a.OtherCostAdj+a.ProjectOtherCost+case when a.Type='S' then round(a.ProjectAmount,2) "
					  +"	else round(a.ProjectAmount,2)*-1 end,2)) ProjectAmount "
					  +"	from tItemApp a "
					  +"	where a.Status <>'CANCEL' and a.IsSetItem='1' and a.ItemType1='ZC' "
					  +"	group by a.CustCode "
					  +")f on f.CustCode=a.Code "
					  + " where  a.Expired='F' and a.CustCheckDate>=? and a.CustCheckDate<=? " ;
			list.add(customer.getDateFrom());
			list.add(DateUtil.endOfTheDay(customer.getDateTo()));
			if (StringUtils.isNotBlank(customer.getPrjRegionCode())) {
				sql += " and r2.PrjRegionCode=? ";
				list.add(customer.getPrjRegionCode());
			}
			if (StringUtils.isNotBlank(customer.getDepartment2())) {
				sql += " and e.Department2 in" + "('"+(customer.getDepartment2().replaceAll(",", "','")+"')");
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " )a order by a.address ";
			}
			
			return this.findPageBySql(page, sql, list.toArray());
	} 
		
}
