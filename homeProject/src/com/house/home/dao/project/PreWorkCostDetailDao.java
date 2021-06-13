package com.house.home.dao.project;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
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
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.project.PreWorkCostDetail;
import com.house.home.entity.project.WorkCostDetail;

@SuppressWarnings("serial")
@Repository
public class PreWorkCostDetailDao extends BaseDao {

	/**
	 * PreWorkCostDetail分页信息
	 * 
	 * @param page
	 * @param preWorkCostDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql1(Page<Map<String,Object>> page, PreWorkCostDetail preWorkCostDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tPreWorkCostDetail a where 1=1 ";

    	if (preWorkCostDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(preWorkCostDetail.getPk());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(preWorkCostDetail.getCustCode());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getSalaryType())) {
			sql += " and a.SalaryType=? ";
			list.add(preWorkCostDetail.getSalaryType());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getWorkType2())) {
			sql += " and a.WorkType2=? ";
			list.add(preWorkCostDetail.getWorkType2());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getWorkerCode())) {
			sql += " and a.WorkerCode=? ";
			list.add(preWorkCostDetail.getWorkerCode());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getActName())) {
			sql += " and a.ActName=? ";
			list.add(preWorkCostDetail.getActName());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getCardId())) {
			sql += " and a.CardID=? ";
			list.add(preWorkCostDetail.getCardId());
		}
    	if (preWorkCostDetail.getAppAmount() != null) {
			sql += " and a.AppAmount=? ";
			list.add(preWorkCostDetail.getAppAmount());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(preWorkCostDetail.getRemarks());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getStatus())) {
			sql += " and a.Status=? ";
			list.add(preWorkCostDetail.getStatus());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getApplyMan())) {
			sql += " and a.ApplyMan=? ";
			list.add(preWorkCostDetail.getApplyMan());
		}
    	if (preWorkCostDetail.getApplyDate() != null) {
			sql += " and a.ApplyDate=? ";
			list.add(preWorkCostDetail.getApplyDate());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getConfirmAssist())) {
			sql += " and a.ConfirmAssist=? ";
			list.add(preWorkCostDetail.getConfirmAssist());
		}
    	if (preWorkCostDetail.getAssistConfirmDate() != null) {
			sql += " and a.AssistConfirmDate=? ";
			list.add(preWorkCostDetail.getAssistConfirmDate());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getConfirmManager())) {
			sql += " and a.ConfirmManager=? ";
			list.add(preWorkCostDetail.getConfirmManager());
		}
    	if (preWorkCostDetail.getManagerConfirmDate() != null) {
			sql += " and a.ManagerConfirmDate=? ";
			list.add(preWorkCostDetail.getManagerConfirmDate());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getNo())) {
			sql += " and a.No=? ";
			list.add(preWorkCostDetail.getNo());
		}
    	if (preWorkCostDetail.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(preWorkCostDetail.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(preWorkCostDetail.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(preWorkCostDetail.getExpired()) || "F".equals(preWorkCostDetail.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(preWorkCostDetail.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql_forClient(
			Page<Map<String, Object>> page, PreWorkCostDetail preWorkCostDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.PK,a.CustCode,b.address,a.Status,x1.NOTE statusDescr,a.WorkType2,w2.Descr workType2Descr,a.AppAmount,"
		+"w2.WorkType1,w1.Descr workType1Descr,a.actName "
		+"from tPreWorkCostDetail a "
		+"inner join tCustomer b on a.CustCode=b.code "
		+"left join tXTDM x1 on x1.CBM=a.Status and x1.ID='PRECOSTSTATUS' "
		+"left join tWorkType2 w2 on w2.Code=a.WorkType2 "
		+"left join tWorkType1 w1 on w1.Code=w2.WorkType1 where a.Expired='F' ";

		if (preWorkCostDetail.getApplyMan() != null) {
			sql += " and (a.applyMan=? or b.ProjectMan=?)";
			list.add(preWorkCostDetail.getApplyMan());
			list.add(preWorkCostDetail.getApplyMan());
		}else{
			return null;
		}
		if (preWorkCostDetail.getAddress() != null) {
			sql += " and b.address like ? ";
			list.add("%"+preWorkCostDetail.getAddress()+"%");
		}
    	if (preWorkCostDetail.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(preWorkCostDetail.getPk());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(preWorkCostDetail.getCustCode());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getSalaryType())) {
			sql += " and a.SalaryType=? ";
			list.add(preWorkCostDetail.getSalaryType());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getWorkType2())) {
			sql += " and a.WorkType2=? ";
			list.add(preWorkCostDetail.getWorkType2());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getStatus())) {
			sql += " and a.Status=? ";
			list.add(preWorkCostDetail.getStatus());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getConfirmAssist())) {
			sql += " and a.ConfirmAssist=? ";
			list.add(preWorkCostDetail.getConfirmAssist());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getConfirmManager())) {
			sql += " and a.ConfirmManager=? ";
			list.add(preWorkCostDetail.getConfirmManager());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getNo())) {
			sql += " and a.No=? ";
			list.add(preWorkCostDetail.getNo());
		}
    	if (StringUtils.isNotBlank(preWorkCostDetail.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(preWorkCostDetail.getLastUpdatedBy());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> getByPk(Integer pk) {
		String sql = "select a.PK,a.CustCode,b.address,a.Status,x1.NOTE statusDescr,a.WorkType2,w2.Descr workType2Descr,a.AppAmount,w2.workType12,"
		+"w2.WorkType1,w1.Descr workType1Descr,a.SalaryType,a.WorkerCode,a.ActName,a.CardID,a.ApplyMan,t.confirmAmount,"
		+"t.confirmRemark,a.remarks,a.IsWithHold,a.WithHoldNo,a.IsWorkApp,a.WorkAppAmount "
		+",st.Descr salaryDescr,a.CfmAmount,a.CardID2,a.ApplyDate,wc.PayDate,t.qualityFee, "
		+"(case when t.QualityFee is null then a.CfmAmount else t.ConfirmAmount-t.QualityFee end) realSalary,b.ProjectMan "
		+"from tPreWorkCostDetail a "
		+"inner join tCustomer b on a.CustCode=b.code "
		+"left join tWorkCostDetail t on a.pk=t.refPrePk "
		+"left join tXTDM x1 on x1.CBM=a.Status and x1.ID='PRECOSTSTATUS' "
		+"left join tWorkType2 w2 on w2.Code=a.WorkType2 "
		+"left join tWorkType1 w1 on w1.Code=w2.WorkType1 "
		+"left join tSalaryType st on a.SalaryType = st.Code "
		+"LEFT JOIN dbo.tWorkCost wc ON wc.No = a.No "
		+"where a.pk=? ";

		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{pk});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public boolean canCommit(String custCode, String workType2) {
		String sql = "select count(*) num "
           +"from tworkType2 a inner join tPrjProg b on a.PrjItem=b.PrjItem and b.custCode=? "
           +"where a.code=? and b.enddate is null";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,workType2});
		if (list!=null && list.size()>0){
			Map<String,Object> map = list.get(0);
			return Integer.parseInt(String.valueOf(map.get("num")))==0;
		}
		return true;
	}
	
	
//PW
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PreWorkCostDetail pWorkCostDetail,
			UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		String sql =  " select * from (select a.PK,a.CustCode,b.Descr,b.Address,a.Status,x1.NOTE StatusDescr," +
				"a.WorkType2,w2.Descr workType2Descr,a.AppAmount,w2.WorkType1,w1.Descr workType1Descr," +
				"a.SalaryType,st.Descr salaryTypeDescr,a.ActName,a.WorkerCode,a.CardID,a.ApplyDate,a.CfmAmount,a.ConfirmAssist," +
				"e2.NameChi ConfirmAssistDescr,a.AssistConfirmDate,a.ConfirmManager,e3.NameChi ConfirmManagerDescr," +
				"a.ManagerConfirmDate,a.Remarks,a.ApplyMan,ee.NameChi ApplyManDescr,a.LastUpdatedBy,a.LastUpdate," +
				"dt2.Desc2 Department2Descr,b.DocumentNo,b.Area,a.IsWithHold,x2.Note IsWithHoldDescr,a.WithHoldNo," +
				"a.IsWorkApp,a.WorkAppAmount,x3.Note IsWorkAppDescr,b.CheckStatus,x4.note CheckStatusDescr,dbo.fGetWorkload(a.CustCode,w2.workType12) Workload, " +
				"w2.IsConfirmTwo,isnull(c.QualityFee,0) QualityFee,isnull(c.ConfirmAmount,0)-isnull(c.QualityFee,0) RealAmount " +// 增加工资是否二次审核字段,工人质保金,实发金额 --add by zb
				"from tPreWorkCostDetail a " +
				"inner join tCustomer b on a.CustCode=b.code " +
				"left join tXTDM x1 on x1.CBM=a.Status and x1.ID='PRECOSTSTATUS' " +
				"left join tXTDM x2 on x2.CBM=a.IsWithHold and x2.ID='YESNO' " +
				"left join tXTDM x3 on x3.CBM=a.IsWorkApp and x3.ID='YESNO' " +
				"left join tXTDM x4 on x4.CBM=b.CheckStatus and x4.ID='CheckStatus' " +
				"left join tWorkType2 w2 on w2.Code=a.WorkType2 " +
				"left join tWorkType1 w1 on w1.Code=w2.WorkType1 " +
				"left join tSalaryType st on a.SalaryType=st.Code " +
				"left join tEmployee ee on a.ApplyMan=ee.Number " +
				"left join tEmployee e2 on a.ConfirmAssist=e2.Number " +
				"left join tEmployee e3 on a.ConfirmManager=e3.Number " +
				"left join tDepartment2 dt2 on dt2.code=ee.Department2 " +
				"left join tWorkCostDetail c on c.RefPrePk = a.PK " +
				"where a.Expired='F' and "+
				SqlUtil.getCustRight(uc, "b", "20");

		if (StringUtils.isNotBlank(pWorkCostDetail.getAddress())) {
			sql += " and b.address like ? ";
			list.add("%"+pWorkCostDetail.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(pWorkCostDetail.getDepartment2())) {
			sql += " and dt2.Code= ? ";
			list.add(pWorkCostDetail.getDepartment2());
		}
    	
    	if (StringUtils.isNotBlank(pWorkCostDetail.getIsWorkApp())) {
			sql += " and a.IsWorkApp = ? ";
			list.add(pWorkCostDetail.getIsWorkApp());
		}  	
    	
    	if (StringUtils.isNotBlank(pWorkCostDetail.getProjectMan())) {
			sql += " and a.applyMan = ? ";
			list.add(pWorkCostDetail.getProjectMan());
		}
    	
    	if (StringUtils.isNotBlank(pWorkCostDetail.getStatus())) {
			sql += " and a.Status in " + "('"+pWorkCostDetail.getStatus().replace(",", "','" )+ "')";
		}
    	       	
    	if (StringUtils.isNotBlank(pWorkCostDetail.getWorkType1())) {
			sql += " and w2.WorkType1 = ? ";
			list.add(pWorkCostDetail.getWorkType1());
		}
    	
    	if (StringUtils.isNotBlank(pWorkCostDetail.getWorkType2())) {
			sql += " and a.WorkType2 = ? ";
			list.add(pWorkCostDetail.getWorkType2());
		}
    	
    	if (StringUtils.isNotBlank(pWorkCostDetail.getWorkerCode())) {
			sql += " and a.WorkerCode = ? ";
			list.add(pWorkCostDetail.getWorkerCode());
		}
//    	if ("F".equals(pWorkCostDetail.getExpired())) {
//			sql +=" and exists (select 1 from  tbuilder b1 " 
//				+ " left join tczyspcbuilder cb on cb.spcbuilder = b1.SpcBuilder "
//				+ " where b.BuilderCode is null or b.BuilderCode='' or ( b.BuilderCode=b1.Code and "  
//				+ " ( b1.SpcBuilder is null or b1.SpcBuilder='' " 
//				+ " or (cb.SpcBuilder=b1.SpcBuilder and cb.CZYBH=?)))) ";
//			list.add(pWorkCostDetail.getLastUpdatedBy());
//		}
    	if ("F".equals(pWorkCostDetail.getExpired())) {
			sql +=" and not exists (select 1 from tBuilder where IsPrjSpc='1' and PrjLeader<>? and code=b.BuilderCode) ";
			list.add(uc.getEmnum());
		}
		//  当没有工程角色时或者tWorkType2没有限制tWorkType12时，可以查看所有工种类型，
    	// 当有工程角色时，只能查看工程角色内的工种类型 --add by zb
    	sql += " and exists ( " +
 			   "   select 1 from tCZYBM c " +
 			   "   where c.CZYBH=? and ( " +
 			   "     c.PrjRole is null or c.PrjRole='' " +
 			   "	 or not exists (select 1 from tPrjRoleWorkType12 where PrjRole=c.PrjRole) " +
 			   "	 or exists (select 1 from tPrjRoleWorkType12 where PrjRole=c.PrjRole and WorkType12=w2.Worktype12)" +
 			   "   ) " +
 			   " ) ";
 		list.add(uc.getCzybh());
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	

	
	public List<Map<String,Object>> getMsg(int PK){
		String sql="select a.pk,a.CustCode,a.WorkType2,b.IsUpPosiPic,c.WorkType1 from tPreWorkCostDetail a " +
				"inner join tCustomer b on a.CustCode=b.Code " +
				"left join tWorkType2 c on a.WorkType2=c.Code " +
				"where PK=? ";		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{PK});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			 return list;
		}
		return null;
	}
	
	public Map<String,Object> getWorkType1(String workType2){
		String sql="select WorkType1 from tWorkType2 where Code= ? ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{workType2});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			 return list.get(0);
		}
		return null;
	}
	
	public Map<String,Object> getXTCS(String xtcsid){
		String sql="select QZ from tXTCS where ID = ? ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{xtcsid});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			 return list.get(0);
		}
		return null;
	}
	// 查询该操作员是否拥有该工人对应工种12的权限	
	public Map<String,Object> getzlqx(String czybh,String workerCode){
		String sql= "if exists ( " +
					"	select 1 from tWorkType12 a where " +
					"	exists (select * from tCZYBM a1 " +
					"				left join tPrjRoleWorkType12 a2 on a1.PrjRole=a2.PrjRole " +
					"				where (a2.WorkType12=a.code or a2.WorkType12 is null ) and a1.CZYBH=?" +
					"			 ) " +
					"	and Code=(select WorkType12 from tWorker where Code=?) " +
					") begin select Count=1 end else select Count=0 ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{czybh,workerCode});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			 return list.get(0);
		}
		return null;
	}
	
	//取到工人所对应的工种名称
	public Map<String,Object> getworkType12(String workerCode){
		String sql= " select wt.Descr Descr from tWorker a left join tWorkType12 wt on a.WorkType12=wt.Code where a.Code=? ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{workerCode});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			 return list.get(0);
		}
		return null;
	}
	
	public Map<String,Object> getWorkCon(String custCode,String workType2){
		String sql="select Amount from tWorkCon where CustCode= ? and WorkType2= ? ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{custCode,workType2});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			 return list.get(0);
		}
		return null;
	}

	public Map<String,Object> getAmount(String Yukou){
		String sql="select Amount from tPrjWithHold where PK= ? ";		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{Yukou});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			 return list.get(0);
		}
		return null;
	}
	
	public List<Map<String,Object>> getret(String WithHoldNo){
		String sql="select sum(t.ConfirmAmount) ret1 from (  select a.ConfirmAmount," +
				"a.WithHoldNo from tWorkCostDetail a  left outer join tWorkCost b on a.No=b.No  " +
				"where b.Status <> '3'  and a.WithHoldNo = ?  ) t  ";		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{WithHoldNo});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			 return list;
		}
		return null;
	}
	
	public List<Map<String,Object>> getMaxPk(int pK){
		String sql="select t.PK,t.No,t.CustCode,t1.DocumentNo,t1.Descr CustCodeDescr," +
				"t1.Address,t1.SignDate,t.ApplyMan,e3.NameChi ApplyManDescr,t.SalaryType," +
				"t2.Descr SalaryTypeDescr,t3.WorkType1,t6.Descr WorkType1Descr,t.WorkType2," +
				"t3.Descr WorkType2Descr,t.AppAmount,t.Remarks,t.ActName,t.CardID,t1.Area," +
				"t.CfmAmount,t.IsWithHold,t.WithHoldNo,p.ConfirmAmount,p.ConfirmRemark,t.Status," +
				"t5.NOTE StatusDescr,t.LastUpdate,t.LastUpdatedBy,t.Expired,t.ActionLog, " +
				"isnull(dbo.fGetCustBaseCtrl_Com(t.CustCode,1,t.WorkType2),0) CustCtrl," +				
				"(case when ct1.Type='1' then isnull(dbo.fGetCustBaseCost_Com(t.CustCode,1,t.WorkType2),0)  else c.CustCost end )CustCost," +				
				"isnull(dbo.fGetCustBaseCtrl_Com(t.CustCode,3,''),0) AllCustCtrl," +
				"isnull(dbo.fGetCustBaseCost_Com(t.CustCode,3,''),0) AllCustCost, " +
				"(isnull(dbo.fGetCustBaseCtrl_Com(t.CustCode,1,t.WorkType2),0)-isnull(dbo.fGetCustBaseCost_Com(t.CustCode,1,t.WorkType2),0)) LeaveCustCost," +
				"(isnull(dbo.fGetCustBaseCtrl_Com(t.CustCode,3,''),0)-isnull(dbo.fGetCustBaseCost_Com(t.CustCode,3,''),0)) AllLeaveCustCost," +
				"t.IsWorkApp,t.WorkAppAmount from tPreWorkCostDetail t " +
				"left join tWorkCostDetail p on t.pk=p.refPrePk " +
				"left join tCustomer t1 on t1.Code=t.CustCode " +
				"left join tCusttype ct1 on t1.CustType=ct1.Code "+
				"left join tSalaryType t2 on t2.Code=t.SalaryType " +
				"left join tWorkType2 t3 on t3.Code=t.WorkType2 " +
				"left join tWorkType1 t6 on t6.Code=t3.WorkType1 " +
				"left join tXTDM t5 on t5.IBM=t.Status and t5.ID = 'PRECOSTSTATUS' " +
				"left join tEmployee e3 on t.ApplyMan=e3.Number " +
				"left join (select b.custcode,WorkType2,sum(b.ConfirmAmount)CustCost from tWorkCost a "+
				"			inner join tWorkCostDetail b on a.No=b.No "+
				"			where 1=1  "+
				"				  and b.Status <> '3'  "+   //非取消
				"				  and b.IsCalCost='1'  "+
				"			group by b.WorkType2,b.custcode,WorkType2)c on t.CustCode=c.CustCode and t.WorkType2=c.WorkType2 "+
				"where t.pk=?";		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{pK});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			 return list;
		}
		return null;
	}
	
	public List<Map<String,Object>> getCodeType(String CustCode,String WorkType2){
		String sql=" select b.PrjItem,c.NOTE PrjItemDescr,b.EndDate, b.ConfirmCZY," +
				"d.ZWXM ConfirmCZYDescr,b.ConfirmDate from tWorkType2 a " +
				"inner join tPrjProg b on a.PrjItem=b.PrjItem and b.CustCode= ? " +
				"left join tXTDM c on a.PrjItem=c.CBM and c.ID='PrjItem' " +
				"left join tCZYBM d on b.ConfirmCZY=d.CZYBH where a.Code= ? ";		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{CustCode,WorkType2});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			 return list;
		}
		return null;
	}
	
	public List<Map<String,Object>> getNotNullCustCode(String CustCode){
		String sql=" select isnull(dbo.fGetCustBaseCtrl_Com(?,3,''),0) AllCustCtrl," +
				"isnull(dbo.fGetCustBaseCost_Com(?,3,''),0) AllCustCost," +
				"(isnull(dbo.fGetCustBaseCtrl_Com(?,3,''),0)-isnull(dbo.fGetCustBaseCost_Com(?,3,''),0)) AllLeaveCustCost ";		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{CustCode,CustCode,CustCode,CustCode});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			 return list;
		}
		return null;
	}
	
	public List<Map<String,Object>> getNotNullWorkType2(String CustCode,String WorkType2){
		String sql=" select isnull(dbo.fGetCustBaseCtrlByWorktype1_Com(?,1,?),0) CustCtrl," +
				"isnull(dbo.fGetCustBaseCostByWorktype1_Com(?,1,?),0) CustCost," +
				"(isnull(dbo.fGetCustBaseCtrlByWorktype1_Com(?,1,?),0)-isnull(dbo.fGetCustBaseCostByWorktype1_Com(?,1,?),0)) LeaveCustCost ";		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{CustCode,WorkType2,CustCode,WorkType2,CustCode,WorkType2,CustCode,WorkType2});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			 return list;
		}
		return null;
	}
	
	public Map<String,Object> getCfmAmountByWt2(String custCode,String workType2){
		String sql="select isnull(sum(a.CfmAmount), 0) CfmAmount from ( "
			+"select case when a.Status in ('6', '7') then c.ConfirmAmount else a.CfmAmount end CfmAmount  "
			+"from tPreWorkCostDetail a "
			+"inner join tWorkType2 b on a.WorkType2=b.Code   "
			+"left join tWorkCostDetail c on a.PK = c.RefPrePk "
			+"where  b.Code=? " 
			//已审核工资对于‘不计项目经理成本’的工资不计算 modify by zb on 20191127
			+"and  a.Status in ('4','5','6','7') and a.CustCode= ? and b.IsCalProjectCost='1' "
			+"union "
			+"select case when a.Status = '1' then a.AppAmount else a.ConfirmAmount end "
			+"from tWorkCostDetail a "
			+"inner join tWorkType2 b on a.WorkType2 = b.Code "
			+"where a.RefPrePk is not null " 
			+"and b.Code = ? "
			+"and a.Status <> '3' and a.CustCode=? and b.IsCalProjectCost='1' and isnull(a.RefFixDutyManPk, 0) = 0) a ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{workType2,custCode,workType2,custCode});
		if (list!=null && list.size()>0){
			 return list.get(0);
		}
		return null;
	}
	public Map<String,Object> getCfmAmountByWt12(String custCode,String workType2){
		String sql="select isnull(sum(a.CfmAmount), 0) CfmAmount from ( "
			+"select case when a.Status in ('6', '7') then c.ConfirmAmount else a.CfmAmount end CfmAmount  "
			+"from tPreWorkCostDetail a "
			+"inner join tWorkType2 b on a.WorkType2=b.Code   "
			+"left join tWorkCostDetail c on a.PK = c.RefPrePk "
			+"where b.Worktype12 = (select WorkType12 from tWorkType2 where Code=?)  " 
			//已审核工资对于‘不计项目经理成本’的工资不计算 modify by zb on 20191127
			+"and  a.Status in ('4','5','6','7') and a.CustCode= ? and b.IsCalProjectCost='1' "
			+"union "
			+"select case when a.Status = '1' then a.AppAmount else a.ConfirmAmount end "
			+"from tWorkCostDetail a "
			+"inner join tWorkType2 b on a.WorkType2 = b.Code "
			+"where a.RefPrePk is not null " 
			+"and b.Worktype12 = (select WorkType12 from tWorkType2 where Code=?) "
			+"and a.Status <> '3' and a.CustCode=? and b.IsCalProjectCost='1' and isnull(a.RefFixDutyManPk, 0) = 0) a ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{workType2,custCode,workType2,custCode});
		if (list!=null && list.size()>0){
			 return list.get(0);
		}
		return null;
	}
	public List<Map<String,Object>> findWorkType1(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.Code id,a.Descr name from tWorkType1 a where Expired='F'  ";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.code not  in ("+param.get("pCode")+") ";
		}
		sql += " order by a.Code";
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String,Object>> findWorkType2(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.Code id,a.Descr name  from tWorkType2 a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.workType1=?";
			list.add((String)param.get("pCode"));			
		}
		sql += " order by a.Code";
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String,Object>> findWorkType2ForPrj(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.Code id,a.Descr name  from tWorkType2 a where a.expired='F' and CalType='1' ";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.workType1=?";
			list.add((String)param.get("pCode"));			
		}
		sql += " order by a.Code";
		return this.findBySql(sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result doZhuLiReturnCheckOut(PreWorkCostDetail pWorkCostDetail) {
		Assert.notNull(pWorkCostDetail);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPWorkCostDetailConfirm(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, pWorkCostDetail.getM_umState());
			call.setLong(2, pWorkCostDetail.getPk());
			call.setString(3, pWorkCostDetail.getStatus());
			call.setString(4, pWorkCostDetail.getConfirmAssist());
			call.setTimestamp(5, new java.sql.Timestamp(pWorkCostDetail.getAssistConfirmDate().getTime()));
			call.setTimestamp(6, new java.sql.Timestamp(pWorkCostDetail.getLastUpdate().getTime()));
			call.setString(7, pWorkCostDetail.getLastUpdatedBy());
			call.setString(8, pWorkCostDetail.getExpired());
			call.setString(9, pWorkCostDetail.getActionLog());	
			call.setString(10, String.valueOf(pWorkCostDetail.getCfmAmount()));				
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.NVARCHAR);
			call.setString(13, pWorkCostDetail.getTips());	
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
	
	/**
	 * 从预申请列表导入基础人工成本明细
	 * 
	 * @param page
	 * @param preWorkCostDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql2(Page<Map<String,Object>> page, PreWorkCostDetail preWorkCostDetail) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.*,a.AllCustCtrl-a.AllCustCost AllLeaveCustCost from(select a.pk refPrePk,a.CfmAmount ConfirmAmount,a.withholdno,'1' status,a.actname,a.cardid,a.cardid2,a.appamount,a.cfmamount,a.applyman,a.applydate,a.remarks,"
				    +"a.assistconfirmdate,a.IsWithHold,a.managerconfirmdate,b.Address,s.Descr salaryTypeDescr,w2.Descr workType2Descr,w1.Descr WorkType1Descr,"
				    +"e1.NameChi applyManDescr,e2.NameChi ConfirmAssistDescr,e3.NameChi ConfirmManagerDescr,b.DocumentNo, "
				    +"x1.NOTE IsWithHoldDescr,'申请' StatusDescr,a.CustCode,a.WorkType2,w2.WorkType1,a.SalaryType,b.SignDate,"
				    + "isnull(dbo.fGetCustBaseCtrl_Com(a.CustCode,1,a.WorkType2),0) CustCtrl,isnull(dbo.fGetCustBaseCost_Com(a.CustCode,1,a.WorkType2),0) CustCost,"
					+ "isnull(dbo.fGetCustBaseCtrl_Com(a.CustCode,3,''),0) AllCustCtrl,isnull(dbo.fGetCustBaseCost_Com(a.CustCode,3,''),0) AllCustCost, "
					+ "(isnull(dbo.fGetCustBaseCtrl_Com(a.CustCode,1,a.WorkType2),0)-isnull(dbo.fGetCustBaseCost_Com(a.CustCode,1,a.WorkType2),0)) LeaveCustCost,"
					//+ "(isnull(dbo.fGetCustBaseCtrl_Com(a.CustCode,3,''),0)-isnull(dbo.fGetCustBaseCost_Com(a.CustCode,3,''),0)) AllLeaveCustCost,"
					+ "round(isnull(dbo.fGetCustBaseCtrl_Com(a.CustCode,1,a.WorkType2),0)*isnull(twcc.CtrlPer,1.1),2) CustCtrl_Kzx,"
					+ "a.WorkerCode,x2.Note CheckStatusDescr,t8.Note IsSignDescr,b.iswateritemctrl,isnull(bc.workerPlanOffer,0)workerPlanOffer,"
					+ "isnull(twt.QualityFeeBegin,0) QualityFeeBegin,b.status custStatus,t9.note iswateritemctrldescr,ct.desc1 custtypedescr,tw.idnum "
				    +"from tPreWorkCostDetail a "
				    +"inner join tcustomer b on a.CustCode=b.Code "
				    +"left join tSalaryType s on a.SalaryType=s.Code "
				    +"left join tWorkType2 w2 on a.WorkType2=w2.Code "
				    +"left join tWorkType1 w1 on w2.WorkType1=w1.Code "
				    +"left join tEmployee e1 on a.applyMan=e1.number "
				    +"left join tEmployee e2 on a.ConfirmAssist=e2.number "
				    +"left join tEmployee e3 on a.ConfirmManager=e3.number "
				    +"left outer join tXTDM x1 on x1.IBM=a.IsWithHold and x1.ID = 'YESNO' "
					+"left outer join tWorker tw on tw.Code=a.WorkerCode "
					+"left outer join tWorkType12 twt on twt.Code=tw.WorkType12 " 
					+"left join tXTDM x2 on x2.CBM = b.CheckStatus and x2.id = 'CheckStatus' "
					+"left outer join tXTDM t8 on t8.IBM=tw.IsSign and t8.ID = 'YESNO' "
					+"left outer join tXTDM t9 on t9.IBM=b.iswateritemctrl and t9.ID = 'YESNO' "
					+"left outer join tCustType ct on b.custtype=ct.code "
					+"left outer join tWorkCostCtrl twcc on twcc.WorkType2=a.WorkType2 and twcc.IsSign=tw.IsSign "
					+"left outer join (  SELECT CustCode,SUM(a.offerpri*qty) workerPlanOffer,b.worktype12  "
					+"	 from tBaseCheckItemPlan a,   tBaseCheckItem b   WHERE a.BaseCheckItemCode=b.Code "
					+"GROUP BY Custcode,b.worktype12) bc  ON bc.custcode=b.code and w2.worktype12=bc.worktype12 "
				    +"where 1=1 and (a.status = '4' or a.no=?) "
                    +"and a.expired='F' ";
		list.add(preWorkCostDetail.getAppNo());
		if (StringUtils.isNotBlank(preWorkCostDetail.getRefPrePks())) {
			sql += " and a.Pk not in(" + preWorkCostDetail.getRefPrePks()
					+ ") ";
		}
		if (StringUtils.isNotBlank(preWorkCostDetail.getAddress())) {
			sql += " and b.address like ? ";
			list.add("%"+preWorkCostDetail.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(preWorkCostDetail.getWorkerCode())) {
			sql += " and a.workerCode=? ";
			list.add(preWorkCostDetail.getWorkerCode());
		}
		if (StringUtils.isNotBlank(preWorkCostDetail.getWorkType1())) {
			sql += " and w2.WorkType1=? ";
			list.add(preWorkCostDetail.getWorkType1());
		}
		if (StringUtils.isNotBlank(preWorkCostDetail.getWorkType2())) {
			sql += " and a.WorkType2=? ";
			list.add(preWorkCostDetail.getWorkType2());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a)a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a)a order by a.refPrePk";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	public Map<String, Object> getCustWorkerInfo(String custCode, String workerCode){
		String sql = " select * from tCustWorker where CustCode=? and workerCode=? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{custCode, workerCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * @Description:  根据工种分类2表中的worktype12检查工地工人信息表中是否存在对应数据
	 * @author	created by zb
	 * @date	2018-9-12--下午3:29:14
	 * @param workType12
	 * @return
	 */
	public boolean hasCustWork(String workType2) {
		String sql;
		List<Map<String,Object>> list;
		sql = " select 1 from tWorkType2 wt2 " +
			  " 	inner join tWorkType12 wt12 on ltrim(wt2.Worktype12) = ltrim(wt12.Code) " +
			  " 	inner join tCustWorker a on a.WorkType12 = wt12.Code " +
			  " where wt2.Code = ? ";
		list = this.findBySql(sql, new Object[]{workType2});
		if(list != null && list.size() > 0){// 当存在时返回true
			return true;
		}
		return false;
	}

	/**
	 * 获取定额工资明细
	 * @author	created by zb
	 * @date	2018-11-7--下午3:21:26
	 * @param page
	 * @param pWorkCostDetail
	 * @return
	 */
	public Page<Map<String, Object>> getQuotaSalaryJqGrid(
			Page<Map<String, Object>> page, PreWorkCostDetail pWorkCostDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from( " +
				"select c.Worktype12, f.Descr BaseItemDescr, d.Descr FixAreaDescr, a.Qty, "+
				"	a.OfferPri, f.Uom, g.Descr UomDescr, isnull(a.Qty, 0) * isnull(a.OfferPri, 0) TotalPrice "+
				"from   tBaseCheckItemPlan a "+
				"left join tPreWorkCostDetail b on b.CustCode = a.CustCode "+
				"inner join tWorkType2 c on c.Code = b.WorkType2 "+
				"left join tFixArea d on d.PK = a.FixAreaPK "+
				"inner join tBaseCheckItem f on f.Code = a.BaseCheckItemCode and f.WorkType12 = c.Worktype12 "+
				"left join tUOM g on g.Code = f.Uom "+
				"where  b.PK = ?";

		list.add(pWorkCostDetail.getPk());
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 同工种1二级已审核的工资
	 * @param custCode
	 * @param workType1
	 * @return
	 */
	public Map<String,Object> getCfmAmountByWorkType1(String custCode,String workType1){
		String sql=" select isnull(sum(CfmAmount),0) CfmAmount " +
				"from tPreWorkCostDetail a " +
				"left join tWorkType2 b on a.WorkType2=b.Code " +
				"where Status ='4' and a.CustCode= ? and b.WorkType1= ? and b.IsCalProjectCost='1' ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{custCode,workType1});
		if (list!=null && list.size()>0){
			 return list.get(0);
		}
		return null;
	}
	/**
	 * 是否面向工人解单
	 * @param custCode
	 * @return
	 */
	public Map<String,Object> hasBaseCheckItemPlan(String custCode){
		String sql=" select 1 from tBaseCheckItemPlan where CustCode=?";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			 return list.get(0);
		}
		return null;
	}
	/**
	 * 定额工资 定额工资 -工资控制类型为工种12
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public String getQuotaSalaryByWt12(PreWorkCostDetail preWorkCostDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select isnull(sum(isnull(a.Qty, 0) * isnull(a.OfferPri, 0)),0) quotaSalary "
				+"from tBaseCheckItemPlan a "
				+"left join tBaseCheckItem b on a.BaseCheckItemCode=b.Code  "
				+"left join tFixArea c on c.PK = a.FixAreaPK "
				+"left join tUOM d on d.Code = b.Uom "
				+"where a.CustCode=? and b.worktype12  =(select WorkType12 from tWorkType2 where Code=?)";
		list.add(preWorkCostDetail.getCustCode());
		list.add(preWorkCostDetail.getWorkType2());
		List<Map<String, Object>> resultList= this.findBySql(sql, list.toArray());
		if(resultList.size()>0){
			return resultList.get(0).get("quotaSalary").toString();
					
		}
		return "0";
	}
	
	/**
	 * 定额工资 -工资控制类型为工种2
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public String getQuotaSalaryByWt2(PreWorkCostDetail preWorkCostDetail) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select isnull(sum(isnull(a.Qty, 0) * isnull(a.OfferPri, 0)),0) quotaSalary "
			+"from tBaseCheckItemPlan a "
			+"left join tBaseCheckItem b on a.BaseCheckItemCode=b.Code "  
			+"left join tBaseItemType2 e on b.BaseItemType2=e.Code "
			+"left join tFixArea c on c.PK = a.FixAreaPK " 
			+"left join tUOM d on d.Code = b.Uom " 
			+"where a.CustCode=? and e.OfferWorkType2=?";
		list.add(preWorkCostDetail.getCustCode());
		list.add(preWorkCostDetail.getWorkType2());
		List<Map<String, Object>> resultList= this.findBySql(sql, list.toArray());
		if(resultList.size()>0){
			return resultList.get(0).get("quotaSalary").toString();
					
		}
		return "0";
	}
	
	public Page<Map<String, Object>> goCfmAmountByWt12JqGrid(Page<Map<String, Object>> page, String custCode, String workType2) {
		String sql=" select isnull(a.CfmAmount, 0) CfmAmount, a.Address, a.WorkType2Descr, a.WrokType1Descr "
				  +" from ( "
				  +"	select case when a.Status in ('6', '7') then c.ConfirmAmount else a.CfmAmount end CfmAmount, "
				  +"	d.Address, b.Descr WorkType2Descr, e.Descr WrokType1Descr "
				  +"	from tPreWorkCostDetail a "
				  +"	inner join tWorkType2 b on a.WorkType2=b.Code   "
				  +"	left join tWorkCostDetail c on a.PK = c.RefPrePk "
				  +"    left join tCustomer d on d.Code = a.CustCode "
				  +"	left join tWorkType1 e on e.Code = b.WorkType1 "	
				  +"	where b.Worktype12 = (select WorkType12 from tWorkType2 where Code=?)  " 
				  //已审核工资对于‘不计项目经理成本’的工资不计算 modify by zb on 20191127
				  +"	and  a.Status in ('4','5','6','7') and a.CustCode= ? and b.IsCalProjectCost='1' "
				  +"	union "
				  +"	select case when a.Status = '1' then a.AppAmount else a.ConfirmAmount end, "
				  +"	c.Address, b.Descr WorkType2Descr, d.Descr WrokType1Descr "
				  +"	from tWorkCostDetail a "
				  +"	inner join tWorkType2 b on a.WorkType2 = b.Code "
				  +"    left join tCustomer c on c.Code = a.CustCode "
				  +"	left join tWorkType1 d on d.Code = b.WorkType1 "	
				  +"	where a.RefPrePk is not null " 
				  +"	and b.Worktype12 = (select WorkType12 from tWorkType2 where Code=?) "
				  +"	and a.Status <> '3' and a.CustCode=? and b.IsCalProjectCost='1' "
				  +"	and isnull(a.RefFixDutyManPk, 0) = 0 "
				  +" ) a ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += "order by " + page.getPageOrderBy() + " " + page.getPageOrder();
		}
		return this.findPageBySql(page, sql, new Object[]{workType2,custCode,workType2,custCode});
	}

	public Page<Map<String,Object>> goCfmAmountByWt2JqGrid(Page<Map<String, Object>> page, String custCode,String workType2){
		String sql=" select isnull(a.CfmAmount, 0) CfmAmount, a.Address, a.WorkType2Descr, a.WrokType1Descr  "
				  +" from ( "
			      +" 	select case when a.Status in ('6', '7') then c.ConfirmAmount else a.CfmAmount end CfmAmount, "
				  +"	d.Address, b.Descr WorkType2Descr, e.Descr WrokType1Descr "
			      +"	from tPreWorkCostDetail a "
			      +"	inner join tWorkType2 b on a.WorkType2=b.Code   "
			      +"	left join tWorkCostDetail c on a.PK = c.RefPrePk "
			      +"	left join tCustomer d on d.Code = a.CustCode "
			      +" 	left join tWorkType1 e on e.Code = b.WorkType1 "
			      +"	where  b.Code=? " 
			      //已审核工资对于‘不计项目经理成本’的工资不计算 modify by zb on 20191127
			      +"	and  a.Status in ('4','5','6','7') and a.CustCode= ? and b.IsCalProjectCost='1' "
			      +"	union "
			      +"	select case when a.Status = '1' then a.AppAmount else a.ConfirmAmount end, "
			      +"	c.Address, b.Descr WorkType2Descr, d.Descr WrokType1Descr "
			      +"	from tWorkCostDetail a "
			      +"	inner join tWorkType2 b on a.WorkType2 = b.Code "
			      +"	left join tCustomer c on c.Code = a.CustCode "
			      +" 	left join tWorkType1 d on d.Code = b.WorkType1 "
			      +"	where a.RefPrePk is not null " 
			      +"	and b.Code = ? "
			      +"	and a.Status <> '3' and a.CustCode=? and b.IsCalProjectCost='1' and isnull(a.RefFixDutyManPk, 0) = 0 "
			      +" ) a ";
		
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += "order by " + page.getPageOrderBy() + " " + page.getPageOrder();
		}
		
		return this.findPageBySql(page, sql, new Object[]{workType2,custCode,workType2,custCode});
	}
	
	public Page<Map<String,Object>> goCfmAmountByWorkType1JqGrid(Page<Map<String, Object>> page, String custCode,String workType1){
		String sql=" select * from ( "
				  +" 	select isnull(CfmAmount,0) CfmAmount, c.Address,b.Descr WorkType2Descr, d.Descr WorkType1Descr " 
				  +"	from tPreWorkCostDetail a " 
				  +"	left join tWorkType2 b on a.WorkType2=b.Code " 
				  +"	left join tCustomer c on c.Code = a.CustCode "
				  +" 	left join tWorkType1 d on d.Code = b.WorkType1 "
				  +"	where a.Status ='4' and a.CustCode= ? and b.WorkType1= ? and b.IsCalProjectCost='1' "
				  +" ) a ";
		
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += "order by " + page.getPageOrderBy() + " " + page.getPageOrder();
		}
		
		return this.findPageBySql(page, sql, new Object[]{custCode,workType1});
	}
}

