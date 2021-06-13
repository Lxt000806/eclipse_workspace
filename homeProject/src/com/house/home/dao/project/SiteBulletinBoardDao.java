package com.house.home.dao.project;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Repository
public class SiteBulletinBoardDao extends BaseDao {
	
	public Map<String,Object> getSiteBulletinBoardCountInfo() {
		String sql= " select a.*, isnull(a.CheckYesterdayNum, 0) + isnull(a.ConfirmYesterdayNum, 0) ConfirmOrCheckYesterdayNum from ( "
					+" select "
					+" (select count(a.Code) from tCustomer a "
					+" 	left join tCusttype b on a.CustType = b.Code "
					+" 	where a.Status = '4' and a.ConfirmBegin is not null and a.ConstructStatus <> '7' and b.IsPartDecorate in ('0','1') "
					+" ) BuildingSitesNum, "
					+" (select count(a.Code) from tCustomer a "
					+" 	where exists ( select 1 from tWorkSign where CustCode = a.Code and DateDiff(dd,CrtDate,getdate())=1) "
					+" ) ConstructionYesterdayNum,  "
					+" (select count(a.Code) from tCustomer a "
					+"  left join tCusttype b on a.CustType = b.Code "
					+"  where a.Status = '4' and a.ConfirmBegin is null and a.ConstructStatus <> '7' and b.IsPartDecorate in ('0','1') "
					+" ) WaitBeginSitesNum, ( " 
					+"		select count(No) from tPrjProgCheck where datediff(dd,Date,getdate()) = 1 and ErrPosi = '0' "
					+ ") CheckYesterdayNum, ( "
					+ "		select count(No) from tPrjProgConfirm where datediff(dd,Date,getdate()) = 1 and ErrPosi = '0' "
					+ ") ConfirmYesterdayNum, "
					+" (select count(No) from  tPrjProblem where Status = '2' or Status ='3') WaitDealPrjProblemNum, "
					+" ( select count(a.PK) from  tCustProblem a " 
					+" 	left join tCustComplaint b on a.No = b.No "
					+" 	where a.Status in('0','2')  and b.Status in ('0','1') and PromSource = '2' and a.DealCZY is not null and a.DealCZY <> '' " 
					+" ) WaitDealCustProblemNum ) a";
		
		List<Map<String,Object>> list=this.findBySql(sql,new Object[]{});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String,Object>> getBuildingList(Page<Map<String,Object>> page) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from ( "
					+" 	select case when c.Desc1 is null or c.Desc1 = '' then f.desc1 else c.Desc1 end Department2Descr,count(a.Code) BuildingNum, "
					+" 	sum(case when datediff(dd,d.lastCrtDate,getdate())>7 then 1 else 0 end) StopMoreThanSevenDaysNum "
					+" 	from tCustomer a "
					+" 	left join tEmployee b on a.ProjectMan = b.Number "
					+" 	left join tDepartment2 c on b.Department2 = c.Code "
					+"	left join tDepartment1 f on f.Code = b.Department1 "
					+" 	left join (select max(CrtDate) lastCrtDate,CustCode from tWorkSign group by CustCode) d on d.CustCode = a.Code "
					+" 	left join tCusttype e on a.CustType = e.Code "
					+" 	where a.Status = '4' and a.ConfirmBegin is not null and a.ConstructStatus <> '7' and e.IsPartDecorate in ('0','1') "
					+" 	group  by c.Code,c.Desc1,f.Desc1 "
					+" )a "
					+" order by a.stopMoreThanSevenDaysNum desc" ;
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getConstructionList(Page<Map<String,Object>> page, String workerClassify) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from ( "
					+" 	select b.Descr WorkType12Descr, count(a.PK) ConstructionNum, "
					+"	sum(case when datediff(dd,a.PlanEnd,getdate())>0 then 1 else 0 end) OverDueNum, "
					+" 	sum(case when c.CustWkPk is not null then 1 else 0 end) TodaySignNum "
					+" 	from tCustWorker a "
					+" 	left join tWorkType12 b on a.WorkType12 = b.Code "
					+" 	left join (select CustWkPk from tWorkSign "
					+"  where convert(nvarchar(10), CrtDate, 112) = convert(nvarchar(10), getdate(), 112) "
					+"	group by CustWkPk)c on a.pk = c.custWkPk "
					+"  left join tWorker d on a.WorkerCode = d.Code "
					+" 	where a.EndDate is null and b.WorkType1 <> '01' and b.confType in ('2','1') "
					+"  and a.ComeDate < convert(nvarchar(10), dateadd(dd, 1, getdate()), 112) and b.expired ='F' "
					+"  and not exists(select 1 from tWorkSign in_a where in_a.IsComplete = '1' and in_a.CustWkPk = a.PK " 
					+" 		and in_a.PrjItem2 = (select in_b.Code from tPrjItem2 in_b where in_b.worktype12 = a.WorkType12 " 
					+"			and in_b.Seq = (select max(seq) from tPrjItem2 in_c where in_c.workType12 = a.WorkType12 group by in_c.workType12) "
					+"		) " 
					+"	) ";
		if(StringUtils.isNotBlank(workerClassify)){
			sql += " and d.workerClassify = ? ";
			list.add(workerClassify);
		}
		sql += " group by b.Code,b.Descr "
			+" )a "
			+" order by a.OverDueNum desc " ;
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getWaitConfirmPrjProblemList(Page<Map<String,Object>> page) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from ( "
					+" 	select c.Code Department2, c.Desc1 Department2Descr,count(a.No) WaitConfirmNum, " 
					+" 	sum(case when datediff(dd,a.AppDate,getdate())>1 then 1 else 0 end) MoreThanOneDayNoConfirmNum "
					+" 	from tPrjProblem a "
					+" 	left join tEmployee b on a.AppCZY = b.Number "
					+" 	left join tDepartment2 c on b.Department2 = c.Code "
					+" 	where a.Status = '1' "
					+" 	group by c.Code, c.Desc1 "
					+" ) a"
					+" order by a.MoreThanOneDayNoConfirmNum desc ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getWaitDealPrjProblemList(Page<Map<String,Object>> page) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from ("
					+" 	select a.Department2, a.Department2Descr, count(*) WaitDealNum, "
					+" 	sum(case when datediff(dd,a.ConfirmDate,getdate())>3 then 1 else 0 end) MoreThanThreeDaysNoDealNum "
					+" 	from   (  "
					+" 		select a.No,d.Descr PromDept ,a.ConfirmDate,a.DealDate, "
					+" 		case when a.PromDeptCode = '01' then case when designD2.Code is not null then designD2.Desc2 else designD1.Desc2 end " 
					+" 		when a.PromDeptCode = '02' then  case when projectD2.Code is not null then projectD2.Desc2 else projectD1.Desc2 end "
					+"  	when a.PromDeptCode = '03' then b.Descr else d.Descr end Department2Descr, "
					+"		case when a.PromDeptCode = '01' then case when designD2.Code is not null then designD2.Code else designD1.Code end " 
					+" 		when a.PromDeptCode = '02' then  case when projectD2.Code is not null then projectD2.Code else projectD1.Code end "
					+"		when a.PromDeptCode = '03' then b.Code else a.PromDeptCode end Department2 "
					+" 		from tPrjProblem a "
					+"  	left join tPrjPromType b on a.PromTypeCode = b.Code "
					+"		left join tCustomer c on c.Code = a.CustCode "
					+"		left join tEmployee design on design.Number = c.DesignMan "
					+"  	left join tEmployee project on project.Number = c.ProjectMan "
					+"  	left join tDepartment2 designD2 on designD2.Code = design.Department2 "
					+"  	left join tDepartment2 projectD2 on projectD2.Code = project.Department2 "
					+" 		left join tDepartment1 designD1 on designD1.Code = design.Department1 "
					+"  	left join tDepartment1 projectD1 on projectD1.Code = project.Department1 "
					+" 		left join tPrjPromDept d on d.Code = a.PromDeptCode "
					+"  	where a.Status in ( '2', '3' ) "
					+" 	) a "
					+" group by  a.Department2,a.Department2Descr "
					+" )out_a "
					+" order by out_a.MoreThanThreeDaysNoDealNum desc ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getCustProblemList(Page<Map<String,Object>> page) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from ( "
					+" 	select d.Department2,d.Department2Descr, count(a.PK) CustComplaintNum, "
					+" 	sum(case when datediff(dd,a.InfoDate,case when a.DealDate is not null then a.DealDate else getdate() end)>7 then 1 else 0 end) MoreThanSevenDaysNoDealNum "
					+" 	from tCustProblem a "
					+" 	left join tCustComplaint b on a.No = b.No "
					+" 	left join tCustomer c on b.CustCode = c.Code "
					+" 	left join ( "
					+" 		select in_a.Number,case when in_b.Code is not null and in_b.Code <>'' then in_b.Code else in_c.Code end Department2, "
					+" 		case when in_b.Code is not null and in_b.Code <>'' then in_b.Desc1 else in_c.Desc1 end Department2Descr "
					+" 		from  tEmployee in_a  "
					+" 		left join tDepartment2 in_b on in_a.Department2 = in_b.Code "
					+" 		left join tDepartment1 in_c on in_a.Department1 = in_c.code "
					+" 	)d on a.DealCZY = d.Number"
					+" 	where a.PromSource = '2' and a.Status in('0','2') and b.Status in ('0','1') and a.DealCZY is not null and a.DealCZY <> ''"
					+" 	group by d.Department2,d.Department2Descr "
					+" )a "
					+" order by a.MoreThanSevenDaysNoDealNum desc ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getPrjCheckOrConfirmList(Page<Map<String,Object>> page, String countType) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.Number, a.NameChi, a.ConfirmNum, a.CheckNum, a.ModifyNum from ( "
					+" 	select a.NameChi,a.Number, ";
				if("yesterday".equals(countType)){
					sql += " (select sum(case when datediff(dd,date,getdate())=1 then 1 else 0 end) from tPrjProgConfirm where LastUpdatedBy = a.Number and ErrPosi = '0') ConfirmNum, "
						+" 	(select sum(case when datediff(dd,date,getdate())=1 then 1 else 0 end) from tPrjProgCheck  where LastUpdatedBy = a.Number and ErrPosi = '0') CheckNum,  "
						+" 	(select sum(case when datediff(dd,date,getdate())=1 then 1 else 0 end) from tPrjProgCheck  where LastUpdatedBy = a.Number and IsModify = '1' and ErrPosi = '0') ModifyNum  ";
				}else if("today".equals(countType)){
					sql += " (select sum(case when datediff(dd,date,getdate())=0 then 1 else 0 end) from tPrjProgConfirm where LastUpdatedBy = a.Number and ErrPosi = '0') ConfirmNum, "
						+" 	(select sum(case when datediff(dd,date,getdate())=0 then 1 else 0 end) from tPrjProgCheck  where LastUpdatedBy = a.Number and ErrPosi = '0') CheckNum,  "
						+" 	(select sum(case when datediff(dd,date,getdate())=0 then 1 else 0 end) from tPrjProgCheck  where LastUpdatedBy = a.Number and IsModify = '1' and ErrPosi = '0') ModifyNum  ";
				}else if("thisWeek".equals(countType)){
					sql += " (select sum(case when date >= dateadd(week, datediff(week,0,CONVERT(DATETIME,getdate(),120)-1),0) and Date <= dateadd(week, datediff(week,0,CONVERT(DATETIME,getdate(),120)-1),6) then 1 else 0 end) from tPrjProgConfirm where LastUpdatedBy = a.Number and ErrPosi = '0') ConfirmNum, "
						+" 	(select sum(case when date >= dateadd(week, datediff(week,0,CONVERT(DATETIME,getdate(),120)-1),0) and Date <= dateadd(week, datediff(week,0,CONVERT(DATETIME,getdate(),120)-1),6) then 1 else 0 end) from tPrjProgCheck  where LastUpdatedBy = a.Number and ErrPosi = '0') CheckNum,  "
						+" 	(select sum(case when date >= dateadd(week, datediff(week,0,CONVERT(DATETIME,getdate(),120)-1),0) and Date <= dateadd(week, datediff(week,0,CONVERT(DATETIME,getdate(),120)-1),6) then 1 else 0 end) from tPrjProgCheck  where LastUpdatedBy = a.Number and IsModify = '1' and ErrPosi = '0') ModifyNum  ";
				}else if("thisMonth".equals(countType)){
					sql += " (select sum(case when datediff(month,date,getdate())=0 then 1 else 0 end) from tPrjProgConfirm where LastUpdatedBy = a.Number and ErrPosi = '0') ConfirmNum, "
						+" 	(select sum(case when datediff(month,date,getdate())=0 then 1 else 0 end) from tPrjProgCheck  where LastUpdatedBy = a.Number and ErrPosi = '0') CheckNum,  "
						+" 	(select sum(case when datediff(month,date,getdate())=0 then 1 else 0 end) from tPrjProgCheck  where LastUpdatedBy = a.Number and IsModify = '1' and ErrPosi = '0') ModifyNum  ";
				}
					
				sql	+=" from tEmployee a "
					+" 	where exists(select 1 from tPrjProgConfirm where LastUpdatedBy = a.Number) or exists(select 1 from tPrjProgCheck where LastUpdatedBy = a.Number) "
					+" 	group by a.Number,a.NameChi "
					+" )a "
					+" where a.ConfirmNum > 0  or a.CheckNum > 0  or a.ModifyNum > 0 "
					+" order by a.ConfirmNum+a.CheckNum desc";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getPrjProblemDetailList(Page<Map<String,Object>> page,String prjProblemType, String department2) {
		List<Object> list = new ArrayList<Object>();
		String sql = "";
		if("waitConfirmPrjProblem".equals(prjProblemType)) {
			sql = "select out_a.Address, out_a.AppDate, out_a.StatusDescr, out_a.Remarks,out_a.MoreThanDays from ( "
				+" 	select c.Address, a.AppDate ,xt.NOTE StatusDescr, a.Remarks, datediff(dd,a.AppDate,getdate()) MoreThanDays "
				+" 	from tPrjProblem a "
				+" 	left join tEmployee b on a.AppCZY = b.Number "
				+" 	left join tCustomer c on a.CustCode = c.Code "
				+" 	left join tXTDM xt on a.status = xt.CBM and xt.ID='PRJPROMSTATUS' "
				+" 	where a.Status = '1' and b.Department2 = ? "
				+" ) out_a "
				+" order by out_a.MoreThanDays desc ";
		}
		
		if("waitDealPrjProblem".equals(prjProblemType)) {
			sql = "select out_a.Address, out_a.ConfirmDate, out_a.StatusDescr, out_a.Remarks, out_a.MoreThanDays, out_a.DealRemarks "
				+" from ( "
				+" 	select a.No,d.Descr PromDept ,a.ConfirmDate,a.DealDate,c.address, a.Remarks,xt.Note StatusDescr,a.DealRemarks, "
				+"	case when a.PromDeptCode = '01' then case when designD2.Code is not null then designD2.Code else designD1.Code end " 
				+" 	when a.PromDeptCode = '02' then  case when projectD2.Code is not null then projectD2.Code else projectD1.Code end "
				+"	when a.PromDeptCode = '03' then b.Code else a.PromDeptCode end Department2, "
				+"  datediff(dd,a.ConfirmDate,getdate()) MoreThanDays "
				+" 	from tPrjProblem a "
				+"  left join tPrjPromType b on a.PromTypeCode = b.Code "
				+"  left join tCustomer c on c.Code = a.CustCode "
				+"  left join tEmployee design on design.Number = c.DesignMan "
				+"  left join tEmployee project on project.Number = c.ProjectMan "
				+"  left join tDepartment2 designD2 on designD2.Code = design.Department2 "
				+"  left join tDepartment2 projectD2 on projectD2.Code = project.Department2 "
				+" 	left join tDepartment1 designD1 on designD1.Code = design.Department1 "
				+"  left join tDepartment1 projectD1 on projectD1.Code = project.Department1 "
				+"  left join tPrjPromDept d on d.Code = a.PromDeptCode "
				+"  left join tXTDM xt on a.status = xt.CBM and xt.ID='PRJPROMSTATUS' "
				+"  where a.Status in ( '2', '3' ) "
				+" ) out_a "
				+" where out_a.Department2 = ? "
				+" order by out_a.MoreThanDays desc ";
		}
		
		list.add(department2);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getCustProblemDetailList(Page<Map<String,Object>> page, String department2) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( "
					+" 	select c.Address,d.NameChi DealCzyDescr, a.InfoDate, replace(b.Remarks, char(9), '') Remarks, replace(a.DealRemarks, char(9), '') DealRemarks, "
					+"  datediff(dd,a.InfoDate,case when a.DealDate is not null then a.DealDate else getdate() end) MoreThanDays "
					+" 	from tCustProblem a "
					+" 	left join tCustComplaint b on a.No = b.No "
					+" 	left join tCustomer c on b.CustCode = c.Code "
					+" 	left join ( "
					+" 		select in_a.Number,in_a.NameChi,case when in_a.Department2 is not null and in_a.Department2 <>'' then in_b.Code else in_c.Code end Department2, "
					+" 		case when in_a.Department2 is not null and in_a.Department2 <>'' then in_b.Desc1 else in_c.Desc1 end Department2Descr "
					+" 		from  tEmployee in_a  "
					+" 		left join tDepartment2 in_b on in_a.Department2 = in_b.Code "
					+" 		left join tDepartment1 in_c on in_a.Department1 = in_c.code "
					+" 	)d on a.DealCZY = d.Number"
					+" 	where a.PromSource = '2' and a.Status in('0','2') and b.Status in ('0','1') and a.DealCZY is not null and a.DealCZY <> '' and d.Department2 = ? " 
					+" )out_a "
					+" order by out_a.MoreThanDays desc ";
		
		list.add(department2);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getConfirmDetailList(Page<Map<String,Object>> page, String countType, String number) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select b.Address, c.Descr PrjItemDescr,tx.NOTE IsPassDescr,a.Date from tPrjProgConfirm a "
					+" left join tCustomer b on a.CustCode = b.Code "
					+" left join tPrjItem1 c on a.PrjItem = c.Code "
					+" left join tXTDM tx on a.IsPass = tx.CBM and tx.ID = 'YESNO' "
					+" where a.LastUpdatedBy = ? and a.ErrPosi='0' ";
		
		if("yesterday".equals(countType)){
			sql += " and datediff(dd,a.Date,getdate()) = 1 ";
		}else if("today".equals(countType)){
			sql += " and datediff(dd,a.Date,getdate()) = 0 ";
		}else if("thisWeek".equals(countType)){
			sql += " and datediff(week,a.Date,getdate()) = 0 ";
		}else if("thisMonth".equals(countType)){
			sql += " and datediff(month,a.Date,getdate()) = 0 ";
		}
		
		list.add(number);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getCheckDetailList(Page<Map<String,Object>> page, String countType, String number) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select b.Address, a.Remarks, tx.NOTE IsModifyDescr, a.Date  from tPrjProgCheck a "
					+" left join tCustomer b on a.CustCode = b.Code "
					+" left join tXTDM tx on a.IsModify = tx.CBM and tx.ID = 'YESNO' "
					+" where a.AppCZY = ? ";
		
		if("yesterday".equals(countType)){
			sql += " and datediff(dd,a.Date,getdate()) = 1 ";
		}else if("today".equals(countType)){
			sql += " and datediff(dd,a.Date,getdate()) = 0 ";
		}else if("thisWeek".equals(countType)){
			sql += " and datediff(week,a.Date,getdate()) = 0 ";
		}else if("thisMonth".equals(countType)){
			sql += " and datediff(month,a.Date,getdate()) = 0 ";
		}
		
		list.add(number);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getPrjStageProgList(
			Page page, Date beginDate, Date endDate,String stage,Integer pageSize) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  exec pPrjStageProgAnalyApp ?,?,?,?,?";
		list.add(new Timestamp(
				DateUtil.startOfTheDay(beginDate).getTime()));
		list.add(new Timestamp(
				DateUtil.endOfTheDay(endDate).getTime()));
		list.add(stage);
		list.add(pageSize);
		list.add("1");
		page.setResult(findListBySql(sql, list.toArray())); 		
		page.setTotalCount(page.getResult().size());
		return page;
	}
	
	public Page<Map<String, Object>> getWorkerArrangeList(
			Page<Map<String, Object>> page,Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.*,b.Descr workType12Descr," +
				"	cast(Convert(decimal(18,0)," +
				"			case when arrnum=0 then 0 " +
				"				else (cast(isnull(a.ComeInTime,0) as money)*100/cast(a.arrnum as money)) end)" +
				"		as nvarchar(10)" +
				"	)+'%' FirstSignInTimeRate" +
				"	from( select COUNT(1) ArrNum ," +
				"			sum(case when ws.minSignDate is null or " +
				"					CONVERT(varchar(10), ws.minSignDate,120)> CONVERT(varchar(10), a.ComeDate,120) " +
				"					then 0 else 1 end ) ComeInTime," +
				"			Round(avg(case	when cwa.AppComeDate is null" +
				"												or datediff(day, cwa.AppComeDate, a.ComeDate) * 1.0 < 0 then 0.0" +
				"										else datediff(day, cwa.AppComeDate, a.ComeDate) * 1.0" +
				"									end),1) AvgDelayDay," +
				"			sum(case when cwa.AppComeDate is not null then 1 else 0 end) AppNum," +
				"			sum(case when cwa.AppComeDate is not null and DATEDIFF(D,cwa.AppComeDate,a.ComeDate)<=0 then 1 else 0 end ) inTimeNum," +
				"			sum(isnull(arrworkload,0)) arrworkload," +
				"			a.WorkType12,1 dispSeq from tCustWorker a" +
				"		left join tCustomer b on b.Code=a.CustCode " +
				"		left join tEmployee e on e.Number=b.projectman" +
				"		left join tWorker w on w.Code=a.WorkerCode" +
				"		left join tPrjRegion p on w.PrjRegionCode=p.Code " +
				"		left join tcustworkerapp cwa on cwa.CustWorkPk=a.PK" +
				"		left join (select min(crtdate)  minSignDate,CustWkPk  " +
				"					from tWorkSign group by CustWkPk" +
				"		) ws on ws.CustWkPk=a.PK" +
				"		left join ( select in_b.worktype12,in_a.custcode,sum(round(in_a.qty*in_a.offerpri,0)) arrworkload " +
				"		   from tbasecheckitemplan in_a " +
				"		   inner join tbasecheckitem in_b on in_b.code = in_a.basecheckitemcode " +
				"		   group by in_b.worktype12,in_a.custcode " +
				"		)aw on aw.worktype12=a.worktype12  and aw.CustCode=a.CustCode  " +
				"		left join tWorkType12 k on k.code = a.workType12" +
				" where 1=1  and k.ConfType in ('2','1') and k.expired ='F' ";
		if(customer.getBeginDate() != null){
			sql+=" and a.ComeDate >= ? ";
			list.add(new Timestamp(
					DateUtil.startOfTheDay(customer.getBeginDate()).getTime()));
		}
		if(customer.getEndDate() != null){
			sql+=" and a.ComeDate <= ?";
			list.add(new Timestamp(
					DateUtil.endOfTheDay( customer.getEndDate()).getTime()));
		}
		if(StringUtils.isNotBlank(customer.getWorkerClassify())){
			sql += " and w.WorkerClassify = ?";
			list.add(customer.getWorkerClassify());
		}
		sql +=" group by a.WorkType12 " +
				" union all " +
				"		select  0,0,0,0,0,0,a.Code,0 dispSeq from tWorkType12 a" +
				"			where not exists (select 1 from tcustworker in_a " +
				"					left join tWorker in_b on in_a.WorkerCode = in_b.Code" +
				"					where  1=1 and a.code = in_a.WorkType12 " ;
		if(customer.getBeginDate() != null){
			sql+=" and in_a.ComeDate >= ? ";
			list.add(new Timestamp(
					DateUtil.startOfTheDay(customer.getBeginDate()).getTime()));
		}
		if(customer.getEndDate() != null){
			sql+=" and in_a.ComeDate <= ?";
			list.add(new Timestamp(
					DateUtil.endOfTheDay( customer.getEndDate()).getTime()));
		}
		if(StringUtils.isNotBlank(customer.getWorkerClassify())){
			sql += " and in_b.WorkerClassify = ?";
			list.add(customer.getWorkerClassify());
		}
		sql+=") and a.confType in ('1','2') and a.expired = 'F' )a " +
			" left join tWorkType12 b on b.code = a.WorkType12" +
			" where b.expired ='F'" +
			" order by a.dispSeq desc,ComeInTime";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getWorkerCompletedList(
			Page<Map<String, Object>> page,Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select	a.completeNum, Round(cast(isnull(case when a.InTimeConfirm = 0 then 0 else a.inTimeDays / a.InTimeConfirm end,0)as money),1) avgConsDays," +
				"	cast(Convert(decimal(18,1),case when a.completeNum = 0 or a.completeNum is null then 0 else " +
				"	cast(a.InTimeConfirm as money) * 100 / a.completeNum end) as nvarchar(10))+'%' InTimeConfirm, b.Descr workType12Descr" +
				" from	( " +
				"	select a.WorkType12,count(1) completeNum ," +
				"	sum(case  when DATEDIFF(DAY,a.comedate ," +
				"							case when ppc.ConfirmDate is null or ppc.ConfirmDate ='' " +
				"								then a.enddate else ppc.ConfirmDate end )+1<=a.ConstructDay " +
				"				then 1 else 0 end) InTimeConfirm," +
				"	sum(case when  ws.minsigndate is null then 1 else 0 end)  notSignNum," +
				"	avg(case when  datediff(DAY,a.comedate ,case when ppc.ConfirmDate is null or ppc.ConfirmDate =''" +
				"		 then a.enddate else ppc.ConfirmDate end ) < 0 then 0 else " +
				"		datediff(DAY,a.comedate ,case when ppc.ConfirmDate is null or ppc.ConfirmDate =''" +
				"		 then a.enddate else ppc.ConfirmDate end ) end *1.0+1) avgconsDays," +
				"	sum(case  when DATEDIFF(DAY,a.comedate," +
				"				case when ppc.ConfirmDate is null or ppc.ConfirmDate =''  then a.enddate else ppc.ConfirmDate end )+1 > 0 and" +
				"			DATEDIFF(DAY,a.comedate," +
				"				case when ppc.ConfirmDate is null or ppc.ConfirmDate =''  then a.enddate else ppc.ConfirmDate end )+1<=a.ConstructDay" +
				"			then DATEDIFF(DAY,a.comedate ," +
				"				case when ppc.ConfirmDate is null or ppc.ConfirmDate =''  then a.enddate else ppc.ConfirmDate end )+1 else 0 end )inTimeDays" +
				"	,1 dispSeq" +
				" from dbo.tCustWorker a " +
				" left join (select min(crtdate) minSignDate,CustWkPk  from tWorkSign group by CustWkPk) ws on ws.CustWkPk=a.pk" +
				" left join tWorkType12 b on a.WorkType12=b.Code" +
				" left join dbo.tWorker w on w.code=a.WorkerCode" +
				" left join tPrjRegion p on w.PrjRegionCode=p.Code " +
				" left join dbo.tCustomer d on d.Code=a.CustCode " +
				" left join tEmployee e on e.number =d.ProjectMan" +
				" left join tPrjProg ppc on ppc.CustCode=a.CustCode and ppc.prjitem = b.PrjItem " +
				" where 1=1 and b.confType in ('1','2') and b.expired ='F' ";
					
		if(customer.getBeginDate() != null){
			sql +=" and a.EndDate >= ?";
			list.add(new Timestamp(
					DateUtil.startOfTheDay(customer.getBeginDate()).getTime()));
		}
		if(customer.getEndDate() != null){
			sql+=" and a.EndDate <= ?";
			list.add(new Timestamp(
					DateUtil.endOfTheDay( customer.getEndDate()).getTime()));
		}
		if(StringUtils.isNotBlank(customer.getWorkerClassify())){
			sql += " and w.WorkerClassify = ?";
			list.add(customer.getWorkerClassify());
		}
		sql +=" group by a.WorkType12 " +
				"union all" +
				"			select  a.Code,0,0,0,0,0,0 dispSeq from tWorkType12 a " +
				"			where not exists (select 1 from tcustworker in_a " +
				"					left join tWorker in_b on in_a.WorkerCode = in_b.Code" +
				"					where 1=1 and a.code = in_a.WorkType12" ;
		if(customer.getBeginDate() != null){
			sql +=" and in_a.EndDate >= ?";
			list.add(new Timestamp(
					DateUtil.startOfTheDay(customer.getBeginDate()).getTime()));
		}
		if(customer.getEndDate() != null){
			sql+=" and in_a.EndDate <= ?";
			list.add(new Timestamp(
					DateUtil.endOfTheDay( customer.getEndDate()).getTime()));
		}
		if(StringUtils.isNotBlank(customer.getWorkerClassify())){
			sql += " and in_b.WorkerClassify = ?";
			list.add(customer.getWorkerClassify());
		}
		sql+=") and a.confType in ('1','2') and a.expired = 'F')a " +
			" left join tWorkType12 b on b.code = a.WorkType12 " +
			" and b.expired ='F' " +
			" order by a.dispSeq desc,case	when a.completeNum = 0" +
			"							or a.completeNum is null then 0" +
			"							else a.InTimeConfirm*100 / a.completeNum" +
			"						end  ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getPrjAgainList(
			Page page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  exec pPrjStageProgAnalyApp ?,?,?,?,?";
		list.add(new Timestamp(
				DateUtil.startOfTheDay(customer.getBeginDate()).getTime()));
		list.add(new Timestamp(
				DateUtil.endOfTheDay(customer.getEndDate()).getTime()));
		list.add("");
		list.add(page.getPageSize());
		list.add("2");
		page.setResult(findListBySql(sql, list.toArray())); 		
		page.setTotalCount(page.getResult().size());
		return page;
	}
}
