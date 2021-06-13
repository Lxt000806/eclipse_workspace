package com.house.home.dao.project;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.ListCompareUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ItemPlan;
import com.house.home.entity.project.ItemChg;
import com.sun.accessibility.internal.resources.accessibility;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.bcel.internal.generic.Select;

@SuppressWarnings("serial")
@Repository
public class ItemChgDao extends BaseDao {

	/**
	 * ItemChg分页信息
	 * 
	 * @param page
	 * @param itemChg
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemChg itemChg,UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from (select ep.nameChi designManDescr,ed.desc1 designDept2,a.IsService," 
				+ "e.NOTE as IsServiceDescr,a.No,a.ItemType1,b.Descr as ItemType1Descr,a.CustCode,c.Descr as CustomerDescr,c.Address,c.Area,a.Status, "
                + "d.NOTE  as StatusDescr,a.Date,a.BefAmount,a.DiscAmount,a.Amount,a.Remarks,a.Tax," 
                + "case when a.FaultType='1' then m.NameChi when a.FaultType='2' then n.NameChi else '' end FaultManDescr," 
                + "l.QualityFee PrjQualityFee,k.ProjectMan,o.NameChi ProjectManDescr, "
                + "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.AppCZY,a.ConfirmCZY,a.ConfirmDate,g.ZWXM as AppCZYDescr,h.ZWXM as ConfirmCZYDescr , "
                + "c.ContainMain,c.ContainSoft,c.ContainInt,c.ContainMainServ,c.DocumentNo,c.CustType,f.NOTE as  CustTypeDescr,a.ManageFee, "
                + "a.IsCupboard,i.NOTE as IsCupboardDescr,a.PerfPK,a.IscalPerf,j.NOTE as IscalPerfDescr "
                + " ,cast(dbo.fgetempnamechi(c.code, '50') as nvarchar(1000)) designEmp,a.ExceptionRemarks,p.Note ConfirmStatusDescr " 
                + " from tItemChg a "
                + " left outer join tItemType1 b on ltrim(rtrim(a.ItemType1))=ltrim(rtrim(b.Code)) "
                + " left outer join tCustomer c on a.CustCode=c.Code "
                + " left outer join tXTDM d on a.Status=d.CBM and d.ID='ITEMCHGSTATUS' "
                + " left outer join tXTDM e on a.IsService=e.IBM and e.ID='YESNO' "
                + " left outer join tXTDM f on c.CustType = f.CBM and f.ID = 'CUSTTYPE' "
                + " left outer join tCZYBM g  on g.czybh=a.AppCZY  "
                + " left outer join tCZYBM h on h.czybh=a.ConfirmCZY "
                + " left outer join tXTDM i on a.IsCupboard=i.CBM and i.ID='YESNO' "
                + " left outer join tXTDM j on a.IscalPerf=j.CBM and j.ID='YESNO' "
                + " left outer join tEmployee ep on ep.number=c.designman " 
                + " left join tDepartment2 ed on ed.code =ep.Department2 "
                + " left join tCustomer k on a.RefCustCode=k.Code "
                + " left join tWorker l on k.ProjectMan=l.EmpCode and isnull(l.EmpCode, '') <> '' "
                + " left join tEmployee m on a.FaultMan=m.Number "
                + " left join tWorker n on a.FaultMan=n.Code "
                + " left join tEmployee o on k.ProjectMan=o.Number "
                + " left join tXtdm p on p.id = 'ITEMCHGCONFSTAT' and p.Cbm = a.ConfirmStatus "
				+ " where 1=1 and " + SqlUtil.getCustRight(uc, "c", 0);
	   String[] arr=uc.getItemRight().trim().split(",");
	   String itemRight="";
	   for(String str:arr) itemRight+="'"+str+"',";
	   sql+="  and a.ItemType1 in("+itemRight.substring(0,itemRight.length()-1)+") ";
    	if (StringUtils.isNotBlank(itemChg.getNo())) {
			sql += " and a.No=? ";
			list.add(itemChg.getNo());
		}
    	if(itemChg.getIsService()!=null){
    		sql+=" and a.isService= ? ";
    		list.add(itemChg.getIsService());
    	}
    	if (StringUtils.isNotBlank(itemChg.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(itemChg.getItemType1());
		}
    	if (StringUtils.isNotBlank(itemChg.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(itemChg.getCustCode());
		}
    	if (StringUtils.isNotBlank(itemChg.getStatus())) {
			sql += " and a.Status in (" + itemChg.getStatus() + ")";
		}
    	if (itemChg.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(itemChg.getDateFrom());
		}
		if (itemChg.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(DateUtil.addInteger(itemChg.getDateTo(), Calendar.DATE, 1));
			
		}
		if (itemChg.getConfirmDateFrom() != null){
			sql += " and a.ConfirmDate>= ? ";
			list.add(itemChg.getConfirmDateFrom());
		}
		if (itemChg.getConfirmDateTo() != null){
			sql += " and a.ConfirmDate<= ? ";
			list.add(DateUtil.addInteger(itemChg.getConfirmDateTo(), Calendar.DATE, 1));
		}
		if(StringUtils.isNotBlank(itemChg.getCustType())){
			sql += " and c.custtype in (" + itemChg.getCustType() + ")";
		}
	
    	if (StringUtils.isNotBlank(itemChg.getRemarks())) {
			sql += " and a.Remarks like ? ";
			list.add("%"+itemChg.getRemarks().trim()+"%");
		}
		if (StringUtils.isBlank(itemChg.getExpired()) || "F".equals(itemChg.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemChg.getAppCzy())) {
			sql += " and a.AppCZY=? ";
			list.add(itemChg.getAppCzy());
		}
    	if (StringUtils.isNotBlank(itemChg.getAddress())) {
			sql += " and  c.Address like ? ";
			list.add("%"+itemChg.getAddress()+"%");
		}
    	if(StringUtils.isNotBlank(itemChg.getDepartment1())){
    		sql += " and  ep.department1=? ";
			list.add(itemChg.getDepartment1());
    	}
    	if(StringUtils.isNotBlank(itemChg.getItemDescr())){
   			 sql+="  and exists (SELECT  1 FROM  dbo.tItemChgDetail a1 inner JOIN dbo.tItem b1 ON a1.ItemCode=b1.Code "
   		               +"   WHERE b1.Descr like ? and a1.no=a.no) ";
   			 list.add("%"+itemChg.getItemDescr()+"%");

    	}
    	if (StringUtils.isNotBlank(itemChg.getEndCode())) {
			sql += " and c.EndCode in (" + itemChg.getEndCode() + ")";
		}
    	if (StringUtils.isNotBlank(itemChg.getCheckStatus())) {
			sql += " and c.CheckStatus in (" + itemChg.getCheckStatus() + ")";
		}
    	if(StringUtils.isNotBlank(itemChg.getConfirmStatus())){
    		sql += " and a.ConfirmStatus in ('"+itemChg.getConfirmStatus().replaceAll(",", "','")+"')";
    	}
    	
    	sql+=")a ";
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageByCustCode(
			Page<Map<String, Object>> page, ItemChg itemChg) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.No,a.ItemType1,b.Descr ItemType1Descr,a.CustCode,c.Descr CustomerDescr,c.Address,a.Status,"
                     + "d.NOTE StatusDescr,a.Date,a.BefAmount,case when a.BefAmount>0 then a.DiscAmount else a.DiscAmount*-1 end DiscAmount,a.Amount,a.Remarks,"
                     + "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,case when a.BefAmount>0 then a.DiscCost else a.DiscCost*-1 end DiscCost "	//产品优惠和优惠金额保持 modify by zb on 20200409
                     + "from tItemChg a left outer join tItemType1 b on ltrim(rtrim(a.ItemType1))=ltrim(rtrim(b.Code)) "
                     + "left outer join tCustomer c on a.CustCode=c.Code "
                     + "left outer join tXTDM d on a.Status=d.CBM and d.ID='ITEMCHGSTATUS' "
                     + "where a.CustCode =? ";
                     
		if (StringUtils.isNotBlank(itemChg.getCustCode())){
   			list.add(itemChg.getCustCode());
   		}else{
   			return null;
   		}
		if (StringUtils.isNotBlank(itemChg.getItemType1())){
   			sql += "and a.ItemType1=? ";
   			list.add(itemChg.getItemType1());
   		}
		if (itemChg.getIsService()!=null){
   			sql += "and a.IsService=? ";
   			list.add(itemChg.getIsService());
   		}
		if ("1".equals(itemChg.getIsCupboard()) && StringUtils.isNotBlank(itemChg.getIsIntPerfDetail())){
   			sql += "and a.IsCupboard='1' and a.status='2' and a.confirmdate<? ";
   			list.add(DateUtil.addInteger(itemChg.getIntPerfEndDate(),Calendar.DATE, 1));
   		}
		if ("0".equals(itemChg.getIsCupboard()) && StringUtils.isNotBlank(itemChg.getIsIntPerfDetail())){
   			sql += "and a.IsCupboard='0' and a.status='2' and a.confirmdate<? ";
   			list.add(DateUtil.addInteger(itemChg.getIntPerfEndDate(),Calendar.DATE, 1));
   		}
        if (StringUtils.isNotBlank(page.getPageOrderBy())){
   			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
   		}else{
   			sql += ") a order by a.date desc";
   		}
   		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, ItemChg itemChg, UserContext uc) {
List<Object> list = new ArrayList<Object>();
		
		String sql = "SELECT  a.IsService,e.NOTE IsServiceDescr,a.No,a.ItemType1,b.Descr ItemType1Descr,a.CustCode,c.Descr CustomerDescr,c.Address,c.Area,a.Status, "
               +" d.NOTE StatusDescr,a.Date,a.BefAmount,a.DiscAmount,a.Amount, "
               +" a.LastUpdate,a.LastUpdatedBy,a.confirmdate,a.Expired,a.ActionLog,sql.Descr SqlCodeDescr, "
               +"  c.ContainMain,c.ContainSoft,c.ContainInt,c.ContainMainServ, "
               +"  t.ReqPK,isnull(t3.Qty,0) ReqQty,t.FixAreaPK,t2.Descr FixAreaDescr,t.IntProdPK,t.ItemCode,t1.Descr ItemCodeDescr,isnull(t.Qty,0) Qty,t.Cost,t.UnitPrice, "
	           +" t.BefLineAmount,t.Markup,(t.Qty*t.UnitPrice*t.Markup/100) TmpLineAmount,t.LineAmount,t.Remarks,isnull(t.ProcessCost,0) ProcessCost,t.DispSeq,t.IsCommi,t4.NOTE IsCommiDescr,u.Descr UnitDescr "
	           +"  from tItemChg a " 
	           +"  left outer join tItemType1 b on a.ItemType1=b.Code " 
           	   +" left outer join tCustomer c on a.CustCode=c.Code " 
               +"  left outer join tXTDM d on a.Status=d.CBM and d.ID='ITEMCHGSTATUS' "
               +"  left outer join tXTDM e on a.IsService=e.IBM and e.ID='YESNO'"
               +"  left outer join tItemChgDetail t on a.No=t.No "
               +"  left outer join tItem t1 on t1.Code = t.ItemCode "
              +"   left outer join tBrand sql on sql.code=t1.sqlcode "
               +"  left outer join tFixArea t2 on t2.PK = t.FixAreaPK "
              +"left outer join tItemReq t3 on t3.PK = t.ReqPK "
               +"left outer join tUOM u on u.Code = t1.UOM "
               +"  left outer join tXTDM t4 on t.IsCommi=t4.IBM and t4.ID='YESNO' "
				+ "where 1=1 and " + SqlUtil.getCustRight(uc, "a", 0);
		 String[] arr=uc.getItemRight().trim().split(",");
		 String itemRight="";
		 for(String str:arr) itemRight+="'"+str+"',";
		 sql+="  and a.ItemType1 in("+itemRight.substring(0,itemRight.length()-1)+") ";
    	if (StringUtils.isNotBlank(itemChg.getNo())) {
			sql += " and a.No=? ";
			list.add(itemChg.getNo());
		}
    	if (StringUtils.isNotBlank(itemChg.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(itemChg.getItemType1().trim());
		}
    	if (StringUtils.isNotBlank(itemChg.getItemType2())) {
			sql += " and t1.ItemType2=? ";
			list.add(itemChg.getItemType2().trim());
		}
    	if (StringUtils.isNotBlank(itemChg.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(itemChg.getCustCode());
		}
    	if (StringUtils.isNotBlank(itemChg.getStatus())) {
			sql += " and a.Status in ("+itemChg.getStatus()+") ";
		}
    	if (itemChg.getDateFrom() != null){
			sql += " and a.confirmdate>= ? ";
			list.add(itemChg.getDateFrom());
		}
		if (itemChg.getDateTo() != null){
			sql += " and a.confirmdate<= ? ";
			list.add(DateUtil.addInteger(itemChg.getDateTo(), Calendar.DATE, 1));
		}
  
    	
		if (StringUtils.isBlank(itemChg.getExpired()) || "F".equals(itemChg.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemChg.getItemCode())) {
			sql += " and t.ItemCode =? ";
			list.add(itemChg.getItemCode());
		}
    	if (StringUtils.isNotBlank(itemChg.getAddress())) {
			sql += " and  c.Address like ? ";
			list.add("%"+itemChg.getAddress()+"%");
		}
    
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate desc,t.DispSeq";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public void doComfirm(ItemChg itemChg,String status) {
		Assert.notNull(itemChg);

		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pClzj(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1,"C");
			call.setInt(2, itemChg.getIsService());
			call.setString(3, itemChg.getNo());
			call.setString(4, itemChg.getItemType1().trim());
			call.setString(5, itemChg.getCustCode());
			call.setString(6, itemChg.getStatus().trim());
			call.setString(7, status);
			call.setTimestamp(8,new Timestamp(new Date().getTime()));
			call.setDouble(9,itemChg.getBefAmount());
			call.setDouble(10,itemChg.getDiscAmount());
			call.setDouble(11,itemChg.getAmount());
			call.setString(12,itemChg.getRemarks());
			call.setString(13,itemChg.getAppCzy());
			call.setString(14,itemChg.getIsCupboard());
			call.setDouble(15,itemChg.getDiscCost());
			call.setString(16,itemChg.getAppCzy());
			call.setString(17,itemChg.getAppCzy());
			call.execute();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
	}

	@SuppressWarnings("deprecation")
	public Result doItemChgForProc(ItemChg itemChg,String status) {
		Assert.notNull(itemChg);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pClzj_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			String FromStatus="";
			call.setString(1,itemChg.getM_umState());
			call.setInt(2, itemChg.getIsService());
			if("A".equals(itemChg.getM_umState())){
				call.setString(3, null);
				FromStatus="1";
			}else{
				call.setString(3, itemChg.getNo());
				FromStatus=itemChg.getStatus();
			}
			call.setString(4, itemChg.getItemType1().trim());
			call.setString(5, itemChg.getCustCode());
			call.setString(6,FromStatus);
			if(StringUtils.isNotBlank(status)){
				call.setString(7, status);
			}else{
				call.setString(7,itemChg.getStatus());
			}
			
			call.setTimestamp(8,new Timestamp(new Date().getTime()));
			call.setDouble(9,itemChg.getBefAmount()==null?0.0:itemChg.getBefAmount());
			call.setDouble(10,itemChg.getDiscAmount()==null?0.0:itemChg.getDiscAmount());
			call.setDouble(11,itemChg.getAmount()==null?0.0:itemChg.getAmount());
			call.setString(12,itemChg.getRemarks());
			call.setString(13,itemChg.getAppCzy());
			call.setString(14,itemChg.getIsCupboard());
			if(StringUtils.isBlank(itemChg.getIsCupboard())) call.setString(14,"0");
			call.setDouble(15,itemChg.getDiscCost()==null?0.0:itemChg.getDiscCost());
			call.setString(16,itemChg.getAppCzy());
			call.setString(17,itemChg.getAppCzy());
			call.setString(18,itemChg.getIsOutSet());
			call.setDouble(19,itemChg.getManageFee()==null?0.0:itemChg.getManageFee());
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.NVARCHAR);
			call.setString(22, itemChg.getItemChgDetailXml());
	        call.setTimestamp(23, itemChg.getPlanArriveDate()== null ? null: new Timestamp(itemChg.getPlanArriveDate().getTime()));
			call.setString(24,itemChg.getRefCustCode());
			call.setDouble(25,itemChg.getTax()==null?0.0:itemChg.getTax());
			call.setString(26, itemChg.getItemBatchNo());
			call.setString(27, itemChg.getIsAddAllInfo());	//增加常规变更（必选），业务员 add by zb on 20200420
			call.setString(28, itemChg.getChgStakeholderList());
			call.setString(29, itemChg.getTempNo());
			call.setString(30, itemChg.getFaultType());
			call.setString(31, itemChg.getFaultMan());
			call.setDouble(32,itemChg.getFaultAmount()==null?0.0:itemChg.getFaultAmount());
			call.setString(33,itemChg.getConfirmStatus());
			call.setString(34,itemChg.getDiscTokenNo());
			call.setDouble(35,itemChg.getManageFeePer());
			call.setString(36, itemChg.getExceptionRemarks());
			call.execute();
			result.setCode(String.valueOf(call.getInt(20)));
			result.setInfo(call.getString(21));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	public boolean isAllowChg(Customer customer) {
		if(!"RZ".equals(customer.getItemType1())){
				return true;
		}
		String sql="select datediff(day,SignDate,getdate()) Days from tCustomer where Code=?";
		if(this.findBySql(sql, new Object[]{customer.getCode()}).get(0).get("Days")==null)
		return true;
		int days=(Integer) this.findBySql(sql, new Object[]{customer.getCode()}).get(0).get("Days");
		sql="if exists(select 1 from tItemReq where CustCode=? and ItemType1=?)select 1 HasRequire else select 0 HasRequire";
		int HasRequire=(Integer) this.findBySql(sql, new Object[]{customer.getCode(),customer.getItemType1()}).get(0).get("HasRequire");
		sql="select cast(QZ as money) TwoSaleDay from tXTCS where ID='TwoSaleDay'";
		int TwoSaleDay=(int) Math.round((Double) this.findBySql(sql, null).get(0).get("TwoSaleDay")) ;
		if("1".equals(customer.getSaleType().trim())&&days>TwoSaleDay&&HasRequire==0) return false;
		return true;
	}

	public Page<Map<String, Object>> findReferencePageBySql(
			Page<Map<String, Object>> page, ItemChg itemChg) {
		Assert.notNull(itemChg);

		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pClzj_ckyj(?)}");
			call.setString(1,itemChg.getNo());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			Collections.sort(list, new ListCompareUtil("itemtype2descr","desc"));
			if(page.isAutoCount()){
				page.setTotalCount(list.size());
			}	
			page.setResult(list); 			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	}
	
	public List<Map<String, Object>>  getChangeParameterItemType2(String custCode,String itemtype1,String isService){
		String sql=" select rtrim(code) Code,descr from tItemType2 where Code in(" +
				"Select i.ItemType2 from tItemReq t1 " +
				"left outer join tItem i on t1.ItemCode=i.Code " +
				" where t1.qty <> 0 AND  t1.expired='F' " +
				" and t1.CustCode= ?  and t1.ItemType1= ?  " +
				" and t1.IsService= ? )";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{custCode,itemtype1,isService});
		
		if(list!=null && list.size()>0){
			Map<String, Object> map= list.get(0);
			return   list;
		}
		return null;
	}
	
	public boolean  getItemChgStatus(String no){
		String sql=" select rtrim(status) status from titemChg where no = ? ";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{no});
		
		if(list!=null && list.size()>0){
			if("1".equals(list.get(0).get("status"))){
				return  true ;
			}else{
				return false;
			}
		}
		return false;
	}

	public int getCountByCustCode(String custCode) {
		String sql = "select count(1) ret from tItemchg where status ='1' and custcode=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return Integer.parseInt(String.valueOf(list.get(0).get("ret")));
		}
		return 0;
	}

	public boolean hasOpenRecord(String custCode) {
		String sql = "select 1 ret where exists(select 1 from tItemChg where CustCode=? and Status='1')"
				+" or exists(select 1 from tBaseItemChg where CustCode=? and Status='1')"
				+" or exists(select 1 from tConFeeChg where CustCode=? and Status='OPEN')";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,custCode,custCode});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	public String getArfCustCodeList() {
		String sql = "select QZ from tXTCS where ID='AftCustCode'";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{});
		if (list!=null && list.size()>0){
			return list.get(0).get("QZ").toString();
		}
		return "";
	}
	
	public boolean existsItemCmp(String ItemCode,String custCode) {
		String sql = " select 1 from tItem a " +
				" left join tSupplier b on a.supplCode = b.Code " +
				" where a.Code = ? and (b.isGroup = '1' or exists (select 1 from tCustomer in_a " +
					"			left join tBuilder in_b on in_b.code = in_a.BuilderCode " +
					" 			left Join tRegion in_c on in_c.code= in_b.regionCode " +
					"			where exists ( select 1 from tSupplCmp in_d where in_d.SupplCode = b.Code and in_d.CmpCode = in_c.CmpCode ) " +
					" and in_a.Code = ?  " +
					"  ))";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{ItemCode,custCode});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	public Page<Map<String, Object>> findPlzjyjPageBySql(
			Page<Map<String, Object>> page, ItemChg itemChg) {
		List<Object> list = new ArrayList<Object>();
		String sql = 
			" select * from (select *,case when a.planFee = '0' then 0 else -a.Amount/a.planFee end Feeper  from (" +
			" select b.DocumentNo ,f.Desc1 CustTypeDescr ,a.isService,a.isCupboard,a.ItemType1,b.code,b.address" +
			" ,c.Descr itemDescr,d.note isServiceDescr,e.NOTE isCupboardDescr,-a.amount amount," +
			" case when a.ItemType1 ='ZC' and a.IsService='1' then b.MainServFee " +
			" when a.ItemType1 ='ZC' and IsService='0' then b.MainFee" +
			" when a.ItemType1 = 'RZ' then SoftFee when a.ItemType1 = 'JC' and IsCupboard ='1' then b.CupboardFee " +
			" when a.ItemType1 ='JC' and IsCupboard='0' then b.IntegrateFee else 0 end planFee" +
			" from ( " +

//			" 	select a.CustCode,-sum(c.LineAmount) amount, a.ItemType1,a.IsService,a.IsCupboard " +
//			"	from (" +
//			"		select max(a.lastUpdate) lastUpdate ,a.custCode " +
//			"		from tItemChg a " +
//			"		where a.status = '2' and a.IscalPerf ='0' " ;
//			if(itemChg.getConfirmDateFrom() != null){
//				sql+="and a.ConfirmDate >= ?";
//				list.add(itemChg.getConfirmDateFrom());
//			}
//			if(itemChg.getConfirmDateTo() != null){
//				sql+=" and a.confirmDate < dateAdd(d,1,?) ";
//				list.add(itemChg.getConfirmDateTo());
//			}
//			sql+= " group by a.CustCode) m " +
//				  "	left join tItemChg a on a.custCode = m.CustCode " +
//				  "	left join tItemChgDetail c on c.no = a.No  "+
//				  "	where a.Status='2' and a.PerfPK is null and a.IscalPerf <>'1'";
//			if(StringUtils.isNotBlank(itemChg.getItemType1())){
//				sql+=" and a.itemType1 = ? ";
//				list.add(itemChg.getItemType1());
//			}
//			if(StringUtils.isNotBlank(itemChg.getIsCupboard())){
//				sql+= " and a.isCupboard = ? ";
//				list.add(itemChg.getIsCupboard());
//			}
//			if(itemChg.getIsService()!=null){
//				sql+=" and a.IsService = ?";
//				list.add(itemChg.getIsService());
//			}
//			sql =sql+ "	group by a.CustCode,a.ItemType1,a.IsService,a.IsCupboard" +
            // 原写法会查询会超出审核日期范围日期,因此改写  byzjf20200826 
		    " select max(a.lastUpdate) lastUpdate, a.CustCode,-isnull(c.amount,0) amount, "+
			" a.ItemType1, a.IsService,a.IsCupboard  "+
			" from  tItemChg a "+
			" left join ( "+
			"	select sum(in_a.amount) amount,in_a.CustCode,in_a.ItemType1,in_a.IsService,in_a.IsCupboard  "+
			"	from tItemChg in_a "+
//			"	left join tItemChgDetail in_b on in_a.no=in_b.No "+
			"	where in_a.Status = '2' "+
			"	and in_a.PerfPK is null and in_a.IscalPerf <> '1' ";
			if(itemChg.getConfirmDateTo() != null){
				sql+=" and in_a.confirmDate < dateAdd(d,1,?) ";
				list.add(itemChg.getConfirmDateTo());
			}
			sql += " group by in_a.CustCode,in_a.ItemType1,in_a.IsService,in_a.IsCupboard "+
			" )c on c.CustCode=a.CustCode and c.ItemType1=a.ItemType1 and c.IsService=a.IsService and c.IsCupboard=a.IsCupboard "+
			" where a.Status = '2' and  a.PerfPK is null and a.IscalPerf <> '1' ";
			if(itemChg.getConfirmDateFrom() != null){
				sql+="and a.ConfirmDate >= ?";
				list.add(itemChg.getConfirmDateFrom());
			}
			if(itemChg.getConfirmDateTo() != null){
				sql+=" and a.confirmDate < dateAdd(d,1,?) ";
				list.add(itemChg.getConfirmDateTo());
			}
			if(StringUtils.isNotBlank(itemChg.getItemType1())){
				sql+=" and a.itemType1 = ? ";
				list.add(itemChg.getItemType1());
			}
			if(StringUtils.isNotBlank(itemChg.getIsCupboard())){
				sql+= " and a.isCupboard = ? ";
				list.add(itemChg.getIsCupboard());
			}
			if(itemChg.getIsService()!=null){
				sql+=" and a.IsService = ?";
				list.add(itemChg.getIsService());
			}
			sql+="	group by a.CustCode,a.ItemType1,a.IsService,a.IsCupboard,c.amount" +
			" )a " +
			" left join tCustomer b on b.code = a.CustCode " +
			" left join tItemType1 c on c.Code = a.ItemType1 " +
			" left join tXTDM d on d.CBM = a.IsService and d.ID = 'YESNO' " +
			" left join tXTDM e on e.CBM = a.IsCupboard and e.ID = 'YESNO' " +
			" left join tCustType f on f.Code = b.CustType " +
			" where 1=1 and f.IsAddAllInfo <> '0' ";
			if(itemChg.getChgPer() != null ){
				sql =sql+ " and (" +
						" (a.ItemType1='JC' and IsCupboard='1' and case when b.CupboardFee=0 then 0 else a.amount/b.CupboardFee end>= ? ) " +
						" or (a.ItemType1='JC' and IsCupboard='0' and case when b.IntegrateFee=0 then 0 else a.amount/b.IntegrateFee end>= ? ) " +
						" or (a.ItemType1 = 'ZC' and a.IsService = '0' " +
						"		and (case when b.mainFee =0 then 0 else a.amount/b.MainFee end >=?))" +
						" or (a.ItemType1 = 'RZ' and " +
						"		(case when b.SoftFee = 0 then 0 else a.amount / b.SoftFee end >= ? ))" +
						" or (a.ItemType1 = 'ZC' and a.IsService = '1' " +
						"		and (case when MainServFee =0 then 0 else a.amount / b.MainServFee end >= ?))" +
						") ";
				list.add(itemChg.getChgPer());
				list.add(itemChg.getChgPer());
				list.add(itemChg.getChgPer());
				list.add(itemChg.getChgPer());
				list.add(itemChg.getChgPer());
			}
			sql += ")a where 1=1 ";
			 
			if(itemChg.getAmount() != null ){ 
				sql+=" and -a.Amount >= ? ";
				list.add(itemChg.getAmount());
			}
        if (StringUtils.isNotBlank(page.getPageOrderBy())){
   			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
   		}else{
   			sql += ") a order by a.address desc";
   		}
   		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Result doPlzjyj(ItemChg itemChg) {
		Assert.notNull(itemChg);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pClzj_plzjyj(?,?,?,?,?,?)}");
			call.setTimestamp(1,new Timestamp(itemChg.getConfirmDateFrom().getTime()));
			call.setTimestamp(2, new Timestamp(DateUtil.endOfTheDay(itemChg.getConfirmDateTo()).getTime()));
			call.setString(3, itemChg.getLastUpdatedBy());
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.setString(6, itemChg.getItemChgDetailXml());
			call.execute();
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	/**
	 * 查看软装材料状态
	 * @author	created by zb
	 * @date	2020-3-18--下午6:05:37
	 * @param page
	 * @param itemChg
	 * @return
	 */
	public Page<Map<String, Object>> findItemStatusPageBySql(
			Page<Map<String, Object>> page, ItemChg itemChg) {
		String sql = "select a.Code ItemCode,a.ItemType2,b.Descr ItemType2Descr, "
					+"a.ItemType3,c.Descr ItemType3Descr,a.Descr ItemDescr,a.SqlCode, "
					+"d.Descr SqlDescr,e.PUQtyCal,f.IASendQty "
					+"from tItem a "
					+"left join tItemType2 b on b.Code=a.ItemType2 "
					+"left join tItemType3 c on c.Code=a.ItemType3 "
					+"left join tBrand d on d.Code=a.SqlCode "
					+"left join ( "
					+"	select in_a.CustCode,in_b.ITCode,sum(isnull(in_b.QtyCal,0)) PUQtyCal, "
					+"  sum(case when in_a.AppItemDate is not null then case when in_a.Type='S' then isnull(in_b.QtyCal,0) else -isnull(in_b.QtyCal,0) end else 0 end )AppQty "
					+"	from tPurchase in_a "
					+"	left join tPurchaseDetail in_b on in_b.PUNo=in_a.No "
					+"	where in_a.Status in ('OPEN','CONFIRMED') "
					+"	group by in_a.CustCode,in_b.ITCode "
					+") e on e.ITCode=a.Code and e.CustCode=? "
					+"left join ( "
					+"  select in_a.CustCode,in_b.ItemCode,sum( "
					+"    case when in_a.Type='S'  "
					+"    then isnull(in_b.SendQty,0)  "
					+"    else -isnull(in_b.SendQty,0) end "
					+"  ) IASendQty "
					+"  from tItemApp in_a "
					+"  left join tItemAppDetail in_b on in_b.No=in_a.No "
					+"  where in_a.status<>'CANCEL' "
					+"  group by in_a.CustCode,in_b.ItemCode "
					+") f on f.ItemCode=a.Code and f.CustCode=? "
					+"where 1=1 "
					+"and a.Code in (select item from dbo.fStrToTable(?, ',')) ";
		return this.findPageBySql(page, sql, new Object[]{itemChg.getCustCode(),itemChg.getCustCode(),itemChg.getItemCode()});
	}
	/**
	 * 获取增减干系人列表
	 * @author	created by zb
	 * @param page 
	 * @date	2020-4-20--下午4:43:09
	 * @param itemChg
	 * @return
	 */
	public Page<Map<String, Object>> findItemChgStakeholderPageBySql(
			Page<Map<String,Object>> page, ItemChg itemChg) {
		String sql = "select a.PK, a.ChgNo, a.Role, c.Descr RoleDescr, a.EmpCode,"
				+"	b.NameChi EmpName,a.LastUpdate,a.LastUpdatedBy, "
				+"a.ActionLog,a.Expired  "
				+"from tItemChgStakeholder a "
				+"left join tEmployee b on b.Number=a.EmpCode "
				+"left join tRoll c on c.Code=a.Role "
				+"where a.ChgNo=?";
		return this.findPageBySql(page, sql, new Object[]{itemChg.getNo()});
	}
	
	public Result doItemChgTempProc(ItemChg itemChg) {
		Assert.notNull(itemChg);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pItemChgFromPrePlanTemp(?,?,?,?,?)}");
			call.setString(1, itemChg.getCustCode());
			call.setString(2, itemChg.getTempNo());
			call.setString(3, itemChg.getLastUpdatedBy());
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			List<JSONObject> jsonLists=new ArrayList<JSONObject>();
			for(Map<String, Object> map:list){
				for (String k : map.keySet()) {
					if (map.get(k)==null){
						map.put(k, "");
					}
	            }
				String date= map.get("lastupdate").toString();
				JSONObject json = JSONObject.fromObject(map); 
				json.put("lastupdate", date);
				jsonLists.add(json);
			}
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
			result.setJson(jsonLists.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Result doRegenFromPrePlanTemp(ItemChg itemChg) {
		Assert.notNull(itemChg);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pRegenItemChgFromPrePlanTemp(?,?,?,?,?,?)}");
			call.setString(1, itemChg.getCustCode());
			call.setString(2, itemChg.getTempNo());
			call.setString(3, itemChg.getLastUpdatedBy());
			call.setString(4,itemChg.getItemChgDetailXml());
			System.out.println(itemChg.getItemChgDetailXml());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			List<JSONObject> jsonLists=new ArrayList<JSONObject>();
			for(Map<String, Object> map:list){
				for (String k : map.keySet()) {
					if (map.get(k)==null){
						map.put(k, "");
					}
	            }
				JSONObject json = JSONObject.fromObject(map); 
				if(map.get("lastupdate")!=null){
					String date= map.get("lastupdate").toString();
					json.put("lastupdate", date);
				}
				jsonLists.add(json);
			}
			result.setCode(String.valueOf(call.getInt(5)));
			result.setInfo(call.getString(6));
			result.setJson(jsonLists.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public boolean getExistsTemp(String custCode,String no) {
		String sql = " select 1 from tItemChg where custCode = ? and TempNo is not null and tempNo <> '' and status<>'3' ";
		if(StringUtils.isNotBlank(no)){
			sql+=" and no <> ?";
		}else{
			no = "";
			sql+=" and ?=''";
		}
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,no});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	public Double getAmountByItemType(String itemType, String custCode) {
		
		String sql = "select sum(isnull(CheckAmount,0)) CheckAmount from ( select sum(isNull(Amount, 0)) CheckAmount from tItemChg " +
				" where ItemType1 = ? and CustCode = ? and status not in ('1','3') and expired = 'F'" +
				" union all " +
				" select sum(IsNull(LineAmount,0)) CheckAmount from tItemPlan " +
				" where ItemType1 = ? and CustCode = ? and Expired = 'F') a ";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itemType, custCode, itemType, custCode});
		if (list!=null && list.size()>0){
			return Double.parseDouble(list.get(0).get("CheckAmount").toString());
		}
		return 0.0;
	}

    public Page<Map<String, Object>> findSetDeductions(Page<Map<String, Object>> page,
            ItemChg itemChg) {

        ArrayList<Object> parameters = new ArrayList<Object>();

        String sql = "select * from (";
        
        sql += "    select b.PK, b.FixAreaPK, d.Descr FixAreaDescr, "
                + "     c.Code BaseItemCode, c.Descr BaseItemDescr, "
                + "     b.Qty, c.Uom, e.Descr UomDescr, "
                + "     isnull(b.UnitPrice, 0) + isnull(b.Material, 0) UnitPrice, "
                + "     b.LineAmount, b.Remark "
                + " from tCustomer a "
                + " left join tBaseItemReq b on a.Code = b.CustCode "
                + " left join tBaseItem c on b.BaseItemCode = c.Code "
                + " left join tFixArea d on b.FixAreaPK = d.PK "
                + " left join tUOM e on c.Uom = e.Code "
                + " where c.Category = '4' and b.Qty < 0.0 ";
        
        if (StringUtils.isNotBlank(itemChg.getCustCode())) {
            sql += "and a.Code = ? ";
            parameters.add(itemChg.getCustCode());
        }
        
        if (StringUtils.isNotBlank(itemChg.getItemType2())) {
            sql += "and c.ItemType2 in ('" + itemChg.getItemType2().replace(",", "', '") + "')";
        }
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.BaseItemCode ";
        }

        return findPageBySql(page, sql, parameters.toArray());
    }
    
    public Result doGenChgMainItemSet(ItemChg itemChg) {
		Assert.notNull(itemChg);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGenChgMainItemSet(?,?,?,?,?,?)}");
			call.setString(1, itemChg.getCustCode());
			call.setString(2, itemChg.getItemSetNo());
			call.setInt(3, itemChg.getPrePlanAreaPK());
			call.setString(4, itemChg.getLastUpdatedBy());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			List<JSONObject> jsonLists=new ArrayList<JSONObject>();
			for(Map<String, Object> map:list){
				for (String k : map.keySet()) {
					if (map.get(k)==null){
						map.put(k, "");
					}
	            }
				JSONObject json = JSONObject.fromObject(map); 
				jsonLists.add(json);
			}
			result.setCode(String.valueOf(call.getInt(5)));
			result.setInfo(call.getString(6));
			result.setJson(jsonLists.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

}

