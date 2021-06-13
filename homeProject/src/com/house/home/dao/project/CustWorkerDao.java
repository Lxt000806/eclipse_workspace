package com.house.home.dao.project;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.project.Worker;
import com.sun.jmx.snmp.Timestamp;

@SuppressWarnings("serial")
@Repository
public class CustWorkerDao extends BaseDao{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustWorker custWorker,String czybh, UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select  * from ( select cwa.pk appPk,case when a.EndDate is null then '施工中'  else '完工' end endstatusdescr ,cwa.status AppStatus,cwa.CustWorkPk,a.pk,a.WorkerCode," +
					" b.NameChi WorkerCodeDescr,a.WorkType12,c.Descr WorkType12Descr,b.level,x4.Note leveldescr,"
				    + " d.Address,d.CustType,e.Desc1 CustTypeDescr,d.Layout,x1.NOTE LayOutDescr,d.Area,d.ConfirmBegin,a.CustCode,"
				    + " g.RegionCode,h.Descr RegionCodeDescr,a.ComeDate,a.EndDate,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,"
				    + " emp.NameChi ProjectManDescr,x2.Note isSignDescr,ppc.ConfirmCZY,tc.Zwxm ConfirmCzyDescr,ppc.ConfirmDate,ppc.prjLevel,x3.Note PrjLevelDescr,"
				    + " isnull(tt.ConfirmAmount,0) ConfirmAmount,isnull(mm.AppAmount,0) AppAmount, "
				    + " a.IsSysArrange,x5.NOTE IsSysArrangeDescr, "
				    + " a.ConstructDay ,x9.note ComeDelayDescr, x8.note EndDelayDescr, "
				    + " (case when a.ConstructDay = '0' then null  "
				    + "  else (DATEADD(DAY,a.ConstructDay-1,a.ComeDate)) end ) PlanEnd,a.Status,x6.NOTE StatusDescr, "
				    + " DATEDIFF(DAY,a.comedate ,a.enddate)+1 ActualDays  "	//实际工期 = 实际完工 - 进场时间 +1
				    + " ,MaxWs.MaxCrtDate,MaxWs.MinCrtDate,cwa.appDate,cwa.appComeDate ," +
				    " case when DATEDIFF(DAY,a.comedate,a.enddate ) + 1<=a.ConstructDay and a.endDate<>'' and a.endDate is not null then '是'" +
					" when DATEDIFF(DAY, a.comedate,a.enddate) + 1>a.ConstructDay and a.endDate<>'' and a.endDate is not null then '否' "
				    + " else '' end isIntimeComplete ,d2.desc1 projectDept ,ws.WorkSignDate,si.PrjPassDate, " +
				    " case when c.code='05' then ppc2.ConfirmDate else null end YQconfirmDate,p2.Code prjItem2,a.ConPlanEnd,x7.NOTE RelCustDescr, " +
				    " k.Descr SignPrjItem2, l.NOTE IsCompleteDescr " +
				    " from tCustWorker a "
				    + " left join tWorker b on a.WorkerCode=b.Code "
				    + " left join tWorkType12 c on a.WorkType12=c.Code "
				    + " left join tcustomer d on a.CustCode=d.Code "
				    + " left join tCusttype e on d.CustType=e.Code "
				    + " left join tXTDM x1 on d.Layout=x1.CBM and x1.ID='LAYOUT' "
				    + " left join tXTDM x2 on b.isSign=x2.CBM and x2.ID='WSIGNTYPE' " //改成签约类型 --update by zb
					+ " left join tPrjProg ppc on ppc.CustCode=a.CustCode and ppc.prjitem = c.PrjItem" 
				    + " left join tXTDM x3 on ppc.prjLevel=x3.CBM and x3.ID='PRJLEVEL' "
				    + " left join tczybm tc on ppc.ConfirmCZY=tc.czybh "
				    + " left join tBuilder g on d.BuilderCode=g.Code "
				    + " left join tRegion h on g.RegionCode=h.Code " 
				    + " left join tRegion2 h2 on h2.code=g.regionCode2 " 
				    + " left join tcustWorkerApp cwa on cwa.custWorkPK=a.pk "
				    + " left join tEmployee emp on d.ProjectMan=emp.Number " 
				    + " left  join tDepartment2 d2 on d2.code =emp.department2 "
				    + " left join tXTDM x4 on b.level=x4.cbm and x4.id='WORKERLEVEL' "
				    + " left join (select bb.WorkerCode,bb.CustCode,sum(isnull(bb.ConfirmAmount,0)) ConfirmAmount "
				    + " from tWorkCost aa left join tWorkCostDetail bb on aa.No=bb.No "
				    + " where aa.Status='2' and aa.PayCZY is not null "
				    + " group by bb.WorkerCode,bb.CustCode) tt on a.WorkerCode=tt.WorkerCode and a.CustCode=tt.CustCode "
				    + " left join (select a.WorkerCode,a.CustCode,sum(isnull(a.AppAmount,0)) AppAmount "
				    + " from tPreWorkCostDetail a where a.Status<>'9' and a.Status<>'1' and a.Status<>'8' "
				    + " group by a.WorkerCode,a.CustCode) mm on a.WorkerCode=mm.WorkerCode and a.CustCode=mm.CustCode "
				    + " left join tXTDM x5 on a.IsSysArrange=x5.CBM and x5.ID='YESNO'   "
				    + " left join tXTDM x6 on a.Status=x6.CBM and x6.ID='CUSTWKSTATUS' " 
				    + " left join tXtdm x9 on x9.Cbm = a.ComeDelayType and x9.id = 'CUSTWKDELAYTYPE' " 
				    + " left join tXtdm x8 on x8.CBM = a.EndDelayType and x8.id = 'CUSTWKDELAYTYPE' "
				    + " left join tXTDM x7 on d.RelCust=x7.CBM and x7.ID='RELCUST' "
				    + " left join tPrjItem2 p2 on c.prjItem=p2.Code"
				    + " left join (" 
				    + "   select b.PK,max(a.PK) MaxPk, max(a.CrtDate) MaxCrtDate, min(a.CrtDate) MinCrtDate " 
					+ "   from tWorkSign a " 
					+ "   left join tCustWorker b on b.PK=a.CustWkPk " 
					+ "   left join tWorkType12 d on d.Code=b.WorkType12 " 
					+ "   group by b.PK" 
					+ " ) MaxWs on a.PK = MaxWs.PK " 
					+ " left join (  select max(a.CrtDate)WorkSignDate,a.CustWkPk,b.worktype12 from tWorkSign a " 
					+ "				left join tPrjItem2 b on a.PrjItem2=b.Code " 
					+ "				where  a.IsComplete='1' "
                    + " 			group by a.CustWkPk,b.worktype12 " 
                    + "				having count(a.PK)=(select count(1) from tPrjItem2 in_pi2 where in_pi2.worktype12=b.worktype12 and in_pi2.expired='F') ) ws on ws.custWkPk=a.PK and ws.worktype12=a.worktype12  "
					+ " left join (select SignCZY,CustCode,in_b.WorkType12,max(CrtDate)PrjPassDate " 
					+ "     from tSignIn in_a left join tPrjItem1 in_b on in_a.PrjItem=in_b.Code where IsPass='1' " 
					+ "     group by SignCZY,CustCode,in_b.WorkType12 ) si on si.SignCZY=d.ProjectMan and si.CustCode=a.CustCode and a.WorkType12=si.WorkType12" 
					+ " left join tWorkSign j on MaxWs.MaxPk = j.PK " 
					+ " left join tPrjItem2 k on j.PrjItem2 = k.Code " 
					+ " left join tXTDM l on j.IsComplete = l.CBM and l.ID = 'YESNO' "    
					
					/*" where DATEDIFF(DAY,case when MinWs.minsigndate is null then a.comedate else MinWs.minsigndate end," +
					" case when ppc.ConfirmDate is null or ppc.ConfirmDate ='' then a.enddate else ppc.ConfirmDate end )<=a.ConstructDay "*/
					+ " left join tPrjProg ppc2 on ppc2.CustCode=a.CustCode and ppc2.prjitem =(case when c.code='05' then '19' else c.PrjItem end)" 
				    + " where 1=1  and " + SqlUtil.getCustRight(uc, "d", 0);
		
		if(StringUtils.isNotBlank(custWorker.getWorkType12())){
			sql += " and  a.WorkType12 in " + "('"+custWorker.getWorkType12().replace(",", "','" )+ "')";
		}
		if (StringUtils.isNotBlank(custWorker.getCustType())) {
			sql += " and d.CustType in " + "('"
					+ custWorker.getCustType().replaceAll(",", "','") + "')";
		}
		if(StringUtils.isNotBlank(custWorker.getConstructType())){
			sql+=" and d.ConstructType = ? ";
			list.add(custWorker.getConstructType());
		}
		if(StringUtils.isNotBlank(custWorker.getRegion())){
			sql += " and  h.code in " + "('"+custWorker.getRegion().replace(",", "','" )+ "')";

		}
		if(StringUtils.isNotBlank(custWorker.getIsWaterItemCtrl())){
			sql+=" and d.isWaterItemCtrl = ? ";
			list.add(custWorker.getIsWaterItemCtrl());
		}
		if(StringUtils.isNotBlank(custWorker.getAddress())){
			sql+=" and d.address like ? ";
			list.add("%"+custWorker.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(custWorker.getPrjRegionCode())){
			sql+=" and h2.prjRegionCode= ? ";
			list.add(custWorker.getPrjRegionCode());
		}
		if(StringUtils.isNotBlank(custWorker.getM_umState())){
			sql+=" and a.status= ? ";
			list.add(custWorker.getM_umState());
		}
		if(StringUtils.isNotBlank(custWorker.getDepartment2())){
			sql += " and  emp.department2 in " + "('"+custWorker.getDepartment2().replace(",", "','" )+ "')";
		}
		if (custWorker.getDateFrom() != null) {
			sql += " and a.comeDate>= ? ";
			list.add(custWorker.getDateFrom());
		}
		if (custWorker.getDateTo() != null) {
			sql += " and a.comeDate< dateadd(d,1,?) ";
			list.add(custWorker.getDateTo());
		}
		if(StringUtils.isNotBlank(custWorker.getProjectMan())){
			sql+=" and emp.number = ? ";
			list.add(custWorker.getProjectMan());
		}
		if(StringUtils.isNotBlank(custWorker.getWorkerCode())){
			sql+=" and b.code = ? ";
			list.add(custWorker.getWorkerCode());
		}
		if(StringUtils.isNotBlank(custWorker.getWorkType12Dept())){
			sql+=" and b.workType12Dept = ? ";
			list.add(custWorker.getWorkType12Dept());
		}
		if(StringUtils.isNotBlank(custWorker.getStatus())){
			if("0".equals(custWorker.getStatus())){
				sql+=" and a.EndDate is null ";
			}else if("1".equals(custWorker.getStatus())){
				sql+=" and a.EndDate is not null  ";
			}
		}
		if (custWorker.getEndDateFrom() != null) {
			sql += " and a.EndDate>= ? ";
			list.add(custWorker.getEndDateFrom());
		}
		if (custWorker.getEndDateTo() != null) {
			sql += " and a.EndDate< dateadd(d,1,?) ";
			list.add(custWorker.getEndDateTo());
		}
		if (StringUtils.isNotBlank(custWorker.getWorkerClassify())) {
			sql += " and b.WorkerClassify in ('"+custWorker.getWorkerClassify().replace(",", "','")+"') ";
		}
		if(StringUtils.isNotBlank(custWorker.getSearchType())){
			if("1".equals(custWorker.getSearchType())){
				
			} else if("2".equals(custWorker.getSearchType())){//超期在建
				sql+=" and convert(nvarchar(10), a.planEnd,120) < convert(nvarchar(10), getdate(),120) ";
			} else if("3".equals(custWorker.getSearchType())){//首日未进场
				sql+=" and convert(nvarchar(10), a.ComeDate,120) = convert(nvarchar(10), getdate(),120) " +
						" and not exists (select 1 from tWorkSign in_a " +
						" 				where in_a.CustWkPk = a.pk " +
						" 				and convert(nvarchar(10), in_a.CrtDate,120) = convert(nvarchar(10), getdate(),120)" +
						" )";
			} else if("4".equals(custWorker.getSearchType())){
				sql+=" and a.ComeDate < convert(nvarchar(10), dateadd(dd, 1, getdate()), 120) " +
						" and not exists (select 1 from tWorkSign in_a " +
						" 				where in_a.CustWkPk = a.pk " +
						" 				and convert(nvarchar(10), in_a.CrtDate,120) = convert(nvarchar(10), getdate(),120)" +
						" ) and a.endDate is null";
			}
		}
		sql+=" and exists (select 1 from tCZYBM in_a "
                   + " left join tPrjRole in_b on in_a.PrjRole=in_b.Code "
                   + " left join tPrjRoleWorkType12 in_c on in_b.Code=in_c.PrjRole "
                   + " where in_a.CZYBH= ? and (in_b.Code is null or in_c.WorkType12=a.WorkType12) )";
		list.add(czybh);
		sql+=" ) a where 1=1 ";
		if(custWorker.getPlanEndDateFrom()!=null){
			sql+=" and a.PlanEnd >= ? ";
			list.add(custWorker.getPlanEndDateFrom());
		}
		if(custWorker.getPlanEndDateTo()!=null){ 
			sql+=" and a.planEnd < ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(custWorker.getPlanEndDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.pk  desc ";
		}
		if(StringUtils.isNotBlank(custWorker.getIsDoExcel())){//导出excel时，格式化时间，不要时分秒
			Page<Map<String, Object>> list2=new Page<Map<String,Object>>();
			java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
			java.text.SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd ", Locale.getDefault());
			list2= findPageBySql(page, sql, list.toArray());
			for(int i=0 ;i<list2.getResult().size();i++){
				if(list2.getResult().get(i).get("confirmbegin")!=null){
					list2.getResult().get(i).put("confirmbegin", sdf.format(list2.getResult().get(i).get("confirmbegin")));
				}
				if(list2.getResult().get(i).get("appcomedate")!=null){
					list2.getResult().get(i).put("appcomedate", sdf.format(list2.getResult().get(i).get("appcomedate")));
				}
				if(list2.getResult().get(i).get("planend")!=null){
					list2.getResult().get(i).put("planend", sdf.format(list2.getResult().get(i).get("planend")));
				}
				if(list2.getResult().get(i).get("comedate")!=null){
					list2.getResult().get(i).put("comedate", sdf.format(list2.getResult().get(i).get("comedate")));
				}

				if(list2.getResult().get(i).get("appdate")!=null){
					list2.getResult().get(i).put("appdate", sdfTime.format(list2.getResult().get(i).get("appdate")));
				}
				if(list2.getResult().get(i).get("enedate")!=null){
					list2.getResult().get(i).put("enedate", sdfTime.format(list2.getResult().get(i).get("enedate")));
				}
				if(list2.getResult().get(i).get("mincrtdate")!=null){
					list2.getResult().get(i).put("mincrtdate", sdfTime.format(list2.getResult().get(i).get("mincrtdate")));
				}
				if(list2.getResult().get(i).get("maxcrtdate")!=null){
					list2.getResult().get(i).put("maxcrtdate", sdfTime.format(list2.getResult().get(i).get("maxcrtdate")));
				}
				if(list2.getResult().get(i).get("lastupdate")!=null){
					list2.getResult().get(i).put("lastupdate", sdfTime.format(list2.getResult().get(i).get("lastupdate")));
				}
				if(list2.getResult().get(i).get("enddate")!=null){
					list2.getResult().get(i).put("enddate", sdfTime.format(list2.getResult().get(i).get("enddate")));
				}
				if(list2.getResult().get(i).get("confirmdate")!=null){
					list2.getResult().get(i).put("confirmdate", sdfTime.format(list2.getResult().get(i).get("confirmdate")));
				}
			}
			return list2;
		}
		return this.findPageBySql(page, sql, list.toArray());
	}	
	
	public Page<Map<String,Object>> findWorkerDetailPageBySql(Page<Map<String,Object>> page, CustWorker custWorker) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (select a.WorkerCode,a.WorkerCodeDescr,a.WorkType12Descr,a.leveldescr,a.IsSignDescr,sum(a.zjts) zjts," +
				"sum(a.monthts) monthts,sum(a.yearts) yearts "
		    + "from (select b.Code WorkerCode,b.NameChi WorkerCodeDescr,b.WorkType12,c.Descr WorkType12Descr,b.level,x4.Note leveldescr,"
		    + "d.Address,d.Area,d.BeginDate,d.EndDate,a.CustCode,xt1.Note IsSignDescr,"
		    + "g.RegionCode,h.Descr RegionCodeDescr,d.status,"
		    + "case when a.EndDate is null  and d.Status='4' then 1 else 0 end zjts,"
		    + "case when a.EndDate is not null and convert(varchar(6),a.EndDate,112)=convert(varchar(6),getdate(),112) then 1 else 0 end monthts,"
		    + "case when a.EndDate is not null and convert(varchar(4),a.EndDate,112)=convert(varchar(4),getdate(),112) then 1 else 0 end yearts "
		    + "from tWorker b "
		    + "left join tCustWorker a on a.WorkerCode=b.Code "
		    + "left join tWorkType12 c on b.WorkType12=c.Code "
		    + "left join tcustomer d on a.CustCode=d.Code "
		    + "left join tBuilder g on d.BuilderCode=g.Code "
		    + "left join tRegion h on g.RegionCode=h.Code "
		    + "left join tPrjProg f on c.PrjItem=f.PrjItem and a.CustCode=f.CustCode "
		    + "left join txtdm xt1 on xt1.cbm=b.IsSign and xt1.ID='YESNO'"
		    + "left join tXTDM x4 on b.level=x4.cbm and x4.id='WORKERLEVEL'  ";
						
		return this.findPageBySql(page, sql, list.toArray());
	}	
	
	public Page<Map<String,Object>> findPageByCustCode(Page<Map<String,Object>> page, String custCode) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select b.descr workType12Descr,c.nameChi workerDescr,a.comeDate,a.EndDate," +
				" isnull(dateDiff(d,a.ComeDate,a.EndDate),'') 工期,a.constructDay," +
				" MaxWs.MaxCrtDate,MinWs.MinCrtDate from tCustWorker a" +
				" left join tworktype12 b on b.Code=a.WorkType12 " +
				" left join tworker c on c.Code=a.WorkerCode "+
				
				" left join (select  a.CustCode,max(a.CrtDate)MaxCrtDate,d.Code from tWorkSign a " +
				"  left join tCustWorker b on b.PK=a.CustWkPk" +
				" left join tWorkType12 d on d.Code=b.WorkType12 " +
				" group by a.CustCode,d.Code) MaxWs on a.custCode=MaxWs.custCode and a.workType12 = maxWs.code" +
				
				" left join (select  a.CustCode,min(a.CrtDate)MinCrtDate,d.Code from tWorkSign a " +
				"  left join tCustWorker b on b.PK=a.CustWkPk" +
				" left join tWorkType12 d on d.Code=b.WorkType12 " +
				" group by a.CustCode,d.Code) MinWs on a.custCode=MinWs.custCode and a.workType12 = minWs.code " +
				" where 1=1 " ;
		
		if(StringUtils.isNotBlank(custCode)){
			sql+=" and a.custCode=? ";
			list.add(custCode);
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findViewSignPageBySql(Page<Map<String,Object>> page, CustWorker custWorker,String czybh) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( " +
				"	select case when b.custScore is null or custScore = '' then '' else cast(b.custScore as varchar(1))+'星' end custScore ," +
				"	b.custRemarks,b.no,isnull((select count(1) from tWorkSignPic where no = b.no group by no ),0) photonum," +
				" c.code custCode,c.Address,d.descr workType12Descr,e.NameChi workerDescr ,b.CrtDate," +
				" f.Descr prjitem2descr, b.isComplete, case when b.isComplete='1' then '是' else '否' end isEnd, " +
				" b.PK WorkSignPk " +
				" from tCustWorker a " +
				" left join tWorkSign b on b.CustWkPk =a.PK" +
				" left join tPrjItem2 f on f.Code=b.PrjItem2" +
				" left join tcustomer c on c.Code=a.CustCode" +
				" left join tworktype12 d on d.code=a.WorkType12 " +
				" left join tworker e on e.Code=a.WorkerCode " +
				" where 1=1 and b.crtDate is not null and b.crtDate <>''" ;
		if(StringUtils.isNotBlank(custWorker.getAddress())){
			sql+=" and c.address like ?  ";
			list.add("%"+custWorker.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(custWorker.getWorkType12())){
			sql+=" and a.workType12 = ? ";
			list.add(custWorker.getWorkType12());
		}
		if(StringUtils.isNotBlank(custWorker.getWorkerCode()) ){
			sql+=" and a.workerCode = ? ";
			list.add(custWorker.getWorkerCode());
		}
		if(custWorker.getPk() != null && custWorker.getPk() > 0) {
			sql += " and a.PK = ? ";
			list.add(custWorker.getPk());
		}
		if(custWorker.getDateFrom()!=null){
			sql+=" and  b.CrtDate >= ?";
			list.add( custWorker.getDateFrom());
		}
		if(custWorker.getDateTo()!=null){
			sql+=" and  b.CrtDate < dateAdd(d,1,?)";
			list.add( custWorker.getDateTo());
		}
		if(StringUtils.isNotBlank(custWorker.getCustCode())){
			sql+=" and a.CustCode = ?";
			list.add(custWorker.getCustCode());
		}
		if (StringUtils.isNotBlank(custWorker.getWorkerClassify())) {
			sql += " and e.WorkerClassify in ('"+custWorker.getWorkerClassify().replace(",", "','")+"') ";
		}
		if(StringUtils.isNotBlank(custWorker.getWorkType12Dept())){
			sql+=" and e.workType12Dept in ('"+custWorker.getWorkType12Dept().replace(",", "','")+"')  ";
		}
		sql+=" and exists (select 1 from tCZYBM in_a "
                   + " left join tPrjRole in_b on in_a.PrjRole=in_b.Code "
                   + " left join tPrjRoleWorkType12 in_c on in_b.Code=in_c.PrjRole "
                   + " where in_a.CZYBH= ? and (in_b.Code is null or in_c.WorkType12=a.WorkType12) )";
		list.add(czybh);
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.CrtDate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	//获取最大PK
	public Map<String,Object> getPk(String actionLog ){
		String sql=" select max(PK) pk from tCustWorker where 1=1 and actionLog =? ";
		
		List<Map<String,Object>> list=this.findBySql(sql,new Object[]{actionLog});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public CustWorker getByCode(String custCode,String workType12,String workerCode) {
		String hql = "from CustWorker where custCode=? and workType12=? and workerCode=?";
		List<CustWorker> list = this.find(hql, new Object[] {custCode,workType12,workerCode});
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public boolean getIsExistsWorkerArr(CustWorker custWorker){
		if(custWorker.getPk() == null || custWorker.getPk() <= 0){
			custWorker.setPk(0);
		}
		String sql=" select 1  from tCustWorker  where WorkType12 = ? and WorkerCode= ? and custcode= ? and pk <> ? ";
		
		List<Map<String,Object>> list=this.findBySql(sql,new Object[]{custWorker.getWorkType12(),custWorker.getWorkerCode(),custWorker.getCustCode(),custWorker.getPk()});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	public void doDelWorkerCode(String custWorkPk ){
		String sql=" delete from tCustWorker where pk = ? " ;
		this.executeUpdateBySql(sql, new Object[]{custWorkPk});
	}
	
	public Page<Map<String,Object>> getWorkSignPicBySql(Page<Map<String,Object>> page, CustWorker custWorker) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select b.*,case when b.isPushCust ='1' then '可见' else '不可见' end ispushcustdescr from tworkSign a " +
				" left join tWorkSignPic b on b.no =a.no " +
				" where 1=1 ";

    	if (StringUtils.isNotBlank(custWorker.getWorkSignNo())) {
			sql += " and a.no = ? ";
			list.add(custWorker.getWorkSignNo());
		}else {
			sql+=" and a.no ='-1'";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by b.lastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public void updateIsPushCust(CustWorker custWorker) {
		float i = 0l;
		String sql = " update tWorkSignPic set  IsPushCust = ?,lastUpdate = getDate() where pk = ? ";
		i = this.executeUpdateBySql(sql, new Object[]{custWorker.getIsPushCust(), custWorker.getPk()});
		if(i > 0){
			sql = " update tWorkSign set IsPushCust =  " +
					" case when (select count(1) from tWorkSignPic " +
					" where no = a.no and IsPushCust ='1')>0 then '1' else '0' end " +
					" from tWorkSignPic a where a.no  = (select no from tWorkSignPic where pk = ? )";
					i = this.executeUpdateBySql(sql, new Object[]{custWorker.getPk()});
					System.out.println(i);
		}
	}
	
	public void updateIsPushCustAll(CustWorker custWorker) {
		String sql = " update tWorkSignPic set  IsPushCust = ?,lastUpdate = getDate()" +
				" where No = ? ";
		this.executeUpdateBySql(sql, new Object[]{custWorker.getIsPushCust(),custWorker.getWorkSignNo()});
		
		String sql_con = " update tWorkSign set isPushCust = ?  where no = ? ";
		this.executeUpdateBySql(sql_con, new Object[]{custWorker.getIsPushCust(),custWorker.getWorkSignNo()});
	}
	
	public Page<Map<String,Object>> findCodePageBySql(Page<Map<String,Object>> page, CustWorker custWorker) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.PK,a.WorkerCode,c.NameChi WorkerName,a.CustCode,b.Address,a.WorkType12,d.Descr WorkType12Descr from tCustWorker a "
				+" left join tCustomer b on a.CustCode=b.Code "
				+" left join tWorker c on a.WorkerCode=c.Code "
				+" left join tWorkType12 d on a.WorkType12=d.Code "
				+" where 1=1 ";
		if(StringUtils.isNotBlank(custWorker.getAddress()) ){
			sql+=" and b.Address like ? ";
			list.add("%"+custWorker.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(custWorker.getWorkType12()) ){
			sql+=" and a.WorkType12 = ? ";
			list.add(custWorker.getWorkType12());
		}
		if(StringUtils.isNotBlank(custWorker.getWorkerCode()) ){
			sql+=" and a.WorkerCode = ? ";
			list.add(custWorker.getWorkerCode());
		}
		return this.findPageBySql(page, sql,list.toArray());
	}
	
	public List<Map<String, Object>> getWorkType12Dept(String workType12){
		String sql=" select Code,Code+' '+Descr Descr from tWorkType12Dept where expired='F' ";
		if(StringUtils.isNotBlank(workType12)){
			sql+="and WorkType12 in "+"('"+workType12.replace(",", "','")+"')";
		}
		List<Map<String,Object>> list=this.findBySql(sql,new Object[]{});
		if (list!=null && list.size()>0){
			return list;
		}
		return null;
	}
	
	
	public Page<Map<String,Object>> getWorkType12List(Page<Map<String,Object>> page,String czybh){
		String sql=" select a.Code WorkType12, a.Descr WorkType12Descr from tWorkType12 a where ((select PrjRole from tCZYBM where CZYBH=?) is null or "
				+" ( select PrjRole from tCZYBM where CZYBH=?) ='') or "
				+" Code in( select WorkType12 From tprjroleworktype12 pr where pr.prjrole = (select PrjRole from tCZYBM where CZYBH=?) or pr.prjrole = '')";
		return this.findPageBySql(page,sql,new Object[]{czybh,czybh,czybh});
	}
	
	public Page<Map<String,Object>> getPrjRegionList(Page<Map<String,Object>> page){
		String sql="select Code PrjRegionCode, Descr PrjRegionDescr from tPrjRegion where Expired='F'";
		return this.findPageBySql(page,sql,new Object[]{});
	}
	
	public Page<Map<String,Object>> getWorkerList(Page<Map<String,Object>> page,Worker worker){
		List<Object> list = new ArrayList<Object>();
		
		String sql="select a.Code WorkerCode,a.NameChi WorkerName,b.Descr PrjRegionDescr,c.Descr WorkType12Descr,a.Num,a.Remarks,(select count(*) from tcustworker cw left join tcustomer c on c.code=cw.custcode  where  workercode= a.Code "
				+" and cw.endDate is null and c.status='4') onDo from  tWorker a "
				+" left join tPrjRegion b on a.PrjRegionCode=b.Code "
				+" left join tWorkType12 c on a.WorkType12=c.Code "
				+" where a.Expired='F' ";
		if(StringUtils.isNotBlank(worker.getNameChi())){
			sql+=" and a.NameChi like ? ";
			list.add("%"+worker.getNameChi()+"%");
		}
		if(StringUtils.isNotBlank(worker.getIsSign())){
			sql+=" and a.IsSign = ? ";
			list.add(worker.getIsSign());
		}
		if(StringUtils.isNotBlank(worker.getWorkType12())){
			sql+=" and a.WorkType12 = ? ";
			list.add(worker.getWorkType12());
		}
		if(StringUtils.isNotBlank(worker.getWorkerClassify())){
			sql+=" and a.WorkerClassify = ? ";
			list.add(worker.getWorkerClassify());
		}
		if(StringUtils.isNotBlank(worker.getPrjRegionCode())){
			sql+=" and a.PrjRegionCode = ? ";
			list.add(worker.getPrjRegionCode());
		}
		sql+=" and exists (select 1 from tCZYBM in_a "
                + " left join tPrjRole in_b on in_a.PrjRole=in_b.Code "
                + " left join tPrjRoleWorkType12 in_c on in_b.Code=in_c.PrjRole "
                + " where in_a.CZYBH= ? and (in_b.Code is null or in_c.WorkType12=a.WorkType12) )";
		list.add(worker.getCzybh());
		sql+="order by a.LastUpdate desc";
		return this.findPageBySql(page,sql,list.toArray());
	}
	
	public Page<Map<String,Object>> getCustWorkerList(Page<Map<String,Object>> page,CustWorker custWorker){
		List<Object> list = new ArrayList<Object>();
		
		String sql="select a.Pk CustWkPk,b.Address,c.Descr WorkType12Descr,d.NameChi WorkerName,a.ComeDate,a.PlanEnd,e.Pk from tCustWorker a "
				+" left join tCustomer b on a.CustCode=b.Code "
				+" left join tWorkType12 c on a.WorkType12=c.Code "
				+" left join tWorker d on a.WorkerCode=d.Code "
				+" left join tCustWorkerApp e on a.Pk=e.CustWorkPk"
				+" where  a.Expired='F' and a.EndDate is null ";
		if(StringUtils.isNotBlank(custWorker.getAddress())){
			sql+=" and b.Address like ? ";
			list.add("%"+custWorker.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(custWorker.getWorkType12())){
			sql+=" and a.WorkType12 = ? ";
			list.add(custWorker.getWorkType12());
		}
		if(StringUtils.isNotBlank(custWorker.getWorkerCode())){
			sql+=" and a.WorkerCode = ? ";
			list.add(custWorker.getWorkerCode());
		}
		if(StringUtils.isNotBlank(custWorker.getWorkerName())){
			sql+=" and d.nameChi like ? ";
			list.add("%"+custWorker.getWorkerName()+"%");
		}
		sql+=" and exists (select 1 from tCZYBM in_a "
                + " left join tPrjRole in_b on in_a.PrjRole=in_b.Code "
                + " left join tPrjRoleWorkType12 in_c on in_b.Code=in_c.PrjRole "
                + " where in_a.CZYBH= ? and (in_b.Code is null or in_c.WorkType12=a.WorkType12) )";
		list.add(custWorker.getCzybh());
		sql+="order by a.LastUpdate desc";
		return this.findPageBySql(page,sql,list.toArray());
	}
	
	public Page<Map<String,Object>> getCustWorkerAppList(Page<Map<String,Object>> page,CustWorker custWorker){
		List<Object> list = new ArrayList<Object>();
		
		String sql="select  a.pk,a.CustCode,b.Address,a.WorkType12,c.Descr WorkType12Descr,a.AppDate,a.AppComeDate,b.ProjectMan,d.NameChi ProjectManName,a.Remarks from tCustWorkerApp a "
				+" left join tCustomer b on a.CustCode=b.Code "
				+" left join tWorkType12 c on a.WorkType12=c.Code "
				+" left join tEmployee d on b.ProjectMan=d.Number"
				+" where a.Expired='F' and a.Status='1' ";
		if(StringUtils.isNotBlank(custWorker.getAddress())){
			sql+=" and b.Address like ? ";
			list.add("%"+custWorker.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(custWorker.getWorkType12())){
			sql+=" and a.WorkType12 = ? ";
			list.add(custWorker.getWorkType12());
		}
		if(StringUtils.isNotBlank(custWorker.getDepartment2())){
			sql+=" and a.CustCode in(select in_a.Code from tCustomer in_a "
			+" left join tCustStakeholder in_b on in_a.Code=in_b.CustCode "
			+" left join tEmployee in_c on in_b.EmpCode=in_c.Number "
			+ "where in_b.Role='11' and in_c.Department2=? )";
			list.add(custWorker.getDepartment2());
		}
		sql+=" and exists (select 1 from tCZYBM in_a "
                + " left join tPrjRole in_b on in_a.PrjRole=in_b.Code "
                + " left join tPrjRoleWorkType12 in_c on in_b.Code=in_c.PrjRole "
                + " where in_a.CZYBH= ? and (in_b.Code is null or in_c.WorkType12=a.WorkType12) )";
		list.add(custWorker.getCzybh());
		sql+="order by a.LastUpdate desc";
		return this.findPageBySql(page,sql,list.toArray());
	}
	
	public Page<Map<String,Object>> getAllCustomerList(Page<Map<String,Object>> page,CustWorker custWorker){
		List<Object> list = new ArrayList<Object>();
		
		String sql="select a.Code CustCode,a.Address,a.ProjectMan,c.NameChi ProjectManName from tCustomer a "
				+" left join tCusttype b on a.CustType=b.Code"
				+" left join tEmployee c on a.ProjectMan=c.Number"
				+" where a.Status='4' and a.Expired='F'  ";
		if(StringUtils.isNotBlank(custWorker.getAddress())){
			sql+=" and a.Address like ? ";
			list.add("%"+custWorker.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(custWorker.getIsAddAllInfo())&&"1".equals(custWorker.getIsAddAllInfo())){
			sql+=" and b.IsAddAllInfo = ? ";
			list.add(custWorker.getIsAddAllInfo());
		}
		sql+="order by a.LastUpdate desc";
		return this.findPageBySql(page,sql,list.toArray());
	}
	
	public Page<Map<String,Object>> getDepartment2List(Page<Map<String,Object>> page){
		String sql="select Code Department2,Desc1 Department2Descr from tDepartment2 where DepType='6' and Expired='F'";
		return this.findPageBySql(page,sql,new Object[]{});
	}
	/**
	 * 查询工种施工进度（工程进度拖延分析用）
	 * @author	created by zb
	 * @date	2019-11-8--下午6:11:25
	 * @param page
	 * @param custCode
	 * @return
	 */
	public Page<Map<String,Object>> findWorkDaysPageByCustCode(Page<Map<String,Object>> page, String custCode) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select b.descr workType12Descr,c.nameChi workerDescr,a.comeDate,a.EndDate,d.ConfirmBegin," +
				" isnull(dateDiff(d,a.ComeDate,isnull(a.EndDate,getdate())),'')+1  工期,a.constructDay," +
				" isnull(dateDiff(d,a.ComeDate,isnull(a.EndDate,getdate())),'')+1-a.constructDay delaydays,"+
				" isnull(dateDiff(d,d.ConfirmBegin,a.ComeDate),0)+1 BeginDay, " +
				" case when a.EndDate is not null then isnull(dateDiff(d,d.ConfirmBegin,a.EndDate),0)+1 else null end EndDay,"+
				" isnull(tst.signTimes,0) signTimes,e.AppComeDate,	"+
				" MaxWs.MaxCrtDate,MinWs.MinCrtDate,si.PrjPassDate, wc.workCompleteDate,cpd.ComfirmPassDate from tCustWorker a" +
				" left join tworktype12 b on b.Code=a.WorkType12 " +
				" left join tworker c on c.Code=a.WorkerCode "+
				
				" left join (select  a.CustCode,max(a.CrtDate)MaxCrtDate,d.Code from tWorkSign a " +
				"  left join tCustWorker b on b.PK=a.CustWkPk" +
				" left join tWorkType12 d on d.Code=b.WorkType12 " +
				" group by a.CustCode,d.Code) MaxWs on a.custCode=MaxWs.custCode and a.workType12 = maxWs.code" +
				
				" left join (select  a.CustCode,min(a.CrtDate)MinCrtDate,d.Code from tWorkSign a " +
				"  left join tCustWorker b on b.PK=a.CustWkPk" +
				" left join tWorkType12 d on d.Code=b.WorkType12 " +
				" group by a.CustCode,d.Code) MinWs on a.custCode=MinWs.custCode and a.workType12 = minWs.code " +
				
				" left join tCustomer d on a.CustCode=d.Code"+
				" left join tCustWorkerApp e on e.custWorkPk=a.pk"+
				
				" left join ( "+
				"	select count(*) signTimes,in_b.CustWkPk from "+
				"		(select CustWkPk from tWorkSign in_a group by cast(CrtDate AS DATE),CustWkPk"+
				"	)in_b group by in_b.CustWkPk "+
				" )tst on tst.CustWkPk=a.pk " +
				//初检时间
				" left join (" +
				"	select CustCode,in_b.WorkType12,max(CrtDate)PrjPassDate from tSignIn in_a " +
				"	left join tPrjItem1 in_b on in_a.PrjItem=in_b.Code " +
				"   where IsPass='1' "+
				" 	group by CustCode,in_b.WorkType12 " +
				" ) si on si.CustCode = a.CustCode and si.WorkType12=a.Worktype12 "+
				//工人完工时间
				" left join (" +
				" select  max(a.CrtDate) workCompleteDate,a.CustWkPk,b.worktype12 from tWorkSign a " +
				" left join tPrjItem2 b on a.PrjItem2=b.Code " +
				" where a.IsComplete = '1' " +
				" group by a.CustWkPk,b.worktype12 " +
				" having count(a.PK)=(select count(1) from tPrjItem2 in_pi2 where in_pi2.worktype12=b.worktype12 and in_pi2.expired='F') "+
				") wc on a.PK = wc.CustWkPk and wc.worktype12=a.Worktype12 " +
				// 验收时间
				" left join ( select CustCode,in_b.WorkType12,max(in_a.LastUpdate)ComfirmPassDate from tPrjProgConfirm in_a " +
				" left join tPrjItem1 in_b on in_a.PrjItem=in_b.Code " +
				" where IsPass='1' " +
				" group by CustCode,in_b.WorkType12 " +
				" ) cpd on cpd.CustCode = a.CustCode and cpd.WorkType12=a.Worktype12 " +
				
				" where 1=1 " ;
		if(StringUtils.isNotBlank(custCode)){
			sql+=" and a.custCode=? ";
			list.add(custCode);
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 根据custCode和项目查询更新开始时间
	 * @author	created by zb
	 * @date	2019-11-26--下午5:37:19
	 * @param custCode
	 * @param beginPrjItem
	 */
	public void getPrjProg(String custCode, String beginPrjItem, Date comeDate) {
		try {
			String sql = " update tPrjProg set BeginDate=? "
						+" from tPrjProg a "
						+" where a.CustCode=? and a.PrjItem=? and a.BeginDate is null ";
			this.executeUpdateBySql(sql, new Object[]{comeDate, custCode, beginPrjItem});
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public Page<Map<String,Object>> findWaterAftInsItemAppPageBySql(Page<Map<String,Object>> page, CustWorker custWorker) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select e.Descr ItemType2Descr,b.Descr,a.Qty,c.Descr UomDescr,a.Date,f.NameChi workerDescr from tWaterAftInsItemApp a "
				 	+" left join tWaterAftInsItemRuleDetail b on a.WaterAftInsItemPk=b.PK "
				 	+" left join tUOM c on b.Uom=c.Code "
				 	+" left join tWaterAftInsItemRule d on b.No=d.No "
				 	+" left join tItemType2 e on e.Code=d.ItemType2 " +
				 	" left join tWorker f on f.code = a.WorkerCode "
				 	+" where a.CustCode = ? ";
		list.add(custWorker.getCustCode());

		if(StringUtils.isNotBlank(custWorker.getWorkerClassify())){
			sql+=" and workerCode = ? ";
			list.add(custWorker.getWorkerCode());
		}
		if(StringUtils.isNotBlank(custWorker.getItemType12())){
			sql += " and e.itemType12 = ? ";
			list.add(custWorker.getItemType12());
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}	
	
	public Page<Map<String,Object>> goLogJqGrid(Page<Map<String,Object>> page, CustWorker custWorker) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select b.NameChi WorkerName,c.NOTE OperTypeDescr,a.ComeDate,a.Expired,a.LastUpdate,d.NameChi LastUpdatedBy,a.ActionLog "
					+"from tCustWorkerlog a  "
					+"left join tWorker b on a.WorkerCode=b.Code "
					+"left join tXTDM c on a.OperType=c.CBM and c.ID='CUSTWKLOGTYPE'"
					+"left join tEmployee d on a.LastUpdatedBy = d.Number "
					+"where a.CustCode=? and a.WorkType12=? ";
		list.add(custWorker.getCustCode());
		list.add(custWorker.getWorkType12());
		return this.findPageBySql(page, sql, list.toArray());
	}
}
