package com.house.home.dao.query;

import java.sql.Timestamp;
import java.util.ArrayList;
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
public class SecondPayRecvCustDao extends BaseDao {
	
	/**
	 * 分页信息
	 * 
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql ="";
		if("2".equals(customer.getStatistcsMethod())){
			sql += "select * from (";
		}                                                                                           
		sql += "select * from (select a.Code CustCode,a.DocumentNo,a.Address,b.Desc1 CustTypeDescr,d.NOTE StatusDescr, "
				+"a.ContractFee,a.DesignFee,isnull(e.HasPay,0)HasPay,isnull(c.FirstPay,a.FirstPay)FirstPay,a.SecondPay,c.SecondPayDate, "
				+"dbo.fGetEmpNameChi(a.code,'01') BusinessManDescr,dbo.fGetEmpNameChi(a.code,'00') DesignManDescr, "
				+"dbo.fGetEmpNameChi(a.code,'24') AgainManDescr,dbo.fGetEmpNameChi(a.code,'63') SceneDesignMan,a.ConfirmBegin, "
				+"f.NOTE PayTypeDescr,a.ThirdPay,a.FourPay,g.qqConfirmDate,h.sdConfirmDate,l.Descr RegionDescr," 
				+"case when i.DrawNoChg='1' then i.DrawNoChgDate else j.ConfirmDate end ChgConfirmDate, "
				+"case when c.SecondPayDate>case when i.DrawNoChg='1' then i.DrawNoChgDate else j.ConfirmDate end then c.SecondPayDate " 
				+"else case when i.DrawNoChg='1' then i.DrawNoChgDate else j.ConfirmDate end end MaxDate "
				+"from tCustomer a  "
				+"left join tCusttype b on a.CustType=b.Code "
				+"left join ( "
				+"	select min(in_g.SecondPayDate)SecondPayDate,in_g.CustCode,in_g.FirstPay "
				+"	from ( "
				+"		select in_d.AddDate SecondPayDate,in_d.CustCode ,in_d.TotalAmount,in_e.SecondPay, "
				+"		in_e.FirstPay+isnull(case when in_f.DesignFeeType = '2' then in_e.StdDesignFee  else in_e.DesignFee  end, 0)*  "
				+"		 ( case when exists ( select 1 from tPayRuleDetail where No = in_f.No and IsRcvDesignFee = '1' and Expired = 'F' )then 1 else 0 end ) firstPay "
				+"		from ( "
				+"			select in_a.Amount+isnull((select sum(Amount) from tCustPay in_c where in_c.PK<in_a.pk and in_c.CustCode=in_a.CustCode),0)TotalAmount, "
				+"				in_a.CustCode,in_a.AddDate "
				+"			from tCustPay in_a "
				+"		)in_d  "
				+"		left join tCustomer in_e on in_d.CustCode=in_e.Code "
				+"		left join tPayRule in_f on in_f.CustType=in_e.Custtype and in_f.PayType=in_e.PayType "
				+"		left join tCustType in_g on in_e.CustType=in_g.Code "
				+"      where in_e.Status='4' and in_g.IsPartDecorate='0' and in_e.ConstructStatus<>'7'  "
				+"	)in_g "
				+"	where in_g.TotalAmount>=in_g.FirstPay+in_g.SecondPay "
				+"	group by in_g.CustCode,in_g.FirstPay "
				+")c on a.Code=c.CustCode "
				+"left join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
				+"left join ( "
				+"	select CustCode,isnull(sum(Amount),0) HasPay  "
				+"	from tCustPay  "
				+"	group by CustCode "
				+")e on e.CustCode = a.Code "
				+"left join tXTDM f on a.PayType=f.CBM and f.ID='TIMEPAYTYPE' "
				+"left join (" 
				+"	select max(ConfirmDate)qqConfirmDate,in_a.CustCode " 
				+"	from tPrjProg in_a " 
				+"	where in_a.PrjItem='3' " 
				+"	group by in_a.CustCode " 
				+") g on a.Code=g.CustCode "
				+"left join (" 
				+"	select max(ConfirmDate)sdConfirmDate,in_a.CustCode " 
				+"	from tPrjProg in_a " 
				+"	where in_a.PrjItem='5' " 
				+"	group by in_a.CustCode " 
				+") h on a.Code=h.CustCode "
				+"left join tDesignPicPrg i on i.CustCode=a.Code  "
				+"left join (" 
				+"	select min(ConfirmDate) ConfirmDate,CustCode " 
				+"	from tCustDoc " 
				+"	where Type='2' " 
				+"	group by CustCode " 
				+")j on j.CustCode=a.code "
				+"left join tBuilder k on k.Code=a.BuilderCode " 
				+"left join tRegion l on l.code=k.RegionCode " 
				+"where a.Status='4' and b.IsPartDecorate='0' and ConstructStatus<>'7' "; 
		
		if("1".equals(customer.getStatistcsMethod())){
			if (customer.getDateFrom()!=null) {
				sql += " and c.SecondPayDate >= ? ";
				list.add(customer.getDateFrom());
			}
			if(customer.getDateTo()!=null){
				sql += " and c.SecondPayDate <= ? ";
				list.add(new Timestamp(DateUtil.endOfTheDay(customer.getDateTo()).getTime()));
			}
			if (customer.getConfirmBeginFrom()!=null) {
				sql += " and a.ConfirmBegin >= ? ";
				list.add(customer.getConfirmBeginFrom());
			}
			if(customer.getConfirmBeginTo()!=null){
				sql += " and a.ConfirmBegin <= ? ";
				list.add(new Timestamp(DateUtil.endOfTheDay(customer.getConfirmBeginTo()).getTime()));
			}
			if (customer.getConfirmWallDateFrom()!=null) {
				sql += " and g.qqConfirmDate >= ? ";
				list.add(customer.getConfirmWallDateFrom());
			}
			if(customer.getConfirmWallDateTo()!=null){
				sql += " and g.qqConfirmDate <= ? ";
				list.add(new Timestamp(DateUtil.endOfTheDay(customer.getConfirmWallDateTo()).getTime()));
			}
			if (customer.getConfirmWaterDateFrom()!=null) {
				sql += " and h.sdConfirmDate >= ? ";
				list.add(customer.getConfirmWaterDateFrom());
			}
			if(customer.getConfirmWaterDateTo()!=null){
				sql += " and h.sdConfirmDate <= ? ";
				list.add(new Timestamp(DateUtil.endOfTheDay(customer.getConfirmWaterDateTo()).getTime()));
			}
			if(customer.getChangeDateFrom()!=null && customer.getChangeDateTo()!=null){
				sql+="and ( (i.DrawNoChg='1' and i.DrawNoChgDate>=? and i.DrawNoChgDate<dateadd(day,1,?)) "
						+" or (isnull(i.DrawNoChg,'')<>'1' and j.ConfirmDate>=? and j.ConfirmDate<dateadd(day,1,?)) )"; 
				list.add(customer.getChangeDateFrom());
				list.add(customer.getChangeDateTo());
				list.add(customer.getChangeDateFrom());
				list.add(customer.getChangeDateTo());
			}else if(customer.getChangeDateFrom()!=null && customer.getChangeDateTo()==null){
				sql+="and ( (i.DrawNoChg='1' and i.DrawNoChgDate>=? ) "
						+" or (isnull(i.DrawNoChg,'')<>'1' and j.ConfirmDate>=? ) )"; 
				list.add(customer.getChangeDateFrom());
				list.add(customer.getChangeDateFrom());
			}else if(customer.getChangeDateFrom()==null && customer.getChangeDateTo()!=null){
				sql+="and ( (i.DrawNoChg='1' and i.DrawNoChgDate<dateadd(day,1,?)) "
						+" or (isnull(i.DrawNoChg,'')<>'1' and  j.ConfirmDate<dateadd(day,1,?)) )"; 
				list.add(customer.getChangeDateTo());
				list.add(customer.getChangeDateTo());
			}
		}
		
		if("2".equals(customer.getStatistcsMethod())){
			sql += ")a where 1=1 " ;
			if (customer.getDateFrom()!=null) {
				sql += " and a.MaxDate >= ? ";
				list.add(customer.getDateFrom());
			}
			if(customer.getDateTo()!=null){
				sql += " and and a.MaxDate <= ? ";
				list.add(new Timestamp(DateUtil.endOfTheDay(customer.getDateTo()).getTime()));
			}		
		}
		
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.address like ? ";
			list.add("%"+customer.getAddress().trim()+"%");
		}

		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a  order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a  order by a.CustCode ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
}

