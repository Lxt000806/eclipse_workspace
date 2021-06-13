package com.house.home.dao.project;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
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
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.BaseItemChgEvt;
import com.house.home.entity.design.ItemPlan;
import com.house.home.entity.project.BaseItemChg;
import com.house.home.entity.project.ItemChg;
import com.sun.accessibility.internal.resources.accessibility;

@SuppressWarnings("serial")
@Repository
public class BaseItemChgDao extends BaseDao {

	/**
	 * BaseItemChg分页信息
	 * 
	 * @param page
	 * @param baseItemChg
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemChg baseItemChg, UserContext userContext) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select g.desc1 custTYpeDescr,a.prjStatus,f.note prjStatusDescr,a.No,a.CustCode,c.Descr CustomerDescr,c.Address,c.Area,a.Status,d.NOTE StatusDescr,a.Date,"
				+"a.BefAmount,a.DiscAmount,a.Amount,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,c.DocumentNo,"
				+"c.CustType,a.ManageFee,a.PerfPK,a.IscalPerf,e.NOTE IscalPerfDescr,a.AppCzy,a.ConfirmCzy,a.ConfirmDate,"
				+"i.ZWXM DeptLeaderConfirmCZYName, a.DeptLeaderConfirmDate, "
				+"tc.zwxm appCzyDescr,tc2.zwxm confirmCzyDescr,h.NOTE isAddAllInfoDescr from tBaseItemChg a "
				+"left join tCustomer c on a.CustCode=c.Code "
				+"left join tXTDM d on a.Status=d.CBM and d.ID='ITEMCHGSTATUS' "
				+"left join tXTDM e on a.IscalPerf=e.CBM and e.ID='YESNO' "
				+"left join tCZYBM tc on tc.czybh=a.appCzy "
				+"left join tCZYBM tc2 on tc2.czybh=a.ConfirmCzy " +
				" left join tXtdm f on f.cbm = a.prjStatus and f.id = 'PRJSTATUS' " +
				" left join tCustType g on g.code = c.custType "
				+" left join tXtdm h on h.cbm = a.isAddAllInfo and h.id = 'YESNO' "
				+"left join tCZYBM i on a.DeptLeaderConfirmCZY = i.CZYBH " +
				" left join tEmployee j on j.number = tc.EmNum " +
				" left join tDepartment k on k.Code = j.Department "
				+" where 1=1 ";

		sql += "and " + SqlUtil.getCustRight(userContext, "c", "");
		
    	if (StringUtils.isNotBlank(baseItemChg.getNo())) {
			sql += " and a.No=? ";
			list.add(baseItemChg.getNo());
		}
    	if (StringUtils.isNotBlank(baseItemChg.getAddress())){
    		sql += " and c.address like ? ";
			list.add("%"+baseItemChg.getAddress()+"%");
    	}
    	if (StringUtils.isNotBlank(baseItemChg.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(baseItemChg.getCustCode());
		}
    	if (StringUtils.isNotBlank(baseItemChg.getIsAddAllInfo())) {
			sql += " and a.IsAddAllInfo=? ";
			list.add(baseItemChg.getIsAddAllInfo());
		}
    	if (StringUtils.isNotBlank(baseItemChg.getStatus())) {
			String str = SqlUtil.resetStatus(baseItemChg.getStatus());
			sql += " and a.status in (" + str + ")";
		}
    	if (baseItemChg.getDateFrom() != null){
			sql += " and a.Date>=CONVERT(VARCHAR(10),?,120) ";
			list.add(baseItemChg.getDateFrom());
		}
		if (baseItemChg.getDateTo() != null){
			sql += " and a.Date<=CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(baseItemChg.getDateTo());
		}
    	if (baseItemChg.getBefAmount() != null) {
			sql += " and a.BefAmount=? ";
			list.add(baseItemChg.getBefAmount());
		}
    	if (baseItemChg.getDiscAmount() != null) {
			sql += " and a.DiscAmount=? ";
			list.add(baseItemChg.getDiscAmount());
		}
    	if (baseItemChg.getAmount() != null) {
			sql += " and a.Amount=? ";
			list.add(baseItemChg.getAmount());
		}
    	if (StringUtils.isNotBlank(baseItemChg.getRemarks())) {
			sql += " and a.Remarks like ? ";
			list.add("%"+baseItemChg.getRemarks()+"%");
		}
    	if (baseItemChg.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(baseItemChg.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(baseItemChg.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(baseItemChg.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(baseItemChg.getExpired()) || "F".equals(baseItemChg.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(baseItemChg.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(baseItemChg.getActionLog());
		}
    	if (baseItemChg.getManageFee() != null) {
			sql += " and a.ManageFee=? ";
			list.add(baseItemChg.getManageFee());
		}
    	if (baseItemChg.getSetMinus() != null) {
			sql += " and a.SetMinus=? ";
			list.add(baseItemChg.getSetMinus());
		}
    	if (baseItemChg.getPerfPk() != null) {
			sql += " and a.PerfPK=? ";
			list.add(baseItemChg.getPerfPk());
		}
    	if (StringUtils.isNotBlank(baseItemChg.getIscalPerf())) {
			sql += " and a.IscalPerf=? ";
			list.add(baseItemChg.getIscalPerf());
		}
    	if(StringUtils.isNotBlank(baseItemChg.getPrjStatus())){
    		String str = SqlUtil.resetStatus(baseItemChg.getPrjStatus());
			sql += " and a.prjStatus in (" + str + ")";
    	}
    	if (baseItemChg.getConfirmDateFrom() != null){
			sql += " and a.ConfirmDate>=? ";
			list.add(new Timestamp(
					DateUtil.startOfTheDay( baseItemChg.getConfirmDateFrom()).getTime()));
		}
		if (baseItemChg.getConfirmDateTo() != null){
			sql += " and a.ConfirmDate<=? ";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(baseItemChg.getConfirmDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(baseItemChg.getApplyManDept())){
			sql+=" and '/'+k.Path+'/' like ? ";
			list.add("%/"+baseItemChg.getApplyManDept()+"%/");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String,Object>> findPageByCustCode(Page<Map<String,Object>> page, String custCode){
		String sql = "select * from (select a.No,a.CustCode,c.Descr CustomerDescr,c.Address,a.Status,"
             + "d.NOTE StatusDescr,a.Date,a.BefAmount,case when a.BefAmount>0 then a.DiscAmount else a.DiscAmount*-1 end DiscAmount,a.Amount,a.Remarks,"
             + "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
             + "from tBaseItemChg a "
             + "left outer join tCustomer c on a.CustCode=c.Code "
             + "left outer join tXTDM d on a.Status=d.CBM and d.ID='ITEMCHGSTATUS' "
             + "where a.CustCode =? ";
        if (StringUtils.isNotBlank(page.getPageOrderBy())){
 			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
 		}else{
 			sql += ") a order by a.No";
 		}
		return this.findPageBySql(page, sql, new Object[]{custCode});
	}

	@SuppressWarnings("deprecation")
	public Result saveForProc(BaseItemChg baseItemChg, String xml) {
		Assert.notNull(baseItemChg);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			if ("1".equals(baseItemChg.getCustTypeType())){
				call = conn.prepareCall("{Call pJzzj_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				call.setString(1, baseItemChg.getM_umState());
				call.setString(2, baseItemChg.getNo());
				call.setString(3, baseItemChg.getCustCode());
				call.setString(4, baseItemChg.getStatus());
				call.setTimestamp(5, baseItemChg.getDate()==null?null:new Timestamp(baseItemChg.getDate().getTime()));
				call.setDouble(6, baseItemChg.getBefAmount());
				call.setDouble(7, baseItemChg.getDiscAmount());
				call.setDouble(8, baseItemChg.getAmount());
				call.setString(9, baseItemChg.getRemarks());
				call.setString(10, baseItemChg.getLastUpdatedBy());
				call.registerOutParameter(11, Types.INTEGER);
				call.registerOutParameter(12, Types.NVARCHAR);
				call.setString(13, xml);
				call.setDouble(14, baseItemChg.getBaseDiscPer());
				call.setDouble(15, baseItemChg.getChgBaseDiscPer());
				call.setString(16, baseItemChg.getPrjStatus());//add by zb on 20190313
				call.setString(17, baseItemChg.getIsAddAllInfo());
				call.setString(18, baseItemChg.getChgStakeholderList());//干系人列表XML add by zb on 20191224
				call.setDouble(19, baseItemChg.getTax());
				call.setString(20, baseItemChg.getTempNo());
				call.execute();
				result.setCode(String.valueOf(call.getInt(11)));
				result.setInfo(call.getString(12));
			}else{
				call = conn.prepareCall("{Call pJzzj_TC_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				call.setString(1, baseItemChg.getM_umState());
				call.setString(2, baseItemChg.getNo());
				call.setString(3, baseItemChg.getCustCode());
				call.setString(4, baseItemChg.getStatus());
				call.setTimestamp(5, baseItemChg.getDate()==null?null:new Timestamp(baseItemChg.getDate().getTime()));
				call.setDouble(6, baseItemChg.getBefAmount());
				call.setDouble(7, baseItemChg.getDiscAmount());
				call.setDouble(8, baseItemChg.getAmount());
				call.setString(9, baseItemChg.getRemarks());
				call.setString(10, baseItemChg.getLastUpdatedBy());
				call.setDouble(11, baseItemChg.getManageFee());
				call.setDouble(12, baseItemChg.getSetMinus());
				call.registerOutParameter(13, Types.INTEGER);
				call.registerOutParameter(14, Types.NVARCHAR);
				call.setString(15, xml);
				call.setString(16, baseItemChg.getPrjStatus());//add by zb on 20190313
				call.setString(17, baseItemChg.getIsAddAllInfo());
				call.setString(18, baseItemChg.getChgStakeholderList());//干系人列表XML add by zb on 20191224
				call.setDouble(19, baseItemChg.getTax());
				call.setString(20, baseItemChg.getTempNo());
				call.execute();
				result.setCode(String.valueOf(call.getInt(13)));
				result.setInfo(call.getString(14));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	public int getCountByCustCode(String custCode) {
		String sql = "select count(1) ret from tbaseItemchg where status ='1' and custcode=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return Integer.parseInt(String.valueOf(list.get(0).get("ret")));
		}
		return 0;
	}
	
	public double getLastBaseDiscPer(String custCode) {
		String sql = " select baseDiscPer-chgBaseDiscPer baseDiscPer from tBaseItemChg where status = '2'  " +
				" and CustCode = ? order by LastUpdate desc ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return Double.parseDouble(list.get(0).get("baseDiscPer").toString());
		}
		return 1d;
	}
	
	public double getBaseFeeDirct(String custCode) {
		String sql = " select isnull(sum(a.LineAmount),0) baseFeeDirct from tBaseItemPlan a " +
				" left join tFixArea b on b.pk=a.FixAreaPK" +
				" where a.CustCode= ? and b.descr<>'综合项目' ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return Double.parseDouble(list.get(0).get("baseFeeDirct").toString());
		}
		return 0d;
	}
	
	public double getBaseItemChgDirct(String custCode) {
		String sql = " select isnull(sum(c.LineAmount),0) baseFeeDirct  from tBaseItemChg a " +
				" left join tBaseItemChgDetail c on c.no = a.no " +
				" left join tFixArea b on b.pk=c.FixAreaPK" +
				" where a.CustCode= ? and b.descr<>'综合项目' and a.status = '2' ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return Double.parseDouble(list.get(0).get("baseFeeDirct").toString());
		}
		return 0d;
	}

	/**
	 * 客户经理APP_获取当前操作员申请的基装增减信息
	 * @author	created by zb
	 * @date	2019-2-22--下午4:46:59
	 * @param page
	 * @param evt
	 * @return
	 */
	public Page<Map<String, Object>> getBaseItemChgList(
			Page<Map<String, Object>> page, BaseItemChgEvt evt) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.No,b.Address,a.PrjStatus,tx.NOTE PrjStatusDescr,a.Amount,a.DiscAmount,a.LastUpdate,a.Date ChangeDate, " +
					"b.DocumentNo,a.AppCZY,tc.ZWXM AppCzyDescr,a.ConfirmCZY,tc2.ZWXM ConfirmCzyDescr,a.ConfirmDate,b.CustType, " +
					"tct.Type CustTypeType, a.Status " +
					"from tBaseItemChg a " +
					"left join tCustomer b on b.Code=a.CustCode " +
					"left join tXTDM tx on tx.CBM=a.PrjStatus and ID='PrjStatus' " +
					"left join tCZYBM tc on tc.czybh=a.appCzy " +
					"left join tCZYBM tc2 on tc2.CZYBH=a.ConfirmCZY " +
					"left join tCusttype tct on tct.Code=b.CustType " +
					"where 1=1 " ;
		if(StringUtils.isNotBlank(evt.getAddress())){
			sql += " AND b.Address like ? ";
			list.add("%"+evt.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(evt.getPrjStatus())){
			sql += " AND a.prjStatus =? ";
			list.add(evt.getPrjStatus());
		}
		if(StringUtils.isNotBlank(evt.getProjectMan())){
			sql += " AND a.AppCZY=? ";
			list.add(evt.getProjectMan());
		}
		sql += " order by a.Date desc,a.LastUpdate desc "; 
		return this.findPageBySql(page, sql , list.toArray());
	}
	/**
	 * 根据增减no获取详细信息
	 * @author	created by zb
	 * @date	2019-2-26--下午3:08:06
	 * @param no
	 * @return
	 */
	public Map<String, Object> getDetailByNo(String no) {
		String sql = "select a.No,b.Address,a.Status,tx.NOTE StatusDescr,a.Amount,a.DiscAmount,a.LastUpdate,a.Date ChangeDate, " +
					"b.DocumentNo,a.AppCZY,tc.ZWXM AppCzyDescr,a.ConfirmCZY,tc2.ZWXM ConfirmCzyDescr,a.ConfirmDate,b.CustType " +
					"from tBaseItemChg a " +
					"left join tCustomer b on b.Code=a.CustCode " +
					"left join tXTDM tx on tx.CBM=a.Status and ID='PrjStatus' " +
					"left join tCZYBM tc on tc.czybh=a.appCzy " +
					"left join tCZYBM tc2 on tc2.CZYBH=a.ConfirmCZY " +
					"where a.No=? " ;
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{no});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public void doPrjReceive(String no,String czybh) {
		String sql = " update tBaseItemchg set PrjStatus = '3',lastUpdate = getDate(),lastUpdatedBy = ? where no = ? ";
		this.executeUpdateBySql(sql, new Object[]{czybh,no});
	}
	
	public void doPrjReturn(String no,String czybh) {
		String sql = " update tBaseItemchg set PrjStatus = '1',lastUpdate = getDate(),lastUpdatedBy = ? where no = ? ";
		this.executeUpdateBySql(sql, new Object[]{czybh,no});
	}
	
	public Page<Map<String,Object>> findDetailPageBySql(Page<Map<String,Object>> page, BaseItemChg baseItemChg) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = "select * from ( select a.No,c.Descr baseitemtype1descr,d.CustCode,e.Descr custDescr,e.Address,x1.note statusDescr,d.ConfirmDate,f.Descr fixAreaDescr,a.BaseItemCode" +
				" ,b.Descr baseItemDescr,g.Qty reqQty,a.Qty ChgQty,h.Descr uomDescr,b.offerPRi,b.Material Materialprice,a.lineamount Amount,x2.NOTE isOutSetDescr,a.Remarks" +
				" ,a.LastUpdate,a.LastUpdatedBy,a.ActionLog,a.IsMainItem,a.BaseItemSetNo,x3.note IsMainItemDescr" +
				" from tBaseItemChgDetail a" +
				" left join tBaseItem b on b.Code = a.BaseItemCode" +
				" left join tBaseItemType1 c on c.Code=b.BaseItemType1" +
				" left join tBaseItemChg d on d.no = a.No" +
				" left join tCustomer e on e.Code=d.CustCode" +
				" left join tFixArea f on f.PK= a.FixAreaPK" +
				" left join tBaseItemReq g on g.pk = a.ReqPK" +
				" left join tUOM h on h.Code=b.Uom " +
				" left join txtdm x1 on x1.id='ITEMCHGSTATUS' and x1.CBM = d.Status" +
				" left join tXTDM x2 on x2.id='YESNO' and x2.cbm = a.IsOutSet" +
				" left join tXTDM x3 on x3.id='YESNO' and x3.cbm = a.IsMainItem" 
				+" where 1=1 ";
		/*增减单号、客户编号、楼盘地址、基装类型1、基装类型2、基装项目编号、审核时间（XXX至XXX）、增减单状态*/
    	if(StringUtils.isNotBlank(baseItemChg.getNo())){
    		sql+=" and a.no = ? ";
    		list.add(baseItemChg.getNo());
    	}
    	if(StringUtils.isNotBlank(baseItemChg.getCustCode())){
    		sql+=" and d.custcode =  ? ";
    		list.add(baseItemChg.getCustCode());
    	}
    	if(StringUtils.isNotBlank(baseItemChg.getAddress())){
    		sql+=" and e.address like ? ";
    		list.add("%"+baseItemChg.getAddress()+"%");
    	}
    	if(StringUtils.isNotBlank(baseItemChg.getBaseItemChgType1())){
    		sql+=" and b.BaseitemType1 = ? ";
    		list.add(baseItemChg.getBaseItemChgType1());
    	}
    	if(StringUtils.isNotBlank(baseItemChg.getBaseItemChgType2())){
    		sql+=" and b.baseitemType2 = ?";
    		list.add(baseItemChg.getBaseItemChgType2());
    	}
    	if(StringUtils.isNotBlank(baseItemChg.getCode())){
    		sql+=" and b.Code = ? ";
    		list.add(baseItemChg.getCode());
    	}
    	if (baseItemChg.getConfirmDateFrom() != null){
			sql += " and d.ConfirmDate>=? ";
			list.add(baseItemChg.getConfirmDateFrom());
		}
		if (baseItemChg.getConfirmDateTo() != null){
			sql += " and d.Date<=? ";
			list.add(baseItemChg.getConfirmDateTo());
		}
		if (StringUtils.isNotBlank(baseItemChg.getStatus())) {
			String str = SqlUtil.resetStatus(baseItemChg.getStatus());
			sql += " and d.status in (" + str + ")";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 获取基础增减干系人表
	 * @author	created by zb
	 * @date	2019-12-24--上午11:40:04
	 * @param page
	 * @param baseItemChg
	 * @return
	 */
	public Page<Map<String, Object>> findBaseChgStakeholderPageByNo(
			Page<Map<String, Object>> page, BaseItemChg baseItemChg) {
		String sql = "select a.PK, a.BaseChgNo, a.Role, c.Descr RoleDescr, a.EmpCode,"
					+"	b.NameChi EmpName,a.LastUpdate,a.LastUpdatedBy, "
					+"a.ActionLog,a.Expired  "
					+"from tBaseChgStakeholder a "
					+"left join tEmployee b on b.Number=a.EmpCode "
					+"left join tRoll c on c.Code=a.Role "
					+"where a.BaseChgNo=?";
		return this.findPageBySql(page, sql, new Object[]{baseItemChg.getNo()});
	}
	
	/**
	 * 获取基础预算从模板导入数据
	 * @param itemChg
	 * @return
	 */
	public Result doBaseItemChgTempProc(BaseItemChg baseItemChg) {
		Assert.notNull(baseItemChg);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pBaseItemChgFromPrePlanTemp(?,?,?,?,?)}");
			call.setString(1, baseItemChg.getCustCode());
			call.setString(2, baseItemChg.getTempNo());
			call.setString(3, baseItemChg.getLastUpdatedBy());
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
	
	/**
	 * 基础增减重新生成
	 * @param baseItemChg
	 * @return
	 */
	public Result doRegenBaseItemChgFromPrePlanTemp(BaseItemChg baseItemChg) {
		Assert.notNull(baseItemChg);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pRegenBaseItemChgFromPrePlanTemp(?,?,?,?,?,?,?,?)}");
			call.setString(1, baseItemChg.getCustCode());
			call.setString(2, baseItemChg.getTempNo());
			call.setString(3, baseItemChg.getLastUpdatedBy());
			call.setString(4,baseItemChg.getBaseItemChgXml());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.setString(7, baseItemChg.getIsRegenCanreplace());
			call.setString(8, baseItemChg.getIsRegenCanmodiQty());
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
		String sql = " select 1 from tBaseItemChg where custCode = ? and TempNo is not null and tempNo <> '' and status<>'3' ";
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

	public String getConfirmNotice(String no) {
		String sql = " select dbo.fGetBaseItemChgNotice(?) notice ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{no});
		if (list!=null && list.size()>0){
			if(list.get(0).get("notice") != null){
				return list.get(0).get("notice").toString();
			}
		}
		return "";
	}
}

