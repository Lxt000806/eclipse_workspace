package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.basic.Department2;

@Repository
@SuppressWarnings("serial")
public class PrjDeptPerfDao extends BaseDao {

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Department2 department2,
			String orderBy, String direction,String statistcsMethod) {
		/*
		 * String sql = " select * from ( " +
		 * " 		select d2.Code dept2code,d2.Desc2 dept2descr,d1.Desc1 dept1descr,e.nameChi prjdeptleaderdescr,pl.Number prjdeptleadercode,isnull(cbf.confirmbuilds,0) confirmbuilds,isnull(cbf.contractfee,0) contractfee, "
		 * +
		 * " 		isnull(cbap.checkbuilds,0) checkbuilds,isnull(cbap.checkamount,0) checkamount,isnull(cbap.checkperf,0) checkperf, "
		 * + " 		isnull(otb.ontimecheckbuilds,0) ontimecheckbuilds, " +
		 * " 		round( case when cbap.checkbuilds is null or cbap.checkbuilds = '0' then 0 "
		 * +
		 * " 		else isnull(otb.ontimecheckbuilds,0)*1.0/isnull(cbap.checkbuilds,0) end ,3) ontimecheckrate, "
		 * +
		 * " 		isnull(rbf.reorderbuilds,0) reorderbuilds,isnull(rbf.reorderfee,0) reorderfee,d2.DispSeq, "
		 * +
		 * " 		case when isnull(cbap.checkbuilds, 0) = 0 then 0 else isnull(cbap.sumConstructDay, 0)/isnull(cbap.checkbuilds, 0) end avgConstructDay "
		 * + " 		from tDepartment2 d2 " +
		 * " 		left join tDepartment1 d1 on d2.Department1 = d1.Code " +
		 * " 		left join ( " + " 			select b.Code,min(a.Number) Number " +
		 * " 			from tEmployee a " +
		 * " 			left join tDepartment2 b on a.Department2 = b.Code " +
		 * " 			where a.LeadLevel='1' and a.IsLead='1' " +
		 * " 			group by b.Code " + " 		) pl on pl.Code = d2.Code " +
		 * " 		left join tEmployee e on e.Number = pl.Number " +
		 * " 		left join ( " +
		 * " 			select count(*) confirmbuilds,c.Code,sum(isnull(contractfee,0)) contractfee "
		 * + " 			from tCustomer a " +
		 * " 			left join tEmployee b on a.ProjectMan = b.Number " +
		 * " 			left join tDepartment2 c on b.Department2 = c.Code " +
		 * " 			where c.DepType='3' and a.ConfirmBegin is not null ";
		 * 
		 * if(department2.getDateFrom() != null){ sql +=
		 * " and a.ConfirmBegin >= ? "; params.add(department2.getDateFrom()); }
		 * if(department2.getDateTo() != null){ sql +=
		 * " and a.ConfirmBegin < ? ";
		 * params.add(DateUtil.addDate(department2.getDateTo(),1)); }
		 * if(StringUtils.isNotBlank(department2.getCustType())){ sql +=
		 * " and a.CustType in ('"+department2.getCustType().trim().replace(",",
		 * "','")+"')"; } sql += " 	group by c.Code " +
		 * " ) cbf on cbf.Code = d2.Code " + " left join ( " +
		 * " 	select count(*) checkbuilds,d.Code,sum(isnull(a.CheckAmount,0)) checkamount,sum(isnull(a.CheckPerf,0)) checkperf, "
		 * + " 	sum(datediff(day,b.ConfirmBegin,b.EndDate)) sumConstructDay " +
		 * " 	from tPrjPerfDetail a " +
		 * " 	left join tCustomer b on a.CustCode = b.Code " +
		 * " 	left join tEmployee c on c.Number = a.ProjectMan " +
		 * " 	left join tDepartment2 d on d.Code = c.Department2 " +
		 * " 	where d.DepType='3' "; if(department2.getDateFrom() != null){ sql
		 * += " and a.CustCheckDate >= ? ";
		 * params.add(department2.getDateFrom()); } if(department2.getDateTo()
		 * != null){ sql += " and a.CustCheckDate < ? ";
		 * params.add(DateUtil.addDate(department2.getDateTo(),1)); }
		 * if(StringUtils.isNotBlank(department2.getCustType())){ sql +=
		 * " and b.CustType in ('"+department2.getCustType().trim().replace(",",
		 * "','")+"')"; } sql += " 	group by d.Code " +
		 * " ) cbap on cbap.Code = d2.Code " + " left join ( " +
		 * " 	select d.Code,count(*) ontimecheckbuilds " +
		 * " 	from tPrjPerfDetail a " +
		 * " 	left join tCustomer b on a.CustCode = b.Code " +
		 * " 	left join tEmployee c on c.Number = a.ProjectMan " +
		 * " 	left join tDepartment2 d on d.Code = c.Department2 " +
		 * " 	where d.DepType='3' and datediff(day,b.ConfirmBegin,a.CustCheckDate) <= b.ConstructDay "
		 * ; if(department2.getDateFrom() != null){ sql +=
		 * " and a.CustCheckDate >= ? "; params.add(department2.getDateFrom());
		 * } if(department2.getDateTo() != null){ sql +=
		 * " and a.CustCheckDate < ? ";
		 * params.add(DateUtil.addDate(department2.getDateTo(),1)); }
		 * if(StringUtils.isNotBlank(department2.getCustType())){ sql +=
		 * " and b.CustType in ('"+department2.getCustType().trim().replace(",",
		 * "','")+"')"; } sql += " 	group by d.Code " +
		 * " ) otb on otb.Code = d2.Code " + " left join ( " +
		 * " 	select count(*) reorderbuilds,d.Code,sum(a.ContractFee) reorderfee "
		 * + " 	from tCustomer a " +
		 * " 	left join tCustStakeholder b on a.Code=b.CustCode " +
		 * " 	left join tEmployee c on b.EmpCode = c.Number " +
		 * " 	left join tDepartment2 d on c.Department2 = d.Code " +
		 * " 	where b.Role='01' and d.DepType='3' ";
		 * if(department2.getDateFrom() != null){ sql +=
		 * " and a.SignDate >= ? "; params.add(department2.getDateFrom()); }
		 * if(department2.getDateTo() != null){ sql += " and a.SignDate < ? ";
		 * params.add(DateUtil.addDate(department2.getDateTo(),1)); }
		 * if(StringUtils.isNotBlank(department2.getCustType())){ sql +=
		 * " and a.CustType in ('"+department2.getCustType().trim().replace(",",
		 * "','")+"')"; } sql += " 	group by d.code " +
		 * " ) rbf on rbf.Code = d2.Code " + " where d2.DepType='3' ";
		 * if(StringUtils.isNotBlank(department2.getDept1Code())){ sql +=
		 * " and d1.Code = ? "; params.add(department2.getDept1Code()); }
		 * if(StringUtils.isNotBlank(department2.getCode())){ sql +=
		 * " and d2.code in " + "('"+department2.getCode().replaceAll(",",
		 * "','")+"')"; } if(StringUtils.isNotBlank(page.getPageOrderBy())){ sql
		 * +=
		 * " ) a where not ( a.confirmBuilds = 0 and a.checkBuilds = 0 and a.reorderbuilds = 0 ) order by "
		 * +page.getPageOrderBy()+" "+page.getPageOrder(); }else{ sql +=
		 * " ) a where not ( a.confirmBuilds = 0 and a.checkBuilds = 0 and a.reorderbuilds = 0 ) order by a.DispSeq "
		 * ; } System.out.println(sql); System.out.println(params);
		 */
		List<Object> list = new ArrayList<Object>();
		String sql = "  exec pGcbyjfx ?,?,?,?,?,?,?,?,? ";
		list.add(department2.getDateFrom());
		list.add(department2.getDateTo());
		list.add(department2.getCustType());
		list.add(department2.getCode());
		list.add(orderBy);
		list.add(direction);
		list.add(department2.getCheckDateFrom());
		list.add(department2.getCheckDateTo());
		list.add(statistcsMethod);
		page.setResult(findListBySql(sql, list.toArray()));
		page.setTotalCount(page.getResult().size());
		return page;
	}

	public Page<Map<String, Object>> goConfBuildsOutJqGrid(
			Page<Map<String, Object>> page, String dept2Code, Date dateFrom,
			Date dateTo, String custType) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select a.address,a.confirmBegin,a.contractFee,d.Desc1 custTypeDescr,b.nameChi projectManDescr "
				+ " from tCustomer a "
				+ " left join tEmployee b on a.ProjectMan = b.Number "
				+ " left join tDepartment2 c on b.Department2 = c.Code "
				+ " left join tCusttype d on d.Code = a.CustType "
				+ " where ConfirmBegin is not null and c.Code=? "
				+ " and exists ( select * "
				+ " from   tBuilder f "
				+ " left join tEmployee e on f.PrjLeader = e.Number "
				+ " where  a.BuilderCode = f.Code "
				+ " and f.IsPrjSpc = '1' "
				+ " and e.Department2 <> b.Department2 ) ";
		params.add(dept2Code);
		if (dateFrom != null) {
			sql += " and a.ConfirmBegin >= ? ";
			params.add(dateFrom);
		}
		if (dateTo != null) {
			sql += " and a.ConfirmBegin < ?  ";
			params.add(DateUtil.addDate(dateTo, 1));
		}
		if (StringUtils.isNotBlank(custType)) {
			sql += " and a.custType in ('"
					+ custType.trim().replace(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by " + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String, Object>> goConfBuildsInJqGrid(
			Page<Map<String, Object>> page, String dept2Code, Date dateFrom,
			Date dateTo, String custType) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select a.address,a.confirmBegin,a.contractFee,d.Desc1 custTypeDescr,b.nameChi projectManDescr "
				+ " from tCustomer a "
				+ " left join tEmployee b on a.ProjectMan = b.Number "
				+ " left join tDepartment2 c on b.Department2 = c.Code "
				+ " left join tCusttype d on d.Code = a.CustType "
				+ " where ConfirmBegin is not null and c.Code<>? "
				+ " and exists ( select * "
				+ " from   tBuilder f "
				+ " left join tEmployee e on f.PrjLeader = e.Number "
				+ " where a.BuilderCode = f.Code and f.IsPrjSpc = '1' "
				+ " and e.Department2 =?)";
		params.add(dept2Code);
		params.add(dept2Code);
		if (dateFrom != null) {
			sql += " and a.ConfirmBegin >= ? ";
			params.add(dateFrom);
		}
		if (dateTo != null) {
			sql += " and a.ConfirmBegin < ?  ";
			params.add(DateUtil.addDate(dateTo, 1));
		}
		if (StringUtils.isNotBlank(custType)) {
			sql += " and a.custType in ('"
					+ custType.trim().replace(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by " + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String, Object>> goConfBuildsJqGrid(
			Page<Map<String, Object>> page, String empCode, Date dateFrom,
			Date dateTo, String custType) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select a.address,a.confirmBegin,a.contractFee,d.Desc1 custTypeDescr,b.nameChi projectManDescr "
				+ " from tCustomer a "
				+ " left join tEmployee b on a.ProjectMan = b.Number "
				+ " left join tDepartment2 c on b.Department2 = c.Code "
				+ " left join tCusttype d on d.Code = a.CustType "
				+ " where ConfirmBegin is not null and b.Number=? and a.status='4' and a.checkoutdate is null ";
		params.add(empCode);
		if (dateFrom != null) {
			sql += " and a.ConfirmBegin >= ? ";
			params.add(dateFrom);
		}
		if (dateTo != null) {
			sql += " and a.ConfirmBegin < ?  ";
			params.add(DateUtil.addDate(dateTo, 1));
		}
		if (StringUtils.isNotBlank(custType)) {
			sql += " and a.custType in ('"
					+ custType.trim().replace(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by " + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String, Object>> goCheckBuildsJqGrid(
			Page<Map<String, Object>> page, String empCode, Date checkDateFrom,
			Date checkDateTo, String custType,String department2) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select b.address,b.area,b.confirmBegin,c.nameChi projectManDescr, "
				+ " d.Desc1 custTypeDescr,a.CustCheckDate,a.checkAmount,a.checkPerf,b.ConstructDay,tpp.ConfirmDate EndCheckDate, "
				+ " b.EndDate,"
				+ " isnull(datediff(day, isnull(f.EarliestComeDate, b.ConfirmBegin), isnull(tpp.ConfirmDate, b.EndDate))"
				+ "     - isnull(g.NewYearDays, 0) + 1, 0) ActConfDay,"
				+ " a.mainchg+a.softchg+a.intchg+a.servchg itemchg,a.basechg, "
				+ " case when datediff(day, isnull(f.EarliestComeDate, b.ConfirmBegin), isnull(tpp.ConfirmDate, b.EndDate)) "
				+ "         - isnull(g.NewYearDays, 0) + 1 <= b.ConstructDay " 
				+ " then '是' else '否' end isontime, "
				+ " f.EarliestComeDate BeginCheckDate, isnull(g.NewYearDays, 0) NewYearDays "
				+ " from tPrjPerfDetail a "
				+ " left join tCustomer b on a.CustCode = b.Code "
				+ " left join tEmployee c on c.Number = a.ProjectMan "
				+ " left join tCusttype d on d.Code = b.CustType "
				+ " left join tDepartment2 e on c.Department2 = e.Code "
				+ " left join tPrjProg tpp on tpp.CustCode=a.CustCode and tpp.ConfirmDate is not null and tpp.PrjItem='16' "//竣工验收
				+ " left join ( "
				+ "     select in_a.CustCode, min(in_a.ComeDate) EarliestComeDate "
				+ "     from tCustWorker in_a "
				+ "     left join tWorkType12 in_b on in_a.WorkType12 = in_b.Code "
				+ "     where in_b.BeginCheck = '1' "
				+ "     group by in_a.CustCode "
				+ " ) f on a.CustCode = f.CustCode "
				+ " outer apply ( "
				+ "     select count(in_a.Date) NewYearDays "
				+ "     from tCalendar in_a "
				+ "     where in_a.Date >= convert(nvarchar(10), isnull(f.EarliestComeDate, b.ConfirmBegin), 120) "
				+ "         and in_a.Date <= convert(nvarchar(10), isnull(tpp.ConfirmDate, b.EndDate), 120) "
				+ "         and in_a.HoliType = '3' "
				+ " ) g "
				+ " where a.PrjDeptLeader=? and a.prjDepartment2=? "; 
		params.add(empCode);
		params.add(department2);
		System.out.println(department2);
		if (checkDateFrom != null) {
			sql += " and a.CustCheckDate >= ? ";
			params.add(checkDateFrom);
		}
		if (checkDateTo != null) {
			sql += " and a.CustCheckDate < ?  ";
			params.add(DateUtil.addDate(checkDateTo, 1));
		}
		if (StringUtils.isNotBlank(custType)) {
			sql += " and b.custType in ('"
					+ custType.trim().replace(",", "','") + "')";
		}
		/*if (StringUtils.isNotBlank(department2)) {
			sql += " and a.prjDepartment2 in ('"
					+ department2.trim().replace(",", "','") + "')";
		}*/
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by " + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String, Object>> goCheckBuildsOutJqGrid(
			Page<Map<String, Object>> page, String dept2Code, Date dateFrom,
			Date dateTo, String custType) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select b.address,b.confirmBegin,c.nameChi projectManDescr,d.Desc1 custTypeDescr,a.CustCheckDate,a.checkAmount,a.checkPerf,b.ConstructDay, "
				+ " b.EndDate,datediff(day,b.ConfirmBegin,b.EndDate) ActConfDay "
				+ " from tPrjPerfDetail a "
				+ " left join tCustomer b on a.CustCode = b.Code "
				+ " left join tEmployee c on c.Number = a.projectMan "
				+ " left join tCusttype d on d.Code = b.CustType "
				+ " left join tDepartment2 e on c.Department2 = e.Code "
				+ " where e.Code=? "
				+ " and exists ( select * "
				+ " from   tBuilder f "
				+ " left join tEmployee g on f.PrjLeader = g.Number "
				+ " where  b.BuilderCode = f.Code "
				+ " and f.IsPrjSpc = '1' "
				+ " and g.Department2 <> c.Department2 ) ";
		params.add(dept2Code);
		if (dateFrom != null) {
			sql += " and a.CustCheckDate >= ? ";
			params.add(dateFrom);
		}
		if (dateTo != null) {
			sql += " and a.CustCheckDate < ?  ";
			params.add(DateUtil.addDate(dateTo, 1));
		}
		if (StringUtils.isNotBlank(custType)) {
			sql += " and b.custType in ('"
					+ custType.trim().replace(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by " + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String, Object>> goCheckBuildsInJqGrid(
			Page<Map<String, Object>> page, String empCode, Date dateFrom,
			Date dateTo, String custType) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select b.address,b.confirmBegin,c.nameChi projectManDescr,d.Desc1 custTypeDescr,a.CustCheckDate,a.checkAmount,a.checkPerf,b.ConstructDay, "
				+ " b.EndDate,datediff(day,b.ConfirmBegin,b.EndDate) ActConfDay "
				+ " from tPrjPerfDetail a "
				+ " left join tCustomer b on a.CustCode = b.Code "
				+ " left join tEmployee c on c.Number = a.projectMan "
				+ " left join tCusttype d on d.Code = b.CustType "
				+ " left join tDepartment2 e on c.Department2 = e.Code "
				+ " where c.Number<>? "
				+ " and exists ( select * "
				+ " from   tBuilder f "
				+ " left join tEmployee e on f.PrjLeader = e.Number "
				+ " where b.BuilderCode = f.Code and f.IsPrjSpc = '1' "
				+ " and e.Number =?)";
		params.add(empCode);
		params.add(empCode);
		if (dateFrom != null) {
			sql += " and a.CustCheckDate >= ? ";
			params.add(dateFrom);
		}
		if (dateTo != null) {
			sql += " and a.CustCheckDate < ?  ";
			params.add(DateUtil.addDate(dateTo, 1));
		}
		if (StringUtils.isNotBlank(custType)) {
			sql += " and b.custType in ('"
					+ custType.trim().replace(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by " + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String, Object>> goReOrderBuildsJqGrid(
			Page<Map<String, Object>> page, String empCode, Date dateFrom,
			Date dateTo, String custType,String department2 ) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from (  select a.AchieveDate signDate,e.address,e.area,f.Desc1 custtypedescr,c.Number," 
		 			+"a.RecalPerf reorderfee,c.NameChi reordermandescr,c2.NameChi leadername,d.desc1 department2descr, "
		 			+"a.quantity,g.NOTE typedescr,c3.NameChi projectmandescr,e.BusinessMan,c4.NameChi BusinessManDescr, "
		 			+"b.EmpCode,c5.NameChi AgainManDescr, a.ContractFee * b.PerfPer ContractAmount "
					+"from tPerformance a  "
					+"inner join tPerfStakeholder b on a.PK=b.PerfPK "
					+"left join tEmployee c on b.EmpCode = c.Number "
					+"inner join tEmployee c2 on b.LeaderCode = c2.Number "
					+"left join tDepartment1 d1 on c2.Department1 = d1.Code "
					+"left join tDepartment2 d2 on c2.Department2 = d2.Code "
					+"left join tDepartment2 d on c.Department2 = d.Code "
					+"left join tCustomer e on a.CustCode = e.Code "
					+"left join tEmployee c3 on e.ProjectMan = c3.Number "
					+"left join tCusttype f on f.Code = e.CustType "
					+"left join tXTDM g on a.Type = g.CBM and g.ID='PERFTYPE' "
					//添加业务员、翻单员 add by zb on 20190529
					+"left join tEmployee c4 on c4.Number=e.BusinessMan "
					+"left join tEmployee c5 on c5.Number=b.EmpCode "
					+"where c2.Number=? and b.Department2=? and b.Role in ('01','24') "
					+"    and (d1.DepType in ('3','9','10') or d2.DepType in ('3','9','10')) "
					+"    and b.IsCalcDeptPerf='1' and a.Type in ('1','4','5') ";
		params.add(empCode);
		params.add(department2);
		if (dateFrom != null) {
			sql += " and a.AchieveDate >= ? ";
			params.add(dateFrom);
		}
		if (dateTo != null) {
			sql += " and a.AchieveDate < ?  ";
			params.add(DateUtil.addDate(dateTo, 1));
		}
		if (StringUtils.isNotBlank(custType)) {
			sql += " and e.custType in ('"
					+ custType.trim().replace(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by " + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public Page<Map<String, Object>> goChangedPerformanceJqGrid(
	        Page<Map<String, Object>> page, Map<String, String> postData) {
	    
	    List<Object> params = new ArrayList<Object>();
	    
	    String sql = " select * from ( "
	            + "select a.AchieveDate signDate, f.address, f.area, "
	            + "    h.Desc1 custtypedescr, c.Number, a.RecalPerf PerfAmount, "
	            + "    c.NameChi AgainManDescr, lead.NameChi leadername, "
	            + "    e.desc1 department2descr, a.quantity, i.NOTE typedescr, "
	            + "    g.NameChi projectmandescr, f.BusinessMan, "
	            + "    j.NameChi BusinessManDescr, "
	            + "    a.ContractFee * b.PerfPer ContractAmount "
	            + "from tPerformance a "
	            + "inner join tPerfStakeholder b on a.PK = b.PerfPK "
	            + "left join tEmployee c on b.EmpCode = c.Number "
	            + "inner join tEmployee lead on b.LeaderCode = lead.Number "
	            + "left join tDepartment1 ld1 on lead.Department1 = ld1.Code "
	            + "left join tDepartment2 ld2 on lead.Department2 = ld2.Code "
	            + "left join tDepartment2 e on c.Department2 = e.Code "
	            + "left join tCustomer f on a.CustCode = f.Code "
	            + "left join tEmployee g on f.ProjectMan = g.Number "
	            + "left join tCusttype h on f.CustType = h.Code "
	            + "left join tXTDM i on a.Type = i.CBM and i.ID = 'PERFTYPE' "
	            + "left join tEmployee j on j.Number = f.BusinessMan "
	            + "where lead.Number = ? "
	            + "    and lead.Department2 = ? "
	            + "    and b.Role in ('01', '24') "
	            + "    and (ld1.DepType in ('3','9','10') or ld2.DepType in ('3','9','10')) "
	            + "    and b.IsCalcDeptPerf = '1' "
	            + "    and a.Type in ('3', '6')";
	    
	    params.add(postData.get("empCode"));
	    params.add(postData.get("department2"));
	    
	    if (postData.get("dateFrom") != null) {
	        sql += " and a.AchieveDate >= ? ";
	        params.add(postData.get("dateFrom"));
	    }
	    
	    if (postData.get("dateTo") != null) {
	        sql += " and a.AchieveDate < dateadd(day, 1, ?) ";
	        params.add(postData.get("dateTo"));
	    }
	    
	    String custType = postData.get("custType");
	    if (StringUtils.isNotBlank(custType)) {
	        sql += " and f.custType in ('"
	                + custType.trim().replace(",", "','") + "')";
	    }
	    
	    if (StringUtils.isNotBlank(page.getPageOrderBy())) {
	        sql += " ) a order by " + page.getPageOrderBy() + " "
	                + page.getPageOrder();
	    } else {
	        sql += " ) a ";
	    }
	    
	    return this.findPageBySql(page, sql, params.toArray());
	}

}
