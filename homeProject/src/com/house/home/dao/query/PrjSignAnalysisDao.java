package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.design.Customer;
@Repository
@SuppressWarnings("serial")
public class PrjSignAnalysisDao extends BaseDao {
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.ProjectMan,c.NameChi ProjectManDescr,SignTimes,RealSignDays,isnull(b.CmpNum,0)CmpNum,ValidSignDays, "
					+"case when SignTimes<>0 then round(ValidSignDays/cast(SignTimes as money),4) else 0.00 end SignRate "
					+"from ( "
					+"	select isnull(sum(a.SignTimes),0)SignTimes,isnull(sum(a.signDays),0)RealSignDays," 
					+"	isnull(sum(case when signDays > SignTimes then SignTimes else signDays end),0) ValidSignDays,a.ProjectMan  from( "
					+"		select a.PrjItem,b.Descr,b.SignTimes,c.ProjectMan,d.signDays "
					+"		from tPrjProg a  "
					+"		left join tPrjItem1 b on a.PrjItem=b.Code "
					+"		left join tCustomer c on a.CustCode=c.Code "
					+"		left join ( "
					+"			select count(distinct convert(varchar(100), in_a.crtDate, 112))signDays ,in_a.SignCZY,in_a.PrjItem,in_a.CustCode "
					+"			from tSignIn in_a  "
					+"			inner join tCustomer in_b on in_a.SignCZY=in_b.ProjectMan and in_a.CustCode=in_b.Code "
					+"			group by in_a.SignCZY,in_a.PrjItem,in_a.CustCode "
					+"		)d on c.ProjectMan=d.SignCZY and a.PrjItem=d.PrjItem and c.Code=d.CustCode "
					+"		where a.EndDate >=? and a.EndDate<dateadd(day,1,?) "
					+"	)a group by a.ProjectMan "
					+")a "
					+"left join ( "
					+"	select count(*)cmpNum,in_b.ProjectMan " 
					+"	from tPrjProg in_a  "
					+"	left join tCustomer in_b on in_a.CustCode=in_b.Code "	
					+"	where in_a.EndDate >=? and in_a.EndDate<dateadd(day,1,?) " 
					+"	group by in_b.ProjectMan "
					+")b on a.ProjectMan=b.ProjectMan "
					+"left join tEmployee c on a.ProjectMan=c.Number where 1=1";
				
		list.add(customer.getDateFrom());
		list.add(customer.getDateTo());
		list.add(customer.getDateFrom());
		list.add(customer.getDateTo());
		if(StringUtils.isNotBlank(customer.getProjectMan())){
			sql+=" and a.ProjectMan=?";
			list.add(customer.getProjectMan());
		}
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql += " and c.department2 in (" + SqlUtil.resetStatus(customer.getDepartment2()) + ")";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.SignRate desc";
		}
		List<Map<String, Object>> resultList=this.findListBySql(sql, list.toArray());
		page.setResult(resultList);
		page.setTotalCount(resultList.size());
		return page;
	} 

	public Page<Map<String, Object>> findPageBySql_detail(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.BeginDate,a.EndDate,a.PrjItem,b.Descr PrjItemDescr,b.SignTimes,isnull(d.signDays,0)SignDays,a.CustCode,c.Address, " 
				+"case when isnull(d.signDays,0)>b.SignTimes then b.SignTimes else isnull(d.signDays,0) end ValidSignDays "  
				+"from tPrjProg a "  
				+"left join tPrjItem1 b on a.PrjItem=b.Code " 
				+"left join tCustomer c on a.CustCode=c.Code " 
				+"left join ( " 
				+"	select count(distinct convert(varchar(100), in_a.crtDate, 112))signDays ,in_a.CustCode,in_a.PrjItem " 
				+"	from tSignIn in_a  " 
				+"	inner join tCustomer in_b on in_a.SignCZY=in_b.ProjectMan and in_a.CustCode=in_b.Code " 
				+"	group by in_a.CustCode,in_a.PrjItem " 
				+")d on c.code=d.CustCode and a.PrjItem=d.PrjItem " 
				+"where a.EndDate >=? and a.EndDate<dateadd(day,1,?) "  
				+"and c.ProjectMan=?";

		list.add(customer.getDateFrom());
		list.add(customer.getDateTo());
		list.add(customer.getProjectMan());
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.Address,a.EndDate ";
		}
		List<Map<String, Object>> resultList=this.findListBySql(sql, list.toArray());
		page.setResult(resultList);
		page.setTotalCount(resultList.size());
		return page;
	}
	public Page<Map<String, Object>> findPageBySql_detail_detail(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select CrtDate,c.Descr SignInType2Descr,b.NOTE IsPassDescr,a.Remarks "
			+"from tSignIn a  "
			+"left join tXTDM b on a.IsPass=b.CBM and b.ID='YESNO' "
			+"left join tSignInType2 c on a.SignInType2=c.Code " 
			+"where CustCode=? and PrjItem=? ";

		list.add(customer.getCode());
		list.add(customer.getPrjItem());
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.CrtDate ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
}
