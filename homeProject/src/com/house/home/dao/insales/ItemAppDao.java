package com.house.home.dao.insales;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.house.framework.web.login.UserContextHolder;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.ItemApp;

@SuppressWarnings("serial")
@Repository
public class ItemAppDao extends BaseDao {

	/**
	 * ItemApp分页信息
	 * 
	 * @param page
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemApp itemApp) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from (select "
				+ " a.No,a.Type,s3.Note TypeDescr,a.ItemType1,i.Descr ItemType1Descr,a.ItemType2,i2.Descr ItemType2Descr,"
				+ "	a.CustCode,b.Descr CustDescr,b.CustType,b.Address,b.DocumentNo,a.Status,s1.Note StatusDescr,a.OldNo,"
			    + "	a.AppCZY,c1.zwxm AppCZYDescr,a.Date,a.ConfirmCZY,c2.zwxm ConfirmCZYDescr,a.ConfirmDate,"
			    + "	a.SendCZY,c3.zwxm SendCZYDescr,a.SendDate,a.SendType,s2.Note SendTypeDescr,a.SupplCode,sp.Descr SupplCodeDescr,"
			    + "	a.PUNo,a.WHCode,w.Desc1 WHCodeDescr,a.Remarks,a.ProjectMan,a.Phone,a.LastUpdate,"
			    + "	a.LastUpdatedBy,a.Expired,a.ActionLog,a.DelivType,x.NOTE DelivTypeDescr,a.OtherCost,a.IsService,i2.MaterWorkType2,a.PreAppNo, "
			    + "	a.Amount,a.ProjectAmount,a.IsSetItem,s4.Note IsSetItemDescr,b.CheckStatus,wco.DocumentNo WHCheckOutDocumentNo, "
			    + " a.SplStatus,s5.NOTE SplStatusDescr,a.ArriveDate,a.SplRemark,a.OtherCostAdj,a.PrintTimes,a.PrintCZY,c4.ZWXM PrintCZYDescr,a.PrintDate, "
			    + " (select top 1 in_emp.NameChi from tCustStakeholder in_cs left outer join tEmployee in_emp on in_cs.EmpCode=in_emp.Number "
			    + "  where in_cs.CustCode=a.CustCode and in_cs.Role='50') SoftDesignDescr,"
			    + " (select top 1 in_emp.Phone from tCustStakeholder in_cs left outer join tEmployee in_emp on in_cs.EmpCode=in_emp.Number "
			    + "  where in_cs.CustCode=a.CustCode and in_cs.Role='50') SoftDesignPhone, e5.NameChi DesignDescr,"
			    + " d2.Desc1 PrjDept2Descr,wco.Status WHCheckOutStatus,s6.NOTE WHCheckOutStatusDescr,wco.No WHCheckOutNo" +
			    ",em.nameChi mainGjDescr,tr.descr regiondescr,b.RealAddress,pc.SplStatus puSplStatus,s7.NOTE puSplStatusDescr, " //增加实际地址显示 add by zb
			    + " e.NameChi LeaderName, e.Phone LeaderPhone "
				+ "	from tItemApp a "
			    + "	left outer join tItemType1 i on a.ItemType1=i.Code "
				+ " left outer join tItemType2 i2 on a.ItemType2=i2.Code "
				+ " left outer join tCustomer b on b.Code=a.CustCode "
			    + " left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='ITEMAPPSTATUS'"
				+ " left outer join tCzybm c1 on a.AppCZY=c1.czybh "
			    + " left outer join tCzybm c2 on a.ConfirmCZY=c2.czybh "
				+ " left outer join tCzybm c3 on a.SendCZY=c3.czybh "
				+ " left outer join tCzybm c4 on a.PrintCZY=c4.czybh "
			    + " left outer join tWareHouse w on a.WHCode=w.Code "
				+ " left outer join tXTDM  s2 on a.SendType=s2.CBM and s2.ID='ITEMAPPSENDTYPE'"
			    + " left outer join tXTDM  s3 on a.Type=s3.CBM and s3.ID='ITEMAPPTYPE' "
				+ " left outer join tXTDM  x on a.DelivType=x.CBM and x.ID='DELIVTYPE'"
			    + " left outer join tSupplier sp on a.SupplCode=sp.Code"
				+ " left outer join tXTDM s4 on a.IsSetItem=s4.CBM and s4.ID='YESNO'" 
				+ " left outer join tWHCheckOut wco on a.WHCheckOutNo=wco.No"
			    + " left outer join tXTDM s5 on a.SplStatus=s5.CBM and s5.ID='APPSPLSTATUS'" 
			    + " left outer join tEmployee e4 on b.ProjectMan=e4.Number"
				+ " left outer join tDepartment2 d2 on e4.Department2=d2.Code"
			    + " left outer join tXTDM s6 on wco.Status=s6.CBM and s6.ID='WHChkOutStatus'"
			    + " left outer join tCusttype ct on ct.Code=b.CustType"
				+ " left outer join tEmployee e5 on b.DesignMan = e5.Number"
				+ " left outer join tPurchase pc on a.puno = pc.no" +
				" left outer join tXTDM s7 on pc.splStatus = s7.CBM and s7.ID='PuSplStatus' " +
				" left join  ( select  max(a.PK)pk,a.CustCode from tCustStakeholder a" +
				" where role='34' group by a.custCode)mainGJ on mainGJ.custCode=b.Code" +
				" left join tCustStakeholder csh on csh.pk=mainGJ.pk" +
				" left join temployee eM on eM.number=csh.empCode " 
				+ " left join tBuilder tb on tb.code=b.BuilderCode " 
				+ " left join tRegion tr on tr.Code=tb.RegionCode "
				+ " left join tEmployee c on b.ProjectMan = c.Number "
				+ " left join tDepartment d on c.Department = d.Code "
				+ " left join tEmployee e on d.LeaderCode = e.Number "
			    + " where 1=1 ";
		
    	if (StringUtils.isNotBlank(itemApp.getNo())) {
			sql += " and a.No=? ";
			list.add(itemApp.getNo());
		}
    	if (StringUtils.isNotBlank(itemApp.getConfirmStatus())) {
			sql += " and exists(select 1 from tItemAppSend where confirmStatus=? and iano=a.no) ";
			list.add(itemApp.getConfirmStatus());
		}
    	if (itemApp.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(itemApp.getDateFrom());
		}
		if (itemApp.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(DateUtil.endOfTheDay(itemApp.getDateTo()));
		}
		if (StringUtils.isNotBlank(itemApp.getSupplCode())) {
			if (StringUtils.isNotBlank(itemApp.getCzybh())){
				sql += " and ((a.SupplCode=? and a.SendType='1') or exists(select 1 from tWareHouseOperater who where a.WHCode = who.WHCode and who.CZYBH=? and a.SendType='2')) ";
				list.add(itemApp.getSupplCode());
				list.add(itemApp.getCzybh());
			}else{
				sql += " and a.SupplCode=? ";
				list.add(itemApp.getSupplCode());
			}
		}
    	if (StringUtils.isNotBlank(itemApp.getType())) {
			sql += " and a.Type=? ";
			list.add(itemApp.getType());
		}
    	if (StringUtils.isNotBlank(itemApp.getDelivType())) {
			sql += " and a.DelivType=? ";
			list.add(itemApp.getDelivType());
		}
    	if (StringUtils.isNotBlank(itemApp.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(itemApp.getItemType1());
		}
    	if (StringUtils.isNotBlank(itemApp.getItemType2())) {
			sql += " and a.ItemType2=? ";
			list.add(itemApp.getItemType2());
		}
    	if (StringUtils.isNotBlank(itemApp.getAppCzy())) {
			sql += " and a.AppCZY=? ";
			list.add(itemApp.getAppCzy());
		}
    	if (StringUtils.isNotBlank(itemApp.getCustCode())) {
    		sql += " and ( a.CustCode=? or a.RefCustCode=? ) ";
			list.add(itemApp.getCustCode());
			list.add(itemApp.getCustCode());
    	}
    	if (StringUtils.isNotBlank(itemApp.getCustAddress())) {
    		sql += " and b.Address like ? ";
    		list.add("%"+itemApp.getCustAddress()+"%");
    	}
    	if (StringUtils.isNotBlank(itemApp.getSendType())) {
			sql += " and a.sendType=? ";
			list.add(itemApp.getSendType());
		}
    	if (StringUtils.isNotBlank(itemApp.getModule()) &&
        	    "SupplierItemApp".equalsIgnoreCase(itemApp.getModule())) {
        		sql += " and a.Status in ('CONFIRMED','SEND') ";
        }	
    	if (StringUtils.isNotBlank(itemApp.getStatus())) {
			sql += " and a.Status in " + "('"+itemApp.getStatus().replaceAll(",", "','")+"')";
		}
    	if (StringUtils.isNotBlank(itemApp.getSplStatus())) {
			sql += " and a.SplStatus in " + "('"+itemApp.getSplStatus().replaceAll(",", "','")+"')";
		}
    	if (itemApp.getConfirmDateFrom() != null){
			sql += " and a.ConfirmDate>= ? ";
			list.add(itemApp.getConfirmDateFrom());
		}
		if (itemApp.getConfirmDateTo() != null){
			sql += " and a.ConfirmDate<= ? ";
			list.add(DateUtil.endOfTheDay(itemApp.getConfirmDateTo()));
		}
		if (itemApp.getSendDateFrom() != null){
			sql += " and a.SendDate>= ? ";
			list.add(itemApp.getSendDateFrom());
		}
		if (itemApp.getSendDateTo() != null){
			sql += " and a.SendDate<= ? ";
			list.add(DateUtil.endOfTheDay(itemApp.getSendDateTo()));
		}
		if (StringUtils.isNotBlank(itemApp.getPreAppNo())) {
			sql += " and a.PreAppNo=? ";
    		list.add(itemApp.getPreAppNo());
		}
		if (StringUtils.isNotBlank(itemApp.getSendNo())) {
			sql += " and exists (select 1 from tItemAppSend where IANo=a.No and No=?) ";
    		list.add(itemApp.getSendNo());
		}
		if (StringUtils.isNotBlank(itemApp.getWhcode())) {
			sql += " and a.WHCode=? ";
			list.add(itemApp.getWhcode());
		}
		if (StringUtils.isNotBlank(itemApp.getCustDocumentNo())) {
			sql += " and b.DocumentNo=? ";
    		list.add(itemApp.getCustDocumentNo());
		}
		if (StringUtils.isNotBlank(itemApp.getIsSetItem())) {
			sql += " and a.IsSetItem=? ";
    		list.add(itemApp.getIsSetItem());
		}
		if (StringUtils.isNotBlank(itemApp.getRemarks())) {
    		sql += " and a.Remarks like ? ";
    		list.add("%"+itemApp.getRemarks()+"%");
    	}
		if("1".equals(itemApp.getWareHouseSendAuth())){
			sql += " and ( exists (select 1 from TSYS_CZYBM_AUTHORITY where CZYBH=? and AUTHORITY_ID=1119) or a.SendType<>'2' )";
			list.add(itemApp.getCzybh());
		}
		if(StringUtils.isNotBlank(itemApp.getpPrint())){
			sql+=" and a.SplStatus not in ('1','0') ";
		}
		if (StringUtils.isNotBlank(itemApp.getPuSplStatus())) {
			sql += " and pc.SplStatus=? ";
			list.add(itemApp.getPuSplStatus());
		}
		if (StringUtils.isNotBlank(itemApp.getDelivType())) {
			sql += " and a.DelivType=? ";
			list.add(itemApp.getDelivType());
		}
    	if (StringUtils.isNotBlank(itemApp.getIsMaterialSendJob())){
    		if ("1".equals(itemApp.getIsMaterialSendJob())) {
    			sql += " and a.NotifySendDate is not null ";
			}else {
				sql += " and a.NotifySendDate is null ";
			}
    	}	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * ItemApp_code分页信息
	 * 
	 * @param page
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySqlCode(Page<Map<String,Object>> page, ItemApp itemApp) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.No,a.Type,s3.Note TypeDescr,a.ItemType1,i.Descr ItemType1Descr,a.ItemType2,i2.Descr ItemType2Descr,a.CustCode,"
			    + "b.Descr CustDescr,b.Address,b.DocumentNo,a.Status,s1.Note StatusDescr,"
			    + "a.AppCZY,e1.zwxm AppCzyDescr,a.Date,a.ConfirmCZY,e2.zwxm ConfirmCzyDescr,a.ConfirmDate,"
			    + "a.SendCZY,e3.zwxm SendCzyDescr,a.SendDate,a.SendType,s2.Note SendTypeDescr,a.SupplCode,sp.Descr SupplCodeDescr,"
			    + "a.PUNo,a.WHCode,w.Desc1 whcodedescr,a.Remarks,a.ProjectMan,a.Phone,a.LastUpdate,a.LastUpdatedBy,a.Expired,"
			    + "a.ActionLog,a.DelivType,x.NOTE DelivTypeDescr,a.IsService,a.IsSetItem,b.CheckStatus,a.iscupboard "
			    + " from tItemApp a "
			    + "left outer join tItemType1 i on a.ItemType1=i.Code "
			    + "left outer join tItemType2 i2 on a.ItemType2=i2.Code "
			    + "left outer join tCustomer b on b.Code=a.CustCode "
			    + "left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='ITEMAPPSTATUS' "
			    + "left outer join tCzybm e1 on a.AppCZY=e1.czybh "
			    + "left outer join tCzybm e2 on a.ConfirmCZY=e2.czybh "
			    + "left outer join tCzybm e3 on a.SendCZY=e3.czybh "
			    + "left outer join tWareHouse w on a.WHCode=w.Code "
			    + "left outer join tXTDM  s2 on a.SendType=s2.CBM and s2.ID='ITEMAPPSENDTYPE' "
			    + "left outer join tXTDM  s3 on a.Type=s3.CBM and s3.ID='ITEMAPPTYPE' "
			    + "left outer join tXTDM  x on a.DelivType=x.CBM and x.ID='DELIVTYPE' "
			    + "left outer join tSupplier sp on a.SupplCode=sp.Code "
			    + "where a.Expired='F' ";
		if (StringUtils.isNotBlank(itemApp.getSupplCode())) {
			if (StringUtils.isNotBlank(itemApp.getCzybh())){
				sql += " and ((a.SupplCode=? and a.SendType='1') or exists(select 1 from tWareHouseOperater who where a.WHCode = who.WHCode and who.CZYBH=? and a.SendType='2')) ";
				list.add(itemApp.getSupplCode());
				list.add(itemApp.getCzybh());
			}else{
				sql += " and a.SupplCode=? ";
				list.add(itemApp.getSupplCode());
			}
		}
    	if (StringUtils.isNotBlank(itemApp.getType())) {
			sql += " and a.Type=? ";
			list.add(itemApp.getType());
		}
    	if (StringUtils.isNotBlank(itemApp.getWhcode())) {
			sql += " and a.whCode=? ";
			list.add(itemApp.getWhcode());
		}
    	if (StringUtils.isNotBlank(itemApp.getStatus())) {
			sql += " and a.Status=? ";
			list.add(itemApp.getStatus());
		}
    	if (StringUtils.isNotBlank(itemApp.getNo())) {
			sql += " and a.No like ? ";
			list.add("%"+itemApp.getNo()+"%");
		}
    	if (StringUtils.isNotBlank(itemApp.getItemCode())) {
			sql += " and exists(select ItemCode from tItemAppDetail iad2 where iad2.No=a.no and ItemCode=?) ";
			list.add(itemApp.getItemCode());
		}
    	if (StringUtils.isNotBlank(itemApp.getCustAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+itemApp.getCustAddress()+"%");
		}
    	if (StringUtils.isNotBlank(itemApp.getItemCodeDescr())) {
			sql += " and exists(select 1 from (select ItemCode from tItemAppDetail iad where iad.no=a.no) ic "
			    + " left outer join tItem it on it.Code=ic.ItemCode where it.descr like ? ) ";
			list.add("%"+itemApp.getItemCodeDescr()+"%");
		}
    	if(StringUtils.isNotBlank(itemApp.getItemType1())){
    		sql += " and a.itemType1 = ? ";
    		list.add(itemApp.getItemType1());
    	}else if (StringUtils.isNotBlank(itemApp.getItemRight())) { //没有材料类型时，按材料权限选择（仓库发货申请） add by zb on 0190428
			sql += " and a.itemType1 in ('"+itemApp.getItemRight().replace(",", "','")+"') ";
		}
    	if (StringUtils.isNotBlank(itemApp.getIsAllApp())) { //领料单号所有材料都已申请的，不再显示 add by zb on 20190805
			sql += "and exists ( "
				+  "  select 1 from tItemAppDetail in_a "
				+  "  left join tPreItemAppSendDetail in_b on in_b.RefPk=in_a.PK and in_b.Expired='F' "
				+  "  left join tPreItemAppSend in_c on in_c.No=in_b.No "
				+  "  where in_a.No=a.No and (in_b.PK is null or in_c.Status='4') and in_a.Expired='F' "
        		+  ") ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.SendDate desc,a.ConfirmDate desc,a.Date desc,a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	

	/**
	 * 操作领料管理（使用存储过程）
	 * @param itemApp
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doItemAppForProc(ItemApp itemApp) {
		Assert.notNull(itemApp);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLlgl_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemApp.getM_umState());
			call.setDouble(2, itemApp.getOtherCost());
			call.setString(3, itemApp.getStatus());
			call.setString(4, itemApp.getNo());
			call.setString(5, itemApp.getDelivType());
			call.setString(6, itemApp.getType());
			call.setString(7, itemApp.getItemType1());
			call.setString(8, itemApp.getItemType2());
			call.setString(9, itemApp.getCustCode());
			call.setString(10,itemApp.getAppCzy());
			call.setString(11, itemApp.getRemarks());
			call.setString(12, itemApp.getProjectMan());
			call.setString(13, itemApp.getPhone());
			call.setString(14, itemApp.getLastUpdatedBy());
			call.setString(15, "F");
			call.setString(16, itemApp.getPreAppNo());
			call.setInt(17, itemApp.getIsService());
			call.setString(18, itemApp.getIsSetItem());
			call.setString(19, itemApp.getSendType());
			call.setString(20, itemApp.getSupplCode());
			call.setString(21, itemApp.getWhcode());
			call.setString(22, itemApp.getSplStatus());
			call.setString(23, itemApp.getProductType());
			call.setTimestamp(24, itemApp.getArriveDate()==null?null:new Timestamp(itemApp.getArriveDate().getTime()));
			call.setString(25, itemApp.getSplRemark());
			call.setDouble(26, itemApp.getOtherCostAdj());
			call.setTimestamp(27, itemApp.getArriveSplDate()==null?null:new Timestamp(itemApp.getArriveSplDate().getTime()));
			call.setString(28, "");
			call.registerOutParameter(29, Types.INTEGER);
			call.registerOutParameter(30, Types.NVARCHAR);
			call.setString(31, itemApp.getItemAppDetailXml());	
			call.execute();
			result.setCode(String.valueOf(call.getInt(29)));
			result.setInfo(call.getString(30));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	/**
	 * 领料管理退回
	 * @param itemApp
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doItemAppBackForProc(ItemApp itemApp) {
		Assert.notNull(itemApp);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLlglth_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemApp.getM_umState());
			call.setDouble(2, itemApp.getOtherCost()==null?0:itemApp.getOtherCost());
			call.setString(3, itemApp.getNo());
			call.setString(4, itemApp.getStatus());
			call.setString(5, itemApp.getType());
			if ("1".equals(itemApp.getSendType())){
				call.setString(6, "2");//退货类型 1-仓库 2-供应商 (跟原来相反)
			}else{
				call.setString(6, "1");
			}
			call.setTimestamp(7, itemApp.getDate() == null ? null : new Timestamp(
					itemApp.getDate().getTime()));
			call.setString(8, itemApp.getItemType1());
			call.setString(9, itemApp.getItemType2());
			call.setString(10,itemApp.getCustCode());
			call.setString(11, itemApp.getAppCzy());
			call.setString(12, itemApp.getWhcode()==null?"":itemApp.getWhcode());
			call.setString(13, itemApp.getSupplCode());
			call.setString(14, itemApp.getRemarks());
			call.setString(15, itemApp.getLastUpdatedBy());
			call.setString(16, itemApp.getExpired());
			call.setString(17, itemApp.getOldNo());
			call.setInt(18, itemApp.getIsService());
			if (StringUtils.isNotBlank(itemApp.getIsSetItem())){
				call.setInt(19, Integer.parseInt(itemApp.getIsSetItem()));
			}else{
				call.setInt(19, 0);
			}
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.NVARCHAR);
			call.setString(22, itemApp.getItemAppDetailXml());
			call.setString(23, itemApp.getIsCupboard());	
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

	public Map<String,Object> getByNo(String id) {
		String sql = "select a.*,b.descr custCodeDescr,c.descr supplCodeDescr,w.Desc1 whcodeDescr from tItemApp a "
			+ "left join tCustomer b on a.custCode=b.code "
			+ "left join tWareHouse w on a.whcode=w.code "
			+ "left join tSupplier c on a.supplCode=c.code where a.no=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{id});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String,Object>> findDetailBySql(Page<Map<String,Object>> page, ItemApp itemApp) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select qz from tXTCS where id='Titles'";
		String str = "";
		List<Map<String,Object>> listMap = this.findBySql(sql);
		if (listMap!=null && listMap.size()>0){
			 str = (String) listMap.get(0).get("qz");
		}
		  
		sql = " select iad.ProjectCost*iad.Qty projectAllCost,iad.PK, ia.No, iad.ReqPK, iad.FixAreaPK, iad.IntProdPK, ia.ConfirmDate, iad.ItemCode, i.Descr ItemDescr, iad.Qty,"
			+ " ir.Qty ReqQty, iad.SendQty, isnull(iad.ShortQty, 0) ShortQty, case when ir.SendQty is not null then ir.SendQty else ("
			+ "   select sum(in_b.SendQty) from tItemApp in_a inner join tItemAppDetail in_b on in_a.No=in_b.No"
			+ "   where in_a.CustCode=ir.CustCode and in_b.ItemCode=iad.ItemCode and in_b.ReqPK<=0) end as SendedQty, "
			+ " u.Descr UOMDescr, iad.Remarks, iad.LastUpdate, iad.LastUpdatedBy, iad.Expired, iad.ActionLog, "
			+ " isnull(iad.Cost, 0) Cost, iad.ProjectCost, iad.AftQty, iad.AftCost, iad.PreAppDTPK, ir.Remark ReqRemarks,isnull(iad.DeclareQty, 0) DeclareQty,tfa.Descr FixAreaDescr,tip.Descr IntProdDescr, "
			+ " isnull(iad.Cost, 0)*isnull(iad.Qty, 0) SumCost, ";
		if (StringUtils.isNotBlank(str)){
			sql += "isnull(case when ir.Qty<>0 and i.ItemType2 in (" + SqlUtil.resetStatus(str)+")"
			+ " then round(ir.ProcessCost * iad.Qty / ir.Qty, 2) else ir.ProcessCost end, 0) ProcessCost";
		}else{
			sql += "isnull(ir.ProcessCost,0) ProcessCost";
		}
		sql += " from tItemAppDetail iad "
		    + "	inner join tItemApp ia on ia.No=iad.No "
			+ " left join tItemReq ir on iad.ReqPk=ir.Pk "
			+ " left join tItem i on i.Code=iad.ItemCode "
			+ " left join tUOM u on u.Code = i.UOM "
			+ " left join tFixArea tfa on tfa.PK = iad.FixAreaPK "
			+ " left join tIntProd tip on tip.PK = iad.IntProdPK "
		    + " where 1=1 ";

    	if (StringUtils.isNotBlank(itemApp.getNo())) {
			sql += " and iad.No=? ";
			list.add(itemApp.getNo());
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 发货-供应商直送
	 * @param itemApp
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doLlglSendBySupplForProc(ItemApp itemApp) {
		Assert.notNull(itemApp);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLlglSendBySuppl_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemApp.getM_umState());
			call.setString(2, itemApp.getNo());
			call.setString(3, itemApp.getWhcode());
			call.setString(4, itemApp.getItemType1());
			call.setTimestamp(5, new java.sql.Timestamp(new java.util.Date().getTime()));
			call.setString(6,itemApp.getCustCode());
			call.setString(7, itemApp.getSendCzy());
			call.setTimestamp(8, new java.sql.Timestamp(itemApp.getSendDate().getTime()));
			call.setString(9, itemApp.getSupplCode());
			call.setString(10, itemApp.getRemarks());
			call.setString(11, itemApp.getLastUpdatedBy());
			call.setString(12, itemApp.getExpired());
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.NVARCHAR);
			call.setString(15, itemApp.getDelayReson());
			call.setString(16, itemApp.getDelayRemark());
			call.setDouble(17, itemApp.getOtherCost());
			call.setDouble(18, itemApp.getOtherCostAdj());
			call.setString(19, itemApp.getSplRemark());	//发货时，要填写备注。 add by zb on 20200227
			call.execute();
			result.setCode(String.valueOf(call.getInt(13)));
			result.setInfo(call.getString(14));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	public Page<Map<String, Object>> findPageBySql_itemReturn(Page<Map<String,Object>> page,
			ItemApp itemApp) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select b.PK,b.PK appDtpk,b.ItemCode,fc.Descr itemCodeDescr,fa.Descr fixAreaDescr,"
		+"b.Qty oldQty,b.SendQty,b.SendQty qty,b.Remarks,fb.Descr uom,a.sendDate,b.cost  "
		+"from titemapp a inner join tItemAppDetail b on a.no=b.no "
	    +"inner join tCustomer tc on a.custCode=tc.code "
		+"left join tFixArea fa on b.FixAreaPK=fa.PK "
		+"left join tItem fc on b.itemCode=fc.code "
		+"left join tuom fb on fc.uom=fb.code "
		+"where 1=1 and a.Type='S' and a.status='SEND' ";
		
		if (StringUtils.isNotBlank(itemApp.getProjectMan())) {
			sql += " and tc.projectMan=? ";
			list.add(itemApp.getProjectMan());
		}else{
			return null;
		}
		if (StringUtils.isNotBlank(itemApp.getCustCode())) {
			sql += " and a.custCode=? ";
			list.add(itemApp.getCustCode());
		}else{
			return null;
		}
		if (StringUtils.isNotBlank(itemApp.getItemType1())) {
			sql += " and a.itemType1=? ";
			list.add(itemApp.getItemType1());
			if (StringUtils.isNotBlank(itemApp.getItemType2())) {
				sql += " and fc.itemType2=? ";
				list.add(itemApp.getItemType2());
			}
		}
		if (StringUtils.isNotBlank(itemApp.getItemCodeDescr())) {
			sql += " and fc.descr like ? ";
			list.add("%"+itemApp.getItemCodeDescr()+"%");
		}
		sql += " order by b.itemCode,a.sendDate";
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageByPreAppNo(
			Page<Map<String, Object>> page, String id) {
		String sql="SELECT a.No, d.NOTE,a.Date,a.ConfirmDate,a.ArriveDate,a.SendDate,b.Descr itemType1Descr,g.Descr itemType2Descr, "
				+" h.ArriveDate ActualArriveDate "
				+" FROM  tItemApp a "
				+" LEFT JOIN tItemType1 b ON a.ItemType1=b.Code "
				+" LEFT JOIN (SELECT no,MIN(pk)pk FROM tItemPreAppDetail GROUP BY No) c ON a.PreAppNo=c.NO "
				+" LEFT JOIN tItemPreAppDetail e ON c.pk=e.pk "
				+" INNER JOIN titem f ON e.ItemCode =f.Code "
				+" INNER JOIN  tItemType2 g ON f.ItemType2=g.Code "
				+" INNER JOIN tXTDM d ON d.CBM=a.Status "
				+" left join tItemAppSend h on a.No = h.IANo "
				+" where  d.id='ITEMAPPSTATUS'  AND a.PreAppNo=?";
		return this.findPageBySql(page, sql, new Object[]{id});
	}
	
	//计算是否需要自动生成预计到货日期
	public boolean calcIsAutoArriveDate(String supplCode) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select SupplCode from tSupplierTime where Expired='F' and SupplCode=?";
		list.add(supplCode);
		return countSqlResult(sql, "", list.toArray()) > 0;
	}
	
	//根据规则计算预计到货日期
	public Page<Map<String, Object>> findPageByProductType(
			Page<Map<String, Object>> page, ItemApp itemApp) {
		String sql = " select dateadd(day, c.SendDay, a.ConfirmDate) ArriveDate,a.No"
				+ " from tItemApp a"
				+ " inner join tItemAppDetail iad on a.No=iad.No"
				+ " inner join tItem i on iad.ItemCode=i.Code"
				+ " inner join tSupplierTime b on a.SupplCode=b.SupplCode and b.Expired='F'"
				+ " inner join tSendTime c on b.SendTimeNo=c.No and a.ItemType1=c.ItemType1 and (c.ProductType is null or c.ProductType='' or c.ProductType='" + itemApp.getProductType() + "')"
				+ " left join tSendTimeDetail d on c.No=d.No and d.Expired='F' and (d.ItemType2 is null or d.ItemType2=' or d.ItemType2=i.ItemType2) "
				+ " and (d.ItemDesc is null or d.ItemDesc=' or i.Descr like '%' + d.ItemDesc + '%')"
				+ " where a.SendType='1' and a.No='" + itemApp.getNo() + "' and (c.IsSetItem='0' or (c.IsSetItem='1' and d.No is not null))"
				+ " order by c.Prior desc,c.SendDay desc";
		return this.findPageBySql(page, sql);
	}
	
	//计算是否发货超时
	public boolean calcIsTimeout(ItemApp itemApp) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select No from tItemApp where (ArriveDate is not null or NotifySendDate is not null) "
				+ " and datediff(day,case when (NotifySendDate>ArriveDate or ArriveDate is null) then NotifySendDate else ArriveDate end,getdate())>3"
				+ " and No=?";
		list.add(itemApp.getNo());
		return countSqlResult(sql, "", list.toArray()) > 0;
	}

	public Page<Map<String, Object>> findPageBySql_khxxcx(
			Page<Map<String, Object>> page, ItemApp itemApp) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select  a.No ,a.ItemType1 ,i.Descr ItemType1Descr ,a.CustCode ,b.Descr CustDescr ,b.Address ,a.Status ,"
        +"s1.Note StatusDescr ,a.AppCZY ,e1.NameChi AppName ,a.Date ,a.ConfirmCZY ,e2.NameChi ConfirmName ,a.ConfirmDate ,"
        +"a.SendCZY ,e3.NameChi SendName ,a.SendDate ,a.SendType ,s2.Note SendTypeDescr ,a.SupplCode ,sp.Descr SupplDescr ,"
        +"a.PUNo ,a.WHCode ,w.Desc1 WareHouse ,a.Remarks ,a.LastUpdate ,a.LastUpdatedBy ,a.Expired ,a.ActionLog ,p.CheckOutNo ,"
        +"sco.Date CheckOutDate,s3.NOTE IsCupboardDescr "
        +"from tItemApp a "
        +"left join tItemType1 i on a.ItemType1 = i.Code "
        +"left join tCustomer b on b.Code = a.CustCode "
        +"left join tXTDM s1 on a.Status = s1.CBM and s1.ID = 'ITEMAPPSTATUS' "
        +"left join tEmployee e1 on a.AppCZY = e1.Number "
        +"left join tEmployee e2 on a.ConfirmCZY = e2.Number "
        +"left join tEmployee e3 on a.SendCZY = e3.Number "
        +"left join tWareHouse w on a.WHCode = w.Code "
        +"left join tXTDM s2 on a.SendType = s2.CBM and s2.ID = 'ITEMAPPSENDTYPE' "
        +"left join tSupplier sp on a.SupplCode = sp.Code "
        +"left join tPurchase p on a.PUNo = p.no "
        +"left join tSplCheckOut sco on sco.no = p.CheckOutNo "
        +"left join tXTDM s3 on a.IsCupboard = s3.CBM and s3.ID = 'YESNO' "
        +"where 1=1 ";
		if (StringUtils.isNotBlank(itemApp.getCustCode())) {
			sql += " and a.custCode=? ";
			list.add(itemApp.getCustCode());
		}else{
			return null;
		}
		if (StringUtils.isNotBlank(itemApp.getItemType1())) {
			sql += " and a.itemType1=? ";
			list.add(itemApp.getItemType1());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findDetailListBySql(Page<Map<String,Object>> page, ItemApp itemApp) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select qz from tXTCS where id='Titles'";
		String str = "";
		List<Map<String,Object>> listMap = this.findBySql(sql);
		if (listMap!=null && listMap.size()>0){
			 str = (String) listMap.get(0).get("qz");
		}
		  
		sql = "select * from (select iad.projectCost*iad.Qty+isnull(ir.ProcessCost,0) projectAllCost,iad.projectCost,ia.No,ia.ConfirmDate,ia.LastUpdate,iad.ItemCode,i.Descr ItemDescr,iad.Qty,u.Descr UOMDescr,"
				+"iad.Remarks,c.address,c.DocumentNo,ia.projectMan,ia.phone,";
			
		if (StringUtils.isNotBlank(str)){
			sql += "isnull(case when ir.Qty<>0 and i.ItemType2 in (" + SqlUtil.resetStatus(str)+")"
			+ " then round(ir.ProcessCost * iad.Qty / ir.Qty, 2) else ir.ProcessCost end, 0) ProcessCost,";
		}else{
			sql += "isnull(ir.ProcessCost,0) ProcessCost,";
		}
		sql += " ia.WHCode, wh.Desc1 WHCodeDescr from tItemAppDetail iad "
		    + "	inner join tItemApp ia on ia.No=iad.No "
			+ " left join tItemReq ir on iad.ReqPk=ir.Pk "
			+ " left join tItem i on i.Code=iad.ItemCode "
			+ " left join tUOM u on u.Code = i.UOM "
			+ " left join tCustomer c on c.Code = ia.CustCode "
			+ " left join tWareHouse wh on wh.Code = ia.WHCode "
			+ " where 1=1 and ia.SplStatus not in ('0','1') ";

//		UserContext uc = UserContextHolder.getUserContext();
//		sql += " where i.SupplCode= ? ";
//		list.add(uc.getEmnum());
		
		if (StringUtils.isNotBlank(itemApp.getSupplCode())) {
			if (StringUtils.isNotBlank(itemApp.getCzybh())){
				sql += " and ((ia.SupplCode=? and ia.SendType='1') or exists(select 1 from tWareHouseOperater who where ia.WHCode = who.WHCode and who.CZYBH=? and ia.SendType='2')) ";
				list.add(itemApp.getSupplCode());
				list.add(itemApp.getCzybh());
			}else{
				sql += " and ia.SupplCode=? ";
				list.add(itemApp.getSupplCode());
			}
		}
		
		if (StringUtils.isNotBlank(itemApp.getWhcode())) {
			sql += " and ia.WHCode=? ";
			list.add(itemApp.getWhcode());
		}
		if (StringUtils.isNotBlank(itemApp.getSendType())) {
			sql += " and ia.SendType=? ";
			list.add(itemApp.getSendType());
		}
    	if (itemApp.getConfirmDateFrom() != null){
			sql += " and ia.ConfirmDate>= ? ";
			list.add(itemApp.getConfirmDateFrom());
		}
		if (itemApp.getConfirmDateTo() != null){
			sql += " and ia.ConfirmDate<= ? ";
			list.add(new java.util.Date(itemApp.getConfirmDateTo().getTime()+1000*60*60*24-1));
		}
		
		if (itemApp.getSendDateFrom() != null){
			sql += " and ia.SendDate>= ? ";
			list.add(itemApp.getSendDateFrom());
		}
		if (itemApp.getSendDateTo() != null){
			sql += " and ia.SendDate<= ? ";
			list.add(new java.util.Date(itemApp.getSendDateTo().getTime()+1000*60*60*24-1));
		}
		if (StringUtils.isNotBlank(itemApp.getCustAddress())) {
    		sql += " and c.Address like ? ";
    		list.add("%"+itemApp.getCustAddress()+"%");
    	}
		//供应商平台的明细查询要限制领料状态只能是审核、发货
		if (StringUtils.isNotBlank(itemApp.getModule()) &&
        	    "SupplierItemApp".equalsIgnoreCase(itemApp.getModule())) {
        		sql += " and ia.Status in ('CONFIRMED','SEND') ";
        }
		if (StringUtils.isNotBlank(itemApp.getStatus())) {
			sql += " and ia.Status in " + "('"+itemApp.getStatus().replaceAll(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(itemApp.getSplStatus())) {
			sql += " and ia.SplStatus in " + "('"+itemApp.getSplStatus().replaceAll(",", "','")+"')";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> searchIsSetItem(Page<Map<String,Object>> page, ItemApp itemApp) {	
		List<Object> list = new ArrayList<Object>();
		String sql = " select CanInPlan,CanOutPlan from tItemAppCtrl where ItemType1=?"  
				   + " and CustType=(select top 1 CustType from tCustomer where Code=?)";
		list.add(itemApp.getItemType1());
		list.add(itemApp.getCustCode());
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 发货-供应商直送
	 * @param itemApp
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doLlglSendForProc(ItemApp itemApp) {
		Assert.notNull(itemApp);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLlglSend_forProc(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			call.setString(1, itemApp.getM_umState());
			call.setString(2, itemApp.getNo());
			call.setString(3, itemApp.getWhcode());
			call.setString(4, itemApp.getItemType1());
			call.setTimestamp(5, new java.sql.Timestamp(new java.util.Date().getTime()));
			call.setString(6,itemApp.getCustCode());
			call.setString(7, itemApp.getSendCzy());
			call.setTimestamp(8, new java.sql.Timestamp(itemApp.getSendDate().getTime()));
			call.setString(9, itemApp.getRemarks());
			call.setString(10, itemApp.getLastUpdatedBy());
			call.setString(11, itemApp.getExpired());
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.NVARCHAR);
			call.setString(14, "2");
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
	
	public Page<Map<String,Object>> goJqGridItemApp(Page<Map<String,Object>> page, ItemApp itemApp) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select * from ( " 
				   + " select a.No,a.Type,s3.Note TypeDescr,a.ItemType1,i.Descr ItemType1Descr,a.ItemType2,i2.Descr ItemType2Descr, "
				   + " a.CustCode,c.Descr CustDescr,c.CustType,b.Address,b.DocumentNo,a.Status,s1.Note StatusDescr,a.OldNo,a.iscupboard, " 
				   //+ " a.AppCZY,e1.NameChi AppName,a.Date,a.ConfirmCZY,e2.NameChi ConfirmName,a.ConfirmDate, "
				   + " a.AppCZY,c1.ZWXM AppName,a.Date,a.ConfirmCZY,C2.ZWXM ConfirmName,a.ConfirmDate, "
				   //+ " a.SendCZY,e3.NameChi SendName,a.SendDate,a.SendType,s2.Note SendTypeDescr,a.SupplCode,sp.Descr SupplDescr, "
				   + " a.SendCZY,c3.ZWXM SendName,a.SendDate,c5.ZWXM WHReceiveName,a.WHReceiveDate,a.SendType,s2.Note SendTypeDescr,a.SupplCode,sp.Descr SupplDescr, "
				   + " a.PUNo,a.WHCode,w.Desc1 WareHouse,a.Remarks,a.ProjectMan,a.Phone,a.LastUpdate,s10.NOTE checkstatusdescr, "
				   + " a.LastUpdatedBy,a.Expired,a.ActionLog,a.DelivType,x.NOTE DelivTypeDescr,a.OtherCost,a.IsService,i2.MaterWorkType2,a.PreAppNo, " //^_^ 20160314 modi by xzp 增加查询字段a.PreAppNo
				   + " a.Amount,a.ProjectAmount,c.CheckStatus,a.IsSetItem,s4.Note IsSetItemDescr,wco.DocumentNo WHCheckOutDocumentNo, " //^_^ 20160105 modi by xzp 增加查询字段WHCheckOutDocumentNo
				   + " a.SplStatus,s5.Note SplStatusDescr,a.ArriveDate,a.SplRemark,a.OtherCostAdj,a.PrintTimes,a.PrintCZY,c4.ZWXM PrintCZYDescr,a.PrintDate, "
				   + " d2.Desc1 PrjDept2Descr,wco.Status WHCheckOutStatus,s6.NOTE WHCheckOutStatusDescr,wco.No WHCheckOutNo,w.IsManagePosi,s7.NOTE DelayResonDescr, "
				   + " s8.Note ProductTypeDescr,DelayRemark,a.ArriveSplDate,b.SignDate ,ct.desc1 custTypeDescr  ,a.SendBatchNo,tr1.descr region1descr, s11.NOTE IsCupboardDescr,"
				   + " dbo.fGetEmpNameChi(a.CustCode,'62') softTnstallleader ,dbo.fGetEmpNameChi(a.CustCode, '34') MainManDescr,s9.NOTE IsServiceDescr,ts.ConfirmDate arriveConfirmDate, " 
				   + " tr.descr RegionDescr,cast(a.toConfirmDate as datetime) toConfirmDate,cp.lastPayDate,a.EntrustProcStatus, "//增加一级区域显示 zb
				   + " s12.NOTE EntrustProcStatusDescr,a.EntrustProcSendDate, IntR.No ReplenishNo,a.NotifySendDate " //增加委托加工状态、委外发出日期 add by zb on 20191231
				   + " from tItemApp a "
				   + " left outer join tItemType1 i on a.ItemType1=i.Code "
				   + " left outer join tItemType2 i2 on a.ItemType2=i2.Code "
				   + " left outer join tCustomer c on a.CustCode=c.Code "
				   + " left outer join tCustomer b on b.Code=a.CustCode "
				   + " left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='ITEMAPPSTATUS' "
				   //  + " left outer join tEmployee e1 on a.AppCZY=e1.Number "
				   //  + " left outer join tEmployee e2 on a.ConfirmCZY=e2.Number "
				   //  + " left outer join tEmployee e3 on a.SendCZY=e3.Number "
				   + " left outer join tCZYBM c1 on a.AppCZY=c1.CZYBH "
				   + " left outer join tCZYBM c2 on a.ConfirmCZY=c2.CZYBH "
				   + " left outer join tCZYBM c3 on a.SendCZY=c3.CZYBH "
				   + " left outer join tCZYBM c4 on a.PrintCZY=c4.CZYBH "
				   + " left outer join tCZYBM c5 on a.WHReceiveCZY=c5.CZYBH "
				   + " left outer join tWareHouse w on a.WHCode=w.Code "
				   + " left outer join tXTDM  s2 on a.SendType=s2.CBM and s2.ID='ITEMAPPSENDTYPE' "
				   + " left outer join tXTDM  s3 on a.Type=s3.CBM and s3.ID='ITEMAPPTYPE' "
				   + " left outer join tXTDM  x on a.DelivType=x.CBM and x.ID='DELIVTYPE' "
				   + " left outer join tSupplier sp on a.SupplCode=sp.Code "
				   + " left outer join tXTDM s4 on a.IsSetItem=s4.CBM and s4.ID='YESNO' " //^_^ 20151123 add by xzp
				   + " left outer join tWHCheckOut wco on a.WHCheckOutNo=wco.No " //^_^ 20160105 add by xzp
				   + " left outer join tXTDM s5 on a.SplStatus=s5.CBM and s5.ID='APPSPLSTATUS' "
				   + " left outer join tEmployee e4 on c.ProjectMan=e4.Number "
				   + " left outer join tDepartment2 d2 on e4.Department2=d2.Code "
				   + " left outer join tXTDM s6 on wco.Status=s6.CBM and s6.ID='WHChkOutStatus' "
				   + " left outer join tCusttype ct on ct.Code=c.CustType "
				   + " left outer join tXTDM s7 on a.DelayReson=s7.CBM and s7.ID='APPDELAYRESON' "
				   + " left outer join tXTDM s8 on a.ProductType=s8.CBM and s8.ID='APPPRODUCTTYPE' "
				   + " left outer join tXTDM s9 on a.IsService=s9.CBM and s9.ID='YESNO' " //^_^ 20151123 add by xzp
				   + " left join tXTDM s10 on s10.CBM = b.CheckStatus and s10.ID = 'CheckStatus' "//增加客户结算状态 by cjg
				   + " left join tXTDM s11 on s11.CBM = a.isCupboard and s11.ID = 'YESNO' "//增加是否橱柜by cjg
				   + " left join (select IANo,max(ConfirmDate) ConfirmDate from tItemAppSend where ConfirmDate is not null group by IANo) ts on ts.iano=a.no " 
				   + " left join tBuilder tb on tb.code=c.BuilderCode " //增加一级区域 zb
				   + " left join tSendRegion tr on tr.No=tb.SendRegion "//一级区域改为配送区域update by cjg
				   + " left join tRegion tr1 on tr1.Code=tb.RegionCode "
				   + " left join (   "
				   + "     select CustCode, max(AddDate) lastPayDate "
				   + "     from tCustPay "
				   + "     where  Amount > 0 and (DiscTokenNo is null or DiscTokenNo = '') " // 付款记录中金额大于0，并且优惠券号为空 zhy
				   + "     group by CustCode "
				   + " ) cp on a.CustCode=cp.CustCode"
				   + " left join tXTDM s12 on s12.ID='EntrProcStatus' and s12.CBM = a.EntrustProcStatus "
				   + " left join tIntReplenishdt IntR on IntR.Pk=a.IntRepPK   "
				   + " where 1=1 ";
		
    	if (StringUtils.isNotBlank(itemApp.getNo())) {
			sql += " and a.No=? ";
			list.add(itemApp.getNo());
		}
    	if(StringUtils.isNotBlank(itemApp.getWhcode())){
    		sql += " and a.whcode in ('"+itemApp.getWhcode().trim().replace(",", "','")+"') ";
    	}
		if (StringUtils.isNotBlank(itemApp.getRemarks())) {
    		sql += " and a.Remarks like ? ";
    		list.add("%"+itemApp.getRemarks()+"%");
    	}
    	if (StringUtils.isNotBlank(itemApp.getCustCode())) {
    		sql += " and a.CustCode=? ";
    		list.add(itemApp.getCustCode());
    	}
    	if (StringUtils.isNotBlank(itemApp.getAppCzy())) {
			sql += " and a.AppCZY=? ";
			list.add(itemApp.getAppCzy());
		}
		if (StringUtils.isNotBlank(itemApp.getSupplCode())) {
/*			if (StringUtils.isNotBlank(itemApp.getCzybh())){
				sql += " and ((a.SupplCode=? and a.SendType='1') or exists(select 1 from tWareHouseOperater who where a.WHCode = who.WHCode and who.CZYBH=? and a.SendType='2')) ";
				list.add(itemApp.getSupplCode());
				list.add(itemApp.getCzybh());
			}else{*/ //remove by zzr 2018/1/3
				sql += " and a.SupplCode=? ";
				list.add(itemApp.getSupplCode());
			//}
		}
    	if (StringUtils.isNotBlank(itemApp.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(itemApp.getItemType1());
		}else{
			sql += " and a.ItemType1 in ('"+itemApp.getItemRight().trim().replace(",", "','")+"')";
		}
    	if (StringUtils.isNotBlank(itemApp.getItemType2())) {
			sql += " and a.ItemType2=? ";
			list.add(itemApp.getItemType2());
		}
    	if (StringUtils.isNotBlank(itemApp.getDelivType())) {
			sql += " and a.DelivType=? ";
			list.add(itemApp.getDelivType());
		}
    	if (StringUtils.isNotBlank(itemApp.getType())) {
			sql += " and a.Type=? ";
			list.add(itemApp.getType());
		}
    	if (StringUtils.isNotBlank(itemApp.getCustAddress())) {
    		sql += " and b.Address like ? ";
    		list.add("%"+itemApp.getCustAddress()+"%");
    	}
    	if (itemApp.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(itemApp.getDateFrom());
		}
		if (itemApp.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(DateUtil.endOfTheDay(itemApp.getDateTo()));
		}
		if (StringUtils.isNotBlank(itemApp.getIsSetItem())) {
			sql += " and a.IsSetItem=? ";
    		list.add(itemApp.getIsSetItem());
		}
		if (StringUtils.isNotBlank(itemApp.getPreAppNo())) {
			sql += " and a.PreAppNo=? ";
    		list.add(itemApp.getPreAppNo());
		}
    	if (itemApp.getConfirmDateFrom() != null){
			sql += " and a.ConfirmDate>= ? ";
			list.add(itemApp.getConfirmDateFrom());
		}
		if (itemApp.getConfirmDateTo() != null){
			sql += " and a.ConfirmDate<= ? ";
			list.add(DateUtil.endOfTheDay(itemApp.getConfirmDateTo()));
		}
    	if (StringUtils.isNotBlank(itemApp.getSplStatus())) {
			sql += " and a.SplStatus in " + "('"+itemApp.getSplStatus().replaceAll(",", "','")+"')";
		}
		if (itemApp.getSendDateFrom() != null){
			sql += " and a.SendDate>= ? ";
			list.add(itemApp.getSendDateFrom());
		}
		if (itemApp.getSendDateTo() != null){
			sql += " and a.SendDate<= ? ";
			list.add(DateUtil.endOfTheDay(itemApp.getSendDateTo()));
		}
		if (StringUtils.isNotBlank(itemApp.getCustDocumentNo())) {
			sql += " and c.DocumentNo like ? ";
    		list.add("%"+itemApp.getCustDocumentNo()+"%");
		}
		if (StringUtils.isNotBlank(itemApp.getSendNo())) {
			sql += " and exists (select 1 from tItemAppSend where IANo=a.No and No=?) ";
    		list.add(itemApp.getSendNo());
		}
/*    	if (StringUtils.isNotBlank(itemApp.getItemCode())) {
			sql += " and exists(select 1 from tItemAppDetail where No=a.no and ItemCode=?) ";
			list.add(itemApp.getItemCode());
		}*/
		if (StringUtils.isNotBlank(itemApp.getItemCodeDescr())){
			sql += " and exists ( "
			     + " 	select 1 from tItemAppDetail in_a " 
				 + " 	inner join tItem in_b on in_a.ItemCode=in_b.Code "
			     + " 	where in_a.No=a.No and in_b.Descr like ? " 
				 + " )";
			list.add("%"+itemApp.getItemCodeDescr()+"%");
		}
    	if (StringUtils.isNotBlank(itemApp.getSendType())) {
			sql += " and a.sendType=? ";
			list.add(itemApp.getSendType());
		}
    	if(StringUtils.isNotBlank(itemApp.getCustType())){
    		sql += " and c.CustType in ('"+itemApp.getCustType().trim().replace(",", "','")+"') ";
    	}
/*    	if (StringUtils.isNotBlank(itemApp.getConfirmStatus())) {
			sql += " and exists(select 1 from tItemAppSend where confirmStatus=? and iano=a.no) ";
			list.add(itemApp.getConfirmStatus());
		}
    	if (StringUtils.isNotBlank(itemApp.getModule()) &&
        	    "SupplierItemApp".equalsIgnoreCase(itemApp.getModule())) {
        		sql += " and a.Status in ('CONFIRMED','SEND') ";
        }	*/ //remove by zzr 2017/1/3
    	if (StringUtils.isNotBlank(itemApp.getStatus())) {
			sql += " and a.Status in " + "('"+itemApp.getStatus().replaceAll(",", "','")+"')";
		}
    	if("T".equals(itemApp.getDiffQty())){
    		sql += " and exists (select 1 from tItemAppDetail where No=a.No and DeclareQty<>Qty) ";
    	}
    	if("F".equals(itemApp.getExpired())){
    		sql += " and a.Expired='F' ";
    	}
    	if(StringUtils.isNotBlank(itemApp.getDepartment2())){
    		sql += " and d2.Code in ('"+itemApp.getDepartment2().trim().replace(",", "','")+"')";
    	}
		if (itemApp.getIsService() != null) {
			sql += " and a.IsService=? ";
    		list.add(itemApp.getIsService());
		}
		if (StringUtils.isNotBlank(itemApp.getCheckStatus())) {
			sql += " and b.CheckStatus in ('"+itemApp.getCheckStatus().replace(",", "','")+"') ";
		}
		if(StringUtils.isNotBlank(itemApp.getPrjRegion())){
			sql+="  and exists ( select  1 from tRegion2 a  where a.PrjRegionCode = ? and a.Code=tb.regionCode2 )";
			list.add(itemApp.getPrjRegion());
		}
		if (StringUtils.isNotBlank(itemApp.getProductType())) {// 增加产品类型查询条件 add by zb on 20191111
			sql += " and a.productType in ('"+itemApp.getProductType().replace(",", "','")+"')";
		}
		
		if (StringUtils.isNotBlank(itemApp.getDelayReson())) {
            sql += " and a.DelayReson in('" + itemApp.getDelayReson().replace(",", "','") + "') ";
        }
		
		if (StringUtils.isNotBlank(itemApp.getIsMaterialSendJob())){
		    if ("0".equals(itemApp.getIsMaterialSendJob())) {
                sql += " and a.NotifySendDate is null ";
            } else if ("1".equals(itemApp.getIsMaterialSendJob())) {
                sql += " and a.NotifySendDate is not null";
            }
		}
		
		if (StringUtils.isNotBlank(itemApp.getIntReplenishNo())) {
            sql += " and IntR.No = ? ";
            list.add(itemApp.getIntReplenishNo());
        }
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Map<String,Object> getCanInOutPlan(String itemType1,String custCode){
		String sql = " select CanInPlan,CanOutPlan "
					+" from tItemAppCtrl "
					+" where ItemType1=?  and CustType=(select top 1 CustType from tCustomer where Code=? ) ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itemType1,custCode});
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public Page<Map<String,Object>> getReturnAddJqGrid(Page<Map<String,Object>> page, String oldNo,String reqPks,String custCode){
		List<Object> params = new ArrayList<Object>();
		String titles = this.get(Xtcs.class, "Titles").getQz();
		String sql = " select * from ( select a.specreqpk,a.PK,a.No,a.ReqPK,a.FixAreaPK,b.Descr FixAreaDescr,a.IntProdPK,c.Descr IntProdDescr, "
                   + " a.ItemCode,d.Descr ItemDescr,a.Qty,isnull(ir.Qty,0) ReqQty,ir.SendQty SendedQty, "
                   + " ConfirmedQty=ISNULL((SELECT SUM(Qty) FROM tItemAppDetail i2, "
                   + " tItemApp i1 WHERE i2.No=i1.No AND i1.Status='CONFIRMED' and i2.ReqPK=a.ReqPK ),0), "
                   + " a.Remarks,a.Cost,a.ProjectCost,a.AftQty,a.AftCost, "
                   + " round(a.Cost*a.Qty,2) AllCost,round(a.ProjectCost*a.Qty,2) AllProjectCost,u.Descr UomDescr, "
                   + " d.PerWeight,d.PerNum,a.Qty*d.PerWeight Weight,a.Qty*d.PerNum Num, "
                   + " case when d.PackageNum=1 or d.PackageNum=0 or a.Qty*d.PerNum<d.PackageNum then cast (cast(a.Qty*d.PerNum as float) as nvarchar(100)) "
                   + " when a.Qty*d.PerNum/d.PackageNum = cast(a.Qty*d.PerNum/d.PackageNum as int) then cast(cast(a.Qty*d.PerNum/d.PackageNum as int) as nvarchar(100))+'箱'  "
                   + " else cast(cast(floor(a.Qty*d.PerNum/d.PackageNum) as int) as nvarchar(100))+'箱'+ cast(cast(a.Qty*d.PerNum-d.PackageNum*floor(a.Qty*d.PerNum/d.PackageNum) as float) as nvarchar(100))+'片' end NumDescr, "
                   + " isnull(case when d.ItemType2 in ('" + titles.trim().replace(",", "','") + "') and ir.Qty<>0 then round(ir.ProcessCost*a.Qty/ir.Qty,2) else ir.ProcessCost end, 0) ProcessCost,isnull(f.Qty,0) ReturnAppQty, "
                   + " a.SendQty,a.SupplDescr,0 PreAppDTPK,0 ShortQty,a.Qty DeclareQty"
                   + " from ( "
                   + " 		select t1.PK,isnull(t1.ReqPK,0) ReqPK,isnull(t1.specreqpk,0) specreqpk, "//,t1.no
                   + " 		isnull(t1.FixAreaPK,0) FixAreaPK,fa.Descr FixAreaDescr,t1.IntProdPK,ip.Descr IntProdDescr, "
                   + " 		t1.ItemCode,sp.Descr SupplDescr,i.Descr ItemDescr,t1.Qty,isnull(t2.Qty,0) ReqQty, "
                   + " 		isnull(t2.SendQty,0) SendQty,t1.Cost,t1.ProjectCost,t1.AftQty,t1.AftCost, "
                   + " 		t1.Remarks,1 No "
                   + " 		from tItemAppDetail t1  "
                   + " 		left outer join tItemReq t2 on t2.pk=t1.ReqPK "
                   + " 		left outer join tFixArea fa on t1.FixAreaPK=fa.PK "
                   + " 		left outer join tIntProd ip on t1.IntProdPK=ip.PK "
                   + " 		left outer join tItem i on t1.ItemCode=i.Code "
                   + " 		left outer join tSupplier sp on sp.Code=i.SupplCode and i.Code = t1.ItemCode "
                   + " 		where t1.no=? ";
		params.add(oldNo);		
		if(StringUtils.isNotBlank(reqPks)){
			sql += " and t1.reqPk not in ('"+reqPks.trim().replace(",", "','")+"')";
		}
		sql += " ) a  "
             + "  left outer join tItemReq IR on IR.pk=a.ReqPK "
             + "  left outer join tFixArea b on a.FixAreaPK = b.PK "
             + "  left outer join tIntProd c on a.IntProdPK = c.PK "
             + "  left outer join tItem d on a.ItemCode = d.Code "
             + "  left outer join tUom u on u.code=d.uom "
             //+ " left outer join tItemApp p on a.No = p.No and p.ItemType1 =  ' +  QuotedStr(GetBh(CbxItemType1.Text))
             + "  left outer join ( "
             + "    select b.ItemCode, sum(b.Qty) Qty from tItemReturn a "
             + "    inner join tItemReturnDetail b on a.No=b.No "
             + "    where a.Status in ('3','4','5','6') and a.CustCode=? "
             + "    group by b.ItemCode "
             + "  ) f on f.ItemCode=a.ItemCode "
             + "  where  exists( "
             + " 	select 1 from ( "
             + " 		select sum(in2_a.SendQty) SendQty "
             + " 		from tItemAppDetail in2_a "
             + " 		inner join tItem in2_b on in2_a.ItemCode = in2_b.Code "
             + " 		left join tItem in2_c on in2_b.IsActualItem = '0' and in2_b.WareHouseItemCode = in2_c.Code "
             + " 		left join titemapp in2_d on in2_a.no=in2_d.no "
             + " 		where in2_d.OldNo=? and ((a.FixAreaPK > 0 and in2_a.FixAreaPK  = a.FixAreaPK) or a.FixAreaPK  <= 0 or a.FixAreaPK  is null) "
             + " 		and (case in2_b.IsActualItem when '1' then in2_b.Code when '0' then in2_c.Code end) = a.ItemCode "
             + " 	) in_a  "
             + " where  isnull(in_a.SendQty,0) < a.Qty "
             + " ) ";
		
		params.add(custCode);
		params.add(oldNo);
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String,Object>> getReturnDetailJqGrid(Page<Map<String,Object>> page, String itemCode,String custCode){
		String sql = " select * from ( select a.No, a.Date, b.ItemCode, i.Descr ItemDescr, b.Qty "
				   + " 		from tItemReturn a "
				   + " 		inner join tItemReturnDetail b on a.No=b.No "
				   + " 		left join tItem i on b.ItemCode=i.Code "
				   + " 		where a.Status in ('3','4','5','6') and a.CustCode=? and b.ItemCode=? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.Date ";
		}
		return this.findPageBySql(page, sql, new Object[]{custCode,itemCode});
	}

	public Map<String,Object> getWHOperator(String czybh,String whcode){
		String sql = " select 1 from tWareHouseOperater where Expired='F' and CZYBH=? and WHCode=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{czybh,whcode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Result doLlglthSave(ItemApp itemApp, String xml){		
		Assert.notNull(itemApp);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLlglthPt1_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, xml);
			call.setString(2, itemApp.getM_umState());
			call.setString(3, itemApp.getBackType());
			call.setString(4, itemApp.getCzybh());
			call.setString(5, itemApp.getWhcode());
			call.setDouble(6, itemApp.getOtherCost() == null ? 0:itemApp.getOtherCost());
			call.setString(7, itemApp.getNo());
			call.setString(8, itemApp.getStatus());
			call.setString(9, itemApp.getType());
			call.setString(10, itemApp.getBackType());
			call.setString(11, itemApp.getItemType1());
			call.setString(12, itemApp.getItemType2());
			call.setString(13, itemApp.getCustCode());
			call.setString(14, itemApp.getAppCzy());
			call.setString(15, itemApp.getSupplCode());
			call.setString(16, itemApp.getRemarks());
			call.setInt(17, itemApp.getIsService());
			call.setInt(18, Integer.parseInt(itemApp.getIsSetItem()));
			call.setString(19, itemApp.getOldNo());
			call.setString(20, itemApp.getCzybh());
			call.setString(21, "F");
			
			call.registerOutParameter(22, Types.INTEGER);
			call.registerOutParameter(23, Types.NVARCHAR);
			call.setString(24, itemApp.getIsCupboard());
			call.execute();
			result.setCode(String.valueOf(call.getInt(22)));
			result.setInfo(call.getString(23));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	public Map<String,Object> getItemAppInfo(String no){
		String sql = " select tia.CustCode,tia.OtherCost,tia.no appNo,tc.Address,tc.DocumentNo,tc.Area,tia.PreAppNo,tia.ItemType1,tia.ItemType2,tia.DelivType, "
				   + " tia.Type itemAppType,tia.ProjectMan projectManDescr,td1.Desc1 department1Descr,td2.Desc1 department2Descr,tia.SendType, "//,te.NameChi 
				   + " tia.Status itemAppStatus,tia.Phone,tia.IsSetItem,tia.WHCode wareHouseCode,tia.SupplCode,tia.Remarks,RTRIM(tia.IsService+'') IsService,RTRIM(tia.IsCupboard+'') IsCupboard, "
				   + " te1.NameChi appCzyDescr,te2.NameChi confirmCzyDescr,tczybm.ZWXM sendCzyDescr,tczybm2.ZWXM whReceiveCzyDescr,tia.Date,tia.ConfirmDate,tia.SendDate,tia.WHReceiveDate, "
				   + " tia.PUNo,tia.OtherCostAdj,tia.Expired,tspl.Descr supplCodeDescr,twh.Desc1 whcodeDescr,tc.SignDate,tc.custType,tc.CheckStatus m_CheckStatus,tia.splRemark, "
				   + " tia.splStatus,tx1.NOTE splStatusDescr,tia.arriveSplDate,tia.splRemark,tia.arriveDate,tspl.Descr supplerDescr,twh.Desc1 wareHouseDescr,tc.Descr custCodeDescr, "
				   + " tczybm1.ZWXM splRcvCZYDescr,tia.splRcvDate,it1.isSpecReq,tia.ConfirmRemark,tia.RefCustCode,tc2.Descr RefCustCodeDescr,tc2.address refAddress,bd.regionCode, "
				   + " tia.intRepPK,irdt.remarks repRemarks,tia.faultType,tia.faultMan,case when tia.faultType='1' then te3.NameChi when tia.faultType='2' then wk2.NameChi else '' end faultManDescr," 
				   + " wk.QualityFee prjQualityFee,tc2.ProjectMan refProjectMan,te4.NameChi refProjectManDescr "
				   + " from tItemApp tia "
				   + " left join tCustomer tc on tia.CustCode = tc.Code "
				   + " left join tCustomer tc2 on tia.RefCustCode = tc2.Code "
				   + " left join tEmployee te on te.nameChi = tia.ProjectMan "
				   + " left join tDepartment1 td1 on td1.Code = te.Department1 "
				   + " left join tDepartment2 td2 on td2.Code = te.Department2 "
				   + " left join tEmployee te1 on te1.Number = tia.AppCZY "
				   + " left join tEmployee te2 on te2.Number = tia.ConfirmCZY "
				   + " left join tCZYBM tczybm on tczybm.CZYBH = tia.SendCZY "
				   + " left join tCZYBM tczybm2 on tczybm2.CZYBH = tia.WHReceiveCZY "
				   + " left join tSupplier tspl on tspl.Code = tia.SupplCode "
				   + " left join tWareHouse twh on twh.Code = tia.WHCode "	
				   + " left join tXTDM tx1 on tx1.ID='APPSPLSTATUS' and tx1.CBM = tia.splStatus "
				   + " left join tCZYBM tczybm1 on tczybm1.CZYBH = tia.splRcvCZY "
				   + " left join tItemType1 it1 on it1.code = tia.itemtype1 "
				   + " left join tBuilder bd on tc.BuilderCode=bd.Code "
				   + " left join tIntReplenishDT irdt on tia.IntRepPK=irdt.PK "
				   + " left join tEmployee te3 on tia.FaultMan=te3.Number "
				   + " left join tWorker wk on tc2.ProjectMan=wk.EmpCode "
				   + " left join tWorker wk2 on tia.FaultMan=wk2.Code "
				   + " left join tEmployee te4 on tc2.ProjectMan=te4.Number "
				   + " where tia.No=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{no});
		if(list != null && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}

	public Map<String,Object> getItemReturnInfo(String no){
		String sql = " select tia.No,tia.Expired,tia.Type,tia.Status,tia.SendType backType,tia.CustCode,tc.Address custAddress,tc.DocumentNo custDocumentNo, "
				   + " tia.IsService,tia.ItemType1,tia.ItemType2,tia.SupplCode,tspl.Descr SupplCodeDescr,tia.WHCode,twh.Desc1 whcodeDescr,tia.IsSetItem, "
				   + " tczybh.ZWXM appCzyDescr,tia.Date,tia.OldNo,tia.OtherCost,tia.Remarks,tc.Descr custCodeDescr,tia.SendType,tiaOld.SendType oldSendType, "
				   + " tsplOld.Descr oldSupplCodeDescr,tiaOld.SupplCode oldSupplCode,twhOld.Desc1 oldWhcodeDescr,tia.WHCode oldWhCode,tiaOld.projectMan,tia.isCupboard "
				   + " from tItemApp tia "
				   + " left join tItemApp tiaOld on tiaOld.No = tia.OldNo"
				   + " left join tCustomer tc on tia.CustCode = tc.Code "
				   + " left join tSupplier tspl on tspl.Code = tia.SupplCode "
				   + " left join tWareHouse twh on twh.Code = tia.WHCode "
				   + " left join tCZYBM tczybh on tczybh.CZYBH = tia.AppCZY "
				   + " left join tSupplier tsplOld on tsplOld.Code = tiaOld.SupplCode "
				   + " left join tWareHouse twhOld on twhOld.Code = tiaOld.WHCode "
				   + " where tia.No=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{no});
		if(list != null && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String,Object>> getItemReturnDTJqGrid(Page<Map<String,Object>> page, String no,String custCode){
		Xtcs xtcs = this.get(Xtcs.class, "Titles");
		String sql = " select * from ( Select a.specreqpk,a.PK,a.No,a.ReqPK,a.FixAreaPK,b.Descr FixAreaDescr,a.IntProdPK,c.Descr IntProdDescr, "
				   + " a.ItemCode,d.Descr ItemDescr,a.Qty,ir.Qty ReqQty,ir.SendQty SendedQty,a.declareqty, "
				   + " ConfirmedQty=ISNULL((SELECT SUM(Qty) FROM tItemAppDetail i2, "
				   + " tItemApp i1 WHERE i2.No=i1.No AND i1.Status='CONFIRMED' and i2.ReqPK=a.ReqPK ),0), "
				   + " a.Remarks,a.Cost,a.ProjectCost,a.AftQty,a.AftCost, "
				   + " round(a.Cost*a.Qty,2) AllCost,round(a.ProjectCost*a.Qty,2) AllProjectCost,u.Descr UomDescr, "
				   + " d.PerWeight,d.PerNum,a.Qty*d.PerWeight Weight,a.Qty*d.PerNum Num, "
				   + " case when d.PackageNum=1 or d.PackageNum=0 or a.Qty*d.PerNum<d.PackageNum then cast (cast(a.Qty*d.PerNum as float) as nvarchar(100)) "
				   + " when a.Qty*d.PerNum/d.PackageNum = cast(a.Qty*d.PerNum/d.PackageNum as int) then cast(cast(a.Qty*d.PerNum/d.PackageNum as int) as nvarchar(100))+'箱' "
				   + " else cast(cast(floor(a.Qty*d.PerNum/d.PackageNum) as int) as nvarchar(100))+'箱'+ cast(cast(a.Qty*d.PerNum-d.PackageNum*floor(a.Qty*d.PerNum/d.PackageNum) as float) as nvarchar(100))+'片' end NumDescr, "
				   + " isnull(case when d.ItemType2 in ('"+xtcs.getQz().trim().replace(",", "','")+"') and ir.Qty<>0 then round(ir.ProcessCost*a.Qty/ir.Qty,2) else ir.ProcessCost end, 0) ProcessCost,isnull(f.Qty,0) ReturnAppQty "
				   + " ,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
				   + " from ( "
				   + " 		select a.specreqpk,a.PK,a.No,a.ReqPK,a.FixAreaPK,a.IntProdPK,a.ItemCode,a.Qty, "
				   + " 		a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.declareqty, "
				   + " 		a.Cost,a.ProjectCost,a.AftQty,a.AftCost "
				   + " 		FROM tItemAppDetail a "
				   + " 		where a.No = ? "
				   + " ) a"
				   + " left outer join tItemReq IR on IR.pk=a.ReqPK "
				   + " left outer join tFixArea b on a.FixAreaPK = b.PK "
		           + " left outer join tIntProd c on a.IntProdPK = c.PK "
		           + " left outer join tItem d on a.ItemCode = d.Code "
		           + " left outer join tUom u on u.code=d.uom "
		           //+ 'left outer join tItemApp p on a.No = p.No and p.ItemType1 =  ' +  QuotedStr(GetBh(CbxItemType1.Text))
		           + " left outer join ( "
		           + "   select b.ItemCode, sum(b.Qty) Qty from tItemReturn a "
		           + "   inner join tItemReturnDetail b on a.No=b.No "
		           + "   where a.Status in ('3','4','5','6') and a.CustCode=? "
		           + "   group by b.ItemCode "
		           + " ) f on f.ItemCode=a.ItemCode "
		           + " where 1=1  ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, new Object[]{no,custCode});
	}

	public Map<String,Object> getCZYGNQX(String czybh,String mkdm,String gnmc){
		String sql = " select 1 result from tCZYGNQX where MKDM=? and GNMC=? and CZYBH=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{mkdm,gnmc,czybh});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public List<Map<String,Object>> getSendQtyByNo(String no){
		String sql = "select isnull(sum(SendQty),0) sumSendQty from tItemAppDetail where no=? ";
		return this.findBySql(sql, new Object[]{no});
	}
	
	public Map<String,Object> doUnCheck(String no){
		String sql = " set nocount on "
				   + " Update tItemApp Set ConfirmCZY = '',ConfirmDate = NULL,Status ='OPEN',SplStatus='0', SplRcvCZY=null, splRcvDate=null, ToConfirmDate=null where Status='CONFIRMED' "
				   + " and No=? and not exists (select 1 from tItemAppDetail where No=tItemApp.No and SendQty>0) "
				   + " select @@ROWCOUNT ret "
				   + " set nocount off ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{no});
		if(list != null && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}
	public Page<Map<String,Object>> getSendAppDtlJqGrid(Page<Map<String,Object>> page, String no,String custCode){
		String sql = " Select a.Cost,a.PK,a.No,a.ReqPK,a.FixAreaPK,b.Descr FixAreaDescr,a.IntProdPK,c.Descr IntProdDescr, "
				   + " a.ItemCode,d.Descr ItemDescr,sp.Code,sp.Descr SupplierDescr,a.Qty,a.ReqQty,a.SendedQty,ConfirmedQty=ISNULL((SELECT sum(Qty) FROM tItemAppDetail i2, "
				   + " tItemApp i1 WHERE i2.No=i1.No AND i2.ReqPk = a.ReqPk AND i1.Status='CONFIRMED' and i1.CustCode = ? ),0),"
				   + " a.Remarks,a.Color "
				   + " from ( "
				   + " 		select a.Cost,a.ProjectCost,a.PK,a.No,a.ReqPK,a.FixAreaPK,a.IntProdPK,a.ItemCode,a.Qty,b.Qty as ReqQty,b.SendQty as SendedQty, "
				   + " 		a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,ts.Color "
				   + " 		FROM tItemAppDetail a "
				   + " 		left outer join tItemAppSendDetail ts on ts.RefPK = a.PK "
				   + " 		left outer join tItemReq b on a.ReqPK = b.PK "
				   + " 		where a.No = ? "
				   + " ) a "
				   + " left outer join tFixArea b on a.FixAreaPK = b.PK "
				   + " left outer join tIntProd c on a.IntProdPK = c.PK "
				   + " left outer join tItem d on a.ItemCode = d.Code "
				   + " left outer join tSupplier sp on d.SupplCode = sp.Code "
				   + " where 1=1 ";
		return this.findPageBySql(page, sql, new Object[]{custCode,no});
	}
	
	public Result doSendForXml(String m_umState,String no,String whcode,Date sendDate,String itemSendBatchNo ,String czybh, String xml, String manySendRsn,String delivType,String delayReson,String delayRemark){
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLlglSend_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, m_umState);
			call.setString(2, no);
			call.setString(3, whcode);
			call.setDate(4, new java.sql.Date(sendDate.getTime()));
			call.setString(5, itemSendBatchNo);
			call.setString(6, czybh);
			call.setString(7, xml);
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.NVARCHAR);
			call.setString(10, manySendRsn);
			call.setString(11, delivType);
			call.setString(12, StringUtils.isBlank(delayReson)?"0":delayReson);
			call.setString(13, delayRemark);
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
	
	public Result doSendBySupplForXml(String m_umState,String no,Date sendDate,String supplCode,String czybh, String xml,String manySendRsn,String itemSendBatchNo){
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLlglSendBySupplPt1_forXml(?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, m_umState);
			call.setString(2, no);
			call.setDate(3, new java.sql.Date(sendDate.getTime()));
			call.setString(4, supplCode);
			call.setString(5, czybh);
			call.setString(6, xml);
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.NVARCHAR);	
			call.setString(9, manySendRsn);
			call.setString(10, itemSendBatchNo);
			call.execute();
			result.setCode(String.valueOf(call.getInt(7)));
			result.setInfo(call.getString(8));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Page<Map<String,Object>> getPSendAppDtlJqGrid(Page<Map<String,Object>> page, String no,String custCode){
		String sql = " select * from ( Select a.PK,a.No,a.ReqPK,a.FixAreaPK,a.IntProdPK,a.ItemCode,a.Qty,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired, "
				   + " a.ActionLog,a.Cost,a.AftQty,a.AftCost,a.SendQty,a.ProjectCost,a.PreAppDTPK,a.ShortQty,a.DeclareQty, "
				   + " b.Descr FixAreaDescr,c.Descr IntProdDescr, "
				   + " d.Descr ItemDescr,sp.Code,sp.Descr SupplierDescr,"
				   + " ConfirmedQty=ISNULL((SELECT sum(Qty) FROM tItemAppDetail i2, "
				   + " tItemApp i1 WHERE i2.No=i1.No AND i2.ReqPk = a.ReqPk AND i1.Status='CONFIRMED' and i1.CustCode = ? ),0), "
				   + " a.TheSendQty,a.ReqQty,a.SendedQty "
				   + " from ( "
				   + " 		select a.PK,a.No,a.ReqPK,a.FixAreaPK,a.IntProdPK,a.ItemCode,a.Qty,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired, "
				   + " 		a.ActionLog,a.Cost,a.AftQty,a.AftCost,a.SendQty,a.ProjectCost,a.PreAppDTPK,a.ShortQty,a.DeclareQty, "
				   + " 		b.Qty as ReqQty,b.SendQty as SendedQty,a.Qty-a.SendQty TheSendQty,ts.Color "
				   + " 		FROM tItemAppDetail a "
				   + " 		left outer join tItemAppSendDetail ts on ts.RefPK = a.PK "
				   + " 		left outer join tItemReq b on a.ReqPK = b.PK "
				   + " 		where a.Qty-a.SendQty <> 0 and a.No = ? "
				   + " ) a "
				   + " left outer join tFixArea b on a.FixAreaPK = b.PK "
				   + " left outer join tIntProd c on a.IntProdPK = c.PK "
				   + " left outer join tItem d on a.ItemCode = d.Code "
				   + " left outer join tSupplier sp on d.SupplCode = sp.Code "
				   + " where 1=1 ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, new Object[]{custCode,no});
	}
	
	public Result doSendBatchForXml(String no,String whcode,String itemType1,
	        String custCode,Date sendDate,String remarks,String itemSendBatchNo,
	        String czybh, String xml, String manySendRsn,String delayReson,
	        String delayRemark, String delivType) {
	    
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLlglSendByPart_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, no);
			call.setString(2, whcode);
			call.setString(3, itemType1);
			call.setDate(4, new java.sql.Date(new Date().getTime()));
			call.setString(5, custCode);
			call.setString(6, czybh);
			call.setDate(7, new java.sql.Date(sendDate.getTime()));
			call.setString(8, remarks);
			call.setString(9, czybh);
			call.setString(10, "F");
			call.setString(11, itemSendBatchNo);
			call.setString(12, xml);
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.NVARCHAR);		
			call.setString(15, manySendRsn);
			call.setString(16, StringUtils.isBlank(delayReson)?"0":delayReson );
			call.setString(17, delayRemark);
			call.setString(18, delivType);
			call.execute();
			result.setCode(String.valueOf(call.getInt(13)));
			result.setInfo(call.getString(14));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	public boolean isSendPartNo(String no){
		
		String sql = " select 1 from tItemAppDetail where SendQty<>0 and No=? ";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{no});
		
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
	
	public Page<Map<String,Object>> getShortageJqGrid(Page<Map<String,Object>> page, String no,String custCode){
		String sql = " select * from ( "
				   + " 		Select a.Cost,a.PK,a.No,a.ReqPK,a.FixAreaPK,b.Descr FixAreaDescr,a.IntProdPK,c.Descr IntProdDescr, " 
				   + " 		a.ItemCode,d.Descr ItemDescr,sp.Code,sp.Descr SupplierDescr,a.Qty,a.SendQty,a.ReqQty,a.SendedQty,ConfirmedQty=ISNULL((SELECT sum(Qty) FROM tItemAppDetail i2, "
				   + "		tItemApp i1 WHERE i2.No=i1.No AND i2.ReqPk = a.ReqPk AND i1.Status='CONFIRMED' and i1.CustCode=?),0), "
		           + " 		a.Remarks,a.ProjectCost,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.actionLog "
				   + " 		from ("	 
				   + " 			select a.Cost,a.ProjectCost,a.PK,a.No,a.ReqPK,a.FixAreaPK,a.IntProdPK,a.ItemCode,a.Qty,a.SendQty,b.Qty as ReqQty,b.SendQty as SendedQty, "
				   + " 			a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
				   + " 			from tItemAppDetail a "
				   + " 			left outer join tItemReq b on a.ReqPK = b.PK "
				   + " 			where a.Qty-a.SendQty<>0 and a.No=? "
				   + " 		) a "
				   + " 		left outer join tFixArea b on a.FixAreaPK = b.PK "
				   + " 		left outer join tIntProd c on a.IntProdPK = c.PK "
				   + " 		left outer join tItem d on a.ItemCode = d.Code "
				   + " 		left outer join tSupplier sp on d.SupplCode = sp.Code ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, new Object[]{custCode,no});
	}
	
	public Result doShortage(String no,String czybh, String xml){
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLlglShort_forXml(?,?,?,?,?)}");
			call.setString(1, no);
			call.setString(2, czybh);
			call.setString(3, xml);
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);			
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
	
	public Result doSendMemo(String no,Date arriveDate,String delivType,String splRemarks,String czybh){
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLlglSendRemark_forProc(?,?,?,?,?,?,?)}");
			call.setString(1, no);
			call.setDate(2, arriveDate!=null?new java.sql.Date(arriveDate.getTime()):null);
			call.setString(3, splRemarks);
			call.setString(4, delivType);
			call.setString(5, czybh);
			call.registerOutParameter(6, Types.INTEGER);
			call.registerOutParameter(7, Types.NVARCHAR);			
			call.execute();
			result.setCode(String.valueOf(call.getInt(6)));
			result.setInfo(call.getString(7));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public boolean isExistSendBatch(String no,String sendBatchNo,String custCode){
		List<Object> params = new ArrayList<Object>();
		String sql = " select 1 from tItemApp where SendBatchNo=? and  no<>? and custcode=? ";
		params.add(sendBatchNo);
		params.add(no);
		params.add(custCode);
		List<Map<String,Object>> list = this.findBySql(sql, params.toArray());
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}

	public Page<Map<String,Object>> goUnCheckJqGrid(Page<Map<String,Object>> page, String itemType1, String itemType2, Date appDateFrom, Date appDateTo, String mainManCode){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select a.No,a.Type,s3.Note TypeDescr,a.ItemType1,i.Descr ItemType1Descr,a.ItemType2,i2.Descr ItemType2Descr, "
				   + " a.CustCode,c.Descr CustDescr,c.CustType,b.Address,b.DocumentNo,a.Status,s1.Note StatusDescr,a.OldNo, "
				   + " a.AppCZY,c1.ZWXM AppName,a.Date,a.ConfirmCZY,C2.ZWXM ConfirmName,a.ConfirmDate, "
				   + " a.SendCZY,c3.ZWXM SendName,a.SendDate,a.SendType,s2.Note SendTypeDescr,a.SupplCode,sp.Descr SupplDescr, "
				   + " a.PUNo,a.WHCode,w.Desc1 WareHouse,a.Remarks,a.ProjectMan,a.Phone,a.LastUpdate, "
				   + " a.LastUpdatedBy,a.Expired,a.ActionLog,a.DelivType,x.NOTE DelivTypeDescr,a.OtherCost,a.IsService,i2.MaterWorkType2,a.PreAppNo, "
				   + " a.Amount,a.ProjectAmount,c.CheckStatus,a.IsSetItem,s4.Note IsSetItemDescr,wco.DocumentNo WHCheckOutDocumentNo, "
				   + " a.SplStatus,s5.Note SplStatusDescr,a.ArriveDate,a.SplRemark,a.OtherCostAdj,a.PrintTimes,a.PrintCZY,c4.ZWXM PrintCZYDescr,a.PrintDate,b.SignDate,a.ArriveSplDate,e1.NameChi MainManDescr  "
				   + " from tItemApp a "
				   + " left outer join tItemType1 i on a.ItemType1=i.Code "
				   + " left outer join tItemType2 i2 on a.ItemType2=i2.Code "
				   + " left outer join tCustomer c on a.CustCode=c.Code "
				   + " left outer join tCustomer b on b.Code=a.CustCode "
				   + " left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='ITEMAPPSTATUS' "
				   + " left outer join tCZYBM c1 on a.AppCZY=c1.CZYBH "
				   + " left outer join tCZYBM c2 on a.ConfirmCZY=c2.CZYBH "
				   + " left outer join tCZYBM c3 on a.SendCZY=c3.CZYBH "
				   + " left outer join tCZYBM c4 on a.PrintCZY=c4.CZYBH "
				   + " left outer join tWareHouse w on a.WHCode=w.Code "
				   + " left outer join tXTDM  s2 on a.SendType=s2.CBM and s2.ID='ITEMAPPSENDTYPE' "
				   + " left outer join tXTDM  s3 on a.Type=s3.CBM and s3.ID='ITEMAPPTYPE' "
				   + " left outer join tXTDM  x on a.DelivType=x.CBM and x.ID='DELIVTYPE' "
				   + " left outer join tSupplier sp on a.SupplCode=sp.Code "
				   + " left outer join tXTDM s4 on a.IsSetItem=s4.CBM and s4.ID='YESNO' "
				   + " left outer join tWHCheckOut wco on a.WHCheckOutNo=wco.No "
				   + " left outer join tXTDM s5 on a.SplStatus=s5.CBM and s5.ID='APPSPLSTATUS' "
				   + " left  outer join  tCustStakeholder cs ON cs.CustCode=a.custCode and Role='34'  "
				   + " left outer join tEmployee e1  on e1.number= cs.EmpCode "
				   + " where a.Expired='F' and a.Status='OPEN' and a.ItemType1 in ("+itemType1+") ";
		
		if (StringUtils.isNotBlank(itemType2)) {
		    sql += " and a.ItemType2 = ? ";
		    params.add(itemType2);
		}
		
		if(appDateFrom != null){
			sql += " and a.Date >= ? ";
			params.add(DateUtil.startOfTheDay(appDateFrom));
		}
		if(appDateTo != null){
			sql += " and a.Date < ? ";
			params.add(DateUtil.addDate(appDateTo, 1));
		}
		if(StringUtils.isNotBlank(mainManCode)){
			sql += " and e1.number=? ";
			params.add(mainManCode);
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String,Object>> goConfirmReturnJqGrid(Page<Map<String,Object>> page, String itemType1, String itemType2, String mainManCode){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select a.No,a.Type,s3.Note TypeDescr,a.ItemType1,i.Descr ItemType1Descr,a.ItemType2,i2.Descr ItemType2Descr, "
				   + " a.CustCode,c.Descr CustDescr,c.CustType,b.Address,b.DocumentNo,a.Status,s1.Note StatusDescr,a.OldNo, "
				   + " a.AppCZY,c1.ZWXM AppName,a.Date,a.ConfirmCZY,C2.ZWXM ConfirmName,a.ConfirmDate, "
				   + " a.SendCZY,c3.ZWXM SendName,a.SendDate,a.SendType,s2.Note SendTypeDescr,a.SupplCode,sp.Descr SupplDescr, "
				   + " a.PUNo,a.WHCode,w.Desc1 WareHouse,a.Remarks,a.ProjectMan,a.Phone,a.LastUpdate, "
				   + " a.LastUpdatedBy,a.Expired,a.ActionLog,a.DelivType,x.NOTE DelivTypeDescr,a.OtherCost,a.IsService,i2.MaterWorkType2,a.PreAppNo, "
				   + " a.Amount,a.ProjectAmount,c.CheckStatus,a.IsSetItem,s4.Note IsSetItemDescr,wco.DocumentNo WHCheckOutDocumentNo, "
				   + " a.SplStatus,s5.Note SplStatusDescr,a.ArriveDate,a.SplRemark,a.OtherCostAdj,a.PrintTimes,a.PrintCZY,c4.ZWXM PrintCZYDescr,a.PrintDate,b.SignDate, " 
				   + " ArriveSplDate,e1.NameChi MainManDescr  "
				   + " from tItemApp a "
				   + " left outer join tItemType1 i on a.ItemType1=i.Code "
				   + " left outer join tItemType2 i2 on a.ItemType2=i2.Code "
				   + " left outer join tCustomer c on a.CustCode=c.Code "
				   + " left outer join tCustomer b on b.Code=a.CustCode "
				   + " left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='ITEMAPPSTATUS' "
				   + " left outer join tCZYBM c1 on a.AppCZY=c1.CZYBH "
				   + " left outer join tCZYBM c2 on a.ConfirmCZY=c2.CZYBH "
				   + " left outer join tCZYBM c3 on a.SendCZY=c3.CZYBH "
				   + " left outer join tCZYBM c4 on a.PrintCZY=c4.CZYBH "
				   + " left outer join tWareHouse w on a.WHCode=w.Code "
				   + " left outer join tXTDM  s2 on a.SendType=s2.CBM and s2.ID='ITEMAPPSENDTYPE' "
				   + " left outer join tXTDM  s3 on a.Type=s3.CBM and s3.ID='ITEMAPPTYPE' "
				   + " left outer join tXTDM  x on a.DelivType=x.CBM and x.ID='DELIVTYPE' "
				   + " left outer join tSupplier sp on a.SupplCode=sp.Code "
				   + " left outer join tXTDM s4 on a.IsSetItem=s4.CBM and s4.ID='YESNO' "
				   + " left outer join tWHCheckOut wco on a.WHCheckOutNo=wco.No "
				   + " left outer join tXTDM s5 on a.SplStatus=s5.CBM and s5.ID='APPSPLSTATUS' "
				   + " left  outer join  tCustStakeholder cs ON cs.CustCode=a.custCode and Role='34'  "
				   + " left outer join tEmployee e1  on e1.number= cs.EmpCode "
				   + " where a.Expired='F' and a.Status='CONRETURN' and a.ItemType1 in ("+itemType1+") ";
		
		if (StringUtils.isNotBlank(itemType2)) {
            sql += " and a.ItemType2 = ? ";
            params.add(itemType2);
        }
		
		if(StringUtils.isNotBlank(mainManCode)){
			sql += " and e1.number=? ";
			params.add(mainManCode);
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String,Object>> goUnReceiveBySplJqGrid(Page<Map<String,Object>> page, String itemType1, String itemType2,
	        String supplCode, Date confirmDateFrom, Date confirmDateTo, String mainManCode,String delivType) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select a.No,a.Type,s3.Note TypeDescr,a.ItemType1,i.Descr ItemType1Descr,a.ItemType2,i2.Descr ItemType2Descr, "
				   + " a.CustCode,c.Descr CustDescr,c.CustType,b.Address,b.DocumentNo,a.Status,s1.Note StatusDescr,a.OldNo, "
				   + " a.AppCZY,c1.ZWXM AppName,a.Date,a.ConfirmCZY,C2.ZWXM ConfirmName,a.ConfirmDate, "
				   + " a.SendCZY,c3.ZWXM SendName,a.SendDate,a.SendType,s2.Note SendTypeDescr,a.SupplCode,sp.Descr SupplDescr, "
				   + " a.PUNo,a.WHCode,w.Desc1 WareHouse,a.Remarks,a.ProjectMan,a.Phone,a.LastUpdate, "
				   + " a.LastUpdatedBy,a.Expired,a.ActionLog,a.DelivType,x.NOTE DelivTypeDescr,a.OtherCost,a.IsService,i2.MaterWorkType2,a.PreAppNo, "
				   + " a.Amount,a.ProjectAmount,c.CheckStatus,a.IsSetItem,s4.Note IsSetItemDescr,wco.DocumentNo WHCheckOutDocumentNo, "
				   + " a.SplStatus,s5.Note SplStatusDescr,a.ArriveDate,a.SplRemark,a.OtherCostAdj,a.PrintTimes,a.PrintCZY,c4.ZWXM PrintCZYDescr,a.PrintDate,b.SignDate,ArriveSplDate,"
				   + " e1.NameChi MainManDescr "
				   + " from tItemApp a "
				   + " left outer join tItemType1 i on a.ItemType1=i.Code "
				   + " left outer join tItemType2 i2 on a.ItemType2=i2.Code "
				   + " left outer join tCustomer c on a.CustCode=c.Code "
				   + " left outer join tCustomer b on b.Code=a.CustCode "
				   + " left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='ITEMAPPSTATUS' "
				   + " left outer join tCZYBM c1 on a.AppCZY=c1.CZYBH "
				   + " left outer join tCZYBM c2 on a.ConfirmCZY=c2.CZYBH "
				   + " left outer join tCZYBM c3 on a.SendCZY=c3.CZYBH "
				   + " left outer join tCZYBM c4 on a.PrintCZY=c4.CZYBH "
				   + " left outer join tWareHouse w on a.WHCode=w.Code "
				   + " left outer join tXTDM  s2 on a.SendType=s2.CBM and s2.ID='ITEMAPPSENDTYPE' "
				   + " left outer join tXTDM  s3 on a.Type=s3.CBM and s3.ID='ITEMAPPTYPE' "
				   + " left outer join tXTDM  x on a.DelivType=x.CBM and x.ID='DELIVTYPE' "
				   + " left outer join tSupplier sp on a.SupplCode=sp.Code "
				   + " left outer join tXTDM s4 on a.IsSetItem=s4.CBM and s4.ID='YESNO' "
				   + " left outer join tWHCheckOut wco on a.WHCheckOutNo=wco.No "
				   + " left outer join tXTDM s5 on a.SplStatus=s5.CBM and s5.ID='APPSPLSTATUS' "
				   + " left  outer join  tCustStakeholder cs ON cs.CustCode=a.custCode and Role='34'  "
				   + " left outer join tEmployee e1  on e1.number= cs.EmpCode "
				   + " where a.Expired='F' and a.Status='CONFIRMED' and a.SplStatus='0' and a.SendType='1' and exists (select 1 from tCZYBM where ZFBZ=0 and CZYLB='2' and EMNum=a.SupplCode) "
				   + " and a.ItemType1 in ("+itemType1+") ";

		if (StringUtils.isNotBlank(itemType2)) {
            sql += " and a.ItemType2 = ? ";
            params.add(itemType2);
        }
		
		if(StringUtils.isNotBlank(mainManCode)){
			sql += " and e1.number=? ";
			params.add(mainManCode);
		}
		if(confirmDateFrom != null){
			sql += " and a.ConfirmDate >= ? ";
			params.add(DateUtil.startOfTheDay(confirmDateFrom));
		}
		if(confirmDateTo != null){
			sql += " and a.ConfirmDate < ? ";
			params.add(DateUtil.addDate(confirmDateTo, 1));
		}
		if(StringUtils.isNotBlank(supplCode)){
			sql += " and a.SupplCode=? ";
			params.add(supplCode);
		}
		if(StringUtils.isNotBlank(supplCode)){
			sql += " and a.SupplCode=? ";
			params.add(supplCode);
		}
		if(StringUtils.isNotBlank(delivType)){
			sql += " and a.DelivType=? ";
			params.add(delivType);
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String,Object>> goUnSendBySplJqGrid(Page<Map<String,Object>> page, String itemType1, String itemType2,
	        String supplCode, Date arriveDateFrom, Date arriveDateTo, String mainManCode,String prjRegion,String region,String delivType,
	        Date notifySendDateFrom, Date notifySendDateTo) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select a.No,a.Type,s3.Note TypeDescr,a.ItemType1,i.Descr ItemType1Descr,a.ItemType2,i2.Descr ItemType2Descr, "
			       + " a.CustCode,c.Descr CustDescr,c.CustType,b.Address,b.DocumentNo,a.Status,s1.Note StatusDescr,a.OldNo, "
			       + " a.AppCZY,c1.ZWXM AppName,a.Date,a.ConfirmCZY,C2.ZWXM ConfirmName,a.ConfirmDate, "
			       + " a.SendCZY,c3.ZWXM SendName,a.SendDate,a.SendType,s2.Note SendTypeDescr,a.SupplCode,sp.Descr SupplDescr, "
			       + " a.PUNo,a.WHCode,w.Desc1 WareHouse,a.Remarks,a.ProjectMan,a.Phone,a.LastUpdate, "
			       + " a.LastUpdatedBy,a.Expired,a.ActionLog,a.DelivType,x.NOTE DelivTypeDescr,a.OtherCost,a.IsService,i2.MaterWorkType2,a.PreAppNo, "
			       + " a.Amount,a.ProjectAmount,c.CheckStatus,a.IsSetItem,s4.Note IsSetItemDescr,wco.DocumentNo WHCheckOutDocumentNo, "
			       + " a.SplStatus,s5.Note SplStatusDescr,a.ArriveDate,a.SplRemark,a.OtherCostAdj,a.PrintTimes,a.PrintCZY,c4.ZWXM PrintCZYDescr,a.PrintDate,b.SignDate,ArriveSplDate, "
			       + " e1.NameChi MainManDescr,a.followRemark,a.NotifySendDate,datediff(day,a.NotifySendDate,getdate()) WaitDays "
			       + " from tItemApp a "
			       + " left outer join tItemType1 i on a.ItemType1=i.Code "
			       + " left outer join tItemType2 i2 on a.ItemType2=i2.Code "
			       + " left outer join tCustomer c on a.CustCode=c.Code "
			       + " left outer join tCustomer b on b.Code=a.CustCode "
			       + " left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='ITEMAPPSTATUS' "
			       + " left outer join tCZYBM c1 on a.AppCZY=c1.CZYBH "
			       + " left outer join tCZYBM c2 on a.ConfirmCZY=c2.CZYBH "
			       + " left outer join tCZYBM c3 on a.SendCZY=c3.CZYBH "
			       + " left outer join tCZYBM c4 on a.PrintCZY=c4.CZYBH "
			       + " left outer join tWareHouse w on a.WHCode=w.Code "
			       + " left outer join tXTDM  s2 on a.SendType=s2.CBM and s2.ID='ITEMAPPSENDTYPE' "
			       + " left outer join tXTDM  s3 on a.Type=s3.CBM and s3.ID='ITEMAPPTYPE' "
			       + " left outer join tXTDM  x on a.DelivType=x.CBM and x.ID='DELIVTYPE' "
			       + " left outer join tSupplier sp on a.SupplCode=sp.Code "
			       + " left outer join tXTDM s4 on a.IsSetItem=s4.CBM and s4.ID='YESNO' "
			       + " left outer join tWHCheckOut wco on a.WHCheckOutNo=wco.No "
			       + " left outer join tXTDM s5 on a.SplStatus=s5.CBM and s5.ID='APPSPLSTATUS' "
			       + " left  outer join  tCustStakeholder cs ON cs.CustCode=a.custCode and Role='34' "
			       + " left outer join tEmployee e1  on e1.number= cs.EmpCode "
			       + " left join tBuilder g on c.BuilderCode=g.Code "
				   + " left join tRegion h on g.RegionCode=h.Code " 
			       + " where a.Expired='F' and a.Status='CONFIRMED' and a.SplStatus='2' and a.SendType='1' "
			       + " and exists (select 1 from tCZYBM where ZFBZ=0 and CZYLB='2' and EMNum=a.SupplCode) "
			       + " and a.ItemType1 in ("+itemType1+") and SplStatus not in ('6','7') ";
		
		if (StringUtils.isNotBlank(itemType2)) {
		    sql += " and a.ItemType2 = ? ";
		    params.add(itemType2);
		}
		
		if(StringUtils.isNotBlank(mainManCode)){
			sql += " and e1.number=? ";
			params.add(mainManCode);
		}
		if(arriveDateFrom != null){
			sql += " and a.ArriveDate >= ? ";
			params.add(DateUtil.startOfTheDay(arriveDateFrom));
		}
		if(arriveDateTo != null){
			sql += " and a.ArriveDate < ? ";
			params.add(DateUtil.addDate(arriveDateTo, 1));
		}
		if(notifySendDateFrom != null){
			sql += " and a.NotifySendDate >= ? ";
			params.add(DateUtil.startOfTheDay(notifySendDateFrom));
		}
		if(notifySendDateTo != null){
			sql += " and a.NotifySendDate < ? ";
			params.add(DateUtil.addDate(notifySendDateTo, 1));
		}
		if(StringUtils.isNotBlank(supplCode)){
			sql += " and a.SupplCode=? ";
			params.add(supplCode);
		}
		if(StringUtils.isNotBlank(region)){
			sql += " and  h.code in " + "('"+region.replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(prjRegion)){
			sql+="  and exists ( select  1 from tRegion2 a  where a.PrjRegionCode = ? and a.Code=g.regionCode2 )";
			params.add(prjRegion);
		}
		if(StringUtils.isNotBlank(delivType)){
			sql += " and a.DelivType=? ";
			params.add(delivType);
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String,Object>> goDetailQueryJqGrid(Page<Map<String,Object>> page, ItemApp itemApp){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select a.No,a.Type,s3.Note TypeDescr,a.ItemType1,i.Descr ItemType1Descr,a.ItemType2,i2.Descr ItemType2Descr, "
			       + " a.CustCode,c.Descr CustDescr,b.DocumentNo,b.Address,a.Status,s1.Note StatusDescr,a.OldNo, "
			       + " a.AppCZY,e1.NameChi AppName,a.Date,a.ConfirmCZY,e2.NameChi ConfirmName,a.ConfirmDate, "
			       + " a.SendCZY,e3.NameChi SendName,a.SendDate,a.SendType,s2.Note SendTypeDescr,a.SupplCode,sp.Descr SupplDescr, "
			       + " a.PUNo,a.WHCode,w.Desc1 WareHouse,a.Remarks,a.ProjectMan,a.Phone,a.LastUpdate, a.SplRemark, "
			       + " a.LastUpdatedBy,a.Expired,a.ActionLog,a.DelivType,x.NOTE DelivTypeDescr,a.IsService, "
			       + " case when isnull(ir.Qty, 0) <> 0 then (isnull(d.Qty, 0)+isnull(d.ShortQty, 0))/isnull(ir.Qty, 0)*isnull(ir.processcost, 0) else 0 end OtherCost,"
			       + " d.ReqPK,d.FixAreaPK,t1.Descr FixAreaDescr,d.IntProdPK,d.ItemCode,t2.Descr ItemDescr, "
			       + " case when a.Type = 'R' then -d.Qty else d.Qty end Qty, "
			       + " case when a.Type = 'R' then -d.SendQty else d.SendQty end SendQty,d.Cost, "/*,d.Remarks*/
			       + " round(case when a.Type = 'R' then -d.Qty else d.Qty end*d.Cost,2) CostAmount, "
			       + " d.AftQty,d.AftCost,w.Desc1 WHCodeDescr,d.Remarks ItemRemarks,t2.Uom,u.Descr UomDescr, "
			       + " isnull(ir.UnitPrice,0) UnitPrice, isnull(ir.UnitPrice,0)*case when a.Type = 'R' then -d.Qty else d.Qty end SaleAmount,tct.Desc1 custTypeDescr, "
			       + " (case when a.Type = 'R' then -d.SendQty else d.SendQty end)*isnull(t2.PerWeight, 0) allWeight,d.projectcost, "
			       + " x2.note IsCupboardDescr "
			       + " from tItemApp a "
			       + " left outer join tItemType1 i on a.ItemType1=i.Code "
			       + " left outer join tItemType2 i2 on a.ItemType2=i2.Code "
			       + " left outer join tCustomer c on a.CustCode=c.Code "
			       + " left outer join tCustomer b on b.Code=a.CustCode "
			       + " left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='ITEMAPPSTATUS' "
			       + " left outer join tEmployee e1 on a.AppCZY=e1.Number "
			       + " left outer join tEmployee e2 on a.ConfirmCZY=e2.Number "
			       + " left outer join tEmployee e3 on a.SendCZY=e3.Number "
			       + " left outer join tWareHouse w on a.WHCode=w.Code "
			       + " left outer join tXTDM  s2 on a.SendType=s2.CBM and s2.ID='ITEMAPPSENDTYPE' "
			       + " left outer join tXTDM  s3 on a.Type=s3.CBM and s3.ID='ITEMAPPTYPE' "
			       + " left outer join tXTDM  x on a.DelivType=x.CBM and x.ID='DELIVTYPE' "
			       + " left outer join tSupplier sp on a.SupplCode=sp.Code "
			       + " left outer join tItemAppDetail d on a.No = d.No "
			       + " left outer join tFixArea t1 on t1.PK = d.FixAreaPK "
			       + " left outer join tItem t2 on t2.Code = d.ItemCode "
			       + " left outer join tUom u on u.Code = t2.Uom "
			       + " left outer join tItemReq ir on ir.PK = d.ReqPK "
			       + " left join tCustType tct on tct.Code = c.Custtype "
			       + " left join tXtdm x2 on x2.id = 'YESNO' and x2.cbm = a.IsCupboard "
			       + " where 1=1 "
			       + " and a.ItemType1 in ("+itemApp.getItemType1()+") ";
		if(StringUtils.isNotBlank(itemApp.getStatus())){
			sql += " and a.Status in ('"+itemApp.getStatus().trim().replace(",", "','")+"')";
		}
		if("F".equals(itemApp.getExpired())){
			sql += " and a.Expired=? ";
			params.add(itemApp.getExpired());
		}
		if(StringUtils.isNotBlank(itemApp.getNo())){
			sql += " and a.No=? ";
			params.add(itemApp.getNo());
		}
		if(StringUtils.isNotBlank(itemApp.getCustCode())){
			sql += " and a.CustCode = ? ";
			params.add(itemApp.getCustCode());
		}
		if(StringUtils.isNotBlank(itemApp.getAppCzy())){
			sql += " and a.AppCZY=? ";
			params.add(itemApp.getAppCzy());
		}
		if(StringUtils.isNotBlank(itemApp.getItemType2())){
			sql += " and t2.ItemType2 in ('"+itemApp.getItemType2().trim().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(itemApp.getItemType3())){
			sql += " and t2.ItemType3 in ('"+itemApp.getItemType3().trim().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(itemApp.getDelivType())){
			sql += " and a.DelivType=? ";
			params.add(itemApp.getDelivType());
		}
		if(StringUtils.isNotBlank(itemApp.getType())){
			sql += " and a.Type=? ";
			params.add(itemApp.getType());
		}
		if(StringUtils.isNotBlank(itemApp.getWhcode())){
			sql += " and a.WHCode in ('"+itemApp.getWhcode().trim().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(itemApp.getSupplCode())){
			sql += " and a.SupplCode=? ";
			params.add(itemApp.getSupplCode());
		}
		if(StringUtils.isNotBlank(itemApp.getCustAddress())){
			sql += " and b.Address like ? ";
			params.add("%"+itemApp.getCustAddress()+"%");
		}
		if(itemApp.getDateFrom() != null){
			sql += " and a.Date >= ? ";
			params.add(DateUtil.startOfTheDay(itemApp.getDateFrom()));
		}
		if(itemApp.getDateTo() != null){
			sql += " and a.Date < ? ";
			params.add(DateUtil.addDate(itemApp.getDateTo(), 1));
		}
		if(itemApp.getSendDateFrom() != null){
			sql += " and a.SendDate >= ? ";
			params.add(DateUtil.startOfTheDay(itemApp.getSendDateFrom()));
		}
		if(itemApp.getSendDateTo() != null){
			sql += " and a.SendDate < ? ";
			params.add(DateUtil.addDate(itemApp.getSendDateTo(), 1));
		}
		if(itemApp.getConfirmDateFrom() != null){
			sql += " and a.ConfirmDate >= ? ";
			params.add(DateUtil.startOfTheDay(itemApp.getConfirmDateFrom()));
		}
		if(itemApp.getConfirmDateTo() != null){
			sql += " and a.ConfirmDate < ? ";
			params.add(DateUtil.addDate(itemApp.getConfirmDateTo(), 1));
		}
		if(StringUtils.isNotBlank(itemApp.getItemCode())){
			sql += " and d.ItemCode = ? ";
			params.add(itemApp.getItemCode());
		}
		if(StringUtils.isNotBlank(itemApp.getCustType())){
			sql += " and c.CustType in ('"+itemApp.getCustType().trim().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String,Object>> goJqGridLlglSendList(Page<Map<String,Object>> page, ItemApp itemApp){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select ia.CustCode,c.DocumentNo, c.Address,ias.No,ias.IANo,ias.Date,ia.ItemType1,it1.Descr ItemType1Descr, "
			       + " ia.ItemType2,it2.Descr ItemType2Descr,ia.Type,ias.WHCode,wh.Desc1 WHCodeDescr,x1.NOTE TypeDescr,ias.Remarks, "
			       + " ias.IsConfirm,ias.ConfirmDate,ias.ProjectManRemark,ias.ArriveAddress,ias.ConfirmMan,czy.ZWXM ConfirmManDescr, "
			       + " ias.ConfirmStatus,x2.Note ConfirmStatusDescr,ias.ConfirmReason,x3.Note ConfirmReasonDescr,spl.Descr SupplierDescr, "
			       + " isnull(round(f.SaleAmount,2),0) SaleAmount, isnull(round(f.SaleAmountAftDisc,2),0) SaleAmountAftDisc,ias.lastUpdate, "
			       + " cast(dbo.fGetEmpNameChi(ia.CustCode, '34') as nvarchar(100)) MainBusinessMan,e.NameChi BusinessManDescr "
			       + " from tItemAppSend ias "
			       + " left join tItemApp ia on ia.No=ias.IANo "
			       + " left outer join tSupplier spl on spl.code=ia.SupplCode "   /*增加供应商查询*/
			       + " left join tCustomer c on c.Code=ia.CustCode "
			       + " left join tItemType1 it1 on it1.Code=ia.ItemType1 "
			       + " left join tItemType2 it2 on it2.Code=ia.ItemType2 "
			       + " left join tWareHouse wh on wh.Code=ias.WHCode "
			       + " left join tXTDM x1 on x1.CBM=ia.Type and x1.ID='ITEMAPPTYPE' "
			       + " left join tXTDM x2 on x2.CBM=ias.ConfirmStatus and x2.ID='SENDCFMSTATUS' "
			       + " left join tXTDM x3 on x3.CBM=ias.ConfirmReason and x3.ID='APPSENDRSN' "
			       + " left join tCZYBM czy on ias.ConfirmMan=czy.CZYBH "
			       + " left join ( "
			       + "   select ias.No, "
			       + " 		sum(case when ia.Type = 'R' then -1 else 1 end * (isnull(ir.UnitPrice,0)*iasd.SendQty*case when ia.Type='R' and iasd.SendQty<0 then -1 else 1 end*ir.Markup/100 "
			       + "     	+ case when ir.Qty=0 then 0 else ir.ProcessCost*iasd.SendQty/ir.Qty end)) SaleAmount, "
			       + "   	sum(case when ia.Type = 'R' then -1 else 1 end * (isnull(ir.UnitPrice,0)*iasd.SendQty*case when ia.Type='R' and iasd.SendQty<0 then -1 else 1 end*ir.Markup/100 "
			       + " 		+ case when ir.Qty=0 then 0 else ir.ProcessCost*iasd.SendQty/ir.Qty end "
			       + "     - case when ir.Qty=0 then 0 else ir.DiscCost*iasd.SendQty/ir.Qty end)) SaleAmountAftDisc "
			       + "   from tItemAppSend ias "
			       + "   inner join tItemApp ia on ias.IANo=ia.No "
			       + "   inner join tItemAppSendDetail iasd on iasd.No=ias.No "
			       + "   inner join tItemAppDetail iad on iad.PK=iasd.RefPk "
			       + "   inner join tItemReq ir on ir.PK=iad.ReqPK "
			       + "   group by ias.No "
			       + " ) f on f.No=ias.No "
			       + " left join tEmployee e on c.BusinessMan=e.Number "
			       + " where 1=1 "
			       + " and ia.ItemType1 in ("+itemApp.getItemType1()+") ";
		if(StringUtils.isNotBlank(itemApp.getCustCode())){
			sql += " and ia.CustCode = ? ";
			params.add(itemApp.getCustCode());
		}
		/*增加档案号查询*/
		if (StringUtils.isNotBlank(itemApp.getCustDocumentNo())) {
			sql += " and c.DocumentNo=? ";
			params.add(itemApp.getCustDocumentNo());
		}
		if(StringUtils.isNotBlank(itemApp.getSendNo())){
			sql += " and ias.No = ? ";
			params.add(itemApp.getSendNo());
		}
		if(StringUtils.isNotBlank(itemApp.getNo())){
			sql += " and ias.IANo=? ";
			params.add(itemApp.getNo());
		}
		if(StringUtils.isNotBlank(itemApp.getSendCfmStatus())){
			sql += " and ias.confirmStatus = ? ";
			params.add(itemApp.getSendCfmStatus());
		}
		if(StringUtils.isNotBlank(itemApp.getWhcode())){
			sql += " and ias.WHCode in ('"+itemApp.getWhcode().trim().replace(",", "','")+"')";
		}
		if(itemApp.getSendDateFrom() != null){
			sql += " and ias.Date >= ? ";
			params.add(DateUtil.startOfTheDay(itemApp.getSendDateFrom()));
		}
		if(itemApp.getSendDateTo() != null){
			sql += " and ias.Date < ? ";
			params.add(DateUtil.addDate(itemApp.getSendDateTo(), 1));
		}
		if(StringUtils.isNotBlank(itemApp.getItemType2())){
			sql += " and ia.ItemType2 = ? ";
			params.add(itemApp.getItemType2().trim());
		}
		if(StringUtils.isNotBlank(itemApp.getSupplCode())){
			sql += " and ia.SupplCode=? ";
			params.add(itemApp.getSupplCode());
		}
		if(StringUtils.isNotBlank(itemApp.getManySendRsn())){
			sql += " and ias.ManySendRsn=? ";
			params.add(itemApp.getManySendRsn());
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String,Object>> goJqGridFhDetail(Page<Map<String,Object>> page, String fhNo){
		String sql = " select * from ( select  a.No,a.SendQty,b.ItemCode,c.descr  Itemdescr,d.descr ItemType1descr,u.Descr uomDescr,b.Remarks, "
		           + " a.Color,fa.Descr FixAreaDescr "
		           + " from tItemAppSendDetail a "
		           + " left outer join tItemAppDetail b on b.PK=a.RefPk "
		           + " left outer join  tItem c on c.code=b.ItemCode "
		           + " left outer join tItemtype1 d on  d.code=c.ItemType1 "
		           + " left outer join tUom u on u.code = c.uom "
		           + " left outer join tFixArea fa on b.FixAreaPK = fa.PK "
			       + " where a.no= ? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, new Object[]{fhNo});
	}
	
	public Result doSaveArriveConfirm(String no, String projectManRemark, String czybh){
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLlglSendConfirm_forProc(?,?,?,?,?)}");
			call.setString(1, no);
			call.setString(2, projectManRemark);
			call.setString(3, czybh);
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);			
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
	
	public Result getPhoneMessage(String module, String custCode, String no, String czybh){		
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pAddSendMessage_forProc(?,?,?,?,?,?,?)}");
			call.setString(1, module);
			call.setString(2, custCode);
			call.setString(3, no);
			call.setString(4, czybh);
			
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.registerOutParameter(7, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(5)));
			if(result.isSuccess()){
				result.setInfo(call.getString(7));
			}else{
				result.setInfo(call.getString(6));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public List<Map<String,Object>> getPhoneList(String custCode){
		String sql = " select case when len(Descr)=2 then left(rtrim(Descr),1)+'  '+right(rtrim(Descr),1)+'('+Mobile1+'):'+'客户' "
				   + " else rtrim(Descr)+'('+Mobile1+')：'+'客户' end Caption, "
				   + " Mobile1,1 IsCheck from tCustomer " 
				   + " where Code=? " 
				   + " union "
				   + " select case when len(NameChi)=2 then left(rtrim(NameChi),1)+'  '+right(rtrim(NameChi),1)+'('+a.Phone+'):'+c.Descr "
				   + " else NameChi+'('+Phone+'):'+c.Descr end, "
				   + " Phone, "
			 	   + " case when b.Role in ('00','20') then 1 else 0 end IsCheck from tEmployee a "
			 	   + " inner join tCustStakeholder b on b.EmpCode=a.Number and b.CustCode=? "
			 	   + " left outer join tRoll c on c.Code=b.Role ";
		return this.findBySql(sql, new Object[]{custCode,custCode});
	}
	
	public Result doSaveSendSMS(String no, String message, String czybh, String xml){		
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSaveSendSMS_forXml(?,?,?,?,?,?)}");
			call.setString(1, message);
			call.setString(2, no);
			call.setString(3, czybh);
			call.setString(4, xml);
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(5)));
			result.setInfo(call.getString(6));
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	public Page<Map<String,Object>> goJqGridPrintBatch(Page<Map<String,Object>> page, ItemApp itemApp){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select a.No,a.Type,s3.Note TypeDescr,a.ItemType1,i.Descr ItemType1Descr, "
				   + " a.CustCode,c.Descr CustDescr,b.Address,a.Status,s1.Note StatusDescr,a.OldNo, "
		           + " a.AppCZY,c1.ZWXM AppName,a.Date,a.ConfirmCZY,C2.ZWXM ConfirmName,a.ConfirmDate, "    
		           + " a.SendCZY,c3.ZWXM SendName,a.SendDate,a.SendType,s2.Note SendTypeDescr,a.SupplCode,sp.Descr SupplDescr, "
		           + " a.PUNo,a.WHCode,w.Desc1 WareHouse,a.Remarks,a.ProjectMan,a.Phone,a.LastUpdate, "
		           + " a.LastUpdatedBy,a.Expired,a.ActionLog,a.DelivType,x.NOTE DelivTypeDescr,a.OtherCost,a.PrintTimes,a.PrintCZY,c4.ZWXM PrintCZYDescr,a.PrintDate, "
		           + " w.IsManagePosi "
		           + " from tItemApp a "
		           + " left outer join tItemType1 i on a.ItemType1=i.Code "
		           + " left outer join tCustomer c on a.CustCode=c.Code "
		           + " left outer join tCustomer b on b.Code=a.CustCode "
		           + " left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='ITEMAPPSTATUS' "
		           + " left outer join tCZYBM c1 on a.AppCZY=c1.CZYBH "
		           + " left outer join tCZYBM c2 on a.ConfirmCZY=c2.CZYBH "
		           + " left outer join tCZYBM c3 on a.SendCZY=c3.CZYBH "
		           + " left outer join tCZYBM c4 on a.PrintCZY=c4.CZYBH "
		           + " left outer join tWareHouse w on a.WHCode=w.Code "
		           + " left outer join tXTDM  s2 on a.SendType=s2.CBM and s2.ID='ITEMAPPSENDTYPE' "
		           + " left outer join tXTDM  s3 on a.Type=s3.CBM and s3.ID='ITEMAPPTYPE' "
		           + " left outer join tXTDM  x on a.DelivType=x.CBM and x.ID='DELIVTYPE' "
		           + " left outer join tSupplier sp on a.SupplCode=sp.Code "
		           + " where 1=1 and a.Status<>'OPEN' and a.ItemType1 in ('"+itemApp.getItemType1().replace(",", "','")+"')";
		if(StringUtils.isNotBlank(itemApp.getStatus())){
			sql += " and a.Status in ('"+itemApp.getStatus().trim().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(itemApp.getExpired()) && "F".equals(itemApp.getExpired())){
			sql += " and a.Expired='F' ";
		}
		if(StringUtils.isNotBlank(itemApp.getNo())){
			sql += " and a.No = ? ";
			params.add(itemApp.getNo());
		}
		if(StringUtils.isNotBlank(itemApp.getWhcode())){
			sql += " and a.whcode = ? ";
			params.add(itemApp.getWhcode());
		}
		if(StringUtils.isNotBlank(itemApp.getCustCode())){
			sql += " and a.CustCode = ? ";
			params.add(itemApp.getCustCode());
		}
		if(StringUtils.isNotBlank(itemApp.getAppCzy())){
			sql += " and a.AppCZY=? ";
			params.add(itemApp.getAppCzy());
		}
		if(StringUtils.isNotBlank(itemApp.getDelivType())){
			sql += " and a.DelivType=? ";
			params.add(itemApp.getDelivType());
		}
		if(StringUtils.isNotBlank(itemApp.getType())){
			sql += " and a.Type=? ";
			params.add(itemApp.getType());
		}
		if(StringUtils.isNotBlank(itemApp.getCustAddress())){
			sql += " and b.Address like ? ";
			params.add("%"+itemApp.getCustAddress()+"%");
		}
		if(itemApp.getDateFrom() != null){
			sql += " and a.Date >= ? ";
			params.add(DateUtil.startOfTheDay(itemApp.getDateFrom()));
		}
		if(itemApp.getDateTo() != null){
			sql += " and a.Date < ? ";
			params.add(DateUtil.addDate(itemApp.getDateTo(), 1));
		}
		if(itemApp.getConfirmDateFrom() != null){
			sql += " and a.ConfirmDate >= ? ";
			params.add(DateUtil.startOfTheDay(itemApp.getConfirmDateFrom()));
		}
		if(itemApp.getConfirmDateTo() != null){
			sql += " and a.ConfirmDate < ? ";
			params.add(DateUtil.addDate(itemApp.getConfirmDateTo(), 1));
		}
		if(StringUtils.isNotBlank(itemApp.getRemarks())){
			sql += " and a.remarks like ? ";
			params.add("%"+itemApp.getRemarks()+"%");
		}
		if(StringUtils.isNotBlank(itemApp.getUseSupplierPlatform()) && "T".equals(itemApp.getUseSupplierPlatform())){
			sql += " and not exists (select 1 from tCZYBM where ZFBZ=0 and CZYLB='2' and EMNum=a.SupplCode) ";
		}
		if(StringUtils.isNotBlank(itemApp.getUnPrint()) && "T".equals(itemApp.getUnPrint())){
			sql += " and a.PrintTimes=0 ";
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public boolean getBeforeDoPrintZX(String no){
		String sql = " select a.ItemCode,a.FixAreaDescr,a.ItemTypeDescr,a.SqlDescr,a.Model,a.SizeDesc,a.UOMDescr,a.Qty,a.Remarks "
                   + " from ( "
                   + " 		SELECT fa.Descr FixAreaDescr,t1.PK,t2.NO,t2.CustCode,t1.ItemCode, "
                   + " 		it.ItemType2,case when it3.Descr is null or it3.Descr='' then ip.Descr else ip.Descr+'.'+it3.Descr end ItemTypeDescr,it.SqlCode,sc.Descr SqlDescr, "
                   + " 		it.Model,it.SizeDesc,it.Descr ITCodeDescr,it.UOM,u.Descr UOMDescr,case when t2.Status = 'SEND' then (t4.SendQty-t4.Qty) "
                   + " 		when t2.Status = 'CONFIRMED' or t2.Status= 'OPEN' then (t1.Qty+t4.SendQty-t4.Qty) end Qty, "
                   + " 		t1.Remarks "
                   + "  	FROM tItemAppDetail t1 "
                   + " 		LEFT OUTER JOIN tItemReq t4 on t4.PK = t1.ReqPk "
                   + " 		LEFT OUTER JOIN tItem it ON it.Code = t1.ItemCode "
                   + " 		LEFT OUTER JOIN tItemApp t2 ON t2.NO = t1.No "
                   + " 		LEFT OUTER JOIN tItemType2 ip ON ip.Code = it.ItemType2 "
                   + " 		left outer join tItemType3 it3 on it3.Code = it.ItemType3 "
                   + " 		LEFT OUTER JOIN tUOM u ON u.Code = it.UOM "
                   + " 		LEFT OUTER JOIN tBrand sc ON sc.Code = it.SqlCode "
                   + " 		LEFT OUTER JOIN tFixArea fa on fa.pk=t1.FixAreaPK "
                   + " 		WHERE t2.NO = ? "
                   + " ) a "
                   + " where a.qty > 0 ";

		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{no});

		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	public boolean getBeforeDoPrintWFH(String no, String checkFH, String whcode){
		List<Object> params = new ArrayList<Object>();
		String sql = " SELECT fa.Descr FixAreaDescr,t1.PK,t2.NO,t2.CustCode,t1.ItemCode, "
                   + " it.ItemType2,case when it3.Descr is null or it3.Descr='' then ip.Descr else ip.Descr+'.'+it3.Descr end ItemTypeDescr,it.SqlCode,sc.Descr SqlDescr,it.supplCode,spl.Descr SplCodeDescr, "
                   + " it.Model,it.SizeDesc,it.Descr ITCodeDescr,it.UOM,u.Descr UOMDescr, ";
		if("T".equals(checkFH)){
			sql += " t1.Qty+ISNULL(t1.ShortQty,0) Qty, ";
		}else{
			sql += " case when t2.Type='R' then -(t1.Qty-t1.SendQty) else t1.Qty-t1.SendQty end Qty, ";
		}
        sql += " t1.Remarks, "
             + " it.PerWeight*(t1.Qty-t1.SendQty) Weight, it.PerNum*(t1.Qty-t1.SendQty) Num, "
             + " case when it.PackageNum=1 or (t1.Qty-t1.SendQty)*it.PerNum<it.PackageNum then cast (cast((t1.Qty-t1.SendQty)*it.PerNum as float) as nvarchar(100)) "
             + " when (t1.Qty-t1.SendQty)*it.PerNum/it.PackageNum = cast((t1.Qty-t1.SendQty)*it.PerNum/it.PackageNum as int) then cast(cast((t1.Qty-t1.SendQty)*it.PerNum/it.PackageNum as int) as nvarchar(100))+'箱' "
             + " else cast(cast(floor((t1.Qty-t1.SendQty)*it.PerNum/it.PackageNum) as int) as nvarchar(100))+'箱' + cast(cast((t1.Qty-t1.SendQty)*it.PerNum-it.PackageNum*floor((t1.Qty-t1.SendQty)*it.PerNum/it.PackageNum) as float) as nvarchar(100))+'片' end NumDescr, "
             + " it.Volume,whp.Desc1 WHPDescr " 
             + " FROM tItemAppDetail t1 "
             + " inner join tItemApp ia on t1.No=ia.No "
             + " LEFT OUTER JOIN tItem it ON it.Code = t1.ItemCode "
             + " left outer join tsupplier spl on spl.code=it.supplcode "
             + " LEFT OUTER JOIN tItemApp t2 ON t2.NO = t1.No "
             + " LEFT OUTER JOIN tItemType2 ip ON ip.Code = it.ItemType2 "
             + " left outer join tItemType3 it3 on it3.Code = it.ItemType3 "
             + " LEFT OUTER JOIN tUOM u ON u.Code = it.UOM "
             + " LEFT OUTER JOIN tBrand sc ON sc.Code = it.SqlCode "
             + " LEFT OUTER JOIN tFixArea fa on fa.pk=t1.FixAreaPK "
             + " left outer join ( "
             + " 		select whp.WHCode,wpb.ITCode,max(whp.PK) WHPPk "
             + " 		from tWareHousePosi whp "
             + " 	 	inner join tWHPosiBal wpb on wpb.WHPPk=whp.PK "
             + " 		where wpb.QtyCal=(select max(b.QtyCal) from tWareHousePosi a inner join tWHPosiBal b on b.WHPPk=a.PK where a.WHCode=whp.WHCode and b.ITCode=wpb.ITCode) "
             + " 		group by whp.WHCode,wpb.ITCode "
             + " ) t4 on ";
		if(StringUtils.isNotBlank(whcode)){
			sql += "t4.WHCode=ia.WHCode and t4.ITCode=t1.ItemCode";
		}else{
			sql += "t4.WHCode=? and t4.ITCode=t1.ItemCode";
			params.add(whcode);
		}
		
        sql += " left outer join tWareHousePosi whp on whp.PK=t4.WHPPk "
             + " WHERE t2.NO = ? ";
		if("T".equals(checkFH)){
		}else{
			sql += " and t1.Qty-t1.SendQty<>0 ";
		}
		params.add(no);
		List<Map<String,Object>> list = this.findBySql(sql, params.toArray());

		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	public double getTotalQty(String custCode) {
		String sql = "select isnull(sum(case when a.Type='R' then Qty*-1 else Qty end),0) TotalQty"
                + " from tItemApp a,tItemAppdetail b"
                + " where a.No=b.No and a.CustCode=?"
                + " and a.Status in ('SEND', 'RETURN')";
		List<Map<String,Object>> list = this.findBySql(sql.toLowerCase(), new Object[]{custCode});
		if (list!=null && list.size()>0){
			return Double.parseDouble(String.valueOf(list.get(0).get("totalqty")));
		}
		return 0;
	}

	public Page<Map<String,Object>> goJqGridDishesSend(Page<Map<String,Object>> page, ItemApp itemApp) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( "
				   + " 		select tc.address,tia.date,tx1.NOTE statusDescr,ti.Descr itemCodeDescr,ti.size,ti.model,tiad.qty "
				   + " 		from tItemAppDetail tiad "
				   + " 		inner join tItemApp tia on tia.No=tiad.No "
				   + " 		left join tCustomer tc on tc.Code=tia.CustCode "
				   + " 		left join tXTDM tx1 on tx1.ID='ITEMAPPSTATUS' and tx1.CBM=tia.Status "
				   + " 		left join tItem ti on ti.Code=tiad.ItemCode "
				   + " 		left join tCustIntProg tcip on tcip.CustCode = tia.CustCode "
				   + " 		left join tCZYBM tczybm on tczybm.EMNum = tcip.TableSpl "
				   + " 		where tczybm.CZYBH=? and ti.ItemType3='620' and tia.Status in ('CONFIRMED','OPEN','CONRETURN','SEND') and tia.Type='S' ";
		params.add(itemApp.getCzybh());
		if(StringUtils.isNotBlank(itemApp.getCustAddress())){
			sql += " and tc.Address like ? ";
			params.add("%"+itemApp.getCustAddress()+"%");
		}
		if(itemApp.getDate() != null){
			sql += " and tia.Date>=? and tia.Date<=? ";
			params.add(DateUtil.startOfTheDay(itemApp.getDate()));
			params.add(DateUtil.endOfTheDay(itemApp.getDate()));
		}
		if(StringUtils.isNotBlank(itemApp.getStatus())){
			sql += " and tia.Status in ('"+itemApp.getStatus().trim().replace(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}

		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public String isExistFSGR(String custCode) {
		String sql = "select 1 isExistFSGR from tWorkCost a " +
				" left join tWorkCostDetail b  on b.no=a.No" +
				" where 1=1 and a.PayDate <> '' and a.PayDate is not null" +
				" and b.WorkType2='019' and CustCode= ? and b.Status <> '3' ";
		List<Map<String,Object>> list = this.findBySql(sql.toLowerCase(), new Object[]{custCode});
		if (list!=null && list.size()>0){
			return "true";
		}
		return "false";
	}
	/**
	 * 提交审核/验证成本控制规则
	 * @param custCode
	 * @param appNo
	 * @param m_umState
	 * @param czy
	 * @param isPayCtrl
	 * @return
	 */
	public Result doSubmitCheck(String custCode,String appNo,String m_umState,String czy,String isPayCtrl,String isCupboard,String itemType1,String itemType2,String custType,String chgNo){		
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLlglshtj(?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, appNo);
			call.setString(2, custCode);
			call.setString(3, itemType1);
			call.setString(4, isCupboard);
			call.setString(5, czy);
			call.setString(6, m_umState);
			call.setString(7, isPayCtrl);
			call.registerOutParameter(8, Types.NVARCHAR);
			call.registerOutParameter(9, Types.NVARCHAR);
			call.setString(10, itemType2);
			call.setString(11, custType);
			call.setString(12, chgNo);
			call.execute();
			result.setInfo(call.getString(8));
			result.setJson(call.getString(9));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	/**
	 * 供应商下单金额是否小于我们下单金额
	 * 
	 * @return
	 */
	public List<Map<String, Object>> checkCost(String no) {
		String sql = "select isnull(sum(cost*qty-cost*declareqty),0) result from tItemAppDetail where no=?";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] {no});
		return list;
	}
	/**
	 * 供应商下单金额是否小于我们下单金额直接通过
	 * 
	 * @param itemApp
	 */
	public void doSuplCheck(ItemApp itemApp) {
		String sql = " Update tItemApp set SplStatus = '5'," +
				"LastUpdate=getdate(),LastUpdatedBy=?,ActionLog='EDIT' where No=? ";
		this.executeUpdateBySql(sql, new Object[] {itemApp.getLastUpdatedBy(),itemApp.getNo() });
	}
	/**
	 * 是否已申请材料
	 * @param custCode
	 * @return
	 */
	public String isExistJZSD(String custCode, String itemType2) {
		String sql = "select 1 isExistJZSD from tItemType2 a left join tWorkType2 b on a.MaterWorkType2=b.Code "
				+"where exists ("
				+"	select 1 from tWorkCostDetail c where c.CustCode=? and c.WorkType2=b.DenyOfferWorkType2 and c.Status <> '3'" 
				+")"
				+"and a.Code=? ";
		List<Map<String, Object>> list = this.findBySql(sql.toLowerCase(),
				new Object[] { custCode,itemType2  });
		if (list != null && list.size() > 0) {
			return "true";
		}
		return "false";
	}
	/**
	 * 采购费用明细
	 * @param page
	 * @param puno
	 * @return
	 */
	public Page<Map<String,Object>> goJqGridPuFeeDetail(Page<Map<String,Object>> page, String puno){
		String sql = "select a.amount,a.supplFeeType,a.generateType,b.Descr supplFeeTypeDescr, "
			+"c.note generateTypeDescr,a.LastUpdate,a.LastUpdatedBy,a.ActionLog,a.Expired,a.pk,a.remarks,a.carryFloor "
			+"from tPurchaseFeeDetail a  "
			+"left join tSupplFeeType b on a.supplFeeType=b.Code  "
			+"left join tXTDM c on a.generateType=c.IBM and c.ID='PuFeeGenType' "
			+"where a.puno=? and a.generateType='1'";
		return this.findPageBySql(page, sql, new Object[]{puno});
	}
	/**
	 * 结算申请
	 * @param itemApp
	 * @return
	 */
	public Result doCheckApp(ItemApp itemApp){		
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCheckApp_forXml(?,?,?,?,?,?,?)}");
			call.setString(1, itemApp.getPuno());
			call.setDouble(2, itemApp.getSplAmount());
			call.setString(3, itemApp.getLastUpdatedBy());
			call.setString(4, itemApp.getPurchaseFeeDetailJson());
			call.registerOutParameter(5, Types.NVARCHAR);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.setString(7, itemApp.getM_umState());
			call.execute();
			result.setCode(String.valueOf(call.getInt(5)));
			result.setInfo(call.getString(6));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	/**
	 * 结算申请-材料明细
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> goJqGridItemDetail(Page<Map<String,Object>> page, ItemApp itemApp){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from (select a.ItemCode,b.Descr itemDescr,c.Descr fixAreaDescr,"
				+"a.Qty,d.Descr uomDescr,e.ProcessCost,a.Remarks "
                +"from tItemAppDetail a  "
                +"left join tItem b on a.ItemCode=b.Code "
                +"left join tFixArea c on a.FixAreaPK=c.PK "
                +"left join tUOM d on b.Uom=d.Code "
                +"left join tItemReq e on a.ReqPK=e.PK "
                +"where No=?";
		params.add(itemApp.getNo());
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.itemDescr ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}
	/**
	 * 退管管理-结算申请保存
	 * 
	 * @param itemApp
	 */
	public void doCheckAppR(ItemApp itemApp) {
		String sql = "update tPurchase set SplAmount=?,SplStatus='1',LastUpdate=getdate(),LastUpdatedBy=?,AppCheckDate=getdate(),OtherCost=? where No=?";
		this.executeUpdateBySql(sql, new Object[] {itemApp.getSplAmount(),itemApp.getLastUpdatedBy(),itemApp.getOtherCost(),itemApp.getPuno() });
	}
	
	/**
	 * 退管管理-结算申请提交审核 通过
	 * 
	 * @param itemApp
	 */
	public void doPassCheckAppR(ItemApp itemApp) {
		String sql = "update tPurchase set OtherCostAdj=?,SplStatus='2',ToCheckConfirmDate=getdate(),CheckConfirmDate=getdate(),CheckConfirmCZY=?,SYSConfirmRemarks='自动审核通过' where No=? ";
		this.executeUpdateBySql(sql, new Object[] {itemApp.getOtherCostAdj(),itemApp.getLastUpdatedBy(),itemApp.getPuno() });
	}
	
	/**
	 * 退管管理-结算申请提交审核 不通过
	 * 
	 * @param itemApp
	 */
	public void doNotPassCheckAppR(ItemApp itemApp) {
		String sql = "update tPurchase set SplStatus='4',ToCheckConfirmDate=getdate(),SYSConfirmRemarks=? where No=? ";
		this.executeUpdateBySql(sql, new Object[] {itemApp.getRemarks(),itemApp.getPuno() });
	}
	
	/**
	 * 存在下单数量为0的材料
	 * 
	 * @return
	 */
	public List<Map<String, Object>> hasZero(String no) {
		String sql = "select 1 from tItemAppDetail a where no=? and qty=0 ";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] {no});
		return list;
	}
	/**
	 * 供货管理--不能安装明细
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> goNotInstallDetailJqGrid(Page<Map<String,Object>> page, Customer customer){
		List<Object> list = new ArrayList<Object>();
		UserContext uc = UserContextHolder.getUserContext();
		String sql = " select * from (select x.Note IsCupboardDescr,b.Address,a.Date,a.Remarks "
				+"from tIntProgDetail a   "
				+"inner join tCustIntProg c on a.CustCode=c.CustCode "
				+"left join tXTDM x on a.IsCupboard = x.CBM and x.id='YESNO'  "
				+"left join tCustomer b on a.custcode=b.code  "
				+"where 1=1 and (( a.IsCupboard='0' and (c.IntSpl=? or c.DoorSpl=?) )or( a.IsCupboard='1' and c.CupSpl=? )) ";
		list.add(uc.getEmnum());
		list.add(uc.getEmnum());
		list.add(uc.getEmnum());
		if (customer.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(customer.getDateFrom());
		}
		if (customer.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(DateUtil.endOfTheDay(customer.getDateTo()));
		}
		if (StringUtils.isNotBlank(customer.getAddress())){
			sql += " and b.Address like ? ";
			list.add("%"+customer.getAddress()+"%");
		}
		
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.Date Desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 供货管理--补货明细
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> goIntReplenishDetailJqGrid(Page<Map<String,Object>> page, Customer customer){
		List<Object> list = new ArrayList<Object>();
		UserContext uc = UserContextHolder.getUserContext();
		String sql = " select * from (select b.Status,c.Address,x1.NOTE TypeDescr,a.Date,a.Remarks," 
				+"a.ArriveDate,d.No,d.Date AppDate,x2.NOTE AppStatus,d.SendDate "
				+"from tIntReplenishDT a  "
				+"inner join tIntReplenish b on a.No=b.No "
				+"left join tCustomer c on b.CustCode=c.Code "
				+"left join tItemApp d on a.PK=d.IntRepPK and d.ItemType1='JC' "
				+"left join tXTDM x1 on x1.ID='IntRepType' and a.Type=x1.CBM "
				+"left join tXTDM x2 on x2.ID='ITEMAPPSTATUS' and d.Status=x2.CBM "
				+"where b.Status in ('2','3','4','5') and a.IntSpl=? ";
		list.add(uc.getEmnum());
		if (customer.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(customer.getDateFrom());
		}
		if (customer.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(DateUtil.endOfTheDay(customer.getDateTo()));
		}
		if (StringUtils.isNotBlank(customer.getAddress())){
			sql += " and c.Address like ? ";
			list.add("%"+customer.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(customer.getStatus())){
			sql += " and b.Status in ("+SqlUtil.resetStatus(customer.getStatus())+")";
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.Date Desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 仓库收货
	 * @param itemApp
	 * @return
	 */
	public Result doWhReceive(ItemApp itemApp){		
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pItemAppWhReceive(?,?,?,?,?,?)}");
			call.setString(1, itemApp.getNo());
			call.setString(2, itemApp.getWhcode());
			call.setString(3, itemApp.getRemarks());
			call.setString(4, itemApp.getLastUpdatedBy());
			call.registerOutParameter(5, Types.NVARCHAR);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(5)));
			result.setInfo(call.getString(6));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	/**
	 * 加工单数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> hasCutNum(String no) {
		String sql = "select isnull(sum(case when b.Status in('1','2','3') then 1 else 0 end),0)submitNum, "
					+"isnull(sum(case when b.Status='0' then 1 else 0 end),0)appNum "
					+"from tCutCheckOutDetail a  "
					+"inner join tCutCheckOut b on a.No=b.No  "
					+"where IANo=? ";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] {no});
		return list;
	}
	
	/**
	 * 是否存在未加工完成的领料明细
	 * 
	 * @return
	 */
	public List<Map<String, Object>> isComplete(String no,String pks) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select 1 "	
					+"from tCutCheckOutDetail a left join tCutCheckOut b on a.no=b.no "
					+"where a.IsComplete='0' and b.Status<>'4' ";
		if(StringUtils.isNotBlank(no)){
			sql+=" and a.IANo=? ";
			list.add(no);
		}
		if(StringUtils.isNotBlank(pks)){
			sql+=" and a.RefPK in("+SqlUtil.resetIntStatus(pks)+")";
		}
		return this.findBySql(sql,list.toArray());
	}

	public String isMaterialSendJob(ItemApp ia) {
		String sql = "select 1 from tItemApp where NotifySendDate is not null and No=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{ia.getNo().trim()});
		
		if (list!=null && list.size()>0){
			return "true";
		}
		return "false";
	}
	
	/**
	 * 获取期数和差额
	 * @param no
	 * @param custCode
	 * @return
	 */
	public Map<String, Object> getBalance(String no,String custCode) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select c.PayNum,d.ShouldBalance " 
					+"from ( "
					+"	select  in2_c.ItemType1,in2_c.ItemType2,in2_c.ItemType3,in2_a.no  "
					+"	from (  "
					+"	  select min(PK) PK,in3_a.No  "
					+"	  from tItemAppDetail in3_a  "
					+"	  group by in3_a.No  "
					+"	) in2_a  "
					+"	inner join tItemAppDetail in2_b on in2_a.PK = in2_b.PK  "
					+"	inner join tItem in2_c on in2_c.Code = in2_b.ItemCode  "
					+"	where in2_a.No = ?  "
					+")a   "
					+"inner join tConfItemTypeDt b on a.ItemType2 = b.ItemType2  "
					+"inner join tItemSendNodeDetail c on b.ConfItemType = c.ConfItemType  "
					+"outer apply(select dbo.fGetShouldBanlanceByPayNum(?,c.PayNum,default) ShouldBalance )d  "
					+"inner join tItemSendNode e on e.Code = c.Code "
					+"left join tCustomer f on f.Code = ? "
					+"left join tCustType g on g.Code = f.CustType "
					+"where (b.ItemType3 is null or b.ItemType3 = '' or a.ItemType3 is null or a.ItemType3 = '' or a.ItemType3 = b.ItemType3) "
					+"and c.PayNum > 0 and d.ShouldBalance>0  and g.WorkerClassify = e.WorkerClassify and e.Type = '2' ";
		list.add(no);
		list.add(custCode);
		list.add(custCode);
		List<Map<String, Object>> resultList = this.findBySql(sql,list.toArray());
		if(resultList != null && resultList.size() > 0){
			return resultList.get(0);
		}
		return null;
	}
}

