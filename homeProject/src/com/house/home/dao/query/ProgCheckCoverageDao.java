package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;

@Repository
@SuppressWarnings("serial")
public class ProgCheckCoverageDao extends BaseDao  {

	public List<Map<String,Object>> goJqGrid(Page<Map<String, Object>> page, Date dateFrom, Date dateTo, String isCheckDept){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( "
				   + " 		select '5' type,'4次以上' progCheckNum,count(*) allBuilds "
				   + " 		from ( "
				   + " 			select count(*) num from tPrjProgCheck where Date >= ? and Date < ? ";
		params.add(DateUtil.startOfTheDay(dateFrom));
		params.add(DateUtil.addDate(dateTo, 1));
		if(StringUtils.isNotBlank(isCheckDept)){
			sql += " and isCheckDept=? ";
			params.add(isCheckDept);
		}
		sql += " group by CustCode having count(*)>4 ) a "
			 + " union "
			 + " select '4' type,'4次' progCheckNum,count(*) allBuilds "
			 + " from ( "
			 + " 	select count(*) num from tPrjProgCheck where Date >= ? and Date < ? ";
		params.add(DateUtil.startOfTheDay(dateFrom));
		params.add(DateUtil.addDate(dateTo, 1));
		if(StringUtils.isNotBlank(isCheckDept)){
			sql += " and isCheckDept=? ";
			params.add(isCheckDept);
		}
		sql += " group by CustCode having count(*)=4 ) a "
			 + " union "
			 + " select '3' type,'3次' progCheckNum,count(*) allBuilds "
			 + " from ( "
			 + " 	select count(*) num from tPrjProgCheck where Date >= ? and Date < ? ";
		params.add(DateUtil.startOfTheDay(dateFrom));
		params.add(DateUtil.addDate(dateTo, 1));
		if(StringUtils.isNotBlank(isCheckDept)){
			sql += " and isCheckDept=? ";
			params.add(isCheckDept);
		}
		sql += " group by CustCode having count(*)=3 ) a "
			 + " union "
			 + " select '2' type,'2次' progCheckNum,count(*) allBuilds "
			 + " from ( "
			 + "	select count(*) num from tPrjProgCheck where Date >= ? and Date < ? ";
		params.add(DateUtil.startOfTheDay(dateFrom));
		params.add(DateUtil.addDate(dateTo, 1));
		if(StringUtils.isNotBlank(isCheckDept)){
			sql += " and isCheckDept=? ";
			params.add(isCheckDept);
		}
		sql += " group by CustCode having count(*)=2 ) a "
			 + " union "
			 + " select '1' type,'1次' progCheckNum,count(*) allBuilds "
			 + " from ( "
			 + "	select count(*) num from tPrjProgCheck where Date >= ? and Date < ? ";
		params.add(DateUtil.startOfTheDay(dateFrom));
		params.add(DateUtil.addDate(dateTo, 1));
		if(StringUtils.isNotBlank(isCheckDept)){
			sql += " and isCheckDept=? ";
			params.add(isCheckDept);
		}
		sql += " group by CustCode having count(*)=1 ) a "
			 + " union "
			 + " select '0' type,'0次' progCheckNum,count(*) allBuilds "
			 + " from tCustomer tc "
			 + " left join tCustType tct on tct.Code = tc.CustType "
			 + " where (not  ( tc.SignDate > ? or isnull(tc.EndDate, getdate()) < ? )) "
			 + " and (tc.Status='4' or (tc.Status='5' and (tc.EndCode='3' or tc.EndCode='4'))) "
			 + " and not exists( "
			 + " 	select 1 from tPrjProgCheck where Date >= ? and Date < ? and CustCode = tc.Code ";
		params.add(DateUtil.startOfTheDay(dateTo));
		params.add(DateUtil.startOfTheDay(dateFrom));
		params.add(DateUtil.startOfTheDay(dateFrom));
		params.add(DateUtil.addDate(dateTo, 1));
		if(StringUtils.isNotBlank(isCheckDept)){
			sql += " and isCheckDept=? ";
			params.add(isCheckDept);
		}
		sql += " ) and tct.IsAddAllInfo='1' and tc.ConfirmBegin is not null ";
		if(StringUtils.isNotBlank(page.getPageOrderBy()) && !"proportion".equals(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, params.toArray());
	}
	
	public Page<Map<String,Object>> goJqGridView(Page<Map<String, Object>> page, String type, Date dateFrom, Date dateTo, String isCheckDept){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( ";
		if("1".equals(type) || "2".equals(type) || "3".equals(type) || "4".equals(type) || "5".equals(type)){
			sql += " select a.*,tc.address,td2.Desc1 department2Descr "
				 + " from ( "
				 + " 	select a.CustCode,te.Department2,count(*) progCheckNum,min(tppc.Date) minProgCheckDate,max(tppc.Date) maxProgCheckDate "
				 + " 	from ( "
				 + " 		select CustCode "
				 + " 		from tPrjProgCheck "
				 + " 		where Date >= ? and Date < ? ";				
			params.add(DateUtil.startOfTheDay(dateFrom));
			params.add(DateUtil.addDate(dateTo, 1));
			if(StringUtils.isNotBlank(isCheckDept)){
				sql += " and isCheckDept = ? ";
				params.add(isCheckDept);
			}
			sql += " 		group by CustCode "
				 + " 		having count(*)"+("5".equals(type)?">":"=")+("5".equals(type)?"4":type)//类型5查找4次以上数据拼接sql ">=4" 其他拼接sql "="+type
				 + " 	) a "
				 + " 	left join tPrjProgCheck tppc on tppc.CustCode = a.CustCode "
				 + " 	left join tEmployee te on te.Number = tppc.AppCZY "
				 + " 	where tppc.Date >= ? and tppc.Date < ? ";			
			params.add(DateUtil.startOfTheDay(dateFrom));
			params.add(DateUtil.addDate(dateTo, 1));
			if(StringUtils.isNotBlank(isCheckDept)){
				sql += " and isCheckDept = ? ";
				params.add(isCheckDept);
			}
			sql += "	group by a.CustCode,te.Department2 "
				 + " ) a "
				 + " left join tCustomer tc on tc.Code = a.CustCode "
				 + " left join tDepartment2 td2 on td2.Code = a.Department2 ";
		}else{
			sql += " select tc.address,td2.Desc1 department2Descr,0 progCheckNum,tc.confirmBegin,a.note prjItemDescr "
				 + " from tCustomer tc "
				 + " left join tEmployee te on tc.ProjectMan = te.Number "
				 + " left join tDepartment2 td2 on td2.Code = te.Department2 "
				 + " left join tCustType tct on tct.Code = tc.CustType "
				 + " left join ( "
				 + " 		select  p.CustCode ,n.note ,p.PRJITEM "
				 + " 		from( "
				 + " 			select m.CustCode,( "
				 + " 				select top 1 PRJITEM " 
				 + " 				from  tPrjProg q "   
				 + " 				where   q.CustCode = m.CustCode "   
				 + " 				and BeginDate < getdate() and q.BeginDate = (select max(BeginDate) from tPrjProg r where r.CustCode=m.CustCode and r.BeginDate < getdate())"
				 + " 			) PRJITEM "       
				 + " 			from  tPrjProg m "   
				 + " 			group by  m.CustCode "
				 + " 		) p "
				 + " 		left join dbo.tXTDM n on p.PRJITEM = n.CBM and n.ID = 'PRJITEM' "
				 + " 		LEFT JOIN dbo.tPrjItem1 pi1 ON pi1.Code = p.PRJITEM "
				 + " ) a on a.CustCode = tc.Code "
				 + " where (not  ( tc.SignDate > ? or isnull(tc.EndDate, getdate()) < ? )) "
				 + " and (tc.Status='4' or (tc.Status='5' and (tc.EndCode='3' or tc.EndCode='4'))) "
				 + " and tct.IsAddAllInfo='1' and tc.ConfirmBegin is not null and not exists( "
				 + " 	select 1 from tPrjProgCheck where  Date >= ? and Date < ?  and CustCode = tc.Code ";
			params.add(DateUtil.startOfTheDay(dateTo));
			params.add(DateUtil.startOfTheDay(dateFrom));
			params.add(DateUtil.startOfTheDay(dateFrom));
			params.add(DateUtil.addDate(dateTo, 1));
			if(StringUtils.isNotBlank(isCheckDept)){
				sql += " and isCheckDept=? ";
				params.add(isCheckDept);
			}
			sql += " ) ";
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		
		return this.findPageBySql(page, sql, params.toArray());
	}
}
