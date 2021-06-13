package com.house.home.dao.insales;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.ItemWHBal;
@SuppressWarnings("serial")
@Repository
public class ItemWHBalDao extends BaseDao {
	
	@Autowired
	private HttpServletRequest request;

	public  Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select *from(select it2.Descr ItemType2Descr,a.ITCode,b.Descr,c.Desc1,a.QtyCal,a.LastUpdate,d.ZWXM,a.Expired," +
				"a.ActionLog,a.LastUpdatedBy, a.AvgCost,a.QtyCal*a.AvgCost CostAmount,a.WHCode,tb.Descr SqlCodeDescr,s1.descr spldescr," +
				"tc.Desc1 CustTypeDescr,dm.Note SaleStragyDescr,b.HasSample,dm2.NOTE HasSampleDescr "+
				"from tItemWHBal a  " +
				"inner join tItem b on a.ITCode=b.Code  " +
				"inner join tWareHouse c on a.WHCode=c.Code  " +
				"left join tbrand tb on b.SqlCode=tb.code " +
				"left join tEmployee e1 on b.buyer1=e1.number "+
		        "left join tEmployee e2 on b.buyer2=e2.number "+
				"inner join tCZYBM d on a.LastUpdatedBy=d.CZYBH  " +
				"left outer join tItemType2 it2 on it2.Code=b.ItemType2 "+
				"left join tSupplier s1 on b.SupplCode=s1.Code "+
				"left join (select Itemcode,max(custtype) custtype from tCustTypeItem where Price=0 group by ItemCode) t on a.ITCode=t.ItemCode "+
				"left join tCustType tc on t.CustType=tc.Code "+
				"left join tXtdm dm on b.saleStragy=dm.cbm and dm.id='SALESTRAGY' " +
				"left join tXtdm dm2 on b.HasSample=dm2.cbm and dm2.id='YESNO' ";
		if(StringUtils.isNotBlank(itemWHBal.getCzybh())){
			sql += "  left join tWareHouseOperater e on c.code=e.whCode and e.czybh=? where 1=1 and e.whCode is not null  ";
			list.add(itemWHBal.getCzybh().trim());
		}else{
			sql+=" where 1=1 ";
		}
		if (StringUtils.isNotBlank(itemWHBal.getWhCode())) {
			sql += " and a.whCode=? ";
			list.add(itemWHBal.getWhCode());
		}
    	if (StringUtils.isNotBlank(itemWHBal.getItCode())) {
			sql += " and a.ITCode=? ";
			list.add(itemWHBal.getItCode());
		}
    	if (StringUtils.isNotBlank(itemWHBal.getDescr())) {
			sql += " and b.Descr like ? ";
			list.add("%"+itemWHBal.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(itemWHBal.getDesc1())) {
			sql += " and c.Desc1 like ? ";
			list.add("%"+itemWHBal.getDesc1()+"%");
		}
    	if (StringUtils.isNotBlank(itemWHBal.getItemType1())) {
			sql += " and b.itemType1=? ";
			list.add(itemWHBal.getItemType1().trim());
		}else{
			UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
			String itemRight="";
			for(String str:uc.getItemRight().trim().split(",")){
				itemRight+="'"+str+"',";
			}
			sql += " and b.ItemType1 in("+itemRight.substring(0, itemRight.length()-1)+") ";
		}
    	if (StringUtils.isNotBlank(itemWHBal.getItemType2())) {
			sql += " and b.itemType2 in ('" + itemWHBal.getItemType2().replaceAll(",", "','") + "')";
		}
    	if(StringUtils.isNotBlank(itemWHBal.getSqlCode())){
    		sql += " and b.sqlCode=? ";
			list.add(itemWHBal.getSqlCode().trim());
    	}
    	if (StringUtils.isNotBlank(itemWHBal.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemWHBal.getLastUpdatedBy());
		}
    	if (StringUtils.isNotBlank(itemWHBal.getDepartment2())) {
			sql += " and (e1.Department2=? or e2.Department2=?) ";
			list.add(itemWHBal.getDepartment2());
			list.add(itemWHBal.getDepartment2());
		}
		if (StringUtils.isBlank(itemWHBal.getExpired()) || "F".equals(itemWHBal.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemWHBal.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemWHBal.getActionLog());
		}
    	sql += " )a";
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.whCode";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	public  Page<Map<String, Object>> findPurchPageBySql(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select it2.Descr ItemType2Descr,a.ITCode,b.Descr,c.Desc1,a.QtyCal,a.LastUpdate,d.ZWXM,a.Expired," +
				"a.ActionLog,a.LastUpdatedBy, a.AvgCost,a.QtyCal*a.AvgCost CostAmount,a.WHCode,tb.Descr SqlCodeDescr  " +
				" from tItemWHBal a  " +
				"left join tItem b on a.ITCode=b.Code  " +
				"left join tWareHouse c on a.WHCode=c.Code  " +
				"left join tbrand tb on b.SqlCode=tb.code " +
				" inner join tCZYBM d on a.LastUpdatedBy=d.CZYBH  " +
				"left outer join tItemType2 it2 on it2.Code=b.ItemType2    ";
		if(StringUtils.isNotBlank(itemWHBal.getCzybh())){
			sql += "  left join tWareHouseOperater e on c.code=e.whCode and e.czybh=? where 1=1 and e.whCode is not null  ";
			list.add(itemWHBal.getCzybh().trim());
		}else{
			sql+=" where 1=1 ";
		}
		if (StringUtils.isNotBlank(itemWHBal.getWhCode())) {
			sql += " and a.whCode=? ";
			list.add(itemWHBal.getWhCode());
		}
    	if (StringUtils.isNotBlank(itemWHBal.getItCode())) {
			sql += " and a.ITCode=? ";
			list.add(itemWHBal.getItCode());
		}
    	if (StringUtils.isNotBlank(itemWHBal.getDescr())) {
			sql += " and b.Descr like ? ";
			list.add("%"+itemWHBal.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(itemWHBal.getDesc1())) {
			sql += " and c.Desc1 like ? ";
			list.add("%"+itemWHBal.getDesc1()+"%");
		}
    	if (StringUtils.isNotBlank(itemWHBal.getItemType1())) {
			sql += " and b.itemType1=? ";
			list.add(itemWHBal.getItemType1().trim());
		}
    	if (StringUtils.isNotBlank(itemWHBal.getItemType2())) {
			sql += " and b.itemType2=? ";
			list.add(itemWHBal.getItemType2());
		}
    	if(StringUtils.isNotBlank(itemWHBal.getSqlCode())){
    		sql += " and b.sqlCode=? ";
			list.add(itemWHBal.getSqlCode().trim());
    	}
    	if (StringUtils.isNotBlank(itemWHBal.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemWHBal.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemWHBal.getExpired()) || "F".equals(itemWHBal.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemWHBal.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemWHBal.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.whCode";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageGroupByItem(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select * from("
		+"select it2.Descr ItemType2Descr,a.ITCode,b.Descr,sum(a.QtyCal) TotalQty, isnull(d.ConfirmedQty, 0) ConfirmedQty," 
		+"isnull(e.OpenQty, 0) OpenQty, isnull(f.SalesInvoiceQty, 0) SalesInvoiceQty,isnull(g.SampleQty, 0) SampleQty," 
		+"isnull(d.ConfirmedQty, 0)+isnull(e.OpenQty, 0)+isnull(f.SalesInvoiceQty, 0) ApplyQty,isnull(tcon.yqrXqqty,0) yqrXqqty," 
		+"sum(a.QtyCal)-isnull(d.ConfirmedQty, 0)-isnull(e.OpenQty, 0)-isnull(f.SalesInvoiceQty, 0)-isnull(g.SampleQty, 0)+isnull(m1.CgzQty,0) UseQty," 
		+"isnull(m1.CgzQty,0) CgzQty,isnull(m2.XqQty,0) XqQty,isnull(m3.Send30,0) Send30,isnull(tSend7.Send7,0) Send7," 
		+"case when (sum(a.QtyCal)+isnull(m1.CgzQty,0))<>0 then Round((isnull(m2.XqQty,0)+isnull(m4.qty,0))/(sum(a.QtyCal)+isnull(m1.CgzQty,0))*100,0) else 0 end XqZb," 
		+"case when (sum(a.QtyCal)+isnull(m1.CgzQty,0))<>0 then Round(isnull(m3.Send30,0)/(sum(a.QtyCal)+isnull(m1.CgzQty,0))*100,0) else 0 end SendZb,"  
		+"b.MinQty,isnull(m4.qty,0) YgXqQty,isnull(m2.XqQty,0)+isnull(m4.qty,0) AllXqQty,isnull(jdReq.JdXqQty,0) JdXqQty "  
		+"from ("
		//+"--有需求的材料"
		+"select WHCode, ITCode, QtyCal from tItemWHBal union  select '1000' WHCode ,itemcode ITCode ,0 QtyCal "
		+"from (select b.ItemCode  from tCustomer a  inner join tItemReq b on a.code = b.CustCode " 
		+"where a.Status = '4'  and b.Qty - b.SendQty > 0  group by  b.ItemCode  union select a.itemCode " 
		+"from ( select  a.code, c.ItemCode, c.Qty,  c.JudgeSend, case when c.JudgeSend = '1' then x1.itemtype2  else x1.itemtype3 end itemType "
		+"from tCustomer a  inner join tSetItemQuota b on a.Status = '4'  and a.CustType = b.CustType and a.Area between b.FromArea "
		+"and b.ToArea  inner join tSetItemQuotaDetail c on b.no = c.No  inner join tItem x1 on x1.code = c.ItemCode ) a " 
		+"left join ( select  b.ItemCode, c.itemtype2,c.itemtype3, d.CustCode  from  titemappsenddetail a "
		+"inner join tItemAppDetail b on a.RefPk = b.pk " 
		+"inner join titemapp d on b.no = d.No " 
		+"inner join tItem c on b.ItemCode = c.Code ) b on a.code = b.CustCode " 
		+"and (( a.itemType = isnull(b.itemType2, a.ItemType)  and a.JudgeSend = '1') or(a.ItemType=isnull(b.ItemType3,a.ItemType) and a.JudgeSend = '2') ) " 
		+"and b.ItemCode is null group by  a.itemcode) a  where  not exists ( select 1 from   tItemWHBal iw where  iw.ITCode = a.ItemCode )) a " 
		+"inner join tItem b on a.ITCode=b.Code " 
		+"inner join tWareHouse c on a.WHCode=c.Code " 
		+"left join tEmployee e1 on b.buyer1=e1.number "
		+"left join tEmployee e2 on b.buyer2=e2.number "
		+"left join tItemType2 it2 on it2.Code=b.ItemType2 " 
		//+"--领料审核"
		+"left join (select sum(case when bb.type='S' then aa.Qty-aa.SendQty else -(aa.Qty-aa.SendQty) end) ConfirmedQty, aa.ItemCode "   
		+"from tItemAppDetail aa "    
		+"inner join tItemApp bb on aa.No=bb.No "   
		+"where bb.Status='CONFIRMED'  and bb.date>=dateadd(day,-180, getdate())  group by aa.ItemCode) d  on d.ItemCode = a.ITCode "
		//+"--领料申请"
		+"left join (select sum(case when bb.type='S' then aa.Qty else -aa.Qty end) OpenQty,aa.ItemCode "   
		+"from tItemAppDetail aa    inner join tItemApp bb on aa.No=bb.No "   
		+"where (bb.Status='OPEN' or bb.Status='CONRETURN') and  bb.date>=dateadd(day,-180, getdate()) " 
		+"group by aa.ItemCode) e  on e.ItemCode = a.ITCode "
		//+"--未发货销售"
		+"left join (select sum(case when bb.Type='S' then aa.Qty else -aa.Qty end) SalesInvoiceQty,aa.ITCode "   
		+"from tSalesInvoiceDetail aa    inner join tSalesInvoice bb on aa.SINo=bb.No "   
		+"where bb.Status='OPEN'  and  bb.date>=dateadd(day,-180, getdate()) "  
		+"and bb.Expired='F' group by aa.ITCode) f  on f.ITCode = a.ITCode "
		//+"--样品库数量"
		+"left join (select sum(aa.QtyCal) SampleQty,aa.ITCode "   
		+"from tItemWHBal aa "   
		+"inner join tWareHouse bb on aa.WHCode=bb.Code "   
		+"where bb.WareType='2' group by aa.ITCode) g  on g.ITCode = a.ITCode "
		//+"--未到货采购数量"
		+"left join  (select b.ITCode,sum(case when a.Type='S' then b.QtyCal-b.ArrivQty else b.QtyCal*-1 end) CgzQty " 
		+"from tPurchase a inner join tPurchaseDetail b on a.no=b.PUNo " 
		+"where a.Status='OPEN'  and  a.date>=dateadd(day,-180, getdate()) " 
		+"group by b.ITCode) m1 on a.ITCode=m1.ITCode " 
		//+"--需求数量"
		+"left join (select b.ItemCode,sum(b.Qty-b.SendQty) XqQty " 
		+"from  tCustomer a inner join tItemReq b on a.code=b.CustCode " 
		+"where a.Status='4' and b.Qty-b.SendQty>0  group by b.ItemCode) m2 on a.ITCode=m2.ItemCode " 
		//+"--近30日发货数量"
		+"left join (select isnull(ta.ItemCode,tb.ITCode) ItemCode,isnull(ta.Send30,0)+isnull(tb.Send30,0) Send30 "
		+"from ( select b.ItemCode,sum(case when a.Type='S' then b.SendQty else b.SendQty*-1 end) Send30 " 
		+"from tItemApp a inner join tItemAppDetail b on a.no=b.No " 
		+"where a.SendDate>=dateadd(day,-30, getdate()) and a.Status in ('SEND','RETURN') " 
		+"group by b.ItemCode) ta full join ( select b.ITCode,sum(case when a.Type='S' then b.Qty else b.Qty*-1 end) Send30 " 
		+"from tSalesInvoice a inner join tSalesInvoiceDetail b on a.no=b.SINo " 
		+"where a.GetItemDate>=dateadd(day,-30, getdate()) and a.Status='CONFIRMED' "  
		+"group by b.ITCode) tb on ta.ItemCode=tb.ITCode) m3 on a.ITCode=m3.ItemCode " 
		//+"--近7日发货数量"
		+"left join (select isnull(ta.ItemCode,tb.ITCode) ItemCode,isnull(ta.Send7,0)+isnull(tb.Send7,0) Send7 "
		+"from ( select b.ItemCode,sum(case when a.Type='S' then b.SendQty else b.SendQty*-1 end) Send7 " 
		+"from tItemApp a inner join tItemAppDetail b on a.no=b.No " 
		+"where a.SendDate>=dateadd(day,-7, getdate()) and a.Status in ('SEND','RETURN') " 
		+"group by b.ItemCode) ta full join ( select b.ITCode,sum(case when a.Type='S' then b.Qty else b.Qty*-1 end) Send7 " 
		+"from tSalesInvoice a inner join tSalesInvoiceDetail b on a.no=b.SINo " 
		+"where a.GetItemDate>=dateadd(day,-7, getdate()) and a.Status='CONFIRMED' "  
		+"group by b.ITCode) tb on ta.ItemCode=tb.ITCode) tSend7 on a.ITCode=tSend7.ItemCode "
		//+"--预估需求数量"
		+"left join (select a.itemCode,sum(a.qty) qty from (select a.code,c.ItemCode,c.Qty,c.JudgeSend,"
		+"case when c.JudgeSend='1' then x1.itemtype2 else x1.itemtype3 end itemType from tCustomer a "
		+"inner join tSetItemQuota b on a.Status='4' and a.CustType=b.CustType and a.Area between b.FromArea and b.ToArea "
		+"inner join tSetItemQuotaDetail c on b.no=c.No "
		+"inner join tItem x1 on x1.code=c.ItemCode ) a "
		+"left join (select b.ItemCode,c.itemtype2,c.itemtype3,d.CustCode from titemappsenddetail a "
		+"inner join tItemAppDetail b on a.RefPk=b.pk "
		+"inner join titemapp d on b.no=d.No "
		+"inner join tItem c on b.ItemCode=c.Code ) b on a.code=b.CustCode "
		+"and ((a.itemType=isnull(b.itemType2,a.ItemType) and a.JudgeSend='1') "
		+"or (a.ItemType=isnull(b.ItemType3,a.ItemType) and a.JudgeSend='2')) "
		+"and b.ItemCode is null group by a.itemcode) m4 on a.ITCode=m4.ItemCode "  
		//+"--进度需求量"
		+"left join (select b.ItemCode,sum(b.Qty-b.SendQty) JdXqQty "
		+"from  tCustomer a inner join tItemReq b on a.code=b.CustCode "
		+"left join tItem it on b.ItemCode=it.Code "
		+"left join tItemType2 it2 on it.ItemType2=it2.Code "
		+"left join tItemType12 it12 on it2.ItemType12=it12.Code "
		+"where a.Status='4' and b.Qty-b.SendQty>0 and it12.Code='11' "
		+"and exists(select 1 from tPrjProg where CustCode=a.Code and PrjItem='1' and BeginDate is not null) "
		+"group by b.ItemCode "
		+"union all "
		+"select b.ItemCode,sum(b.Qty-b.SendQty) JdXqQty "
		+"from  tCustomer a inner join tItemReq b on a.code=b.CustCode "
		+"left join tItem it on b.ItemCode=it.Code "
		+"left join tItemType2 it2 on it.ItemType2=it2.Code "
		+"left join tItemType12 it12 on it2.ItemType12=it12.Code "
		+"where a.Status='4' and b.Qty-b.SendQty>0 and it12.Code='12' "
		+"and exists(select 1 from tPrjProg where CustCode=a.Code and ( ( ( PrjItem = '10' and BeginDate is not null)or (  PrjItem = '10' and confirmdate is not null ) ) "
		+"or  (( PrjItem = '9' and BeginDate is not null) or (  PrjItem = '9' and confirmdate is not null ))) ) "
		+"group by b.ItemCode ) jdReq on a.ItCode=jdReq.ItemCode "
		//+"--已确认需求量"
		+"left join (select c.code,SUM(b.qty-b.SendQty) yqrXqqty "
		+"from tCustomer a "
		+"inner join titemreq b on a.Code=b.CustCode "
		+"inner join tItem c on b.ItemCode=c.Code "
		+"where a.Status='4' and b.qty-b.SendQty>0 and "
		+"exists "
		+"(select 1 from dbo.tConfItemTypeDt cfd "
		+"inner join tCustItemConfirm cif on cfd.ConfItemType=cif.ConfItemType "
		+"where cif.CustCode=a.code and c.ItemType2=cfd.ItemType2 and (cfd.ItemType3 is null or cfd.ItemType3='' or c.ItemType3=cfd.ItemType3) "
		+"and cif.ItemConfStatus  in ('2','3')) "
		+"group by c.code) tcon on a.ItCode=tcon.code";
		if(StringUtils.isNotBlank(itemWHBal.getCzybh())){
			sql += " left join tWareHouseOperater wo on c.code=wo.whCode and wo.czybh=? where 1=1 and wo.whCode is not null ";
			list.add(itemWHBal.getCzybh().trim());
		}else{
			sql+=" where 1=1 ";
		}
		
		if (StringUtils.isNotBlank(itemWHBal.getWhCode())) {
			sql += " and a.whCode=? ";
			list.add(itemWHBal.getWhCode());
		}
    	if (StringUtils.isNotBlank(itemWHBal.getItCode())) {
			sql += " and a.ITCode=? ";
			list.add(itemWHBal.getItCode());
		}
    	if (StringUtils.isNotBlank(itemWHBal.getDescr())) {
			sql += " and b.Descr like ? ";
			list.add("%"+itemWHBal.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(itemWHBal.getDesc1())) {
			sql += " and c.Desc1 like ? ";
			list.add("%"+itemWHBal.getDesc1()+"%");
		}
    	if (StringUtils.isNotBlank(itemWHBal.getItemType1())) {
			sql += " and b.itemType1=? ";
			list.add(itemWHBal.getItemType1().trim());
		}
    	if (StringUtils.isNotBlank(itemWHBal.getItemType2())) {
			sql += " and b.itemType2=? ";
			list.add(itemWHBal.getItemType2());
		}
    	if(StringUtils.isNotBlank(itemWHBal.getSqlCode())){
    		sql += " and b.sqlCode=? ";
			list.add(itemWHBal.getSqlCode().trim());
    	}
    	if (StringUtils.isNotBlank(itemWHBal.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemWHBal.getLastUpdatedBy());
		}
//    	if(StringUtils.isBlank(itemWHBal.getConstructStatus())){
//			if (StringUtils.isNotBlank(itemWHBal.getConstructStatus())) {
//				String str = SqlUtil.resetStatus(itemWHBal.getConstructStatus());
//				sql += " and a.status in (" + str + ")";
//			}
//		}
    	if (StringUtils.isNotBlank(itemWHBal.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemWHBal.getActionLog());
		}
    	if (StringUtils.isNotBlank(itemWHBal.getDepartment2())) {
			sql += " and (e1.Department2=? or e2.Department2=?) ";
			list.add(itemWHBal.getDepartment2());
			list.add(itemWHBal.getDepartment2());
		}
    	sql+=" group by it2.Descr,a.ITCode,b.Descr,d.ConfirmedQty,e.OpenQty,f.SalesInvoiceQty,g.SampleQty, m1.CgzQty,m2.XqQty,m3.Send30,tSend7.Send7,b.MinQty,m4.qty,jdReq.JdXqQty,tcon.yqrXqqty  ";
    	if("1".equals(itemWHBal.getOnlyWarn())){
    		sql+=" having b.MinQty<>0 and sum(a.QtyCal)-isnull(d.ConfirmedQty, 0)-isnull(e.OpenQty, 0)-isnull(f.SalesInvoiceQty, 0)-isnull(g.SampleQty, 0)+isnull(m1.CgzQty,0) < b.MinQty  ";
    	}
    	if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.SendZb desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 仓库滞销品查询存储过程
	 * 
	 * @param customer
	 * @return
	 */	
	@SuppressWarnings("deprecation")
	public Page<Map<String,Object>> findPageBySql_ckzxp(Page<Map<String,Object>> page, ItemWHBal itemWHBal) {
		Assert.notNull(itemWHBal);
		if (itemWHBal.getDateFrom()==null || itemWHBal.getDateTo()==null) {
			return null;
		}
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn
					.prepareCall("{Call pCkzxp(?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemWHBal.getItemType1());
			call.setString(2, itemWHBal.getWhCode());
			call.setTimestamp(3, itemWHBal.getDateFrom()==null?null:new Timestamp(itemWHBal.getDateFrom().getTime()));
			call.setTimestamp(4, itemWHBal.getDateTo()==null?null:new Timestamp(itemWHBal.getDateTo().getTime()));
			call.setInt(5, page.getPageSize());
			call.setInt(6, page.getPageNo());
			call.setString(7, page.getPageOrderBy());
			call.setString(8, page.getPageOrder());
			call.registerOutParameter(9, Types.INTEGER);
			call.setString(10, itemWHBal.getItemType2());
			call.setInt(11, itemWHBal.getTjfs());
			call.setString(12, StringUtils.isBlank(itemWHBal.getExpired())?"F":itemWHBal.getExpired());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list);
			page.setTotalCount(call.getInt(9));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 
	
	/**
	 * 库存余额分页查询
	 * 
	 * @param customer
	 * @return
	 */	
	@SuppressWarnings("deprecation")
	public Page<Map<String,Object>> findPageBySql_kcyecx(Page<Map<String,Object>> page, ItemWHBal itemWHBal) {
		Assert.notNull(itemWHBal);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn
					.prepareCall("{Call pKcyecxPage(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemWHBal.getCzybh());
			call.setString(2, itemWHBal.getWhCode());
			call.setString(3, itemWHBal.getItCode());
			call.setString(4, itemWHBal.getDescr());
			call.setString(5, itemWHBal.getDesc1());
			call.setString(6, itemWHBal.getItemType1());
			call.setString(7, itemWHBal.getItemType2());
			call.setString(8, itemWHBal.getSqlCode());
			call.setString(9, itemWHBal.getDepartment2());
			call.setString(10, itemWHBal.getOnlyWarn());
			call.setString(11, itemWHBal.getConstructStatus());
			call.setInt(12, itemWHBal.getTjfs());
			call.setInt(13, page.getPageSize());
			call.setInt(14, page.getPageNo());
			call.setString(15, page.getPageOrderBy());
			call.setString(16, page.getPageOrder());
			call.registerOutParameter(17, Types.INTEGER);
			call.setString(18, itemWHBal.getContainClearItem());
			call.setString(19, itemWHBal.getPercentRequirs());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list);
			page.setTotalCount(call.getInt(17));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 
	
	public List<Map<String, Object>> findListBySql_ckzxp(ItemWHBal itemWHBal) {
		List<Object> list = new ArrayList<Object>();
		String sql ="if object_id('tempdb..#tempResult') is not null drop table #tempResult "
		+"select ITCode,cast('' as varchar(200)) itCodeDescr,cast('' as varchar(100)) hj,qtyCal qcQty,qtyCal qcJe,"
		+"qtyCal rkQty,qtyCal rkJe,qtyCal ckQty,qtyCal ckJe,qtyCal kcQty,qtyCal kcJe,qtyCal zxQty,qtyCal zxJe "
		+"into #tempResult "
		+"from tItemWHBal where 1=2 "
		+"insert into #tempResult "
		+"exec pckzxp ?,?,?,? "
		+"select * from #tempResult ";	
		list.add(itemWHBal.getItemType1());
		list.add(itemWHBal.getWhCode());
		list.add(itemWHBal.getDateFrom());
		list.add(itemWHBal.getDateTo());
		
		return this.findListBySql(sql, list.toArray());
	}


	public Page<Map<String, Object>> findItemSampleDetailPageBySql(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select isnull(a.QtyCal,0) qtyCal "
				+"from tItemWHBal a,tWareHouse b "
				+"where a.WHCode=b.Code and b.WareType='2' "
				+"and a.Expired='F' and b.Expired='F' ";
		if (StringUtils.isNotBlank(itemWHBal.getItCode())) {
			sql += "  and a.ITCode=? ";
			list.add(itemWHBal.getItCode());
		}
    	
		return this.findPageBySql(page, sql, list.toArray());
	}


	/**
	 * 库位余额分页查询（库存余额查询模块）
	 * @author	created by zb
	 * @date	2018-12-8--下午2:06:15
	 * @param page
	 * @param itemWHBal
	 * @return
	 */
	public Page<Map<String, Object>> findWHPosiBalPageBySql(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
		String sql ="select a.Code,a.Desc1 WHDescr,b.PK,b.Desc1 WHPosiDescr,b.WHCode, " +
				"c.WHPPk,c.ITCode,c.QtyCal " +
				"from tWareHouse a " +
				"left join tWareHousePosi b on b.WHCode=a.Code " +
				"left join tWHPosiBal c on c.WHPPk=b.PK " +
				"where c.ITCode=? ";
		return this.findPageBySql(page, sql, new Object[]{itemWHBal.getItCode()});
	}

	/**
	 * 按材料类型2查询（库存余额查询模块）
	 * @author	created by zb
	 * @date	2018-12-8--下午5:51:23
	 * @param page
	 * @param itemWHBal
	 * @return
	 */
	public Page<Map<String, Object>> findItemType2PageBySql(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select b.ItemType2,it2.Descr ItemType2Descr,sum(a.QtyCal) QtyCal,sum(a.QtyCal*a.AvgCost) CostAmount " +
					"from tItemWHBal a " +
					"inner join tItem b on a.ITCode=b.Code " +
					"inner join tWareHouse c on a.WHCode=c.Code " +
					"left join tEmployee e1 on b.buyer1=e1.number " +
					"left join tEmployee e2 on b.buyer2=e2.number " +
					"left outer join tItemType2 it2 on it2.Code=b.ItemType2 ";
		if(StringUtils.isNotBlank(itemWHBal.getCzybh())){
			sql += 	"left join tWareHouseOperater e on c.code=e.whCode and e.czybh=? where 1=1 and e.whCode is not null  ";
			list.add(itemWHBal.getCzybh().trim());
		}else{
			sql+=" where 1=1 ";
		}
		if (StringUtils.isNotBlank(itemWHBal.getWhCode())) {
			sql += " and a.whCode=? ";
			list.add(itemWHBal.getWhCode());
		}
    	if (StringUtils.isNotBlank(itemWHBal.getItCode())) {
			sql += " and a.ITCode=? ";
			list.add(itemWHBal.getItCode());
		}
    	if (StringUtils.isNotBlank(itemWHBal.getDescr())) {
			sql += " and b.Descr like ? ";
			list.add("%"+itemWHBal.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(itemWHBal.getDesc1())) {
			sql += " and c.Desc1 like ? ";
			list.add("%"+itemWHBal.getDesc1()+"%");
		}
    	if (StringUtils.isNotBlank(itemWHBal.getItemType1())) {
			sql += " and b.itemType1=? ";
			list.add(itemWHBal.getItemType1().trim());
		}else{
			UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
			String itemRight="";
			for(String str:uc.getItemRight().trim().split(",")){
				itemRight+="'"+str+"',";
			}
			sql += " and b.ItemType1 in("+itemRight.substring(0, itemRight.length()-1)+") ";
		}
    	if (StringUtils.isNotBlank(itemWHBal.getItemType2())) {
			sql += " and b.itemType2 in ('" + itemWHBal.getItemType2().replaceAll(",", "','") + "')";
		}
    	if(StringUtils.isNotBlank(itemWHBal.getSqlCode())){
    		sql += " and b.sqlCode=? ";
			list.add(itemWHBal.getSqlCode().trim());
    	}
    	if (StringUtils.isNotBlank(itemWHBal.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemWHBal.getLastUpdatedBy());
		}
    	if (StringUtils.isNotBlank(itemWHBal.getDepartment2())) {
			sql += " and (e1.Department2=? or e2.Department2=?) ";
			list.add(itemWHBal.getDepartment2());
			list.add(itemWHBal.getDepartment2());
		}
		if (StringUtils.isBlank(itemWHBal.getExpired()) || "F".equals(itemWHBal.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemWHBal.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemWHBal.getActionLog());
		}
		sql += "group by b.ItemType2,it2.Descr ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Page<Map<String,Object>> findOrderAnalysis(Page<Map<String,Object>> page, ItemWHBal itemWHBal) {
		Assert.notNull(itemWHBal);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn
					.prepareCall("{Call pOrderAnalysis(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemWHBal.getCzybh());
			call.setString(2, itemWHBal.getWhCode());
			call.setString(3, itemWHBal.getItCode());
			call.setString(4, itemWHBal.getItemType2());
			call.setString(5, itemWHBal.getConstructStatus());
			call.setString(6, itemWHBal.getIsClearInv());//包含清单材料
			call.setInt(7, page.getPageSize());
			call.setInt(8, page.getPageNo());
			call.setString(9, page.getPageOrderBy());
			call.setString(10, page.getPageOrder());
			call.registerOutParameter(11, Types.INTEGER);
			call.setString(12, itemWHBal.getItemType1());
			call.setTimestamp(13, itemWHBal.getDateFrom() == null ? null
					: new Timestamp(itemWHBal.getDateFrom().getTime()));
			call.setTimestamp(14, itemWHBal.getDateTo() == null ? null
					: new Timestamp(itemWHBal.getDateTo().getTime()));
			call.setString(15, itemWHBal.getPurchOrderAnalyse());
			call.setString(16, itemWHBal.getItemCodes());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list);
			page.setTotalCount(call.getInt(11));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 

}
