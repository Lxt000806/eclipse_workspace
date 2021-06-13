package com.house.home.dao.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;

@SuppressWarnings("serial")
@Repository
public class PrjEffAnlyDao extends BaseDao {

	@SuppressWarnings("deprecation")
	public List<Map<String, Object>> goJqGrid(Date dateFrom, Date dateTo, String department2s, String custTypes, 
			String sType, String builderCode){
		Connection conn = null;
		CallableStatement call = null;
		List<Map<String, Object>> list = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPrjEffAnly(?,?,?,?,?,?)}");
			call.setString(1, sType);
			call.setTimestamp(2, dateFrom == null ? null:new Timestamp(dateFrom.getTime()));
			call.setTimestamp(3, dateTo == null ? null:new Timestamp(dateTo.getTime()));
			call.setString(4, department2s);
			call.setString(5, custTypes);
			call.setString(6, builderCode);
			call.execute();
			ResultSet rs = call.getResultSet();
			list = BeanConvertUtil.resultSetToList(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return list;
	}

	public Page<Map<String, Object>> goJqGridView(Page<Map<String, Object>> page, Date dateFrom, Date dateTo, String department2s, String custTypes, 
													String dept2Code, String custType, String constructType, String builderCode){//新增项目名称查询字段（builderCode） ADD By zb
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( "
				   + " 		select tc.address,te.NameChi ProjectMan,tct.Desc1 custtypedescr,tx1.NOTE constructtypedescr,tx2.NOTE constructstatusdescr, "
				   + " 		case when datediff(day, ConfirmBegin, getdate()) > tc.ConstructDay then datediff(day, ConfirmBegin, getdate())-tc.ConstructDay "
				   + " 		else 0 end delayday,tc.confirmbegin,d.PlanEnd sdplanend,d.ConfirmDate sdconfirmdate, "
				   + " 		e.PlanEnd nssmplanend,e.confirmdate nssmconfirmdate,f.planend gddmplanend,f.confirmdate gddmconfirmdate, "
				   + " 		g.PlanEnd jgysplanend,g.ConfirmDate jgysconfirmdate, "
				   + " 		case when d.ConfirmDate is null then '水电预埋' "
				   + " 		when d.ConfirmDate is not null and e.ConfirmDate is null then '泥水饰面' "
				   + " 		when d.ConfirmDate is not null and e.ConfirmDate is not null and f.ConfirmDate is null then '刮底打磨' "
				   + " 		else '竣工验收' end currprjitem "	 
				   + " 		from tCustomer tc "
				   + " 		left join tCusttype tct on tc.CustType = tct.Code "
				   + " 		left join tEmployee te on te.Number = tc.ProjectMan "
				   + " 		left join tDepartment2 td2 on td2.Code = te.Department2 "
				   + " 		left join tXTDM tx1 on tx1.ID='CONSTRUCTTYPE' and tx1.CBM = tc.ConstructType "
				   + " 		left join tXTDM tx2 on tx2.ID='CONSTRUCTSTATUS' and tx2.CBM = tc.ConstructStatus "
				   + " 		left join ( "
				   + " 			select * from dbo.fStrToTable(?, ',') "
				   + " 		) a on a.item = td2.Code "
				   + " 		left join ( "
				   + " 			select * from dbo.fStrToTable(?, ',') "
				   + " 		) b on b.item = tc.CustType "
				   + " 		left join ( "
				   + " 			select * from dbo.fStrToTable(?, ',') "
				   + " 		) i on i.item = tc.BuilderCode "//新增项目名称查询（builderCode）--ADD By @zb 2018-07-28
				   + " 		left join ( "
				   + " 			select tpp.CustCode,tpp.PlanEnd,tpp.ConfirmDate "
				   + " 			from tPrjProg tpp "
				   + " 			where PrjItem='5' "
				   + "		) d on d.CustCode = tc.Code "
				   + " 		left join ( "
				   + " 			select tpp.CustCode,tpp.PlanEnd,tpp.ConfirmDate "
				   + " 			from tPrjProg tpp "
				   + " 			where PrjItem='8' "
				   + " 		) e on e.CustCode = tc.Code "
				   + " 		left join ( "
				   + " 			select tpp.CustCode,tpp.PlanEnd,tpp.ConfirmDate "
				   + " 			from tPrjProg tpp "
				   + " 			where PrjItem='19' "
				   + " 		) f on f.CustCode = tc.Code "
				   + " 		left join ( "
				   + " 			select tpp.CustCode,tpp.PlanEnd,tpp.ConfirmDate "
				   + " 			from tPrjProg tpp "
				   + " 			where PrjItem='16' "
				   + " 		) g on g.CustCode = tc.Code "
				   + " 		where tct.IsAddAllInfo='1' and tc.ConfirmBegin is not null and tc.Status='4' "
				   + " 		and ( case when ? <> '' then (case when a.item is not null then 1 else 0 end) else 1 end )=1 "
				   + " 		and ( case when ? <> '' then (case when b.item is not null then 1 else 0 end) else 1 end )=1 "
				   + " 		and ( case when ? <> '' then (case when i.item is not null then 1 else 0 end) else 1 end )=1 ";
		params.add(department2s);
		params.add(custTypes);
		params.add(builderCode);
		params.add(department2s);
		params.add(custTypes);
		params.add(builderCode);
		if(StringUtils.isNotBlank(custType)){
			sql += " and ((?='1' and tc.Custtype=? and tc.ConstructType=?) or (?<>'1' and tc.Custtype=?)) ";
			params.add(custType);
			params.add(custType);
			params.add(constructType);
			params.add(custType);
			params.add(custType);
		}
		if(StringUtils.isNotBlank(dept2Code)){
			sql += " and td2.Code = ? ";
			params.add(dept2Code);
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		System.out.println("\n\n"+sql+"\n\n");
		return this.findPageBySql(page, sql, params.toArray());
	}
}	
