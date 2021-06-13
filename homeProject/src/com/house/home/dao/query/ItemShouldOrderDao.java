package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.design.Customer;
@Repository
@SuppressWarnings("serial")
public class ItemShouldOrderDao extends BaseDao {
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String beginOrEnd="";
		if("1".equals(customer.getStatistcsMethod())){
			beginOrEnd="Begin";
		}else{
			beginOrEnd="End";
		}	
		String sql = "select *,case when a.shouldOrderDate > getdate() then 0 else  dateDiff(d,a.shouldOrderDate,getdate()) end overDays from (select eZCGJ.namechi zcgjnamechi,a.Address,a.Code CustCode,d.ConfItemType,f.Descr,case when d."+beginOrEnd+"DateType='1' then e.BeginDate when d."+beginOrEnd+"DateType='2' then e.EndDate when d."+beginOrEnd+"DateType='3' then e.ConfirmDate end nodeTriggerDate, "
			+"dateadd(DAY,d."+beginOrEnd+"AddDays,case when d."+beginOrEnd+"DateType='1' then e.BeginDate when d."+beginOrEnd+"DateType='2' then e.EndDate when d."+beginOrEnd+"DateType='3' then e.ConfirmDate end)shouldOrderDate, "
			+"x3.NOTE payType,isnull(x1.NOTE,'未确认') isConfirmed,g.LastUpdate confirmDate,h.Descr shouldOrderNode,x2.NOTE nodeDateType,b.Desc1 CustTypeDescr,k.NameChi ProjectManDescr,l.Desc1 PrjDeptDescr,n.Remarks ConfirmRemarks "
			+"from tCustomer a "
			+"left join tCustType b on a.CustType = b.Code "
			+"left join tItemSendNode c on b.WorkerClassify = c.WorkerClassify "
			+"left join tItemSendNodeDetail d on c.Code = d.Code "
//			+"inner join tPrjProg e on dateadd(DAY,d."+beginOrEnd+"AddDays,case when d."+beginOrEnd+"DateType='1' then e.BeginDate when d."+beginOrEnd+"DateType='2' then e.EndDate when d."+beginOrEnd+"DateType='3' then e.ConfirmDate end)" 
//			+" <dateadd(day,1,";
//			if(customer.getEndDate()!=null){
//				sql+="?";
//				list.add(customer.getEndDate());
//			}else{
//				sql+="getdate()";
//			}
//			sql+=") and e.PrjItem=d."+beginOrEnd+"Node and a.Code=e.CustCode "
			+"inner join tPrjProg e on e.PrjItem=d."+beginOrEnd+"Node and a.Code=e.CustCode " 
			+"left join tConfItemType f on f.Code=d.ConfItemType "
			+"left join tCustItemConfirm g on f.Code=g.ConfItemType and a.Code=g.CustCode "
			+"left join tPrjItem1 h on e.PrjItem=h.Code "
			+"left join tBuilder i on a.BuilderCode=i.Code "
			+"left join tRegion j on i.RegionCode=j.Code "
			+"left join tEmployee k on a.ProjectMan=k.Number "
			+"left join tDepartment2 l on k.Department2=l.Code "
			+"left join tXTDM x1 on g.ItemConfStatus=x1.CBM and x1.ID='ITEMCONFSTS' "
			+"left join tXTDM x2 on d."+beginOrEnd+"DateType=x2.CBM and x2.ID='ALARMDAYTYPE' "
			+"left join tXTDM x3 on a.PayType=x3.CBM and x3.ID='TIMEPAYTYPE' "
			+" left join  ( select  max(a.PK)pk,a.CustCode from tCustStakeholder a where role='34' group by a.custCode) zcgj on zcgj.custCode=a.Code" 
			+" left join tCustStakeholder cZCGJ on cZCGJ.pk=zcgj.pk" 
			+" left join temployee eZCGJ on eZCGJ.number=cZCGJ.empCode " 
			+" left join ( "
			+" 		select CustCode,max(No) No "
			+"   	from tCustItemConfProg "
			+"     	where ItemConfStatus='2' "
          	+"    	group by CustCode "
          	+" ) m on m.CustCode = a.Code "
			+" left join tCustItemConfProg n on m.No=n.No "
			+"where a.Status='4' and c.Type='1' "
			+"and exists ( "//过滤掉明细为空的记录
			+"   select 1  "
            +"   from tItemReq ex_a "
            +"   left join tItem ex_b on ex_a.ItemCode = ex_b.Code "
            +"   left join tItemType2 ex_c on ex_b.ItemType2 = ex_c.Code "
            +"   left join tItemType3 ex_d on ex_b.ItemType3 = ex_d.Code "
            +"   left join tFixArea ex_e on ex_a.FixAreaPK = ex_e.PK "
            +"   where  ex_a.CustCode = a.Code  and ex_a.Qty>0 "
            +"   and exists ( select 1 from   tConfItemTypeDt in_a where  ConfItemType = f.Code "
            +"            and ((ex_b.ItemType2 = in_a.ItemType2 and isnull(in_a.ItemType3,'') <> '' and ex_b.ItemType3 = in_a.ItemType3) "
            +"            or( ex_b.ItemType2 = in_a.ItemType2 and isnull(in_a.ItemType3,'') = '')) ) "
            +"   and not exists ( select 1 from tItemAppDetail in_b where ex_a.PK = in_b.ReqPK ) )"; 
		if(StringUtils.isNotBlank(customer.getAddress())){
			sql+=" and a.Address like ? ";
			list.add("%"+customer.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(customer.getItemType1())){
			sql+=" and c.ItemType1=? ";
			list.add(customer.getItemType1());
		}
		if (StringUtils.isNotBlank(customer.getBuilderCode())) {
			sql += " and a.BuilderCode=? ";
			list.add(customer.getBuilderCode());
		}
		if(StringUtils.isNotBlank(customer.getRegion())){
			String str = SqlUtil.resetStatus(customer.getRegion());
			sql += " and i.RegionCode in (" + str + ")";
		}
		if(StringUtils.isNotBlank(customer.getCustType())){
			String str = SqlUtil.resetStatus(customer.getCustType());
			sql += " and a.CustType in (" + str + ")";
		}
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			String str = SqlUtil.resetStatus(customer.getDepartment2());
			sql += " and k.Department2 in (" + str + ")";
		}
		if(StringUtils.isNotBlank(customer.getPrjItem())){
			sql+=" and h.code = ? ";
			list.add(customer.getPrjItem());
		}
		
		if(customer.getEndDateFrom()!=null){
			sql+=" and dateadd(DAY,d."+beginOrEnd+"AddDays,case when d."+beginOrEnd+"DateType='1' then e.BeginDate when d."+beginOrEnd+"DateType='2' then e.EndDate when d."+beginOrEnd+"DateType='3' then e.ConfirmDate end)>=? ";
			list.add(customer.getEndDateFrom());
		}
		
		if(customer.getEndDateTo()!=null){
			sql+=" and dateadd(DAY,d."+beginOrEnd+"AddDays,case when d."+beginOrEnd+"DateType='1' then e.BeginDate when d."+beginOrEnd+"DateType='2' then e.EndDate when d."+beginOrEnd+"DateType='3' then e.ConfirmDate end) < ? ";
			list.add(DateUtil.addDateOneDay(customer.getEndDateTo()));
		}
		
		if(StringUtils.isNotBlank(customer.getMainBusinessMan())){
			sql+=" and cZCGJ.EmpCode = ? ";
			list.add(customer.getMainBusinessMan());
		}
		
		if(StringUtils.isNotBlank(customer.getCheckStatus())){
			sql +=" and isnull(g.ItemConfStatus ,'1') = ? ";
			list.add(customer.getCheckStatus());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.CustCode asc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	} 

	public Page<Map<String, Object>> findPageBySql_detail(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.ItemCode,b.Descr ItemDescr,c.Descr ItemType2Descr,d.Descr ItemType3Descr,a.Qty,e.Descr FixAreaDescr "
			+"from tItemReq a "
			+"left join tItem b on a.ItemCode = b.Code "
			+"left join tItemType2 c on b.ItemType2=c.Code "
			+"left join tItemType3 d on b.ItemType3=d.Code "
			+"left join tFixArea e on a.FixAreaPK=e.PK "
			+"where a.CustCode=? and a.Qty>0 " 
			+"and exists(select 1 from tConfItemTypeDt in_a where ConfItemType=? " 
			+" and ((b.ItemType2=in_a.ItemType2 and isnull(in_a.ItemType3,'')<>'' and b.ItemType3=in_a.ItemType3) "
			+" or(b.ItemType2=in_a.ItemType2 and isnull(in_a.ItemType3,'')=''))) "
			+"and not exists(select 1 from tItemAppDetail in_b left join tItemApp in_c on in_b.No = in_c.No where a.PK=in_b.ReqPK and in_c.Status <> 'CANCEL')";
		list.add(customer.getCode());
		list.add(customer.getConfItemType());
		if(StringUtils.isNotBlank(customer.getViewAll()) && "0".equals(customer.getViewAll())){
			sql += " and not exists(select 1 from tItemPreAppDetail in_c "
				 + " 	left join tItemPreApp in_d on in_c.No = in_d.No "
				 + "    where in_c.ReqPK = a.PK and in_d.Status not in ('6'))";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.ItemCode asc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
}
