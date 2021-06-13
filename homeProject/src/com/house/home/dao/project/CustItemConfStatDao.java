package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;

@SuppressWarnings("serial")
@Repository
public class CustItemConfStatDao extends BaseDao {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Date dateFrom, Date dateTo,
			Date sdDateFrom ,Date sdDateTo, Date nsDateFrom, Date nsDateTo,String mainBusinessMan, String custType){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( "
				   + "		select tc.Address,tct.Desc1 custTypeDescr,te1.NameChi designCZYDescr,td2.Desc1 department2Descr,dbo.fGetEmpNameChi(tc.Code,'34') mainCZYDescr, "
				   + " 		case when a.ConfirmDate is not null and (a.EndDate is null or a.ConfirmDate <= a.EndDate) " +
				   "		then '是' else '否' end first,a.ConfirmDate firstConDate,b.ConfirmDate secondConDate, "
				   + " 		case when ((b.PrjItem is not null and b.ConfirmDate is not null and (b.EndDate is null or b.ConfirmDate <= b.EndDate) ) " +
				   "				or (b.PrjItem is null and exists(" +
						   "			select 1 from tCustWorkerApp where WorkType12='03' and CustCode = tc.Code and Expired='F') " +
						   "			and (select top 1 appDate from tCustWorkerApp where WorkType12='03' and CustCode = tc.Code " +
						   "					and Expired='F') >= b.ConfirmDate)" +
						   "		) and b.ConfirmDate is not null then '是' else '否' end second, "
				   + " 		case when c.ConfirmDate is not null and (c.EndDate is null  or c.ConfirmDate <= c.EndDate) then '是' else '否' end third, "
				   + " 		case when isnull(d.alreadyConfCount,0)>=2 and d.ConfirmDate is not null and (d.EndDate is null or d.ConfirmDate < d.EndDate) then '是' else '否' end isAllConfirm,d.ConfirmDate "
				   + "      ,sd.confirmdate SDConfirmdate,ns.confirmdate  NSConfirmdate "
				   + " 		from tCustomer tc "
				   + " 		left join tEmployee te1 on te1.Number = tc.DesignMan "
				   + " 		left join tDepartment2 td2 on td2.Code = te1.Department2 "
				   + " 		left join tCusttype tct on tct.Code = tc.CustType "
				   + " 		left join ( "/*一阶段确认*/
				   + " 			select a.CustCode,a.ConfirmDate,tpp.PrjItem,tpp.ConfirmDate enddate	"		
				   + "			from ( "
				   + "				select CustCode,ConfirmDate "  				
				   + "				from tCustItemConfDate "			
				   + "				where ItemTimeCode='01' "  			
				   + "		 	) a "    			
				   + " 	 		left join tPrjProg tpp on a.CustCode = tpp.CustCode and exists(select 1 from tConfItemTime where endPrjItem=tpp.PrjItem and Code='01') "
				   + " 		) a on a.CustCode = tc.Code "
				   + " 		left join ( "/*二阶段确认*/
				   + " 			select a.CustCode,a.ConfirmDate,tpp.PrjItem,tpp.ConfirmDate EndDate	"		
				   + "			from ( "
				   + "				select CustCode,ConfirmDate "  				
				   + "				from tCustItemConfDate "			
				   + "				where ItemTimeCode='02' "  			
				   + "		 	) a "    			
				   + " 	 		left join tPrjProg tpp on a.CustCode = tpp.CustCode and exists(select 1 from tConfItemTime where endPrjItem=tpp.PrjItem and Code='02') "
				   + " 		) b on b.CustCode = tc.Code "
				   + " 		left join ( "/*三阶段确认*/
				   + " 			select a.CustCode,a.ConfirmDate,tpp.PrjItem,tpp.ConfirmDate EndDate	"		
				   + "			from ( "
				   + "				select CustCode,ConfirmDate "  				
				   + "				from tCustItemConfDate "			
				   + "				where ItemTimeCode='03' "  			
				   + "		 	) a "    			
				   + " 	 		left join tPrjProg tpp on a.CustCode = tpp.CustCode and exists(select 1 from tConfItemTime where PrjItem=tpp.PrjItem and Code='03') "
				   + " 		) c on c.CustCode = tc.Code "
				   + " 		left join ( "//一次确认全部
				   + "			select a.alreadyConfCount,a.CustCode,tpp.PrjItem,tpp.ConfirmDate EndDate,a.ConfirmDate "
				   + "			from ( "
				   + " 				select count(*) alreadyConfCount,CustCode,max(ConfirmDate) ConfirmDate from tCustItemConfDate group by CustCode "	
				   + " 			) a "
				   + " 			left join tPrjProg tpp on tpp.CustCode = a.CustCode and exists(select 1 from tConfItemTime where EndPrjItem=tpp.PrjItem and Code='01') "
				   + "		) d on d.CustCode = tc.Code "
				   + "  left join (select tpp.CustCode,tpp.ConfirmDate from tPrjProg tpp where PrjItem='5') sd on tc.Code = sd.CustCode "
				   + "  left join (select tpp.CustCode,tpp.ConfirmDate from tPrjProg tpp where PrjItem='8') ns on tc.Code = ns.CustCode ";
	    if ((sdDateFrom != null)||(sdDateTo != null)|| (nsDateFrom != null)||(nsDateTo != null)){
		   sql += " where 1=1 ";
	    }else{
	       sql +=  " where isnull(d.alreadyConfCount,0)>=(select count(1) qty from tConfItemTime where expired = 'F') ";
	    }			  
		if(dateFrom != null){
			sql += " and d.ConfirmDate >= ? ";
			params.add(DateUtil.startOfTheDay(dateFrom));
		}
		if(dateTo != null){
			sql += " and d.ConfirmDate < ? ";
			params.add(DateUtil.addDate(dateTo, 1));
		}
		if(sdDateFrom != null){
			sql += " and sd.ConfirmDate >= ? ";
			params.add(DateUtil.startOfTheDay(sdDateFrom));
		}
		if(sdDateTo != null){
			sql += " and sd.ConfirmDate < ? ";
			params.add(DateUtil.addDate(sdDateTo, 1));
		}
		if(nsDateFrom != null){
			sql += " and ns.ConfirmDate >= ? ";
			params.add(DateUtil.startOfTheDay(nsDateFrom));
		}
		if(nsDateTo != null){
			sql += " and ns.ConfirmDate < ? ";
			params.add(DateUtil.addDate(nsDateTo, 1));
		}
		if(StringUtils.isNotBlank(custType)){
			sql += " and tc.custtype in (" + custType + ")";
		}
  
    	if (StringUtils.isNotBlank(mainBusinessMan)) {
    		sql +=" and exists (select 1 from tCustStakeholder where CustCode=tc.Code and Role='34' and EmpCode='"+mainBusinessMan+"')";
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}


}

