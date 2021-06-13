package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.GetConfirmInfoEvt;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.ConfirmCustomFiledValue;
import com.house.home.entity.project.PrjConfirmApp;
import com.house.home.entity.project.PrjProg;
import com.house.home.entity.project.PrjProgConfirm;
@SuppressWarnings("serial")
@Repository
public class PrjProgConfirmDao extends BaseDao {



	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PrjProgConfirm prjProgConfirm) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select *,s1.Note PrjItemDescr from tPrjProgConfirm a " +
				"left join tXTDM s1 on s1.CBM= a.PrjItem and s1.Id='PRJITEM'" +
				"where 1=1 ";
	
		if (StringUtils.isNotBlank(prjProgConfirm.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(prjProgConfirm.getCustCode());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}
		
		return this.findPageBySql(page, sql, list.toArray());

	}
	
	public Page<Map<String, Object>> findConfirmPageBySql(
			Page<Map<String, Object>> page, PrjProgConfirm prjProgConfirm,UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from ( select x4.note prjWorkabledescr,case when a.custScore is null or custScore = '' then ''" +
				"	 else cast (a.custScore as char(1)) + '星' end custScore,a.custRemarks ,dep.desc1 department1descr,a.No,a.CustCode,a.PrjItem,a.Remarks, " +
						"a.ErrPosi,x3.NOTE ErrPosiDescr,a.Address GPSAddress,a.Ispass,a.Date,c.NOTE " +
						"PrjItemDescr,b.ProjectMan,b.Address, f.NameChi ProjectManDescr, " +
						" a.LastUpdate,a.LastUpdatedBy,a.Expired, g.zwxm ConfirmMen , " +
						"  x1.note  IspassDescr ,x2.NOTE  prjLevelDescr ,i.ComeDate workerComedate ,j.NameChi WorkerDescr,b.Area,tct.Desc1 CustTypeDescr " +
				 		 "from  tPrjProgConfirm  a " +
				 		 " left join tCustomer b on a.CustCode=b.Code " +
				 		 " left join tXTDM c on a.PrjItem = c.CBM and c.ID = 'PRJITEM' " +
				 		 " left join tEmployee f on b.ProjectMan=f.Number " +
				 		 " left join tDepartment2 dep on dep.code=f.department2 " +
				 		 " left join tCZYBM g on g.CZYBH=A.LastUpdatedBy  " +
				 		 " left  join txtdm x1 on x1.cbm=a.IsPass and X1.ID='YESNo'" +
				 		 " left join tXTDM x2 on a.prjLevel=x2.cbm and x2.id='PRJLEVEL' " +
				 		 "left join tPrjItem1 h on a.PrjItem=h.Code " +
				 		 " left join (select max(PK) pk,CustCode,WorkType12 from tCustWorker group by CustCode,WorkType12) tcw " +
				 		 " on h.workType12=tcw.WorkType12 and tcw.CustCode=a.CustCode  " +
				 		 " left join tCustWorker i on i.PK=tcw.pk  "+
				 		 " left join tworker j on i.WorkerCode=j.Code  " +
				 		 " left join tCusttype tct on tct.Code = b.Custtype " +
				 		 " left join tXTDM x3 on x3.ID='YESNO' and x3.CBM=a.ErrPosi " +
				 		 " left join tXtdm x4 on x4.cbm = a.prjWorkable and x4.id='YESNO'" +				 		 " where 1=1  and " +
						 SqlUtil.getCustRight(uc, "b", 0);
	
		
		if (StringUtils.isNotBlank(prjProgConfirm.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+prjProgConfirm.getAddress()+"%");
		}
		
		if(StringUtils.isNotBlank(prjProgConfirm.getDepartment2())){
			sql+=" and f.department2 = ?";
			list.add(prjProgConfirm.getDepartment2());
		}
		if(StringUtils.isNotBlank(prjProgConfirm.getCustScoreComfirm())){
			sql+=" and a.custScore in ('"+prjProgConfirm.getCustScoreComfirm().replace(",", "','")+"')";
		}
		
		if(StringUtils.isNotBlank(prjProgConfirm.getLastUpdatedBy())){
			sql+= " and a.lastUpdatedBy = ?";
			list.add(prjProgConfirm.getLastUpdatedBy());
			
		}
		
		if(StringUtils.isNotBlank(prjProgConfirm.getLastUpdatedBy())){
			sql+= " and a.lastUpdatedBy = ?";
			list.add(prjProgConfirm.getLastUpdatedBy());
			
		}
		if(StringUtils.isNotBlank(prjProgConfirm.getPrjItem1())){
			String str = SqlUtil.resetStatus(prjProgConfirm.getPrjItem1());
			sql += " and a.PrjItem in (" + str + ")";
		}
		if(StringUtils.isNotBlank(prjProgConfirm.getIsPass())){
			sql+= " and  a.isPass= ?";
			list.add(prjProgConfirm.getIsPass() );
			  
		}
		if (prjProgConfirm.getDateFrom() != null) {
			sql += " and a.date>= ? ";
			list.add(prjProgConfirm.getDateFrom());
		}
		if (prjProgConfirm.getDateTo() != null) {
			sql += " and a.date-1<= ? ";
			list.add(prjProgConfirm.getDateTo());
		}
		if(StringUtils.isNotBlank(prjProgConfirm.getConstructType())){
			sql+=" and b.ConstructType = ? ";
			list.add(prjProgConfirm.getConstructType());
		}
		if(StringUtils.isNotBlank(prjProgConfirm.getIsPrjConfirm())){
			sql+=" and b.projectman = a.lastUpdatedby ";
			
		}
		if(StringUtils.isNotBlank(prjProgConfirm.getPrjWorkable())){
			sql+=" and a.PrjWorkable = ? ";
			list.add(prjProgConfirm.getPrjWorkable());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.date desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());

	}
	
	public Page<Map<String, Object>> findPrjConfirmPageBySql(
			Page<Map<String, Object>> page, PrjProgConfirm prjProgConfirm) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select (select count(1) picNum from tPrjProgPhoto where RefNo= a.no group by RefNo) picNum,a.no," +
				" a.prjitem,a.custcode,p.Descr prjitemDescr,x1.NOTE isPassDescr,x2.note prjLevelDescr,a.Remarks,e.NameChi confCZYDescr," +
				" a.Date,a.Address,a.isPass from tPrjProgConfirm a" +
				" left join tPrjItem1 p on p.Code=a.PrjItem " +
				" left join tXTDM x1 on x1.CBM =a.IsPass and x1.ID='YESNO' " +
				" left join tXTDM x2 on x2.cbm=a.prjLevel and x2.id='prjLevel' " +
				" left join tEmployee e on e.Number=a.LastUpdatedBy " +
				" where 1=1 " ;
		if(StringUtils.isNotBlank(prjProgConfirm.getCustCode())){
			sql+=" and a.custcode= ? ";
			list.add(prjProgConfirm.getCustCode());
		}
		if(StringUtils.isNotBlank(prjProgConfirm.getIsPass())){
			sql+=" and a.isPass = ? ";
			list.add(prjProgConfirm.getIsPass());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.date desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());

	}
		
	public Page<Map<String, Object>> getSiteConfirmListById(
			Page<Map<String, Object>> page, String czy,String custCode, String fromPageFlag) {
		// TODO Auto-generated method stub
		List<Object> list = new ArrayList<Object>();
		String sql="SELECT a.no, c.address, a.Date, b.NOTE from tPrjProgConfirm a "
				+"INNER JOIN  tXTDM b ON  a.PrjItem=b.IBM "
				+"INNER JOIN tCustomer c ON a.CustCode=c.Code "
				+"WHERE 1=1 ";
		if("1".equals(fromPageFlag)){
			sql += " and c.Code = ? and a.WorkerCode=? ";
			list.add(custCode);
			list.add(czy);
		}else{
				if(StringUtils.isNotBlank(custCode)){
					sql += " and c.Code = ? ";
					list.add(custCode);
				}else{
					sql += " and a.lastUpdatedBy=? ";
					list.add(czy);
				}
		}
				sql += "  and  b.ID='PRJITEM'  ORDER BY a.Date DESC ";
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> getPrjItemDescr(
			Page<Map<String, Object>> page, String custCode,String prjRole,String prjItem) {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();
		String sql= "select t.Code prjItem, t.Descr PrjItemDescr, tcw.ComeDate, tcw.ConstructDay,a.PK,a.CustCode," 
				+" a.PlanBegin,a.BeginDate,a.PlanEnd,a.EndDate, b.address, tw.NameChi WorkerName " 
				+" from tPrjItem1 t "
				+" left join tPrjProg a on a.PrjItem = t.Code and a.CustCode = ? "
				+" left join tCustomer b on a.CustCode=b.code and a.CustCode=? "
				+" left join (select max(PK) PK,WorkType12 from tCustWorker where CustCode=? group by WorkType12) c on c.WorkType12 = t.WorkType12 "
				+" left join tCustWorker tcw on tcw.PK = c.PK "
				+" left join tWorker tw on tw.Code = tcw.WorkerCode ";
		params.add(custCode);
		params.add(custCode);
		params.add(custCode);
		if(StringUtils.isNotBlank(prjRole)){
			sql += " inner join tPrjRolePrjItem prpi on prpi.PrjItem = t.Code AND prpi.PrjRole = ? ";
			params.add(prjRole);
		}
		sql += " where ( not exists (select 1 from tPrjProgConfirm where PrjItem = t.Code and CustCode = ? and IsPass='1') ";
		params.add(custCode);
		if(StringUtils.isNotBlank(prjItem)){
			sql += " or t.Code = ? ";
			params.add(prjItem);
		}
		sql += " ) AND t.IsConfirm='1' AND t.Expired='F'  "
			 + " ORDER BY t.Seq ";
		return this.findPageBySql(page, sql,params.toArray());
	}
	
	public Page<Map<String, Object>> getWorkerAppPrjItemDescr(
			Page<Map<String, Object>> page, String custCode,String prjRole,String prjItem,String workerApp) {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();
		String sql= "select t.prjPhotoNum,we.pk starPk,we.remark ,we.score,we.healthScore,we.toolScore,tcw.PK custWkPk,a.*,b.address," +
				" t.Descr PrjItemDescr,tcw.ComeDate,tcw.ConstructDay,tw.code WorkerCode,tw.NameChi WorkerName from tPrjProg a " +
				" inner join tCustomer b on a.CustCode=b.code and a.CustCode=? "
				+"left join tPrjItem1 t on a.PrjItem = t.Code "
				+ " left join (select max(PK) PK,WorkType12 from tCustWorker where CustCode=? group by WorkType12) c on c.WorkType12 = t.WorkType12 "
				+ " left join tCustWorker tcw on tcw.PK = c.PK "
				+ " left join tWorker tw on tw.Code = tcw.WorkerCode " +
				" left join (select b.* from (select max(pk) pk,workerCode from tWorkerEval group by CustWkPk ,workerCode) a " +
				" left join tWorkerEval b on b.pk=a.pk) we on we.custWkPk = tcw.pk and  tcw.workerCode=we.workerCode and b.projectMan = we.EvaMan ";
		params.add(custCode);
		params.add(custCode);
		
		sql += " where ( a.ConfirmCZY is null or a.ConfirmCZY ='' ";
		if(StringUtils.isNotBlank(prjItem)){
			sql += " or a.PrjItem = ? ";
			params.add(prjItem);
		}
		sql += " ) AND t.IsConfirm='1' AND t.Expired='F' " +
				" ";
		if(StringUtils.isNotBlank(workerApp)){
			if("sitesBtn".equals(workerApp)){
				sql+=" and exists (select 1 from tprjCOnfirmApp where custcode = ? and prjitem = t.code )";
				params.add(custCode);
			}
		}
			 sql+=" ORDER BY a.PlanBegin,a.PrjItem  ";
		return this.findPageBySql(page, sql,params.toArray());
	}

	public Map<String, Object> getSiteConfirmDetail(String no, String platform, String czybh, String custCode, String prjItem) {
		List<Object> params = new ArrayList<Object>();
		String sql="SELECT we.score,w.namechi workerName,a.No,b.Address house,c.NOTE,a.Date,a.Remarks,a.IsPass ,a.IsPushCust,a.Address,a.CustCode,e.nameChi confirmCzyDescr,"
				+"CASE WHEN a.PrjLevel IS NOT NULL OR a.PrjLevel != '' THEN x1.NOTE ELSE '不合格' END prjLevelDescr,a.PrjItem,a.appCheck,a.PrjWorkable  FROM tPrjProgConfirm a  "
				+"INNER JOIN tCustomer b ON a.CustCode=b.Code "
				+"INNER JOIN tXTDM c ON a.PrjItem=c.IBM "
				+"LEFT JOIN tEmployee e ON e.Number = a.LastUpdatedBy "
				+"LEFT JOIN tXTDM x1 ON x1.cbm = a.PrjLevel AND x1.ID='PRJLEVEL' " +
				" left join tPrjItem1 t on a.PrjItem = t.Code  " +
				" left join (select max(PK) PK,WorkType12,custcode from tCustWorker " +
				" where 1=1 group by WorkType12,custcode) tc on tc.WorkType12 = t.WorkType12 and tc.CustCode=b.Code " +
				" left join tCustWorker tcw on tcw.PK = tc.PK" +
				" left join tWorkerEval we on we.EvaMan=b.ProjectMan and we.Type='3' and we.CustWkPk=tcw.PK " +
				" left join tworker  w on w.code=we.workerCode "
				+" WHERE c.ID='PRJITEM'";
		if("workerApp".equals(platform)){
			sql += " and a.IsPass='1' and WorkerCode=? and custCode=? and prjItem=? ";
			params.add(czybh);
			params.add(custCode);
			params.add(prjItem);
		}else{
			sql += " and a.No=? ";
			params.add(no);
		}
		List<Map<String, Object>> list = this.findBySql(sql, params.toArray());
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	public Page<Map<String,Object>> findConfirmAppPageBySql(Page<Map<String,Object>> page,PrjConfirmApp prjConfirmApp, UserContext uc){
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select a.PK,a.Status,c.Address,e.Desc1 department1Descr,f.Desc1 department2Descr," +
				"d.NameChi projectManDescr,d.Phone, a.AppDate,a.Remarks,b.Date,d1.nameChi ConfirmCZY," 
					+" case when a.RefConfirmNo is not null then case when b.IsPass='1' then h.NOTE else '不合格' end end prjLevelDescr," +
					" h1.Descr prjItemDescr,c.RealAddress,i.Descr RegionDescr,c.area,j.WorkerDescr,a.WorkerCode,k.NameChi WorkerName,tx1.NOTE statusDescr, "
					+" ws.IsLeaveProblem,tx2.NOTE IsLeaveProblemDescr,ws.LeaveProblemRemark,l.Desc1 CustTypeDescr "
					+" from tPrjConfirmApp a "
					+" left join tPrjProgConfirm b on a.RefConfirmNo = b.No "
					+" left join tCustomer c on a.CustCode = c.Code "
					+" left join tEmployee d on c.ProjectMan = d.Number "
					+" left join tDepartment1 e on d.Department1 = e.Code "
					+" left join tDepartment2 f on d.Department2 = f.Code "
					+" left join tEmployee d1 on b.LastUpdatedBy = d1.Number"
					+" left join tBuilder g on c.BuilderCode = g.Code "
					+" left join tXTDM h on h.CBM = b.prjLevel and h.ID='PRJLEVEL' "
					+" left join tPrjItem1 h1 on h1.Code = a.PrjItem "
					+" left join tRegion i on i.Code = g.RegionCode "
					+" left join (" 
					+" 	select a.CustCode,a.WorkType12,c.NameChi WorkerDescr,c.workType12Dept "
					+"  from ( "
					+"  	SELECT MAX(cw.PK) maxPK,cw.CustCode,cw.WorkType12 "
					+"  	FROM dbo.tCustWorker cw "
					+"  	GROUP BY cw.CustCode,cw.WorkType12 "	
					+"	) a "
					+" 	left join tCustWorker b on a.maxPK = b.PK "
					+"	left join tWorker c on c.Code = b.WorkerCode "
					+" ) j on j.CustCode = a.CustCode and h1.worktype12 = j.WorkType12 "
					+" left join tWorker k on k.Code = a.WorkerCode " //增加增加验收申请工人的显示 add by zb
					+" left join tXTDM tx1 on tx1.CBM = a.Status and tx1.ID='CONFMAPPSTS' "
					+" left join tWorkSign ws on a.WorkSignPK=ws.PK"
					+" left join tXTDM tx2 on ws.IsLeaveProblem=tx2.CBM and tx2.ID='YESNO'"
					+" left join tCusttype l on c.CustType=l.Code "
					+" where 1=1 and " + SqlUtil.getCustRight(uc, "c", 0);
		if(StringUtils.isNotBlank(prjConfirmApp.getAddress())){
			sql += " and c.Address like ? ";
			list.add("%"+prjConfirmApp.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(prjConfirmApp.getPrjItem())){
			sql = sql + " and a.PrjItem in ('"+prjConfirmApp.getPrjItem().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(prjConfirmApp.getIsPrjSpc())){
			sql += " and g.IsPrjSpc=? ";
			list.add(prjConfirmApp.getIsPrjSpc());
		}
		if(StringUtils.isNotBlank(prjConfirmApp.getStatus())){
			sql += " and a.Status=? ";
			list.add(prjConfirmApp.getStatus());
		}
		if(StringUtils.isNotBlank(prjConfirmApp.getDepartment1())){
			sql += " and d.Department1=? ";
			list.add(prjConfirmApp.getDepartment1());
		}
		if(prjConfirmApp.getAppDateFrom() != null){
			sql += " and cast(a.appDate as date) >= ? ";
			list.add(prjConfirmApp.getAppDateFrom());
		}
		if(prjConfirmApp.getAppDateTo() != null){
			sql += " and cast(a.appDate as date) <= ? ";
			list.add(prjConfirmApp.getAppDateTo());
		}
		if(StringUtils.isNotBlank(prjConfirmApp.getRegion())){
			sql += " and g.RegionCode=? ";
			list.add(prjConfirmApp.getRegion());
		}
		if(StringUtils.isNotBlank(prjConfirmApp.getDepartment2())){
			sql += " and d.Department2=? ";
			list.add(prjConfirmApp.getDepartment2());
		}
		if(StringUtils.isNotBlank(prjConfirmApp.getCustType())){
			sql += " and c.Custtype in ('"+prjConfirmApp.getCustType().trim().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(prjConfirmApp.getPrjRegion())){
			sql+=" and exists (select 1 from tRegion2 in_a where in_a.Code = g.regionCode2 and in_a.prjRegionCode = ? )";
			list.add(prjConfirmApp.getPrjRegion());
		}
		if(StringUtils.isNotBlank(prjConfirmApp.getWorkType12Dept())){
			sql+=" and j.workType12Dept = ? ";
			//j.workType12
			list.add(prjConfirmApp.getWorkType12Dept());
		}
		
		// 没有工程角色或此工程角色没有对应工种类型，可以查看所有工种类型
		// 当有工程角色时，只能查看工程角色内的工种类型
		// 参考于工人信息管理中查询方法
		// 张海洋 20200604
        sql += " and exists ( " +
               "   select 1 from tCZYBM c " +
               "     where c.CZYBH = ? and ( " +
               "       c.PrjRole is null " +
               "       or c.PrjRole = '' " +
               "       or not exists (select 1 from tPrjRolePrjItem where PrjRole = c.PrjRole) " +
               "       or exists (select 1 from tPrjRolePrjItem where PrjRole = c.PrjRole and PrjItem = a.PrjItem) " +
               "   ) " +
               " ) ";
        
        list.add(uc.getCzybh());
		
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.AppDate desc ";
		}
		System.out.println("\n\n"+sql+"\n\n");
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Map<String,Object> prjConfirmAppExist(String custCode,String prjItem){
		String sql = " select * from tPrjConfirmApp where Status='1' and CustCode=? and PrjItem=? ";
		List<Map<String,Object>> lists = this.findBySql(sql, new Object[]{custCode,prjItem});
		if(lists!=null && lists.size()>0){
			return lists.get(0);
		}
		return null;
	}

	public Map<String,Object> checkPrjConfirmPassProg(String custCode,String prjItem){
		Map<String,Object> map = null;
		String sql = " SELECT b.NameChi,a.PrjItem "
					+" FROM tPrjProg a "
					+" LEFT JOIN tEmployee b ON a.ConfirmCZY=b.Number "
					+" WHERE a.CustCode=? AND a.PrjItem=? ";
		List<Map<String,Object>> lists = this.findBySql(sql, new Object[]{custCode,prjItem});
		if(lists != null && lists.size()>0){
			map = lists.get(0);
		}
		return map;
	}
	public List<Map<String,Object>> getConfirmPrjItem(String custCode){
		String sql  = " select b.CBM,b.NOTE "
					+ " from tPrjProg a "
					+ " left join tXTDM b on b.CBM = a.PrjItem and b.ID='PRJITEM' "
					+ " where a.CustCode=? and a.ConfirmCZY is null "
					+ " order by b.IBM asc ";
		return this.findBySql(sql, new Object[]{custCode});
	}
	public Map<String,Object> getConfirmInfo(String custCode,String prjItem){
		String sql = " SELECT COUNT(*) UnPassCount,MAX(a.No) No "
					+" 	FROM (  "
					+" 	SELECT * "
					+" 	FROM dbo.tPrjProgConfirm "
					+" 	WHERE IsPass='0' AND CustCode=? AND PrjItem=? "
					+" ) a ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,prjItem});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null ;
	}
	public Page<Map<String,Object>> getPrjConfirmByPage(Page<Map<String,Object>> page,String id,String address,String prjItem,String prjRole,String regionCode,String isLeaveProblem){
		List<Object> listObj = new ArrayList<Object>();
		String sql = " SELECT c.Code,c.Address,pca.PrjItem,tpi.Descr PrjItemDescr,pca.AppDate,c.RealAddress,pca.Remarks "
			+ " FROM dbo.tPrjConfirmApp pca "
			+ " LEFT JOIN dbo.tCustomer c ON pca.CustCode = c.Code " +
			" left join tbuilder b on b.Code = c.builderCode"
			+ " LEFT JOIN dbo.tPrjItem1 tpi ON tpi.Code = pca.PrjItem  "
			+ " left join tWorkSign ws on pca.WorkSignPK=ws.PK";
		if(StringUtils.isNotBlank(prjRole)){
			sql += " INNER JOIN dbo.tPrjRolePrjItem prpi ON prpi.PrjItem = pca.PrjItem AND prpi.PrjRole=? ";
			listObj.add(prjRole);
		}
		sql += " WHERE pca.Status='1' ";
		if (StringUtils.isNotBlank(prjItem)){
			sql += " AND pca.PrjItem = ? ";
			listObj.add(prjItem);
		}
		if (StringUtils.isNotBlank(address)){
			sql += " AND c.Address LIKE ? or c.RealAddress like ? ";
			listObj.add("%"+address+"%");
			listObj.add("%"+address+"%");
		}
		if(StringUtils.isNotBlank(regionCode)){
			sql+=" and b.regionCode = ? ";
			listObj.add(regionCode);
		}
		if("1".equals(isLeaveProblem)){
			sql+="and ws.IsLeaveProblem='1'";
		}
		sql += " order by pca.AppDate desc ";
		return this.findPageBySql(page,sql,listObj.toArray());
	}
	
	public Page<Map<String,Object>> getWorkerAppPrjConfirmByPage(Page<Map<String,Object>> page,String id,String address,String prjItem,String prjRole){
		List<Object> listObj = new ArrayList<Object>();
		String sql = " select  d.descr prjitemdescr,c.PrjItem,a.code,a.address, b.isPrjConfirm  from tcustomer a " +
				" left join tCustType b on b.code=a.custType " +
				" left join tprjConfirmApp c on c.custCode = a.code and c.workerCode is not null and c.custCode <>'' " +
				" left join  tPrjItem1 d on d.Code=c.PrjItem" +
				" where 1=1  and c.status = '1' " +
				" and exists (select 1 from tPrjConfirmApp pca where pca.custCode=a.code" +
				"				and pca.workerCode is not null and workerCode <> '' and pca.status='1' )";
		
		if(StringUtils.isNotBlank(id)){
			sql+=" and a.projectMan = ? ";
			listObj.add(id);
		}
		if(StringUtils.isNotBlank(address)){
			sql+=" and a.address like ? ";
			listObj.add("%"+address+"%");
		}
		
		if(StringUtils.isNotBlank(prjItem)){
			sql+=" and d.code = ? ";
			listObj.add(prjItem);
		}
		
		return this.findPageBySql(page,sql,listObj.toArray());
	}
	
	public boolean existCustPrjItemPass(String custCode, String prjItem){
		String sql = " select 1 from tPrjProgConfirm where custCode=? and prjItem=? and isPass='1' ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{custCode, prjItem});
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}

	public Map<String, Object> getSiteConfirmStatus(String custCode, String prjItem){
		String sql = " select * from tPrjProg where custCode = ? and PrjItem = ? and ConfirmDate is not null ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{custCode, prjItem});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public String getWorkerCode(String custCode, String prjItem){
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.*,b.address,t.Descr PrjItemDescr,isNUll(tw.Code,'') WorkerCode,tcw.ComeDate,tcw.ConstructDay,tw.NameChi WorkerName from tPrjProg a inner join tCustomer b on a.CustCode=b.code and a.CustCode=? "
				+"left join tPrjItem1 t on a.PrjItem = t.Code "
				+ " left join (select max(PK) PK,WorkType12 from tCustWorker where CustCode=? group by WorkType12) c on c.WorkType12 = t.WorkType12 "
				+ " left join tCustWorker tcw on tcw.PK = c.PK "
				+ " left join tWorker tw on tw.Code = tcw.WorkerCode " +
				 " where  1=1 ";
		list.add(custCode);
		list.add(custCode);
		if(StringUtils.isNotBlank(prjItem)){
			sql += " and a.PrjItem = ? ";
			list.add(prjItem);
		}
		sql += "  AND t.IsConfirm='1' AND t.Expired='F' "
			 + " ORDER BY a.PlanBegin,a.PrjItem ";
		List<Map<String, Object>> workerList = this.findBySql(sql, list.toArray());
		Map<String, Object> map=null;
		
		if(workerList != null && workerList.size() > 0){
			return workerList.get(0).get("WorkerCode").toString();
		}
		return null;
	}
	
	public String getAllowItemApp(String id){
		String sql = " select AllowItemApp from tWorker where Code= ?  ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{ id });
		if(list != null && list.size() > 0){
			return list.get(0).get("AllowItemApp").toString();
		}
		return null;
	}
	
	public List<Map<String, Object>> findNoSendYunPics(String type){
		List<Object> params = new ArrayList<Object>();
		String sql = " select top 100 PhotoName,PK from tPrjProgPhoto where LastUpdate >= dateadd(day,-30,getdate()) and (IsSendYun = '0' or IsSendYun is null or IsSendYun = '') ";
		if(StringUtils.isNotBlank(type)){
			sql += " and type=? ";
			params.add(type);
		}
		sql += " order by PK desc ";
		return this.findBySql(sql, params.toArray());
	}

	public void setPrjConfirmPhotoIsPushCust(){
		String sql = " update tPrjProgPhoto set IsPushCust='0' "
				   + " from tPrjProgPhoto tppp "
				   + " where tppp.Type='2' and tppp.PrjItem='16' "
				   + " and tppp.LastUpdate >= dateadd(minute,-10,getdate()) "
				   + " and PK = (select max(PK) from tPrjProgPhoto in_tppp where in_tppp.PrjItem='16' and in_tppp.RefNo=tppp.RefNo) "
				   + " and tppp.IsPushCust='1' "; 
		this.executeUpdateBySql(sql, new Object[]{});
	}

	public void setPrjProgConfirmIsPushCust(){
		String sql = " update tPrjProgConfirm set IsPushCust='0' "
				   + " from tPrjProgConfirm tppc "
				   + " where (tppc.LastUpdate >= dateadd(day,-1,getdate()) and ( "
				   + " 		select count(*) "
				   + " 		from tPrjProgPhoto in_tppp "
				   + " 		where in_tppp.RefNo = tppc.No and in_tppp.IsPushCust = '1' "
				   + " ) <= 2 ) and tppc.IsPushCust <> '0' "; 
		this.executeUpdateBySql(sql, new Object[]{});
	}
	
	public List<Map<String,Object>> getConfirmCustomFiledList(String prjItem, String prjProgConfirmNo){
		List<Object> params = new ArrayList<Object>();
		String sql="";
		if(StringUtils.isNotBlank(prjProgConfirmNo)) {
			sql +=" select a.Code, a.Descr, a.Type, a.Options, b.Value, b.Pk ConfirmCustomFiledValuePk "
				+ " from tConfirmCustomFiled a "
				+ " left join tConfirmCustomFiledValue b on a.Code = b.ConfirmCustomFiledCode "
				+ " where a.PrjItem = ? and (b.PrjProgConfirmNo = ? or isnull(b.PrjProgConfirmNo,'') = '')";
			params.add(prjItem);
			params.add(prjProgConfirmNo);
		}else {
			sql +=" select a.Code, a.Descr, a.Type, a.Options "
				+ " from tConfirmCustomFiled a "
				+ " where a.PrjItem = ? and a.Expired = 'F' ";
			params.add(prjItem);
		}
		
		return this.findBySql(sql, params.toArray());
	}
	
	public List<Map<String, Object>> getPrjProgPhoto(String no) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.PK,a.PhotoName,a.IsSendYun from tPrjProgPhoto a " 
				+" where 1=1 ";
		
		if(StringUtils.isNotBlank(no)){
			sql+=" and a.RefNo = ? ";
			list.add(no);
		}

		return this.findBySql(sql, list.toArray());
	}
}
