package com.house.home.dao.query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.PrjProblem;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class PrjProblemAnalyDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjProblem prjProblem) {
		List<Object> params = new ArrayList<Object>();
	
		String sql = " select * from ( "
				   + " 		select a.Department2, a.Department2Code, b.NameChi, count(*) count, "
				   + "		sum(case when datediff(hour, a.AppDate, getdate()) > 24 then 1 else 0 end) morethan24 "
				   + "		from ( "
				   + "			select max(d.Number) leader,a.No,a.AppDate,c.Desc2 Department2, c.Code Department2Code "
				   + "			from tprjProblem a "
				   + "			left join tEmployee b on a.AppCZY = b.Number "
				   + "			left join tDepartment2 c on c.Code = b.Department2 "
				   + "			left join tEmployee d on d.Department2 = b.Department2 and d.IsLead='1' and d.LeadLevel='1' and d.expired='F' "
				   + "			where a.Status='1' ";
		
		if (prjProblem.getAppDateFrom() != null) {
			sql += " and a.AppDate >= ? ";
			params.add(prjProblem.getAppDateFrom());
		}
		if (prjProblem.getAppDateTo() != null) {
			sql += " and a.AppDate < ? ";
			params.add(DateUtil.addDateOneDay(prjProblem.getAppDateTo()));
		}
		
		sql += " 	group by a.No,a.AppDate,c.Desc2, c.Code "
		     + " ) a "
			 + " left join tEmployee b on a.leader = b.Number "
			 + " group by a.Department2, a.Department2Code, b.NameChi";
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.morethan24 desc, count desc ";
		}
		
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public Page<Map<String,Object>> findPageBySqlWaitDeal(Page<Map<String,Object>> page, PrjProblem prjProblem) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( "
				   + " 		select a.PromDept,a.Department2,a.Department2Code,a.PromDeptCode, count(*) count, "
				   + "		sum(case when datediff(hour, a.ConfirmDate, " 
				   + "							case when a.FeedbackDate is not null then a.FeedbackDate else getdate() end "
				   + "		) > 24 then 1 else 0 end) morethan24NoFeed, "
				   + "		sum(case when datediff(hour, a.ConfirmDate, " 
				   + "							case when a.DealDate is not null then a.DealDate else getdate() end "
				   + "		) > 36 then 1 else 0 end) morethan36NoDeal "
				   + "		from ( "
				   + "			select d.Descr PromDept, case when a.PromDeptCode = '01' then designD2.Desc2 "
				   + "			when a.PromDeptCode = '02' then projectD2.Desc2 "
				   + " 			when a.PromDeptCode = '03' then b.Descr else d.Descr end Department2, "
				   + "			case when a.PromDeptCode = '01' then designD2.Code "
				   + "			when a.PromDeptCode = '02' then projectD2.Code "
				   + " 			when a.PromDeptCode = '03' then b.Code else a.PromDeptCode end Department2Code, "
				   + "			a.ConfirmDate,a.FeedbackDate,a.DealDate,a.Status,a.PromDeptCode "
				   + " 			from tPrjProblem a "
				   + "			left join tPrjPromType b on a.PromTypeCode = b.Code "
				   + "			left join tCustomer c on c.Code = a.CustCode "
				   + "			left join tEmployee design on design.Number = c.DesignMan "
				   + "			left join tEmployee project on project.Number = c.ProjectMan "
				   + "			left join tDepartment2 designD2 on designD2.Code = design.Department2 "
				   + "			left join tDepartment2 projectD2 on projectD2.Code = project.Department2 "
				   + "			left join tPrjPromDept d on d.Code = a.PromDeptCode "
				   + "			where a.Status in ('2', '3') ";

		if (prjProblem.getAppDateFrom() != null) {
			sql += " and a.AppDate >= ? ";
			params.add(prjProblem.getAppDateFrom());
		}
		if (prjProblem.getAppDateTo() != null) {
			sql += " and a.AppDate < ? ";
			params.add(DateUtil.addDateOneDay(prjProblem.getAppDateTo()));
		}
		
		sql += " ) a "
			 + " group by a.PromDept,a.Department2,a.Department2Code,a.PromDeptCode ";
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.morethan36NoDeal desc, morethan24NoFeed desc, count desc ";
		}
		
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String,Object>> findPageBySqlView(Page<Map<String,Object>> page, PrjProblem prjProblem) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( "
				   + " 		select a.No, b.Address,c.Desc1 CustTypeDescr,d.NameChi AppCZYDescr,a.AppDate, "
				   + " 		e.Descr PromDeptDescr,f.Descr PromTypeDescr,x1.NOTE prjPromPropDescr,x2.NOTE isBringStopDescr, "
				   + "		a.StopDays,a.Remarks "
				   + " 		from tPrjProblem a "
				   + " 		left join tCustomer b on a.CustCode = b.Code "	
				   + " 		left join tCusttype c on c.Code = b.CustType "
				   + "		left join tEmployee d on d.Number = a.AppCZY "
				   + "		left join tPrjPromDept e on e.Code = a.PromDeptCode "
				   + "		left join tPrjPromType f on f.Code = a.PromTypeCode "
				   + " 		left join txtdm x1 on x1.cbm=a.prompropCode and x1.id='PRJPROMPROP' "
				   + " 		left join txtdm x2 on x2.cbm=a.isBringStop and x2.id='YESNO' "
				   + "		left join tEmployee design on design.Number = b.DesignMan "
				   + "		left join tEmployee project on project.Number = b.ProjectMan "
				   + "		left join tDepartment2 designD2 on designD2.Code = design.Department2 "
				   + "		left join tDepartment2 projectD2 on projectD2.Code = project.Department2 "
				   + " 		where 1=1 "; 
		
		if ("1".equals(prjProblem.getStatistcsMethod())) {
			sql += " and a.Status = '1' and isnull(d.Department2, '') = isnull(?, '') ";
			params.add(prjProblem.getPrjDepartment2());
		} else {
			sql += " and a.Status in ('2', '3')  and isnull( "
				 + " 	case when a.PromDeptCode = '01' then designD2.Code "
				 + "	when a.PromDeptCode = '02' then projectD2.Code "
				 + " 	when a.PromDeptCode = '03' then f.Code else a.PromDeptCode end, "
				 + " '') = isnull(?, '') ";
			params.add(prjProblem.getPrjDepartment2());
		}
		if (prjProblem.getAppDateFrom() != null) {
			sql += " and a.AppDate >= ? ";
			params.add(prjProblem.getAppDateFrom());
		}
		if (prjProblem.getAppDateTo() != null) {
			sql += " and a.AppDate < ? ";
			params.add(DateUtil.addDateOneDay(prjProblem.getAppDateTo()));
		}
		if (StringUtils.isNotBlank(prjProblem.getPromDeptCode())) {
			sql += " and a.PromDeptCode = ? ";
			params.add(prjProblem.getPromDeptCode());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
		} else {
			sql += " ) a order by a.AppDate desc ";
		}

		return this.findPageBySql(page, sql, params.toArray());
	}
}
