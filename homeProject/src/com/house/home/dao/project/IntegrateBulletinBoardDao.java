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
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.IntegrateBulletinBoardEvt;
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Repository
public class IntegrateBulletinBoardDao extends BaseDao {
	
	public Page<Map<String,Object>> getDepartmentList(Page<Map<String,Object>> page, UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select Code DepartmentCode,desc2 DepartmentDescr from tDepartment a "
				+" where a.DepType = '6' and Expired = 'F' and a.IsActual = '1' and len(a.Path) - len(replace(a.Path, '/', '')) = 1 ";
		if("2".equals(uc.getCustRight())){//查看本部门权限，可查看本部门+权限部门 （均包含子部门）
			sql+="and (" +
					 " exists(" +
					 "    select 1 from (  "+
					 "       select case when in_dd.Department is not null then in_dd.Department when in_cc.Department is not null then in_cc.Department   "+ 
					 "		 else in_bb.Department end Department   "+
					 "		 from tCzyDept in_aa    "+
					 "		 left join tDepartment1 in_bb on in_aa.Department1=in_bb.Code  "+
					 "		 left join tDepartment2 in_cc on in_aa.Department2=in_cc.Code  "+
					 "		 left join tDepartment3 in_dd on in_aa.Department3=in_dd.Code  "+
					 "		 where CZYBH=?    "+
					 " 	 )in_a  where a.Path like '%'+in_a.Department+'%' " +
					 " ) "+
					 " or " +
					 " exists(" +
					 "    select 1 from tEmployee in_b " +
					 "    left join tDepartment in_c on in_b.Department=in_c.Code " +
					 "    where in_b.Number=? and a.Path like '%'+in_b.Department+'%'" +
					 " ) " +
					 ")";
			list.add(uc.getCzybh());
			list.add(uc.getCzybh());
		}else if("1".equals(uc.getCustRight())) {
			sql += "and 1<>1 ";
		}
		sql += "order by a.DispSeq ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Map<String,Object> getIntegrateBulletinBoardCountInfo(IntegrateBulletinBoardEvt evt) {
		List<Object> params = new ArrayList<Object>();
		String sql= " select a.* from ( "
				+" select "
				+" (select count(a.Code) from tCustomer a "
				+" 	left join tCustIntProg b on a.Code = b.CustCode "
				+"  left join tCustStakeholder c on a.Code = c.CustCode  "
				+"  left join tEmployee d on c.EmpCode = d.Number "
				+"  left join tDepartment e on d.Department = e.Code "
				+" 	where c.Role = ? and a.Status = '4' ";
		if("018".equals(evt.getItemType2())) {
			sql += " and isnull(b.CupDeliverDate,'') = '' ";
			params.add("61");
		}else {
			sql += " and isnull(b.IntDeliverDate,'') = '' ";
			params.add("11");
		}
		if(StringUtils.isNotBlank(evt.getDepartment())) {
			sql += " and e.Code = ? ";
			params.add(evt.getDepartment());
		}
		sql += " ) BuildingNum, "
				+" (select count(a.Code) from tCustomer a "
				+" 	left join tCustIntProg b on a.Code = b.CustCode "
				+"  left join tCustStakeholder c on a.Code = c.CustCode  "
				+"  left join tEmployee d on c.EmpCode = d.Number "
				+"  left join tDepartment e on d.Department = e.Code "
				+" 	where c.Role = ? and a.Status = '4' ";
		if("018".equals(evt.getItemType2())) {
			sql += " and isnull(b.CupDesignDate,'') = '' ";
			params.add("61");
		}else {
			sql += " and isnull(b.IntDesignDate,'') = '' ";
			params.add("11");
		}
		if(StringUtils.isNotBlank(evt.getDepartment())) {
			sql += " and e.Code = ? ";
			params.add(evt.getDepartment());
		}
		sql += " ) DesignNum, "
				+" (select count(a.Code) from tCustomer a "
				+" 	left join tCustIntProg b on a.Code = b.CustCode "
				+"  left join tCustStakeholder c on a.Code = c.CustCode  "
				+"  left join tEmployee d on c.EmpCode = d.Number "
				+"  left join tDepartment e on d.Department = e.Code "
				+" 	where c.Role = ? and a.Status = '4' ";
		if("018".equals(evt.getItemType2())) {
			sql += " and isnull(b.CupAppDate,'') <> '' and  isnull(b.CupSendDate,'') = '' ";
			params.add("61");
		}else {
			sql += " and isnull(b.IntAppDate,'') <> '' and  isnull(b.IntSendDate,'') = '' ";
			params.add("11");
		}
		if(StringUtils.isNotBlank(evt.getDepartment())) {
			sql += " and e.Code = ? ";
			params.add(evt.getDepartment());
		}
		sql +=" ) ProductionNum, " 
				+" (select count(a.Code) from tCustomer a "
				+" 	left join tCustIntProg b on a.Code = b.CustCode "
				+"  left join tCustStakeholder c on a.Code = c.CustCode  "
				+"  left join tEmployee d on c.EmpCode = d.Number "
				+"  left join tDepartment e on d.Department = e.Code "
				+" 	where c.Role = ? and a.Status = '4' ";
		if("018".equals(evt.getItemType2())) {
			sql += " and isnull(b.CupSendDate,'') <> '' and  isnull(b.CupDeliverDate,'') = '' ";
			params.add("61");
		}else {
			sql += " and isnull(b.IntSendDate,'') <> '' and  isnull(b.IntDeliverDate,'') = '' ";
			params.add("11");
		}
		if(StringUtils.isNotBlank(evt.getDepartment())) {
			sql += " and e.Code = ? ";
			params.add(evt.getDepartment());
		}
		sql +=" ) InstallNum, " 
				+" (select count(CustCode) from ( "
				+"   select in_a.custCode from tIntReplenish in_a "
				+"   left join tCustStakeholder in_b on in_a.CustCode = in_b.CustCode and in_b.Role = ? "
				+"   left join tEmployee in_c on in_b.EmpCode = in_c.Number where 1=1 ";
		if("018".equals(evt.getItemType2())) {
			params.add("61");
		}else {
			params.add("11");
		}
		if (StringUtils.isNotBlank(evt.getDepartment())) {
			sql += " and in_c.Department = ? ";
			params.add(evt.getDepartment());
		}
		sql+="	 and in_a.Status in('2','3','4') and in_a.IsCupboard = ? "
		+"	 group by in_a.CustCode "
		+"	)a ";
		if("018".equals(evt.getItemType2())) {
			params.add("1");
		}else {
			params.add("0");
		}
		
		sql +=" ) SalingNum, "
				+" (select count(No) from ( "
				+"   select No from tPrjJob "
				+"   where JobType in('01','06','07','24') and ErrPosi = '0' and datediff(day,Date,getdate()) = 1 "
				+"   union all "
				+"   select a.no from tSignIn a "
				+"   left join tEmployee b on a.SignCZY = b.Number "
				+"   left join tDepartment c on b.Department = c.Code "
				+"   where c.DepType = '6' and a.ErrPosi = '0' and datediff(day,a.CrtDate,getdate()) = 1 "
				+" )a ) SignNum, " 
				+" (select count(no) from ("
				+" 	select No from tPrjProgConfirm "
				+" 	where PrjItem in ('17','26') and IsPass = '1' and ErrPosi = '0' and datediff(day,Date,getdate()) = 1 "
				+" )a ) ConfirmNum "
				+") a";
		System.out.println(sql);
		System.out.println(params);
		List<Map<String,Object>> list=this.findBySql(sql,params.toArray());
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String,Object>> getDesigningList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( ";
		if("design".equals(evt.getDesigningCountType())) {
			sql += " select a.DesignManDescr,count(a.Code) DesigningNum, isnull(sum(a.overtime),0) OvertimeNum ";
		} else {
			sql += " select a.DepartmentDescr,count(a.Code) DesigningNum, isnull(sum(a.overtime),0) OvertimeNum ";
		}
		sql += " from ( " 
			+" 		select a.Code,d.Number,d.NameChi DesignManDescr,e.overtime, "
			+" 		case when isnull(d.Department2,'') = '' then d.Department1 else d.Department2 end Department, "
			+" 		case when isnull(d.Department2Descr,'') = '' then d.Department1Descr else d.Department2Descr end DepartmentDescr "
			+" 		from tCustomer a "
			+" 		left join tCustIntProg b on a.Code = b.CustCode "
			+" 		left join tCustStakeholder c on a.Code = c.CustCode "
			+" 		inner join ( "
			+" 			select in_a.Number,in_a.NameChi,in_a.Department1,in_b.Desc1 Department1Descr,in_a.Department2,in_c.Desc1 Department2Descr "
			+" 			from tEmployee in_a "
			+" 			left join tDepartment1 in_b on in_a.Department1 = in_b.Code "
			+" 			left join tDepartment2 in_c on in_a.Department2 = in_c.Code "
			+"  		left join tDepartment in_d on in_a.Department = in_d.Code ";
		if(StringUtils.isNotBlank(evt.getDepartment())) {
			sql += " where in_d.Code = ? ";
			list.add(evt.getDepartment());
		}
		sql += " 	)d on d.Number = c.EmpCode "
			+" 		left join ( "
			+" 			select CustCode,1 overtime from tPrjJob "
			+" 			where Status in ('2','3','4') "
			+" 			and JobType = ? and datediff(day,Date, case when DealDate is null then getdate() else DealDate end) > ? "
			+" 			group by CustCode "
			+" 		)e on e.CustCode = a.Code "
			+" 		where c.Role = ? and a.Status = '4' ";
		if("018".equals(evt.getItemType2())) { //橱柜
			sql += " and isnull(b.CupDesignDate ,'') = '' ";
		}else {
			sql += " and isnull(b.IntDesignDate ,'') = '' ";
		}
		sql += " ) a ";
		if("design".equals(evt.getDesigningCountType())) {
			sql += " group by a.Number, a.DesignManDescr ";
		} else {
			sql += " group by a.Department, a.DepartmentDescr  ";
		}
		sql += " ) a order by a.OvertimeNum desc ";
		if("018".equals(evt.getItemType2())) { //橱柜
			list.add("07"); // 任务类型为 橱柜精良
			list.add(5); // 超期天数
			list.add("61"); // 橱柜设计师
		}else {
			list.add("01"); // 任务类型为 衣柜精良
			list.add(7); // 超期天数
			list.add("11"); // 衣柜设计师
		}
		System.out.println(sql);
		System.out.println(list);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getDesignedList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from ( ";
		if("design".equals(evt.getDesigningCountType())) {
			sql += " select a.DesignManDescr,count(a.Code) DesignedNum ,round((count(a.Code)-isnull(sum(a.overtime),0))*1.0/count(a.Code)*100,0) TimelyRate ";
				
		} else {
			sql += " select a.DepartmentDescr,count(a.Code) DesignedNum ,round((count(a.Code)-isnull(sum(a.overtime),0))*1.0/count(a.Code)*100,0) TimelyRate ";
		}
		sql += " from ( " 
			+" 		select a.Code,d.Number,d.NameChi DesignManDescr,e.overtime, "
			+" 		case when isnull(d.Department2,'') = '' then d.Department1 else d.Department2 end Department, "
			+" 		case when isnull(d.Department2Descr,'') = '' then d.Department1Descr else d.Department2Descr end DepartmentDescr "
			+" 		from tCustomer a "
			+" 		left join tCustIntProg b on a.Code = b.CustCode "
			+" 		left join tCustStakeholder c on a.Code = c.CustCode "
			+" 		inner join ( "
			+" 			select in_a.Number,in_a.NameChi,in_a.Department1,in_b.Desc1 Department1Descr,in_a.Department2,in_c.Desc1 Department2Descr "
			+" 			from tEmployee in_a "
			+" 			left join tDepartment1 in_b on in_a.Department1 = in_b.Code "
			+" 			left join tDepartment2 in_c on in_a.Department2 = in_c.Code "
			+"  		left join tDepartment in_d on in_a.Department = in_d.Code ";
		if(StringUtils.isNotBlank(evt.getDepartment())) {
			sql += " where in_d.Code = ? ";
			list.add(evt.getDepartment());
		}
		sql += " 	)d on d.Number = c.EmpCode "
			+" 		left join ( "
			+" 			select CustCode,1 overtime from tPrjJob "
			+" 			where Status in ('2','3','4') "
			+" 			and JobType = ? and datediff(day,Date, case when DealDate is null then getdate() else DealDate end) > ? "
			+" 			group by CustCode "
			+" 		)e on e.CustCode = a.Code "
			+" 		where c.Role = ? and a.Status = '4' ";
		if("018".equals(evt.getItemType2())) {
			sql += " and isnull(b.CupDesignDate ,'') <> '' and datediff(month,b.CupDesignDate,getdate()) = ? ";
		}else {
			sql += " and isnull(b.IntDesignDate ,'') <> '' and datediff(month,b.IntDesignDate,getdate()) = ? ";
		}
		sql += " ) a ";
		if("design".equals(evt.getDesigningCountType())) {
			sql += " group by a.Number, a.DesignManDescr ";
		} else {
			sql += " group by a.Department, a.DepartmentDescr  ";
		}
		sql += " ) a order by a.TimelyRate desc ";
		if("018".equals(evt.getItemType2())) { //橱柜
			list.add("07"); // 任务类型为 橱柜精良
			list.add(5); // 超期天数
			list.add("61"); // 橱柜设计师
		}else {
			list.add("01"); // 任务类型为 衣柜精良
			list.add(7); // 超期天数
			list.add("11"); // 衣柜设计师
		}
		if("thisMonth".equals(evt.getDesignedCountCondition())){
			list.add(0);
		}else {
			list.add(1);
		}
		System.out.println(sql);
		System.out.println(list);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getProductionList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from ( "
			+" select a.SupplierCode,a.SupplierDescr,count(a.Code) productionNum ,isnull(sum(a.overtime),0) OvertimeNum "
		 	+" from ( " 
			+" 		select a.Code,e.overtime,f.Code SupplierCode,f.Descr SupplierDescr "
			+" 		from tCustomer a "
			+" 		left join tCustIntProg b on a.Code = b.CustCode "
			+" 		left join tCustStakeholder c on a.Code = c.CustCode "
			+" 		inner join ( "
			+" 			select in_a.Number,in_a.NameChi,in_a.Department1,in_b.Desc1 Department1Descr,in_a.Department2,in_c.Desc1 Department2Descr "
			+" 			from tEmployee in_a "
			+" 			left join tDepartment1 in_b on in_a.Department1 = in_b.Code "
			+" 			left join tDepartment2 in_c on in_a.Department2 = in_c.Code "
			+"  		left join tDepartment in_d on in_a.Department = in_d.Code ";
			if(StringUtils.isNotBlank(evt.getDepartment())) {
				sql += " where in_d.Code = ? ";
				list.add(evt.getDepartment());
			}
		sql += " 	)d on d.Number = c.EmpCode "
			+" 		left join ( "
			+" 			select CustCode,1 overtime from tPrjJob "
			+" 			where Status in ('2','3','4') "
			+" 			and JobType = ? and  datediff(day,Date, case when DealDate is null then getdate() else DealDate end) > 3 and getdate()>PlanDate "
			+" 			group by CustCode "
			+" 		)e on e.CustCode = a.Code ";
		if("018".equals(evt.getItemType2())) { //橱柜
			sql += " left join tSupplier f on b.CupSpl = f.Code "
				+  " where c.Role = ? and a.Status = '4' and isnull(b.CupAppDate,'') <> '' and  isnull(b.CupSendDate,'') = '' ";
		}else {
			sql += " left join tSupplier f on b.IntSpl = f.Code "
				+  " where c.Role = ? and a.Status = '4' and isnull(b.IntAppDate,'') <> '' and  isnull(b.IntSendDate,'') = '' ";
		}
		sql += " ) a  "
			+" group by a.SupplierCode,a.SupplierDescr "
			+" ) a order by a.OvertimeNum desc ";
		if("018".equals(evt.getItemType2())) { //橱柜
			list.add("08"); // 任务类型为 橱柜出货申请
			list.add("61"); // 橱柜设计师
		}else {
			list.add("09"); // 任务类型为 衣柜出货申请
			list.add("11"); // 衣柜设计师
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getShippedList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from ( "
			+" select a.SupplierCode,a.SupplierDescr,count(a.Code) sendNum ,round((count(a.Code)-isnull(sum(a.overtime),0))*1.0/count(a.Code)*100,0) TimelyRate "
		 	+" from ( " 
			+" 		select a.Code,e.overtime,f.Code SupplierCode,f.Descr SupplierDescr "
			+" 		from tCustomer a "
			+" 		left join tCustIntProg b on a.Code = b.CustCode "
			+" 		left join tCustStakeholder c on a.Code = c.CustCode "
			+" 		left join ( "
			+" 			select in_a.Number,in_a.NameChi,in_a.Department1,in_b.Desc1 Department1Descr,in_a.Department2,in_c.Desc1 Department2Descr "
			+" 			from tEmployee in_a "
			+" 			left join tDepartment1 in_b on in_a.Department1 = in_b.Code "
			+" 			left join tDepartment2 in_c on in_a.Department2 = in_c.Code "
			+"  		left join tDepartment in_d on in_a.Department = in_d.Code ";
		if(StringUtils.isNotBlank(evt.getDepartment())) {
			sql += " where in_d.Code = ? ";
			list.add(evt.getDepartment());
		}
		sql += " 	)d on d.Number = c.EmpCode "
			+" 		left join ( "
			+" 			select CustCode,1 overtime from tPrjJob "
			+" 			where Status in ('2','3','4') "
			+" 			and JobType = ? and  datediff(day,Date, case when DealDate is null then getdate() else DealDate end) > 3 and getdate()>PlanDate "
			+" 			group by CustCode "
			+" 		)e on e.CustCode = a.Code ";
		if("018".equals(evt.getItemType2())) { //橱柜
			sql += " left join tSupplier f on b.CupSpl = f.Code "
				+" 		where c.Role = ? and a.Status = '4' and isnull(b.CupAppDate,'') <> '' and  isnull(b.CupSendDate,'') <> ''  ";
		}else {
			sql += " left join tSupplier f on b.IntSpl = f.Code "
				+" 		where c.Role = ? and a.Status = '4' and isnull(b.IntAppDate,'') <> '' and  isnull(b.IntSendDate,'') <> ''  ";
		}
		sql += " ) a  "
			+" group by a.SupplierCode,a.SupplierDescr "
			+" ) a order by a.TimelyRate desc ";
		if("018".equals(evt.getItemType2())) { //橱柜
			list.add("08"); // 任务类型为 橱柜出货申请
			list.add("61"); // 橱柜设计师
		}else {
			list.add("09"); // 任务类型为 衣柜出货申请
			list.add("11"); // 衣柜设计师
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getInstallingList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( ";
		if("worker".equals(evt.getInstallingCountType())) {
			sql += " select a.WorkerCode,a.WorkerDescr,count(a.Code) InstallingNum, isnull(sum(a.overtime),0) OvertimeNum, isnull(sum(a.todayContruction),0) TodayContructionNum ";
		} else {
			sql += " select a.Department DepartmentCode,a.DepartmentDescr,count(a.Code) InstallingNum, isnull(sum(a.overtime),0) OvertimeNum, isnull(sum(a.todayContruction),0) TodayContructionNum  ";
		}
		sql += " from ( " 
			+" 		select a.Code,g.Code WorkerCode,case when isnull(g.NameChi,'') = '' then '未安排' else g.NameChi end WorkerDescr, "
			+" 		case when isnull(d.Department2,'') = '' then d.Department1 else d.Department2 end Department, "
			+" 		case when isnull(d.Department2Descr,'') = '' then d.Department1Descr else d.Department2Descr end DepartmentDescr, "
			+"  	case when isnull(d.Department2,'') = '' then '1' else '2' end DepartmentType,";
		if("018".equals(evt.getItemType2())) { //橱柜
			sql += " case when datediff(day,b.CupSendDate, getdate()) > 5 then 1 else 0 end Overtime, " ;
		}else {
			sql += " case when datediff(day,b.IntSendDate, getdate()) > 10 then 1 else 0 end Overtime, " ;
		}
		sql	+= " 	case when isnull(f.CrtDate,'') <> '' then 1 else 0 end todayContruction "
			+" 		from tCustomer a "
			+" 		left join tCustIntProg b on a.Code = b.CustCode "
			+" 		left join tCustStakeholder c on a.Code = c.CustCode "
			+" 		inner join ( "
			+" 			select in_a.Number,in_a.NameChi,in_a.Department1,in_b.Desc1 Department1Descr,in_a.Department2,in_c.Desc1 Department2Descr "
			+" 			from tEmployee in_a "
			+" 			left join tDepartment1 in_b on in_a.Department1 = in_b.Code "
			+" 			left join tDepartment2 in_c on in_a.Department2 = in_c.Code "
			+"  		left join tDepartment in_d on in_a.Department = in_d.Code ";
		if(StringUtils.isNotBlank(evt.getDepartment())) {
			sql += " where in_d.Code = ? ";
			list.add(evt.getDepartment());
		}
		sql += " 	)d on d.Number = c.EmpCode "
			+" 		left join ( "
			+" 			select max(Pk) PK,in_a.CustCode,in_a.WorkerCode from tCustWorker in_a "
			+" 			where in_a.WorkType12 = ? "
			+" 			group by in_a.CustCode,in_a.WorkerCode "
			+"  	)e on e.CustCode =a.Code "
			+"		left join tWorkSign f on e.PK = f.CustWkPk and datediff(day,f.CrtDate,getdate()) = 0 "
			+"		left join tWorker g on e.WorkerCode = g.Code "
			+" 		where c.Role = ? and a.Status = '4' ";
		if("018".equals(evt.getItemType2())) { //橱柜
			sql += " and isnull(b.CupSendDate,'') <> '' and  isnull(b.CupDeliverDate,'') = '' ";
		}else {
			sql += " and isnull(b.IntSendDate,'') <> '' and  isnull(b.IntDeliverDate,'') = '' ";
		}	
		sql += " ) a ";
		if("worker".equals(evt.getInstallingCountType())) {
			sql += " group by a.WorkerCode,a.WorkerDescr ";
		} else {
			sql += " group by a.Department, a.DepartmentDescr  ";
		}
		sql += " )a order by a.OvertimeNum desc ";
		if("018".equals(evt.getItemType2())) { //橱柜
			list.add("18"); // 橱柜安装工种
			list.add("61"); // 橱柜设计师
		}else {
			list.add("17"); // 衣柜安装工种
			list.add("11"); // 衣柜设计师
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getInstalledList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (";
		if("worker".equals(evt.getInstalledCountType())) {
			sql += " select a.WorkerCode,a.WorkerDescr,count(a.Code) InstalledNum, round((count(a.Code)-isnull(sum(a.overtime),0))*1.0/count(a.Code)*100,0) TimelyRate ";
				
		} else {
			sql += " select a.DepartmentDescr,count(a.Code) InstalledNum, round((count(a.Code)-isnull(sum(a.overtime),0))*1.0/count(a.Code)*100,0) TimelyRate ";
		}
		sql += " from ( " 
			+" 		select a.Code,f.Code WorkerCode,case when isnull(f.NameChi,'') = '' then '未安排' else f.NameChi end WorkerDescr, "
			+" 		case when isnull(d.Department2,'') = '' then d.Department1 else d.Department2 end Department, "
			+" 		case when isnull(d.Department2Descr,'') = '' then d.Department1Descr else d.Department2Descr end DepartmentDescr ";
		if("018".equals(evt.getItemType2())) { //橱柜
			sql += " ,case when datediff(day,b.CupSendDate, b.CupDeliverDate) > 5 then 1 else 0 end Overtime ";
		}else {
			sql += " ,case when datediff(day,b.IntSendDate, b.IntDeliverDate) > 10 then 1 else 0 end Overtime ";
		}
		sql	+= " 	from tCustomer a "
			+" 		left join tCustIntProg b on a.Code = b.CustCode "
			+" 		left join tCustStakeholder c on a.Code = c.CustCode "
			+" 		left join ( "
			+" 			select in_a.Number,in_a.NameChi,in_a.Department1,in_b.Desc1 Department1Descr,in_a.Department2,in_c.Desc1 Department2Descr "
			+" 			from tEmployee in_a "
			+" 			left join tDepartment1 in_b on in_a.Department1 = in_b.Code "
			+" 			left join tDepartment2 in_c on in_a.Department2 = in_c.Code "
			+"  		left join tDepartment in_d on in_a.Department = in_d.Code ";
		if(StringUtils.isNotBlank(evt.getDepartment())) {
			sql += " where in_d.Code = ? ";
			list.add(evt.getDepartment());
		}
		sql += " 	)d on d.Number = c.EmpCode "
			+" 		left join tCustWorker e on a.Code = e.CustCode and WorkType12 = ? "
			+"		left join tWorker f on e.WorkerCode = f.Code "
			+" 		where c.Role = ? and a.Status = '4' ";
		if("018".equals(evt.getItemType2())) { //橱柜
			sql += "and isnull(b.CupSendDate,'') <> '' and  isnull(b.CupDeliverDate,'') <> '' and datediff(month,b.CupDeliverDate,getdate()) = ? ";
		}else {
			sql += "and isnull(b.IntSendDate,'') <> '' and  isnull(b.IntDeliverDate,'') <> '' and datediff(month,b.IntDeliverDate,getdate()) = ? ";
		}
		sql += " ) a ";
		if("worker".equals(evt.getInstalledCountType())) {
			sql += " group by a.WorkerCode,a.WorkerDescr ";
		} else {
			sql += " group by a.Department, a.DepartmentDescr  ";
		}
		sql += " ) a order by a.TimelyRate desc ";
		if("018".equals(evt.getItemType2())) { //橱柜
			list.add("18"); // 橱柜安装工种
			list.add("61"); // 橱柜设计师
		}else {
			list.add("17"); // 衣柜安装工种
			list.add("11"); // 衣柜设计师
		}
		if("thisMonth".equals(evt.getInstalledCountCondition())){
			list.add(0);
		}else {
			list.add(1);
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getSalingList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		List<Object> list = new ArrayList<Object>();
		String sql = "";
		if ("ordered".equals(evt.getSalingCountCondition())) {
			sql = " select d.Descr supplDescr,count(distinct b.CustCode) salingNum, "
					+" isnull(sum(case when datediff(day,b.Date,case when isnull(b.FinishDate,'') = '' then getdate() else b.FinishDate end) > 3 then 1 else 0 end),0) OvertimeNum "
					+" from tIntReplenish b"
					+" left join tIntReplenishDT c on b.No = c.No "
					+" left join tSupplier d on c.IntSpl = d.Code "
					+" where b.Status = '2' and b.IsCupboard = ? "
					+" group by d.Code,d.Descr ";
			if("018".equals(evt.getItemType2())) {
				list.add("1");
			}else {
				list.add("0");
			}
		}else if("sent".equals(evt.getSalingCountCondition())) {
			sql = " select d.Desc1 DepartmentDescr,count(a.CustCode) salingNum, "
					+" isnull(sum(case when datediff(day,b.ReadyDate,case when isnull(b.FinishDate,'') = '' then getdate() else b.FinishDate end) > 3 then 1 else 0 end),0) OvertimeNum, "
					+" isnull(sum(case when isnull(e.ConfirmDate,'') <> '' then 1 else 0 end ),0) TopcoatedNum "
					+" from "
					+" (select max(No) No, custCode from tIntReplenish "
					+" 	where Status in('3','4') and IsCupboard = ? "
					+" 	group by CustCode "
					+" ) a "
					+" left join tIntReplenish b on a.No = b.No "
					+" left join tCustStakeholder f on a.CustCode = f.CustCode and f.Role = ? "
					+" left join tEmployee c on f.EmpCode = c.Number "
					+" left join tDepartment d on c.Department = d.Code "
					+" left join tPrjProg e on a.CustCode = e.CustCode and PrjItem = '10' "
					+" where 1=1 ";
			if("018".equals(evt.getItemType2())) {
				list.add("1");
				list.add("61");
			}else {
				list.add("0");
				list.add("11");
			}
			if(StringUtils.isNotBlank(evt.getDepartment())) {
				sql += " and d.Code = ? ";
				list.add(evt.getDepartment());
			}
			sql+=" group by d.Code,d.Desc1 ";
		}else {
			sql = " select d.Desc1 DepartmentDescr,count(a.CustCode) salingNum, "
					+" isnull(sum(case when datediff(day,b.Date,case when isnull(b.FinishDate,'') = '' then getdate() else b.FinishDate end) > 6 then 1 else 0 end),0) OvertimeNum, "
					+" isnull(sum(case when isnull(e.ConfirmDate,'') <> '' then 1 else 0 end ),0) TopcoatedNum "
					+" from "
					+" (select max(No) No, custCode from tIntReplenish "
					+" 	where Status in('2','3','4') and IsCupboard = ? "
					+" 	group by CustCode "
					+" ) a "
					+" left join tIntReplenish b on a.No = b.No "
					+" left join tCustStakeholder f on a.CustCode = f.CustCode and f.Role = ? "
					+" left join tEmployee c on f.EmpCode = c.Number "
					+" left join tDepartment d on c.Department = d.Code "
					+" left join tPrjProg e on a.CustCode = e.CustCode and PrjItem = '10' "
					+" where 1=1 ";
			if("018".equals(evt.getItemType2())) {
				list.add("1");
				list.add("61");
			}else {
				list.add("0");
				list.add("11");
			}
			if(StringUtils.isNotBlank(evt.getDepartment())) {
				sql += " and d.Code = ? ";
				list.add(evt.getDepartment());
			}
			sql+=" group by d.Code,d.Desc1 ";
		}
		
		System.out.println(sql);
		System.out.println(list);
		return this.findPageByJdbcTemp(page, sql, "a.salingNum desc", list.toArray());
	}
	
	public Page<Map<String,Object>> getSignOrConfirmList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		List<Object> list = new ArrayList<Object>();
		
		String dateStr = "day";
		int dateDiff = 0;
		
		if("yesterday".equals(evt.getSignOrConfirmCountCondition())) {
			dateDiff = 1;
		}
		if("thisWeek".equals(evt.getSignOrConfirmCountCondition())) {
			dateStr = "week";
		}else if("thisMonth".equals(evt.getSignOrConfirmCountCondition())) {
			dateStr = "month";
		}

		String sql = " select a.Number, a.NameChi, a.SignNum, a.ConfirmNum from ( "
					+" 	select a.NameChi,a.Number, isnull(b.SignNum, 0) SignNum, isnull(c.ConfirmNum, 0) ConfirmNum, "
					+" 	isnull(b.SignNum, 0) + isnull(c.ConfirmNum, 0) OrderNum "
					+" 	from tEmployee a "
					+" 	left join ( "
					+" 		select a.CrtCzy,count(1) SignNum from ( "
					+" 			select in_a.CustCode,in_a.No,in_a.DealDate CrtDate,in_a.DealCZY CrtCzy from tPrjJob in_a "
					+" 			where in_a.JobType in('01','06','07','24') and in_a.ErrPosi = '0' and datediff(" + dateStr + ",in_a.DealDate, getdate()) = " + dateDiff 
					+" 			union all "
					+" 			select in_a.CustCode,in_a.no,in_a.CrtDate,in_a.SignCZY CrtCzy from tSignIn in_a " 
					+" 			left join tEmployee in_b on in_a.SignCZY = in_b.Number "
					+" 			left join tDepartment in_c on in_b.Department = in_c.Code " 
					+" 			where in_a.ErrPosi = '0'  and datediff(" + dateStr + ",in_a.CrtDate, getdate()) = " + dateDiff
					+" 		) a group by a.CrtCzy "
					+" 	) b on a.Number = b.CrtCzy "
					+" 	left join ( "
					+" 		select a.ConfirmCzy, count(1) ConfirmNum from ( "
					+" 			select in_a.CustCode,in_a.Date ConfirmDate,in_a.LastUpdatedBy ConfirmCzy from tPrjProgConfirm in_a " 
					+" 			where in_a.PrjItem in ('17','26') and in_a.IsPass = '1' and in_a.ErrPosi = '0' and datediff(" + dateStr + ",in_a.Date, getdate()) = " + dateDiff
					+"		) a group by a.ConfirmCzy "
					+" 	)c on a.Number = c.ConfirmCzy " 
					+" 	left join tDepartment d on a.Department = d.Code "
					+" 	where d.DepType = '6' ";
				if(StringUtils.isNotBlank(evt.getDepartment())) {
					sql += " and d.Code = ? ";
					list.add(evt.getDepartment());
				}
				sql	+= " )a "
					+" where a.ConfirmNum > 0  or a.SignNum > 0 "
					+" order by a.OrderNum desc";
		return this.findPageBySql(page, sql, list.toArray());

	}
	
	public Page<Map<String,Object>> getProductionDetailList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.Address,f.Descr SupplierDescr,e.SendAppDate ";
		if("018".equals(evt.getItemType2())) { //橱柜
			sql += " ,b.CupAppDate AppDate,dateadd(day,g.SendDay,b.CupAppDate) SendPlanDate  ";
		}else {
			sql += " ,b.IntAppDate AppDate,dateadd(day,g.SendDay,b.IntAppDate) SendPlanDate ";
		}
		sql += " from tCustomer a "
			+" 	left join tCustIntProg b on a.Code = b.CustCode "
			+" 	left join tCustStakeholder c on a.Code = c.CustCode ";
		if("018".equals(evt.getItemType2())) { //橱柜
			sql += " outer apply (select SendDay from tIntSendDay in_a where b.CupMaterial = in_a.MaterialCode and in_a.ItemType12 = '30' ) g ";
		}else {
			sql += " outer apply (select SendDay from tIntSendDay in_a where b.IntMaterial = in_a.MaterialCode and in_a.ItemType12 = '31' ) g ";
		}
		sql+=" 	left join ( "
			+" 		select in_a.Number,in_a.NameChi,in_a.Department1,in_b.Desc1 Department1Descr,in_a.Department2,in_c.Desc1 Department2Descr "
			+" 		from tEmployee in_a "
			+" 		left join tDepartment1 in_b on in_a.Department1 = in_b.Code "
			+" 		left join tDepartment2 in_c on in_a.Department2 = in_c.Code "
			+"  	left join tDepartment in_d on in_a.Department = in_d.Code ";
		if(StringUtils.isNotBlank(evt.getDepartment())) {
			sql += " where in_d.Code = ? ";
			list.add(evt.getDepartment());
		}
		sql += " )d on d.Number = c.EmpCode "
			+" 	inner join ( "
			+" 		select CustCode,Date SendAppDate from tPrjJob "
			+" 		where Status in ('2','3','4') "
			+" 		and JobType = ? and  datediff(day,Date, case when DealDate is null then getdate() else DealDate end) > 3 and getdate()>PlanDate "
			+" 		group by CustCode,PlanDate,Date "
			+" 	)e on e.CustCode = a.Code ";
		if("018".equals(evt.getItemType2())) { //橱柜
			sql += " left join tSupplier f on b.CupSpl = f.Code "
				+" where c.Role = ? and a.Status = '4' and isnull(b.CupAppDate,'') <> '' and  isnull(b.CupSendDate,'') = '' and f.Code = ? ";
		}else {
			sql += " left join tSupplier f on b.IntSpl = f.Code "
				+" where c.Role = ? and a.Status = '4' and isnull(b.IntAppDate,'') <> '' and  isnull(b.IntSendDate,'') = '' and f.Code = ? ";
		}

		if("018".equals(evt.getItemType2())) { //橱柜
			list.add("08"); // 任务类型为 橱柜出货申请
			list.add("61"); // 橱柜设计师
		}else {
			list.add("09"); // 任务类型为 衣柜出货申请
			list.add("11"); // 衣柜设计师
		}
		list.add(evt.getSupplierCode());
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getInstallingDetailList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.Code,g.Code WorkerCode,case when isnull(g.NameChi,'') = '' then '未安排' else g.NameChi end WorkerDescr, "
			+" a.Address,h.ComeDate ";
		if("018".equals(evt.getItemType2())) { //橱柜
			sql += " ,CupSendDate ";
		}else {
			sql += " ,IntSendDate ";
		}
		sql	+= " from tCustomer a "
			+" left join tCustIntProg b on a.Code = b.CustCode "
			+" left join tCustStakeholder c on a.Code = c.CustCode "
			+" inner join ( "
			+" 	select in_a.Number,in_a.NameChi,in_a.Department1,in_b.Desc1 Department1Descr,in_a.Department2,in_c.Desc1 Department2Descr "
			+" 	from tEmployee in_a "
			+" 	left join tDepartment1 in_b on in_a.Department1 = in_b.Code "
			+" 	left join tDepartment2 in_c on in_a.Department2 = in_c.Code "
			+" 	left join tDepartment in_d on in_a.Department = in_d.Code ";
		if(StringUtils.isNotBlank(evt.getDepartment())) {
			sql += " where in_d.Code = ? ";
			list.add(evt.getDepartment());
		}
		sql	+= " )d on d.Number = c.EmpCode "
			+" 	left join ( "
			+" 		select max(Pk) PK,in_a.CustCode,in_a.WorkerCode from tCustWorker in_a "
			+" 		where in_a.WorkType12 = ? "
			+" 		group by in_a.CustCode,in_a.WorkerCode "
			+"  )e on e.CustCode =a.Code "
			+"	left join tWorkSign f on e.PK = f.CustWkPk and datediff(day,f.CrtDate,getdate()) = 0 "
			+"	left join tWorker g on e.WorkerCode = g.Code "
			+"  left join tCustWorker h on e.pk = h.PK ";
			
		
		if("018".equals(evt.getItemType2())) { //橱柜
			sql += " where c.Role = ? and a.Status = '4' and isnull(b.CupSendDate,'') <> '' and  isnull(b.CupDeliverDate,'') = '' "
				+" and datediff(day,b.CupSendDate, case when isnull(b.CupDeliverDate,'') = '' then getdate() else b.CupDeliverDate end) > 5 ";
			list.add("18"); // 橱柜安装工种
			list.add("61"); // 橱柜设计师
		}else {
			sql += " where c.Role = ? and a.Status = '4' and isnull(b.IntSendDate,'') <> '' and  isnull(b.IntDeliverDate,'') = '' "
				+" and datediff(day,b.IntSendDate, case when isnull(b.IntDeliverDate,'') = '' then getdate() else b.IntDeliverDate end) > 10 ";
			list.add("17"); // 衣柜安装工种
			list.add("11"); // 衣柜设计师
		}
		if("worker".equals(evt.getInstallingCountType())) {
			if(StringUtils.isNotBlank(evt.getCode())) {
				sql += " and g.Code = ? ";
				list.add(evt.getCode());
			}else {
				sql += " and g.Code is null ";
			}
		} else {
			if("1".equals(evt.getDepartmentType())) {
				sql += " and d.Department1 = ? ";
				
			}else {
				sql += " and d.Department2 = ? ";
			}
			list.add(evt.getCode());
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getSignOrConfirmDetailList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.*,b.Address,c.NameChi CrtCzyDescr from ( "
					+"	select '测量' Type,CustCode,No,DealDate CrtDate,DealCZY CrtCzy from tPrjJob " 
					+"	where JobType in('01','06','07','24') and ErrPosi = '0' "
					+"	union all "
					+"	select '签到' Type,a.CustCode,a.no,a.CrtDate,a.SignCZY CrtCzy from tSignIn a "  
					+"	left join tEmployee b on a.SignCZY = b.Number "
					+"	left join tDepartment c on b.Department = c.Code "  
					+"	where c.DepType = '6' and a.ErrPosi = '0' "
					+"	union all "
					+"	select '验收' Type,CustCode,No,Date CrtDate,LastUpdatedBy CrtCzy from tPrjProgConfirm " 
					+"	where PrjItem in ('17','26') and IsPass = '1' and ErrPosi = '0' "
					+" ) a "
					+" left join tCustomer b on a.CustCode = b.Code "
					+" left join tEmployee c on a.CrtCzy = c.Number "
					+" where a.CrtCzy = ? ";
		list.add(evt.getNumber());
		if("yesterday".equals(evt.getSignOrConfirmCountCondition())){
			sql += " and datediff(day,a.CrtDate,getdate()) = 1 ";
		}else if("today".equals(evt.getSignOrConfirmCountCondition())){
			sql += " and datediff(day,a.CrtDate,getdate()) = 0 ";
		}else if("thisWeek".equals(evt.getSignOrConfirmCountCondition())){
			sql += " and datediff(week,a.CrtDate,getdate()) = 0 ";
		}else if("thisMonth".equals(evt.getSignOrConfirmCountCondition())){
			sql += " and datediff(month,a.CrtDate,getdate()) = 0 ";
		}
		return this.findPageBySql(page, sql, list.toArray());

	}
	
	public Page<Map<String,Object>> getWorkSignList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from ( select a.WorkerCode, a.WorkerDescr, count(1) SignNum from ("
				+" select a.WorkerCode,b.NameChi WorkerDescr,count(a.CustCode) SignNum from tWorkSign a "
				+" left join tWorker b on a.WorkerCode = b.Code "
				+" where 1=1 ";
		if("018".equals(evt.getItemType2())) {
			sql += " and a.PrjItem2 = '22' ";
		}else {
			sql += " and a.PrjItem2 = '21' ";
		}
		if("yesterday".equals(evt.getWorkSignCountCondition())){
			sql += " and datediff(dd,a.CrtDate,getdate()) = 1 ";
		}else if("today".equals(evt.getWorkSignCountCondition())){
			sql += " and datediff(dd,a.CrtDate,getdate()) = 0 ";
		}else if("thisWeek".equals(evt.getWorkSignCountCondition())){
			sql += " and datediff(week,a.CrtDate,getdate()) = 0 ";
		}else if("thisMonth".equals(evt.getWorkSignCountCondition())){
			sql += " and datediff(month,a.CrtDate,getdate()) = 0 ";
		}
		sql += " group by a.WorkerCode,b.NameChi,a.CustCode "
			+ " ) a "
			+ " group by a.WorkerCode, a.WorkerDescr ) a "
			+ " order by a.SignNum desc ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getWorkSignDetailList(Page<Map<String,Object>> page, IntegrateBulletinBoardEvt evt) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.CustCode,c.Address,a.WorkerCode,b.NameChi WorkerDescr,a.CrtDate,a.No from tWorkSign a "
				+" left join tWorker b on a.WorkerCode = b.Code "
				+" left join tCustomer c on a.CustCode = c.Code "
				+" where 1=1 and a.WorkerCode = ? ";
		list.add(evt.getWorkerCode());
		if("018".equals(evt.getItemType2())) {
			sql += " and a.PrjItem2 = '22' ";
		}else {
			sql += " and a.PrjItem2 = '21' ";
		}
		if("yesterday".equals(evt.getWorkSignCountCondition())){
			sql += " and datediff(dd,a.CrtDate,getdate()) = 1 ";
		}else if("today".equals(evt.getWorkSignCountCondition())){
			sql += " and datediff(dd,a.CrtDate,getdate()) = 0 ";
		}else if("thisWeek".equals(evt.getWorkSignCountCondition())){
			sql += " and datediff(week,a.CrtDate,getdate()) = 0 ";
		}else if("thisMonth".equals(evt.getWorkSignCountCondition())){
			sql += " and datediff(month,a.CrtDate,getdate()) = 0 ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
}
