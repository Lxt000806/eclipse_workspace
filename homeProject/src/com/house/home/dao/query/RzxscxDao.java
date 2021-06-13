package com.house.home.dao.query;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.insales.ItemPlanLog;
import com.house.home.entity.project.ItemCheck;
import com.house.home.entity.project.ItemChg;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Repository
@SuppressWarnings("serial")
public class RzxscxDao extends BaseDao {
	
	/**
	 * 按材料类型2——按增减预算
	 * rbZj.Checked
	 * @param page
	 * @param itemChg
	 * @return
	 */
	public Page<Map<String,Object>> findRbZjPageBy_itemType_Sql(Page<Map<String,Object>> page, ItemChg itemChg) {
		List<Object> list = new ArrayList<Object>();                                                                                                       
		
		String sql = "" ;
		
		String chgSql="select c.ItemType2,d.Descr ItemType2Descr,sum(case when b.IsOutSet='1' then b.LineAmount else round(round(b.Qty*c.ProjectCost*b.Markup/100,0)+b.ProcessCost,0) end) xse," +
				" sum(b.Qty*b.Cost) Cost"
	            + " , sum(case when b.IsOutSet = '1' then b.LineAmount else round(round(b.Qty * c.ProjectCost * b.Markup / 100, 0)+ b.ProcessCost, 0)end) chgAmount,0 planAmount"
            + " from tItemChg a "
            + " inner join tItemChgDetail b on a.No=b.No "
            + " inner join tItem c on b.ItemCode=c.Code "
            + " inner join tItemType2 d on c.ItemType2=d.Code "
            + " left join tCustomer e on a.CustCode=e.Code "
            + " left join tEmployee e1 on c.Buyer1=e1.Number "
            + " left join tEmployee e2 on c.Buyer2=e2.Number "
            + " where a.Status='2'";
		
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			chgSql+=" and a.ItemType1 = '"+ itemChg.getItemType1() +"'" ;
		}
		if(itemChg.getDateFrom()!=null){
			chgSql+=" and a.ConfirmDate>=  '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'" ;
		}
		if(itemChg.getDateTo()!=null){
			chgSql+=" and a.ConfirmDate<=  '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			chgSql+=" and a.Expired='F' ";
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			chgSql+=" and e.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(itemChg.getBrandCode())){
			chgSql+=" and c.sqlCode =  '" + itemChg.getBrandCode() +"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getBuyer())){					
			chgSql+=" and (c.Buyer1=  '"+itemChg.getBuyer() +"'" +" or c.Buyer2= '" + itemChg.getBuyer() +"')" ;
		}
		if(StringUtils.isNotBlank(itemChg.getDepartment2())){
			chgSql+=" and (e1.department2 = '"+itemChg.getDepartment2() +"' or e2.department2 = '"+itemChg.getDepartment2()+"')" ;
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			chgSql+="  and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+e.Code+'%') ";
		}
		if(StringUtils.isNotBlank(itemChg.getSupplCode())){
			chgSql+=" and c.SupplCode= '" + itemChg.getSupplCode() +"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getNotContainPlan())){
			//chgSql+=" and c.SqlCode not in ('1052','1943')  " ;
			chgSql+=" and c.ItemType2 <> '0267' " ;	
		}
		if(StringUtils.isNotBlank(itemChg.getIsOutSet())){
			chgSql+=" and b.IsOutSet=  '" + itemChg.getIsOutSet() +"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getSendType())){
			chgSql+=" and c.SendType=  '" + itemChg.getSendType() +"'" ;
		}
		
		// 增加角色和干系人的筛选条件
		if (StringUtils.isNotBlank(itemChg.getRole()) && StringUtils.isNotBlank(itemChg.getEmpCode())) {
			chgSql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = a.CustCode and in_a.Role = '" + itemChg.getRole() + "' and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getRole())) {
			chgSql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = a.CustCode and in_a.Role = '" + itemChg.getRole() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getEmpCode())) {
			chgSql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = a.CustCode and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		}
		
		chgSql+=" group by c.ItemType2,d.Descr ";
		
		
		String planSql="  select i.ItemType2,it2.Descr ItemType2Descr,sum(case when ip.IsOutSet='1' then ip.LineAmount else round(round(ip.Qty*i.ProjectCost*ip.Markup/100,0)+ip.ProcessCost,0) end) xse," +
				"sum(ip.Qty*ip.Cost) Cost"
	             + " ,0 chgAmount, sum(case when ip.IsOutSet = '1' then ip.LineAmount else round(round(ip.Qty * i.ProjectCost * ip.Markup / 100, 0)+ ip.ProcessCost, 0) end) planAmount"
             + " from tItemPlan ip"
             + " inner join tCustomer c on ip.CustCode=c.Code "
             + " inner join tItem i on ip.ItemCode=i.Code"
             + " inner join tItemType2 it2 on i.ItemType2=it2.Code"
             + " left join tIntProd ipd on ip.IntProdPK=ipd.Pk"
             + " left join tEmployee e1 on i.Buyer1=e1.Number "
             + " left join tEmployee e2 on i.Buyer2=e2.Number "
             + " where c.SignDate is not null ";
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			planSql+=" and i.itemType1 =  '"+itemChg.getItemType1() +"'" ;
			if("ZC".equals(itemChg.getItemType1())){
				planSql+=" and ((c.ContainMain=1 and ip.IsService=0) or (c.ContainMainServ=1 and ip.IsService=1)) ";
			}
			if("RZ".equals(itemChg.getItemType1())){
				planSql+="  and c.ContainSoft=1  ";
			}
			if("JC".equals(itemChg.getItemType1())){
				planSql+="  and ((c.ContainInt=1 and ipd.IsCupboard='0') or (c.ContainCup=1 and ipd.IsCupboard='1')) ";
			}
		}else{
			planSql+="and ("
               + " (i.ItemType1='ZC' and ((c.ContainMain=1 and ip.IsService=0) or (c.ContainMainServ=1 and ip.IsService=1))) or"
               + " (i.ItemType1='RZ' and c.ContainSoft=1) or"
               + " (i.ItemType1='JC' and ((c.ContainInt=1 and ipd.IsCupboard='0') or (c.ContainCup=1 and ipd.IsCupboard='1')))"
               + " )  ";
		}
		 
		if(itemChg.getDateFrom()!=null){
			planSql+=" and  c.SignDate>=  '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'" ;
		}
		if(itemChg.getDateTo()!=null){
			planSql+=" and  c.SignDate<=  '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			planSql+=" and c.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(itemChg.getBrandCode())){
			planSql+=" and i.sqlCode =  '" + itemChg.getBrandCode() +"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getBuyer())){
			planSql+=" and (i.Buyer1= '"+itemChg.getBuyer() +"'" +" or i.buyer1= '" + itemChg.getBuyer() +"')" ;
		}
		if(StringUtils.isNotBlank(itemChg.getDepartment2())){
			planSql+=" and e1.department2 = '"+itemChg.getDepartment2() +"'" +" or e2.Department2 = '" +itemChg.getDepartment2() +"'";
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			planSql+="  and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+c.Code+'%') ";
		}
		if(StringUtils.isNotBlank(itemChg.getSupplCode())){
			planSql+=" and i.SupplCode= '" + itemChg.getSupplCode() +"'"  ;
		}
		if(StringUtils.isNotBlank(itemChg.getNotContainPlan())){
			//planSql+=" and i.SqlCode not in ('1052','1943')  " ;
			planSql +=" and i.ItemType2 <> '0267' " ;	
		}
		if(StringUtils.isNotBlank(itemChg.getIsOutSet())){
			planSql+=" and ip.IsOutSet=  '" + itemChg.getIsOutSet() +"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getSendType())){
			planSql+=" and i.SendType=  '" + itemChg.getSendType() +"'" ;
		}
		
		// 增加角色和干系人的筛选条件
		if (StringUtils.isNotBlank(itemChg.getRole()) && StringUtils.isNotBlank(itemChg.getEmpCode())) {
			planSql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = ip.CustCode and in_a.Role = '" + itemChg.getRole() + "' and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getRole())) {
			planSql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = ip.CustCode and in_a.Role = '" + itemChg.getRole() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getEmpCode())) {
			planSql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = ip.CustCode and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		}
		
		planSql+=" group by i.ItemType2,it2.Descr ";
		
		String chgPlanSql=" select a.ItemType2,a.ItemType2Descr,'全案' xslx,sum(a.xse) xse, sum(a.Cost) Cost,"
                  + " case when sum(a.xse) = 0 then 0 else (sum(a.xse) - sum(a.Cost)) / sum(a.xse) * 100 end mlv "
                  + " ,sum(chgAmount) chgAmount,sum(planAmount) planAmount"
                  + " from (" + chgSql + " union all " + planSql + " ) a group by a.ItemType2, a.ItemType2Descr";
		
		
		String saleSql="select c.ItemType2,d.Descr ItemType2Descr,'独立销售' xslx,"
             + " sum(case when a.Type='R' then b.LineAmount*-1 else b.LineAmount end) xse,"
             + " sum(case when a.Type='R' then b.Qty*b.BCost*-1 else b.Qty*b.BCost end) Cost,"
             + " case when sum(case when a.Type='R' then b.LineAmount*-1 else b.LineAmount end)=0 then 0 "
             + " else (sum(case when a.Type='R' then b.LineAmount*-1 else b.LineAmount end)"
             + " -sum(case when a.Type='R' then b.Qty*b.BCost*-1 else b.Qty*b.BCost end))"
             + " /sum(case when a.Type='R' then b.LineAmount*-1 else b.LineAmount end)*100 end mlv "
             + " ,0 chgAmount,0 planAmount"
             + " from tSalesInvoice a "
             + " inner join tSalesInvoiceDetail b on a.No=b.SINo "
             + " inner join tItem c on b.ItCode=c.Code "
             + " inner join tItemType2 d on c.ItemType2=d.Code "
             + " left join tEmployee e1 on c.Buyer1=e1.Number "
             + " left join tEmployee e2 on c.Buyer2=e2.Number "
             + " where a.Status='CONFIRMED'";
		
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			saleSql+=" and a.ItemType1 =   '" + itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			saleSql+=" and a.getItemDate>=  '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			saleSql+=" and a.getItemDate<=  '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		/*if(StringUtils.isNotBlank(itemChg.getCustType())){
			saleSql+=" and e.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}*/
		if(StringUtils.isNotBlank(itemChg.getBrandCode())){
			saleSql+=" and c.sqlCode ='"  + itemChg.getBrandCode()+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getBuyer())){
			saleSql+=" and (c.Buyer1= '"+itemChg.getBuyer()+"'"+" or c.buyer2= '" + itemChg.getBuyer()+"')";
		}
		if(StringUtils.isNotBlank(itemChg.getDepartment2())){
			saleSql+=" and (e1.department2 =  '"+itemChg.getDepartment2()+"'"+" or e2.Department2 = '" +itemChg.getDepartment2()+"')";
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			saleSql+="  and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+c.Code+'%') ";
		}
		if(StringUtils.isNotBlank(itemChg.getSupplCode())){
			saleSql+=" and c.SupplCode= '" + itemChg.getSupplCode()+"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getNotContainPlan())){
			//saleSql+=" and c.SqlCode not in ('1052','1943')  " ;
			saleSql +=" and c.ItemType2 <> '0267' " ;	
		}
		if(StringUtils.isNotBlank(itemChg.getIsOutSet())){
			if("0".equals(itemChg.getIsOutSet()) )
			saleSql+=" and 1<>1 " ;
		}
		if(StringUtils.isNotBlank(itemChg.getSendType())){
			saleSql+=" and c.SendType=  '" + itemChg.getSendType() +"'" ;
		}
		
		// 增加角色和干系人的筛选条件
		if (StringUtils.isNotBlank(itemChg.getRole()) && StringUtils.isNotBlank(itemChg.getEmpCode())) {
			planSql += 
					" and exists (" +
					"  select 1 from tSaleStakeHolder in_a " +
					"  where in_a.SINo = a.No and in_a.Role = '" + itemChg.getRole() + "' and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getRole())) {
			planSql += 
					" and exists (" +
					"  select 1 from tSaleStakeHolder in_a " +
					"  where in_a.SINo = a.No and in_a.Role = '" + itemChg.getRole() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getEmpCode())) {
			planSql += 
					" and exists (" +
					"  select 1 from tSaleStakeHolder in_a " +
					"  where in_a.SINo = a.No and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		}
		
		saleSql+=" group by c.ItemType2,a.Type,d.Descr ";
		
		sql+=" select  * from(select a.ItemType2,a.ItemType2Descr,a.xslx,round(a.xse,0) xse,round(a.Cost,0) Cost,a.mlv ,round(chgAmount,0) chgAmount," +
				"round(planAmount,0) planAmount from (" + chgPlanSql + " union all " + saleSql + ") a" ;
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a   ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 按材料类型2——按结算时间统计
	 * RbWg.check 我也不知道Rbwg是什么
	 * @param page
	 * @param itemChg
	 * @return
	 */
	public Page<Map<String,Object>> findRbWgPageBy_itemType_Sql(Page<Map<String,Object>> page, ItemChg itemChg) {
		List<Object> list = new ArrayList<Object>();                                                                                                       
		String sql=" select  e.ItemType2,f.Descr ItemType2Descr,'全案' xslx, " +
				" sum(case when d.IsOutSet='1' then d.LineAmount else round(round(d.Qty*isnull(iad.ProjectCost,e.ProjectCost)*d.Markup/100,0)+d.ProcessCost,0) end) xse, " +
				" sum(d.Qty*d.Cost) Cost," +
				" case when sum(case when d.IsOutSet='1' then d.LineAmount else round(round(d.Qty*isnull(iad.ProjectCost,e.ProjectCost)*d.Markup/100,0)+d.ProcessCost,0) end)=0 then 0" +
				"  else  (sum(case when d.IsOutSet='1' then d.LineAmount else round(round(d.Qty*isnull(iad.ProjectCost,e.ProjectCost)*d.Markup/100,0)+d.ProcessCost,0) end)-sum(d.Qty*d.Cost)) " +
				"	/sum(case when d.IsOutSet='1' then d.LineAmount else round(round(d.Qty*isnull(iad.ProjectCost,e.ProjectCost)*d.Markup/100,0)+d.ProcessCost,0) end)*100 end mlv " +
				" from tCustomer a" +
				" inner join tItemReq d on a.Code=d.CustCode" +
				" inner join tItem e on d.ItemCode=e.Code" +
				" inner join tItemType2 f on e.ItemType2=f.Code" +
				" left join tEmployee e1 on e.Buyer1=e1.Number " +
				" left join tEmployee e2 on e.Buyer2=e2.Number" +
				" left join tItemAppDetail iad on d.PK=iad.ReqPk" +
				" where 1=1 and a.Status='5'";
		
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and d.ItemType1 = '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			sql+=" and a.CustCheckDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			sql+=" and a.CustCheckDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and a.Expired='F'";
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			sql+=" and a.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(itemChg.getBrandCode())){
			sql+=" and e.sqlCode = '" + itemChg.getBrandCode()+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getBuyer())){
			sql+=" and (e.Buyer1= '"+itemChg.getBuyer()+"'"+" or e.buyer2= '" + itemChg.getBuyer()+"')";
		}
		if(StringUtils.isNotBlank(itemChg.getDepartment2())){
			sql+=" and (e1.department2 = '"+itemChg.getDepartment2()+"'"+" or e2.department2 = '"+itemChg.getDepartment2()+"')";
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			sql+="  and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+e.Code+'%') ";
		}
		if(StringUtils.isNotBlank(itemChg.getSupplCode())){
			sql+=" and c.SupplCode= '" + itemChg.getSupplCode()+"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getIsOutSet())){
			sql+=" and d.IsOutSet=  '" + itemChg.getIsOutSet() +"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getSendType())){
			sql+=" and e.SendType=  '" + itemChg.getSendType() +"'" ;
		}
		
		// 增加角色和干系人的筛选条件
		if (StringUtils.isNotBlank(itemChg.getRole()) && StringUtils.isNotBlank(itemChg.getEmpCode())) {
			sql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = a.Code and in_a.Role = '" + itemChg.getRole() + "' and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getRole())) {
			sql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = a.Code and in_a.Role = '" + itemChg.getRole() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getEmpCode())) {
			sql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = a.Code and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		}
		
		sql+=" group by e.ItemType2,f.Descr " +
				" union all "
		      + " select c.ItemType2,d.Descr ItemType2Descr,'独立销售' xslx,"
		      + " sum(case when a.Type='R' then b.LineAmount*-1 else b.LineAmount end) xse,"
		      + " sum(case when a.Type='R' then b.Qty*b.BCost*-1 else b.Qty*b.BCost end) Cost,"
		      + " case when sum(case when a.Type='R' then b.LineAmount*-1 else b.LineAmount end)=0 then 0 "
		      + " else (sum(case when a.Type='R' then b.LineAmount*-1 else b.LineAmount end)"
		      + " -sum(case when a.Type='R' then b.Qty*b.BCost*-1 else b.Qty*b.BCost end))"
		      + " /sum(case when a.Type='R' then b.LineAmount*-1 else b.LineAmount end)*100 end mlv "
		      + " from tSalesInvoice a "
		      + " inner join tSalesInvoiceDetail b on a.No=b.SINo "
		      + " inner join tItem c on b.ItCode=c.Code "
		      + " inner join tItemType2 d on c.ItemType2=d.Code "
		      + " left join tEmployee e1 on c.Buyer1=e1.Number "
		      + " left join tEmployee e2 on c.Buyer2=e2.Number "
		      + " where 1=1";
		
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and a.ItemType1 =  '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			sql+=" and a.getItemDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			sql+=" and a.getItemDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and a.Expired='F'";
		}
		if(StringUtils.isNotBlank(itemChg.getBrandCode())){
			sql+=" and c.sqlCode = '" + itemChg.getBrandCode()+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getBuyer())){
			sql+=" and (c.Buyer1=  '"+itemChg.getBuyer()+"'"+" or c.buyer2=  '" + itemChg.getBuyer()+"')";
		}
		if(StringUtils.isNotBlank(itemChg.getDepartment2())){
			sql+=" and (e1.department2 ='"+itemChg.getDepartment2()+"'"+ " or e2.department2 = '" + itemChg.getDepartment2()+"')";
		}
		if(StringUtils.isNotBlank(itemChg.getSupplCode())){
			sql+=" and c.SupplCode= '" + itemChg.getSupplCode()+"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getIsOutSet())){
			if("0".equals(itemChg.getIsOutSet()) )
			sql+=" and 1<>1 " ;
		}
		if(StringUtils.isNotBlank(itemChg.getSendType())){
			sql+=" and c.SendType=  '" + itemChg.getSendType() +"'" ;
		}
		
		// 增加角色和干系人的筛选条件
		if (StringUtils.isNotBlank(itemChg.getRole()) && StringUtils.isNotBlank(itemChg.getEmpCode())) {
			sql += 
					" and exists (" +
					"  select 1 from tSaleStakeHolder in_a " +
					"  where in_a.SINo = a.No and in_a.Role = '" + itemChg.getRole() + "' and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getRole())) {
			sql += 
					" and exists (" +
					"  select 1 from tSaleStakeHolder in_a " +
					"  where in_a.SINo = a.No and in_a.Role = '" + itemChg.getRole() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getEmpCode())) {
			sql += 
					" and exists (" +
					"  select 1 from tSaleStakeHolder in_a " +
					"  where in_a.SINo = a.No and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		}
		
		sql+=" group by c.ItemType2,a.Type,d.Descr  ";
		
		String sqlTxt="select * from ( select a.ItemType2,a.ItemType2Descr,a.xslx,round(a.xse,0) xse,round(a.Cost,0) Cost,a.mlv from ( "
				+sql+ " ) a ";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sqlTxt += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sqlTxt += ") a ";
		}
		return this.findPageBySql(page, sqlTxt, list.toArray());
	}
	
	/**
	 * 增减优惠金额——按增减统计
	 * @param itemChg
	 * @return
	 */
	public String getYHJE_rbzj_by_itemType_Sql(ItemChg itemChg){
		//rbzj
		String sql=" select isnull(sum(case when a.BefAmount>0 then a.DiscAmount else a.DiscAmount*-1 end),0) yhje from " +
				" tItemChg a " +
				" left join tCustomer c on a.CustCode=c.Code  " +
				" where a.Status='2'   " +
				"  " ;
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and a.ItemType1 =  '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			sql+="  and a.ConfirmDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			sql+="  and a.ConfirmDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and a.Expired='F'";
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			sql+=" and c.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			sql+="  and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+c.Code+'%') ";
		}
		
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return  map.get("yhje").toString();
		}
		return "";
	}
	/**
	 * 按材料类型2——按结算时间统计需求
	 * 增减优惠 
	 * @param itemChg
	 * @return
	 */
	public String getYHJE_rbwg_by_itemType_Sql(ItemChg itemChg){
		//rbWg
		String sql=" select isnull(sum(case when g.BefAmount>0 then g.DiscAmount else g.DiscAmount*-1 end),0) yhje from" +
				" tCustomer a " +
				" inner join tItemChg g on a.Code=g.CustCode " +
				" where 1=1 and a.Status='5'";
		
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and g.ItemType1 =  '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			sql+="  and a.CustCheckDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			sql+="  and a.CustCheckDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and a.Expired='F'";
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			sql+=" and a.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			sql+="  and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+a.Code+'%') ";
		}
		
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return  map.get("yhje").toString();
		}
		return "";
	}
	
	/**
	 * 独立销售优惠
	 * 按材料类型2——按增减统计
	 * @param itemChg
	 * @return
	 */
	public String getDLYH_rbzj_by_itemType_Sql(ItemChg itemChg){
		
		String sql=" select isnull(sum(case when a.Type='R' then (a.Amount-a.BefAmount)*-1 else " +
				" a.Amount-a.BefAmount end),0) yhje   from tSalesInvoice a " +
				"  where a.Status='CONFIRMED'" ;
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and a.ItemType1 =  '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			sql+="  and a.getItemDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			sql+="  and a.getItemDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and a.Expired='F'";
		}
		
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return  map.get("yhje").toString();
		}
		return "";
	}
	
	/**
	 * 按材料类型2——按结算时间需求
	 * 独立销售优惠
	 * @param itemChg
	 * @return
	 */
	public String getDLYH_rbwg_by_itemType_Sql(ItemChg itemChg){
		
		String sql="select isnull(sum(case when a.Type='R' then (a.Amount-a.BefAmount)*-1 else " +
				"a.Amount-a.BefAmount end),0) yhje  from tSalesInvoice " +
				"a where 1=1 " ;
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and a.ItemType1 =  '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			sql+="  and a.getItemDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			sql+="  and a.getItemDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and a.Expired='F'";
		}
		
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return  map.get("yhje").toString();
		}
		return "";
	}
	
				/***************************************按供应商************************************************/
	/**
	 * 按供应商 ——按增减需求
	 * @param page
	 * @param itemChg
	 * @return
	 */
	public Page<Map<String,Object>> findRbZjPageBy_sqlCode_Sql(Page<Map<String,Object>> page, ItemChg itemChg) {
		List<Object> list = new ArrayList<Object>();                                                                                                       
		String sql=" ";
		String chgSql = "  select c.SqlCode,d.Descr SqlDescr,sum(case when b.IsOutSet='1' then b.LineAmount else round(round(b.Qty*c.ProjectCost*b.Markup/100,0)+b.ProcessCost,0) end) xse,sum(b.Qty*b.Cost) Cost"
            + " from tItemChg a "
            + " inner join tItemChgDetail b on a.No=b.No "
            + " inner join tItem c on b.ItemCode=c.Code "
            + " inner join tBrand d on c.SqlCode=d.Code "
            + " left join tCustomer e on a.CustCode=e.Code "
            + " left join tEmployee e1 on c.Buyer1=e1.Number "
            + " left join tEmployee e2 on c.Buyer2=e2.Number " +
            "  where a.Status='2'";
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			chgSql+=" and a.ItemType1 =  '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			chgSql+=" and a.ConfirmDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			chgSql+=" and a.ConfirmDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			chgSql+=" and a.expired= 'F'";
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			chgSql+=" and e.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(itemChg.getBrandCode())){
			chgSql+=" and c.sqlCode = '" + itemChg.getBrandCode()+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getBuyer())){
			chgSql+=" and (c.Buyer1= '"+itemChg.getBuyer()+"'"+" or c.buyer2= '" + itemChg.getBuyer()+"')";
		}
		if(StringUtils.isNotBlank(itemChg.getDepartment2())){
			chgSql+=" and (e1.department2 = '"+itemChg.getDepartment2()+"'"+" or e2.department2 = '"+ itemChg.getDepartment2()+"')";
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			chgSql+="  and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+e.Code+'%') ";
		}
		if(StringUtils.isNotBlank(itemChg.getSupplCode())){
			chgSql+=" and c.SupplCode='" + itemChg.getSupplCode() +"'";
		}
		if(StringUtils.isNotBlank(itemChg.getNotContainPlan())){
			//chgSql+=" and c.SqlCode not in ('1052','1943')  " ;
			  chgSql+=" and c.ItemType2 <> '0267' " ;	
		}
		if(StringUtils.isNotBlank(itemChg.getIsOutSet())){
			chgSql+=" and b.IsOutSet=  '" + itemChg.getIsOutSet() +"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getSendType())){
			chgSql+=" and c.SendType=  '" + itemChg.getSendType() +"'" ;
		}
		
		// 增加角色和干系人的筛选条件
		if (StringUtils.isNotBlank(itemChg.getRole()) && StringUtils.isNotBlank(itemChg.getEmpCode())) {
			chgSql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = a.CustCode and in_a.Role = '" + itemChg.getRole() + "' and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getRole())) {
			chgSql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = a.CustCode and in_a.Role = '" + itemChg.getRole() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getEmpCode())) {
			chgSql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = a.CustCode and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		}
		
		chgSql+=" group by c.sqlCode,d.Descr ";
		
		String planSql="  select i.SqlCode,br.Descr SqlDescr,sum(case when ip.IsOutSet='1' then ip.LineAmount " +
				"else round(round(ip.Qty*i.ProjectCost*ip.Markup/100,0)+ip.ProcessCost,0) end) xse,sum(ip.Qty*ip.Cost) Cost"
             + " from tItemPlan ip "
             + " inner join tCustomer c on ip.CustCode=c.Code "
             + " inner join tItem i on ip.ItemCode=i.Code"
             + " inner join tBrand br on i.SqlCode=br.Code"
             + " left join tIntProd ipd on ip.IntProdPK=ipd.Pk"
             + " left join tEmployee e1 on i.Buyer1=e1.Number "
             + " left join tEmployee e2 on i.Buyer2=e2.Number "
             + " where c.SignDate is not null ";
		
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			planSql+=" and i.ItemType1 ='" + itemChg.getItemType1()+"'";
			
			if("ZC".equals(itemChg.getItemType1())){
				planSql+=" and ((c.ContainMain=1 and ip.IsService=0) or (c.ContainMainServ=1 and ip.IsService=1)) ";
			}
			if("RZ".equals(itemChg.getItemType1())){
				planSql+="  and c.ContainSoft=1  ";
			}
			if("JC".equals(itemChg.getItemType1())){
				planSql+="  and ((c.ContainInt=1 and ipd.IsCupboard='0') or (c.ContainCup=1 and ipd.IsCupboard='1')) ";
			}
		}else{
			planSql+="and ("
               + " (i.ItemType1='ZC' and ((c.ContainMain=1 and ip.IsService=0) or (c.ContainMainServ=1 and ip.IsService=1))) or"
               + " (i.ItemType1='RZ' and c.ContainSoft=1) or"
               + " (i.ItemType1='JC' and ((c.ContainInt=1 and ipd.IsCupboard='0') or (c.ContainCup=1 and ipd.IsCupboard='1')))"
               + " )  ";
		}
		 
		if(itemChg.getDateFrom()!=null){
			planSql+=" and  c.SignDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			planSql+=" and  c.SignDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			planSql+=" and ip.Expired= 'F'";
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			planSql+=" and c.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(itemChg.getBrandCode())){
			planSql+=" and i.sqlCode = '" + itemChg.getBrandCode()+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getBuyer())){
			planSql+=" and (i.Buyer1= '"+itemChg.getBuyer()+"'"+" or i.buyer2= '" + itemChg.getBuyer()+"')";
		}
		if(StringUtils.isNotBlank(itemChg.getDepartment2())){
			planSql+=" and (e1.department2 = '"+itemChg.getDepartment2()+"'"+" or e2.Department2 = '" +itemChg.getDepartment2()+"')" ;
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			planSql+="  and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+c.Code+'%') ";
		}
		if(StringUtils.isNotBlank(itemChg.getSupplCode())){
			planSql+=" and i.SupplCode='" + itemChg.getSupplCode() +"'";
		}
		if(StringUtils.isNotBlank(itemChg.getNotContainPlan())){
			//planSql+=" and i.SqlCode not in ('1052','1943')  " ;
			planSql +=" and i.ItemType2 <> '0267' " ;	
		}
		if(StringUtils.isNotBlank(itemChg.getIsOutSet())){
			planSql+=" and ip.IsOutSet=  '" + itemChg.getIsOutSet() +"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getSendType())){
			planSql+=" and i.SendType=  '" + itemChg.getSendType() +"'" ;
		}
		
		// 增加角色和干系人的筛选条件
		if (StringUtils.isNotBlank(itemChg.getRole()) && StringUtils.isNotBlank(itemChg.getEmpCode())) {
			planSql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = ip.CustCode and in_a.Role = '" + itemChg.getRole() + "' and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getRole())) {
			planSql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = ip.CustCode and in_a.Role = '" + itemChg.getRole() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getEmpCode())) {
			planSql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = ip.CustCode and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		}
		
		planSql+="  group by i.SqlCode,br.Descr ";
		
		String chgPlanSql=" select a.SqlCode,a.SqlDescr,'全案' xslx,sum(a.xse) xse, sum(a.Cost) Cost,"
                + " case when sum(a.xse) = 0 then 0 else (sum(a.xse) - sum(a.Cost)) / sum(a.xse) * 100 end mlv "
                + " from (" + chgSql + " union all " + planSql + " ) " +
                	" a group by a.SqlCode, a.SqlDescr ";
		
		String saleSql=" select c.ItemType2,d.Descr ItemType2Descr,'独立销售' xslx,"
             + " sum(case when a.Type='R' then b.LineAmount*-1 else b.LineAmount end) xse,"
             + " sum(case when a.Type='R' then b.Qty*b.BCost*-1 else b.Qty*b.BCost end) Cost,"
             + " case when sum(case when a.Type='R' then b.LineAmount*-1 else b.LineAmount end)=0 then 0 "
             + " else (sum(case when a.Type='R' then b.LineAmount*-1 else b.LineAmount end)"
             + " -sum(case when a.Type='R' then b.Qty*b.BCost*-1 else b.Qty*b.BCost end))"
             + " /sum(case when a.Type='R' then b.LineAmount*-1 else b.LineAmount end)*100 end mlv "
            // + " ,0 chgAmount,0 planAmount"
             + " from tSalesInvoice a "
             + " inner join tSalesInvoiceDetail b on a.No=b.SINo "
             + " inner join tItem c on b.ItCode=c.Code "
             + " inner join tItemType2 d on c.ItemType2=d.Code "
             + " left join tEmployee e1 on c.Buyer1=e1.Number "
             + " left join tEmployee e2 on c.Buyer2=e2.Number "
             + " where a.Status='CONFIRMED'";
		
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			saleSql+=" and a.ItemType1 =  '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			saleSql+=" and a.getItemDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			saleSql+=" and a.getItemDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			saleSql+=" and a.expired='F'";
		}
		/*if(StringUtils.isNotBlank(itemChg.getCustType())){
			saleSql+=" and e.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}*/
		if(StringUtils.isNotBlank(itemChg.getBrandCode())){
			saleSql+=" and c.sqlCode = '" + itemChg.getBrandCode()+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getBuyer())){
			saleSql+=" and (c.Buyer1= '"+itemChg.getBuyer()+"'"+" or c.buyer2= '" + itemChg.getBuyer()+"')";
		}
		if(StringUtils.isNotBlank(itemChg.getDepartment2())){
			saleSql+=" and (e1.department2 = '"+itemChg.getDepartment2()+"'"+" or e2.Department2 ='" +itemChg.getDepartment2()+"')";
		}
		if(StringUtils.isNotBlank(itemChg.getSupplCode())){
			saleSql+=" and c.SupplCode='" + itemChg.getSupplCode()+"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getIsOutSet())){
			if("0".equals(itemChg.getIsOutSet()) )
			saleSql+=" and 1<>1 " ;
		}
		if(StringUtils.isNotBlank(itemChg.getSendType())){
			saleSql+=" and c.SendType=  '" + itemChg.getSendType() +"'" ;
		}
		
		// 增加角色和干系人的筛选条件
		if (StringUtils.isNotBlank(itemChg.getRole()) && StringUtils.isNotBlank(itemChg.getEmpCode())) {
			planSql += 
					" and exists (" +
					"  select 1 from tSaleStakeHolder in_a " +
					"  where in_a.SINo = a.No and in_a.Role = '" + itemChg.getRole() + "' and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getRole())) {
			planSql += 
					" and exists (" +
					"  select 1 from tSaleStakeHolder in_a " +
					"  where in_a.SINo = a.No and in_a.Role = '" + itemChg.getRole() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getEmpCode())) {
			planSql += 
					" and exists (" +
					"  select 1 from tSaleStakeHolder in_a " +
					"  where in_a.SINo = a.No and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		}
		
		saleSql+=" group by c.SqlCode,a.Type,d.Descr,c.itemType2 ";
		
		sql=" select * from ( select a.SqlCode,a.SqlDescr,a.xslx,round(a.xse,0) xse,round(a.Cost,0) Cost,a.mlv, b.ItemType2, it2.Descr ItemType2Descr " +
				"from (" + chgPlanSql + " union all " + saleSql + ") a"
            + " left join tBrand b on b.Code=a.SqlCode "
            + " left join tItemType2 it2 on it2.Code=b.ItemType2 ";
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a  ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findRbWgPageBy_sqlCode_Sql(Page<Map<String,Object>> page, ItemChg itemChg) {
		List<Object> list = new ArrayList<Object>();                                                                                                       
		String sql="select e.SqlCode,f.Descr SqlDescr,'全案' xslx, " +
				" sum(case when d.IsOutSet='1' then d.LineAmount else round(round(d.Qty*isnull(iad.ProjectCost,e.ProjectCost)*d.Markup/100,0)+d.ProcessCost,0) end) xse, " +
				" sum(d.Qty*d.Cost) Cost," +
				" case when sum(case when d.IsOutSet='1' then d.LineAmount else round(round(d.Qty*isnull(iad.ProjectCost,e.ProjectCost)*d.Markup/100,0)+d.ProcessCost,0) end)=0 then 0" +
				" else (sum(case when d.IsOutSet='1' then d.LineAmount else round(round(d.Qty*isnull(iad.ProjectCost,e.ProjectCost)*d.Markup/100,0)+d.ProcessCost,0) end)-sum(d.Qty*d.Cost))" +
				" /sum(case when d.IsOutSet='1' then d.LineAmount else round(round(d.Qty*isnull(iad.ProjectCost,e.ProjectCost)*d.Markup/100,0)+d.ProcessCost,0) end)*100 end mlv " +
				" from tCustomer a" +
				" inner join tItemReq d on a.Code=d.CustCode" +
				" inner join tItem e on d.ItemCode=e.Code " +
				" inner join tBrand f on e.SqlCode=f.Code" +
				" left join tEmployee e1 on e.Buyer1=e1.Number" +
				" left join tEmployee e2 on e.Buyer2=e2.Number" +
				" left join tItemAppDetail iad on d.PK=iad.ReqPk " +
				" where 1=1 and a.Status='5'" ;
				//" where 1=1 ";

		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and d.ItemType1 =  '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			sql+=" and a.CustCheckDate>= '" +new Timestamp(
					DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			sql+=" and a.CustCheckDate<= '" + new Timestamp(
					DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and a.Expired='F'";
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			sql+=" and a.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(itemChg.getBrandCode())){
			sql+=" and e.sqlCode = '" + itemChg.getBrandCode()+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getBuyer())){
			sql+=" and (e.Buyer1= '"+itemChg.getBuyer()+"'"+" or e.buyer2= '" + itemChg.getBuyer()+"')";
		}
		if(StringUtils.isNotBlank(itemChg.getDepartment2())){
			sql+=" and (e1.department2 = '"+itemChg.getDepartment2()+"'"+ " or e2.department2 = '"+itemChg.getDepartment2()+"')";
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			sql+="  and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+e.Code+'%') ";
		}
		if(StringUtils.isNotBlank(itemChg.getSupplCode())){
			sql+=" and e.SupplCode='" + itemChg.getSupplCode()+"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getIsOutSet())){
			sql+=" and d.IsOutSet=  '" + itemChg.getIsOutSet() +"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getSendType())){
			sql+=" and e.SendType=  '" + itemChg.getSendType() +"'" ;
		}
		
		// 增加角色和干系人的筛选条件
		if (StringUtils.isNotBlank(itemChg.getRole()) && StringUtils.isNotBlank(itemChg.getEmpCode())) {
			sql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = a.Code and in_a.Role = '" + itemChg.getRole() + "' and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getRole())) {
			sql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = a.Code and in_a.Role = '" + itemChg.getRole() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getEmpCode())) {
			sql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = a.Code and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		}
		
		sql+=" group by e.SqlCode,f.Descr "
			  + " union all "
		      + " select c.SqlCode,d.Descr SqlDescr,'独立销售' xslx,"
		      + " sum(case when a.Type='R' then b.LineAmount*-1 else b.LineAmount end) xse,"
		      + " sum(case when a.Type='R' then b.Qty*b.BCost*-1 else b.Qty*b.BCost end) Cost,"
		      + " case when sum(case when a.Type='R' then b.LineAmount*-1 else b.LineAmount end)=0 then 0 "
		      + " else (sum(case when a.Type='R' then b.LineAmount*-1 else b.LineAmount end)"
		      + " -sum(case when a.Type='R' then b.Qty*b.BCost*-1 else b.Qty*b.BCost end))"
		      + " /sum(case when a.Type='R' then b.LineAmount*-1 else b.LineAmount end)*100 end mlv "
		      + " from tSalesInvoice a "
		      + " inner join tSalesInvoiceDetail b on a.No=b.SINo "
		      + " inner join tItem c on b.ItCode=c.Code "
		      + " inner join tBrand d on c.SqlCode=d.Code "
		      + " left join tEmployee e1 on c.Buyer1=e1.Number "
		      + " left join tEmployee e2 on c.Buyer2=e2.Number "
		      + " left join tItemType2 it2 on it2.Code=d.ItemType2 "
		      + " where 1=1 ";
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and a.ItemType1 = '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			sql+=" and a.getItemDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			sql+=" and a.getItemDate<= '" + new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and a.Expired='F'";
		}
		if(StringUtils.isNotBlank(itemChg.getBrandCode())){
			sql+=" and c.sqlCode = '" + itemChg.getBrandCode()+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getBuyer())){
			sql+=" and (c.Buyer1= '"+itemChg.getBuyer()+"'"+" or c.buyer2= '" + itemChg.getBuyer()+"')";
		}
		if(StringUtils.isNotBlank(itemChg.getDepartment2())){
			sql+=" and (e1.department2 = '"+itemChg.getDepartment2()+"'"+ " or e2.department2 = '" + itemChg.getDepartment2()+"')";
		}
		if(StringUtils.isNotBlank(itemChg.getSupplCode())){
			sql+=" and c.SupplCode='" + itemChg.getSupplCode() +"'";
		}
		if(StringUtils.isNotBlank(itemChg.getIsOutSet())){
			if("0".equals(itemChg.getIsOutSet()) )
			sql+=" and 1<>1 " ;
		}
		if(StringUtils.isNotBlank(itemChg.getSendType())){
			sql+=" and c.SendType=  '" + itemChg.getSendType() +"'" ;
		}
		
		// 增加角色和干系人的筛选条件
		if (StringUtils.isNotBlank(itemChg.getRole()) && StringUtils.isNotBlank(itemChg.getEmpCode())) {
			sql += 
					" and exists (" +
					"  select 1 from tSaleStakeHolder in_a " +
					"  where in_a.SINo = a.No and in_a.Role = '" + itemChg.getRole() + "' and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getRole())) {
			sql += 
					" and exists (" +
					"  select 1 from tSaleStakeHolder in_a " +
					"  where in_a.SINo = a.No and in_a.Role = '" + itemChg.getRole() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getEmpCode())) {
			sql += 
					" and exists (" +
					"  select 1 from tSaleStakeHolder in_a " +
					"  where in_a.SINo = a.No and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		}
		
		sql+=" group by c.SqlCode,a.Type,d.Descr ";
		
		String sqltxt=" select * from( select a.SqlCode,a.SqlDescr,a.xslx,round(a.xse,0) xse,round(a.Cost,0) Cost,a.mlv, b.ItemType2, it2.Descr ItemType2Descr from (" + sql + ") a"
            + " left join tBrand b on b.Code=a.SqlCode "
            + " left join tItemType2 it2 on it2.Code=b.ItemType2 ";
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sqltxt += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sqltxt += ") a  ";
		}
		return this.findPageBySql(page, sqltxt, list.toArray());
	}	
	
	/**
	 * 增减优惠金额——按增减统计
	 * @param itemChg
	 * @return
	 */
	public String getYHJE_rbzj_by_sqlCode_Sql(ItemChg itemChg){
		//rbzj
		String sql=" select isnull(sum(case when a.BefAmount>0 then a.DiscAmount else a.DiscAmount*-1 end),0) yhje from " +
				" tItemChg a " +
				" left join tCustomer c on a.CustCode=c.Code  " +
				" where a.Status='2'   " +
				"  " ;
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and a.ItemType1 =  '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			sql+="  and a.ConfirmDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			sql+="  and a.ConfirmDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and a.Expired='F'";
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			sql+=" and c.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			sql+="  and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+c.Code+'%') ";
		}
		
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return  map.get("yhje").toString();
		}
		return "";
	}
	/**
	 * 按供应商——按结算时间统计需求
	 * 增减优惠 
	 * @param itemChg
	 * @return
	 */
	public String getYHJE_rbwg_by_sqlCode_Sql(ItemChg itemChg){
		//rbWg
		String sql=" select isnull(sum(case when g.BefAmount>0 then g.DiscAmount else g.DiscAmount*-1 end),0) yhje from" +
				" tCustomer a " +
				" inner join tItemChg g on a.Code=g.CustCode " +
				" where 1=1 and a.Status='5'";
		
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and g.ItemType1 =  '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			sql+="  and a.CustCheckDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			sql+="  and a.CustCheckDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and a.Expired='F'";
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			sql+=" and a.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			sql+="  and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+a.Code+'%') ";
		}
		
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return  map.get("yhje").toString();
		}
		return "";
	}
	
	/**
	 * 独立销售优惠
	 * 供应商——按增减统计
	 * @param itemChg
	 * @return
	 */
	public String getDLYH_rbzj_by_sqlCode_Sql(ItemChg itemChg){
		
		String sql="  select isnull(sum(case when a.Type='R' then (a.Amount-a.BefAmount)*-1 else a.Amount-a.BefAmount end),0) yhje "
            + " from tSalesInvoice a"
            + " where a.Status='CONFIRMED'" ;
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and a.ItemType1 =  '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			sql+="  and a.getItemDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			sql+="  and a.getItemDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and a.Expired='F'";
		}
		
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return  map.get("yhje").toString();
		}
		return "";
	}
	
	/**
	 * 按供应商——按结算时间需求
	 * 独立销售优惠
	 * @param itemChg
	 * @return
	 */
	public String getDLYH_rbwg_by_sqlCode_Sql(ItemChg itemChg){
		
		String sql="select isnull(sum(case when a.Type='R' then (a.Amount-a.BefAmount)*-1 else " +
				"a.Amount-a.BefAmount end),0) yhje  from tSalesInvoice " +
				"a where 1=1 " ;
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and a.ItemType1 =  '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			sql+="  and a.getItemDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			sql+="  and a.getItemDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and a.Expired='F'";
		}
		
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return  map.get("yhje").toString();
		}
		return "";
	}
	
				/*********************以下按楼盘汇总**************************/
	/**
	 * 
	 * @param page
	 * @param itemChg
	 * @return
	 */
	public Page<Map<String,Object>> findRbZjPageBy_cust_Sql(Page<Map<String,Object>> page, ItemChg itemChg) {
		List<Object> list = new ArrayList<Object>();                                                                                                       
		
		String sql = " " ;
		String chgSql=" select a.no,e.Code,e.Address,e.CustType,sum(case when b.IsOutSet='1' then b.LineAmount else round(round(b.Qty*c.ProjectCost*b.Markup/100,0)+b.ProcessCost,0) end) xse "
            + ",sum(case when b.IsOutSet = '1' then b.LineAmount else round(round(b.Qty * c.Price * b.Markup / 100, 0)+ b.ProcessCost, 0)end) chgAmount,0 planAmount, 0 PlanDisc,e.SaleType "
            + " from tItemChg a"
            + " inner join tItemChgDetail b on a.No=b.No "
            + " inner join tItem c on b.ItemCode=c.Code "
            + " inner join tItemType2 d on c.ItemType2=d.Code "
            + " left join tCustomer e on a.CustCode=e.Code "
            + " left join tEmployee e1 on c.Buyer1=e1.Number "
            + " left join tEmployee e2 on c.Buyer2=e2.Number " +
            	" where a.Status='2'";
		 
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			chgSql+=" and a.ItemType1 =  '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			chgSql+=" and a.ConfirmDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			chgSql+=" and a.ConfirmDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			chgSql+=" and a.expired='F'";
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			chgSql+=" and e.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(itemChg.getBrandCode())){
			chgSql+=" and c.sqlCode = '" + itemChg.getBrandCode()+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getBuyer())){
			chgSql+=" and (c.Buyer1= '"+itemChg.getBuyer()+"'"+" or c.buyer2= '" + itemChg.getBuyer()+"')";
		}
		if(StringUtils.isNotBlank(itemChg.getDepartment2())){
			chgSql+=" and (e1.department2 = '"+itemChg.getDepartment2()+"'"+" or e2.department2 ='"+itemChg.getDepartment2()+"')";
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			chgSql+="  and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+e.Code+'%') ";
		}
		if(StringUtils.isNotBlank(itemChg.getSupplCode())){
			chgSql+=" and c.SupplCode='" + itemChg.getSupplCode()+"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getNotContainPlan())){
			//chgSql+=" and c.SqlCode not in ('1052','1943')  " ;
			chgSql +=" and c.ItemType2 <> '0267' " ;	
		}
		if(StringUtils.isNotBlank(itemChg.getIsOutSet())){
			chgSql+=" and b.IsOutSet=  '" + itemChg.getIsOutSet() +"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getSendType())){
			chgSql+=" and c.SendType=  '" + itemChg.getSendType() +"'" ;
		}
		
		// 增加角色和干系人的筛选条件
		if (StringUtils.isNotBlank(itemChg.getRole()) && StringUtils.isNotBlank(itemChg.getEmpCode())) {
			chgSql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = a.CustCode and in_a.Role = '" + itemChg.getRole() + "' and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getRole())) {
			chgSql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = a.CustCode and in_a.Role = '" + itemChg.getRole() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getEmpCode())) {
			chgSql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = a.CustCode and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		}
		
		chgSql+=" group by e.Code,e.Address,e.CustType,a.No,e.SaleType  ";
		
		String chgSql_ = " select a.Code,a.address,a.CustType,sum(a.xse) xse,sum(a.chgAmount) chgAmount,sum(a.planAmount) planAmount,sum(a.PlanDisc) PlanDisc," +
				" sum(case when b.BefAmount < 0 then -b.DiscAmount else b.DiscAmount  end) ChgDiscAmount,  sum(case when b.BefAmount < 0 then -b.DiscCost  else b.DiscCost end) ChgDiscCost," +
				" a.SaleType "
	            + "  from "
	            + " (" + chgSql + ") a"
	            + " left join tItemChg b on a.No=b.No"
	            + " group by a.Code,a.address,a.CustType,a.SaleType ";
		
		String planSql=" select c.Code,c.Address,c.CustType,sum(case when ip.IsOutSet='1' then ip.LineAmount else round(round(ip.Qty*i.ProjectCost*ip.Markup/100,0)+ip.ProcessCost,0) end) xse " +
				",0 chgAmount,sum(case when ip.IsOutSet = '1' then ip.LineAmount else round(round(ip.Qty * i.ProjectCost * ip.Markup / 100, 0)+ ip.ProcessCost, 0) end) planAmount,";
		
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			
			if("ZC".equals(itemChg.getItemType1())){
				planSql+="  case when c.ContainMain=1 then c.MainDisc else 0 end PlanDisc  ,0 ChgDiscAmount, 0 ChgDiscCost ";
			}
			if("RZ".equals(itemChg.getItemType1())){
				planSql+=" case when c.ContainSoft=1 then c.SoftDisc else 0 end PlanDisc  ,0 ChgDiscAmount, 0 ChgDiscCost ";
			}
			if("JC".equals(itemChg.getItemType1())){
				planSql+=" (case when c.ContainInt=1 then c.IntegrateDisc else 0 end + case when c.ContainCup=1 then c.CupBoardDisc else 0 end) PlanDisc  ,0 ChgDiscAmount, 0 ChgDiscCost ";
			}
		}else{
			planSql+="("
               + " case when c.ContainMain=1 then c.MainDisc else 0 end +"
               + " case when c.ContainSoft=1 then c.SoftDisc else 0 end +"
               + " case when c.ContainInt=1 then c.IntegrateDisc else 0 end +"
               + " case when c.ContainCup=1 then c.CupBoardDisc else 0 end "
               + " ) PlanDisc ,0 ChgDiscAmount, 0 ChgDiscCost ";
		}
		
		planSql+=",c.SaleType "
             + " from tItemPlan ip"
             + " inner join tCustomer c on ip.CustCode=c.Code "
             + " inner join tItem i on ip.ItemCode=i.Code"
             + " inner join tItemType2 it2 on i.ItemType2=it2.Code"
             + " left join tIntProd ipd on ip.IntProdPK=ipd.Pk"
             + " left join tEmployee e1 on i.Buyer1=e1.Number "
             + " left join tEmployee e2 on i.Buyer2=e2.Number "
             + " where c.SignDate is not null ";
		 
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			planSql+=" and i.itemType1 = '"+itemChg.getItemType1()+"'";
			if("ZC".equals(itemChg.getItemType1())){
				planSql+=" and ((c.ContainMain=1 and ip.IsService=0) or (c.ContainMainServ=1 and ip.IsService=1)) ";
			}
			if("RZ".equals(itemChg.getItemType1())){
				planSql+=" and c.ContainSoft=1  ";
			}
			if("JC".equals(itemChg.getItemType1())){
				planSql+="  and ((c.ContainInt=1 and ipd.IsCupboard='0') or (c.ContainCup=1 and ipd.IsCupboard='1')) ";
			}
		}else{
			planSql+="and ("
               + " (i.ItemType1='ZC' and ((c.ContainMain=1 and ip.IsService=0) or (c.ContainMainServ=1 and ip.IsService=1))) or"
               + " (i.ItemType1='RZ' and c.ContainSoft=1) or"
               + " (i.ItemType1='JC' and ((c.ContainInt=1 and ipd.IsCupboard='0') or (c.ContainCup=1 and ipd.IsCupboard='1')))"
               + " )  ";
		}
		
		if(itemChg.getDateFrom()!=null){
			planSql+=" and  c.SignDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			planSql+=" and  c.SignDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			planSql+= " and ip.expired='F'";
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			planSql+=" and c.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(itemChg.getBrandCode())){
			planSql+=" and i.sqlCode = '" + itemChg.getBrandCode()+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getBuyer())){
			planSql+=" and (i.Buyer1= '"+itemChg.getBuyer()+"'"+" or i.buyer2= '" + itemChg.getBuyer()+"')";
		}
		if(StringUtils.isNotBlank(itemChg.getDepartment2())){
			planSql+=" and (e1.department2 = '"+itemChg.getDepartment2()+"'"+" or e2.Department2 = '" +itemChg.getDepartment2()+"')" ;
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			planSql+="  and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+c.Code+'%') ";
		}
		if(StringUtils.isNotBlank(itemChg.getSupplCode())){
			planSql+=" and i.SupplCode='" + itemChg.getSupplCode()+"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getNotContainPlan())){
			//planSql+=" and i.SqlCode not in ('1052','1943')  " ;
			planSql +=" and i.ItemType2 <> '0267' " ;
		}
		if(StringUtils.isNotBlank(itemChg.getIsOutSet())){
			planSql+=" and ip.IsOutSet=  '" + itemChg.getIsOutSet() +"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getSendType())){
			planSql+=" and i.SendType=  '" + itemChg.getSendType() +"'" ;
		}
		
		// 增加角色和干系人的筛选条件
		if (StringUtils.isNotBlank(itemChg.getRole()) && StringUtils.isNotBlank(itemChg.getEmpCode())) {
			planSql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = ip.CustCode and in_a.Role = '" + itemChg.getRole() + "' and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getRole())) {
			planSql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = ip.CustCode and in_a.Role = '" + itemChg.getRole() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getEmpCode())) {
			planSql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = ip.CustCode and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		}
				
		planSql+="  group by c.Code,c.Address,c.CustType,ContainMain,ContainSoft,ContainInt," +
				" ContainCup,MainDisc,SoftDisc,IntegrateDisc,CupBoardDisc,c.SaleType  ";
		
		String chgPlanSql=" select a.Code,a.Address,a.CustType,round(sum(a.xse),0) xse,'全案' Xslx "
                  + ",round(sum(a.chgAmount),0) chgAmount,round(sum(a.planAmount),0) planAmount,sum(a.PlanDisc) PlanDisc," +
                  " sum(a.ChgDiscAmount) ChgDiscAmount, sum(a.ChgDiscCost) ChgDiscCost,a.saleType " 
                  //"sum(case when b.BefAmount < 0 then -b.DiscAmount else b.DiscAmount  end) ChgDiscAmount,  sum(case when b.BefAmount < 0 then -b.DiscCost  else b.DiscCost end) ChgDiscCost "
                  + " from (" + chgSql_ + " union all " + planSql + " ) a group by a.Code, a.Address, a.CustType,a.SaleType ";
		
		sql= "select * from ( select a.*,b.Desc1 custTypeDescr,x1.note SaleTypeDescr, "
            + " cast(case when a.CustType='2' then dbo.fGetEmpNameChi(a.Code,'00') else dbo.fGetEmpNameChi(a.Code,'50') end as nvarchar(1000)) SoftDesignDescr,"
            + " cast(case when a.CustType='2' then dbo.fGetDept23Descr(a.Code,'00') else dbo.fGetDept23Descr(a.Code,'50') end as nvarchar(1000)) Dept2Descr ,"
            + " cast(case when a.CustType='2' then dbo.fGetEmpPositionDescr(a.Code,'00') else dbo.fGetEmpPositionDescr(a.Code,'50') end as nvarchar(1000)) softposition ,"
            + " cast( dbo.fGetEmpNameChi(a.Code,'24') as nvarchar(1000)) AgainManDescr,"
            + " cast( dbo.fGetEmpNameChi(a.Code,'01') as nvarchar(1000)) BusinessManDescr," 
            + " cast( dbo.fGetEmpPositionDescr(a.Code,'01') as nvarchar(1000)) businessManPosition,"
            + " cast( dbo.fGetDept23Descr(a.Code,'01') as nvarchar(1000)) businessManDeptDescr "
            + " from (" + chgPlanSql + ") a " +
            	" left join tCustType b on b.Code = a.CustType " +
            	" left join tXtdm x1 on x1.id='SALETYPE' and x1.cbm = a.SaleType";
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a  ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findRbWgPageBy_cust_Sql(Page<Map<String,Object>> page, ItemChg itemChg) {
		List<Object> list = new ArrayList<Object>();                                                                                                       
		
		String sql="select a.Code,a.Address,a.CustType,'全案' Xslx,"
			    + " round(sum(case when d.IsOutSet='1' then d.LineAmount else round(round(d.Qty*isnull(iad.ProjectCost,e.ProjectCost)*d.Markup/100,0)+d.ProcessCost,0) end),0) xse,x1.note SaleTypeDescr "
			    + " from tCustomer a "
			    + " inner join tItemReq d on a.Code=d.CustCode "
			    + " inner join tItem e on d.ItemCode=e.Code "
			    + " inner join tItemType2 f on e.ItemType2=f.Code "
			    + " left join tEmployee e1 on e.Buyer1=e1.Number "
			    + " left join tEmployee e2 on e.Buyer2=e2.Number "
			    + " left join tItemAppDetail iad on d.PK=iad.ReqPk " +
			    " left join tXtdm x1 on x1.cbm = a.SaleType and x1.id = 'SaleType'" 
			    + " where 1=1 and a.Status='5'";
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and d.ItemType1 =  '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			sql+=" and a.CustCheckDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			sql+=" and a.CustCheckDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and a.Expired='F'";
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			sql+=" and a.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(itemChg.getBrandCode())){
			sql+=" and e.sqlCode = '" + itemChg.getBrandCode()+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getBuyer())){
			sql+=" and (e.Buyer1= '"+itemChg.getBuyer()+"' or e.buyer2= '" + itemChg.getBuyer()+"')";
		}
		if(StringUtils.isNotBlank(itemChg.getDepartment2())){
			sql+=" and (e1.department2 = '"+itemChg.getDepartment2()+"'"+" or e2.department2 = '"+itemChg.getDepartment2()+"')";
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			sql+="  and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+e.Code+'%') ";
		}
		if(StringUtils.isNotBlank(itemChg.getSupplCode())){
			sql+=" and c.SupplCode='" + itemChg.getSupplCode()+"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getIsOutSet())){
			sql+=" and d.IsOutSet=  '" + itemChg.getIsOutSet() +"'" ;
		}
		if(StringUtils.isNotBlank(itemChg.getSendType())){
			sql+=" and e.SendType=  '" + itemChg.getSendType() +"'" ;
		}
		
		// 增加角色和干系人的筛选条件
		if (StringUtils.isNotBlank(itemChg.getRole()) && StringUtils.isNotBlank(itemChg.getEmpCode())) {
			sql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = a.Code and in_a.Role = '" + itemChg.getRole() + "' and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getRole())) {
			sql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = a.Code and in_a.Role = '" + itemChg.getRole() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getEmpCode())) {
			sql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = a.Code and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		}
		
		sql+="  group by a.Code,a.Address,a.CustType,x1.note  ";
		
		String sqltxt=" select * from( select a.*,b.desc1 custTypeDescr, "
            + " cast(case when a.CustType='2' then dbo.fGetEmpNameChi(a.Code,'00') else dbo.fGetEmpNameChi(a.Code,'50') end as nvarchar(1000)) SoftDesignDescr,"
            + " cast(case when a.CustType='2' then dbo.fGetDept23Descr(a.Code,'00') else dbo.fGetDept23Descr(a.Code,'50') end as nvarchar(1000)) Dept2Descr"
            + " from (" + sql + ") a" +
            	" left join tCustType b on b.Code = a.CustType ";
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sqltxt += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sqltxt += ") a  ";
		}
		
		return this.findPageBySql(page, sqltxt, list.toArray());
	}
	
	
	/**
	 * 增减优惠金额——按增减统计
	 * 按楼盘
	 * @param itemChg
	 * @return
	 */
	public String getYHJE_rbzj_by_cust_Sql(ItemChg itemChg){
		//rbzj
		String sql="  select isnull(sum(case when a.BefAmount>0 then a.DiscAmount else a.DiscAmount*-1 end),0) yhje "
            + " from tItemChg a "
            + " left join tCustomer c on a.CustCode=c.Code "
            + " where a.Status='2'   " +
				"  " ;
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and a.ItemType1 =  '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			sql+="  and a.ConfirmDate>='" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			sql+="  and a.ConfirmDate<='" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and a.Expired='F'";
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			sql+=" and c.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			sql+="  and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+c.Code+'%') ";
		}
		
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return  map.get("yhje").toString();
		}
		return "";
	}
	/**
	 * 按材料类型2——按结算时间统计需求
	 * 增减优惠 
	 * @param itemChg
	 * @return
	 */
	public String getYHJE_rbwg_by_cust_Sql(ItemChg itemChg){
		//rbWg
		String sql=" select isnull(sum(case when g.BefAmount>0 then g.DiscAmount else g.DiscAmount*-1 end),0) yhje "
				      + "from tCustomer a "
				      + "inner join tItemChg g on g.custCode=a.Code "
				      + "where 1=1 and a.Status='5'";
						
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and g.ItemType1 = '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			sql+="  and a.CustCheckDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			sql+="  and a.CustCheckDate<='" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and a.Expired='F'";
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			sql+=" and a.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			sql+="  and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+a.Code+'%') ";
		}
		
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return  map.get("yhje").toString();
		}
		return "";
	}
	
	/**
	 * 独立销售优惠
	 * 楼盘地址——按增减统计
	 * @param itemChg
	 * @return
	 */
	public String getDLYH_rbzj_by_cust_Sql(ItemChg itemChg){
		
		String sql=" select isnull(sum(case when a.Type='R' then (a.Amount-a.BefAmount)*-1 else a.Amount-a.BefAmount end),0) yhje "
            + " from tSalesInvoice a"
            + " where a.Status='CONFIRMED'" ;
		
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and a.ItemType1 = '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			sql+="  and a.getItemDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			sql+="  and a.getItemDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and a.Expired='F'";
		}
		
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return  map.get("yhje").toString();
		}
		return "";
	}
	
	/**
	 * 按材料类型2——按结算时间需求
	 * 独立销售优惠
	 * @param itemChg
	 * @return
	 */
	public String getDLYH_rbwg_by_cust_Sql(ItemChg itemChg){
		
		String sql=" select isnull(sum(case when a.Type='R' then (a.Amount-a.BefAmount)*-1 else a.Amount-a.BefAmount end),0) yhje "
            + " from tSalesInvoice a "
            + " where 1=1  " ;
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and a.ItemType1 = '"+ itemChg.getItemType1()+"'";
		}
		if(itemChg.getDateFrom()!=null){
			sql+="  and a.getItemDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
		}
		if(itemChg.getDateTo()!=null){
			sql+="  and a.getItemDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and a.Expired='F'";
		}
		
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return  map.get("yhje").toString();
		}
		return "";
	}
	
	/**
	 * 独立销售明细
	 * @param page
	 * @param itemChg
	 * @return
	 */
	public Page<Map<String,Object>> findSaleDetailSql(Page<Map<String,Object>> page, ItemChg itemChg) {
		List<Object> list = new ArrayList<Object>();                                                                                                       
		
		String sql = " select  * from ( select si.No,si.Date,sc.Desc1 CustDescr,sc.Address,sid.ITCode ItemCode,i.Descr ItemDescr,"
          + " si.type,s1.Note TypeDescr,sid.Qty,sid.BCost,sid.UnitPrice,u.Descr Uom,sid.Markup,"
          + " case when si.Type = 'R' then sid.Qty*sid.UnitPrice * -1 else sid.Qty*sid.UnitPrice end BefLineAmount,"
          + " case when si.Type = 'R' then sid.LineAmount * -1 else sid.LineAmount end LineAmount,"
          + " case when si.Type = 'R' then sid.Qty * sid.BCost * -1 else sid.Qty * sid.BCost end Cost,"
          + " sid.Remarks,sid.LastUpdate,sid.LastUpdatedBy,sid.Expired,sid.ActionLog"
          + " from tSalesInvoice si"
          + " inner join tSalesInvoiceDetail sid on si.No=sid.SINo"
          + " inner join tItem i on sid.ITCode=i.Code"
          + " left join tUOM u on u.Code=i.Uom"
          + " left join tSaleCust sc on si.CustCode=sc.Code"
          + " left join tXTDM s1 on si.Type=s1.CBM and s1.ID='SALESINVTYPE'"
          + " left join tEmployee e1 on i.Buyer1=e1.Number "
          + " left join tEmployee e2 on i.Buyer2=e2.Number "
          + " where si.Status='CONFIRMED' " +
          "" ;
		 
		if(StringUtils.isNotBlank(itemChg.getItemType2())){
			sql+=" and i.itemType2= ?";
			list.add(itemChg.getItemType2());
		}
		if(StringUtils.isNotBlank(itemChg.getBrandCode())){
			sql+=" and i.sqlCode = ? ";
			list.add(itemChg.getBrandCode());
		}
		if(itemChg.getDateFrom()!=null){
			sql+=" and si.getItemDate >=? ";
			list.add(new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime()));
		}
		if(itemChg.getDateTo()!=null){
			sql+="and si.getItemDate <= ?";
			list.add(new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and si.expired='F' ";
			 
		}
		if(StringUtils.isNotBlank(itemChg.getBuyer())){
			sql+=" and (i.Buyer1 = ? or i.Buyer2 = ? )";
			list.add(itemChg.getBuyer());
			list.add(itemChg.getBuyer());
		}
		if(StringUtils.isNotBlank(itemChg.getDepartment2())){
			sql+=" and (e1.department2= ? or e2.department2 = ? ) ";
			list.add(itemChg.getDepartment2());
			list.add(itemChg.getDepartment2());
		}
		if(StringUtils.isNotBlank(itemChg.getSupplCode())){
			sql+=" and i.SupplCode=? ";
			list.add(itemChg.getSupplCode());
		}
		
		// 增加角色和干系人的筛选条件
		if (StringUtils.isNotBlank(itemChg.getRole()) && StringUtils.isNotBlank(itemChg.getEmpCode())) {
			sql += 
					" and exists (" +
					"  select 1 from tSaleStakeHolder in_a " +
					"  where in_a.SINo = si.No and in_a.Role = '" + itemChg.getRole() + "' and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getRole())) {
			sql += 
					" and exists (" +
					"  select 1 from tSaleStakeHolder in_a " +
					"  where in_a.SINo = si.No and in_a.Role = '" + itemChg.getRole() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getEmpCode())) {
			sql += 
					" and exists (" +
					"  select 1 from tSaleStakeHolder in_a " +
					"  where in_a.SINo = si.No and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		}
		if(StringUtils.isNotBlank(itemChg.getNotContainPlan())){
			//sql +=" and i.SqlCode not in ('1052','1943')  " ;
			sql +=" and i.ItemType2 <> '0267' " ;
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a   ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 材料预算明细
	 * @param page
	 * @param itemChg
	 * @return
	 */
	public Page<Map<String,Object>> findItemPlanSql(Page<Map<String,Object>> page, ItemChg itemChg) {
		List<Object> list = new ArrayList<Object>();                                                                                                       
		
		String sql = " select  * from (select ip.CustCode,ip.ProjectQty,ip.Qty,case when ip.IsOutSet='1' then ip.UnitPrice else i.ProjectCost end UnitPrice,ip.ProcessCost,ip.Cost,"
          + " case when ip.IsOutSet='1' then ip.BefLineAmount else i.ProjectCost*ip.Qty end BefLineAmount,"
          + " ip.Markup,case when ip.IsOutSet='1' then ip.LineAmount else round(round(i.ProjectCost*ip.Qty*ip.Markup/100,0)+ip.ProcessCost,0) end LineAmount,"
          + " round(ip.Qty* case when ip.IsOutSet='1' then ip.UnitPrice else i.ProjectCost end * ip.Markup/100,0) TmpLineAmount,"
          + " ip.DispSeq,"
          + " ip.Remark,ip.LastUpdate,ip.LastUpdatedBy,ip.ActionLog,ip.Expired,"
          + " c.Address,fa.Descr FixAreaDescr,ip.ItemCode,i.Descr ItemDescr,ipd.Descr IntProdDescr,"
          + " u.Descr Uom,sql.Descr SqlCodeDescr,ip.Qty*ip.Cost TotalCost,x1.NOTE IsOutSetDescr,e3.NameChi BusinessManDescr,"
          + " d1.Desc2 BusinessManDepartment,e4.NameChi DesignManDescr,d2.Desc2 DesignManDepartment,s.Descr SupplierDescr "
          + " from tItemPlan ip"
          + " inner join tCustomer c on ip.CustCode=c.Code"
          + " inner join tItem i on ip.ItemCode=i.Code"
          + " left join tBrand sql on sql.code = i.sqlcode"
          + " left join tFixArea fa on fa.Pk = ip.FixAreaPK"
          + " left join tIntProd ipd on ipd.PK = ip.IntProdPK"
          + " left join tUom u on i.Uom = u.Code"
          + " left join tEmployee e1 on i.Buyer1=e1.Number "
          + " left join tEmployee e2 on i.Buyer2=e2.Number "
          + " left join tXTDM x1 on x1.ID='YESNO' and x1.CBM=ip.IsOutSet "
          + " left join tEmployee e3 on c.BusinessMan=e3.Number "
          + " left join tDepartment1 d1 on e3.Department1=d1.Code "
          + " left join tEmployee e4 on c.DesignMan=e4.Number "
          + " left join tDepartment1 d2 on e4.Department1=d2.Code "
          + " left join tSupplier s on i.SupplCode=s.Code "
          + " where c.SignDate is not null " ;

		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and i.itemType1= ? ";
			list.add(itemChg.getItemType1());
			if("ZC".equals(itemChg.getItemType1())){
				sql+="and ((c.ContainMain=1 and ip.IsService=0) or (c.ContainMainServ=1 and ip.IsService=1))";			
			}
			if("RZ".equals(itemChg.getItemType1())){
				sql+="and c.ContainSoft=1";
			}
			if("JC".equals(itemChg.getItemType1())){
				sql+=" and ((c.ContainInt=1 and ipd.IsCupboard='0') or (c.ContainCup=1 and ipd.IsCupboard='1'))";
			}
		}else {
			sql+="and ("
             + " (i.ItemType1='ZC' and ((c.ContainMain=1 and ip.IsService=0) or (c.ContainMainServ=1 and ip.IsService=1))) or "
             + " (i.ItemType1='RZ' and c.ContainSoft=1) or "
             + " (i.ItemType1='JC' and ((c.ContainInt=1 and ipd.IsCupboard='0') or (c.ContainCup=1 and ipd.IsCupboard='1')))"
             + " )";
		}
		
		if(StringUtils.isNotBlank(itemChg.getItemType2())){
			sql+=" and i.itemType2= ?";
			list.add(itemChg.getItemType2());
		}
		if(StringUtils.isNotBlank(itemChg.getBrandCode())){
			sql+=" and i.sqlCode = ? ";
			list.add(itemChg.getBrandCode());
		}
		if(itemChg.getDateFrom()!=null){
			sql+=" and c.SignDate >=? ";
			list.add(new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime()));
		}
		if(itemChg.getDateTo()!=null){
			sql+="and c.SignDate <= ?";
			list.add(new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and c.expired='F' ";
			 
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			sql+=" and c.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(itemChg.getBuyer())){
			sql+=" and (i.Buyer1 = ? or i.Buyer2 = ? ) ";
			list.add(itemChg.getBuyer());
			list.add(itemChg.getBuyer());
		}
		if(StringUtils.isNotBlank(itemChg.getDepartment2())){
			sql+=" and (e1.department2= ? or e2.department2 = ? ) ";
			list.add(itemChg.getDepartment2());
			list.add(itemChg.getDepartment2());
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			sql+=" and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+c.Code+'%') ";
		}
		if(StringUtils.isNotBlank(itemChg.getSupplCode())){
			sql+=" and i.SupplCode=? ";
			list.add(itemChg.getSupplCode());
		}
		if(StringUtils.isNotBlank(itemChg.getCustCode())){
			sql+=" and  ip.CustCode= ?";
			list.add(itemChg.getCustCode());
		}
		if(StringUtils.isNotBlank(itemChg.getNotContainPlan())){
			//sql+=" and i.SqlCode not in ('1052','1943') ";
			sql +=" and i.ItemType2 <> '0267' " ;
		}
		if(StringUtils.isNotBlank(itemChg.getIsOutSet())){
			sql+=" and ip.IsOutSet=?" ;
			list.add(itemChg.getIsOutSet());
		}
		if(StringUtils.isNotBlank(itemChg.getSendType())){
			sql+=" and i.SendType= ?" ;
			list.add(itemChg.getSendType());
		}
		
		// 增加角色和干系人的筛选条件
		if (StringUtils.isNotBlank(itemChg.getRole()) && StringUtils.isNotBlank(itemChg.getEmpCode())) {
			sql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = ip.CustCode and in_a.Role = '" + itemChg.getRole() + "' and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getRole())) {
			sql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = ip.CustCode and in_a.Role = '" + itemChg.getRole() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getEmpCode())) {
			sql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = ip.CustCode and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		}
		if(StringUtils.isNotBlank(itemChg.getNotContainPlan())){
			//sql +=" and i.SqlCode not in ('1052','1943')  " ;
			sql +=" and i.ItemType2 <> '0267' " ;
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.CustCode,a.DispSeq ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 增减明细
	 * @param page
	 * @param itemChg
	 * @return
	 */
	public Page<Map<String,Object>> findChgDetailSql(Page<Map<String,Object>> page, ItemChg itemChg) {
		List<Object> list = new ArrayList<Object>();                                                                                                       
		
		String sql = " select  * from (select ic.No,c.Address,icd.ReqPK,icd.FixAreaPK,fa.Descr FixAreaDescr,icd.IntProdPK,ip.Descr IntProdDescr,"
          + " icd.ItemCode,i.Descr ItemDescr,u.Descr Uom,icd.Qty,"
          + " icd.Cost,case when icd.IsOutSet='1' then icd.UnitPrice else i.ProjectCost end UnitPrice,"
          + " case when icd.IsOutSet='1' then icd.BefLineAmount else i.ProjectCost*icd.Qty end BefLineAmount,icd.Markup,"
          + " case when icd.IsOutSet='1' then icd.LineAmount else round(round(i.ProjectCost*icd.Qty*icd.Markup/100,0)+icd.ProcessCost,0) end LineAmount,"
          + " icd.ProcessCost,(icd.Qty* case when icd.IsOutSet='1' then icd.UnitPrice else i.ProjectCost end * icd.Markup/100) TmpLineAmount,"
          + " icd.Remarks,icd.LastUpdate,icd.LastUpdatedBy,icd.ActionLog,icd.Expired,"
          + " icd.Qty*icd.Cost TotalCost,x1.NOTE IsOutSetDescr,e3.NameChi BusinessManDescr,d1.Desc2 BusinessManDepartment,"
          + " e4.NameChi DesignManDescr,d2.Desc2 DesignManDepartment,s.Descr SupplierDescr "
          + " from tItemChg ic"
          + " inner join tItemChgDetail icd on ic.No = icd.No"
          + " inner join tCustomer c on ic.CustCode=c.Code"
          + " inner join tItem i on icd.ItemCode = i.Code"
          + " left outer join tFixArea fa on fa.PK=icd.FixAreaPK "
          + " left outer join tIntProd ip on ip.PK=icd.IntProdPK"
          + " left outer join tBrand sql on sql.code=i.SqlCode"
          + " left outer join tUom u on i.Uom=u.Code"
          + " left join tEmployee e1 on i.Buyer1=e1.Number "
          + " left join tEmployee e2 on i.Buyer2=e2.Number "
          + " left join tXTDM x1 on x1.ID='YESNO' and x1.CBM=icd.IsOutSet "
          + " left join tEmployee e3 on c.BusinessMan=e3.Number "
          + " left join tDepartment1 d1 on e3.Department1=d1.Code "
          + " left join tEmployee e4 on c.DesignMan=e4.Number "
          + " left join tDepartment1 d2 on e4.Department1=d2.Code "
          + " left join tSupplier s on i.SupplCode=s.Code "
          + " where ic.Status = '2' " ;
		
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and i.itemType1= ? ";
			list.add(itemChg.getItemType1());
		}
		if(StringUtils.isNotBlank(itemChg.getItemType2())){
			sql+=" and i.itemType2= ?";
			list.add(itemChg.getItemType2());
		}
		if(StringUtils.isNotBlank(itemChg.getBrandCode())){
			sql+=" and i.sqlCode = ? ";
			list.add(itemChg.getBrandCode());
		}
		if(itemChg.getDateFrom()!=null){
			sql+=" and ic.ConfirmDate >= ? ";
			list.add(itemChg.getDateFrom());
		}
		if(itemChg.getDateTo()!=null){
			sql+="and ic.ConfirmDate <= dateAdd(d,1,?)";
			list.add(itemChg.getDateTo());
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and ic.expired='F' ";
			 
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			sql+=" and c.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(itemChg.getBuyer())){
			sql+=" and (i.Buyer1 = ? or i.Buyer2 = ? )";
			list.add(itemChg.getBuyer());
			list.add(itemChg.getBuyer());
		}
		if(StringUtils.isNotBlank(itemChg.getDepartment2())){
			sql+=" and (e1.department2= ? or e2.department2 = ? ) ";
			list.add(itemChg.getDepartment2());
			list.add(itemChg.getDepartment2());
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			sql+=" and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+c.Code+'%') ";
		}
		if(StringUtils.isNotBlank(itemChg.getSupplCode())){
			sql+=" and i.SupplCode=? ";
			list.add(itemChg.getSupplCode());
		}
		if(StringUtils.isNotBlank(itemChg.getNotContainPlan())){
			//sql+=" and i.SqlCode not in ('1052','1943') ";
			sql +=" and i.ItemType2 <> '0267' " ;
		}
		if(StringUtils.isNotBlank(itemChg.getCustCode())){
			sql+=" and ic.CustCode = ? ";
			list.add(itemChg.getCustCode());
		}
		if(StringUtils.isNotBlank(itemChg.getIsOutSet())){
			sql+=" and icd.IsOutSet=?" ;
			list.add(itemChg.getIsOutSet());
		}
		if(StringUtils.isNotBlank(itemChg.getSendType())){
			sql+=" and i.SendType= ?" ;
			list.add(itemChg.getSendType());
		}
		
		// 增加角色和干系人的筛选条件
		if (StringUtils.isNotBlank(itemChg.getRole()) && StringUtils.isNotBlank(itemChg.getEmpCode())) {
			sql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = ic.CustCode and in_a.Role = '" + itemChg.getRole() + "' and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getRole())) {
			sql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = ic.CustCode and in_a.Role = '" + itemChg.getRole() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getEmpCode())) {
			sql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = ic.CustCode and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		}
		if(StringUtils.isNotBlank(itemChg.getNotContainPlan())){
			//sql +=" and i.SqlCode not in ('1052','1943')  " ;
			sql +=" and i.ItemType2 <> '0267' " ;
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a   ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 材料需求明细
	 * @param page
	 * @param itemChg
	 * @return
	 */
	public Page<Map<String,Object>> findItemReqSql(Page<Map<String,Object>> page, ItemChg itemChg) {
		List<Object> list = new ArrayList<Object>();                                                                                                       
		
		String sql = " select  * from (select ir.CustCode,ir.Qty,case when ir.IsOutSet='1' then ir.UnitPrice else isnull(iad.ProjectCost,i.ProjectCost) end UnitPrice,ir.ProcessCost,ir.Cost,"
          + " case when ir.IsOutSet='1' then ir.BefLineAmount else isnull(iad.ProjectCost,i.ProjectCost)*ir.Qty end BefLineAmount,"
          + " ir.Markup,case when ir.IsOutSet='1' then ir.LineAmount else round(round(isnull(iad.ProjectCost,i.ProjectCost)*ir.Qty*ir.Markup/100,0)+ir.ProcessCost,0) end LineAmount,"
          + " round(ir.Qty* case when ir.IsOutSet='1' then ir.UnitPrice else isnull(iad.ProjectCost,i.ProjectCost) end * ir.Markup/100, 0) TmpLineAmount,"
          + " ir.DispSeq,"
          + " ir.Remark,ir.LastUpdate,ir.LastUpdatedBy,ir.ActionLog,ir.Expired,"
          + " c.Address,fa.Descr FixAreaDescr,ir.ItemCode,i.Descr ItemDescr,ipd.Descr IntProdDescr,"
          + " u.Descr Uom,sql.Descr SqlCodeDescr,ir.Qty*ir.Cost TotalCost,x1.NOTE IsOutSetDescr "
          + " from tItemReq ir"
          + " inner join tCustomer c on ir.CustCode=c.Code"
          + " inner join tItem i on ir.ItemCode=i.Code"
          + " left join tBrand sql on sql.code = i.sqlcode"
          + " left join tFixArea fa on fa.Pk = ir.FixAreaPK"
          + " left join tIntProd ipd on ipd.PK = ir.IntProdPK"
          + " left join tUom u on i.Uom = u.Code"
          + " left join tEmployee e1 on i.Buyer1=e1.Number "
          + " left join tEmployee e2 on i.Buyer2=e2.Number "
          + " left join tItemAppDetail iad on ir.PK=iad.ReqPk " 
          + " left join tXTDM x1 on x1.ID='YESNO' and x1.CBM=ir.IsOutSet "
          + " where c.Status='5' " ;
		
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			sql+=" and i.itemType1= ? ";
			list.add(itemChg.getItemType1());
		}
		if(StringUtils.isNotBlank(itemChg.getItemType2())){
			sql+=" and i.itemType2= ?";
			list.add(itemChg.getItemType2());
		}
		if(StringUtils.isNotBlank(itemChg.getBrandCode())){
			sql+=" and i.sqlCode = ? ";
			list.add(itemChg.getBrandCode());
		}
		if(itemChg.getDateFrom()!=null){
			sql+=" and c.CustCheckDate >=? ";
			list.add(new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime()));
		}
		if(itemChg.getDateTo()!=null){
			sql+="and c.CustCheckDate <= ?";
			list.add(new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(itemChg.getExpired())){
			sql+=" and c.expired='F' ";
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			sql+=" and c.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(itemChg.getBuyer())){
			sql+=" and (i.Buyer1 = ? or i.Buyer2 = ? )";
			list.add(itemChg.getBuyer());
			list.add(itemChg.getBuyer());
		}
		if(StringUtils.isNotBlank(itemChg.getDepartment2())){
			sql+=" and (e1.department2= ? or e2.department2 = ? ) ";
			list.add(itemChg.getDepartment2());
			list.add(itemChg.getDepartment2());
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			sql+=" and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+c.Code+'%') ";
		}
		if(StringUtils.isNotBlank(itemChg.getSupplCode())){
			sql+=" and i.SupplCode=? ";
			list.add(itemChg.getSupplCode());
		}
		if(StringUtils.isNotBlank(itemChg.getCustCode())){
			sql+="  and ir.CustCode= ? ";
			list.add(itemChg.getCustCode());
		}
		
		// 增加角色和干系人的筛选条件
		if (StringUtils.isNotBlank(itemChg.getRole()) && StringUtils.isNotBlank(itemChg.getEmpCode())) {
			sql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = ir.CustCode and in_a.Role = '" + itemChg.getRole() + "' and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getRole())) {
			sql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = ir.CustCode and in_a.Role = '" + itemChg.getRole() + "'" +
					" )";
		} else if (StringUtils.isNotBlank(itemChg.getEmpCode())) {
			sql += 
					" and exists (" +
					"  select 1 from tCustStakeholder in_a " +
					"  where in_a.CustCode = ir.CustCode and in_a.EmpCode = '" + itemChg.getEmpCode() + "'" +
					" )";
		}
		if(StringUtils.isNotBlank(itemChg.getNotContainPlan())){
			//sql+=" and i.SqlCode not in ('1052','1943')  " ;
			sql +=" and i.ItemType2 <> '0267' " ;
		}

		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a  ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public String getItem1byItem2(String itemType2){
		String sql = " select ItemType1 from tItemType2 where Code= ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itemType2});
		if(list != null && list.size() > 0){
			return list.get(0).get("ItemType1").toString();
		}
		return "";
	}
	
	public String getDLYH_ysyh_by_itemType_Sql(ItemChg itemChg){
		String sql=" select isnull(sum( " ;
		if(StringUtils.isNotBlank(itemChg.getItemType1())){
			if("ZC".equals(itemChg.getItemType1())){
				sql+=" c.MainDisc*c.ContainMain ";
			}else if("RZ".equals(itemChg.getItemType1())){
				sql+=" c.SoftDisc*c.ContainSoft ";
			}else if("JC".equals(itemChg.getItemType1())){
				sql+=" c.IntegrateDisc*c.ContainInt+c.CupBoardDisc*c.ContainCup ";
			}
		}else{
			sql+=" c.MainDisc*c.ContainMain+c.SoftDisc*c.ContainSoft+c.IntegrateDisc*c.ContainInt+c.CupBoardDisc*c.ContainCup ";
		}
		sql+="),0) yhje from tCustomer c where 1=1 ";
		if("0".equals(itemChg.getCountType())){
			if(itemChg.getDateFrom()!=null){
				sql+="  and c.SignDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
			}
			if(itemChg.getDateTo()!=null){
				sql+="  and c.SignDate<='" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
			}
		}if("1".equals(itemChg.getCountType())){
			if(itemChg.getDateFrom()!=null){
				sql+="  and c.CustCheckDate>= '" +new Timestamp(DateUtil.startOfTheDay( itemChg.getDateFrom()).getTime())+"'";
			}
			if(itemChg.getDateTo()!=null){
				sql+="  and c.CustCheckDate<= '" +new Timestamp(DateUtil.endOfTheDay( itemChg.getDateTo()).getTime())+"'";
			}
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			sql+=" and c.custType in "+ "('"+itemChg.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isBlank(itemChg.getContainCmpCust())){
			sql+="  and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+c.Code+'%') ";
			
		}
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return  map.get("yhje").toString();
		}
		return "";
	}
}
