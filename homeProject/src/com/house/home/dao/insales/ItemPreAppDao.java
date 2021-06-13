package com.house.home.dao.insales;

import java.sql.CallableStatement;
import java.sql.Connection;
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
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.ItemPreApp;
import com.house.home.entity.project.ItemPreMeasure;

@SuppressWarnings("serial")
@Repository
public class ItemPreAppDao extends BaseDao {

	/**
	 * ItemPreApp分页信息
	 * 
	 * @param page
	 * @param itemPreApp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPreApp itemPreApp) {
		List<Object> list = new ArrayList<Object>();

		String sql = "SELECT a.*,b.Address,c.Descr itemType1Descr,d.NOTE statusDescr,f.note endCodeDescr,z.Descr itemType2Descr, "
				+"CASE WHEN e.conreturncount>0 THEN '1' ELSE '0' END mark,asd.NOTE appStatusDescr, "
				+" case when a.AppCzy = ? then '1' else '0' end IsPrjManApp, "
				+" case when a.Status in ('1','2','3','6') then null " 
				+" when exists(select 1 from tItemApp in_a where PreAppNo = a.No and (in_a.Status = 'CONRETURN' or in_a.Status = 'WAITCON') ) then '审核不通过' "
				+" when exists(select 1 from tItemApp in_a where PreAppNo = a.No and in_a.Status = 'CANCEL' )  then '部分取消' "
				+" when exists(select 1 from tItemApp in_a where PreAppNo = a.No and in_a.Status = 'SEND' )  then '全部发货' "
				+" else '待发货'  end ItemPreAppStatusTipDescr "
				+"  FROM tItemPreApp a  "
			+"INNER JOIN tCustomer b on a.CustCode=b.Code "
			+"LEFT JOIN tItemType1 c ON a.ItemType1=c.Code "
			+"OUTER APPLY(" 
			+" select top(1) x1.note " 
			+" from tItemApp ia " 
			+" left join tXTDM x1 on ia.Status=x1.CBM and x1.ID='ITEMAPPSTATUS' " 
			+" where a.No=ia.PreAppNo  and ia.Status in('CONRETURN','WAITCON') " 
			+")asd "
			+"LEFT JOIN txtdm f ON a.endCode=f.cbm and f.id='PREAPPENDCODE' "
			+"LEFT JOIN txtdm d ON a.Status=d.CBM AND d.ID='PREAPPSTATUS' " 
			+"OUTER APPLY (select TOP(1) * from tItemPreAppDetail e WHERE e.no = a.no ) x "
			+"outer apply (select count(*) conreturncount from  titemapp q where q.preappno =a.no and q.status='conreturn') e "
			+"left JOIN tItem y ON x.ItemCode=y.Code "
			+"left JOIN tItemType2 z ON z.Code=y.ItemType2"
			+" where a.status<>'6' ";
		list.add(itemPreApp.getAppCzy());
		
    	if (StringUtils.isNotBlank(itemPreApp.getNo())) {
			sql += " and a.No=? ";
			list.add(itemPreApp.getNo());
		}
    	if (StringUtils.isNotBlank(itemPreApp.getAddress())) {
			sql += " and b.address like ? ";
			list.add("%"+itemPreApp.getAddress()+"%");
		}
    	if (StringUtils.isNotBlank(itemPreApp.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(itemPreApp.getCustCode());
		}
    	if (StringUtils.isNotBlank(itemPreApp.getIsSetItem())) {
			sql += " and a.IsSetItem=? ";
			list.add(itemPreApp.getIsSetItem());
		}
    	if (StringUtils.isNotBlank(itemPreApp.getStatus())) {
			sql += " and a.Status=? ";
			list.add(itemPreApp.getStatus());
		}
    	if (StringUtils.isNotBlank(itemPreApp.getAppCzy())) {
			sql += " and ( b.ProjectMan = ? ";
			list.add(itemPreApp.getAppCzy());
			if("1".equals(itemPreApp.getSaleIndependence())){
				sql += " or ( b.OldCode is not null and b.OldCode <> '' and exists ( "
					 + " select 1 from tCustomer in_a where in_a.Code = b.OldCode and in_a.Status = '4' and in_a.ProjectMan=? "
					 + " )) ";
				list.add(itemPreApp.getAppCzy());
			}
			sql += " ) ";
		}
    	if (itemPreApp.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(itemPreApp.getDateFrom());
		}
		if (itemPreApp.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(itemPreApp.getDateTo());
		}
    	if (StringUtils.isNotBlank(itemPreApp.getConfirmCzy())) {
			sql += " and a.ConfirmCZY=? ";
			list.add(itemPreApp.getConfirmCzy());
		}
    	if (itemPreApp.getDateFrom() != null){
			sql += " and a.ConfirmDate>= ? ";
			list.add(itemPreApp.getDateFrom());
		}
		if (itemPreApp.getDateTo() != null){
			sql += " and a.ConfirmDate<= ? ";
			list.add(itemPreApp.getDateTo());
		}
    	if (StringUtils.isNotBlank(itemPreApp.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(itemPreApp.getRemarks());
		}
    	if (itemPreApp.getDateFrom() != null){
			sql += " and a.LastUpdate>= ? ";
			list.add(itemPreApp.getDateFrom());
		}
		if (itemPreApp.getDateTo() != null){
			sql += " and a.LastUpdate<= ? ";
			list.add(itemPreApp.getDateTo());
		}
    	if (StringUtils.isNotBlank(itemPreApp.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemPreApp.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemPreApp.getExpired()) || "F".equals(itemPreApp.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemPreApp.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemPreApp.getActionLog());
		}
    	if (StringUtils.isNotBlank(itemPreApp.getItemType1())) {
    		if("dealing".equals(itemPreApp.getItemType1())){
    			sql += " and b.Status = '4'  " 
    				+" and (a.Status in ('1','2','3','4') or  exists (select 1 from  tItemApp where PreAppNo = a.No and Status not in('SEND', 'CANCEL'))) ";
    		}else{
    			sql += " and a.ItemType1=? ";
    			list.add(itemPreApp.getItemType1());
    		}
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**添加预领料
	 * @param itemPreApp
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result saveForProc(ItemPreApp itemPreApp, String xml) {
		Assert.notNull(itemPreApp);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pYllgl_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemPreApp.getM_umState());
			call.setString(2, itemPreApp.getOldStatus());
			call.setString(3, itemPreApp.getNo());
			call.setString(4, itemPreApp.getCustCode());
			call.setString(5, itemPreApp.getItemType1());
			call.setString(6, itemPreApp.getIsSetItem());
			call.setString(7, itemPreApp.getStatus());
			call.setString(8, itemPreApp.getAppCzy());
			call.setTimestamp(9, itemPreApp.getDate()==null?null:new Timestamp(
					itemPreApp.getDate().getTime()));
			call.setString(10, itemPreApp.getConfirmCzy());
			call.setTimestamp(11, itemPreApp.getConfirmDate()==null?null:new Timestamp(
					itemPreApp.getConfirmDate().getTime()));
			call.setString(12, itemPreApp.getRemarks());
			call.setString(13, itemPreApp.getExpired());
			call.setString(14, itemPreApp.getPhotoNames());
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.NVARCHAR);
			call.setString(17, xml);
			call.setString(18, itemPreApp.getFromInfoAdd());
			if(StringUtils.isBlank(itemPreApp.getFromInfoAdd()) || "0".equals(itemPreApp.getFromInfoAdd()))  itemPreApp.setInfoPk(0);
			call.setInt(19, itemPreApp.getInfoPk());
			itemPreApp.setItemConfCheck("0");//由于APP还没更新所以不检查
			call.setString(20, itemPreApp.getItemConfCheck());
			call.setString(21, itemPreApp.getWorkerApp());
			call.setString(22, itemPreApp.getConfirmRemark());
			call.execute();
			result.setCode(String.valueOf(call.getInt(15)));
			result.setInfo(call.getString(16));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	/**check预领料
	 * @param itemPreApp
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result checkForProc(ItemPreApp itemPreApp, String xml) {
		Assert.notNull(itemPreApp);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pYllgl_check(?,?,?,?,?)}");
			call.setString(1, itemPreApp.getCustCode());
			call.setString(2, itemPreApp.getItemType1());
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.setString(5, xml);
			call.execute();
			result.setCode(String.valueOf(call.getInt(3)));
			result.setInfo(call.getString(4));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	//20171011领料预申请管理
	public Page<Map<String,Object>> findItemPreAppManageListPageBySql(Page<Map<String,Object>> page, ItemPreApp itemPreApp) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from ( select  a.no, a.custCode,b.DocumentNo,a.ItemType1,c.Descr ItemType1Descr,a.IssetItem,x1.note IssetItemdDescr,x2.note xtdmStatus,  "
		           + " a.Status,a.AppCZy,d.ZWXM  APPCZyDescr,Date,a.ConfirmCZY,e.ZWXM ConfirmCZYDescr, a.ConfirmDate, a.ConfirmRemark, "
		           + " a.remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,b.Address, t.phone,b1.Desc2 Dept1Desc, b2.Desc2 Dept2Desc,b3.Desc2 Dept3Desc,a.EndCode,x3.note EndCodeDesc, "
		           + " ( SELECT in_c.Desc1 FROM tItemPreAppDetail in_a,tItem in_b,tWareHouse in_c "
		           + " WHERE in_a.ItemCode = in_b.Code AND in_b.WHCode = in_c.Code "
		           + " AND in_a.PK IN ( SELECT MIN(PK) PK FROM tItemPreAppDetail in_a,tItem in_b,tWareHouse in_c WHERE in_a.ItemCode = in_b.Code AND in_b.WHCode = in_c.Code AND in_a.No = a.No ) "
		           + " ) WHDescr , " 
		           + " ( case when not exists ( select 1 from   tItemPreMeasure ipm where  ipm.PreAppNo = a.no )or  not exists ( select 1 from   tItemPreMeasure ipm  "
		           + "  where  ipm.PreAppNo = a.no and ipm.Status <>'5') then  '无测量'   "
		           + "  when  exists ( select 1 from   tItemPreMeasure ipm  where  ipm.PreAppNo = a.no and ipm.Status in('1','2','3','7') ) then '测量中' "
		           + "  else '测量完成'  end ) MeasureStatus,f.Desc1 CustTypeDescr, b.SignDate,e1.NameChi MainManDescr,b.area,a.WorkerCode,w.NameChi WorkerName,h.descr regionDescr "
		           + " from tItempreApp a  "
		           + " left outer join tCustomer  b on b.code=a.CustCode  "
		           + " left outer join tItemType1 c on c.code=a.ItemType1 "
		           + " left outer join tXTDM  x1 on x1.cbm=a.IssetItem and x1.id='YESNO' "
		           + " left outer join tXtdm  x2 on x2.cbm=a.Status  and   x2.id='PREAPPSTATUS' "
		           + " left outer join tCZYBM d on d.CZYBH=a.AppCZY "
		           + " left outer join tCZYBM  e  on e.CZYBH=a.ConfirmCZY "
		           + " left outer join tEmployee t  on t.number= a.AppCZy "
		           + " left outer join tDepartment1 b1 on t.Department1=b1.Code "
		           + " left outer join tDepartment2 b2 on t.Department2=b2.Code "
		           + " left outer join tDepartment3 b3 on t.Department3=b3.Code "
		           + " left outer join tXTDM x3 on x3.cbm=a.EndCode and  x3.id = 'PREAPPENDCODE'   "
		           + " left join tCusttype f on f.code=b.CustType "
		           + " left  outer join  tCustStakeholder cs ON cs.CustCode=a.custCode and Role='34'  "
		           + " left outer join tEmployee e1  on e1.number= cs.EmpCode "
		           + " left join tWorker w on w.Code = a.WorkerCode "// 增加左链接工人信息表 增加‘工人’字段 --add by zb
		           + " left join tBuilder g on b.BuilderCode=g.Code "
				   + " left join tRegion h on g.RegionCode=h.Code " 
		           + " where 1=1 and a.itemType1 in ('"+itemPreApp.getItemRight().replace(",", "','")+"')";
    	if (StringUtils.isNotBlank(itemPreApp.getNo())) {
			sql += " and a.No=? ";
			list.add(itemPreApp.getNo());
		}
    	if (StringUtils.isNotBlank(itemPreApp.getDepartment1())) {
			sql += " and t.Department1=? ";
			list.add(itemPreApp.getDepartment1());
		}
    	if (StringUtils.isNotBlank(itemPreApp.getAppCzy())){
    		sql += " and a.AppCZY=? ";
    		list.add(itemPreApp.getAppCzy());
    	}
    	if (StringUtils.isNotBlank(itemPreApp.getConfirmCzy())){
    		sql += " and ( a.ConfirmCZY=? or a.ConfirmCZY='' or a.ConfirmCZY is null ) ";
    		list.add(itemPreApp.getConfirmCzy());
    	}
    	if (StringUtils.isNotBlank(itemPreApp.getAddress())) {
			sql += " and b.address like ? ";
			list.add("%"+itemPreApp.getAddress()+"%");
		}
    	if (StringUtils.isNotBlank(itemPreApp.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(itemPreApp.getItemType1());
		}
    	if (itemPreApp.getAppDateFrom()!=null) {
			sql += " and cast(a.Date as date) >= ? ";
			list.add(itemPreApp.getAppDateFrom());
		}
    	if (itemPreApp.getAppDateTo()!=null) {
			sql += " and cast(a.Date as date) <= ? ";
			list.add(itemPreApp.getAppDateTo());
		}
    	if (itemPreApp.getConfirmDateFrom()!=null) {
			sql += " and cast(a.ConfirmDate as date) >= ? ";
			list.add(itemPreApp.getConfirmDateFrom());
		}
    	if (itemPreApp.getConfirmDateTo()!=null) {
			sql += " and cast(a.ConfirmDate as date) <= ? ";
			list.add(itemPreApp.getConfirmDateTo());
		}
    	if(StringUtils.isNotBlank(itemPreApp.getDepartment2())){
    		sql += " and b2.Code in ('"+itemPreApp.getDepartment2().replace(",", "','")+"')";
    	}
    	if(StringUtils.isNotBlank(itemPreApp.getStatus())){
    		sql += " and a.Status in ('"+itemPreApp.getStatus().replace(",", "','")+"')";
    	}
    	if(StringUtils.isNotBlank(itemPreApp.getMainManCode())){
    		sql += " and exists (select 1 from tCustStakeholder where CustCode=a.custCode and Role='34' and EmpCode=? ) ";
    		list.add(itemPreApp.getMainManCode());
    	}
    	if(StringUtils.isNotBlank(itemPreApp.getRegion())){
			sql += " and  h.code in " + "('"+itemPreApp.getRegion().replace(",", "','" )+ "')";
		}
    	if(StringUtils.isNotBlank(itemPreApp.getPrjRegion())){
			sql+="  and exists ( select  1 from tRegion2 a  where a.PrjRegionCode = ? and a.Code=g.regionCode2 )";
			list.add(itemPreApp.getPrjRegion());
		}
    	if(StringUtils.isNotBlank(itemPreApp.getCustType())){
			sql += " and  b.CustType in " + "('"+itemPreApp.getCustType().replace(",", "','" )+ "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate DESC";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String,Object>> getApplyListByNo(Page<Map<String,Object>> page,String no,String supplCode){
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from ( SELECT a.PK ,a.NO ,a.ItemCode ,b.Descr ItemDescr ,a.Qty ,u.Descr UnitDescr ,a.ReqPK ,a.LastUpdate ,a.LastUpdatedBy ,a.Expired ,a.ActionLog ,a.Remarks ,fa.Descr FixAreaDescr ,ISNULL(c.OrderQty, 0) OrderQty ,c.No ItemAppNo ,t1.note ItemAppStatus ,sl.Descr SupplierDescr "
					+" FROM   tItemPreAppDetail a "
					+" LEFT OUTER JOIN tItem b ON b.code = a.ItemCode "
					+" LEFT OUTER JOIN tUOM u ON u.Code = b.UOM "
					+" LEFT OUTER JOIN tItemReq ir ON ir.PK = a.ReqPK "
					+" LEFT OUTER JOIN tFixArea fa ON fa.pk = ir.FixAreaPK "
					+" LEFT OUTER JOIN tSupplier sl ON sl.Code = b.supplcode "
					+" LEFT JOIN ( SELECT  iad.PreAppDTPK , ia.status ,iad.no ,SUM(iad.Qty) OrderQty FROM    tItemApp ia INNER JOIN tItemAppDetail iad ON ia.No = iad.No GROUP BY iad.PreAppDTPK ,ia.status ,iad.no) c ON a.PK = c.PreAppDTPK "
					+" LEFT OUTER JOIN txtdm t1 ON t1.CBM = c.Status AND id = 'ITEMAPPSTATUS' "
					+" WHERE 1=1";
		if(StringUtils.isNotBlank(no)){
			sql += " and a.No=? ";
			list.add(no);
		}
		if(StringUtils.isNotBlank(supplCode)){
			sql += " and b.supplCode=? ";
			list.add(supplCode);
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page,sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getItemPreAppPhotoListByNo(Page<Map<String,Object>> page, ItemPreApp itemPreApp){
		String sql = " SELECT c.Descr itemType1Descr,b.PhotoName "
					+" FROM dbo.tItemPreApp a "
					+" LEFT JOIN dbo.tItemPreAppPhoto b ON a.No=b.No "
					+" LEFT JOIN dbo.tItemType1 c ON a.ItemType1 = c.Code "
					+" WHERE b.No is not null ";
		List<Object> list = new ArrayList<Object>();
		if(StringUtils.isNotBlank(itemPreApp.getNo())){
			sql += " AND a.No=? ";
			list.add(itemPreApp.getNo());
		}
		return this.findPageBySql(page, sql,list.toArray());
	}
	public Page<Map<String,Object>> getMeasureByCondition(Page<Map<String,Object>> page,String no,Integer pk){
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( SELECT a.PK ,a.SupplCode ,a.Status ,a.Remarks ,a.PreAppNo ,a.AppCZY ,a.Date ,a.ConfirmCZY ,a.ConfirmDate ,a.LastUpdatedBy , "
					+" a.Expired ,a.ActionLog ,a.LastUpdate ,a.MeasureRemark ,b.Descr SupplerDescr ,X1.NOTE StatusDescr ,e1.ZWXM AppCZYDescr , "
					+" e2.ZWXM ConfirmCZYDescr ,a.RecvDate ,a.CompleteDate ,a.CompleteCZY ,e3.ZWXM CompleteCZYDescr,a.DelayReson,x2.NOTE DelayResonDescr,a.cancelRemark "
					+" FROM   tItemPreMeasure a "
					+" LEFT OUTER JOIN tSupplier b ON b.Code = a.SupplCode "
					+" LEFT OUTER JOIN tXTDM x1 ON x1.CBM = a.Status AND X1.ID = 'MEASURESTATUS' "
					+" LEFT OUTER JOIN tCZYBM e1 ON e1.CZYBH = a.AppCZY "
					+" LEFT OUTER JOIN tCZYBM e2 ON e2.CZYBH = a.ConfirmCZY "
					+" LEFT OUTER JOIN tCZYBM e3 ON e3.CZYBH = a.CompleteCZY "
					+" LEFT OUTER JOIN tXTDM x2 ON x2.CBM = a.DelayReson AND x2.ID='APPDELAYRESON' "
					+" WHERE 1=1 ";
		if(StringUtils.isNotBlank(no)){
			sql +=" and a.PreAppNo = ? ";
			list.add(no);
		}
		if(pk!=null && pk > 0){
			sql +=" and a.pk = ? ";
			list.add(pk);
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page,sql, list.toArray());
	}

	public Result updatePreItemAppStatus(ItemPreApp itemPreApp){
		Assert.notNull(itemPreApp);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLlYsqGl_forProc(?,?,?,?,?,?,?,?)}");
			call.setString(1, itemPreApp.getStatus());
			call.setString(2, itemPreApp.getNo());
			call.setString(3, itemPreApp.getConfirmCzy());
			call.setString(4, itemPreApp.getRemarks());
			call.setString(5, itemPreApp.getEndCode());
			call.setString(6, itemPreApp.getLastUpdatedBy());
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.NVARCHAR);
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

	public Map<String,Object> existPreMeasure(Integer pk,String supplCode,String preAppNo){
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from tItemPreMeasure where 1=1";
		if(pk != null){
			sql += " and pk <> ? ";
			params.add(pk);
		}
		if(StringUtils.isNotBlank(supplCode)){
			sql += " and SupplCode=? ";
			params.add(supplCode);
		}
		if(StringUtils.isNotBlank(preAppNo)){
			sql += " and PreAppNo=? ";
			params.add(preAppNo);
		}
		List<Map<String,Object>> list = this.findBySql(sql, params.toArray());
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String,Object>> goClhxdJqGrid(Page<Map<String,Object>> page, ItemPreMeasure itemPreMeasure){
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( SELECT a.PK ,a.SupplCode ,a.Status ,a.Remarks ,a.PreAppNo ,a.CancelRemark ,a.LastUpdatedBy ,a.Expired ,a.ActionLog , "
					+" a.LastUpdate ,a.MeasureRemark ,b.Descr SupplerDescr ,X1.NOTE StatusDescr ,d.Address ,d.descr ,a.AppCZY , "
					+" a.Date ,a.ConfirmCZY ,a.ConfirmDate ,e1.zwxm AppCZYDescr ,e2.zwxm ConfirmCZYDescr ,f.Descr ItemType1Descr , "
					+" g.Descr SupplierDescr ,a.RecvDate ,a.CompleteDate ,a.CompleteCZY ,e3.ZWXM CompleteCZYDescr ,e4.NameChi ProjectManDescr , "
					+" d2.Desc1 prjDepartment2Descr ,h.Desc1 CustTypeDescr ,x2.note DelayResonDescr ,e5.NameChi MainManDescr,c.CustCode,c.Itemtype1, "
					+" x3.note chgStatusDescr,a.chgcompletedate,a.informdate, "
					+" m.Descr ActualProgressItemDescr "
					+" FROM tItemPreMeasure a "
					+" LEFT OUTER JOIN tSupplier b ON b.Code = a.SupplCode "
					+" LEFT OUTER JOIN tXTDM x1 ON x1.CBM = a.Status AND X1.ID = 'MEASURESTATUS' "
					+" LEFT OUTER JOIN tXTDM x2 ON x2.cbm = a.DelayReson AND X2.ID = 'APPDELAYRESON' "
					+" LEFT OUTER JOIN tItempreApp c ON c.no = a.PreAppNo "
					+" LEFT OUTER JOIN tCustomer d ON d.Code = c.CustCode "
					+" LEFT OUTER JOIN tCZYBM e1 ON e1.CZYBH = a.AppCZY "
					+" LEFT OUTER JOIN tCZYBM e2 ON e2.CZYBH = a.ConfirmCZY "
					+" LEFT OUTER JOIN tCZYBM e3 ON e3.CZYBH = a.CompleteCZY "
					+" LEFT OUTER JOIN tItemType1 f ON f.Code = c.Itemtype1 "
					+" LEFT OUTER JOIN tSupplier g ON g.Code = a.SupplCode "
					+" LEFT OUTER JOIN tEmployee e4 ON e4.Number = d.ProjectMan "
					+" LEFT OUTER JOIN tDepartment2 d2 ON d2.code = e4.Department2 "
					+" LEFT OUTER JOIN tCusttype h ON h.code = d.CustType "
					+" LEFT OUTER JOIN tCustStakeholder cs ON cs.CustCode = c.custCode AND cs.Role = '34' "
					+" LEFT OUTER JOIN tEmployee e5 ON e5.number = cs.EmpCode "
		            +" left join tBuilder i on d.BuilderCode=i.Code "
				    +" left join tRegion j on i.RegionCode=j.Code "
		            +" left join txtdm x3 on x3.cbm = a.ChgStatus and x3.id = 'MEASURECHGSTAT' "
		            +" outer apply ( "
		            +"      select max(PK) pk "
		            +"          from tPrjProg in_a "
		            +"      where in_a.CustCode = d.Code "
		            +"      and in_a.PlanBegin = ( "
		            +"          select max(in1_a.PlanBegin) a "
		            +"          from tPrjProg in1_a "
		            +"             where in1_a.CustCode = in_a.CustCode and in1_a.BeginDate = ( "
		            +"              select max(in2_a.BeginDate) "
		            +"              from tPrjProg in2_a "
		            +"              where in2_a.CustCode=in1_a.CustCode and in2_a.BeginDate < getdate() "
		            +"          ) "
		            +"      ) "
		            +" ) k "
		            +" left join tPrjProg l on l.PK = k.pk "
		            +" left join tPrjItem1 m on m.Code = l.PrjItem "
					+" WHERE 1 = 1 and c.itemType1 in ('"+itemPreMeasure.getItemRight().replace(",", "','")+"')";
		if(StringUtils.isNotBlank(itemPreMeasure.getAppCzy())){
			sql += " and a.AppCZY=? ";
			list.add(itemPreMeasure.getAppCzy());
		}
		if(StringUtils.isNotBlank(itemPreMeasure.getSupplCode())){
			sql += " and a.SupplCode=? ";
			list.add(itemPreMeasure.getSupplCode());
		}
		if(StringUtils.isNotBlank(itemPreMeasure.getAddress())){
			sql += " and d.Address like ? ";
			list.add("%"+itemPreMeasure.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(itemPreMeasure.getItemType1())){
			sql += " and c.ItemType1=? ";
			list.add(itemPreMeasure.getItemType1());
		}else{
			sql += " and c.ItemType1 in ( SELECT Code FROM dbo.tItemType1 WHERE Expired='F' )";
		}
		if(StringUtils.isNotBlank(itemPreMeasure.getStatus())){
			sql += " and a.Status in ('"+itemPreMeasure.getStatus().replace(",", "','")+"')";
		}
		if(itemPreMeasure.getDateFrom() != null){
			sql += " and cast(a.Date as date) >= ? ";
			list.add(itemPreMeasure.getDateFrom());
		}
		if(itemPreMeasure.getDateTo() != null){
			sql += " and cast(a.Date as date) <= ? ";
			list.add(itemPreMeasure.getDateTo());
		}
		if(StringUtils.isNotBlank(itemPreMeasure.getPreAppNo())){
			sql += " and a.PreAppNo=? ";
			list.add(itemPreMeasure.getPreAppNo());
		}
		if(StringUtils.isNotBlank(itemPreMeasure.getEmpCode())){
			sql += " and exists (select 1 from tCustStakeholder where CustCode=c.custCode and Role='34' and EmpCode=? ) ";
			list.add(itemPreMeasure.getEmpCode());
		}
		if(StringUtils.isNotBlank(itemPreMeasure.getDepartment2())){
			sql += " and d2.Code in ('"+itemPreMeasure.getDepartment2().trim().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(itemPreMeasure.getRegion())){
			sql += " and  j.code in " + "('"+itemPreMeasure.getRegion().replace(",", "','" )+ "')";
		}
    	if(StringUtils.isNotBlank(itemPreMeasure.getPrjRegion())){
			sql+="  and exists ( select  1 from tRegion2 a  where a.PrjRegionCode = ? and a.Code=i.regionCode2 )";
			list.add(itemPreMeasure.getPrjRegion());
		}
    	if(StringUtils.isNotBlank(itemPreMeasure.getChgStatus())){
    		sql+=" and a.ChgStatus = ? ";
    		list.add(itemPreMeasure.getChgStatus());
    	}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate DESC";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String,Object>> findOtherMaterial(String no){
		String sql = " select 1 from  tItemPreMeasure a left outer join tItemPreAppDetail ipad on  a.PreAppNo = ipad.No "
                   + " inner join tItem i on i.Code = ipad.ItemCode  where ipad.PK not in (select isnull(iad.PreAppDTPK,0)  "
                   + " from tItemAppDetail iad where iad.PreAppDTPK = ipad.PK )  "
                   + " and i.SupplCode <> a.SupplCode  and a.PreAppNo=? ";
		return this.findBySql(sql, new Object[]{no});
	}
	
	public Map<String,Object> getMeasureInfoByPk(Integer pk){
		String sql = " SELECT a.PreAppNo,b.CustCode,c.ProjectMan,d.NameChi ProjectManDescr,d.Phone,b.ItemType1,b.IsSetItem,c.DocumentNo,b.Remarks,"
					+" (h.Descr+'\n'+a.MeasureRemark) MeasureRemark,c.Area,g.OtherCost,g.Status itemAppStatus,g.Type itemAppType, "
					+" c.address,e.Desc2 Department1Descr,f.Desc2 Department2Descr,c.Descr custCodeDescr,c.CheckStatus m_CheckStatus,c.custType,i.regionCode "
					+" FROM dbo.tItemPreMeasure a "
					+" LEFT JOIN dbo.tItemPreApp b ON a.PreAppNo = b.No "
					+" LEFT JOIN dbo.tCustomer c ON b.CustCode = c.Code "
					+" LEFT JOIN dbo.tEmployee d ON d.Number = c.ProjectMan "
					+" LEFT JOIN dbo.tDepartment1 e ON d.Department1 = e.Code "
					+" LEFT JOIN dbo.tDepartment2 f ON d.Department2 = f.Code "
					+" LEFT JOIN dbo.tItemApp g ON g.PreAppNo = b.No "
					+" LEFT JOIN dbo.tSupplier h ON a.SupplCode = h.Code "
					+" LEFT JOIN dbo.tBuilder i ON c.BuilderCode = i.Code "
					+" WHERE a.PK=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{pk});
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public List<Map<String,Object>> hasInSetReq(String custCode,String itemType1){
		String sql = " select top 1 PK from tItemReq where CustCode=? and ItemType1=? and IsOutSet='0' and Qty<>0";
		return this.findBySql(sql, new Object[]{custCode,itemType1});

	}
	
	public List<Map<String,Object>> containServiceItem(String no){
		String sql = " select 1 "
					+" from tItemPreAppDetail a "
					+" inner join tItemReq b on a.ReqPK=b.PK "
					+" where b.IsService=1 and a.No=? ";
		return this.findBySql(sql, new Object[]{no});
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
	
	public List<Map<String,Object>> getItemType2Opt(String itemType1){
		String sql = " select rtrim(Code)+' '+Descr fd,Code from tItemType2 where Expired='F' and ItemType1=? order by DispSeq";
		return this.findBySql(sql, new Object[]{itemType1});
	}

	public Page<Map<String,Object>> goYyxxzJqGrid(Page<Map<String,Object>> page,ItemPreMeasure itemPreMeasure){
		Xtcs xtcs = this.get(Xtcs.class, "Titles");
		List<Object> list = new ArrayList<Object>();
		String sql = "  exec pYyxxz ?,?,?,?,?,?,?,?,?,?,?,?";
		list.add(itemPreMeasure.getCustCode());
		list.add(itemPreMeasure.getItemType1());
		list.add(itemPreMeasure.getItemType2());
		list.add(itemPreMeasure.getAppNo());
		list.add(itemPreMeasure.getIsService());
		list.add(itemPreMeasure.getIsSetItem());
		list.add(itemPreMeasure.getPks());
		list.add(xtcs.getQz());
		list.add(itemPreMeasure.getIsCupboard());
		list.add(itemPreMeasure.getIsAllSend());
		list.add(itemPreMeasure.getIsAutoOrder());
		list.add(itemPreMeasure.getM_umState());
		page.setResult(findListBySql(sql, list.toArray())); 		
		page.setTotalCount(page.getResult().size());
		return page;
	}

	public List<Map<String,Object>> judgeItemInTitles(String itemCode){
		Xtcs xtcs = this.get(Xtcs.class, "Titles");
		String sql = " select 1 from tItem WHERE Code =? and ItemType2 in ('"+xtcs.getQz().trim().replace(",", "','")+"')";
		return this.findBySql(sql, new Object[]{itemCode});
	}

	public List<Map<String,Object>> getItemNum(String itemCode,Double qty){
		List<Object> params = new ArrayList<Object>();
		String sql = " select case when d.PackageNum=1 or d.PackageNum=0 or ? *d.PerNum<d.PackageNum then cast(cast( ? *d.PerNum as float) as nvarchar(100)) "
				   + " when ? *d.PerNum/d.PackageNum = floor( ? *d.PerNum/d.PackageNum) then cast(cast( ? *d.PerNum/d.PackageNum as int) as nvarchar(100))+'箱' "
				   + " else cast(cast(floor( ? *d.PerNum/d.PackageNum) as int) as nvarchar(100))+'箱'+ cast(cast( ? *d.PerNum-d.PackageNum*floor( ? *d.PerNum/d.PackageNum) as float) as nvarchar(100))+'片' end NumDescr "
				   + " from tItem d where Code=? ";
		params.add(qty);
		params.add(qty);
		params.add(qty);
		params.add(qty);
		params.add(qty);
		params.add(qty);
		params.add(qty);
		params.add(qty);
		params.add(itemCode);
		return this.findBySql(sql, params.toArray());
	}
	
	public Page<Map<String,Object>> goPreApplyJqGrid(Page<Map<String,Object>> page, ItemPreMeasure itemPreMeasure){
		
		StringBuilder sbReqPks = new StringBuilder();
		StringBuilder sbItemCodes = new StringBuilder();
		
		String[] reqPks = itemPreMeasure.getReqPks().split(",");
		String[] itemCodes = itemPreMeasure.getItemCodes().split(",");
		
		for(int i=0;i<reqPks.length && itemPreMeasure.getReqPks().length()>0;i++){
			if(Integer.parseInt(reqPks[i]) > 0){
				if(sbReqPks.length() == 0){
					sbReqPks.append("'"+reqPks[i].trim()+"'");
				}else{
					sbReqPks.append(",'"+reqPks[i].trim()+"'");
				}
			}else{
				if(sbItemCodes.length() == 0){
					sbItemCodes.append("'"+itemCodes[i].trim()+"'");
				}else{
					sbItemCodes.append(",'"+itemCodes[i].trim()+"'");
				}
			}
		}
		
		Xtcs xtcs = this.get(Xtcs.class, "Titles");
		List<Object> list = new ArrayList<Object>();
		String sql = " SELECT * FROM ( "
					+" 	Select a.No,a.ReqPK,a.FixAreaPK,b.Descr FixAreaDescr,a.IntProdPK,c.Descr IntProdDescr, "
					+" 	a.ItemCode,d.Descr ItemDescr,a.Qty+isnull(a.ShortQty,0) Qty,ISNULL(a.ReqQty,0) ReqQty,isnull(a.SendedQty,0) SendedQty,a.SendQty,a.ShortQty,d.allqty,dbo.fGetUseQty(d.Code,'',?) userqty, "
					+" 	case when a.ReqPK>0 then isnull(e.ConfirmedQty, 0) else isnull(g.ConfirmedQty, 0) end ConfirmedQty,a.Remarks,a.Cost,a.ProjectCost,tt.remark ReqRemarks, "
					+" 	round(a.Cost*a.Qty,2) AllCost,round(a.ProjectCost*a.Qty,2) AllProjectCost,  "
				    +" 	ir.UnitPrice Price,isnull(case when d.ItemType2 in ('"+xtcs.getQz().trim().replace(",", "','")+"') and ir.Qty<>0 then round(ir.ProcessCost*a.Qty/ir.Qty,2) else ir.ProcessCost end, 0) ProcessCost, ";
		if("C".equals(itemPreMeasure.getM_umState())){
			sql += " a.Cost*a.Qty SumCost,(isnull(e.ConfirmedQty,0)+a.SendedQty+a.Qty-a.ReqQty)*a.Cost as Differences, ";
		}else{
			sql += " d.Cost*a.Qty SumCost,(isnull(e.ConfirmedQty,0)+a.SendedQty+a.Qty-a.ReqQty)*d.Cost as Differences, ";
		}
		sql += " (isnull(e.ConfirmedQty,0)+a.SendedQty+a.Qty-a.ReqQty)*d.Price as PriceDifferences, "
			 + " u.Descr UomDescr,d.color,d.SqlCode,sql.Descr SqlcodeDescr,spl.Descr SupplCodeDescr, "
			 + " case when a.ReqPK>0 then isnull(f.ApplyQty, 0) else isnull(h.ApplyQty, 0) end ApplyQty,x1.NOTE SendTypeDescr,ISNULL(ir.ProcessCost,0) ReqProcessCost,d.PerWeight,d.PerNum,a.Qty*d.PerWeight Weight,a.Qty*d.PerNum Num, "
			 + " case when d.PackageNum=1 or d.PackageNum=0 or a.Qty*d.PerNum<d.PackageNum then cast (cast(a.Qty*d.PerNum as float) as nvarchar(100)) "
			 + " when a.Qty*d.PerNum/d.PackageNum = floor(a.Qty*d.PerNum/d.PackageNum) then cast(cast(a.Qty*d.PerNum/d.PackageNum as int) as nvarchar(100))+'箱' "
			 + " else cast(cast(floor(a.Qty*d.PerNum/d.PackageNum) as int) as nvarchar(100))+'箱'+ cast(cast(a.Qty*d.PerNum-d.PackageNum*floor(a.Qty*d.PerNum/d.PackageNum) as float) as nvarchar(100))+'片' end NumDescr "
			 + " ,a.DeclareQty "  // 增加申报数量 by zjf 
			 + " ,a.pk,a.ItemType2Descr,a.ItemType3Descr,a.SupplDescr,a.IsOutSetDescr,a.IsOutSet,a.AftQty,a.AftCost,wh.Code WHCode,wh.Desc1 WHDescr,spl.Code SupplCode,d.SendType,a.PreAppDTPK,a.itemType2,a.itemType3  " //增加字段显示 by zzr
	         + " from (" 
	         + " 	select a.Cost, "
	         + " 	a.ProjectCost,1 No,isnull(a.ReqPK,0) ReqPK,isnull(a.FixAreaPK,0) FixAreaPK,isnull(a.IntProdPK,0) IntProdPK,a.ItemCode,a.Qty,a.ReqQty, "
	         + " 	a.SendQty SendedQty,a.Remarks,a.PK PreAppDTPK,a.Qty DeclareQty "
	         + " 	,0 ShortQty,a.SendQty,a.pk,a.ItemType2Descr,a.ItemType3Descr,a.SupplDescr,a.IsOutSetDescr,a.IsOutSet,0 AftQty,0 AftCost,a.itemType2,a.itemType3 "
	         + " 	from ("  
	         + " 		Select ipad.PK,ipad.ItemCode,ipad.Qty,ipad.ReqPK,ir.CustCode,ir.FixAreaPK,fa.Descr FixAreaDescr, "
	         + " 		ir.IntProdPK,ip.Descr IntProdDescr,sp.Descr SupplDescr, "
	         + " 		i.Descr ItemDescr,ir.ItemType1,it1.Descr TypeDescr,ir.Qty ReqQty, "
	         + " 		it2.Descr ItemType2Descr,it3.Descr ItemType3Descr, "
	         + " 		ir.SendQty,ipad.Remarks,case when ir.SupplPromItemPK is not null and ir.SupplPromItemPK <> '' then ir.Cost else i.Cost end Cost,"//若供应商促销产品，显示供应商促销成本 
	         + " 		isnull(tcti.ProjectCost,i.ProjectCost) ProjectCost ,"
	         + " 		x1.NOTE SendTypeDescr,ir.IsOutSet,x2.NOTE IsOutSetDescr,it2.Code itemType2,isnull(it3.Code,'') itemType3  "
             + " 		from tItemPreAppDetail ipad "
             + " 		left outer join tItemReq ir on ipad.ReqPK=ir.PK "
             + " 		left outer join tFixArea fa on ir.FixAreaPK=fa.PK "
             + " 		left outer join tIntProd ip on ir.IntProdPK=ip.PK "
             + " 		left outer join tItem i on ipad.ItemCode=i.Code "
             + " 		left outer join tSupplier sp on sp.Code=i.SupplCode "
             + " 		left outer join tItemType1 it1 on ir.ItemType1=it1.Code "
             + " 		left outer join tItemType2 it2 on i.Itemtype2=it2.Code "
             + " 		left outer join tItemType3 it3 on i.Itemtype3=it3.Code "
             + " 		left outer join tXTDM x1 on x1.ID='ITEMAPPSENDTYPE' AND x1.CBM=i.SendType "
             + " 		left outer join tXTDM x2 on x2.ID='YESNO' AND x2.CBM=ir.IsOutSet "
             + " 		left join tCustTypeItem tcti on tcti.PK = ir.CustTypeItemPK "	
             + "		left join tItemPreApp ipa on ipa.No = ipad.No "
             + " 		where 1=1 ";
		list.add(itemPreMeasure.getAppNo());
		if(StringUtils.isNotBlank(itemPreMeasure.getPreAppNo())){
			sql += " and ipad.No = ? ";
			list.add(itemPreMeasure.getPreAppNo());
		}
		if(StringUtils.isNotBlank(itemPreMeasure.getIsSetItem())){
			if("1".equals(itemPreMeasure.getIsSetItem())){
				sql += " and isnull(ir.IsOutSet,case when ipa.ItemType1 = 'JZ' then '0' else ir.IsOutSet end) = '0' ";
			}else{
				sql += " and isnull(ir.IsOutSet,case when ipa.ItemType1 = 'JZ' then '0' else ir.IsOutSet end) = '1' ";
			}
		}
		if(StringUtils.isNotBlank(itemPreMeasure.getPks())){
			sql += " and ipad.PK not in ('"+itemPreMeasure.getPreAppDTPks().replace(",", "','")+"')";
		}
		sql += " 	 and not exists(select 1 from tItemApp ia inner join tItemAppDetail iad on ia.No=iad.No where ia.PreAppNo=ipad.No and iad.PreAppDTPK=ipad.PK ";
		if(StringUtils.isNotBlank(itemPreMeasure.getAppNo())){
			sql += " and iad.No<>? ";
			list.add(itemPreMeasure.getAppNo());
		}
		sql += " 		) ";
		if(sbReqPks.length() > 0 || sbItemCodes.length() > 0){
			sql += " and not ( ";
			boolean existReqPks = false;
			if(sbReqPks.length() > 0){
				existReqPks = true;
				sql += " ir.PK in ( "+sbReqPks.toString()+" ) ";
			}
			if(sbItemCodes.length() > 0){
				if(existReqPks){
					sql += " or ";
				}
				sql += " i.Code in ( "+sbItemCodes.toString()+" ) ";
			}
			sql += ")";
		}
		if(StringUtils.isNotBlank(itemPreMeasure.getItemType2())){
			sql += " and i.ItemType2=? ";
			list.add(itemPreMeasure.getItemType2());
		}
		if("ZC".equals(itemPreMeasure.getItemType1().trim()) && "0".equals(itemPreMeasure.getIsSetItem().trim())){
			if(StringUtils.isNotBlank(itemPreMeasure.getIsService())){
				sql += " and  ir.IsService=? ";
				list.add(itemPreMeasure.getIsService());
			}
		}
		sql += " 	) a "
			 + "	left outer join ( "
			 + "		SELECT i2.ReqPK,sum(Qty) ConfirmedQty  FROM tItemAppDetail i2 "
			 + " 		inner join tItemApp i1 on  i2.No=i1.No "
			 + " 		WHERE i1.Status='CONFIRMED' and i1.CustCode=? "
			 + " 		group by i2.ReqPk) e "
			 + " 	on e.ReqPk = a.PK ";
			 //+ " 	order by a.PK ";
		list.add(itemPreMeasure.getCustCode());
		sql += " ) a "
             + " left outer join ( "
             + "   SELECT i2.ReqPK,sum(Qty-SendQty) ConfirmedQty  FROM tItemAppDetail i2 "
             + "   inner join tItemApp i1 on  i2.No=i1.No "
             + "   WHERE i1.Status='CONFIRMED' and i2.reqpk>0 and i1.CustCode=? "
             + "   group by i2.ReqPk ) e on e.ReqPk = a.ReqPK  ";
		list.add(itemPreMeasure.getCustCode());
		sql += " left outer join ( "
			 + "   SELECT i2.ItemCode,sum(Qty-SendQty) ConfirmedQty  FROM tItemAppDetail i2 "
			 + "   inner join tItemApp i1 on  i2.No=i1.No "
			 + "   WHERE i1.Status='CONFIRMED' and not(i2.ReqPK>0) and i1.CustCode=? "
			 + "   group by i2.ItemCode ) g on g.ItemCode = a.ItemCode  ";
		list.add(itemPreMeasure.getCustCode());
        sql += " left outer join ( "
        	 + "   SELECT i2.ReqPK,sum(case when i1.Type='S' then Qty else -Qty end) ApplyQty  FROM tItemAppDetail i2 "
        	 + "   inner join tItemApp i1 on  i2.No=i1.No "
        	 + "   WHERE (i1.Status='OPEN' or i1.Status='CONRETURN' or i1.Status='WAITCON') and i2.reqpk>0 and i1.CustCode=? "
        	 + "   and i1.No<>? "
        	 + "   group by i2.ReqPk) f on f.ReqPk = a.ReqPK  ";
        list.add(itemPreMeasure.getCustCode());
        list.add(itemPreMeasure.getAppNo());
        sql += " left outer join ( "
        	 + "   SELECT i2.ItemCode,sum(case when i1.Type='S' then Qty else -Qty end) ApplyQty  FROM tItemAppDetail i2 "
        	 + "   inner join tItemApp i1 on  i2.No=i1.No "
        	 + "   WHERE (i1.Status='OPEN' or i1.Status='CONRETURN' or i1.Status='WAITCON') and not(i2.reqpk>0) and i1.CustCode=? "
        	 + "   and i1.No<>? "
        	 + "   group by i2.ItemCode) h on h.ItemCode = a.ItemCode  ";
        list.add(itemPreMeasure.getCustCode());
        list.add(itemPreMeasure.getAppNo());
        sql += " left outer join tFixArea b on a.FixAreaPK = b.PK "
        	 + " left outer join tIntProd c on a.IntProdPK = c.PK "
        	 + " left outer join tItemReq ir on ir.Pk=a.ReqPK "
        	 + " left outer join tItem d on a.ItemCode = d.Code "
        	 + " left outer join tBrand sql on sql.code=d.sqlcode "
        	 + " left outer join tSupplier spl on spl.Code=d.SupplCode "
        	 + " left outer join tItemReq tt on a.ReqPK = tt.PK  "
        	 + " left outer join tUom u on u.code=d.uom "
        	 + " left outer join tXTDM x1 on x1.ID='ITEMAPPSENDTYPE' AND x1.CBM=d.SendType "
        	 + " left join tWareHouse wh on wh.Code = d.WHCode ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.pk ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> goYlclJqGrid(Page<Map<String,Object>> page,  String custCode,String appNo,String itemCodes){
		String sql = " select iad.ItemCode,i.Descr ItemDescr,i.ItemType2,it2.Descr ItemType2Descr,i.Uom,u.Descr UomDescr,"
				   + " sum(case when type='R' then iad.Qty*-1 else iad.Qty end) TotalQty, "
				   + " sum(iad.cost*case when type='R' then iad.Qty*-1 else iad.Qty end) TotalCost "
				   + " from tItemApp ia "
				   + " inner join tItemAppDetail iad on iad.No=ia.No "
				   + " inner join tItem i on i.Code=iad.ItemCode "
				   + " left join tItemType2 it2 on it2.Code=i.ItemType2 "
				   + " left join tUOM u on u.Code=i.Uom "
				   + " where ia.status<>'CANCEL' "
				   + " and ia.CustCode=? "
				   + " and ia.No<>? "
				   + " and iad.ItemCode in ('"+itemCodes.trim().replace(",", "','")+"') "
				   + " group by iad.ItemCode,i.Descr,i.ItemType2,it2.Descr,i.Uom,u.Descr "
				   + " order by i.ItemType2,iad.ItemCode ";

		return this.findPageBySql(page, sql, new Object[]{custCode,appNo});
	}

	public Map<String,Object> getMeasureInfoByNo(String no){
		String sql = " SELECT ipa.NO PreAppNo,ipa.Status,ipa.CustCode,c.ProjectMan,e.NameChi ProjectManDescr,e.Phone,ipa.ItemType1,ipa.IsSetItem,c.DocumentNo,ipa.Remarks,ia.refCustCode,f.Descr refCustCodeDescr,  "
				   + " c.Area,ia.OtherCost,ia.Status itemAppStatus,ia.type itemAppType,c.Address,d1.Desc2 Department1Descr,d2.Desc2 Department2Descr,c.Descr custCodeDescr,c.CheckStatus m_CheckStatus,c.custType,g.regionCode "
				   + " FROM dbo.tItemPreApp ipa "
				   + " LEFT JOIN dbo.tCustomer c ON c.Code = ipa.CustCode " 
				   + " LEFT JOIN dbo.tEmployee e ON e.Number = c.ProjectMan "
				   + " LEFT JOIN dbo.tDepartment1 d1 ON d1.Code = e.Department1 "
				   + " LEFT JOIN dbo.tDepartment2 d2 ON d2.Code = e.Department2 "
				   + " LEFT JOIN dbo.tItemApp ia ON ia.PreAppNo = ipa.No "
				   + " LEFT JOIN dbo.tCustomer f ON f.Code = ia.refCustCode "
				   + " LEFT JOIN dbo.tBuilder g ON c.BuilderCode=g.Code "
				   + " WHERE ipa.No=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{no});
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String,Object>> goKsxzJqGrid(Page<Map<String,Object>> page,ItemPreMeasure itemPreMeasure){
		Xtcs xtcs = this.get(Xtcs.class, "Titles");
		List<Object> list = new ArrayList<Object>();
		String sql = " SELECT * FROM ( "
					+" 	Select a.No,a.ReqPK,a.FixAreaPK,b.Descr FixAreaDescr,a.IntProdPK,c.Descr IntProdDescr, "
					+" 	a.ItemCode,d.Descr ItemDescr,a.Qty+isnull(a.ShortQty,0) Qty,a.ReqQty,isnull(a.SendedQty,0) SendedQty,a.SendQty,a.ShortQty,d.allqty,dbo.fGetUseQty(d.Code,'',?) userqty, "
					+" 	case when a.ReqPK>0 then isnull(e.ConfirmedQty, 0) else isnull(g.ConfirmedQty, 0) end ConfirmedQty,a.Remarks,a.Cost,a.ProjectCost,tt.remark ReqRemarks, "
					+" 	round(a.Cost*a.Qty,2) AllCost,round(a.ProjectCost*a.Qty,2) AllProjectCost,  "
				    //ir.UnitPrice Price,
					+" 	ISNULL(d.Price,0) Price,isnull(case when d.ItemType2 in ('"+xtcs.getQz().trim().replace(",", "','")+"') and ir.Qty<>0 then round(ir.ProcessCost*a.Qty/ir.Qty,2) else ir.ProcessCost end, 0) ProcessCost, ";
		if("C".equals(itemPreMeasure.getM_umState())){
			sql += " a.Cost*a.Qty SumCost,(isnull(e.ConfirmedQty,0)+a.SendedQty+a.Qty-a.ReqQty)*a.Cost as Differences, ";
		}else{
			sql += " d.Cost*a.Qty SumCost,(isnull(e.ConfirmedQty,0)+a.SendedQty+a.Qty-a.ReqQty)*d.Cost as Differences, ";
		}
		sql += " (isnull(e.ConfirmedQty,0)+a.SendedQty+a.Qty-a.ReqQty)*d.Price as PriceDifferences, "
			 + " u.Descr UomDescr,d.color,d.SqlCode,sql.Descr SqlcodeDescr,spl.Descr SupplCodeDescr, "
			 + " case when a.ReqPK>0 then isnull(f.ApplyQty, 0) else isnull(h.ApplyQty, 0) end ApplyQty,x1.NOTE SendTypeDescr,ISNULL(ir.ProcessCost,0) ReqProcessCost,d.PerWeight,d.PerNum,a.Qty*d.PerWeight Weight,a.Qty*d.PerNum Num, "
			 + " case when d.PackageNum=1 or d.PackageNum=0 or a.Qty*d.PerNum<d.PackageNum then cast (cast(a.Qty*d.PerNum as float) as nvarchar(100)) "
			 + " when a.Qty*d.PerNum/d.PackageNum = floor(a.Qty*d.PerNum/d.PackageNum) then cast(cast(a.Qty*d.PerNum/d.PackageNum as int) as nvarchar(100))+'箱' "
			 + " else cast(cast(floor(a.Qty*d.PerNum/d.PackageNum) as int) as nvarchar(100))+'箱'+ cast(cast(a.Qty*d.PerNum-d.PackageNum*floor(a.Qty*d.PerNum/d.PackageNum) as float) as nvarchar(100))+'片' end NumDescr "
			 + " ,a.DeclareQty "  // 增加申报数量 by zjf 
			 + " ,a.Code,a.lastUpdate,a.ItemType1Descr,a.ItemType2Descr,a.ItemType3Descr,d.Model,d.SizeDesc,d.BarCode,a.IsFixPriceDescr,a.AftQty,a.AftCost,wh.Code WHCode,wh.Desc1 WHDescr,spl.Code SupplCode,d.SendType,a.itemType2, " //增加字段显示 by zzr
	         + " a.AvgCost "//增加材料平均移动成本显示 by zb
			 + " from (" 
	         + " 	select a.Cost,a.ProjectCost,1 No,0 ReqPK,0 FixAreaPK,0 IntProdPK,a.Code ItemCode,0 Qty,0 ReqQty, "
	         + " 	0 SendedQty,a.Remark Remarks "
	         + " 	,a.Code,0 ShortQty,0 SendQty,0 DeclareQty,a.lastUpdate,a.ItemType1Descr,a.ItemType2Descr,a.ItemType3Descr,a.IsFixPriceDescr,0 AftQty,0 AftCost,a.itemType2, "
	         + "	a.AvgCost "
	         + " 	from ( " 
	         + " 		select a.Code,a.Descr, "
	         + " 		a.ItemSize,a.SizeDesc, "
	         + " 		a.BarCode,a.RemCode,a.ItemType1,i1.Descr ItemType1Descr, "
	         + " 		a.ItemType2,i2.Descr ItemType2Descr,a.ItemType3,i3.Descr ItemType3Descr, "
	         + " 		a.SqlCode,b.Descr SqlDescr,a.Uom,u.Descr UomDescr,a.SupplCode,s.Descr SplCodeDescr, "
	         + " 		a.Model,a.Color,a.Remark,a.CommiType, "
	         + "		d.NOTE CommiTypeDescr,a.Price,a.MarketPrice,a.IsFixPrice, "
             + "		p.Note IsFixPriceDescr,a.Cost,a.AvgCost,a.AllQty,a.ProjectCost, "
             + " 		a.ActionLog,x1.NOTE SendTypeDescr " 
             + " 		,a.lastUpdate"
             + " 		from  tItem a "
             + " 		left outer join tItemType1 i1 on a.ItemType1=i1.Code "
             + " 		left outer join tItemType2 i2 on a.ItemType2=i2.Code "
             + " 		left outer join tItemtype3 i3 on a.ItemType3=i3.Code "
             + " 		left outer join tBrand b on a.SqlCode=b.Code "
             + " 		left outer join tXTDM d on a.CommiType=d.CBM and d.ID='COMMITYPE'  "
             + " 		left outer join tXTDM p on a.IsFixPrice=p.CBM and p.ID='ISFIXPRICE' "
             + " 		left outer join tSupplier s on a.SupplCode=s.Code "
             + " 		left outer join tUom u on a.Uom = u.Code "
             + " 		left outer join tXTDM x1 on x1.ID='ITEMAPPSENDTYPE' AND x1.CBM=a.SendType "
             + " 		where a.Expired='F' "
             + " 		and a.Code not in ('"+itemPreMeasure.getItemCodes().trim().replace(",", "','")+"')"; //+ ' and not exists(select 1 from #tItemAppDetail iad where iad.ItemCode=a.Code)'
		list.add(itemPreMeasure.getAppNo());
		if(StringUtils.isNotBlank(itemPreMeasure.getIsSetItem())){
			sql += " and a.IsSetItem=? ";
			list.add(itemPreMeasure.getIsSetItem());
		}
        if(StringUtils.isNotBlank(itemPreMeasure.getItemDescr())){
        	sql += " and a.Descr like ? ";
        	list.add("%"+itemPreMeasure.getItemDescr()+"%");
        }
        if(StringUtils.isNotBlank(itemPreMeasure.getBrand())){
        	sql += " and a.SqlCode=? ";
        	list.add(itemPreMeasure.getBrand());
        }
        if(StringUtils.isNotBlank(itemPreMeasure.getItemCode())){
        	sql += " and a.Code=? ";
        	list.add(itemPreMeasure.getItemCode());
        }
        if(StringUtils.isNotBlank(itemPreMeasure.getItemType1())){
        	sql += " and a.ItemType1=? ";
        	list.add(itemPreMeasure.getItemType1());
        }
        if(StringUtils.isNotBlank(itemPreMeasure.getItemType2())){
        	sql += " and a.ItemType2=? ";
        	list.add(itemPreMeasure.getItemType2());
        }
        if(StringUtils.isNotBlank(itemPreMeasure.getItemType3())){
        	sql += " and a.ItemType3=? ";
        	list.add(itemPreMeasure.getItemType3());
        }
        if(StringUtils.isNotBlank(itemPreMeasure.getBarCode())){
        	sql += " and a.BarCode=? ";
        	list.add(itemPreMeasure.getBarCode());
        }
        if(StringUtils.isNotBlank(itemPreMeasure.getModel())){
        	sql += " and a.Model like ? ";
        	list.add("%"+itemPreMeasure.getModel()+"%");
        }
        if(StringUtils.isNotBlank(itemPreMeasure.getSizeDesc())){
        	sql += " and a.SizeDesc like ? ";
        	list.add("%"+itemPreMeasure.getSizeDesc()+"%");
        }
        if(StringUtils.isNotBlank(itemPreMeasure.getSupplCode())){
        	sql += " and a.SupplCode=? ";
        	list.add(itemPreMeasure.getSupplCode());
        }
	    sql += " 	) a "
	         + " ) a "
             + " left outer join ( "
             + "   SELECT i2.ReqPK,sum(Qty-SendQty) ConfirmedQty  FROM tItemAppDetail i2 "
             + "   inner join tItemApp i1 on  i2.No=i1.No "
             + "   WHERE i1.Status='CONFIRMED' and i2.reqpk>0 and i1.CustCode=? "
             + "   group by i2.ReqPk ) e on e.ReqPk = a.ReqPK  ";
		list.add(itemPreMeasure.getCustCode());
		sql += " left outer join ( "
			 + "   SELECT i2.ItemCode,sum(Qty-SendQty) ConfirmedQty  FROM tItemAppDetail i2 "
			 + "   inner join tItemApp i1 on  i2.No=i1.No "
			 + "   WHERE i1.Status='CONFIRMED' and not(i2.ReqPK>0) and i1.CustCode=? "
			 + "   group by i2.ItemCode ) g on g.ItemCode = a.ItemCode  ";
		list.add(itemPreMeasure.getCustCode());
        sql += " left outer join ( "
        	 + "   SELECT i2.ReqPK,sum(case when i1.Type='S' then Qty else -Qty end) ApplyQty  FROM tItemAppDetail i2 "
        	 + "   inner join tItemApp i1 on  i2.No=i1.No "
        	 + "   WHERE (i1.Status='OPEN' or i1.Status='CONRETURN' or i1.Status='WAITCON') and i2.reqpk>0 and i1.CustCode=? "
        	 + "   and i1.No<>? "
        	 + "   group by i2.ReqPk) f on f.ReqPk = a.ReqPK  ";
        list.add(itemPreMeasure.getCustCode());
        list.add(itemPreMeasure.getAppNo());
        sql += " left outer join ( "
        	 + "   SELECT i2.ItemCode,sum(case when i1.Type='S' then Qty else -Qty end) ApplyQty  FROM tItemAppDetail i2 "
        	 + "   inner join tItemApp i1 on  i2.No=i1.No "
        	 + "   WHERE (i1.Status='OPEN' or i1.Status='CONRETURN' or i1.Status='WAITCON') and not(i2.reqpk>0) and i1.CustCode=? "
        	 + "   and i1.No<>? "
        	 + "   group by i2.ItemCode) h on h.ItemCode = a.ItemCode  ";
        list.add(itemPreMeasure.getCustCode());
        list.add(itemPreMeasure.getAppNo());
        sql += " left outer join tFixArea b on a.FixAreaPK = b.PK "
        	 + " left outer join tIntProd c on a.IntProdPK = c.PK "
        	 + " left outer join tItemReq ir on ir.Pk=a.ReqPK "
        	 + " left outer join tItem d on a.ItemCode = d.Code "
        	 + " left outer join tBrand sql on sql.code=d.sqlcode "
        	 + " left outer join tSupplier spl on spl.Code=d.SupplCode "
        	 + " left outer join tItemReq tt on a.ReqPK = tt.PK  "
        	 + " left outer join tUom u on u.code=d.uom "
        	 + " left outer join tXTDM x1 on x1.ID='ITEMAPPSENDTYPE' AND x1.CBM=d.SendType "
        	 + " left join tWareHouse wh on wh.Code = d.WHCode ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.Code,a.LastUpdate Desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}	
	
	public Result AddllForProc(ItemPreMeasure itemPreMeasure, String xml){
		Assert.notNull(itemPreMeasure);
		Result result = new Result();
		itemPreMeasure.setProjectMan(itemPreMeasure.getProjectManDescr());
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLlYsqGl_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemPreMeasure.getSendTypeCheck());
			call.setString(2, itemPreMeasure.getApplyQtyCheck());
			call.setString(3, itemPreMeasure.getIsCmpCustCode());
			call.setString(4, xml);
			call.setString(5, itemPreMeasure.getM_umState());
			call.setDouble(6, itemPreMeasure.getOtherCost() == null ? 0:itemPreMeasure.getOtherCost());
			call.setString(7, itemPreMeasure.getItemAppStatus());
			call.setString(8, itemPreMeasure.getAppNo());
			call.setString(9, itemPreMeasure.getDelivType());
			call.setString(10, itemPreMeasure.getItemAppType());
			call.setString(11, itemPreMeasure.getItemType1());
			call.setString(12, itemPreMeasure.getItemType2());
			call.setString(13, itemPreMeasure.getCustCode());
			call.setString(14, itemPreMeasure.getAppCzy());
			call.setString(15, itemPreMeasure.getRemarks());
			call.setString(16, itemPreMeasure.getProjectMan());
			call.setString(17, itemPreMeasure.getPhone());
			call.setString(18, itemPreMeasure.getAppCzy());
			call.setString(19, itemPreMeasure.getExpired());
			call.setString(20, itemPreMeasure.getPreAppNo());
			call.setInt(21, Integer.parseInt(itemPreMeasure.getIsService()));
			call.setInt(22, Integer.parseInt(itemPreMeasure.getIsSetItem()));
			call.setString(23, itemPreMeasure.getSendType());
			call.setString(24, itemPreMeasure.getSupplCode());
			call.setString(25, itemPreMeasure.getWareHouseCode());
			call.setString(26, itemPreMeasure.getIsCupboard());
			
			call.registerOutParameter(27, Types.INTEGER);
			call.registerOutParameter(28, Types.NVARCHAR);
			call.setString(29, itemPreMeasure.getRefCustCode());
			call.setInt(30, itemPreMeasure.getIntRepPK()==null?-1:itemPreMeasure.getIntRepPK());
			call.setString(31, itemPreMeasure.getIsSubCheck());
			call.setString(32, itemPreMeasure.getFaultType());
			call.setString(33, itemPreMeasure.getFaultMan());
			call.setString(34, itemPreMeasure.getCallType());
			call.setString(35, itemPreMeasure.getExceptionRemarks());
			call.setString(36, itemPreMeasure.getExceptionCost());
			call.setString(37, itemPreMeasure.getExceptionPayNum());
			call.setString(38, itemPreMeasure.getExceptionAmount());
			call.execute();
			result.setCode(String.valueOf(call.getInt(27)));
			result.setInfo(call.getString(28));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	public boolean isStayAddPage(String preAppNo){
		boolean result = false;
		String sql = " select  1  from    tItemPreAppDetail a where   a.No=? and not exists ( select 1 from tItemAppDetail iad ,tItemApp ia where iad.No = ia.No  and iad.PreAppDTPK = a.PK ) ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{preAppNo});
		if(list != null && list.size() > 0 ){
			result = true;
		}
		return result;
	}
	
	public Map<String,Object> findMessageInfo(Integer pk){
		String sql = " select c.Address,g.Descr,a.pk,b.CustCode,e.CZYBH "
                   + " from tItemPreMeasure a "
                   + " left outer join tItempreApp b on b.no = a.PreAppNo "
                   + " left outer join tCustomer c on b.CustCode = c.Code "
                   + " left outer join tCZYBM e on e.EMNum = c.projectman  "
                   + " left outer join tSupplier g on g.Code = a.SupplCode "
                   + " where a.pk=? ";
		
		Map<String,Object> map = null;
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{pk});
		if(list != null && list.size()>0){
			map=list.get(0);
		}
		return map;
	}

	public List<Map<String,Object>> custItemConf(String custCode){
		String sql = " select distinct citd.ItemType2,citd.ItemType3, "
				   + " case when cic.PK is not null then 1 else 0 end isConf "
				   + " from tConfItemTypeDt citd "
				   + " inner join tConfItemType tcit on tcit.Code = citd.ConfItemType "
				   + " 		and tcit.ItemTimeCode is not null and tcit.ItemTimeCode <> '' "
				   + " left join tCustItemConfirm cic on citd.ConfItemType = cic.ConfItemType  "
				   + " 		and cic.CustCode=? and cic.ItemConfStatus not in ('1','3') "
				   + " ORDER BY ItemType2,ItemType3 ";
		return this.findBySql(sql, new Object[]{custCode});
	}

	public Map<String,Object> getFsArea(String custCode){
		String sql = " SELECT SUM(ISNULL(bir.Qty,0)) Area "
				   + " FROM dbo.tBaseItemReq bir "
				   + " LEFT JOIN dbo.tBaseItem bi ON bir.BaseItemCode = bi.Code "
				   + " LEFT JOIN dbo.tBaseItemType2 bit2 ON bi.BaseItemType2 = bit2.Code "
				   + " WHERE CustCode=? AND bit2.MaterWorkType2='010'";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if(list != null && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}

	public Map<String,Object> getCustPayInfo(String custCode){
		List<Object> params = new ArrayList<Object>();
		String sql = " select isnull(c.firstPay,0)  firstPay, "
				   +" isnull(c.secondPay,0) secondPay,isnull(c.thirdPay,0) thirdPay,isnull(c.fourPay,0) fourPay,isnull(case when tpr.DesignFeeType='2' then c.StdDesignFee else c.DesignFee end,0) designFee, "
				   + " isnull(c.contractFee,0) contractFee,isnull(a.sumAmount,0) zjje,isnull(b.payFee,0) payFee, "
				   + " isnull(c.firstPay,0)+isnull(c.secondPay,0)+isnull(c.thirdPay,0)+isnull(c.fourPay,0)+isnull(case when tpr.DesignFeeType='2' then c.StdDesignFee else c.DesignFee end,0)*(case when exists(select 1 from tPayRuleDetail where No=tpr.No and IsRcvDesignFee='1' and Expired='F') then 1 else 0 end)+isnull(a.sumAmount,0)-isnull(b.payFee,0) wdz "
				   + " from tCustomer c "
				   + " left join ( "
				   + " 		select isnull(sum(Amount),0) SumAmount,? CustCode from (  "
				   + " 			select sum(Amount) Amount from titemchg where custcode=? and status='2' "
				   + " 			union all "
				   + " 			select sum(Amount) Amount from tBaseItemChg where custcode=? and status='2' "
				   + " 			UNION ALL  "
				   + " 			select isnull(sum(ChgAmount),0) from tConFeeChg where CustCode=? and status='CONFIRMED' "
				   + " 		) a  "
				   + "	) a on a.CustCode = c.Code "
				   + " left join ( "
				   + " 		select isnull(sum(Amount),0) PayFee,? CustCode from tCustPay where CustCode=? "
				   + " )b on b.CustCode = c.Code "
				   + " left join tPayRule tpr on tpr.CustType=c.Custtype and tpr.PayType=c.PayType "
				   + " where c.Code=? ";
		params.add(custCode);
		params.add(custCode);
		params.add(custCode);
		params.add(custCode);
		params.add(custCode);
		params.add(custCode);
		params.add(custCode);
		List<Map<String,Object>> list = this.findBySql(sql, params.toArray());
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Map<String,Object> getSendCount(String no){
		String sql = " select count(1) num from tItemAppSend where IANo=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{no});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String,Object>> goItemAppDetailJqGrid(Page<Map<String,Object>> page,ItemPreMeasure itemPreMeasure){
		Xtcs xtcs = this.get(Xtcs.class, "Titles");
		List<Object> params = new ArrayList<Object>();
		String sql = " SELECT * FROM ( "
				+" 	Select a.SpecReqPK,a.PK,a.No,a.ReqPK,a.FixAreaPK,b.Descr FixAreaDescr,a.IntProdPK,c.Descr IntProdDescr, "
				+" 	a.ItemCode,d.Descr ItemDescr,a.Qty+isnull(a.ShortQty,0) Qty,a.ReqQty,a.SendQty,a.ShortQty,d.allqty,dbo.fGetUseQty(d.Code,'',a.No) userqty, "
				+" 	case when a.ReqPK>0 then isnull(e.ConfirmedQty, 0) else isnull(g.ConfirmedQty, 0) end ConfirmedQty,a.Remarks,a.Cost,a.ProjectCost,tt.remark ReqRemarks, "
				+" 	round(a.Cost*a.Qty,2) AllCost,round(a.ProjectCost*a.Qty,2) AllProjectCost,  "
				+"  case when a.Qty+isnull(a.ShortQty,0)=0 and a.DeclareQty=0 then 0 when a.Qty+isnull(a.ShortQty,0)<>0 and a.DeclareQty=0 then 1 else round((a.DeclareQty-a.Qty-isnull(a.ShortQty,0))/a.DeclareQty,2) end DifferencesPerf,"//增加差异占比 by cjg
			    +" 	isnull(case when d.ItemType2 in ('"+xtcs.getQz().trim().replace(",", "','")+"') and ir.Qty<>0 then round(ir.ProcessCost*a.Qty/ir.Qty,2) else ir.ProcessCost end, 0) ProcessCost, ";
		if("C".equals(itemPreMeasure.getM_umState())){
			if("1".equals(itemPreMeasure.getIsSetItem()) && "JC".equals(itemPreMeasure.getItemType1())){
				sql += " isnull(zgyfh.SendedQty,0) SendedQty,a.Cost*a.Qty SumCost,(isnull(g.ConfirmedQty,0)+isnull(zgyfh.SendedQty,0)+a.Qty-a.ReqQty)*a.Cost as Differences, ";
			}else{
				sql += " isnull(a.SendedQty,0) SendedQty,a.Cost*a.Qty SumCost,(isnull(e.ConfirmedQty,0)+a.SendedQty+a.Qty-a.ReqQty)*a.Cost as Differences, ";
			}
		}else{
			sql += " d.Cost*a.Qty SumCost,(isnull(e.ConfirmedQty,0)+a.SendedQty+a.Qty-a.ReqQty)*d.Cost as Differences, ";
		}
		if("1".equals(itemPreMeasure.getIsSetItem()) && "JC".equals(itemPreMeasure.getItemType1())){
			sql += " d.Price,(isnull(g.ConfirmedQty,0)+isnull(zgyfh.SendedQty,0)+a.Qty-a.ReqQty)*d.Price as PriceDifferences, ";
		}else{
			sql += " ir.UnitPrice Price,(isnull(e.ConfirmedQty,0)+a.SendedQty+a.Qty-a.ReqQty)*d.Price as PriceDifferences, ";
		}
		sql+= " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,u.Descr UomDescr,d.color,d.SqlCode,sql.Descr SqlcodeDescr,spl.Descr SupplCodeDescr, "
			 + " case when a.ReqPK>0 then isnull(f.ApplyQty, 0) else isnull(h.ApplyQty, 0) end ApplyQty,x1.NOTE SendTypeDescr,ir.ProcessCost ReqProcessCost,d.PerWeight,d.PerNum,a.Qty*d.PerWeight Weight,a.Qty*d.PerNum Num, "
			 + " case when d.PackageNum=1 or d.PackageNum=0 or a.Qty*d.PerNum<d.PackageNum then cast (cast(a.Qty*d.PerNum as float) as nvarchar(100)) "
			 + " when a.Qty*d.PerNum/d.PackageNum = floor(a.Qty*d.PerNum/d.PackageNum) then cast(cast(a.Qty*d.PerNum/d.PackageNum as int) as nvarchar(100))+'箱' "
			 + " else cast(cast(floor(a.Qty*d.PerNum/d.PackageNum) as int) as nvarchar(100))+'箱'+ cast(cast(a.Qty*d.PerNum-d.PackageNum*floor(a.Qty*d.PerNum/d.PackageNum) as float) as nvarchar(100))+'片' end NumDescr "
			 + " ,a.DeclareQty,a.PreAppDTPK "  // 增加申报数量 by zjf 
			 + " from (" 
			 + " 	select c.CustCode,a.SpecReqPK,a.PK,a.No,a.ReqPK,a.FixAreaPK,a.IntProdPK,a.ItemCode,a.Qty,";
		if("1".equals(itemPreMeasure.getIsSetItem()) && "JC".equals(itemPreMeasure.getItemType1())){
			sql+="isnull(sir.Qty, 0) ReqQty";
		}else{
			sql+="isnull(b.Qty, 0) ReqQty";
		}
		sql+=",a.SendQty,a.ShortQty,"
	         + " 	case when b.SendQty is not null then b.SendQty "
			 + " 	else ( "
	         + " 		select sum(case when ia.Type='S' then iad.SendQty else (-1.0)*iad.SendQty end) from tItemApp ia inner join tItemAppDetail iad on iad.No=ia.No "
			 + " 		where ia.CustCode=c.CustCode and iad.ItemCode=a.ItemCode and not(iad.ReqPK>0) and ia.Status in ('SEND','RETURN') "
	         + " 	) end as SendedQty, "
	         + " 	a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.Cost,a.ProjectCost,a.AftQty,AftCost,a.PreAppDTPK, "
	         + " 	b.remark ReqRemarks,a.DeclareQty "
	         + "	FROM tItemAppDetail a "
	         + " 	inner join tItemApp c on c.No=a.No "
	         + " 	left outer join tItemReq b on a.ReqPK = b.PK "
	         + " 	left outer join tSpecItemReqDt sir on a.SpecReqPK = sir.PK "
	         + " 	where a.No=? ";
		params.add(itemPreMeasure.getAppNo());
		if("Q".equals(itemPreMeasure.getM_umState())){
			sql += " and a.Qty<>a.DeclareQty ";
		}
	    sql += " ) a "
             + " left outer join ( "
             + "   SELECT i2.ReqPK,sum(Qty-SendQty) ConfirmedQty  FROM tItemAppDetail i2 "
             + "   inner join tItemApp i1 on  i2.No=i1.No "
             + "   WHERE i1.Status='CONFIRMED' and i2.reqpk>0 and i1.CustCode=? "
             + "   group by i2.ReqPk ) e on e.ReqPk = a.ReqPK  ";
		params.add(itemPreMeasure.getCustCode());
		if("1".equals(itemPreMeasure.getIsSetItem()) && "JC".equals(itemPreMeasure.getItemType1())){
			sql += "left join (select b.SpecReqPK,sum(Qty-SendQty) ConfirmedQty "
				+ "   from tItemApp a "
				+ "   left join tItemAppDetail b on a.No=b.No  "
				+ "   where a.Status ='CONFIRMED'  and a.no<>? and CustCode=? "
				+ "   group by b.SpecReqPK)g on a.SpecReqPK=g.SpecReqPK ";
			params.add(itemPreMeasure.getAppNo());
			params.add(itemPreMeasure.getCustCode());
		}else{
			sql += " left outer join ( "
					+ "   SELECT i2.ItemCode,sum(Qty-SendQty) ConfirmedQty  FROM tItemAppDetail i2 "
					+ "   inner join tItemApp i1 on  i2.No=i1.No "
					+ "   WHERE i1.Status='CONFIRMED' and not(i2.ReqPK>0) and i1.CustCode=? "
					+ "   group by i2.ItemCode ) g on g.ItemCode = a.ItemCode  ";
			params.add(itemPreMeasure.getCustCode());
		}
        sql += " left outer join ( "
        	 + "   SELECT i2.ReqPK,sum(case when i1.Type='S' then Qty else -Qty end) ApplyQty  FROM tItemAppDetail i2 "
        	 + "   inner join tItemApp i1 on  i2.No=i1.No "
        	 + "   WHERE (i1.Status='OPEN' or i1.Status='CONRETURN' or i1.Status='WAITCON') and i2.reqpk>0 and i1.CustCode=? "
        	 + "   and i1.No<>? "
        	 + "   group by i2.ReqPk) f on f.ReqPk = a.ReqPK  ";
        params.add(itemPreMeasure.getCustCode());
        params.add(itemPreMeasure.getAppNo());
        if("1".equals(itemPreMeasure.getIsSetItem()) && "JC".equals(itemPreMeasure.getItemType1())){
	         sql+= "left join (select b.SpecReqPK,sum(case when a.Type='S' then Qty else 0 end) ApplyQty "
	        	+ "    from tItemApp a "
	        	+ "    left join tItemAppDetail b on a.No=b.No "
	        	+ "    where a.Status in ('OPEN','CONRETURN','WAITCON')  and a.no<>? and CustCode=? "
	        	+ "    group by b.SpecReqPK )h on a.SpecReqPK=h.SpecReqPK ";
	         params.add(itemPreMeasure.getAppNo());
	         params.add(itemPreMeasure.getCustCode());
        }else{
            sql += " left outer join ( "
               	 + "   SELECT i2.ItemCode,sum(case when i1.Type='S' then Qty else -Qty end) ApplyQty  FROM tItemAppDetail i2 "
               	 + "   inner join tItemApp i1 on  i2.No=i1.No "
               	 + "   WHERE (i1.Status='OPEN' or i1.Status='CONRETURN' or i1.Status='WAITCON') and not(i2.reqpk>0) and i1.CustCode=? "
               	 + "   and i1.No<>? "
               	 + "   group by i2.ItemCode) h on h.ItemCode = a.ItemCode  ";
               params.add(itemPreMeasure.getCustCode());
               params.add(itemPreMeasure.getAppNo());
        }
        if("1".equals(itemPreMeasure.getIsSetItem()) && "JC".equals(itemPreMeasure.getItemType1())){
        	sql+= "left join (select b.SpecReqPK,sum(case when a.Type='S' then Qty else -Qty end) SendedQty  "
        			+ "           from tItemApp a "
        			+ "           left join tItemAppDetail b on a.No=b.No "
        			+ "           where a.Status in ('SEND','RETURN') and a.no<>? and CustCode=? and b.SpecReqPk > 0 "
        			+ "           group by b.SpecReqPK)zgyfh on a.SpecReqPK=zgyfh.SpecReqPK ";
	         params.add(itemPreMeasure.getAppNo());
	         params.add(itemPreMeasure.getCustCode());
       }
        sql += " left outer join tFixArea b on a.FixAreaPK = b.PK "
        	 + " left outer join tIntProd c on a.IntProdPK = c.PK "
        	 + " left outer join tItemReq ir on ir.Pk=a.ReqPK "
        	 + " left outer join tItem d on a.ItemCode = d.Code "
        	 + " left outer join tBrand sql on sql.code=d.sqlcode "
        	 + " left outer join tSupplier spl on spl.Code=d.SupplCode "
        	 + " left outer join tItemReq tt on a.ReqPK = tt.PK  "
        	 + " left outer join tUom u on u.code=d.uom "
        	 + " left outer join tXTDM x1 on x1.ID='ITEMAPPSENDTYPE' AND x1.CBM=d.SendType "
        	 + " left join tWareHouse wh on wh.Code = d.WHCode ";
        if(StringUtils.isNotBlank(itemPreMeasure.getSeqpks())){
        	sql += " where a.SpecReqPK in ("+itemPreMeasure.getSeqpks()+")";
        }
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.itemCode ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public Map<String,Object> checkCost(String custCode,String materWorkType2){
		List<Object> params = new ArrayList<Object>();
		String sql = " select dbo.fGetCustBaseCost_Com(?,2,?) itemCost,dbo.fGetCustBaseCtrl_Com(?,2,?) itemCtrl,"
				   + " dbo.fGetCustBaseCost_Com(?,3,'') allItemCost,isnull(dbo.fGetCustBaseCtrl_Com(?,3,''),0) allItemCtrl";
		params.add(custCode);
		params.add(materWorkType2);
		params.add(custCode);
		params.add(materWorkType2);
		params.add(custCode);
		params.add(custCode);

		List<Map<String,Object>> list = this. findBySql(sql, params.toArray());
		if(list != null && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}
	
	public Result checkQty(String custCode,String appNo ,String itemType1,String xml){
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLlglCheckQty_forXml(?,?,?,?,?,?,?,?)}");
			call.setString(1, appNo);
			call.setString(2, custCode);
			call.setString(3, itemType1);
			call.setString(4, xml);
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.registerOutParameter(7, Types.NVARCHAR);
			call.registerOutParameter(8, Types.NUMERIC);
			call.execute();
			result.setCode(String.valueOf(call.getInt(5)));
			result.setInfo(call.getString(6));
			result.setJson(call.getString(7));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public List<Map<String,Object>> checkQtyByItemType2(String custCode,String appNo ,String itemType1){
		
		List<Object> params = new ArrayList<Object>();
		
		String sql = " select  b.ItemType2, c.Descr ItemType2Descr, sum(( a.Qty - a.SendQty - a.ConfirmedQty - a.ApplyQty ) * a.Cost) * -1 Amount "
				   + " from ( "
				   + " 		select a.ItemCode, a.Qty, a.SendQty, a.Cost, isnull(b.ConfirmedQty, 0) ConfirmedQty, isnull(c.ApplyQty, 0) ApplyQty "
				   + " 		from tItemReq a "
				   + " 		left join ( "
				   + " 			select  a.ReqPK, sum(a.Qty - a.SendQty) ConfirmedQty "
				   + " 			from tItemAppDetail a "
				   + " 			inner join tItemApp b on a.No = b.No "
				   + " 			where b.Status = 'CONFIRMED' and a.ReqPK > 0 "
				   + " 			group by a.ReqPK "
				   + " 		) b on a.PK = b.ReqPK "
				   + " 		left join ( "
				   + "  		select  a.ReqPK, sum(a.Qty) ApplyQty "
				   + " 			from tItemAppDetail a "
				   + "  		inner join tItemApp b on a.No = b.No "
				   + " 			where a.No = ? and a.ReqPK > 0 "
				   + " 			group by a.ReqPK "
				   + " 		) c on a.PK = c.ReqPK "
				   + " 		where a.CustCode=? and a.ItemType1=? "
				   + " ) a "
				   + " inner join tItem b on a.ItemCode = b.Code "
				   + " inner join tItemType2 c on b.ItemType2 = c.Code "
				  // + " where a.Qty - a.SendQty - a.ConfirmedQty - a.ApplyQty < 0 "
				   + " where a.Qty - a.SendQty  - a.ApplyQty < 0 "//本单有材料采购数量超预算数量时才提示   by zjf
				   + " and exists (select 1 from tItemAppDetail in_a inner join tItem in_b on in_a.ItemCode=in_b.Code where in_a.No=? and in_b.ItemType2=b.ItemType2 ) "
				   + " group by b.ItemType2, c.Descr ";
		params.add(appNo);
		params.add(custCode);
		params.add(itemType1);
		params.add(appNo);
		return this.findBySql(sql, params.toArray());
	}
	
	public Map<String,Object> checkCustStatus(String custCode){
		String sql = " select b.NOTE StatusDescr,status,c.NOTE CheckStatusDescr,CheckStatus from tcustomer a "
				   + " left outer join tXTDM b on a.status=b.CBM and b.id='CUSTOMERSTATUS' "
				   + " left outer join tXTDM c on a.checkstatus=c.CBM and c.id='CHECKSTATUS' "
				   + " where a.code=? ";
		List<Map<String,Object>> list = this. findBySql(sql, new Object[]{custCode});
		if(list != null && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}
	
	public Map<String,Object> checkCustPay(String appNo){
		String sql = " select top 1 c.PayNum,c.PayPer,c.DiffAmount,c.Prior,c.ItemCost,isnull(b.StdDesignFee, 0) StdDesignFee,b.Custtype, "
				   + " isnull(tprd.ChgPer, 1) ChgPer,(" 
				   + "		select case when sum(case when IsRcvDesignFee='1' then 1 else 0 end)>0 then '1' else '0' end "
				   + " 		from tPayRuleDetail tprdin where tprd.no=tprdin.no and tprdin.PayNum<=tprd.paynum) IsRcvDesignFee,isnull(tpr.DesignFeeType, '1') DesignFeeType "
				   + " from tItemApp a "
				   + " inner join tCustomer b on a.CustCode=b.Code "
				   + " inner join tItemAppConfRule c on c.Expired='F' and a.ItemType1=c.ItemType1 and (c.CustType is null or c.CustType='' or c.CustType=b.CustType) "
				   + " 									and (c.PayType is null or c.PayType='' or c.PayType=b.PayType) "
				   + " inner join tPayRule tpr on b.CustType = tpr.CustType and b.PayType = tpr.PayType "
				   + " inner join tPayRuleDetail tprd on tprd.No = tpr.No and tprd.PayNum = c.PayNum " 
				   + " where a.No=? "
				   + " and exists ( "
				   + " 		select 1 from tItemAppDetail in_a "
				   + " 		inner join tItem in_b on in_a.ItemCode=in_b.Code "
				   + " 		inner join tItemAppConfRuleDetail in_c on in_c.No=c.No and in_c.Expired='F' "
				   + " 												and (in_c.ItemType2 is null or in_c.ItemType2='' or in_c.ItemType2=in_b.ItemType2) "
				   + " 												and (in_c.ItemType3 is null or in_c.ItemType3='' or in_c.ItemType3=in_b.ItemType3) "
				   + " 												and (in_c.ItemDesc is null or in_c.ItemDesc='' or in_b.Descr like '%'+in_c.ItemDesc+'%' ) "
				   + " 		where in_a.No=a.No "
				   + " ) "
				   + " and (c.ItemCost=0 or (select sum(in_a.Qty*in_a.Cost) from tItemAppDetail in_a where in_a.No=a.No)<=c.ItemCost) "
				   + " order by c.Prior desc,c.PayNum desc ";
		List<Map<String,Object>> list = this. findBySql(sql, new Object[]{appNo});
		if(list != null && list.size() > 0 ){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String,Object>> goCodeJqGrid(Page<Map<String,Object>> page,ItemPreApp itemPreApp){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( " 
		           + " 		select a.No,a.CustCode,b.Descr CustDescr,b.Address,a.IsSetItem,c.NOTE IsSetItemDescr, "
				   + " 		a.Status,d.NOTE StatusDescr,a.AppCZY,e.NameChi AppCZYDescr,a.Date, "
		           + " 		a.ConfirmCZY,f.NameChi ConfirmCZYDescr,a.ConfirmDate,a.Remarks, "
				   + " 		a.ItemType1,g.Descr ItemType1Descr, " 
		           + " 		a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
		           + " 		from tItemPreApp a "
		           + " 		left join tCustomer b on a.CustCode=b.Code "
		           + " 		left join tXTDM c on c.ID='YESNO' and a.IsSetItem=c.CBM "
		           + " 		left join tXTDM d on d.ID='PREAPPSTATUS' and a.Status=d.CBM "
		           + "		 left join tEmployee e on a.AppCZY=e.Number "
		           + " 		left join tEmployee f on a.ConfirmCZY=f.Number "
		           + " 		left join tItemType1 g on a.ItemType1=g.Code "
		           + " 		where 1=1 ";
		if(StringUtils.isNotBlank(itemPreApp.getRequestPage())){
			if("listAddll".equals(itemPreApp.getRequestPage())){
				sql += " and a.Status in ('3','4') and ConfirmCZY=? ";
				params.add(itemPreApp.getCzybh());
			}
		}
		if(StringUtils.isNotBlank(itemPreApp.getNo())){
			sql += " and a.No=? ";
			params.add(itemPreApp.getNo());
		}
		if(StringUtils.isNotBlank(itemPreApp.getCustCode())){
			sql += " and a.CustCode=? ";
			params.add(itemPreApp.getCustCode());
		}
		if(StringUtils.isNotBlank(itemPreApp.getItemType1())){
			sql += " and a.ItemType1=? ";
			params.add(itemPreApp.getItemType1());
		}
		if(itemPreApp.getAppDateFrom() != null){
			sql += " and a.Date >= ? ";
			params.add(DateUtil.startOfTheDay(itemPreApp.getAppDateFrom()));
		}
		if(itemPreApp.getAppDateTo() != null){
			sql += " and a.Date < ? ";
			params.add(DateUtil.addDate(itemPreApp.getAppDateTo(), 1));
		}
		if(StringUtils.isNotBlank(itemPreApp.getStatus())){
			sql += " and a.Status in ('"+itemPreApp.getStatus().trim().replace(",", "','")+"') ";
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate Desc ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public Page<Map<String,Object>> goCodeDetailJqGrid(Page<Map<String,Object>> page,String no){
		String sql = " select * from ( "
				   + "  	select ipad.PK,ipad.ItemCode,ipad.Qty,ipad.ReqPK,ir.CustCode,ir.FixAreaPK,fa.Descr FixAreaDescr, "
				   + " 		ir.IntProdPK,ip.Descr IntProdDescr,sp.Descr SupplDescr, "
				   + " 		i.Descr ItemDescr,ir.ItemType1,it1.Descr TypeDescr,ir.Qty ReqQty, "
				   + " 		it2.Descr ItemType2Descr,ir.SendQty,ipad.Remarks,i.Cost "
				   + " 		from tItemPreAppDetail ipad "
				   + " 		left outer join tItemReq ir on ipad.ReqPK=ir.PK "
				   + " 		left outer join tFixArea fa on ir.FixAreaPK=fa.PK "
				   + " 		left outer join tIntProd ip on ir.IntProdPK=ip.PK "
				   + " 		left outer join tItem i on ipad.ItemCode=i.Code "
				   + " 		left outer join tSupplier sp on sp.Code=i.SupplCode "
				   + " 		left outer join tItemType1 it1 on ir.ItemType1=it1.Code "
				   + " 		left outer join tItemType2 it2 on i.Itemtype2=it2.Code "
				   + " 		where ipad.No = ? "
				   + " 		and not exists(select 1 from tItemApp ia inner join tItemAppDetail iad on ia.No=iad.No where ia.PreAppNo=ipad.No and iad.PreAppDTPK=ipad.PK) ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.PK ";
		}
		return this.findPageBySql(page, sql, new Object[]{no});
	}
	
	public Page<Map<String,Object>> goJqGridSend(Page<Map<String,Object>> page, String no){
		String sql = " select * from ( "
				   + " 		select a.*,b.Desc1 WHCodeDescr,d.NameChi DriverName, "
		           + "      e.NOTE DelivTypeDescr "
				   + "      from tItemAppSend a "
				   + " 		left outer join tWareHouse b on a.WHCode=b.code "
				   + " 		left outer join tItemSendBatch c on a.SendBatchNo=c.No "
				   + " 		left outer join tDriver d on c.DriverCode=d.Code "
				   + " 		left outer join tXTDM e on a.DelivType = e.CBM and e.ID = 'DELIVTYPE' "
				   + " 		where IANo=? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, new Object[]{no});
	}
	
	public Page<Map<String,Object>> goJqGridSendMx(Page<Map<String,Object>> page, String no){
		String sql = " select * from ( "
				   + " 		select b.Descr ITDescr,a.pk,c.Itemcode,a.sendQty,a.color,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog from tItemAppSendDetail a "
				   + " 		left outer join tItemAppDetail c on a.RefPK=c.pk "
				   + " 		left outer join tItem b on c.Itemcode=b.Code "
				   + " 		where a.no=? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.pk ";
		}
		return this.findPageBySql(page, sql, new Object[]{no});
	}
	
	public Map<String, Object> getItemInfoByCode(String itemCode, String itemType1, String itemType2, String no, String custCode){
		List<Object> params = new ArrayList<Object>();
		String sql = " select a.Code,a.Descr,a.ItemSize,a.SizeDesc,  "
				   + " a.BarCode,a.RemCode,a.ItemType1,i1.Descr ItemType1Descr, "
				   + " a.ItemType2,i2.Descr ItemType2Descr,a.ItemType3,i3.Descr ItemType3Descr, "
				   + " a.SqlCode,b.Descr SqlDescr,a.Uom,u.Descr UomDescr,a.SupplCode,s.Descr SplCodeDescr, "
				   + " a.Model,a.Color,a.Remark,a.CommiType, "
				   + " d.NOTE CommiTypeDescr,a.Price,a.MarketPrice,a.IsFixPrice, "
				   + " p.Note IsFixPriceDescr,a.Cost,a.AvgCost,a.AllQty,a.ProjectCost, "
				   + " a.ActionLog,x1.NOTE SendTypeDescr, "
				   + " case when a.itemType1=? and (a.itemType2 = ? or ?='' or ? is null) then 1 else 0 end itemTypeCheck,ir.ProcessCost ReqProcessCost, "
				   + " isnull(h.ApplyQty, 0) ApplyQty,isnull(g.ConfirmedQty, 0) ConfirmedQty,a.PerWeight,a.PackageNum,a.PerNum,a.SendType,a.SupplCode,a.WHCode "
				   + " from tItem a "
				   + " left outer join tItemType1 i1 on a.ItemType1=i1.Code "
				   + " left outer join tItemType2 i2 on a.ItemType2=i2.Code "
				   + " left outer join tItemtype3 i3 on a.ItemType3=i3.Code "
				   + " left outer join tBrand b on a.SqlCode=b.Code "
				   + " left outer join tXTDM d on a.CommiType=d.CBM and d.ID='COMMITYPE' "
				   + " left outer join tXTDM p on a.IsFixPrice=p.CBM and p.ID='ISFIXPRICE' "
				   + " left outer join tSupplier s on a.SupplCode=s.Code "
				   + " left outer join tUom u on a.Uom = u.Code "
				   + " left outer join tXTDM x1 on x1.ID='ITEMAPPSENDTYPE' AND x1.CBM=a.SendType "
				   + " left outer join tItemReq ir on ir.Pk=0 "
				   + " left outer join ( "
		           + " 		SELECT i2.ItemCode,sum(case when i1.Type='S' then Qty else -Qty end) ApplyQty  FROM tItemAppDetail i2 "
		           + " 		inner join tItemApp i1 on  i2.No=i1.No "
		           + " 		WHERE (i1.Status='OPEN' or i1.Status='CONRETURN' or i1.Status='WAITCON') and not(i2.reqpk>0) and i1.CustCode =? "
		           + "   	and i1.No<>? "
		           + "   	group by i2.ItemCode) h on h.ItemCode = a.Code "
		           + " left outer join ( "
		           + "   	SELECT i2.ItemCode,sum(Qty-SendQty) ConfirmedQty  FROM tItemAppDetail i2 "
		           + "   	inner join tItemApp i1 on  i2.No=i1.No "
		           + "   	WHERE i1.Status='CONFIRMED' and not(i2.ReqPK>0) and i1.CustCode = ? "
		           + "   	group by i2.ItemCode) g on g.ItemCode = a.Code "
				   + " where a.Expired='F' and a.Code = ? ";
		params.add(itemType1);
		params.add(itemType2);
		params.add(itemType2);
		params.add(itemType2);
		params.add(custCode);
		params.add(no);
		params.add(custCode);
		params.add(itemCode);
		List<Map<String, Object>> list = this.findBySql(sql, params.toArray());
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Code", itemCode);
		map.put("codeError", "1");
		map.put("itemTypeCheck", "0");
		return map;
	}
	
	public Map<String, Object> getIntProd(String descr, String custCode){
		String sql = " select tip.PK intProdPk,tip.FixAreaPK,tip.Descr intProdDescr,tfa.Descr fixAreaDescr "
				   + " from tIntProd tip "
				   + " left join tFixArea tfa on tip.FixAreaPK = tfa.PK "
				   + " where tip.Descr=? and tfa.CustCode=? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{descr, custCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String,Object>> getWorkerAppItemList(Page<Map<String,Object>> page, Integer cwPK,String custCode){

		String sql = " select (select in_c.Descr from tItemPreAppDetail in_a " +
				" left join titem in_b on in_b.Code=in_a.ItemCode" +
				" left join tItemType2 in_c on in_c.Code=in_b.itemtype2 " +
				" where PK=e.pk) workType12Descr ," +
				" (select sum(in_a.Qty*in_b.ProjectCost)" +
				" from titempreappdetail in_a " + 
				" left join titem in_b on in_b.code=in_a.itemcode " +
				" where No=e.no) price," +
				" a.no,c.address addres,d.Descr itemType1Descr ,x1.note statusDescr,a.Date from tItemPreApp a " +
				" left join tCustomer  c on c.Code=a.CustCode" +
				" left join tItemType1 d on d.Code=a.ItemType1" +
				" left join tXTDM x1 on x1.CBM=a.Status and x1.ID='PREAPPSTATUS' " +
				" left join (select MAX(pk) pk,no from tItemPreAppDetail group by No ) e on e.No =a.No " +
				" where 1=1 and a.workerCode= (select workerCode from tCustWorker where pk = ? ) " +
				" and a.custCode = ? order by a.lastupdate desc ";
				

		return this.findPageBySql(page, sql, new Object[]{cwPK,custCode});
	}
	
	public Page<Map<String,Object>> getWorkerItemApp(Page<Map<String,Object>> page, String no){

		String sql = " select a.ItemType1,c.Address Addres,b.Descr itemtype1Descr,a.Remarks Remark ,a.Date,x1.NOTE statusDescr,a.IsSetItem,e.NameChi recipient," +
				" a.ConfirmDate from tItemPreApp a " +
				" left join tCustomer c on c.Code=a.CustCode" +
				" left join tXTDM x1 on x1.CBM=a.Status and x1.ID='PREAPPSTATUS'" +
				" left join tEmployee e on e.Number=a.ConfirmCZY " +
				" left Join tItemType1 b on b.Code=a.ItemType1" +
				" where 1=1 and a.No = ?  ";
				

		return this.findPageBySql(page, sql, new Object[]{no});
	}
	
	public Page<Map<String,Object>> getItemAppDetail(Page<Map<String,Object>> page, String no){

		String sql = " select a.reqPk,a.bdPk,b.code itemCode,b.itemType1,b.Descr itemDescr,a.Qty ,m.qty xdQty,d.Descr fixDescr,a.Remarks,e.descr uomDescr  from  tItemPreAppDetail a " +
				" left join  titem b on b.Code=a.ItemCode" +
				" left join titemreq c on c.PK = a.ReqPK" +
				" left join tFixArea d on d.PK=c.FixAreaPK " +
				" left  join tUom e on e.code=b.uom " +
				" left join tItemAppDetail m on a.pk=m.preAppDtPk "
			+ 	" left join tItemApp n on m.no=n.no "+
				" where a.No= ? ";
				

		return this.findPageBySql(page, sql, new Object[]{no});
	}
	
	
	public Page<Map<String,Object>> goJqGridSendCountView(Page<Map<String,Object>> page, String no){
		String sql = " select * from ( "
				   + " 		select b.no,b.iANo,b.itemType1Descr,b.itemType2Descr,b.date "
				   + " 		from tItemApp a "
				   + " 		left join ( "
				   + " 			select tias.No,tias.IANo,tit1.Descr itemType1Descr,tit2.Descr itemType2Descr,tias.Date,tia.CustCode,tias.WHCode,tias.SupplCode "
				   + " 			from tItemAppSend tias "
				   + " 			left join tItemApp tia on tias.IANo = tia.No "
				   + " 			left join tItemType1 tit1 on tit1.Code = tia.ItemType1 "
				   + "			left join tItemType2 tit2 on tit2.Code = tia.ItemType2 "
				   + " 			where tia.DelivType <> '3' "
				   + " 		) b on a.CustCode = b.CustCode and ((a.SendType = '1' and a.SupplCode = b.SupplCode) or (a.SendType = '2' and a.WHCode = b.WHCode)) "
				   + " 		where a.No=? and b.No is not null and b.No <> '' and a.delivType='1' ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.date ";
		}
		return this.findPageBySql(page, sql, new Object[]{no});
	}
	
	public Page<Map<String,Object>> goJqGirdUnItemAppMaterial(Page<Map<String,Object>> page, String custCode, String itemType1, String itemType2, String whCode, String supplCode){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( "
				   + " 		select tir.pk,tfa.Descr fixAreaDescr, ti.Descr itemDescr,(isnull(tir.Qty, 0)-isnull(tir.SendQty,0)) Qty, tuom.Descr uomDescr,tir.remark "
				   + " 		from tItemReq tir "
				   + " 		left join tFixArea tfa on tir.FixAreaPK = tfa.PK "
				   + " 		left join tItem ti on ti.Code = tir.ItemCode "
				   + " 		left join tUOM tuom on tuom.Code = ti.Uom "
				   + " 		where tir.Expired='F' and tir.CustCode=? and not exists ( "
				   + " 			select 1 "
				   + " 			from tItemApp  in_tia "
				   + " 			left join tItemAppDetail in_tiad on in_tia.No = in_tiad.No "
				   + "			where in_tiad.ReqPK = tir.PK and in_tia.Status in ('OPEN', 'CONFIRMED', 'SEND') "
				   + " 		) and isnull(tir.Qty, 0)-isnull(tir.SendQty,0) > 0 ";
		params.add(custCode);
		if(StringUtils.isNotBlank(itemType1)){
			sql += " and ti.ItemType1=? ";
			params.add(itemType1);
		}
		if(StringUtils.isNotBlank(itemType2)){
			sql += " and ti.ItemType2=? ";
			params.add(itemType2);
		}
		if(StringUtils.isNotBlank(supplCode)){
			sql += " and ti.SupplCode=? ";
			params.add(supplCode);
		}
		if(StringUtils.isNotBlank(whCode)){
			sql += " and ti.WhCode=? ";
			params.add(whCode);
		}
		
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public Integer getSendCountNum(String no){
		String sql = " select count(1) num from tItemApp in_a "
				   + " left join ( select in_tias.Date,in_tia.CustCode,in_tias.WHCode,in_tias.SupplCode,in_tias.No "
				   + " 		from tItemAppSend in_tias "
				   + "		left join tItemApp in_tia on in_tias.IANo = in_tia.No "
				   + " 		where in_tia.DelivType <> '3' "
				   + " ) in_b on in_a.CustCode = in_b.CustCode and ((in_a.SendType = '1' and in_a.SupplCode = in_b.SupplCode) or (in_a.SendType = '2' and in_a.WHCode = in_b.WHCode)) "
				   + " where in_a.No=?  and in_b.No is not null and in_b.No <> '' and in_a.delivType='1' "
				   + " group by convert(nvarchar(100), in_b.Date, 112) ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{no});
		if(list != null && list.size() > 0){
			return list.size();
		}
		return 0;
	}
	
	public Map<String,Object> getRatedMatrial(String custCode,String workType12){
		String sql = " select sum(a.Material*a.Qty) RatedMatrial from tBaseCheckItemPlan a "
					+" left join tBaseCheckItem b on b.Code=a.BaseCheckItemCode "
					+" where 1=1 and a.CustCode=? and b.WorkType12=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,workType12});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String, Object>> goJddrJqGrid(
			Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from( select * from (select a.LastUpdate,a.PK specreqpk,g.Descr fixareadescr,f.Descr intproddescr,e.Descr itemtype2descr, "
				+ "a.ItemCode,b.Descr itemdescr,c.Descr supplcodedescr,x1.NOTE sendtypedescr, "
				+ "f.FixAreaPK,a.IntProdPK,b.ItemType2,a.ItemType1,b.SupplCode, b.SendType,a.CustCode, "
				+ "isnull(ysq.ApplyQty,0)ApplyQty,0 SendQty,isnull(ysh.ConfirmedQty,0)ConfirmedQty,isnull(zgyfh.sendedqty,0) sendedqty , "
				+ "a.qty reqqty,a.qty-isnull(ysq.ApplyQty,0)-isnull(zgyfh.SendedQty,0)-isnull(ysh.ConfirmedQty,0) qty, "
				+ "a.qty-isnull(ysq.ApplyQty,0)-isnull(zgyfh.SendedQty,0)-isnull(ysh.ConfirmedQty,0) declareqty, "
				+ "h.Descr sqlcodedescr,i.Descr UomDescr,b.color,b.ProjectCost,b.Cost,0 shortQty,0 processcost,0 weight,0 numdescr, "
				+ "round(b.Cost*(a.qty-isnull(ysq.ApplyQty,0)-isnull(zgyfh.SendedQty,0)-isnull(ysh.ConfirmedQty,0)),2) AllCost, "
				+ "round(b.ProjectCost*(a.qty-isnull(ysq.ApplyQty,0)-isnull(zgyfh.SendedQty,0)-isnull(ysh.ConfirmedQty,0)),2) AllProjectCost, "
				+ "b.price,0 differences,0 pricedifferences,0 DifferencesPerf,a.remark remarks,b.allqty,dbo.fGetUseQty(b.Code,'',?) userqty,"
				+ "a.qty-isnull(ysq.ApplyDeclareQty,0)-isnull(zgyfh.SendedDeclareQty,0)-isnull(ysh.ConfirmedDeclareQty,0) declareQtyForCheck  "// 根据申报数量算的解单-已申请-已审核-已发货
				+ "from tSpecItemReqDt a "
				+ "left join tItem b on a.ItemCode=b.Code "
				+ "left join tSupplier c on c.Code=b.SupplCode and b.Code = a.ItemCode "
				+ "left join tItemType1 d on a.ItemType1=d.Code "
				+ "left join tItemType2 e on b.Itemtype2=e.Code "
				+ "left join tIntProd f on a.IntProdPK =f.PK "
				+ "left join tFixArea g on f.FixAreaPK=g.PK "
				+ "left join tBrand h on b.sqlcode=h.Code "
				+ "left join tUom i on i.code=b.uom "
				+ "left join tXTDM x1 on x1.ID='ITEMAPPSENDTYPE' AND x1.CBM=b.SendType "
				+ "left join (select a.CustCode,b.SpecReqPK,sum(case when a.Type='S' then Qty else 0 end) ApplyQty,"
				+ "sum(case when a.Type='S' then DeclareQty else 0 end) ApplyDeclareQty "
				+ "           from tItemApp a "
				+ "           left join tItemAppDetail b on a.No=b.No "
				+ "           where a.Status in ('OPEN','CONRETURN','WAITCON') and a.no<>? "
				+ "           group by a.CustCode,b.SpecReqPK )ysq on a.CustCode=ysq.CustCode and a.PK=ysq.SpecReqPK "
				+ "left join (select a.CustCode,b.SpecReqPK,sum(Qty-SendQty) ConfirmedQty,sum(DeclareQty-SendQty) ConfirmedDeclareQty "
				+ "           from tItemApp a "
				+ "           left join tItemAppDetail b on a.No=b.No  "
				+ "           where a.Status ='CONFIRMED' and a.no<>? "
				+ "           group by a.CustCode,b.SpecReqPK)ysh on a.CustCode=ysh.CustCode and a.PK=ysh.SpecReqPK "
				+ "left join (select a.CustCode,b.SpecReqPK,sum(case when a.Type='S' then Qty else -Qty end) SendedQty,"
				+ "sum(case when a.Type='S' then DeclareQty else -DeclareQty end) SendedDeclareQty  "
				+ "           from tItemApp a "
				+ "           left join tItemAppDetail b on a.No=b.No "
				+ "           where a.Status in ('SEND','RETURN') and a.no<>? "
				+ "           group by a.CustCode,b.SpecReqPK)zgyfh on a.CustCode=zgyfh.CustCode and a.PK=zgyfh.SpecReqPK "
				+ "where a.Expired='F' and a.iscupboard=? "
				+ "and a.pk not in ('"
				+ itemPreMeasure.getSeqpks().trim().replace(",", "','")
				+ "') "
				+ "and a.custcode=?";
		if ("保存时生成".equals(itemPreMeasure.getAppNo())) {
			itemPreMeasure.setAppNo("");
		}
		list.add(itemPreMeasure.getAppNo());
		list.add(itemPreMeasure.getAppNo());
		list.add(itemPreMeasure.getAppNo());
		list.add(itemPreMeasure.getAppNo());
		list.add(itemPreMeasure.getIsCupboard());
		list.add(itemPreMeasure.getCustCode());
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )b where declareQtyForCheck>0) a order by "
					+ page.getPageOrderBy() + " " + page.getPageOrder();
		} else {
			sql += " )b where declareQtyForCheck>0) a order by a.itemcode asc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public String checkIsSpecReq(String itemType1){
		String sql = " select IsSpecReq from titemtype1 where code=?";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{itemType1});
		if(list != null && list.size() > 0){
			return list.get(0).get("IsSpecReq").toString();
		}
		return null;
	}
	/**
	 * 领料成本
	 * @param page
	 * @param itemPreMeasure
	 * @return
	 */
	public Page<Map<String, Object>> goLlcbJqGrid(
			Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select case when Type='S' and Status<>'CANCEL' then Amount when Type='R' and Status='RETURN' then -Amount end amount, "
				+"Type,no,s1.NOTE statusdescr,s2.NOTE typedescr,a.ItemType1,a.IsCupboard,s3.NOTE IsCupboardDescr,OtherCost,OtherCostAdj,sp.descr suppldescr, "
				+"case when Type='S' and Status<>'CANCEL' then Amount when Type='R' and Status='RETURN' then -Amount end +OtherCost+OtherCostAdj totalamount "
				+"from tItemApp a "
				+"left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='ITEMAPPSTATUS' " 
				+"left outer join tXTDM s2 on a.Type=s2.CBM and s2.ID='ITEMAPPTYPE' "
				+"left outer join tXTDM s3 on a.IsCupboard=s3.CBM and s3.ID='YESNO' "
				+"left outer join tSupplier sp on a.SupplCode=sp.Code "
				+"where a.ItemType1='JC' and CustCode=? " 
				+"and  ((Type='S' and Status<>'CANCEL') or (Type='R' and Status='RETURN')) " 
				+"and IsCupboard=?";
		list.add(itemPreMeasure.getCustCode());
		list.add(itemPreMeasure.getIsCupboard());
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 橱柜安装成本
	 * @param page
	 * @param itemPreMeasure
	 * @return
	 */
	public Page<Map<String, Object>> goCgazcbJqGrid(
			Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.CupUpHigh,a.CupDownHigh, " 
				+"(select QZ from tXTCS where ID='CupInsFee')CupInsFee, " 
				+"(a.CupUpHigh+a.CupDownHigh)*(select QZ from tXTCS where ID='CupInsFee') totalfee " 
				+"from tSpecItemReq a where CustCode=? ";
		list.add(itemPreMeasure.getCustCode());
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 衣柜安装成本
	 * @param page
	 * @param itemPreMeasure
	 * @return
	 */
	public Page<Map<String, Object>> goYgazcbJqGrid(
			Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select d.code itemcode,d.descr itemdescr,isnull(e.IntInstallFee,0)IntInstallFee,c.Qty ,"
				+"e.Descr,isnull(e.IntInstallFee,0)*c.Qty totalfee,f.descr uom "
				+"from tSpecItemReqDt c " 
				+"left join tItem d on c.ItemCode=d.Code  "
				+"inner join tItemType3 e on d.ItemType3=e.Code "
				+"left join tUom f on d.uom=f.code "
				+"where c.CustCode=? and c.IsCupboard='0'";
		list.add(itemPreMeasure.getCustCode());
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 需求安装成本
	 * @param page
	 * @param itemPreMeasure
	 * @return
	 */
	public Page<Map<String, Object>> goXqazcbJqGrid(
			Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select d.code itemcode,d.descr itemdescr,isnull(e.IntInstallFee,0)IntInstallFee,c.Qty ,"
				+"e.Descr,isnull(e.IntInstallFee,0)*c.Qty totalfee,f.descr uom "
				+"from tItemReq c " 
				+"left join tIntProd b on c.IntProdPK=b.PK  "
				+"left join tItem d on c.ItemCode=d.Code  "
				+"inner join tItemType3 e on d.ItemType3=e.Code "
				+"left join tUom f on d.uom=f.code "
				+"where c.CustCode=? and b.IsCupboard=? ";
		list.add(itemPreMeasure.getCustCode());
		list.add(itemPreMeasure.getIsCupboard());
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	public Map<String,Object> getAppliedMoney(String custCode,String workType12){
		String sql = " select round(sum(case when d.Type='S' then d.ProjectAmount else (d.ProjectAmount*-1) end),0) AppliedMoney  from titemapp d "
				+" inner join titemtype2 e on d.ItemType2=e.Code "
				+" inner join tWorkType2 f on e.MaterWorkType2=f.Code "
				+" where d.Status in ('CONFIRMED','SEND','RETURN') and d.CustCode=? and f.Worktype12=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,workType12});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String,Object>> getWaterItemList(	
			Page<Map<String, Object>> page, String custCode){
		String sql = " select a.No,a.Status,a.Date,d.WorkerCode,x1.Note StatusDescr, "
					+" (case when a.Type ='S' then a.ProjectAmount else a.ProjectAmount*-1 end) ProjectAmount, "
					+" (case when d.WorkerCode is not null then d.WorkerCode "
					+" when d.AppCZY is not null and d.WorkerCode is null then d.AppCZY else a.AppCZY end ) AppCzy, "
					+" (case when e.NameChi is not null then e.NameChi "
					+" when f.NameChi is not null and e.NameChi is null then f.NameChi else a.NameChi end) AppCzyDescr "
					+" from(select in_a.CustCode,in_b.NameChi,in_a.ItemType2,in_a.PreAppNo,in_a.Status,in_a.AppCZY,in_a.ProjectAmount,in_a.Date,in_a.No,in_a.Type  "
					+" 	from tItemApp in_a  "
					+" 	left join tEmployee in_b on in_a.AppCZY=in_b.Number) a "
					+" inner join tItemType2 b on a.ItemType2=b.Code "
					+" inner join tWorkType2 c on b.MaterWorkType2=c.Code " 
					+" left join tItemPreApp d on a.PreAppNo=d.No "
					+" left join tWorker e on d.WorkerCode=e.Code "
					+" left join tEmployee f on d.AppCZY=f.Number "
					+" left join tXTDM x1 on x1.CBM=a.Status and x1.ID='ITEMAPPSTATUS' "
					+"  where a.Status in ('CONFIRMED','SEND','RETURN') and a.CustCode= ? and c.Worktype12='02' ";
		return this.findPageBySql(page, sql, new Object[]{custCode});
	}
	
	public Page<Map<String,Object>> getWaterItemDetail(	
			Page<Map<String, Object>> page, String no){
		String sql = " SELECT a.no, a.pk,a.qty,a.sendQty,b.Descr itemCodeDescr,c.descr uom,d.Address,e.NOTE status, "
				   +" t.date,t.confirmDate,t.sendDate,t.arriveDate,t.remarks,CASE WHEN t.SendType='1' THEN f.Descr ELSE g.Desc1 END supplyAddress,t.followRemark,t.IsSetItem, "
				   +" case when t.isSetItem='1' then round(a.ProjectCost*a.Qty,2) else 0 end AllProjectCost "
				   +" FROM tItemAppDetail a "
				   +" inner join tItemApp t on a.no=t.no "
				   +" LEFT JOIN titem b ON a.ItemCode=b.Code left join tuom c on b.uom=c.code "
				   +" INNER JOIN tCustomer d ON t.CustCode=d.Code "
				   +" INNER JOIN tXTDM e ON e.ID='ITEMAPPSTATUS' AND e.CBM=t.Status "
				   +" LEFT JOIN tSupplier f ON f.Code=t.SupplCode "
				   +" LEFT JOIN tWareHouse g ON g.Code=t.WHCode "
				   +" WHERE a.No=?";
		return this.findPageBySql(page, sql, new Object[]{no});
	}
	
	
	public Map<String,Object> getLastSendSupplier(String custCode){
		String sql = " select top 1 tspl.Descr lastSendSupplierDescr "
				   + " from tItemApp tia "
				   + " left join tItemType2 tit2 on tia.ItemType2 = tit2.Code "
				   + " left join tWorkType2 tw2 on tw2.Code = tit2.MaterWorkType2 "
				   + " left join tSupplier tspl on tspl.Code = tia.SupplCode "
				   + " where tia.CustCode=? and tia.SendType='1' and tia.ItemType1='JZ' and tw2.WorkType1='03' "
				   + " order by No desc ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{custCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 集成利润率材料明细
	 * @param page
	 * @param itemPreMeasure
	 * @return
	 */
	public Page<Map<String, Object>> goIntProDetailJqGrid(
			Page<Map<String, Object>> page, ItemPreMeasure itemPreMeasure) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.Qty,c.Descr itemdescr,d.Descr intproddescr,f.Descr fixareadescr,e.descr uomdescr from "
				+ "tItemAppDetail a "
				+ "left join tItem c on a.ItemCode=c.Code "
				+ "left join tIntProd d on a.IntProdPK=d.PK "
				+ "left join tUOM e on c.Uom=e.Code "
				+ "left join tFixArea f on d.FixAreaPK=f.PK "
				+ "where a.no=?";
		list.add(itemPreMeasure.getAppNo());
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> goAutoOrderJqGrid(Page<Map<String,Object>> page, ItemPreMeasure itemPreMeasure){
		
		StringBuilder sbReqPks = new StringBuilder();
		StringBuilder sbItemCodes = new StringBuilder();
		
		String[] reqPks = itemPreMeasure.getReqPks().split(",");
		String[] itemCodes = itemPreMeasure.getItemCodes().split(",");
		
		for(int i=0;i<reqPks.length && itemPreMeasure.getReqPks().length()>0;i++){
			if(Integer.parseInt(reqPks[i]) > 0){
				if(sbReqPks.length() == 0){
					sbReqPks.append("'"+reqPks[i].trim()+"'");
				}else{
					sbReqPks.append(",'"+reqPks[i].trim()+"'");
				}
			}else{
				if(sbItemCodes.length() == 0){
					sbItemCodes.append("'"+itemCodes[i].trim()+"'");
				}else{
					sbItemCodes.append(",'"+itemCodes[i].trim()+"'");
				}
			}
		}
		Xtcs xtcs = this.get(Xtcs.class, "Titles");
		List<Object> list = new ArrayList<Object>();
		String sql = "  exec pItemPreAppAutoOrder ?,?,?,?,?,?,?,?,?,?";
		list.add(itemPreMeasure.getCustCode());
		list.add(itemPreMeasure.getItemType1());
		list.add(itemPreMeasure.getItemType2());
		list.add(itemPreMeasure.getPreAppNo());
		list.add(itemPreMeasure.getIsService());
		list.add(itemPreMeasure.getIsSetItem());
		list.add(itemPreMeasure.getPreAppDTPks());
		list.add(sbReqPks);
		list.add(sbItemCodes);
		list.add(xtcs.getQz());
		page.setResult(findListBySql(sql, list.toArray())); 		
		page.setTotalCount(page.getResult().size());
		return page;
	}
	/**
	 * 领料预申请管理--自动拆单保存
	 * @param itemPreMeasure
	 * @param xml
	 * @return
	 */
	public Result autoOrderForProc(ItemPreMeasure itemPreMeasure, String xml){
		Assert.notNull(itemPreMeasure);
		Result result = new Result();
		itemPreMeasure.setProjectMan(itemPreMeasure.getProjectManDescr());
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLlYsqGlzdcd_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemPreMeasure.getSendTypeCheck());
			call.setString(2, itemPreMeasure.getApplyQtyCheck());
			call.setString(3, itemPreMeasure.getIsCmpCustCode());
			call.setString(4, xml);
			call.setString(5, itemPreMeasure.getM_umState());
			call.setDouble(6, itemPreMeasure.getOtherCost() == null ? 0:itemPreMeasure.getOtherCost());
			call.setString(7, itemPreMeasure.getItemAppStatus());
			call.setString(8, itemPreMeasure.getAppNo());
			call.setString(9, itemPreMeasure.getDelivType());
			call.setString(10, itemPreMeasure.getItemAppType());
			call.setString(11, itemPreMeasure.getItemType1());
			call.setString(12, itemPreMeasure.getItemType2());
			call.setString(13, itemPreMeasure.getCustCode());
			call.setString(14, itemPreMeasure.getAppCzy());
			call.setString(15, itemPreMeasure.getRemarks());
			call.setString(16, itemPreMeasure.getProjectMan());
			call.setString(17, itemPreMeasure.getPhone());
			call.setString(18, itemPreMeasure.getAppCzy());
			call.setString(19, itemPreMeasure.getExpired());
			call.setString(20, itemPreMeasure.getPreAppNo());
			call.setInt(21, Integer.parseInt(itemPreMeasure.getIsService()));
			call.setInt(22, Integer.parseInt(itemPreMeasure.getIsSetItem()));
			call.setString(23, itemPreMeasure.getSendType());
			call.setString(24, itemPreMeasure.getSupplCode());
			call.setString(25, itemPreMeasure.getWareHouseCode());
			call.setString(26, itemPreMeasure.getIsCupboard());
			
			call.registerOutParameter(27, Types.INTEGER);
			call.registerOutParameter(28, Types.NVARCHAR);
			call.setString(29, itemPreMeasure.getIsSubCheck());
			call.execute();
			result.setCode(String.valueOf(call.getInt(27)));
			result.setInfo(call.getString(28));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	/**
	 * 更新备注
	 * 
	 * @param itemPreMeasure
	 * @return
	 */
	public void updateRemarks( ItemPreMeasure itemPreMeasure) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  update tItemPreApp set remarks=?,actionlog='EDIT',lastupdate=getdate(),lastupdatedby=? where no=? ";
		list.add(itemPreMeasure.getRemarks());
		list.add(itemPreMeasure.getLastUpdatedBy());
		list.add(itemPreMeasure.getPreAppNo());
		executeUpdateBySql(sql, list.toArray());
	}

	public List<Map<String, Object>> findNoSendYunPic(){
		String sql = " select top 100 PK, PhotoName "
				   + " from tItemPreAppPhoto "
				   + " where (IsSendYun is null or IsSendYun = '0') and LastUpdate >= dateadd(day,-30,getdate()) "
				   + " order by PK desc ";
		return this.findBySql(sql, new Object[]{});
	}

	public boolean isExistsSaleIndependence(String custCode){
		String sql = " select 1 from tCustomer tc "
				   + " left join tCustomer oldTc on oldTc.OldCode = tc.Code and oldTc.Status='4' "
				   + " where tc.Code=? and exists( "
				   + " 		select 1 from tItemReq in_tir where in_tir.ItemType1='ZC' and in_tir.CustCode = oldTc.Code "
				   + " 		and in_tir.Qty > isnull(( "
				   + " 			select sum(in_tiad.Qty) from tItemAppDetail in_tiad left join tItemApp in_tia on in_tiad.No = in_tia.No "
				   + " 			where in_tia.Status <> 'CANCEL' and in_tiad.ReqPK=in_tir.PK "
				   + "	 	), 0) " 
				   + " ) ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{custCode});
		if(list != null && list.size() > 0){
			return true;
		}

		return false;
	}
	/**
	 * 通知监理
	 * 
	 * @param itemPreMeasure
	 * @return
	 */
	public void doNotify(ItemPreMeasure itemPreMeasure) {
		List<Object> list = new ArrayList<Object>();
		String sql = "insert into tPersonMessage (MsgType, MsgText, MsgRelNo, MsgRelCustCode, "
				+ " CrtDate, SendDate, RcvType, RcvCZY, RcvDate, RcvStatus, IsPush, PushStatus) "
				+ "select '4',c.Address+':'+?,ia.No,c.Code, "
				+ "getdate(),getdate(),'1',czy.CZYBH,null,'0','1','0' "
				+ "from tItemApp ia "
				+ "inner join tCustomer c on ia.CustCode=c.Code "
				+ "inner join tCZYBM czy on czy.EMNum=c.ProjectMan "
				+ "inner join tItemType1 it1 on ia.ItemType1=it1.Code "
				+ "left join tItemType2 it2 on ia.ItemType2=it2.Code "
				+ "where ia.No=? ";
		list.add(itemPreMeasure.getMsgText());
		list.add(itemPreMeasure.getAppNo());
		executeUpdateBySql(sql, list.toArray());
	}
	/**套内开关数量控制
	 * @param itemPreApp
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String checkKgQty(ItemPreMeasure itemPreMeasure) {
		Assert.notNull(itemPreMeasure);
		Connection conn = null; 
		CallableStatement call = null;
		String overQty="";
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLlglCheckKgQty(?,?,?)}");
			call.setString(1, itemPreMeasure.getAppNo());
			call.setString(2, itemPreMeasure.getCustCode());
			call.registerOutParameter(3, Types.DOUBLE);
			call.execute();
			overQty=String.valueOf(call.getDouble(3));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return overQty;
	}
	/**
	 * 楼盘是否停工状态
	 * 
	 * @return
	 */
	public List<Map<String, Object>> isStopWork(String custCode) {
		String sql = "select 1 from tCustomer where Code=? and ConstructStatus='3' ";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] {custCode});
		return list;
	}
	/**
	 * 是否配置发货到公司仓
	 * 
	 * @return
	 */
	public List<Map<String, Object>> checkSendCmpWh(ItemPreMeasure itemPreMeasure) {
		String sql = "select 1 from tSendToCmpWh " 
				   + "where (isnull(CustType,'')='' or CustType=?) and (isnull(RegionCode,'')='' or RegionCode=?) and (isnull(SupplCode,'')='' or SupplCode=?) " 
				   + "and AmountFrom<=? and AmountTo>=? and Expired='F' and ItemAppType=? ";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] {itemPreMeasure.getCustType(),itemPreMeasure.getRegionCode(),itemPreMeasure.getSupplCode(),itemPreMeasure.getAllcost(),itemPreMeasure.getAllcost(),itemPreMeasure.getToCmItemAppType()});
		return list;
	}
	
	public Page<Map<String,Object>> goJqGridItemPlanSoftSend(Page<Map<String,Object>> page, ItemPreMeasure itemPreMeasure){
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from ( "
				   + " select a.no,a.iano,a.date,b.Desc1 whcodedescr,d.NameChi drivername,it1.descr itemtype1descr,it2.descr itemtype2descrx from tItemAppSend a "
				   + " left join tWareHouse b on a.WHCode=b.code "
				   + " left join tItemSendBatch c on a.SendBatchNo=c.No "
				   + " left join tDriver d on c.DriverCode=d.Code "
				   + " left join tItemApp ia on ia.no=a.iano "
				   + " left join tItemType1 it1 on it1.code=ia.itemType1 "
				   + " left join tItemType1 it2 on it2.code=ia.itemType2 "
				   + " where 1=1  ";
		if(StringUtils.isNotBlank(itemPreMeasure.getCustCode())){
			sql += " and ia.custCode=? ";
			list.add(itemPreMeasure.getCustCode());
		}
		if(StringUtils.isNotBlank(itemPreMeasure.getItemType1())){
			sql += " and ia.ItemType1=? ";
			list.add(itemPreMeasure.getItemType1());
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public String getSoftSendCount(String custCode, String itemType1) {
		String sql = " select count(1) sendcount from titemAppSend a left join tItemApp ia on ia.no=a.iano where ia.custCode=? and ia.ItemType1=?  ";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] {custCode,itemType1});
		if (list != null && list.size() > 0) {
			return list.get(0).get("sendcount").toString();
		} else {
			return "0";
		}
	}
	
	/**
	 * 领料预申请管理--后台拆单保存
	 * @param itemPreMeasure
	 * @return
	 */
	public Result doBackstageOrder(ItemPreMeasure itemPreMeasure){
		Assert.notNull(itemPreMeasure);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pItemPreAppBackstageOrder(?,?,?,?,?)}");
			call.setString(1, itemPreMeasure.getPreAppNo());
			call.setString(2, itemPreMeasure.getAppCzy());
			call.setString(3, itemPreMeasure.getIsCmpCustCode());
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
	
	public Page<Map<String,Object>> findItemShouldOrderJqGrid(Page<Map<String,Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select	* from	(select	eZCGJ.NameChi zcgjnamechi, a.Address, a.Code CustCode, d.ConfItemType, f.Descr," +
				"				case	when d.BeginDateType = '1' then e.BeginDate" +
				"						when d.BeginDateType = '2' then e.EndDate" +
				"						when d.BeginDateType = '3' then e.ConfirmDate end nodeTriggerDate, " +
				"				dateadd(day, d.BeginAddDays, case	when d.BeginDateType = '1' then e.BeginDate" +
				"																		when d.BeginDateType = '2' then e.EndDate" +
				"																		when d.BeginDateType = '3' then e.ConfirmDate" +
				"																	end) shouldOrderDate, x3.NOTE payType," +
				"				isnull(x1.NOTE, '未确认') isConfirmed, g.LastUpdate confirmDate, h.Descr shouldOrderNode," +
				"				x2.NOTE nodeDateType, b.Desc1 CustTypeDescr, k.NameChi ProjectManDescr, l.Desc1 PrjDeptDescr," +
				"				n.remarks ConfirmRemarks" +
				"			from tCustomer a" +
				"			left join tCusttype b on a.CustType = b.Code" +
				"			left join tItemSendNode c on b.WorkerClassify = c.WorkerClassify" +
				"			left join tItemSendNodeDetail d on c.Code = d.Code" +
				"			inner join tPrjProg e on dateadd(day, d.BeginAddDays, case	when d.BeginDateType = '1' then e.BeginDate when d.BeginDateType = '2' then e.EndDate" +
				"											when d.BeginDateType = '3' then e.ConfirmDate end) < dateadd(day, 1, ?)" +
				"										and e.PrjItem = d.BeginNode and a.Code = e.CustCode" +
				"			left join tConfItemType f on f.Code = d.ConfItemType" +
				"			left join tCustItemConfirm g on f.Code = g.ConfItemType and a.Code = g.CustCode" +
				"			left join tPrjItem1 h on e.PrjItem = h.Code" +
				"			left join tEmployee k on a.ProjectMan = k.Number" +
				"			left join tDepartment2 l on k.Department2 = l.Code" +
				"			left join tXTDM x1 on g.ItemConfStatus = x1.CBM and x1.ID = 'ITEMCONFSTS'" +
				"			left join tXTDM x2 on d.BeginDateType = x2.CBM and x2.ID = 'ALARMDAYTYPE'" +
				"			left join tXTDM x3 on a.PayType = x3.CBM and x3.ID = 'TIMEPAYTYPE'" +
				"			left join (select	max(a.PK) pk, a.CustCode from	tCustStakeholder a where	Role = '34' group by a.CustCode) zcgj on zcgj.CustCode = a.Code" +
				"			left join tCustStakeholder cZCGJ on cZCGJ.PK = zcgj.pk" +
				"			left join tEmployee eZCGJ on eZCGJ.Number = cZCGJ.EmpCode" +
				"			left join (select	CustCode, max(No) No from	tCustItemConfProg where	ItemConfStatus = '2' group by CustCode) m on m.CustCode = a.Code" +
				"			left join tCustItemConfProg n on m.No = n.No" +
				"			where a.Status = '4' and c.Type = '1' and g.ItemConfStatus = '2' and f.DispatchOrder = '1' " +
				"				and not(a.IsInitSign='1' and f.ItemType1 in ('ZC','JC','RZ')) " +
				"				and exists ( select	1 from tItemReq ex_a" +
				"								left join tItem ex_b on ex_a.ItemCode = ex_b.Code" +
				"								left join tItemType2 ex_c on ex_b.ItemType2 = ex_c.Code" +
				"								left join tItemType3 ex_d on ex_b.ItemType3 = ex_d.Code" +
				"								where ex_a.CustCode = a.Code" +
				"									and ex_a.Qty > 0" +
				"									and exists ( select	1 from tConfItemTypeDt in_a" +
				"													where ConfItemType = f.Code" +
				"														and ((ex_b.ItemType2 = in_a.ItemType2" +
				"																and isnull(in_a.ItemType3, '') <> ''" +
				"																and ex_b.ItemType3 = in_a.ItemType3" +
				"																)" +
				"																or (ex_b.ItemType2 = in_a.ItemType2" +
				"																	and isnull(in_a.ItemType3, '') = ''" +
				"																	)" +
				"															) " +
				"												)" +
				" 									and not exists ( select	1 from tItemPreApp in_e" +
				"									left join tItemPreAppDetail in_c on in_c.No = in_e.No" +
				"									left join tItem in_d on in_c.ItemCode = in_d.Code" +
				"									left join tConfItemTypeDt in_f on " +
				"										((in_d.ItemType2 = in_f.ItemType2" +
				"											and isnull(in_f.ItemType3, '') <> ''" +
				"											and in_d.ItemType3 = in_f.ItemType3" +
				"											)" +
				"											or (in_d.ItemType2 = in_f.ItemType2" +
				"												and isnull(in_f.ItemType3, '') = ''" +
				"												)" +
				"										)" +
				"										where in_e.CustCode = ex_a.CustCode and in_f.ConfItemType = f.Code and in_e.status not in ('6')" +
				"									) and not exists (" +
				"										select in_h.* from tItemApp in_g " +
				"										left join tItemAppDetail in_h on in_h.No = in_g.No" +
				"										left join tItem in_i on in_h.ItemCode = in_i.Code" +
				"										left join tConfItemTypeDt in_j on ((in_i.ItemType2 = in_j.ItemType2" +
				"										and isnull(in_j.ItemType3, '') <> ''" +
				"										and in_i.ItemType3 = in_j.ItemType3" +
				"										)" +
				"										or (in_i.ItemType2 = in_j.ItemType2" +
				"											and isnull(in_j.ItemType3, '') = '' " +
				"										)) where in_g.CustCode = ex_a.CustCode and in_j.ConfItemType = f.Code and in_g.status <>'CANCEL'" +
				"									)" +
				"							)";
		
		if(customer.getDateTo() != null){
			list.add(new Timestamp(
					DateUtil.endOfTheDay( customer.getDateTo()).getTime()));
		} else {
			list.add(new Timestamp(
					DateUtil.endOfTheDay(new Date()).getTime()));
		}
		
		if(StringUtils.isNotBlank(customer.getAddress())){
			sql+=" and a.Address like ?";
			list.add("%" + customer.getAddress()+ "%");
		}
		if(StringUtils.isNotBlank(customer.getProjectMan())){
			sql+=" and a.ProjectMan = ?";
			list.add(customer.getProjectMan());
		}
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql+=" and k.Department2 in ("+SqlUtil.resetStatus(customer.getDepartment2())+")" ;
		}
		if(StringUtils.isNotBlank(customer.getPrjItem())){
			sql+=" and h.code = ? ";
			list.add(customer.getPrjItem());
		}
		if(StringUtils.isNotBlank(customer.getConfItemType())){
			sql+=" and f.Code = ? ";
			list.add(customer.getConfItemType());
		}
		
		sql+=" ) a where 1=1 ";

		if(customer.getDateFrom() != null){
			sql+=" and a.nodeTriggerDate >= ?";
			list.add(new Timestamp(
					DateUtil.startOfTheDay(customer.getDateFrom()).getTime()));
		}
		if(customer.getDateTo() != null){
			sql+=" and a.nodeTriggerDate <= ? ";
			list.add(new Timestamp(
					DateUtil.endOfTheDay( customer.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.CustCode asc ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result doBatchSave(ItemPreApp itemPreApp) {
		Assert.notNull(itemPreApp);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pYllgl_BatchSave(?,?,?,?)}");
			call.setString(1, itemPreApp.getLastUpdatedBy());
			call.registerOutParameter(2, Types.INTEGER);
			call.registerOutParameter(3, Types.NVARCHAR); 
			call.setString(4, itemPreApp.getDetailXml());
			call.execute();
			result.setCode(String.valueOf(call.getInt(2)));
			result.setInfo(call.getString(3));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Map<String, Object> getByPreAppNo(String preAppNo) {
		String sql = "select b.Address,c.NameChi PrjManName,c.Phone PrjManPhone "
				+"from tItemPreApp a "
				+"left join tCustomer b on a.CustCode = b.Code "
				+"left join tEmployee c on b.ProjectMan = c.Number "
				+"where a.No = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{preAppNo});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}

