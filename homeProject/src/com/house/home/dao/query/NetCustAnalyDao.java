package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.design.Customer;

@Repository
@SuppressWarnings("serial")
public class NetCustAnalyDao extends BaseDao{
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  exec pNetCustAnaly ?,?,?,?,?,?,?,?";
		list.add(customer.getDateFrom());
		list.add(customer.getDateTo());
		list.add(customer.getStatistcsMethod());
		list.add(customer.getCustKind());
		list.add(customer.getDepartment1());
		list.add(customer.getDepartment2());
		list.add(customer.getCustType());
		list.add(customer.getCzybh());
		page.setResult(findListBySql(sql, list.toArray())); 		
		page.setTotalCount(page.getResult().size());
		return page;
	}
	
	public String getDefaultCustType() {
		String sql = " declare @default nvarchar(400)='' " +
				"select @default+=Code+',' from tCustType where IsPartDecorate in ('0','1') " +
				"select @default custType";
		return findListBySql(sql, new Object[]{}).get(0).get("custType").toString();
	}

	public Page<Map<String, Object>> findManPageBySql_month(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from (select case when b.CustResStat not in ('0','2','4') and " +
					 " a.NewBusiness=b.BusinessMan then '是' else '否' end isDispatch, ";
		if(customer.getDateFrom() != null && customer.getDateTo() != null){
			sql += " case when d.SetDate>=? and d.SetDate<? and b.CustResStat not in ('0','2','4') and " + 
				   " d.SignDate>=? and d.SignDate<? and a.NewBusiness=b.BusinessMan then d.ContractFee else 0 end contractFee, " +
				   " case when b.CustResStat not in ('0','2','4') and d.VisitDate>=? and d.VisitDate<? and " +
				   " a.NewBusiness=b.BusinessMan then d.visitdate else null end visitDate, " +
        		   " case when d.SetDate>=? and d.SetDate<? and b.CustResStat not in ('0','2','4') and " +
        		   " a.NewBusiness=b.BusinessMan then d.setdate else null end setDate, " +
        		   " case when d.SetDate>=? and d.SetDate<? and b.CustResStat not in ('0','2','4') and " +
        		   " d.SignDate>=? and d.SignDate<? and a.NewBusiness=b.BusinessMan then d.signdate else null end signDate, ";
			params.add(customer.getDateFrom());
			params.add(DateUtil.addDay(customer.getDateTo()));
			params.add(customer.getDateFrom());
			params.add(DateUtil.addDay(customer.getDateTo()));
			params.add(customer.getDateFrom());
			params.add(DateUtil.addDay(customer.getDateTo()));
			params.add(customer.getDateFrom());
			params.add(DateUtil.addDay(customer.getDateTo()));
			params.add(customer.getDateFrom());
			params.add(DateUtil.addDay(customer.getDateTo()));
			params.add(customer.getDateFrom());
			params.add(DateUtil.addDay(customer.getDateTo()));
		}
		  sql += " d.Code custCode,b.Address,x1.NOTE custResStatDescr,e1.NameChi businessManDescr,e2.NameChi crtCzyDescr, " +
				 " b.DispatchDate,d.Address custAddress,x2.NOTE CustStatusDescr "+
				 " from tResrCustBusinessTran a "+
				 " left join tResrCust b on a.ResrCustCode=b.Code "+
				 " left join tResrCustMapper c on b.Code=c.ResrCustCode " +
				 " left join tCustomer d on c.CustCode=d.Code "+
				 " left join tCustNetCnl f on b.NetChanel=f.Code "+
				 " left join tXTDM x1 on x1.CBM = b.CustResStat and x1.Id = 'CUSTRESSTAT' "+
				 " left join tXTDM x2 on d.Status = x2.CBM and x2.Id = 'CUSTOMERSTATUS' "+
				 " left join tEmployee e1 on e1.Number = a.newBusiness "+
				 " left join tEmployee e2 on e2.Number = b.CrtCZY "+
				 " where f.ChannelType='0' and a.Type='0' ";
		if(StringUtils.isNotBlank(customer.getCustKind())){
			sql += " and b.constructType in (select item from dbo.fStrToTable( ? , ',')) ";
			params.add(customer.getCustKind());
		}
		if(StringUtils.isNotBlank(customer.getCustType())){
			sql += " and((d.Code is not null and d.CustType  in (select item from dbo.fStrToTable(?, ','))) or d.Code is null) ";
			params.add(customer.getCustType());
		}
		if(customer.getDateFrom() != null){
			sql += " and b.dispatchdate >= ? ";
			params.add(customer.getDateFrom());
		}
		if(customer.getDateTo() != null){
			sql += " and b.dispatchdate < ? ";
			params.add(DateUtil.addDay(customer.getDateTo()));
		}
		if(StringUtils.isNotBlank(customer.getCrtCzy())){
			sql += " and b.CrtCZY = ? ";
			params.add(customer.getCrtCzy());
		}
		if(StringUtils.isNotBlank(customer.getBusinessMan())){
			sql += " and a.NewBusiness = ? ";
			params.add(customer.getBusinessMan());
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String, Object>> findNetChanelPageBySql_month(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from (select d.Code custCode,b.Address,x1.NOTE custResStatDescr, "+
					 " e1.NameChi businessManDescr,e2.NameChi crtCzyDescr, "+
					 " b.DispatchDate,d.Address custAddress,x2.NOTE CustStatusDescr ";
		if(customer.getDateFrom() != null && customer.getDateTo() != null){
			sql += " ,case when d.SetDate>=? and d.SetDate<? and b.CustResStat not in ('0','2','4') and " + 
				   " d.SignDate>=? and d.SignDate<? then d.ContractFee else 0 end contractFee, " +
				   " case when b.CustResStat not in ('0','2','4') and d.VisitDate>=? and " +
				   " d.VisitDate<? then d.VisitDate else null end VisitDate, "+
				   " case when d.SetDate>=? and d.SetDate<? and " +
				   " b.CustResStat not in ('0','2','4') then d.SetDate else null end SetDate, "+
				   " case when d.SetDate>=? and d.SetDate<? and " +
				   " b.CustResStat not in ('0','2','4') and d.SignDate>=? and d.SignDate<? then SignDate else null end SignDate ";
			params.add(customer.getDateFrom());
			params.add(DateUtil.addDay(customer.getDateTo()));
			params.add(customer.getDateFrom());
			params.add(DateUtil.addDay(customer.getDateTo()));
			params.add(customer.getDateFrom());
			params.add(DateUtil.addDay(customer.getDateTo()));
			params.add(customer.getDateFrom());
			params.add(DateUtil.addDay(customer.getDateTo()));
			params.add(customer.getDateFrom());
			params.add(DateUtil.addDay(customer.getDateTo()));
			params.add(customer.getDateFrom());
			params.add(DateUtil.addDay(customer.getDateTo()));
		}
			sql +=	 " from tCustNetCnl a "+
				 	 " left join tResrCust b on a.Code=b.NetChanel  "+
					 " left join tResrCustMapper c on b.Code=c.ResrCustCode "+
					 " left join tCustomer d on c.CustCode=d.Code "+
					 " left join tEmployee e1 on e1.Number = b.BusinessMan "+
					 " left join tEmployee e2 on b.CrtCZY=e2.Number "+
					 " left join tXTDM x1 on x1.CBM = b.CustResStat and x1.Id = 'CUSTRESSTAT' "+
					 " left join tXTDM x2 on d.Status = x2.CBM and x2.Id = 'CUSTOMERSTATUS' "+
					 " where  a.ChannelType='0' ";//b.CustResStat not in ('0','2','4') and b.BusinessMan<>'' and
		if(customer.getDateFrom() != null){
			sql += " and b.dispatchdate >= ? ";
			params.add(customer.getDateFrom());
		}
		if(customer.getDateTo() != null){
			sql += " and b.dispatchdate < ? ";
			params.add(DateUtil.addDay(customer.getDateTo()));
		}
		if(StringUtils.isNotBlank(customer.getCustKind())){
			sql += " and b.constructType  in (select item from dbo.fStrToTable( ? , ',' ))";
			params.add(customer.getCustKind());
		}
		if(StringUtils.isNotBlank(customer.getCustType())){
			sql += " and ((d.Code is not null and (d.CustType  in (select item from dbo.fStrToTable( ? , ',')))) or d.Code is null) ";
			params.add(customer.getCustType());
		}
		if(StringUtils.isNotBlank(customer.getDepartment1())){
			sql += " and e2.Department1 in (select item from dbo.fStrToTable(?, ',')) ";
			params.add(customer.getDepartment1());
		}
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql += " and e2.Department2 in (select item from dbo.fStrToTable(?, ',')) ";
			params.add(customer.getDepartment2());
		}
		if(StringUtils.isNotBlank(customer.getNetChanel())){
			sql += " and b.NetChanel = ? ";
			params.add(customer.getNetChanel());
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String, Object>> findManAndChanelPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from (select d.Code custCode,b.Address,x1.NOTE custresstatdescr,e1.NameChi businessmandescr, " +
					 " e2.NameChi crtczydescr,b.DispatchDate,d.Address custaddress,x2.NOTE CustStatusDescr " ;
		if(customer.getDateFrom() != null && customer.getDateTo() != null){
			sql += " ,case when d.SignDate>=? and d.SignDate<? and a.NewBusiness=b.BusinessMan " + 
				   " then d.ContractFee else 0 end/BusinessManNum ContractFee, " +
				   " case when d.VisitDate>=? and d.VisitDate<? and " +
				   " a.NewBusiness=b.BusinessMan then VisitDate else null end VisitDate, " +
				   " case when d.SetDate>=? and d.SetDate<? and " +
				   " a.NewBusiness=b.BusinessMan then SetDate else null end SetDate, " +
				   " case when d.SignDate>=? and d.SignDate<? and " +
				   " a.NewBusiness=b.BusinessMan then SignDate else null end SignDate ";
			params.add(customer.getDateFrom());
			params.add(customer.getDateTo());
			params.add(customer.getDateFrom());
			params.add(customer.getDateTo());
			params.add(customer.getDateFrom());
			params.add(customer.getDateTo());
			params.add(customer.getDateFrom());
			params.add(customer.getDateTo());
		}
			sql +=	 " from tResrCustBusinessTran a "+
					 " left join tResrCust b on a.ResrCustCode=b.Code "+
					 " left join tResrCustMapper c on b.Code=c.ResrCustCode "+
					 " left join tCustomer d on c.CustCode=d.Code "+
					 " left join tXTDM x1 on x1.CBM = b.CustResStat and x1.Id = 'CUSTRESSTAT' "+
					 " left join tXTDM x2 on d.Status = x2.CBM and x2.Id = 'CUSTOMERSTATUS' "+
					 " left join tEmployee e1 on e1.Number = a.NewBusiness "+
					 " left join tEmployee e2 on e2.Number = b.CrtCZY "+
					 " left join ( "+
					 " 	  select case when count(*)=0 then 1 else cast(count(*) as money) end BusinessManNum,CustCode" +
					 " 	  from tCustStakeholder where Role='01'  group by CustCode  "+
					 " )h on d.Code =h.CustCode "+
					 " left join tCustNetCnl f on b.NetChanel=f.Code"+
					 " where f.ChannelType='0' and a.Type='0' and isnull(b.NetChanel,'')<>'' ";
		if(customer.getDateFrom() != null){
			sql += " and b.DispatchDate >= ? ";
			params.add(customer.getDateFrom());
		}
		if(customer.getDateTo() != null){
			sql += " and b.DispatchDate < dateadd(day,1,?) ";
			params.add(customer.getDateTo());
		}
		if(StringUtils.isNotBlank(customer.getCustKind())){
			sql += " and b.constructType  in (select item from dbo.fStrToTable( ? , ',' ))";
			params.add(customer.getCustKind());
		}
		if(StringUtils.isNotBlank(customer.getCustType())){
			sql += " and ((d.Code is not null and (d.CustType  in (select item from dbo.fStrToTable( ? , ',')))) or d.Code is null) ";
			params.add(customer.getCustType());
		}
		if(StringUtils.isNotBlank(customer.getNetChanel())){
			if(customer.getNetChanel().equals("0")){
				sql += " ";
			}else{
				sql += " and b.NetChanel = ? ";
				params.add(customer.getNetChanel());
			}
		}
		if(StringUtils.isNotBlank(customer.getBusinessMan())){
			sql += " and a.NewBusiness = ? ";
			params.add(customer.getBusinessMan());
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String, Object>> findManPageBySql_History(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from (select isnull(e5.NameChi,e1.NameChi)CrtCZY," +
					 " isnull(e2.NameChi,e1.NameChi)BusinessManDescr,a.Code custCode, " +
					 " a.Address custAddress,x2.NOTE CustStatusDescr,e3.NameChi DesignManDescr, "+
					 " e4.NameChi AgainManDescr ";
		if(customer.getDateFrom() != null && customer.getDateTo() != null){
			sql += " ,case when a.SignDate>=? and a.SignDate<? and " +
				   " (c.Code is null or c.DispatchDate<?) then a.ContractFee else 0 end ContractFee, " +
				   " case when (c.Code is null or c.DispatchDate<?) and a.VisitDate>=? " +
				   " and a.VisitDate<? then a.VisitDate else null end VisitDate, " +
				   " case when (c.Code is null or c.DispatchDate<?) and a.SetDate>=? and " +
				   " a.SetDate<? then SetDate else null end SetDate, " +
				   " case when (c.Code is null or c.DispatchDate<?) and " +
				   " a.SignDate>=? and a.SignDate<? then a.SignDate else null end SignDate";
			params.add(customer.getDateFrom());
			params.add(customer.getDateTo());
			params.add(customer.getDateFrom());
			params.add(customer.getDateFrom());
			params.add(customer.getDateFrom());
			params.add(customer.getDateTo());
			params.add(customer.getDateFrom());
			params.add(customer.getDateFrom());
			params.add(customer.getDateTo());
			params.add(customer.getDateFrom());
			params.add(customer.getDateFrom());
			params.add(customer.getDateTo());
		}
			sql +=  " from tCustomer a "+
					" left join tResrCustMapper b on a.Code=b.CustCode "+
					" left join tResrCust c on c.Code=b.ResrCustCode "+
					" left join tEmployee e1 on e1.Number = a.BusinessMan "+
					" left join tEmployee e2 on e2.Number = c.BusinessMan "+
					" left join tEmployee e3 on e3.Number = a.DesignMan "+
					" left join tEmployee e4 on e4.Number = a.AgainMan "+
					" left join tEmployee e5 on e5.Number = c.CrtCZY "+
					" left join tXTDM x2 on a.Status = x2.CBM and x2.Id = 'CUSTOMERSTATUS' "+
					" where 1=1 ";
			if(customer.getDateFrom() != null && customer.getDateTo() != null){
				sql += " and (" +
					   " (case when (c.Code is null or c.DispatchDate<?) and a.VisitDate>=? " +
					   " and a.VisitDate<? then a.VisitDate else null end)>0 "+
					   " or (case when (c.Code is null or c.DispatchDate<?) and a.SetDate>=? and " +
					   " a.SetDate<? then SetDate else null end)>0 " +
					   " or ( case when (c.Code is null or c.DispatchDate<?) and " +
					   " a.SignDate>=? and a.SignDate<? then a.SignDate else null end)>0 "+
					   " or (case when a.SignDate>=? and a.SignDate<? and " +
					   " (c.Code is null or c.DispatchDate<?) then a.ContractFee else 0 end)>0" +
					   " )";
				params.add(customer.getDateFrom());
				params.add(customer.getDateFrom());
				params.add(customer.getDateTo());
				params.add(customer.getDateFrom());
				params.add(customer.getDateFrom());
				params.add(customer.getDateTo());
				params.add(customer.getDateFrom());
				params.add(customer.getDateFrom());
				params.add(customer.getDateTo());
				params.add(customer.getDateFrom());
				params.add(customer.getDateTo());
				params.add(customer.getDateFrom());
			}
		if(StringUtils.isNotBlank(customer.getCustKind())){
			sql +=  " and c.constructType in (select item from dbo.fStrToTable( ? , ',' ))";
			params.add(customer.getCustKind());
		}
		if(StringUtils.isNotBlank(customer.getCustType())){
			sql +=  " and a.CustType in (select item from dbo.fStrToTable(?, ',')) ";
			params.add(customer.getCustType());
		}
		if(StringUtils.isNotBlank(customer.getCrtCzy())){
			sql +=  " and c.CrtCZY = ? ";
			params.add(customer.getCrtCzy());
		}
		if(StringUtils.isNotBlank(customer.getBusinessMan())){
			sql += " and c.BusinessMan = ? ";
			params.add(customer.getBusinessMan());
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String, Object>> findChanelPageBySql_history(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from (select c.custCode,c.custAddress,c.CustStatusDescr,c.businessManDescr,c.DesignManDescr, " +
					 " c.AgainManDescr,c.VisitDate,c.SetDate,c.SignDate,c.ContractFee_history ContractFee "+
					 " from tCustNetCnl a "+
					 " left join ( "+
					 " 	  select in_c.NetChanel, in_c.Code custCode,in_c.Address custAddress," +
					 "	  x2.NOTE CustStatusDescr,e3.NameChi DesignManDescr,e4.NameChi AgainManDescr," +
					 "	  in_e.NameChi businessManDescr ";
		if(customer.getDateFrom() != null && customer.getDateTo() != null){
			sql +=  " ,case when (in_a.Code is null or in_a.DispatchDate<?) and in_c.VisitDate>=? and in_c.VisitDate<? then VisitDate else null end VisitDate, "+
					" case when (in_a.Code is null or in_a.DispatchDate<?) and in_c.SetDate>=? and in_c.SetDate<? then SetDate else null end SetDate, "+
					" case when (in_a.Code is null or in_a.DispatchDate<?) and in_c.SignDate>=? and in_c.SignDate<? then SignDate else null end SignDate, "+
					" case when in_c.SignDate>=? and in_c.SignDate<? and (in_a.Code is null or in_a.DispatchDate<?) then in_c.ContractFee else 0 end ContractFee_history ";
			params.add(customer.getDateFrom());
			params.add(customer.getDateFrom());
			params.add(customer.getDateTo());
			params.add(customer.getDateFrom());
			params.add(customer.getDateFrom());
			params.add(customer.getDateTo());
			params.add(customer.getDateFrom());
			params.add(customer.getDateFrom());
			params.add(customer.getDateTo());
			params.add(customer.getDateFrom());
			params.add(customer.getDateTo());
			params.add(customer.getDateFrom());
		}
			sql +=  " from tCustomer in_c "+
					" left join tResrCustMapper in_b on in_c.Code=in_b.CustCode "+
					" left join tResrCust in_a on in_a.Code=in_b.ResrCustCode "+
					" left join tEmployee in_d on in_a.CrtCZY=in_d.Number "+
					" left join tEmployee in_e on in_c.BusinessMan=in_e.Number "+
					" left join tEmployee e3 on e3.Number = in_c.DesignMan "+
					" left join tEmployee e4 on e4.Number = in_c.AgainMan "+
					" left join tXTDM x2 on in_c.Status = x2.CBM and x2.Id = 'CUSTOMERSTATUS' "+
					" where 1=1 ";
			if(StringUtils.isNotBlank(customer.getCustKind())){
				sql += " and in_a.constructType in (select item from dbo.fStrToTable(?, ','))";
				params.add(customer.getCustKind());
			}
 			if(StringUtils.isNotBlank(customer.getCustType())){
				sql += " and in_c.CustType in (select item from dbo.fStrToTable(?, ',')) ";
				params.add(customer.getCustType());
			}
			if(StringUtils.isNotBlank(customer.getDepartment1())){
				sql += " and in_d.Department1 in (select item from dbo.fStrToTable(?, ',')) ";
				params.add(customer.getDepartment1());
			}
			if(StringUtils.isNotBlank(customer.getDepartment2())){
				sql += " and in_d.Department2 in (select item from dbo.fStrToTable(?, ',')) ";
				params.add(customer.getDepartment2());
			}
			sql +=  " )c on a.Code=c.NetChanel "+
					" where a.ChannelType='0' "+
					" and isnull(c.VisitDate,0)+isnull(c.SetDate,0)+isnull(c.SignDate,0)>0 ";
			if(StringUtils.isNotBlank(customer.getNetChanel())){
				sql += " and c.NetChanel = ? ";
				params.add(customer.getNetChanel());
			}
			if(StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " ) a ";
			}
		return this.findPageBySql(page, sql, params.toArray());
	}

}
