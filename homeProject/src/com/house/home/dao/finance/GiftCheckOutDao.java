package com.house.home.dao.finance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import sun.net.www.content.image.gif;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.StringUtil;
import com.house.home.entity.finance.GiftCheckOut;
import com.house.home.entity.insales.GiftApp;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.org.apache.xpath.internal.operations.And;

@SuppressWarnings("serial")
@Repository
public class GiftCheckOutDao extends BaseDao{
	
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, GiftCheckOut giftCheckOut  ) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select a.*,e1.NameChi AppCZYdescr,w.desc1 whDescr,x.note StatusDescr ,e2.NameChi ConfirmCZYdescr from tGiftCheckOut a  " +
				" left join tEmployee e1 on e1.Number=a.appCZY " +
				" left join tEmployee e2 on e2.Number=a.confirmCzy " +
				" left join tWareHouse w on w.code=a.whCode " +
				" left join tXTDM x on x.cbm=a.Status and x.id='WHChkOutStatus' " +
				" where 1=1  ";
		if(StringUtils.isNotBlank(giftCheckOut.getNo())){
			sql+=" and a.no =?";
			list.add(giftCheckOut.getNo());
		}
		if(StringUtils.isNotBlank(giftCheckOut.getStatus())){
			sql += " and a.Status in " + "('"+giftCheckOut.getStatus().replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(giftCheckOut.getWhCode())){
			sql+=" and a.whcode=?";
			list.add(giftCheckOut.getWhCode());
		}
		if(StringUtils.isNotBlank(giftCheckOut.getDocumentNo())){
			sql+=" and a.documentno like ?";
			list.add("%"+giftCheckOut.getDocumentNo()+"%");
		}
		if(giftCheckOut.getDateFrom()!=null){
			sql+=" and a.Date >=? ";
			list.add(giftCheckOut.getDateFrom());
		}
		if(giftCheckOut.getDateTo()!=null){
			sql+=" and a.Date <= DATEADD(d,1,?)";
			list.add(giftCheckOut.getDateTo());
		}
		if(giftCheckOut.getCheckDateFrom()!=null){
			sql+=" and a.checkDate >=? ";
			list.add(giftCheckOut.getCheckDateFrom());
		}
		if(giftCheckOut.getCheckDateTo()!=null){
			sql+=" and a.checkDate <= DATEADD(d,1,?)";
			list.add(giftCheckOut.getCheckDateTo());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "  order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += "  order by a.lastUpdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	public Page<Map<String, Object>> findAppPageBySql(
			Page<Map<String, Object>> page, GiftApp giftApp,GiftCheckOut giftCheckOut) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select  dbo.fGetGiftEmpPrint(case when a.status='RETURN' then a.oldNo else a.no end ,'BM')BM ," +
				" dbo.fGetGiftEmpPrint(case when a.status='RETURN' then a.oldNo else a.no end ,'BD')BD ,"+
				" dbo.fGetGiftEmpPrint(case when a.status='RETURN' then a.oldNo else a.no end ,'DM')DM ," +
				" dbo.fGetGiftEmpPrint(case when a.status='RETURN' then a.oldNo else a.no end ,'DD')DD ," +
				" case when a.status='RETURN' then -f.ItemSumCost else f.ItemSumCost end ItemSumCost,a.*,s.descr supplDescr,x1.note typedescr,x2.note StatusDescr,x3.note sendtypedescr," +
				" c.address,c.documentNo,e1.namechi useDescr,e2.nameChi appDescr ,ac.descr From tGiftApp a  " +
				" left join temployee e1 on e1.number=a.useMan " +
				" left join temployee e2 on e2.number=a.appCzy " +
				" left join tcustomer c on c.code=a.custcode " +
				" left join tSupplier s on s.code = a.supplCode " +
				" left join tCmpActivity ac on ac.No=a.ActNo " +
				" left join tXTDM x1 on a.type=x1.cbm and x1.id='GIFTAPPTYPE' " +
				" left join txtdm x2 on a.Status=x2.cbm and x2.id='GIFTAPPSTATUS' " +
				" left join txtdm x3 on a.sendType=x3.cbm and x3.id='GIFTAPPSENDTYPE' " +
				" left outer join (select No,sum(Qty*Cost) ItemSumCost from tgiftAppDetail group by No) f on f.no=a.no  " +
				" where 1=1 " +
				"and a.IsCheckOut='0' and a.status in ('return','Send') " +
				"" ;
		sql += " and a.no not in " + "('"+giftCheckOut.getAllDetail().replaceAll(",", "','")+"')";
		
		if(StringUtils.isNotBlank(giftCheckOut.getWhCode())){
			sql+=" and a.whCode= ?";
			list.add(giftCheckOut.getWhCode());
		}
		if(StringUtils.isNotBlank(giftApp.getNo())){
			sql+=" and a.No =?";
			list.add(giftApp.getNo());
		}
		if(StringUtils.isNotBlank(giftApp.getCustCode())){
			sql+=" and a.custCode=?";
			list.add(giftApp.getCustCode());
		}
		if(StringUtils.isNotBlank(giftApp.getAddress())){
			sql+=" and c.address like ?";
			list.add("%"+giftApp.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(giftApp.getStatus())){
			sql += " and a.Status in " + "('"+giftApp.getStatus().replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(giftApp.getType())){
			sql += " and a.Type in " + "('"+giftApp.getType().replaceAll(",", "','")+"')";
			
		}
		if(StringUtils.isNotBlank(giftApp.getAppCzy())){
			sql+=" and a.appCzy =? ";
			list.add(giftApp.getAppCzy());
		}
		if (giftApp.getDateFrom() != null) {
			sql += " and a.Date>= ? ";
			list.add(giftApp.getDateFrom());
		}
		if (giftApp.getDateTo() != null) {
			sql += " and a.Date<=DATEADD(d,1,?) ";
			list.add(giftApp.getDateTo());
		}
		if(StringUtils.isNotBlank(giftApp.getSupplCode())){
			sql+=" and a.supplCode=?";
			list.add(giftApp.getSupplCode());
		}
		if(StringUtils.isNotBlank(giftApp.getAddress())){
			sql+="and c.address like ?";
			list.add("%"+giftApp.getAddress()+"%");
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, GiftApp giftApp  ) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select dbo.fGetGiftEmpPrint(case when a.status='RETURN' then a.oldNo else a.no end,'BM')BM ," +
				" dbo.fGetGiftEmpPrint(case when a.status='RETURN' then a.oldNo else a.no end,'BD')BD ," +
				" dbo.fGetGiftEmpPrint(case when a.status='RETURN' then a.oldNo else a.no end,'DM')DM ," +
				" dbo.fGetGiftEmpPrint(case when a.status='RETURN' then a.oldNo else a.no end,'DD')DD ," +
				" case when a.status='RETURN' then -f.ItemSumCost else f.ItemSumCost end ItemSumCost ,a.*,x1.note typedescr,x2.note StatusDescr,x3.note sendtypedescr ,e1.namechi appdescr,e2.namechi usedescr" +
				" ,c.address ,c.documentNo,ac.descr,c.SetDate,c.SignDate,x4.NOTE CustomerEndCode,x5.NOTE CustSource,g.Descr NetChanelDescr" +
				" from tGiftApp a  " +
				" left join tcustomer c on c.code = a.custcode  " +
				" left join tEmployee e1 on e1.number = a.appCzy  " +
				" left join tEmployee e2 on e2.number = a.Useman  " +
				" left join tCmpActivity ac on ac.no =a.actno  " +
				" left join tXTDM x1 on a.type=x1.cbm and x1.id='GIFTAPPTYPE'  " +
				" left join txtdm x2 on a.Status=x2.cbm and x2.id='GIFTAPPSTATUS'  " +
				" left join txtdm x3 on a.sendType=x3.cbm and x3.id='GIFTAPPSENDTYPE'  " +
				" left outer join (select No,sum(Qty*Cost) ItemSumCost from tgiftAppDetail group by No) f on f.no=a.no  " +
				" left join txtdm x4 on c.EndCode=x4.cbm and x4.id='CUSTOMERENDCODE' "+
				" left join txtdm x5 on c.Source=x5.cbm and x5.id='CUSTOMERSOURCE' "+
				" left join tCustNetCnl g on c.NetChanel = g.Code "+
				" where 1=1  ";
		
		if(StringUtils.isNotBlank(giftApp.getCheckOutNo())){
			sql+=" and a.checkOutNo = ?";
			list.add(giftApp.getCheckOutNo());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.checkSeq ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findIssueDetailPageBySql(
			Page<Map<String, Object>> page, GiftApp giftApp  ) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select a.no, b.descr actDescr ,x1.NOTE apptypedescr,x2.NOTE outtypedescr,d.ItemCode,c.Descr itemdescr,d.Qty," +
				"	e.Descr uomDescr,d.Cost,d.Qty*d.Cost amount,d.Remarks  from tGiftApp a" +
				" left join tGiftAppDetail d on d.No=a.No" +
				" left join tCmpActivity b on b.No=a.ActNo " +
				" left join titem c on c.Code=d.ItemCode " +
				" left join tUOM e on e.Code=c.Uom" +
				" left join tXTDM x1 on x1.ID='GIFTAPPTYPE' and x1.CBM = a.Type" +
				" left join tXTDM x2 on x2.ID='GIFTAPPOUTTYPE' and x2.CBM=a.OutType" +
				" where 1=1 and a.status in ('SEND','RETURN')";
		if(StringUtils.isNotBlank(giftApp.getCustCode())){
			sql+=" and a.custCode = ? ";
			list.add(giftApp.getCustCode());
		} else {
			sql+=" and 1=2 ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findDetail_depaPageBySql(
			Page<Map<String, Object>> page, String no) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select sum((case when a.outType='2' then -ItemSumCost else ItemSumCost end )*b.SharePer )cost," +
				" case when d2.Desc1='' or d2.Desc1 is null then d1.desc1 else d2.Desc1 end department,f.Descr NetChanelDescr" +
				" from  tGiftApp a " +
				" left join tGiftStakeholder b on b.No = case when a.OutType='2' then a.OldNo else a.No end" +
				" left outer join ( " +
				" select No,sum(Qty*Cost) ItemSumCost from tgiftAppDetail group by No" +
				" ) c on c.no=a.no  " +
				" left join dbo.tDepartment1 d1 on d1.Code=b.Department1 " +
				" left join dbo.tDepartment2 d2 on d2.Code=b.Department2" +
				" left join tcustomer e on e.code = a.custcode "+
				" left join tCustNetCnl f on e.NetChanel = f.Code "+
				" where a.CheckOutNo= ?  " +
				" group by d2.Code,d2.Desc1,d1.desc1,f.Descr "+
				" order by d2.Desc1,d1.desc1";
		list.add(no); 
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result saveGiftCheckOut(GiftCheckOut giftCheckOut) {
		Assert.notNull(giftCheckOut);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGiftCheckOut_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setInt(1, giftCheckOut.getTemp());
			call.setString(2, giftCheckOut.getNo());
			call.setString(3, giftCheckOut.getWhCode());
			call.setTimestamp(4, giftCheckOut.getDate()== null ? null
					: new Timestamp(giftCheckOut.getDate().getTime()));
			call.setString(5, giftCheckOut.getAppCzy());
			call.setString(6, giftCheckOut.getConfirmCzy());
			call.setTimestamp(7, giftCheckOut.getConfirmDate()== null ? null
					: new Timestamp(giftCheckOut.getConfirmDate().getTime()));
			call.setString(8, giftCheckOut.getDocumentNo());
			call.setString(9, giftCheckOut.getRemarks());
			call.setTimestamp(10, giftCheckOut.getLastUpdate()== null ? null
					: new Timestamp(giftCheckOut.getLastUpdate().getTime()));
			call.setString(11, giftCheckOut.getLastUpdatedBy());
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.NVARCHAR);
			call.setString(14, giftCheckOut.getGiftAppDetailXml());
			call.execute();
			result.setCode(String.valueOf(call.getInt(12)));
			result.setInfo(call.getString(13));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Page<Map<String, Object>> findDetail_custDepaPageBySql(
			Page<Map<String, Object>> page, String no) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select e.Address,case when d2.Desc1='' or d2.Desc1 is null then d1.desc1 else d2.Desc1 end department, "
			+"sum((case when a.outType='2' then -ItemSumCost else ItemSumCost end )*b.SharePer )cost," 
			+"left(g.itemDescrs, len(g.itemDescrs) - 1)+': '+e.Address abstract,x1.NOTE CustSource,h.Descr NetChanelDescr "
			+"from  tGiftApp a  "
			+"left join tGiftStakeholder b on b.No = case when a.OutType='2' then a.OldNo else a.No end "
			+"left join (  "
			+"	select No,sum(Qty*Cost) ItemSumCost from tGiftAppDetail group by No "
			+") c on c.no=a.no "  
			+"left join dbo.tDepartment1 d1 on d1.Code=b.Department1 " 
			+"left join dbo.tDepartment2 d2 on d2.Code=b.Department2 "
			+"left join tCustomer e on a.CustCode=e.Code "
			+"left join ( "  
			+"	select in_e.Department1,in_e.Department2 ,in_b.CustCode , "
			+"	( select b.descr + '、' "  
			+"	  from tGiftAppDetail a "  
			+"	  left join tItem b on a.ItemCode = b.code  " 
			+"	  inner join tGiftApp in_b1 on a.No = in_b1.No "
			+"	  left join tGiftStakeholder in_e1 on in_e1.No = case when in_b1.OutType='2' then in_b1.OldNo else in_b1.No end " 
			+"	  left join dbo.tDepartment1 in_c1 on in_c1.Code=in_e1.Department1 "  
			+"	  left join dbo.tDepartment2 in_d1 on in_d1.Code=in_e1.Department2 "  
			+"	  where in_e1.Department1=in_e.Department1 and in_e1.Department2=in_e.Department2 and in_b1.CustCode=in_b.CustCode and in_b1.CheckOutNo=? " 
			+"	  for xml path('') "  
			+"	) as itemDescrs "  
			+"	from tGiftAppDetail in_a "
			+"	 inner join tGiftApp in_b on in_a.No = in_b.No "
			+"	left join tGiftStakeholder in_e on in_e.No = case when in_b.OutType='2' then in_b.OldNo else in_b.No end " 
			+"	left join dbo.tDepartment1 in_c on in_c.Code=in_e.Department1 "  
			+"	left join dbo.tDepartment2 in_d on in_d.Code=in_e.Department2 "
			+"  where in_b.CheckOutNo=? "  
			+"	  group by in_e.Department1,in_e.Department2 ,in_b.CustCode " 
			+")g on g.Department1=b.Department1 and g.Department2=b.Department2 and g.CustCode=a.CustCode "
			+" left join txtdm x1 on e.Source=x1.cbm and x1.id='CUSTOMERSOURCE' "
			+" left join tcustomer f on f.code = a.custcode "
			+" left join tCustNetCnl h on f.NetChanel = h.Code "
			+"where a.CheckOutNo=? "
			+"group by d2.Code,d2.Desc1,d1.desc1,a.CustCode,e.Address,g.itemDescrs,x1.NOTE,h.Descr ";
		list.add(no); 
		list.add(no); 
		list.add(no); 
		return this.findPageBySql(page, sql, list.toArray());
	}
}
