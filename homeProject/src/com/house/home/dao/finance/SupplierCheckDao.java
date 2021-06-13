package com.house.home.dao.finance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.finance.SplCheckOut;
import com.house.home.entity.finance.SupplierPrepay;

@SuppressWarnings("serial")
@Repository
public class SupplierCheckDao extends BaseDao {

	public Page<Map<String,Object>> goJqGrid(Page<Map<String,Object>> page, SplCheckOut splCheckOut){
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from ( "
				   + " 		select a.no,a.SplCode,b.Descr SplCodedescr,a.Date,a.PayType,c.NOTE PayTypeDescr,a.BeginDate,a.EndDate, "
				   + " 		a.PayAmount,isnull(a.OtherCost,0) OtherCost,a.Remark,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.Status,d.Note StatusDescr, "
				   + " 		a.PayCZY,a.PayDate,e.NameChi PayCZYDescr,a.DocumentNo,a.ConfirmDate,a.PaidAmount,a.NowAmount,a.PreAmount,a.PayAmount-a.PaidAmount PayBalance, "
				   + " 		a.AppCZY,czy.ZWXM AppCZYDescr,a.ConfirmCZY,e1.Namechi ConfirmCZYDescr,b.ItemType1," +
				   "		case when x2.note is null then x.note else x2.note end discapprovestatus,case when x2.note is null then x.note else x2.note end proctrackstatus," +
				   "		x.note ProcStatusDescr,l.no wfProcInstNo "
				   + " 		from tSplCheckOut a "
				   + " 		left outer join tSupplier b on a.SplCode=b.Code "
				   + " 		left outer join tXTDM d on a.Status=d.CBM and d.ID='SPLCKOTSTATUS' "
				   + " 		left outer join (select * from tXTDM where ID='PAYTYPE') c on a.PayType=c.CBM "
				   + " 		left outer join tEmployee e on e.number=a.PayCZY "
				   + " 		left outer join tCZYBM czy on a.AppCZY=czy.CZYBH "
				   + " 		left outer join tEmployee e1 on e1.number=a.ConfirmCZY " +
				   " left join (select max(WfProcInstNo) wfProcInstNo,a.RefNo from tWfCust_PurchaseExpense a group by a.RefNo) k on k.RefNo = a.No "
	                + " left join tWfProcInst l on l.No = k.WfProcInstNo " 
	                + " left join ("
	                + "  select max(pk) pk,WfProcInstNo from tWfProcTrack where actionType in ('4','5','6') group by  WfProcInstNo "
	                + ")m on m.WfProcInstNo = l.no"
	                + " left join tWfProcTrack n on m.pk = n.pk "
	                + " left join tXtdm x2 on x2.id = 'PROCACTTYPE' and x2.cbm = n.ActionType "
	                + " left join tXtdm x on x.id = 'WFPROCINSTSTAT' and x.cbm = l.status "
				   + " 		where a.Expired='F' ";
		if(StringUtils.isNotBlank(splCheckOut.getSplCode())){
			sql += " and a.SplCode=? ";
			params.add(splCheckOut.getSplCode());
		}
		if(StringUtils.isNotBlank(splCheckOut.getRemark())){
			sql += " and a.Remark like ? ";
			params.add("%"+splCheckOut.getRemark()+"%");
		}
		if(StringUtils.isNotBlank(splCheckOut.getPayType())){
			sql += " and a.PayType=? ";
			params.add(splCheckOut.getPayType());
		}
		if(StringUtils.isNotBlank(splCheckOut.getCustCode())){
			sql += " and exists(select 1 from tPurchase p where p.CheckOutNo=a.No and p.CustCode=?) ";
			params.add(splCheckOut.getCustCode());
		}
		if(StringUtils.isNotBlank(splCheckOut.getStatus())){
			sql += " and a.Status in ('"+splCheckOut.getStatus().trim().replace(",", "','")+"')";
		}
		
		sql +=" and b.ItemType1 in "+splCheckOut.getItemRight();
		
		if (StringUtils.isNotBlank(splCheckOut.getItemType1())) {
            sql += " and b.ItemType1 = ? ";
            params.add(splCheckOut.getItemType1());
        }
		
		if (StringUtils.isNotBlank(splCheckOut.getAppCZY())) {
            sql += " and a.AppCZY = ? ";
            params.add(splCheckOut.getAppCZY());
        }
		
		if(splCheckOut.getDateFrom() != null){
			sql += " and a.Date >=? ";
			params.add(DateUtil.startOfTheDay(splCheckOut.getDateFrom()));
		}
		if(splCheckOut.getDateTo() != null){
			sql += " and a.Date < ? ";
			params.add(DateUtil.addDate(splCheckOut.getDateTo(), 1));
		}
		if(StringUtils.isNotBlank(splCheckOut.getDocumentNo())){
			sql += " and a.DocumentNo like ? ";
			params.add("%"+splCheckOut.getDocumentNo()+"%");
		}
		if("T".equals(splCheckOut.getOnlyUnCheck())){
			sql += " and exists (select 1 from tPurchase x where x.CheckOutNo=a.No and x.RemainAmount<>0) ";
		}
		if(StringUtils.isNotBlank(splCheckOut.getNo())){
			sql += " and a.No=? ";
			params.add(splCheckOut.getNo());
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String,Object>> goJqGridAddPurchase(Page<Map<String,Object>> page, SplCheckOut splCheckOut){
		Xtcs xtcs = this.get(Xtcs.class, "Titles");
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( "
				   + " 		select case when ?='T' and exists(select 1 from tCustomer where Code=a.CustCode) then 1 "
				   + " 		when ?='F' and exists(select 1 from tCustomer where Code=a.CustCode) then 0 "
				   + " 		else a.IsCheckOut end IsCheckOut,a.No,d.DocumentNo,d.Address Address,a.Supplier,b.Descr SupplierDescr, "
				   + " 		c.NOTE TypeDescr,a.Date,e.No AppNo,case when a.Type='S' then a.Amount else -a.Amount end showAmount,a.Amount,a.OtherCost,a.OtherCostAdj, "
				   + "		case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj SumAmount, " 
				   + "		a.FirstAmount,a.SecondAmount,round(case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj-a.FirstAmount-a.SecondAmount,2) RemainAmount, " //^_^ 20160110 add by xzp RemainAmount 保留两位小数byzjf20190624 
				   + "		a.Remarks,e.IsService,x1.NOTE IsServiceDescr,case when ?='T' and exists(select 1 from tCustomer where Code=a.CustCode) then 1 "
				   + "		when ?='F' and exists(select 1 from tCustomer where Code=a.CustCode) then 1 else a.CheckSeq end CheckSeq, "
				   + " 		case when a.Type='S' then a.xmjljsj else -a.xmjljsj end xmjljsj, "
				   + "		a.SplAmount,case when a.Type = 'S' then a.Amount-a.SplAmount else -a.Amount-a.SplAmount end DiffAmount,a.Type,a.DelivType,a.Status,a.WHCode,a.CheckOutNo, " 
				   + " 		a.CustCode,a.ItemType1,x2.NOTE issetitemdescr, ( "
				   + " 			select top 1 it2.Descr  from  tPurchaseDetail pd left outer join tItem "
				   + "          it on pd.ITCode=it.code  left outer join tItemtype2 it2 on it2.code=it.ItemType2 "
				   + "          where pd.puno=a.no ) as Itemtype2Descr,f.desc1 warehousedesc,a.OverCost chaochue,a.ProjectOtherCost, "
		           + "		round(case when a.Type='S' then a.xmjljsj else -a.xmjljsj end + a.OtherCost+a.OtherCostAdj+a.ProjectOtherCost,0) xmjljszj,isnull(g.ProcessCost,0) processcost, "
				   + " 		isnull(h.SumAmount,0) intinstallfee,isnull(case when a.Type='S' then g.sumSaleAmount else g.sumSaleAmount*-1 end,0) sumsaleamount,a.payRemark,"
		           + " 		x3.Note checkstatusdescr,tp.Descr CmpName," 
				   + "		d.custtype,l.Desc1 custtypedescr,a.splstatus,x4.note splstatusdescr,x6.note sourcedescr,a.CheckConfirmRemarks  "
				   + " 		from ( "
				   + "			select a.OtherCost,a.OtherCostAdj,a.ProjectOtherCost,a.No,a.Status,a.Date,a.Type,a.WHCode,a.Supplier,a.Amount, "
				   + " 			a.Remarks,a.IsCheckOut,a.CheckOutNo,a.CustCode,a.checkSeq,a.ItemType1,a.FirstAmount,a.SecondAmount,RemainAmount,a.SplAmount,b.xmjljsj,a.DelivType, "
				   + " 			a.OverCost,a.payRemark,a.splstatus,a.CheckConfirmRemarks "
				   + " 			from( "
				   + " 				select a.OtherCost,a.OtherCostAdj,a.ProjectOtherCost,a.No,a.Status,a.Date,a.Type,a.WHCode, "
				   + " 				a.Supplier,a.Amount,a.Remarks,a.IsCheckOut,a.CheckOutNo,a.CustCode,a.CheckSeq,cast(0 as money) xmjljsj,a.ItemType1, " 
				   + "				a.FirstAmount,a.SecondAmount, "
				   + " 				round(case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj-a.FirstAmount-a.SecondAmount,0) RemainAmount, "
				   + " 				a.SplAmount,a.DelivType,a.OverCost,a.payRemark,a.splstatus,a.CheckConfirmRemarks "
				   + " 				from tPurchase a " 
				   + "				left join (select * from dbo.fStrToTable('"+splCheckOut.getNos()+"',',')) b on a.No = b.item "
				   + " 				where a.IsCheckOut='0' "
				   + " 				and a.Supplier = ? "
				   + " 				and a.Status = 'CONFIRMED' and a.SecondAmount=0 "  
				   + " 				and b.item is null "
				   + " 				union "
				   + " 				select a.OtherCost,a.OtherCostAdj,0,a.No,a.Status,a.Date,a.Type,a.WHCode, "
				   + "				a.Supplier,a.Amount,a.Remarks,0,'',a.CustCode,a.CheckSeq,0 xmjljsj,a.ItemType1, "
				   + " 				a.FirstAmount,a.SecondAmount, "
				   + " 				round(case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj-a.FirstAmount-a.SecondAmount,0) RemainAmount, "
				   + "				a.SplAmount,a.DelivType,a.OverCost,a.payRemark,a.splstatus,a.CheckConfirmRemarks "
				   + " 				from tPurchase a "
				   + "				left join (select * from dbo.fStrToTable('"+splCheckOut.getNos()+"',',')) b on a.No = b.item "
				   + " 				where a.CheckOutNo=? "
				   + " 				and a.IsCheckOut='1' "
				   + " 				and b.item is null "
				   + " 			) a, ( "
				   + " 				select a.No,sum(isnull(b.ProjectCost*b.QtyCal,0)) xmjljsj "
				   + " 				from tPurchase a inner join tPurchaseDetail b on a.No=b.PUNo " 
				   + " 				group by a.No,a.OtherCost,a.OtherCostAdj "
				   + " 			) b "
				   + "			where a.No=b.No "
				   + "		) a "
				   + " 		left outer join tCustomer d on d.Code=a.CustCode "
				   + " 		left outer join tItemApp e on a.No=e.PUNo "
				   + " 		left outer join tSupplier b on b.Code=a.Supplier "
				   + " 		left outer join tXTDM x1 on x1.IBM=e.IsService and x1.id='YESNO' "
				   + " 		left outer join tXTDM c on c.CBM=a.Type and c.ID='PURCHTYPE' "
				   + " 		left outer join tXTDM x2 on x2.IBM=e.IsSetItem and x2.id='YESNO' "
				   + " 		left outer join tWareHouse f on f.code=a.WHcode "
		           + " 		left outer join ( "
				   + " 			select iad.no,sum(case when ir.qty <> 0 then "
		           + "   		Round(iad.qty*ir.UnitPrice * ir.Markup/100 + ir.ProcessCost*iad.qty/ir.qty,0) "
		           + "   		else Round(iad.qty*ir.UnitPrice * ir.Markup/100,0) end) sumSaleAmount, "
		           + "   		sum(case when d.ItemType2 in ('"+xtcs.getQz().trim().replace(",", "','")+"') and ir.Qty<>0 then round(ir.ProcessCost*iad.Qty/ir.Qty,2) else ir.ProcessCost end) ProcessCost "
		           + "   		from ( " 
				   + " 				select a.OtherCost,a.OtherCostAdj,a.ProjectOtherCost,a.No,a.Status,a.Date,a.Type,a.WHCode, "
				   + " 				a.Supplier,a.Amount,a.Remarks,a.IsCheckOut,a.CheckOutNo,a.CustCode,a.CheckSeq,cast(0 as money) xmjljsj,a.ItemType1, " 
				   + "				a.FirstAmount,a.SecondAmount, "
				   + " 				round(case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj-a.FirstAmount-a.SecondAmount,0) RemainAmount, "
				   + " 				a.SplAmount,a.DelivType,a.OverCost,a.payRemark "
				   + " 				from tPurchase a " 
				   + "				left join (select * from dbo.fStrToTable('"+splCheckOut.getNos()+"',',')) b on a.No = b.item "
				   + " 				where a.IsCheckOut='0' "
				   + " 				and a.Supplier = ? "
				   + " 				and a.Status = 'CONFIRMED' and a.SecondAmount=0 "  
				   + " 				and b.item is null "
				   + " 				union "
				   + " 				select a.OtherCost,a.OtherCostAdj,0,a.No,a.Status,a.Date,a.Type,a.WHCode, "
				   + "				a.Supplier,a.Amount,a.Remarks,0,'',a.CustCode,a.CheckSeq,0 xmjljsj,a.ItemType1, "
				   + " 				a.FirstAmount,a.SecondAmount, "
				   + " 				round(case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj-a.FirstAmount-a.SecondAmount,0) RemainAmount, "
				   + "				a.SplAmount,a.DelivType,a.OverCost,a.payRemark "
				   + " 				from tPurchase a "
				   + "				left join (select * from dbo.fStrToTable('"+splCheckOut.getNos()+"',',')) b on a.No = b.item "
				   + " 				where a.CheckOutNo=? "
				   + " 				and a.IsCheckOut='1' "
				   + " 				and b.item is null "
		           + " 			) a  "
		           + "   		inner join tItemApp b on a.no=b.PUNo "
		           + "   		inner join tItemappDetail iad on b.no=iad.No "
		           + "   		inner join tItemreq ir on iad.ReqPK=ir.pk "
		           + "   		inner join tItem d on iad.ItemCode=d.Code "
		           + "   		group by iad.no "
		           + "		) g on g.no=e.no "
		           + " 		left outer join ( "
		           + "   		select lfd.CustCode,sum(Amount) SumAmount from tLaborFee lf "
		           + "   		inner join tLaborFeeDetail lfd on lfd.No=lf.No "
		           + "   		where lf.Status='4' and lfd.FeeType='19' group by lfd.CustCode "
		           + " 		) h on h.CustCode=a.CustCode "
		           + "      left join tBuilder i on d.BuilderCode=i.Code "
		           + "      left join tRegion j on j.Code=i.RegionCode "
		           + "      left join tCompany k on k.Code=j.CmpCode "
		           + "      left join tCmpCustType m on m.custType = d.custType and m.CmpCode = j.CmpCode"
		           + " 		left outer join tXTDM x3 on x3.CBM = d.CheckStatus and x3.id='CheckStatus' "
		           + "		left outer join tCustType l on l.Code=d.CustType " 
		           + " 		left join tTaxPayee tp on tp.code = d.PayeeCode "
		           + "      left join tXTDM x4 on x4.CBM=a.SplStatus and x4.id='PuSplStatus' "
		           + " 		left join tCustomer n on a.CustCode = n.Code "
		           + " 		left join tXTDM x6 on n.Source = x6.CBM and x6.id = 'CUSTOMERSOURCE' "
		           + " 		where 1=1 ";
		params.add(splCheckOut.getSelectAll());
		params.add(splCheckOut.getSelectAll());
		params.add(splCheckOut.getSelectAll());
		params.add(splCheckOut.getSelectAll());
		params.add(splCheckOut.getSplCode());
		params.add(splCheckOut.getCheckOutNo());
		params.add(splCheckOut.getSplCode());
		params.add(splCheckOut.getCheckOutNo());
		if("F".equals(splCheckOut.getIsNormal())){
			sql += " and (not exists(select 1 from  tItemAppSend ias where (ias.ConfirmStatus='0' or ias.ConfirmStatus='2')  and ias.IANo=e.No ) or e.No is null  or e.no='') ";
		}
		if(StringUtils.isNotBlank(splCheckOut.getAddress())){
			sql += " and d.Address like ? ";
			params.add("%"+splCheckOut.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(splCheckOut.getDocumentNo())){
			sql += " and d.DocumentNo=? ";
			params.add(splCheckOut.getDocumentNo());
		}
		if(StringUtils.isNotBlank(splCheckOut.getPurchType())){
			sql += " and a.Type=? ";
			params.add(splCheckOut.getPurchType());
		}
		if(splCheckOut.getDateFrom() != null){
			sql += " and a.Date >= ? ";
			params.add(DateUtil.startOfTheDay(splCheckOut.getDateFrom()));
		}
		if(splCheckOut.getDateTo() != null){
			sql += " and a.Date < ? ";
			params.add(DateUtil.addDate(splCheckOut.getDateTo(), 1));
		}
		if(StringUtils.isNotBlank(splCheckOut.getCmpCode())){
			sql += " and j.CmpCode=? ";
			params.add(splCheckOut.getCmpCode());
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.Address ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public List<Map<String,Object>> goJqGridIntInstall(Page<Map<String,Object>> page, String custCode){
		String sql = " select * from ( select a.no,a.itemtype1,x1.Descr itemtype1descr,a.status,x2.NOTE statusdescr, "
				   + " 		a.actname,b.pk,b.feetype,c.Descr feetypedescr,b.amount,b.remarks,d.iano,d.No sendno "
				   + " 		from tLaborFee a "
				   + " 		inner join tLaborFeeDetail b on a.No=b.No "
				   + " 		left join tLaborFeeType c on b.FeeType=c.Code "
				   + " 		left join tItemAppSend d on b.AppSendNo=d.No "
				   + " 		left join titemapp e on d.IANo=e.No "
				   + " 		left join titemtype1 x1 on a.ItemType1=x1.Code "
				   + " 		left join txtdm x2 on a.Status=x2.CBM and x2.id='LABORFEESTATUS' "
				   + " 		where a.Status='4' and b.FeeType='19' and b.CustCode=? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{custCode});
	}
	
	public List<Map<String,Object>> goJqGridMainItem(Page<Map<String,Object>> page, String checkOutNo){
		Xtcs xtcs = this.get(Xtcs.class, "Titles");
		String sql = "select * from ( "
				   + " 		select a.checkseq,a.ischeckout,a.no,d.Address address,d.documentno,a.supplier,b.Descr supplierdescr,c.NOTE typedescr,a.date,e.No appno,g.desc1 warehousedesc , "
				   + " 		case when a.Type='S' then a.Amount else -a.Amount end showamount,a.amount,a.othercost,a.othercostadj, "	
				   + " 		case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj sumamount, "
				   + " 		a.firstamount,a.secondamount,round(case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj-a.FirstAmount-a.SecondAmount,2) remainamount, " //^_^ 20160110 add by xzp,remainamount保留两位小数byzjf
		           + " 		isnull(case when a.Type='S' then f.sumSaleAmount else f.sumSaleAmount*-1 end,0) sumsaleamount,e.isservice,x1.NOTE isservicedescr,a.remarks,a.payremark, "
		           + " 		case when a.Type='S' then a.xmjljsj else -a.xmjljsj end xmjljsj,a.projectothercost, "
		           + "		round(case when a.Type='S' then a.xmjljsj else -a.xmjljsj end + a.OtherCost+a.OtherCostAdj+a.ProjectOtherCost,2) xmjljszj, "
		           + " 		a.overcost chaochue,e.issetitem,x2.NOTE issetitemdescr, " //^_^ 20160121 modi by xzp 增加查询字段 e.IsSetItem
		           + " 		a.splamount,case when a.Type = 'S' then a.Amount-a.SplAmount else -a.Amount-a.SplAmount end diffamount, isnull(pfd.amount,0) processcost, "  //isnull(f.ProcessCost,0)预算其他费用改为标准其他费用    by zjf
		           + " 		isnull(h.SumAmount,0) intinstallfee,a.custcode,a.arrivestatus,a.arriveremark,itemtype2descr,x3.Note checkstatusdescr,a.type,a.delivtype "  //  add ArriveStatus,ArriveRemark  by zjf
		           + "      ,a.splstatus,x4.note splstatusdescr,ps.sendnum," 
		           + "		tp.descr cmpname"//20191203 modi by xzy 主项目页面的公司，改成取公司销售产品表。tCmpCustType
		           + "		,x5.NOTE iscupboarddescr,d.custtype,m.Desc1 custtypedescr, j.Code region, j.Descr regiondescr, "	//增加片区 add by zb on 20200422
		           + "      x6.note sourcedescr,a.checkconfirmremarks "
		           + "  	from ( "
				   + " 			select a.PayRemark,a.OtherCost,a.OtherCostAdj,a.ProjectOtherCost,a.No,a.Status,a.Date,a.Type,a.WHCode, "
                   + " 			a.Supplier,a.Amount,a.Remarks,a.IsCheckOut,a.CheckOutNo,a.CustCode,a.CheckSeq,ProjectAmount xmjljsj,a.ItemType1,a.OverCost, "
                   + " 			a.FirstAmount,a.SecondAmount,round(case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj-a.FirstAmount-a.SecondAmount,0) RemainAmount, " //^_^ 20160110 add by xzp
                   + "  		a.SplAmount,a.ArriveStatus,a.ArriveRemark,a.delivtype,a.SplStatus,a.CheckConfirmRemarks "    //显示问题  增加 ,ArriveStatus,ArriveRemark 字段by zjf
                   + "  		from tPurchase a "
                   + "  		where a.CheckOutNo=? "
		           + " 		) a "
		           + " 		left outer join tSupplier b on b.Code=a.Supplier "
		           + " 		left outer join tXTDM c on c.CBM=a.Type and c.ID='PURCHTYPE' "
		           + " 		left outer join tCustomer d on d.Code=a.CustCode "
		           + " 		left outer join tItemApp e on a.No=e.PUNo "
		           + " 		left outer join tXTDM x1 on x1.IBM=e.IsService and x1.id='YESNO' "
		           + " 		left outer join tXTDM x2 on x2.CBM=e.IsSetItem and x2.id='YESNO' " //^_^ 20160121 add by xzp
		           + " 		left outer join tXTDM x5 on x5.CBM=e.IsCupboard and x5.id='YESNO' " 
		           + " 		left outer join tWareHouse g on g.code=a.WHcode "
		           + " 		left outer join (select iad.no,sum(case when ir.qty <> 0 then "
		           + "   		Round(iad.qty*ir.UnitPrice * ir.Markup/100 + ir.ProcessCost*iad.qty/ir.qty,0) "
		           + "   		else Round(iad.qty*ir.UnitPrice * ir.Markup/100,0) end) sumSaleAmount, "
		           + "   		sum(case when d.ItemType2 in ('"+xtcs.getQz().trim().replace(",", "','")+"') and ir.Qty<>0 then round(ir.ProcessCost*iad.Qty/ir.Qty,2) else ir.ProcessCost end) ProcessCost "
		           + "   		from ( " 
				   + " 				select a.PayRemark,a.OtherCost,a.OtherCostAdj,a.ProjectOtherCost,a.No,a.Status,a.Date,a.Type,a.WHCode, "
                   + " 				a.Supplier,a.Amount,a.Remarks,a.IsCheckOut,a.CheckOutNo,a.CustCode,a.CheckSeq,ProjectAmount xmjljsj,a.ItemType1,a.OverCost, "
                   + " 				a.FirstAmount,a.SecondAmount,round(case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj-a.FirstAmount-a.SecondAmount,0) RemainAmount, " //^_^ 20160110 add by xzp
                   + "  			a.SplAmount,a.ArriveStatus,a.ArriveRemark  "    //显示问题  增加 ,ArriveStatus,ArriveRemark 字段by zjf
                   + "  			from tPurchase a "
                   + "  			where a.CheckOutNo=? "
		           + " 			) a  "
		           + "   		inner join tItemApp b on a.no=b.PUNo "
		           + "   		inner join tItemappDetail iad on b.no=iad.No "
		           + "   		inner join tItemreq ir on iad.ReqPK=ir.pk "
		           + "   		inner join tItem d on iad.ItemCode=d.Code "
		           + "   		group by iad.no "
		           + "		) f on f.no=e.no "
		           + " 		left outer join ( "
		           + "   		select lfd.CustCode,sum(Amount) SumAmount from tLaborFee lf "
		           + "   		inner join tLaborFeeDetail lfd on lfd.No=lf.No "
		           + "   		where lf.Status='4' and lfd.FeeType='19' group by lfd.CustCode "
		           + " 		) h on h.CustCode=a.CustCode "
		           + "  	left outer join (select  m.no ,(select top 1 it2.Descr  from  tPurchaseDetail pd left outer join tItem "
		           + "      	it on pd.ITCode=it.code  left outer join tItemtype2 it2 on it2.code=it.ItemType2 "
		           + "          where pd.puno=m.no ) as Itemtype2Descr  from     tPurchase  m "
		           + " 		) i on i.no=a.no "
		           + "      left join tBuilder l on d.BuilderCode=l.Code "
		           + "      left join tRegion j on j.Code=l.RegionCode "
		           + "      left join tCompany k on k.Code=j.CmpCode "
		           + "		left join tCmpCustType cct on cct.CmpCode = j.CmpCode and d.CustType = cct.custType "
		           + "		left outer join tCustType m on m.code = d.CustType"
		           + " 		left outer join tXTDM x3 on x3.CBM = d.CheckStatus and x3.id='CheckStatus' "
		           + "       left outer join(select count(1) sendNum,SupplCode,CustCode from tItemApp "
				   + "	where SendType='1' group by  SupplCode,CustCode "
				   + "  )ps on ps.CustCode=a.custcode and ps.SupplCode=a.Supplier "
		           + "  left join tXTDM x4 on x4.CBM=a.SplStatus and x4.id='PuSplStatus' "
				   + " left outer join (select sum(Amount) Amount,PUNo from tPurchaseFeeDetail where GenerateType='2' group by PUNo )pfd on pfd.PUNo=a.no " +
				   " left join tTaxPayee tp on tp.code = d.payeeCode "
				   + " left join tCustomer n on a.CustCode = n.Code "
		           + " left join tXTDM x6 on n.Source = x6.CBM and x6.id = 'CUSTOMERSOURCE' ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a Order by a.CheckSeq ";
		}
		return this.findBySql(sql, new Object[]{checkOutNo, checkOutNo});
	}
	
	public List<Map<String,Object>> goJqGridExcess(Page<Map<String,Object>> page, String nos, String splCode, String checkOutNo){
		String sql = " select * from ( select y1.*,y2.address,y2.status,x2.NOTE statusdescr,y2.projectman,te.NameChi projectmandescr, "
				   + " 		it1.Descr itemtype1descr,x1.NOTE isservicedescr "
				   + " 		from ( "
				   + " 			select *,isnull(dbo.fGetCustOverCost(t1.CustCode,t1.ItemType1,t1.IsService),0) chaochue "
				   + " 			from ( "
				   + " 				select a.custcode,a.itemtype1,ia.IsService,COUNT(1) LastNum "
				   + " 				from ( " 
				   + " 					select a.OtherCost,a.OtherCostAdj,a.ProjectOtherCost,a.No,a.Status,a.Date,a.Type,a.WHCode, "
				   + " 					a.Supplier,a.Amount,a.Remarks,a.IsCheckOut,a.CheckOutNo,a.CustCode,a.CheckSeq,cast(0 as money) xmjljsj,a.ItemType1, " 
				   + "					a.FirstAmount,a.SecondAmount, "
				   + " 					round(case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj-a.FirstAmount-a.SecondAmount,0) RemainAmount, "
				   + " 					a.SplAmount,a.DelivType "
				   + " 					from tPurchase a " 
				   + "					left join (select * from dbo.fStrToTable('"+nos+"',',')) b on a.No = b.item "
				   + " 					where a.IsCheckOut='0' "
				   + " 					and a.Supplier = ? "
				   + " 					and a.Status = 'CONFIRMED' and a.SecondAmount=0 "  
				   + " 					and b.item is not null "
				   + " 					union "
				   + " 					select a.OtherCost,a.OtherCostAdj,0,a.No,a.Status,a.Date,a.Type,a.WHCode, "
				   + "					a.Supplier,a.Amount,a.Remarks,0,'',a.CustCode,a.CheckSeq,0 xmjljsj,a.ItemType1, "
				   + " 					a.FirstAmount,a.SecondAmount, "
				   + " 					round(case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj-a.FirstAmount-a.SecondAmount,0) RemainAmount, "
				   + "					a.SplAmount,a.DelivType "
				   + " 					from tPurchase a "
				   + "					left join (select * from dbo.fStrToTable('"+nos+"',',')) b on a.No = b.item "
				   + " 					where a.CheckOutNo=? "
				   + " 					and a.IsCheckOut='1' "
				   + " 					and b.item is not null "
				   + " 				) a "
				   + "				inner join tItemApp ia on ia.PUNo=a.no "
				   + " 				where ia.IsSetItem='0' and not exists( "
				   + " 					select 1 from tPurchase b "
				   + " 					inner join tItemApp iab on iab.PUNo=b.No "
				   + " 					left outer join tSplCheckOut sco on sco.No=b.CheckOutNo "
				   + " 					where b.CustCode=a.CustCode and b.ItemType1=a.ItemType1 and iab.IsService=ia.IsService "
				   + " 					and (b.IsCheckOut='0' or b.IsCheckOut='1' and sco.status='1') "
				   + " 					and not exists( "
				   + " 						select 1 from ( "
				   + " 							select a.OtherCost,a.OtherCostAdj,a.ProjectOtherCost,a.No,a.Status,a.Date,a.Type,a.WHCode, "
				   + " 							a.Supplier,a.Amount,a.Remarks,a.IsCheckOut,a.CheckOutNo,a.CustCode,a.CheckSeq,cast(0 as money) xmjljsj,a.ItemType1, " 
				   + "							a.FirstAmount,a.SecondAmount, "
				   + " 							round(case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj-a.FirstAmount-a.SecondAmount,0) RemainAmount, "
				   + " 							a.SplAmount,a.DelivType "
				   + " 							from tPurchase a " 
				   + "							left join (select * from dbo.fStrToTable('"+nos+"',',')) b on a.No = b.item "
				   + " 							where a.IsCheckOut='0' "
				   + " 							and a.Supplier = ? "
				   + " 							and a.Status = 'CONFIRMED' and a.SecondAmount=0 "  
				   + " 							and b.item is not null "
				   + " 							union "
				   + " 							select a.OtherCost,a.OtherCostAdj,0,a.No,a.Status,a.Date,a.Type,a.WHCode, "
				   + "							a.Supplier,a.Amount,a.Remarks,0,'',a.CustCode,a.CheckSeq,0 xmjljsj,a.ItemType1, "
				   + " 							a.FirstAmount,a.SecondAmount, "
				   + " 							round(case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj-a.FirstAmount-a.SecondAmount,0) RemainAmount, "
				   + "							a.SplAmount,a.DelivType "
				   + " 							from tPurchase a "
				   + "							left join (select * from dbo.fStrToTable('"+nos+"',',')) b on a.No = b.item "
				   + " 							where a.CheckOutNo=? "
				   + " 							and a.IsCheckOut='1' "
				   + " 							and b.item is not null "
				   + "						)c where b.No=c.no "
				   + " 					) "
				   + " 				) "
				   + " 				group by a.custcode,a.itemtype1,ia.IsService "
				   + "			) t1 "
				   + " 			where t1.LastNum>0 "
				   + " 		) y1 "
				   + " 		inner join tCustomer y2 on y2.Code=y1.custcode "
		           + " 		inner join tItemType1 it1 on it1.Code=y1.ItemType1 "
		           + " 		left outer join tXTDM x1 on x1.ID='YESNO' and y1.IsService=x1.CBM "
		           + " 		left outer join tXTDM x2 on x2.ID='CUSTOMERSTATUS' and y2.Status=x2.CBM "
		           + " 		left outer join tEmployee te on te.Number=y2.ProjectMan "
		           + " 		where y1.chaochue>0 and y2.CheckStatus in('2','3','4') ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{splCode, checkOutNo, splCode, checkOutNo});
	}
	
	public List<Map<String,Object>> goJqGridWithHold(Page<Map<String,Object>> page, String nos, String splCode, String checkOutNo){
		String sql = " select * from ( select a.no,e.No appno,e.isservice,x1.NOTE isservicedescr,d.Address address,a.type,c.NOTE typedescr,d.documentno, "
				   + " 		round(case when a.Type='S' then a.bxmjljsj else -a.bxmjljsj end + a.OtherCost+a.OtherCostAdj+a.ProjectOtherCost,0) xmjljszj,f.Amount xmjljsygj, "
				   + " 		round(case when a.Type='S' then a.bxmjljsj else -a.bxmjljsj end + a.OtherCost+a.OtherCostAdj+a.ProjectOtherCost,0)-f.Amount xmjljscj ,arrivestatus,arriveremark, "
				   + "		d.checkstatus,x2.Note checkstatusdescr "
		           + " 		from ( select a.*,b.xmjljsj bxmjljsj from ( "
				   + " 			select a.OtherCost,a.OtherCostAdj,a.ProjectOtherCost,a.No,a.Status,a.Date,a.Type,a.WHCode, "
				   + " 			a.Supplier,a.Amount,a.Remarks,a.IsCheckOut,a.CheckOutNo,a.CustCode,a.CheckSeq,a.ProjectAmount xmjljsj,a.ItemType1, " 
				   + "			a.FirstAmount,a.SecondAmount, "
				   + " 			round(case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj-a.FirstAmount-a.SecondAmount,0) RemainAmount, "
				   + " 			a.SplAmount,a.DelivType,a.ArriveStatus,a.ArriveRemark "
				   + " 			from tPurchase a " 
				   + "			left join (select * from dbo.fStrToTable('"+nos+"',',')) b on a.No = b.item "
				   + " 			where a.IsCheckOut='0' "
				   + " 			and a.Supplier = ? "
				   + " 			and a.Status = 'CONFIRMED' and a.SecondAmount=0 "  
				   + " 			and b.item is not null "
				   + " 			union "
				   + " 			select a.OtherCost,a.OtherCostAdj,0,a.No,a.Status,a.Date,a.Type,a.WHCode, "
				   + "			a.Supplier,a.Amount,a.Remarks,0,'',a.CustCode,a.CheckSeq,0 xmjljsj,a.ItemType1, "
				   + " 			a.FirstAmount,a.SecondAmount, "
				   + " 			round(case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj-a.FirstAmount-a.SecondAmount,0) RemainAmount, "
				   + "			a.SplAmount,a.DelivType,a.ArriveStatus,a.ArriveRemark "
				   + " 			from tPurchase a "
				   + "			left join (select * from dbo.fStrToTable('"+nos+"',',')) b on a.No = b.item "
				   + " 			where a.CheckOutNo=? "
				   + " 			and a.IsCheckOut='1' "
				   + " 			and b.item is not null "
				   + "		) a, ( "
				   + " 			select a.No,sum(isnull(b.ProjectCost*b.QtyCal,0)) xmjljsj "
				   + " 			from tPurchase a inner join tPurchaseDetail b on a.No=b.PUNo " 
				   + " 			group by a.No,a.OtherCost,a.OtherCostAdj "
				   + " 		) b where a.No = b.No) a"
		           + " 		inner join tItemApp e on a.No=e.PUNo "
		           + " 		inner join tPrjWithHold f on f.ItemAppNo=e.No "
		           + " 		left outer join tXTDM c on c.CBM=a.Type and c.ID='PURCHTYPE' "
		           + " 		left outer join tCustomer d on d.Code=a.CustCode "
		           + " 		left outer join tXTDM x1 on x1.IBM=e.IsService and x1.id='YESNO' "
		           + " 		left outer join tXTDM x2 on x2.CBM=d.CheckStatus and x2.id='CheckStatus' ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{splCode, checkOutNo});
	}
	
	public Result doSaveForProc(SplCheckOut splCheckOut){
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGysjsSave_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, splCheckOut.getDetailJson());
			call.setString(2, splCheckOut.getM_umState());
			call.setString(3, splCheckOut.getNo());
			call.setString(4, splCheckOut.getSplCode());
			call.setString(5, splCheckOut.getPayType());
			call.setTimestamp(6, new Timestamp(new Date("2011/01/11 00:00:00").getTime()));
			call.setTimestamp(7, new Timestamp(new Date("2011/01/11 23:59:59").getTime()));
			call.setDouble(8, splCheckOut.getOtherCost());
			call.setString(9, splCheckOut.getRemark());
			call.setString(10, splCheckOut.getLastUpdatedBy());
			call.setString(11, splCheckOut.getDocumentNo());
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(12)));
			result.setInfo(call.getString(13));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Result doShForProc(SplCheckOut splCheckOut){
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGysjs_Sh_forXml(?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, splCheckOut.getDetailJson());
			call.setString(2, splCheckOut.getNo());
			call.setString(3, splCheckOut.getStatus());
			call.setString(4, splCheckOut.getRemark());
			call.setString(5, splCheckOut.getLastUpdatedBy());
			call.setString(6, splCheckOut.getConfirmCzy());
			call.setString(7, splCheckOut.getDocumentNo());
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.NVARCHAR);
			call.setDouble(10, splCheckOut.getPreAmount());
			call.execute();
			result.setCode(String.valueOf(call.getInt(8)));
			result.setInfo(call.getString(9));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Map<String, Object> getSplCheckOutByNo(String no){
		String sql = " select tsco.no,tsco.status,tsco.splCode,tsco.date,tsco.payType,tsco.beginDate,tsco.endDate,round(tsco.payAmount,2) payAmount,tsco.paidAmount,tsco.nowAmount,tsco.preAmount, "
				   + " 		  tsco.otherCost,tsco.remark,tsco.documentNo,tsco.confirmDate,tspl.cardId,tspl.bank,tspl.actName, "
				   + " 		  tspl.Descr splCodeDescr,tspl.itemType1 "
				   + " from tSplCheckOut tsco "
				   + " left join tSupplier tspl on tspl.Code = tsco.splCode "
				   + " where tsco.no=? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{no});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Map<String, Object> checkSupplierPay(String no){
		String sql = " select 1 from tSupplierPay where Status<>'3' and CheckOutNo=? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{no});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public List<Map<String, Object>> judgePrintPage(String no){
		String sql = " select 1 "
				   + " from ( "
				   + " 		select a.PayRemark,a.OtherCost,a.OtherCostAdj,a.ProjectOtherCost,a.No,a.Status,a.Date,a.Type,a.WHCode, "
				   + " 		a.Supplier,a.Amount,a.Remarks,a.IsCheckOut,a.CheckOutNo,a.CustCode,a.CheckSeq,ProjectAmount xmjljsj,a.ItemType1,a.OverCost, "
			       + " 		a.FirstAmount,a.SecondAmount,round(case when a.Type='S' then a.Amount else -a.Amount end +a.OtherCost+a.OtherCostAdj-a.FirstAmount-a.SecondAmount,0) RemainAmount," //^_^ 20160110 add by xzp
			       + " 		a.SplAmount,a.ArriveStatus,a.ArriveRemark  "    //显示问题  增加 ,ArriveStatus,ArriveRemark 字段by zjf
			       + " 		from tPurchase a "
			       + " 		where a.CheckOutNo=? "
			       + " ) a "
			       + " left outer join tItemApp e on a.No=e.PUNo where e.No is null ";
		return this.findBySql(sql, new Object[]{no});
	}
	
	/**
	 * 自动生成其他费用计算
	 * @param itemAppSend
	 * @return
	 */
	public Map<String, Object> doGenOtherCostForProc(
			SplCheckOut splCheckOut) {
		Connection conn = null;
		CallableStatement call = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call PGenSupplOtherFee_forXml(?,?,?,?)}");
			call.setString(1, splCheckOut.getLastUpdatedBy());
			call.setString(2, splCheckOut.getDetailJson());
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.execute();
			ResultSet rs1 = call.getResultSet();
			List<Map<String, Object>> list1 = BeanConvertUtil
					.resultSetToList(rs1);
			map.put("list1", list1);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return null;
	}
	
	public Page<Map<String,Object>> goJqGridMainItemByCompany(Page<Map<String,Object>> page, SplCheckOut splCheckOut){
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from (select	a.cmpname, sum(remainamount) amount" +
				" from" +
				"	(select	round(case	when a.Type = 'S' then a.Amount" +
				"						else -a.Amount" +
				"					end + a.OtherCost + a.OtherCostAdj - a.FirstAmount - a.SecondAmount, 2) remainamount," +
				"			case when g.DelivType = '1' then n.descr" +
				"				when (select ','+QZ+',' from tXTCS where ID = 'AftCustCode') like '%,'+a.CustCode+',%' then m.Descr else o.Descr end  cmpname," +
				"				 case when g.DelivType = '1' then wh.TaxPayeeCode  " +
				"				when (select ','+QZ+',' from tXTCS where ID = 'AftCustCode') like '%,'+a.CustCode+',%' then m.Code else o.Code end TaxPayeeCode " +
				"		from" +
				"			(select	a.CustCode, a.OtherCost, a.OtherCostAdj, a.Type, a.WHCode, a.Amount, a.FirstAmount," +
				"					a.SecondAmount, a.Supplier, a.No" +
				"				from" +
				"					tPurchase a" +
				"				where" +
				"					a.CheckOutNo = ?) a" +
				"		left join tWareHouse wh on wh.Code = a.whcode" +
				"		left join (select max(no) no from tPurchase where CheckOutNo = ?)f on 1=1" +
				"		left join tPurchase g on g.No = f.no" +
				"		left outer join tSupplier b on b.Code = a.Supplier" +
				"		left outer join tXTDM c on c.CBM = a.Type" +
				"									and c.ID = 'PURCHTYPE'" +
				"		left outer join tItemApp e on a.No = e.PUNo " +
				"		left join tItemApp h on h.PUNo = a.no  and g.DelivType ='2' " +//--非集采 关联关联客户
				"		left join tCustomer d on d.Code = a.custcode" +
				"		left join tCustomer i on i.Code = h.RefCustCode" +
				"		left join tTaxPayee n on n.Code = wh.TaxPayeeCode" +
				"		left join tTaxPayee m on m.Code = i.PayeeCode " +//--非集采 按关联客户
				"		left join tTaxPayee o on o.Code = d.PayeeCode" +
				" ) a" +
				" group by a.TaxPayeeCode, a.cmpname";
		params.add(splCheckOut.getCheckOutNo());
		params.add(splCheckOut.getCheckOutNo());
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.cmpname desc ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}
	/**
	 * 按材料类型3汇总
	 * @author	created by zb
	 * @date	2020-4-22--下午4:44:44
	 * @param page
	 * @param splCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> goJqGridMainItemByItemType3(Page<Map<String,Object>> page, SplCheckOut splCheckOut){
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from (select c.ItemType3,d.Descr ItemType3Descr, "
				+"sum(case when a.Type='S' then b.Amount else -b.Amount end) Amount " +
				"	,sum(isnull(case when a.Type = 'S' then b.ProjectCost*b.qtyCal else -b.ProjectCost*b.qtyCal end,0)) projectCost "
				+"from   tPurchase a "
				+"inner join tPurchaseDetail b on b.PUNo=a.No "
				+"left join tItem c on c.Code=b.ITCode "
				+"left join tItemType3 d on d.Code=c.ItemType3 "
				+"where  a.CheckOutNo = ? "
				+"group by c.ItemType3,d.Descr ";
		params.add(splCheckOut.getCheckOutNo());
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.ItemType3 desc ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	/**
	 * 按楼盘部门汇总
	 * @param page
	 * @param splCheckOut
	 * @return
	 */
	public Page<Map<String,Object>> goJqGridMainItemByCustDept(Page<Map<String,Object>> page, SplCheckOut splCheckOut){
		List<Object> params = new ArrayList<Object>();
		String sql = "select e.Address,case when d2.Desc1='' or d2.Desc1 is null then d1.desc1 else d2.Desc1 end department, "
			+"sum(((case when f.Type='S' then f.Amount else -1*f.Amount end)+ f.OtherCost + f.OtherCostAdj )*b.SharePer )cost," 
			+"left(g.itemDescrs, len(g.itemDescrs) - 1)+': '+e.Address abstract, h.Descr NetChanelDescr "
			+"from  tGiftApp a  "
			+"left join tGiftStakeholder b on b.No = case when a.OutType='2' then a.OldNo else a.No end "
			+"left join dbo.tDepartment1 d1 on d1.Code=b.Department1 " 
			+"left join dbo.tDepartment2 d2 on d2.Code=b.Department2 "
			+"left join tCustomer e on a.CustCode=e.Code "
			+"left join tPurchase f on a.PUNo=f.No "
			+"left join ( "  
			+"	select in_e.Department1,in_e.Department2 ,in_b.CustCode , "
			+"	( select b.descr + '、' "  
			+"	  from tGiftAppDetail a "  
			+"	  left join tItem b on a.ItemCode = b.code  " 
			+"	  inner join tGiftApp in_b1 on a.No = in_b1.No "
			+"	  left join tGiftStakeholder in_e1 on in_e1.No = case when in_b1.OutType='2' then in_b1.OldNo else in_b1.No end " 
			+"	  left join dbo.tDepartment1 in_c1 on in_c1.Code=in_e1.Department1 "  
			+"	  left join dbo.tDepartment2 in_d1 on in_d1.Code=in_e1.Department2 "
			+"    left join tPurchase in_f1 on in_b1.PUNo=in_f1.No "
			+"	  where in_e1.Department1=in_e.Department1 and in_e1.Department2=in_e.Department2 and in_b1.CustCode=in_b.CustCode and in_f1.CheckOutNo=? " 
			+"	  for xml path('') "  
			+"	) as itemDescrs "  
			+"	from tGiftAppDetail in_a "
			+"	inner join tGiftApp in_b on in_a.No = in_b.No "
			+"	left join tGiftStakeholder in_e on in_e.No = case when in_b.OutType='2' then in_b.OldNo else in_b.No end " 
			+"	left join dbo.tDepartment1 in_c on in_c.Code=in_e.Department1 "  
			+"	left join dbo.tDepartment2 in_d on in_d.Code=in_e.Department2 " 
			+"  left join tPurchase in_f on in_b.PUNo=in_f.No "
			+"  where in_f.CheckOutNo=? "  
			+"	group by in_e.Department1,in_e.Department2 ,in_b.CustCode " 
			+")g on g.Department1=b.Department1 and g.Department2=b.Department2 and g.CustCode=a.CustCode "
			+"left join tCustNetCnl h on e.NetChanel = h.Code "
			+"where f.CheckOutNo=? "
			+"group by d2.Code,d2.Desc1,d1.desc1,a.CustCode,e.Address,g.itemDescrs,h.Descr ";
		params.add(splCheckOut.getCheckOutNo());
		params.add(splCheckOut.getCheckOutNo());
		params.add(splCheckOut.getCheckOutNo());
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public String getNoChecAppReturnNum(String supplCode) {
		String sql = "select count(1) noCheckAppNum from tPurchase where type='R' " 
				+ " and (CheckOutNo is null or CheckOutNo='') and Supplier=? and status ='confirmed'  ";
		List<Map<String, Object>> list = this.findBySql(sql,new Object[] {supplCode });
		if (list != null && list.size() > 0) {
			return list.get(0).get("noCheckAppNum").toString();
		}
		return "0";
	}
	
	public List<Map<String, Object>> getDetailByCheckOutNo(SplCheckOut splCheckOut){
		
		String sql="select	a.cmpname TaxPayeeDescr, sum(Amount) Amount,TaxPayeeCode,a.DelivType,sum(remainamount) DetailAmount " +
				" from (select	round(case	when a.Type = 'S' then a.Amount else -a.Amount end, 2) Amount,g.DelivType," +
				"			round(case	when a.Type = 'S' then a.Amount else -a.Amount end, 2)+ a.OtherCost + a.OtherCostAdj remainamount," +
				"			case when g.DelivType = '1' then n.descr" +
				"				when (select ','+QZ+',' from tXTCS where ID = 'AftCustCode') like '%,'+a.CustCode+',%' then m.Descr else o.Descr end  cmpname," +
				"				 case when g.DelivType = '1' then wh.TaxPayeeCode  " +
				"				when (select ','+QZ+',' from tXTCS where ID = 'AftCustCode') like '%,'+a.CustCode+',%' then m.Code else o.Code end TaxPayeeCode " +
				"		from (select	a.CustCode, a.OtherCost, a.OtherCostAdj, a.Type, a.WHCode, a.Amount, a.FirstAmount," +
				"					a.SecondAmount, a.Supplier, a.No " +
				"			  from tPurchase a where a.CheckOutNo = ? " +
				"		) a" +
				"		left join tWareHouse wh on wh.Code = a.whcode" +
				"		left join (select max(no) no from tPurchase where CheckOutNo = ? )f on 1=1" +
				"		left join tPurchase g on g.No = f.no" +
				"		left outer join tSupplier b on b.Code = a.Supplier" +
				"		left outer join tXTDM c on c.CBM = a.Type and c.ID = 'PURCHTYPE'" +
				"		left outer join tItemApp e on a.No = e.PUNo " +
				"		left join tItemApp h on h.PUNo = a.no  and g.DelivType ='2'" +  // 非集采 关联关联客户
				"		left join tCustomer d on d.Code = a.custcode" +
				"		left join tCustomer i on i.Code = h.RefCustCode" +
				"		left join tTaxPayee n on n.Code = wh.TaxPayeeCode" +
				"		left join tTaxPayee m on m.Code = i.PayeeCode " +// 集采 按关联客户
				"		left join tTaxPayee o on o.Code = d.PayeeCode" +
				" ) a" +
				" group by a.TaxPayeeCode, a.cmpname,a.DelivType";
		List<Map<String , Object>> list = this.findBySql(sql, new Object[]{splCheckOut.getNo(), splCheckOut.getNo()});
		
		if(list!=null && list.size()>0){
			return list;
		}
		return null;
	}
	
	public Map<String, Object> getAmountByCheckOutNo(SplCheckOut splCheckOut){
		
		String sql="select sum(round(case	when a.Type = 'S' then a.Amount else -a.Amount end + a.OtherCost + a.OtherCostAdj, 2)) Amount, " +
				" sum(Round(a.FirstAmount, 2))PaidAmount," +
				" sum(round(case when a.Type = 'S' then a.Amount else -a.Amount end - a.FirstAmount + a.OtherCost + a.OtherCostAdj ,2))RealAmount " +
				" from tPurchase a where a.CheckOutNo =? ";
		List<Map<String , Object>> list = this.findBySql(sql, new Object[]{splCheckOut.getNo()});
		
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String, Object>> findProcListJqGrid(
            Page<Map<String, Object>> page, SplCheckOut splCheckOut) {
        
        List<Object> parameters = new ArrayList<Object>();

        String sql = "select * from (" +
        		" select WfProcInstNo, EmpCode, EmpName, RefNo, ApplyDate, ItemType1, Type, ClaimAmount," +
        		" DeptCode, DeptDescr, ClaimRemarks,Remarks, b.ActProcDefId " +
        		" from tWfCust_PurchaseExpense a " +
        		" left join tWfProcInst b on b.No = a.WfProcInstNo "
                + "where 1 = 1 ";

        if (StringUtils.isNotBlank(splCheckOut.getNo())) {
            sql += " and a.RefNo = ? ";
            parameters.add(splCheckOut.getNo());
        }
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.WfProcInstNo desc";
        }
        
        return this.findPageBySql(page, sql, parameters.toArray());
    }
}

