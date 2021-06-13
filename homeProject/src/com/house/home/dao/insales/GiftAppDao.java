package com.house.home.dao.insales;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Types;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.GiftApp;
import com.sun.accessibility.internal.resources.accessibility;

@SuppressWarnings("serial")
@Repository
public class GiftAppDao extends BaseDao {

	/**
	 * giftApp分页信息
	 * 
	 * @param page
	 * @param itemSet
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, GiftApp giftApp,UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		String sql =  "select * from (select a.no, a.Type, x1.NOTE TypeDescr, a.CustCode, b.Descr CustDescr,"
				   + " b.Address, b.DocumentNo CustDocNo, b.SignDate, b.SetDate,a.UseMan,e1.NameChi useManDescr,"
		           + " a.Phone,a.Status,a.ActNo,c.Descr ActDescr,a.SendType,x3.NOTE  SendTypeDescr,a.SupplCode,a.PUNo,"
				   + " a.SplStatus, x7.NOTE SplStatusDescr, a.SplRemark, a.SplRcvDate, a.SplRcvCZY, e4.ZWXM SplRcvCZYDescr, "
		           + " case when sendType='2' then a.CheckOutNo when sendType='1' then e.No end CheckOutNo,case when sendType='2' then f.DocumentNo when sendType='1' then e.DocumentNo end  CheckOutDocumentNo,"
		           + " a.WHCode ,g.Desc1 whDescr,case when sendType='2' then x4.note when sendType='1' then x6.note end CheckOutStatus,x2.NOTE statusDescr ,"
		           + " a.Remarks,a.IsCheckOut,a.CheckSeq,a.OldNo,a.SendCZY,a.outType,x5.note outtypeDescr,a.date,a.senddate,"
		           + " a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog,d.Descr supplDescr,e2.ZWXM AppCZYDescr,e3.ZWXM  SendCZYDescr "
				   + " from tGiftApp a "
				   + " left outer join tcustomer b on b.code = a.CustCode "
				   + " left outer join tCmpActivity c on c.no=a.ActNo  "
				   + " left outer join tSupplier d on d.code=a.SupplCode "
				   + " left outer join tPurchase h on h.no=a.puno "
				   + " left outer join tSplCheckOut e on h.CheckOutNo=e.no "
				   + " left outer join tGiftCheckOut f on f.no=a.CheckOutNo "
				   + " left outer join tWareHouse g on g.code=a.WHCode "
				   + " left outer join txtdm x1 on x1.CBM = a.Type and x1.id = 'GIFTAPPTYPE' "
				   + " left outer join txtdm x2 on x2.CBM = a.Status and x2.id = 'GIFTAPPSTATUS' "
				   + " left outer join txtdm x3 on  x3.cbm=a.SendType and x3.id='GIFTAPPSENDTYPE' "
				   + " left outer join txtdm x4 on x4.cbm=f.Status and  x4.id='GFChkOutStatus' "
				   + " left outer join txtdm x5 on x5.cbm=a.outType and  x5.id='GIFTAPPOUTTYPE' "
				   + " left outer join txtdm x6 on x6.cbm=e.Status and  x6.id='SPLCKOTSTATUS' "
				   + " left outer join txtdm x7 on x7.CBM = a.SplStatus and x7.ID = 'GIFTAPPSPLSTAT' "
				   + " left outer join tEmployee e1 on e1.Number=a.UseMan  "
		           + " left outer join tCZYBM e2 on e2.CZYBH=a.appczy "
		           + " left outer join tCZYBM e3 on e3.CZYBH=a.sendczy "
		           + " left outer join tCZYBM e4 on e4.CZYBH=a.SplRcvCZY "
		           + " where 1=1 ";
		if (StringUtils.isNotBlank(giftApp.getNo())) {
			sql += " and a.no like ? ";
			list.add("%"+giftApp.getNo()+"%");
	
		}
		if (StringUtils.isNotBlank(giftApp.getAddress())) {
			sql += " and b.address like ? ";
			list.add("%"+giftApp.getAddress()+"%");
		}
    	
    	if (StringUtils.isNotBlank(giftApp.getCustCode())) {
			sql += " and a.custcode = ? ";
			list.add(giftApp.getCustCode());
		}  	
    	
    	if (StringUtils.isNotBlank(giftApp.getUseMan())) {
			sql += " and a.useman = ? ";
			list.add(giftApp.getUseMan());
		}
    	if (StringUtils.isNotBlank(giftApp.getSendType())) {
			sql += " and a.sendtype = ? ";
			list.add(giftApp.getSendType());
		}
    	if (StringUtils.isNotBlank(giftApp.getActNo())) {
			sql += " and a.actno = ? ";
			list.add(giftApp.getActNo());
		}
    	if (StringUtils.isNotBlank(giftApp.getSupplCode())) {
			sql += " and a.SupplCode = ? ";
			list.add(giftApp.getSupplCode());
		}
    	if (StringUtils.isNotBlank(giftApp.getWhCode())) {
			sql += " and a.WhCode = ? ";
			list.add(giftApp.getWhCode());
		}
    	
    	if (StringUtils.isNotBlank(giftApp.getStatus())) {
			sql += " and a.Status in " + "('"+giftApp.getStatus().replaceAll(",", "','")+"')";
		}
    	
    	if (StringUtils.isNotBlank(giftApp.getSplStatus())) {
            sql += " and a.SplStatus in " + "('" + giftApp.getSplStatus().replaceAll(",", "','") + "')";
        }
		
    	if (giftApp.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(giftApp.getDateFrom());
		}
    	if (giftApp.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(DateUtil.addDateOneDay(giftApp.getDateTo()));
		}
    	if (giftApp.getSendDateFrom() != null){
			sql += " and a.sendDate>= ? ";
			list.add(giftApp.getSendDateFrom());
		}
    	if (giftApp.getSendDateTo() != null){
			sql += " and a.sendDate<= ? ";
			list.add(DateUtil.addDateOneDay(giftApp.getSendDateTo()));
		}
    	if (StringUtils.isNotBlank(giftApp.getOutType())) {
			sql += " and a.OutType = ? ";
			list.add(giftApp.getOutType());
		}
    	if (StringUtils.isNotBlank(giftApp.getType())) {
			sql += " and a.Type = ? ";
			list.add(giftApp.getType());
		}
    	
    	if (StringUtils.isNotBlank(giftApp.getItemCode())){
			sql += " and exists(select 1 from tGiftAppDetail where no=a.no and ItemCode=? )";
			list.add(giftApp.getItemCode());
		}
    	if (StringUtils.isNotBlank(giftApp.getAppCzy())) {
			sql += " and a.AppCzy = ? ";
			list.add(giftApp.getAppCzy());
		}
		if (StringUtils.isBlank(giftApp.getExpired())
				|| "F".equals(giftApp.getExpired())) {
			sql += " and a.Expired='F' ";
		}	
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	//礼品领用明细表
	public Page<Map<String, Object>> findPageBySqlGiftAppDetail(
			Page<Map<String, Object>> page, GiftApp giftApp) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select * from( "; 
		if (StringUtils.isNotBlank(giftApp.getOldNo())) {
			sql+= " select  a.ItemCode, b.Descr itemDescr, u.Descr uomDescr, a.TokenPk,gt.TokenNo, a.Qty, "
	                   + " a.Price, a.SendQty,  a.Qty*a.cost sumcost,a.cost,a.Remarks, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog,"
	                   +" isnull(C.Sendqtyed,0)-isnull(D.ReturnQtyed,0) SendQtyed,a.UseDiscAmount  " //,x1.note UseDiscAmountDescr
	                   + " from   tGiftAppDetail a "
	                   + " left outer join tItem b on b.code = a.ItemCode "
	                   + " left outer join tUOM u on u.Code = b.Uom"
	                   + " left outer join tGiftToken  gt on gt.PK=a.TokenPk "
					   + " left outer join (select sum(gad.qty) Sendqtyed ,gad.ItemCode from   tGiftAppDetail gad "
		               + " 		inner join tGiftApp ga on ga.no=gad.No where status='Send' and gad.no=?  group by  gad.ItemCode  )c on c.ItemCode=a.ItemCode "
		   			   + " left  outer join (select  sum(gad.qty) ReturnQtyed ,gad.ItemCode from   tGiftAppDetail gad"
		   			   + " 		inner join tGiftApp ga on ga.no=gad.No where status='return' and ga.OldNo=?  group by  gad.ItemCode  )d on d.ItemCode=a.ItemCode "	 
		   		       // + " left join txtdm x1 on x1.id = 'YESNO' and x1.cbm=a.UseDiscAmount "
		   			   + " where 1=1 and a.Expired='F' and a.No=?";
			if (StringUtils.isNotBlank(giftApp.getNo())) {
				 	list.add(giftApp.getOldNo());
				 	list.add(giftApp.getOldNo());
					list.add(giftApp.getNo());
			}else{
				return null;
			}
	   }else{
		   sql+=" select  a.ItemCode, b.Descr itemDescr, u.Descr uomDescr, a.TokenPk,gt.TokenNo, a.Qty, "
	                   + " a.Price, isnull(c.Sendqty,0)-isnull(d.ReturnQtyed,0) SendQty," 
	                   +" a.Qty*a.cost sumcost,a.cost,a.Remarks, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog, "
	                   +" a.UseDiscAmount "// ,x1.note UseDiscAmountDescr
	                   + " from   tGiftAppDetail a "
	                   + " left outer join tItem b on b.code = a.ItemCode "
	                   + " left outer join tUOM u on u.Code = b.Uom"
	                   + " left outer join tGiftToken  gt on gt.PK=a.TokenPk "	
	                   + " left outer join tGiftApp e on e.no=a.No "
	                   + " left outer join ( select sum(gad.qty) sendqty, gad.itemcode,ga.CustCode from  tgiftappdetail gad "
	                   + "   inner join tgiftapp ga on ga.no = gad.no where   status <>'cancel'  and  OutType='1' and ga.CustCode<>'' "
	                   + "   group by  gad.itemcode ,ga.CustCode  ) c on c.itemcode = a.itemcode and c.CustCode=e.CustCode "
	                   + " left  outer join ( select   sum(gad.qty) returnqtyed, gad.itemcode,ga.CustCode from tgiftappdetail gad  "
	                   + "   inner join tgiftapp ga on ga.no = gad.no where  status = 'return' and ga.CustCode<>'' " 
	                   + "   group by gad.itemcode,ga.CustCode ) d on d.itemcode = a.itemcode and d.CustCode=e.CustCode  "
		   		      // + " left join txtdm x1 on x1.id = 'YESNO' and x1.cbm=a.UseDiscAmount "
			           + " where 1=1 and a.Expired='F' and a.No=? ";
			if (StringUtils.isNotBlank(giftApp.getNo())) {	
				list.add(giftApp.getNo());
			}else{
				return null;
			}	
	   }
		
	   if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
	   }else{
			sql += ")a order by a.ItemCode ";
	   }
	   return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String,Object>> findPageByQPrintSql(Page<Map<String,Object>> page, GiftApp giftApp) {
		List<Object> list = new ArrayList<Object>();
		String sql =  "select * from (	select  a.no, c.Descr custDescr, c.Address, Mobile1, a.itemcode,d.Descr itemDescr, a.qty,"
			 	+ " u.Descr uomDescr, a.Remarks "
				+ " from    tGiftAppDetail a "
		        + " inner join tGiftApp b on b.no = a.No "
		        + " left outer join tCustomer c on c.code = b.CustCode "
		        + " inner join tItem d on d.Code = a.ItemCode "
		        + " left outer join tUOM u on u.Code = d.Uom "
		        + "  where 1=1 ";
		if (StringUtils.isNotBlank(giftApp.getNo())) {
			sql += " and a.no like ? ";
			list.add("%"+giftApp.getNo()+"%");
	
		}
		if (StringUtils.isNotBlank(giftApp.getStatus())) {
			sql += " and b.Status in " + "('"+giftApp.getStatus().replaceAll(",", "','")+"')";
		}

    	if (StringUtils.isNotBlank(giftApp.getItemCode())){
			sql += " and a.itemCode=? " ;
			list.add(giftApp.getItemCode());
		}

    	
    	if (StringUtils.isNotBlank(giftApp.getSupplCode())) {
			sql += " and b.SupplCode = ? ";
			list.add(giftApp.getSupplCode());
		}
    	if (StringUtils.isNotBlank(giftApp.getType())) {
			sql += " and b.Type = ? ";
			list.add(giftApp.getType());
		}
    	if (StringUtils.isNotBlank(giftApp.getAddress())) {
			sql += " and c.address like ? ";
			list.add("%"+giftApp.getAddress()+"%");
		}
	
    	if (giftApp.getDateFrom() != null){
			sql += " and b.Date>= ? ";
			list.add(giftApp.getDateFrom());
		}
    	if (giftApp.getDateTo() != null){
			sql += " and b.Date<= ? ";
			list.add(DateUtil.addDateOneDay(giftApp.getDateTo()));
		
		}
 
		if (StringUtils.isBlank(giftApp.getExpired())
				|| "F".equals(giftApp.getExpired())) {
			sql += " and b.Expired='F' ";
		}	
		if (StringUtils.isNotBlank(giftApp.getAppCzy())) {
			sql += " and b.AppCzy = ? ";
			list.add(giftApp.getAppCzy());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.no desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	//礼品领用干系人
	public Page<Map<String, Object>> findPageBySqlGiftStakeholder(
			Page<Map<String, Object>> page,  GiftApp giftApp) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select  a.pk, a.no, a.Role, a.EmpCode, a.Department1, a.Department2, "
	               + " dpt1.Desc1 Department1Descr, dpt2.Desc1 Department2Descr, a.LastUpdate,"
	               + " a.LastUpdatedBy, a.Expired, a.ActionLog,b.Descr roleDescr,e1.NameChi EmpDescr,SharePer"
	               + " from tGiftStakeholder a"
	               + " left outer  join tRoll b on b.code = a.Role"
	               + " left outer  join tDepartment1 dpt1 on dpt1.Code = a.Department1"
	               + " left outer  join tDepartment2 dpt2 on dpt2.Code = a.Department2"
	               + " left outer join tEmployee e1 on e1.Number=a.EmpCode  "
		    	 + " where 1=1 and a.Expired='F' "; 									  					
		if (StringUtils.isNotBlank(giftApp.getNo())) {
			sql += " and a.No=? ";	
			list.add(giftApp.getNo());
		}else{
			sql +=" and a.No='' ";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Role ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	 //根据客户编号获取礼品领用干系人
	public Page<Map<String, Object>> findPageBySqlGiftStakeholderByCustCode(
			Page<Map<String, Object>> page,  GiftApp giftApp) {
		List<Object> list = new ArrayList<Object>();
		Boolean havePerformance = this.isHavePerformance(giftApp);
		
		String sql = " select  a.Role, a.EmpCode ,e1.Department1, e1.Department2,dpt1.Desc1 Department1Descr, dpt2.Desc1 Department2Descr, "
	               + " getdate() LastUpdate,a.LastUpdatedBy, 'F' Expired,a.ActionLog,b.Descr roleDescr,e1.NameChi EmpDescr,"
	               +"  case when e.SignDate >= '2020-08-01' then case when a.Role='00' then 0.4/rolenum else 0.6/rolenum end " +
	               "  else case when a.Role='00' then 0.5/rolenum else 0.5/rolenum end end SharePer ";   
		//领用类型为下定签单送时，干系人及部门优先从业绩表取最近一次正常业绩的干系人及部门，如果取不到，再按现在的方法取。
		//modify by zb on 2020423
	    if (havePerformance && "2".equals(giftApp.getType())) {	
	    	  sql += " from ( " 
			       + "	 select in_d.* "
				   + "	 from ( "
				   + "	   select in_b.CustCode,max(in_b.PK) PK "
				   + "	   from tPerformance in_b "
				   + "	   where in_b.Type='1' "
				   + "	   group by in_b.CustCode "
				   + "	 ) in_a "
				   + "	 left join tPerformance in_c on in_c.PK=in_a.PK "
				   + "	 left join tPerfStakeholder in_d on in_d.PerfPK=in_c.PK "
	    	  	   + " ) a ";
		} else {
			  sql += " from  tCustStakeholder  a";
		}
	          sql += " left outer join tRoll b on b.code = a.Role"
	               + " left outer join tEmployee e1 on e1.Number=a.EmpCode "
	               + " left outer join tDepartment1 dpt1 on dpt1.Code = e1.Department1 "
	               + " left outer join tDepartment2 dpt2 on dpt2.Code = e1.Department2  "
	               + " left outer join( "
	               + "		select CustCode,Role,count(*) rolenum from tCustStakeholder group by CustCode,Role "
	               + " )c on c.CustCode=a.CustCode and  c.Role=a.Role "
	               + " left outer join( "
	               + " 		select  CustCode,count(*) pernum from "
	               + " 			(select role,CustCode from tCustStakeholder where role in('00','01') group by CustCode,role "
	               + "			)rm group by CustCode "
	               + " )d on d.CustCode=a.CustCode 	"
	               + " left join tCustomer e on e.Code = a.CustCode "
		    	   + " where a.Role in('00','01') and a.Expired='F' "; 									  					
		if (StringUtils.isNotBlank(giftApp.getCustCode())) {
			sql += " and a.CustCode=? ";	
			list.add(giftApp.getCustCode());
		}else{
			sql +=" and a.CustCode='' ";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Role ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 是否存在正常业绩的业绩信息
	 * @author	created by zb
	 * @date	2020-4-23--下午6:18:16
	 * @param giftApp
	 * @return
	 */
	public Boolean isHavePerformance(GiftApp giftApp) {
		String sql = "select 1 from tPerformance where CustCode=? and Type='1' ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{giftApp.getCustCode()});
		if (null != list && list.size()>0) {
			return true;
		}
		return false;
	}
	
	//退回明细新增
	public Page<Map<String, Object>> findGiftAppDetailExistsReturn(
			Page<Map<String, Object>> page, Map<String, Object> param) {
		List<Object> list = new ArrayList<Object>();
		String no = (String)param.get("no");
		String unSelected = (String)param.get("unSelected");
		unSelected = SqlUtil.resetIntStatus(unSelected);
		String sql = "select * from (select a.no,a.ItemCode, b.Descr itemDescr, u.Descr uomDescr, a.TokenPk,gt.TokenNo, a.Qty, "
                   + " a.Price, a.SendQty, a.Remarks, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog,a.cost,"
				   +" isnull(C.Sendqtyed,0)-isnull(D.ReturnQtyed,0) Sendqtyed,a.UseDiscAmount " //,x1.note UseDiscAmountDescr
                   + " from   tGiftAppDetail a "
                   + " left outer join tItem b on b.code = a.ItemCode "
                   + " left outer join tUOM u on u.Code = b.Uom"
                   + " left outer join tGiftToken  gt on gt.PK=a.TokenPk "
                   + " left  outer join (select   sum(gad.qty) Sendqtyed ,gad.ItemCode from   tGiftAppDetail gad "
                   + "	inner join tGiftApp ga on ga.no=gad.No where status='Send' and gad.no=?  group by  gad.ItemCode  )c on c.ItemCode=a.ItemCode "
       			   + "  left  outer join (select  sum(gad.qty) ReturnQtyed ,gad.ItemCode from   tGiftAppDetail gad"
       			   + " inner join tGiftApp ga on ga.no=gad.No where status='return' and ga.OldNo=?  group by  gad.ItemCode  )d on d.ItemCode=a.ItemCode "	 
       			  // + " left join txtdm x1 on x1.id = 'YESNO' and x1.cbm=a.UseDiscAmount "
       			   + " where  a.no=? and a.Expired='F' ";	
		if (StringUtils.isNotBlank(no)){
			list.add(no);
			list.add(no);
			list.add(no);
		}else{
			return null;
		}
	
		if (StringUtils.isNotBlank(unSelected)){
			sql += "  and a.ItemCode not in ("+unSelected+") ";
		}else{
			sql += " and 1=1 ";
		}
			
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.itemCode ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	//保存
	@SuppressWarnings("deprecation")
	public Result doGiftAppForProc(GiftApp giftApp) {
		Assert.notNull(giftApp);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGiftApp(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, giftApp.getM_umState());
			call.setString(2, giftApp.getNo());	
			call.setString(3, giftApp.getType());
			call.setString(4, giftApp.getStatus());
			call.setString(5, giftApp.getCustCode());
			call.setString(6, giftApp.getUseMan());
			call.setString(7, giftApp.getPhone());
			call.setString(8, giftApp.getActNo());
			call.setString(9, giftApp.getSupplCode());
			call.setString(10, giftApp.getWhCode());
			call.setString(11, giftApp.getAppCzy());
			call.setString(12, giftApp.getSendCzy());
			call.setString(13, giftApp.getSendType());
			call.setString(14, giftApp.getOldNo());
			call.setString(15, giftApp.getOutType());
			call.setString(16, giftApp.getRemarks());
			call.setString(17, giftApp.getLastUpdatedBy());
			call.setString(18, giftApp.getExpired());
			call.setString(19, giftApp.getGiftAppDetailJson());
			call.setString(20, giftApp.getGiftStakeholderJson());
			call.registerOutParameter(21, Types.INTEGER);
			call.registerOutParameter(22, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(21)));
			result.setInfo(call.getString(22));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	
	//供应商直送
	@SuppressWarnings("deprecation")
	public Result doGiftAppSendBySuppForProc(GiftApp giftApp) {
	Assert.notNull(giftApp);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGiftAppSendBySuppl(?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, giftApp.getM_umState());
			call.setString(2, giftApp.getNo());	
			call.setString(3, giftApp.getWhCode());
			call.setString(4, giftApp.getSendCzy());
			call.setTimestamp(5, new java.sql.Timestamp(giftApp.getSendDate().getTime()));
			call.setString(6, giftApp.getSupplCode());
			call.setString(7, giftApp.getCustCode());
			call.setString(8, giftApp.getRemarks());
			call.setString(9, giftApp.getLastUpdatedBy());
			call.setString(10, giftApp.getExpired());
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(11)));
			result.setInfo(call.getString(12));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;	
	}
	
	//仓库发货
	@SuppressWarnings("deprecation")
	public Result doGiftAppSendForProc(GiftApp giftApp) {
		Assert.notNull(giftApp);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGiftAppSend(?,?,?,?,?,?,?,?,?)}");
			call.setString(1, giftApp.getM_umState());
			call.setString(2, giftApp.getNo());	
			call.setString(3, giftApp.getWhCode());
			call.setString(4, giftApp.getSendCzy());
			call.setTimestamp(5, new java.sql.Timestamp(giftApp.getSendDate().getTime()));
			call.setString(6, giftApp.getRemarks());
			call.setString(7, giftApp.getLastUpdatedBy());
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(8)));
			result.setInfo(call.getString(9));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DbUtil.close(null, call, conn);
		}
		return result;
			
	}		
	//退回
	@SuppressWarnings("deprecation")
	public Result doGiftAppReturnForProc(GiftApp giftApp) {
		Assert.notNull(giftApp);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGiftAppth(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, giftApp.getM_umState());
			call.setString(2, giftApp.getNo());	
			call.setString(3, giftApp.getType());
			call.setString(4, giftApp.getStatus());
			call.setString(5, giftApp.getCustCode());
			call.setString(6, giftApp.getUseMan());
			call.setString(7, giftApp.getPhone());
			call.setString(8, giftApp.getActNo());
			call.setString(9, giftApp.getSupplCode());
			call.setString(10, giftApp.getWhCode());
			call.setString(11, giftApp.getAppCzy());
			call.setString(12, giftApp.getSendCzy());
			call.setString(13, giftApp.getSendType());
			call.setString(14, giftApp.getOldNo());
			call.setString(15, giftApp.getOutType());
			call.setString(16, giftApp.getRemarks());
			call.setString(17, giftApp.getLastUpdatedBy());
			call.setString(18, giftApp.getExpired());
			call.setString(19, giftApp.getGiftAppDetailJson());
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(20)));
			result.setInfo(call.getString(21));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	public Map<String, Object> getSendQty(String itemCode,String custCode) {
		String sql = "select  isnull(sum (case when b.OutType='2' then case when b.Status='RETURN'  then -isnull(a.qty,0) "
                     + " else 0  end else isnull(a.qty,0) end ),0)sendQty from tGiftAppDetail a inner join  tGiftApp b on a.No=b.no "
                     + " where  a.ItemCode=?  and b.CustCode=?  and b.Status<>'CANCEL'    ";		
		List<Map<String, Object>> list =  this.findBySql(sql, new Object[]{itemCode,custCode});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String, Object>> findPageBySql_customerxx(
			Page<Map<String, Object>> page, GiftApp giftApp) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from (select a.no,a.Type,a.Status,a.CustCode,a.UseMan,a.Phone,a.ActNo,a.SupplCode,a.PUNo,a.WHCode,"+
				"a.Date,a.AppCZY,a.SendDate,a.SendCZY,a.Amount,a.OutType,a.Remarks,a.SendType,"+
				"a.IsCheckOut,a.CheckOutNo,a.CheckSeq,a.OldNo,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,"+
				"s.descr supplDescr,x1.note typedescr,x2.note StatusDescr,x3.note sendtypedescr," +
				" c.address,c.documentNo,e1.namechi useDescr,e2.nameChi appDescr ,ac.Descr ActName From tGiftApp a  " +
				" left join temployee e1 on e1.number=a.useMan " +
				" left join temployee e2 on e2.number=a.appCzy " +
				" left join tcustomer c on c.code=a.custcode " +
				" left join tSupplier s on s.code = a.supplCode " +
				" left join tCmpActivity ac on ac.No=a.ActNo "+
				" left join tXTDM x1 on a.type=x1.cbm and x1.id='ACTGIFTTYPE' " +
				" left join txtdm x2 on a.Status=x2.cbm and x2.id='GIFTAPPSTATUS' " +
				" left join txtdm x3 on a.sendType=x3.cbm and x3.id='GIFTAPPSENDTYPE' " +
				" where a.Expired='F' ";
		
		if(StringUtils.isNotBlank(giftApp.getCustCode())){
			sql+=" and a.custCode=?";
			list.add(giftApp.getCustCode());
		}else{
			return null;
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by " + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by no ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	public Page<Map<String,Object>> findPageBySql_Detail(Page<Map<String,Object>> page, GiftApp giftApp) {
		List<Object> list = new ArrayList<Object>();
		String sql =  "select * from (select  a.no,x2.NOTE statusDescr, b.date,  b.SendDate,x1.note TypeDescr, x5.note OutTypeDescr, x3.NOTE SendTypeDescr ,"
				+ " c.Descr ActDescr, b.CustCode,d.Address,a.ItemCode,i.Descr itemDescr,case when b.OutType='2' then -a.Qty else a.Qty end qty ,u.Descr uomDescr,a.Price,sp.Descr suppldescr,a.Remarks,b.LastUpdate,"
				+ " a.cost,case when b.OutType='2' then -a.Qty else a.Qty end * a.cost sumcost,b.CheckOutNo," 
				+ " gc.DocumentNo gcdocumentno,gt.tokenNo,g.Desc1 whDescr,e2.ZWXM AppCZYDescr,f.CheckOutNo splCheckOutNo,"
				+ " case when f.Type='S' then j.Amount else -j.Amount end splCheckOutAmount "
				+ " from tGiftAppDetail a "
				+ " inner join tGiftApp b on a.no=b.no "
                + " left  outer join tCmpActivity c on c.no=b.ActNo  "
                + " left  outer join tcustomer d on d.code = b.CustCode "
                + " left outer join txtdm x1 on x1.CBM = b.Type and x1.id = 'GIFTAPPTYPE'  "
                + " left outer join txtdm x2 on x2.CBM = b.Status and x2.id = 'GIFTAPPSTATUS'  "
                + " left  outer join  txtdm x3 on  x3.cbm=b.SendType and x3.id='GIFTAPPSENDTYPE' "
                + " left  outer join txtdm x5 on x5.cbm=b.outType and  x5.id='GIFTAPPOUTTYPE'  "
                + " left outer join tItem i on i.code = a.ItemCode "
                + " left outer join tUOM u on u.Code = i.Uom "
                + " left outer join tSupplier sp on sp.code=b.SupplCode "
                + " left outer join tGiftCheckOut gc on gc.No=b.CheckOutNo "
                + " left outer join tGiftToken gt on gt.PK=a.TokenPk "
                + " left outer join tWareHouse g on g.code=b.WHCode "
                + " left outer join  tCZYBM e2 on e2.CZYBH=b.appczy "
                + " left outer join tPurchaseDetail j on j.PUNo=b.PUNo and j.ITCode=a.ItemCode "
                + " left outer join tPurchase f on f.No=j.PUNo "
		        + " where 1=1 ";
		if (StringUtils.isNotBlank(giftApp.getNo())) {
			sql += " and a.no like ? ";
			list.add("%"+giftApp.getNo()+"%");
	
		}
		if (StringUtils.isNotBlank(giftApp.getAddress())) {
			sql += " and d.address like ? ";
			list.add("%"+giftApp.getAddress()+"%");
		}
		if (giftApp.getDateFrom() != null){
			sql += " and b.Date>= ? ";
			list.add(giftApp.getDateFrom());
		}
    	if (giftApp.getDateTo() != null){
			sql += " and b.Date<= ? ";
			list.add(DateUtil.addDateOneDay(giftApp.getDateTo()));
		}
    	if (giftApp.getSendDateFrom() != null){
			sql += " and b.sendDate>= ? ";
			list.add(giftApp.getSendDateFrom());
		}
    	if (giftApp.getSendDateTo() != null){
			sql += " and b.sendDate<= ? ";
			list.add(DateUtil.addDateOneDay(giftApp.getSendDateTo()));
		}
    	if (StringUtils.isNotBlank(giftApp.getStatus())) {
			sql += " and b.Status in " + "('"+giftApp.getStatus().replaceAll(",", "','")+"')";
		}
    	if (StringUtils.isNotBlank(giftApp.getOutType())) {
			sql += " and b.OutType = ? ";
			list.add(giftApp.getOutType());
		}
    	if (StringUtils.isNotBlank(giftApp.getType())) {
			sql += " and b.Type = ? ";
			list.add(giftApp.getType());
		}
    	if (StringUtils.isNotBlank(giftApp.getSendType())) {
			sql += " and b.sendtype = ? ";
			list.add(giftApp.getSendType());
		}
    	if (StringUtils.isNotBlank(giftApp.getSupplCode())) {
			sql += " and b.SupplCode = ? ";
			list.add(giftApp.getSupplCode());
		}
    	if (StringUtils.isNotBlank(giftApp.getItemCode())){
			sql += "  and a.ItemCode=? ";
			list.add(giftApp.getItemCode());
		}
    	
    	if (StringUtils.isNotBlank(giftApp.getActNo())) {
			sql += " and b.actno = ? ";
			list.add(giftApp.getActNo());
		}
    	
    	if (StringUtils.isNotBlank(giftApp.getCheckOutNo())) {
			sql += " and b.CheckOutNo like ? ";
			list.add("%"+giftApp.getCheckOutNo()+"%");
	
		}
    	if (StringUtils.isNotBlank(giftApp.getSplCheckOutNo())) {
			sql += " and f.CheckOutNo like ? ";
			list.add("%"+giftApp.getSplCheckOutNo().trim()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 获取礼品使用优惠金额
	 * @param custCode
	 * @return
	 */
	public double getGiftUseDisc(String custCode, String no) {
		String sql = " select isnull(sum(a.UseDiscAmount),0) giftUseDiscAmount "+
                  	 " from tGiftAppDetail a " + 
                  	 " left join tGiftApp b on a.No=b.no " + 
                  	 " where b.status='OPEN' and b.Type='2' and b.CustCode=?  and a.no<>? " +
                	 " and a.UseDiscAmount<>0 and b.OutType='1' ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] { custCode, no });
		if (list != null && list.size() > 0) {
			return (Double) list.get(0).get("giftUseDiscAmount");
		}
		return 0.0;
	}
}

